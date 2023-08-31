package DomainModel;

public abstract class Media {

    private byte[] file;
    private String filename;
    private String date;
    private String time;
    private Educator uploader;

    public Media(byte[] file, String filename, String date, String time, Educator uploader) {
        this.file = file;
        this.filename = filename;
        this.date = date;
        this.time = time;
        this.uploader = uploader;
    }

    public byte[] getFile() {
        return file;
    }

    public String getFilename() {
        return filename;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Educator getUploader() {
        return uploader;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUploader(Educator uploader) {
        this.uploader = uploader;
    }
}
