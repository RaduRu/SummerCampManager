package main.BusinessLogic;

import main.DomainModel.*;
import main.Orm.*;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParentController {
    private final Notifier notifier;

    public ParentController() {
        notifier = Notifier.getInstance();
    }

    public void registerChild(Child child, int numWeeks, String parentid) throws SQLException, ClassNotFoundException {
        ChildDAO childDAO = new ChildDAO();
        ArrayList<Child> children = childDAO.getChildrenbyParent(parentid);
        FeeStrategy feeStrategy;
        if(children.isEmpty())
            feeStrategy = new OnlyChildFee();
        else if (children.size() == 1) {
            feeStrategy = new SiblingFee();
            SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
            subscriptionDAO.editFeeStrategy(children.get(0).getIdcode(), feeStrategy);
        }
        else
            feeStrategy = new SiblingFee();
        Subscription subscription = new Subscription(numWeeks, child, feeStrategy, false);
        child.setSubscription(subscription);
        childDAO.insertChild(child, subscription, parentid);
    }

    public void payFee(String idcodeChild, String idcodeParent) throws SQLException, MessagingException, ClassNotFoundException {
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        subscriptionDAO.editFeePaid(idcodeChild, true);
        Subscription subscription = subscriptionDAO.getChildInfo(idcodeChild);

        ParentDAO parentDAO = new ParentDAO();
        ArrayList<Parent> parents = new ArrayList<>();
        parents.add(parentDAO.getParent(idcodeParent));
        notifier.sendEmailParent(parents, "Fee paid", "the fee of "+ subscription.getFee() + " euros for your child "+ subscription.getChild().getName()+ " " +
                subscription.getChild().getSurname() + " has been paid successfully.");
    }

    public ArrayList<Child> viewChildrenInfo(String parentid) throws SQLException, ClassNotFoundException {
        ChildDAO childDAO = new ChildDAO();
        return childDAO.getChildrenbyParent(parentid);
    }

    public ArrayList<Media> viewPhotosAndVideos() throws SQLException, ClassNotFoundException, IOException {
        MediaDAO mediaDAO = new MediaDAO();
        ArrayList<Media> media = new ArrayList<>();
        media = mediaDAO.getAllMedia();
        for(Media m : media){
            File file = new File("imgs/out/" + m.getFilename());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(m.getFile());
        }
        return media;
    }

    public ArrayList<Activity> viewActivities() throws SQLException, ClassNotFoundException {
        ActivityDAO activityDAO = new ActivityDAO();
        return activityDAO.getAllActivities();
    }


}
