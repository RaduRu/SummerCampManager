package test.controllers;

import main.BusinessLogic.Admin;
import main.BusinessLogic.EducatorController;
import main.DomainModel.*;
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

    @Test
    public void viewPhotosAndVideos() {
        EducatorController educatorController = new EducatorController();
        Educator educator = new Educator("ursurds@gmail.com", "Radu", "Ursu");
        EducatorDAO educatorDAO = new EducatorDAO();
        MediaDAO mediaDAO = new MediaDAO();
        try {
            educatorDAO.addEducator(educator);
            mediaDAO.uploadMedia("imgs/FotoBimbiTest.png", educator.getEmail(), true);
            ArrayList<Media> media = new ArrayList<>();
            media = educatorController.viewPhotosAndVideos();
            assertEquals(media.get(media.size() - 1).getUploader().getEmail(), educator.getEmail());
        } catch (SQLException | ParseException | IOException | ClassNotFoundException e) {
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

    @Test
    public void deletePhotosAndVideos() {
        EducatorController educatorController = new EducatorController();
        Educator educator = new Educator("ursurds@gmail.com", "Radu", "Ursu");
        EducatorDAO educatorDAO = new EducatorDAO();
        MediaDAO mediaDAO = new MediaDAO();
        try {
            educatorDAO.addEducator(educator);
            mediaDAO.uploadMedia("imgs/FotoBimbiTest.png", educator.getEmail(), true);
            ArrayList<Media> media = new ArrayList<>();
            media = mediaDAO.getAllMedia();
            educatorController.deletePhotosAndVideos(media.get(media.size() - 1));
            Media m = mediaDAO.getMediabyfilename("FotoBimbiTest.png");
            assertNull(m);
        } catch (SQLException | ParseException | IOException | ClassNotFoundException e) {
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

    @Test
    public void viewChildInfo() {
        Child child = new Child("ABC123", "Marina", "Carovani", 8, "test");
        FeeStrategy feeStrategy = new OnlyChildFee();
        Subscription subscription = new Subscription(1, child, feeStrategy, false);
        child.setSubscription(subscription);

        ChildDAO childDAO = new ChildDAO();
        EducatorController educatorController = new EducatorController();

        try{
            childDAO.insertChild(child, subscription, "abc123");
            Child c = educatorController.viewChildInfo(child.getIdcode());
            assertEquals(c.getIdcode(), child.getIdcode());
            assertEquals(c.getName(), child.getName());
            assertEquals(c.getSurname(), child.getSurname());
            assertEquals(c.getAge(), child.getAge());
            assertEquals(c.getSubscription().getFee(), child.getSubscription().getFee());
        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        } finally {
            try {
                childDAO.delete(child);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void viewChildrenList(){
        Child child = new Child("ABC123", "Marina", "Carovani", 8, "test");
        FeeStrategy feeStrategy = new OnlyChildFee();
        Subscription subscription = new Subscription(1, child, feeStrategy, false);

        ChildDAO childDAO = new ChildDAO();
        Admin admin = new Admin();
        ArrayList<Child> children;

        try{
            childDAO.insertChild(child, subscription, "abc123");
            children = admin.viewChildrenList();
            assertEquals( children.get(children.size()-1).getIdcode(), child.getIdcode());
            assertEquals( children.get(children.size()-1).getName(), child.getName());
            assertEquals( children.get(children.size()-1).getSurname(), child.getSurname());
            assertEquals( children.get(children.size()-1).getAge(), child.getAge());
            assertEquals( children.get(children.size()-1).getDetails(), child.getDetails());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                childDAO.delete(child);
            } catch (SQLException |ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void  viewActivities(){
        Activity activity = new Activity("2020-12-12", "12:00:00", "test", TypeOfActivity.HOMEWORK);
        ActivityDAO activityDAO = new ActivityDAO();
        Admin admin = new Admin();
        ArrayList<Activity> activities;
        try{
            activityDAO.insert(activity);
            activities = admin.viewActivities();
            assertEquals( activities.get(activities.size()-1).getDate(), activity.getDate());
            assertEquals( activities.get(activities.size()-1).getTime(), activity.getTime());
            assertEquals( activities.get(activities.size()-1).getDescription(), activity.getDescription());
            assertEquals( activities.get(activities.size()-1).getType(), activity.getType());
        } catch (SQLException | ParseException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            try {
                activityDAO.delete(activity);
            } catch (SQLException | ParseException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
