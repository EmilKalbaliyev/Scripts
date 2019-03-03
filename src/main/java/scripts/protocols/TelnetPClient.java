package scripts.protocols;

import scripts.dao.IJsonDao;
import scripts.service.TelnetTerminalException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Emil Kalbaliyev
 */
public class TelnetPClient {

    @Autowired(required = true)
    private IJsonDao jsonDao;
    public TelnetClient telnet = new TelnetClient();
    private InputStream in;
    private PrintStream out;
    private String prompt = ">";

    public static boolean loginSuccess = false;                                  //false da ola biler
    public static ArrayList<String> list = new ArrayList<String>();
    public static String errorMessage = "";
    public static String realUser = "";
    public static String strDate = "";

    public TelnetPClient() {
    }

    public TelnetPClient(String server, String user, String password) {
        try {
            telnet.connect(server, 23);
            telnet.setConnectTimeout(15);
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
            String s = user;
            realUser = user;

            readUntil("login: ");
            write(user);
            readUntil("Password: ");
            write(password);

            readUntil(">");
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 60 * 60 * 12);
                        if (telnet.isConnected()) {
                            telnet.disconnect();
                        }
                    } catch (InterruptedException ie) {
                    } catch (IOException ex) {
                        Logger.getLogger(TelnetPClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            };
            t.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readUntil(String pattern) throws Exception {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            boolean found = false;
            String s = "";
            char ch = (char) in.read();
            while (true) {
                sb.append(ch);

                if (sb.toString().contains("INFO: Connection failed.")
                        || sb.toString().contains("Cannot connect to MO service, exiting...")) {
                    throw new ServletException();
                } else if (sb.toString().contains("Login incorrect")) {
                    loginSuccess = false;
                    throw new TelnetTerminalException("Wrong Telnet arguments passed");

                } else if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        s += sb.toString();
                        loginSuccess = true;

                        list.add(s);
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //   throw new Exception();
        }
        return null;

    }

    public void write(String value) {
        try {
            out.println(value);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(String command) {
        try {
            write(command);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // return null;
    }

    public void disconnect() {
        try {
            System.out.println("Closing TELNET connection");
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
