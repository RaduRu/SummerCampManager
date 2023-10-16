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
        EducatorDAO educatordao = new EducatorDAO();
        String path = "imgs/FotoBimbiTest.png";
        String filename = "FotoBimbiTest.png";
        Educator educator = new Educator("gigi.gogi@edu.unifi.it", "Gigi", "Gogi");
        try {
            educatordao.addEducator(educator);
            mediaDAO.uploadMedia(path, educator.getEmail(),true );
            Media media = mediaDAO.getMediabyfilename(filename);
            assertEquals(media.getFilename(), filename);
            assertEquals(media.getUploader().getEmail(), educator.getEmail());
        } catch (SQLException | ClassNotFoundException| IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                mediaDAO.deleteMedia(filename);
                educatordao.deleteEducator(educator.getEmail());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void uploadMedia(){
        MediaDAO mediaDAO = new MediaDAO();
        EducatorDAO educatordao = new EducatorDAO();
        String path = "imgs/FotoBimbiTest.png";
        String filename = "FotoBimbiTest.png";
        String path1 = "imgs/LogoSummerCamp.png";
        String filename1 = "LogoSummerCamp.png";
        Educator educator = new Educator("gigi.gogi@edu.unifi.it", "Gigi", "Gogi");
        Educator educator1 = new Educator("dino.dani@edu.unifi.it", "Dino", "Dani");
        try{
            educatordao.addEducator(educator);
            educatordao.addEducator(educator1);
            mediaDAO.uploadMedia(path, educator.getEmail(), true);
            Media media = mediaDAO.getMediabyfilename(filename);
            assertEquals(media.getFilename(), filename);
            assertEquals(media.getUploader().getEmail(), educator.getEmail());
            assertEquals(media.getClass(), Photo.class);
            mediaDAO.uploadMedia(path1, educator1.getEmail(), false);
            Media media1 = mediaDAO.getMediabyfilename(filename1);
            assertEquals(media1.getFilename(), filename1);
            assertEquals(media1.getUploader().getEmail(), educator1.getEmail());
            assertEquals(media1.getClass(), Video.class);
        } catch (SQLException | ClassNotFoundException | IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                mediaDAO.deleteMedia(filename);
                educatordao.deleteEducator(educator.getEmail());
                mediaDAO.deleteMedia(filename1);
                educatordao.deleteEducator(educator1.getEmail());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void deleteMedia() {
        MediaDAO mediaDAO = new MediaDAO();
        EducatorDAO educatordao = new EducatorDAO();
        String path = "imgs/FotoBimbiTest.png";
        String filename = "FotoBimbiTest.png";
        Educator educator = new Educator("gigi.gogi@edu.unifi.it", "Gigi", "Gogi");
        try {
            educatordao.addEducator(educator);
            mediaDAO.uploadMedia(path, educator.getEmail(), true);
            Media media = mediaDAO.getMediabyfilename(filename);
            assertEquals(media.getFilename(), filename);
            assertEquals(media.getUploader().getEmail(), educator.getEmail());
            mediaDAO.deleteMedia(filename);
            media = mediaDAO.getMediabyfilename(filename);
            assertNull(media);
        } catch (SQLException | ClassNotFoundException | IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                mediaDAO.deleteMedia(filename);
                educatordao.deleteEducator(educator.getEmail());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
