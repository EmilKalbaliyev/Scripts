package scripts.protocols;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author Emil Kalbaliyev
 */
public class FtpClient {

    private String server;
    private final int port=21;
    private String user;
    private String password;
    private FTPClient ftp;

    public FtpClient(String server, String user, String password) throws IOException {
        ftp = new FTPClient();
       // ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }
        ftp.login(user, password);
    }

    public void downloadFile(String source, String destination) throws IOException {
        FileOutputStream out = new FileOutputStream(destination);
        ftp.retrieveFile(source, out);
    }

    public void close() throws IOException {
        ftp.disconnect();
    }
}
