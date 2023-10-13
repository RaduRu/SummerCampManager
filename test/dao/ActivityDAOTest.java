package test.dao;

import main.DomainModel.Activity;
import main.DomainModel.TypeOfActivity;
import main.Orm.ActivityDAO;
import org.junit.Test;
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


import java.sql.SQLException;
import java.text.ParseException;

public class ActivityDAOTest {

    @Test
    public void getAllActivities() {
        ActivityDAO activityDAO = new ActivityDAO();
        ArrayList<Activity> activities;
        try {
            activities = activityDAO.getAllActivities();
            assertEquals(activities.size(), 0);
            assertEquals(activities.getClass(), ArrayList.class);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insert() {
        ActivityDAO activityDAO = new ActivityDAO();
        Activity activity = new Activity("2020-12-12", "12:00:00", "test", TypeOfActivity.HOMEWORK);
        Activity activity2 = new Activity("2021-12-12", "13:00:00", "test2", TypeOfActivity.MUSIC);
        Activity activity3 = new Activity("2022-12-12", "14:00:00", "test3", TypeOfActivity.OTHER);
        try {
            activityDAO.insert(activity);
            activityDAO.insert(activity2);
            activityDAO.insert(activity3);
            Activity activity0 = activityDAO.getActivitybyDateAndTime(activity.getDate(), activity.getTime());
            Activity activity1 = activityDAO.getActivitybyDateAndTime(activity2.getDate(), activity2.getTime());
            Activity activity4 = activityDAO.getActivitybyDateAndTime(activity3.getDate(), activity3.getTime());
            assertEquals(activity0.getDate(), activity.getDate());
            assertEquals(activity0.getTime(), activity.getTime());
            assertEquals(activity0.getDescription(), activity.getDescription());
            assertEquals(activity0.getType(), activity.getType());
            assertEquals(activity1.getDate(), activity2.getDate());
            assertEquals(activity1.getTime(), activity2.getTime());
            assertEquals(activity1.getDescription(), activity2.getDescription());
            assertEquals(activity1.getType(), activity2.getType());
            assertEquals(activity4.getDate(), activity3.getDate());
            assertEquals(activity4.getTime(), activity3.getTime());
            assertEquals(activity4.getDescription(), activity3.getDescription());
            assertEquals(activity4.getType(), activity3.getType());
        } catch (SQLException | ParseException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                activityDAO.delete(activity);
                activityDAO.delete(activity2);
                activityDAO.delete(activity3);
            } catch (SQLException | ParseException| ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void delete() {
        ActivityDAO activityDAO = new ActivityDAO();
        Activity activity = new Activity("2020-12-12", "12:00:00", "test", TypeOfActivity.HOMEWORK);
        try {
            activityDAO.insert(activity);
            Activity activity0 = activityDAO.getActivitybyDateAndTime(activity.getDate(), activity.getTime());
            assertEquals(activity0.getDate(), activity.getDate());
            assertEquals(activity0.getTime(), activity.getTime());
            assertEquals(activity0.getDescription(), activity.getDescription());
            assertEquals(activity0.getType(), activity.getType());
            activityDAO.delete(activity);
            Activity activity1 = activityDAO.getActivitybyDateAndTime(activity.getDate(), activity.getTime());
            assertEquals(activity1, null);
        } catch (SQLException | ParseException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void modify() {
        ActivityDAO activityDAO = new ActivityDAO();
        Activity activity = new Activity("2020-12-12", "12:00:00", "test", TypeOfActivity.HOMEWORK);
        try {
            activityDAO.insert(activity);
            Activity activity0 = activityDAO.getActivitybyDateAndTime(activity.getDate(), activity.getTime());
            assertEquals(activity0.getDate(), activity.getDate());
            assertEquals(activity0.getTime(), activity.getTime());
            assertEquals(activity0.getDescription(), activity.getDescription());
            assertEquals(activity0.getType(), activity.getType());
            activity.setDescription("test2");
            activity.setType(TypeOfActivity.MUSIC);
            activityDAO.modify(activity);
            Activity activity1 = activityDAO.getActivitybyDateAndTime(activity.getDate(), activity.getTime());
            assertEquals(activity1.getDate(), activity.getDate());
            assertEquals(activity1.getTime(), activity.getTime());
            assertEquals(activity1.getDescription(), activity.getDescription());
            assertEquals(activity1.getType(), activity.getType());
        } catch (SQLException | ParseException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                activityDAO.delete(activity);
            } catch (SQLException | ParseException| ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void getActivitybyDateAndTime() {
        ActivityDAO activityDAO = new ActivityDAO();
        Activity activity = new Activity("2020-12-12", "12:00:00", "test", TypeOfActivity.HOMEWORK);
        try {
            activityDAO.insert(activity);
            Activity activity0 = activityDAO.getActivitybyDateAndTime(activity.getDate(), activity.getTime());
            assertEquals(activity0.getDate(), activity.getDate());
            assertEquals(activity0.getTime(), activity.getTime());
            assertEquals(activity0.getDescription(), activity.getDescription());
            assertEquals(activity0.getType(), activity.getType());
        } catch (SQLException | ParseException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                activityDAO.delete(activity);
            } catch (SQLException | ParseException| ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
