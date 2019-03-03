package scripts.service;

import scripts.protocols.TelnetPClient;
import scripts.beans.Script;
import scripts.beans.ScriptJson;
import scripts.beans.User;
import scripts.dao.JsonDao;
import scripts.protocols.FtpClient;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Emil Kalbaliyev
 */
@Service
public class MainService {

    @Autowired
    JsonDao jsonDao;

    @Value("${path.to.temp}")
    private String tempPath;
    @Value("${NPC.file.name}")
    private String NPCFileName;
    @Value("${NPC.url}")
    private String NPCUrl;
    @Value("${path.to.NPC.download}")
    private String NPCDownloadUrl;
    @Value("${NPC.download.file.name}")
    private String NPCDownloadFileName;

    public List<Script> listScripts() throws IOException {
        List<ScriptJson> scriptJson = jsonDao.list();
        List< Script> scripts = new ArrayList<>();
        for (ScriptJson sjson : scriptJson) {
            String lastModifiedTime = "-";

            if (sjson.getIsDownloadable() == 1) {
                File file = new File(tempPath + sjson.getName() + ".txt");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                lastModifiedTime = sdf.format(file.lastModified());
            }
            String lastRunBy = sjson.getLastPerson() + " at " + sjson.getLastDate();

            Script s = new Script(sjson.getId(), sjson.getName(), sjson.getCount(),
                    sjson.getDate(), sjson.getNote(), sjson.getComment(), lastRunBy,
                    sjson.getStatus(), sjson.getDuration(), sjson.getIsDownloadable(),
                    sjson.getAdditionalParam(), lastModifiedTime, sjson.getRealTime());
            scripts.add(s);
        }
//        scripts.add(getNPC());
        return scripts;
    }

    public Script getNPC() throws IOException {
        ScriptJson sjson = jsonDao.getNPC();
        String lastModifiedTime = "-";
        String lastRunBy = sjson.getLastPerson() + " at " + sjson.getLastDate();

        Script s = new Script(sjson.getId(), sjson.getName(), sjson.getCount(),
                sjson.getDate(), sjson.getNote(), sjson.getComment(), lastRunBy,
                sjson.getStatus(), sjson.getDuration(), sjson.getIsDownloadable(),
                sjson.getAdditionalParam(), lastModifiedTime, sjson.getRealTime());

        return s;
    }

    public ScriptJson find(int id) throws IOException {
        List<ScriptJson> scriptJson = jsonDao.list();
        ScriptJson s = new ScriptJson();
        for (ScriptJson sjson : scriptJson) {
            if (sjson.getId() == id) {
                s = sjson;
            }
        }
        return s;
    }

    public void start(int id, String param, TelnetPClient telnet, User user) throws IOException {
        String command = find(id).getScript();
        if (!param.equals("")) {
            command = command.replace("additionalParam", param);
        }
        telnet.write(command);
        update(id, (String) user.getUsername());
    }

    public void update(int id, String username) throws IOException {
        List<ScriptJson> scriptJson = jsonDao.list();
        for (ScriptJson sjson : scriptJson) {
            if (sjson.getId() == id) {
                sjson.setStatus(1);
                sjson.setLastPerson(username);
                sjson.setCount(sjson.getCount() + 1);
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                String currentDate = formatter.format(new Date());
                sjson.setLastDate(currentDate);
            }
        }
        jsonDao.write(scriptJson);
    }

    public void refresh(TelnetPClient telnetService, User user) throws IOException, Exception {
        List<ScriptJson> scriptJson = jsonDao.list();
        for (ScriptJson sjson : scriptJson) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            LocalDateTime dateTime2 = LocalDateTime.parse(format.format(new Date()), formatter);
            LocalDateTime dateTime1 = LocalDateTime.parse(sjson.getLastDate(), formatter);
            long diffInSeconds = java.time.Duration.between(dateTime1, dateTime2).getSeconds();
            if (diffInSeconds > 60) {
                telnetService.write("cat " + sjson.getCheckFile());
                String s = telnetService.readUntil(user.getUsername() + "@");
                int newStatus = Integer.parseInt(s.substring(s.length() - user.getUsername().length() - 4, s.length() - user.getUsername().length() - 3));
                if (sjson.getStatus() == 1 && newStatus == 0 && sjson.getIsDownloadable() == 1) {
                    FtpClient ftp = new FtpClient("ip goes here", user.getUsername(), user.getPassword());
                    downloadFromOss2(ftp, sjson.getId());
                    ftp.close();
                }
                sjson.setStatus(newStatus);
                if (sjson.getIsDownloadable() == 1) {
                    File file = new File(tempPath + sjson.getName() + ".txt");
                    if (!file.exists()) {
                        FtpClient ftp = new FtpClient("ip goes here", user.getUsername(), user.getPassword());
                        downloadFromOss2(ftp, sjson.getId());
                        ftp.close();

                    }
                }
            }
        }
        jsonDao.write(scriptJson);
    }

    public File download(int id, User user) throws IOException {
        if (find(id).getRealTime() == 1) {
            FtpClient ftp = new FtpClient("ip goes here", user.getUsername(), user.getPassword());
            downloadFromOss2(ftp, id);
            ftp.close();
        }
        File file = new File(tempPath + find(id).getName() + ".txt");
        System.out.println(find(id).getName() + ".txt");
        if (!file.exists()) {
            FtpClient ftp = new FtpClient("ip goes here", user.getUsername(), user.getPassword());
            downloadFromOss2(ftp, id);
            ftp.close();
        }
        return file;
    }

    public void downloadFromOss2(FtpClient ftp, int id) throws IOException {
        List<ScriptJson> scriptJson = jsonDao.list();
        for (ScriptJson sjson : scriptJson) {
            if (id == sjson.getId()) {
                ftp.downloadFile(sjson.getDownloadFilePath(), tempPath + sjson.getName() + ".txt");
            }
        }
    }

    public List<ScriptJson> listScriptAdmin() throws IOException {
        List<ScriptJson> scriptJson = jsonDao.list();
        return scriptJson;
    }

    public void deleteFromJson(int id) throws IOException {
        List<ScriptJson> scriptJson = jsonDao.list();
        List<ScriptJson> newScriptJson = new ArrayList<>();
        for (ScriptJson sjson : scriptJson) {
            if (sjson.getId() != id) {
                newScriptJson.add(sjson);
            }
        }
        jsonDao.write(newScriptJson);

    }

    public void save(ScriptJson s) throws IOException {
        List<ScriptJson> scriptJson = jsonDao.list();
        List<ScriptJson> newScriptJson = new ArrayList<>();
        for (ScriptJson sjson : scriptJson) {
            if (sjson.getId() != s.getId()) {
                newScriptJson.add(sjson);
            }
        }
        newScriptJson.add(s);
        jsonDao.write(newScriptJson);
    }

    public int findNextID() throws IOException {
        List<ScriptJson> scriptJson = jsonDao.list();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (ScriptJson sjson : scriptJson) {
            ids.add(sjson.getId());
        }
        int nextVal = Collections.max(ids) + 1;
        if (nextVal == 1997) {
            nextVal++;
        }
        return nextVal;

    }

    public boolean adminLogin(User user) throws IOException {
        List<User> admins = jsonDao.listAdmin();
        boolean isValid = false;
        for (User admin : admins) {
            if (user.getUsername().equals(admin.getUsername())) {
                if (user.getPassword().equals(admin.getPassword())) {
                    isValid = true;
                }
            }
        }
        return isValid;

    }


}
