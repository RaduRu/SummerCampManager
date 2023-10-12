package test.controllers;

import main.BusinessLogic.Admin;
import main.BusinessLogic.EducatorController;
import main.BusinessLogic.ParentController;
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

public class ParentControllerTest {
    @Test
    public void registerChild() {
        ParentController parentController = new ParentController();
        String idcode = "ursurds";
        Child child = new Child(idcode, "Radu", "Ursu", 9, "test2");
        Child child2 = new Child("mrncrvn", "Marina", "Carovani", 7, "test3");
        ChildDAO childDAO = new ChildDAO();
        try{
            parentController.registerChild(child, 2, "abc123");
            ArrayList<Child> children = childDAO.getChildrenbyParent("abc123");
            assertEquals(children.get(children.size()-1).getIdcode(), child.getIdcode());
            assertEquals(children.get(children.size()-1).getName(), child.getName());
            assertEquals(children.get(children.size()-1).getSurname(), child.getSurname());
            assertEquals(children.get(children.size()-1).getAge(), child.getAge());
            assertEquals(children.get(children.size()-1).getDetails(), child.getDetails());
            assertEquals(children.get(children.size()-1).getSubscription().getWeeksnum(), child.getSubscription().getWeeksnum());
            assertEquals(children.get(children.size()-1).getSubscription().getFee(), child.getSubscription().getFee());
            assertInstanceOf(OnlyChildFee.class, children.get(children.size()-1).getSubscription().getFeeStrategy());
            parentController.registerChild(child2, 2, "abc123");
            children = childDAO.getChildrenbyParent("abc123");
            for(Child c : children){
                assertInstanceOf(SiblingFee.class, c.getSubscription().getFeeStrategy());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                childDAO.delete(child);
                childDAO.delete(child2);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void payFee() {
        ParentController parentController = new ParentController();
        Child child = new Child("ursurds", "Radu", "Ursu", 9, "test2");
        ChildDAO childDAO = new ChildDAO();
        try{
            childDAO.insertChild(child, new Subscription(2, child, new OnlyChildFee(), false), "abc123");
            parentController.payFee(child.getIdcode(), "abc123");
            ArrayList<Child> children = childDAO.getChildrenbyParent("abc123");
            assertTrue(children.get(children.size()-1).getSubscription().isPaid());
        } catch (SQLException | ClassNotFoundException | MessagingException e) {
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

    @Test
    public void viewChildrenInfo() {
        ParentController parentController = new ParentController();
        Child child = new Child("ursurds", "Radu", "Ursu", 9, "test2");
        Subscription subscription = new Subscription(2, child, new OnlyChildFee(), false);
        child.setSubscription(subscription);
        ChildDAO childDAO = new ChildDAO();
        try{
            childDAO.insertChild(child, subscription, "abc123");
            ArrayList<Child> children = parentController.viewChildrenInfo("abc123");
            assertEquals(children.get(children.size()-1).getIdcode(), child.getIdcode());
            assertEquals(children.get(children.size()-1).getName(), child.getName());
            assertEquals(children.get(children.size()-1).getSurname(), child.getSurname());
            assertEquals(children.get(children.size()-1).getAge(), child.getAge());
            assertEquals(children.get(children.size()-1).getDetails(), child.getDetails());
            assertEquals(children.get(children.size()-1).getSubscription().getWeeksnum(), child.getSubscription().getWeeksnum());
            assertEquals(children.get(children.size()-1).getSubscription().getFee(), child.getSubscription().getFee());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                childDAO.delete(child);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
