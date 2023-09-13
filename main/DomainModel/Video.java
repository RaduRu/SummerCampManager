package DomainModel;

public class Video extends Media{
    public Video(byte[] file, String filename, String date, String time, Educator uploader) {
        super(file, filename, date, time, uploader);
    }
}
