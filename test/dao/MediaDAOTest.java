package test.dao;
import main.DomainModel.Activity;
import main.DomainModel.TypeOfActivity;
import main.Orm.*;
import org.junit.Test;
import main.BusinessLogic.Admin;
import main.DomainModel.*;
import main.Orm.ActivityDAO;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


import java.sql.SQLException;
import java.text.ParseException;

public class MediaDAOTest {
    @Test
    public void getAllMedia(){
        MediaDAO mediaDAO = new MediaDAO();
        ArrayList<Media> media;
        try {
            media = mediaDAO.getAllMedia();
            assertEquals(media.size(), 0);
            assertEquals(media.getClass(), ArrayList.class);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMediabyfilename(){
        MediaDAO mediaDAO = new MediaDAO();
        EducatorDAO educator = new EducatorDAO();
        byte[] num = new byte[10];
        Educator educator1 = new Educator("gigi.gogi@edu.unifi.it", "Gigi", "Gogi");
        Photo photo = new Photo(num, "test", "2020-12-12", "12:00:00", educator1);
        try {
            mediaDAO.uploadMedia("test","gigi.gogi@edu.unifi.it",true );
            Media media = mediaDAO.getMediabyfilename(photo.getFilename());
            assertEquals(media.getFilename(), photo.getFilename());
            assertEquals(media.getDate(), photo.getDate());
            assertEquals(media.getTime(), photo.getTime());
            assertEquals(media.getUploader().getEmail(), photo.getUploader().getEmail());
        } catch (SQLException | ClassNotFoundException| IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                educator.deleteEducator(educator1.getEmail());
                mediaDAO.deleteMedia(photo.getFilename());
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }


    }
}
