package scripts.beans;


/**
 *
 * @author Emil Kalbaliyev
 */
public class Script {
    private int id;
    private String name;
    private int count;
    private String date;
    private String note;
    private String comment;
    private String lastRunBy;
    private int status;
    private String duration;
    private int isDownloadable;
    private int additionalParam;
    private String fileModifiedTime;
    private int realtime;
    
    public Script() {
    }

    public Script(int id, String name, int count, String date, String note, String comment, String lastRunBy, int status, String duration, int isDownloadable, int additionalParam, String fileModifiedTime, int realtime) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.date = date;
        this.note = note;
        this.comment = comment;
        this.lastRunBy = lastRunBy;
        this.status = status;
        this.duration = duration;
        this.isDownloadable = isDownloadable;
        this.additionalParam = additionalParam;
        this.fileModifiedTime = fileModifiedTime;
        this.realtime = realtime;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLastRunBy() {
        return lastRunBy;
    }

    public void setLastRunBy(String lastRunBy) {
        this.lastRunBy = lastRunBy;
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
    public String getFileModifiedTime() {
        return fileModifiedTime;
    }

    public void setFileModifiedTime(String fileModifiedTime) {
        this.fileModifiedTime = fileModifiedTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRealtime() {
        return realtime;
    }

    public void setRealtime(int realtime) {
        this.realtime = realtime;
    }
    
}
