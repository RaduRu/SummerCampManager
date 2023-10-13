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

public class ChildDAOTest {

    @Test
    public void insertChild(){
        ChildDAO childdao = new ChildDAO();
        Child child = new Child("12345678", "name", "surname", 5, "details");
        Subscription subscription = new Subscription(1, child , new OnlyChildFee(), false);
        child.setSubscription(subscription);
        try{
            childdao.insertChild(child,subscription, "abc123");
            Child child1 = childdao.getChild("12345678");
            assertEquals(child1.getIdcode(), child.getIdcode());
            assertEquals(child1.getName(), child.getName());
            assertEquals(child1.getSurname(), child.getSurname());
            assertEquals(child1.getAge(), child.getAge());
            assertEquals(child1.getDetails(), child.getDetails());
            assertEquals(child1.getSubscription().getWeeksnum(), child.getSubscription().getWeeksnum());
            assertEquals(child1.getSubscription().getFeeStrategy().getClass(), child.getSubscription().getFeeStrategy().getClass());
            assertEquals(child1.getSubscription().isPaid(), child.getSubscription().isPaid());
        } catch (SQLException|ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                childdao.delete(child);
            } catch (SQLException|ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}

