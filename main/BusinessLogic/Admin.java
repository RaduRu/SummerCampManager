package main.BusinessLogic;

import main.DomainModel.*;
import main.Orm.*;

import com.google.common.collect.Lists;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class Admin {
    private final Notifier notifier;

    public Admin() {
        notifier = Notifier.getInstance();
    }

    public void setActivity(Activity activity) throws SQLException, ParseException, MessagingException, ClassNotFoundException {
        ActivityDAO activityDAO = new ActivityDAO();
        activityDAO.insert(activity);

        ParentDAO parentDAO = new ParentDAO();

        notifier.sendEmailParent(parentDAO.getAllParents() , "New activity", "a new activity has been created. You can check it on the website.");

        EducatorDAO educatorDAO = new EducatorDAO();

        notifier.sendEmailEducator(educatorDAO.getAllEducators(), "New activity", "a new activity has been created. You can check it on the website.");
    }

    public void createWorkshifts() throws SQLException, ParseException, MessagingException, ClassNotFoundException {
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        EducatorDAO educatorDAO = new EducatorDAO();
        ArrayList<Workshift> workshifts = workshiftDAO.getWorkshifts();
        ArrayList<Educator> educators = educatorDAO.getAllEducators();

        for (String date : workshiftDAO.getDates()) {
            ArrayList<Workshift> wsdate = new ArrayList<>();
            for (Workshift workshift : workshifts) {
                if (Objects.equals(workshift.getDate(), date)) {
                    wsdate.add(workshift);
                }
            }
            Collections.shuffle(educators);
            List<List<Educator>> wseducators = Lists.partition(educators, wsdate.size());
            int i = 0;
            for(List<Educator> wsed: wseducators) {
                for (Educator educator : wsed) {
                    workshiftDAO.insert(educator, wsdate.get(i));
                }
                i++;
            }
        }
        notifier.sendEmailEducator(educators, "Workshifts created", "your workshifts for the Summer Camp have been created. You can check them on the website.");
        //se abbiamo tempo far vedere i workshift creati a tutti gli educatori
    }

    public void modifyWorkshift(Educator educator, Workshift oldworkshift, Workshift newworkshift ) throws SQLException, ParseException, MessagingException, ClassNotFoundException {
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        workshiftDAO.modify(educator, newworkshift, oldworkshift);
        ArrayList<Educator> educators = new ArrayList<>();
        educators.add(educator);
        notifier.sendEmailEducator(educators, "Workshift modified", "your workshift for the Summer Camp has been modified. You can check it on the website.");
    }

    public ArrayList<AbstractMap.SimpleEntry<Workshift, Educator>> viewWorkshifts() throws SQLException, ClassNotFoundException {
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        return workshiftDAO.getAllIndividualWorkshift();
    }

    public ArrayList<Activity> viewActivities() throws SQLException, ClassNotFoundException {
        ActivityDAO activityDAO = new ActivityDAO();
        return activityDAO.getAllActivities();
    }

    public void deleteActivity(Activity activity) throws SQLException, MessagingException, ParseException, ClassNotFoundException {
        ActivityDAO activityDAO = new ActivityDAO();
        activityDAO.delete(activity);

        ParentDAO parentDAO = new ParentDAO();
        notifier.sendEmailParent(parentDAO.getAllParents(), "Activity deleted", "an activity has been deleted. You can check it on the website.");

        EducatorDAO educatorDAO = new EducatorDAO();
        notifier.sendEmailEducator(educatorDAO.getAllEducators(), "Activity deleted", "an activity has been deleted. You can check it on the website.");
    }

    public void modifyActivity(Activity activity) throws SQLException, ParseException, MessagingException, ClassNotFoundException {
        ActivityDAO activityDAO = new ActivityDAO();
        activityDAO.modify(activity);

        ParentDAO parentDAO = new ParentDAO();
        notifier.sendEmailParent(parentDAO.getAllParents(), "Activity modified", "an activity has been modified. You can check it on the website.");

        EducatorDAO educatorDAO = new EducatorDAO();
        notifier.sendEmailEducator(educatorDAO.getAllEducators(), "Activity modified", "an activity has been modified. You can check it on the website.");

    }

    public ArrayList<Child> viewChildrenList() throws SQLException, ClassNotFoundException {
        ChildDAO childDAO = new ChildDAO();
        return childDAO.getAllChildren();
    }

    public void paymentReminder() throws SQLException, MessagingException, ClassNotFoundException {
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        ArrayList<Parent> parents = subscriptionDAO.getParentsNotPaid();

        ChildDAO childDAO = new ChildDAO();
        for (Parent parent : parents) {
            parent.setChildren(childDAO.getChildrenbyParent(parent.getIdcode()));
        }
        notifier.sendEmailParent(parents, "Payment reminder", "you have to pay the Summer Camp fee. You can check it on the website.");
    }








}
