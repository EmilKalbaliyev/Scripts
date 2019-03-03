package scripts.beans;

/**
 *
 * @author Emil Kalbaliyev
 */
public class ScriptJson {

    private int id;
    private String name;
    private String date;
    private String note;
    private String script;
    private int status;
    private int count;
    private String duration;
    private String lastPerson;
    private String lastDate;
    private String comment;
    private String checkFile;
    private String downloadFilePath;
    private int isDownloadable;
    private int realTime;
    private int additionalParam;

    public ScriptJson(int id, String name, String date, String note, String script, int status, int count, String duration, String lastPerson, String lastDate, String comment, String checkFile, String downloadFilePath, int isDownloadable, int realTime, int additionalParam) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.note = note;
        this.script = script;
        this.status = status;
        this.count = count;
        this.duration = duration;
        this.lastPerson = lastPerson;
        this.lastDate = lastDate;
        this.comment = comment;
        this.checkFile = checkFile;
        this.downloadFilePath = downloadFilePath;
        this.isDownloadable = isDownloadable;
        this.realTime = realTime;
        this.additionalParam = additionalParam;
    }


    public ScriptJson(int id, String name, String script) {
        this.id = id;
        this.name = name;
        this.script = script;
    }

    public ScriptJson() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLastPerson() {
        return lastPerson;
    }

    public void setLastPerson(String lastPerson) {
        this.lastPerson = lastPerson;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getCheckFile() {
        return checkFile;
    }

    public void setCheckFile(String checkFile) {
        this.checkFile = checkFile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDownloadFilePath() {
        return downloadFilePath;
    }

    public void setDownloadFilePath(String downloadFilePath) {
        this.downloadFilePath = downloadFilePath;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getIsDownloadable() {
        return isDownloadable;
    }

    public void setIsDownloadable(int isDownloadable) {
        this.isDownloadable = isDownloadable;
    }

    public int getAdditionalParam() {
        return additionalParam;
    }

    public void setAdditionalParam(int additionalParam) {
        this.additionalParam = additionalParam;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRealTime() {
        return realTime;
    }

    public void setRealTime(int realTime) {
        this.realTime = realTime;
    }

}
