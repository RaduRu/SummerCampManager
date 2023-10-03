package test.controllers;

import main.BusinessLogic.Admin;
import main.BusinessLogic.EducatorController;
import main.DomainModel.Educator;
import main.DomainModel.Media;
import main.Orm.*;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class EducatorControllerTest {
    @Test
    public void uploadPhotosAndVideos() {
        EducatorController educatorController = new EducatorController();
        Educator educator = new Educator("ursurds@gmail.com", "Radu", "Ursu");
        EducatorDAO educatorDAO = new EducatorDAO();
        MediaDAO mediaDAO = new MediaDAO();
        try {
            educatorDAO.addEducator(educator);
            educatorController.uploadPhotosAndVideos("imgs/FotoBimbiTest.png", educator, true);
            ArrayList<Media> media = new ArrayList<>();
            media = mediaDAO.getAllMedia();
            assertEquals(media.get(media.size() - 1).getUploader().getEmail(), educator.getEmail());
        } catch (SQLException | ParseException | MessagingException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                mediaDAO.deleteMedia("FotoBimbiTest.png");
                educatorDAO.deleteEducator(educator.getEmail());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }




}
