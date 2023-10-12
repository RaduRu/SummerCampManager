package test.controllers;

import main.BusinessLogic.Admin;
import main.DomainModel.*;
import main.Orm.ActivityDAO;
import main.Orm.ChildDAO;
import main.Orm.EducatorDAO;
import main.Orm.ParentDAO;
import org.junit.Test;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {


    @Test
    public void setActivity(){
        Admin admin = new Admin();
        Activity activity = new Activity("2020-12-12", "12:00:00", "test", TypeOfActivity.HOMEWORK);
        try {
            admin.setActivity(activity);
            ActivityDAO activityDAO = new ActivityDAO();
            Activity a = activityDAO.getActivitybyDateAndTime(activity.getDate(), activity.getTime());
            assertEquals( activity.getDate(), a.getDate());
            assertEquals( activity.getTime(), a.getTime());
            assertEquals( activity.getDescription(), a.getDescription());
            assertEquals( activity.getType(), a.getType());
        } catch (SQLException | ParseException | MessagingException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally{
            try {
                ActivityDAO activityDAO = new ActivityDAO();
                activityDAO.delete(activity);
            } catch (SQLException | ParseException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
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
    public void deleteActivity(){
        Activity activity = new Activity("2020-12-12", "12:00:00", "test", TypeOfActivity.HOMEWORK);
        ActivityDAO activityDAO = new ActivityDAO();
        Admin admin = new Admin();
        try{
            activityDAO.insert(activity);
            admin.deleteActivity(activity);
            assertNull(activityDAO.getActivitybyDateAndTime(activity.getDate(), activity.getTime()));
        } catch (SQLException | ParseException | MessagingException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void modifyActivity(){
        Activity activity = new Activity("2020-12-12", "12:00:00", "test", TypeOfActivity.HOMEWORK);
        ActivityDAO activityDAO = new ActivityDAO();
        Admin admin = new Admin();
        try{
            activityDAO.insert(activity);
            Activity newActivity = new Activity("2020-12-12", "12:00:00", "test2", TypeOfActivity.GAME);
            admin.modifyActivity(newActivity);
            Activity a = activityDAO.getActivitybyDateAndTime(activity.getDate(), activity.getTime());
            assertEquals( newActivity.getDate(), a.getDate());
            assertEquals( newActivity.getTime(), a.getTime());
            assertEquals( newActivity.getDescription(), a.getDescription());
            assertEquals( newActivity.getType(), a.getType());
        } catch (SQLException | ParseException | MessagingException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            try {
                activityDAO.delete(activity);
            } catch (SQLException | ParseException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //CREATE_WORKSHIFTS: probabilmente test empirico guardando tabella da postgres
    //VIEW_WORKSHIFTS: probabilmente test empirico guardando tabella da postgres
    //MODIFY_WORKSHIFTS: probabilmente test empirico guardando tabella da postgres
    //nella relazione specificare che i test sono stati fatti empiricamente perché avremmo potuto testare solo esistenza e non contenuto
    //Anche PAYMENT_REMINDER è test empirico

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
}
