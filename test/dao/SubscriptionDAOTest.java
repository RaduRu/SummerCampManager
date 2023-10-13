package test.dao;

import main.DomainModel.Child;
import main.DomainModel.OnlyChildFee;
import main.DomainModel.SiblingFee;
import main.DomainModel.Subscription;
import main.Orm.ChildDAO;
import main.Orm.SubscriptionDAO;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionDAOTest {

    @Test
    public void getChildInfo() {
        ChildDAO childDAO = new ChildDAO();
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        Child child = new Child("12345678", "name", "surname", 5, "details");
        Child child2 = new Child("ABC123", "name2", "surname2", 7, "details2");
        Subscription subscription = new Subscription(1, child , new OnlyChildFee(), false);
        Subscription subscription2 = new Subscription(3, child2 , new SiblingFee(), false);
        child.setSubscription(subscription);
        child2.setSubscription(subscription2);
        try {
            childDAO.insertChild(child, subscription, "abc123");
            childDAO.insertChild(child2, subscription2, "abc123");
            Subscription subscription1 = subscriptionDAO.getChildInfo("12345678");
            assertEquals(subscription1.getWeeksnum(), subscription.getWeeksnum());
            assertEquals(subscription1.getFeeStrategy().getClass(), subscription.getFeeStrategy().getClass());
            assertEquals(subscription1.isPaid(), subscription.isPaid());
            Subscription subscription3 = subscriptionDAO.getChildInfo("ABC123");
            assertEquals(subscription3.getWeeksnum(), subscription2.getWeeksnum());
            assertEquals(subscription3.getFeeStrategy().getClass(), subscription2.getFeeStrategy().getClass());
            assertEquals(subscription3.isPaid(), subscription2.isPaid());
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
    public void editFeeStrategy() {
        ChildDAO childDAO = new ChildDAO();
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        Child child = new Child("12345678", "name", "surname", 5, "details");
        Subscription sub = new Subscription(1, child , new OnlyChildFee(), false);
        child.setSubscription(sub);
        try {
            childDAO.insertChild(child, sub, "abc123");
            Subscription subscription = subscriptionDAO.getChildInfo("12345678");
            assertEquals(subscription.getWeeksnum(), sub.getWeeksnum());
            assertEquals(subscription.getFeeStrategy().getClass(), sub.getFeeStrategy().getClass());
            assertEquals(subscription.isPaid(), sub.isPaid());
            subscriptionDAO.editFeeStrategy("12345678", new SiblingFee());
            Subscription subscription1 = subscriptionDAO.getChildInfo("12345678");
            assertEquals(subscription1.getWeeksnum(), subscription.getWeeksnum());
            assertEquals(subscription1.getFeeStrategy().getClass(), new SiblingFee().getClass());
            assertEquals(subscription1.isPaid(), subscription.isPaid());
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

    @Test
    public void editFeePaid() {
        ChildDAO childDAO = new ChildDAO();
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        Child child = new Child("12345678", "name", "surname", 5, "details");
        Subscription sub = new Subscription(1, child , new OnlyChildFee(), false);
        child.setSubscription(sub);
        try {
            childDAO.insertChild(child, sub, "abc123");
            Subscription subscription = subscriptionDAO.getChildInfo("12345678");
            assertEquals(subscription.getWeeksnum(), sub.getWeeksnum());
            assertEquals(subscription.getFeeStrategy().getClass(), sub.getFeeStrategy().getClass());
            assertEquals(subscription.isPaid(), sub.isPaid());
            subscriptionDAO.editFeePaid("12345678", true);
            Subscription subscription1 = subscriptionDAO.getChildInfo("12345678");
            assertEquals(subscription1.getWeeksnum(), subscription.getWeeksnum());
            assertEquals(subscription1.getFeeStrategy().getClass(), subscription.getFeeStrategy().getClass());
            assertEquals(subscription1.isPaid(), true);
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

    @Test
    public void getParentsNotPaid() {
        ChildDAO childDAO = new ChildDAO();
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        Child child = new Child("12345678", "name", "surname", 5, "details");
        Child child2 = new Child("ABC123", "name2", "surname2", 7, "details2");
        Subscription subscription = new Subscription(1, child, new OnlyChildFee(), false);
        Subscription subscription2 = new Subscription(3, child2, new SiblingFee(), false);
        child.setSubscription(subscription);
        child2.setSubscription(subscription2);
        try {
            childDAO.insertChild(child, subscription, "abc123");
            childDAO.insertChild(child2, subscription2, "abc123");
            assertEquals(subscriptionDAO.getParentsNotPaid().size(), 1);
            assertEquals(subscriptionDAO.getParentsNotPaid().get(0).getIdcode(), "abc123");
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
}
