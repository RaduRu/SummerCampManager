package test.controllers;

import main.BusinessLogic.Admin;
import main.DomainModel.Activity;
import main.DomainModel.Educator;
import main.DomainModel.Parent;
import main.DomainModel.TypeOfActivity;
import main.Orm.ActivityDAO;
import main.Orm.EducatorDAO;
import main.Orm.ParentDAO;
import org.junit.Test;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {


    @Test
    public void setActivity(){
        Admin admin = new Admin();
        Activity activity = new Activity("2020-12-12", "12:00:00", "test", 1, TypeOfActivity.GAME);
        try {
            admin.setActivity(activity);
            ActivityDAO activityDAO = new ActivityDAO();
            Activity a = activityDAO.getActivitybyId(activity.getId());
            assertEquals( activity.getId(), a.getId());
            assertEquals( activity.getDate(), a.getDate());
            assertEquals( activity.getTime(), a.getTime());
            assertEquals( activity.getDescription(), a.getDescription());
            assertEquals( activity.getType(), a.getType());
        } catch (SQLException |ParseException | MessagingException e) {
            e.printStackTrace();
        }finally{
            try {
                ActivityDAO activityDAO = new ActivityDAO();
                activityDAO.delete(activity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
