package BusinessLogic;

import DomainModel.*;
import Orm.*;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParentController {
    private final Notifier notifier;

    public ParentController() {
        notifier = Notifier.getInstance();
    }

    public void registerChild(Child child, int numWeeks, String parentid) throws SQLException {
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
        childDAO.insertChild(child, subscription, parentid);
    }

    public void payFee(String idcode) throws SQLException, MessagingException {
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        subscriptionDAO.editFeePaid(idcode, true);
        Subscription subscription = subscriptionDAO.getChildInfo(idcode);

        ParentDAO parentDAO = new ParentDAO();
        ArrayList<Parent> parents = new ArrayList<>();
        parents.add(parentDAO.getParent(idcode));
        notifier.sendEmailParent(parents, "Fee paid", "the fee of "+ subscription.getFee() + "€ for your child"+ subscription.getChild().getName()+
                subscription.getChild().getSurname() + " has been paid successfully.");
    }

    public ArrayList<Child> viewChildrenInfo(String parentid) throws SQLException {
        ChildDAO childDAO = new ChildDAO();
        return childDAO.getChildrenbyParent(parentid);
    }

    public ArrayList<Media> viewPhotosAndVideos() throws SQLException {
        MediaDAO mediaDAO = new MediaDAO();
        return mediaDAO.getAllMedia();
    }

    public ArrayList<Activity> viewActivities() throws SQLException {
        ActivityDAO activityDAO = new ActivityDAO();
        return activityDAO.getAllActivities();
    }


}
