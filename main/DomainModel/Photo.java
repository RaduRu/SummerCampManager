package main.DomainModel;

public class Photo extends Media {
    public Photo(byte[] file, String filename, String date, String time, Educator uploader) {
        super(file, filename, date, time, uploader);
    }
}
