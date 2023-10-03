package main.BusinessLogic;

import main.DomainModel.*;
import main.Orm.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class EducatorController {
    private final Notifier notifier;

    public EducatorController() {
        notifier = Notifier.getInstance();
    }

    public void uploadPhotosAndVideos(String filename, Educator educator, boolean isPhoto) throws SQLException, IOException, ParseException, MessagingException, ClassNotFoundException {
        MediaDAO mediaDAO = new MediaDAO();
        mediaDAO.uploadMedia(filename, educator.getEmail(), isPhoto);

        ParentDAO parentDAO = new ParentDAO();
        ArrayList<Parent> parents = new ArrayList<>();
        parents = parentDAO.getAllParents();
        notifier.sendEmailParent(parents, "New media", "a new media has been uploaded. You can check it on the website.");
    }

    public ArrayList<Media> viewPhotosAndVideos() throws SQLException, ClassNotFoundException {
        MediaDAO mediaDAO = new MediaDAO();
        ArrayList<Media> media = new ArrayList<>();
        media = mediaDAO.getAllMedia();
        return media;
    }

    public void deletePhotosAndVideos(Media media) throws SQLException, ClassNotFoundException {
        MediaDAO mediaDAO = new MediaDAO();
        mediaDAO.deleteMedia(media.getFilename());
    }

    public ArrayList<Child> viewChildrenList() throws SQLException, ClassNotFoundException {
        ChildDAO childDAO = new ChildDAO();
        return childDAO.getAllChildren();
    }

    public ArrayList<Workshift> viewWorkshifts(Educator educator) throws SQLException, ClassNotFoundException {
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        return workshiftDAO.getIndividualWorkshift(educator);
    }

    public ArrayList<Activity> viewActivities() throws SQLException, ClassNotFoundException {
        ActivityDAO activityDAO = new ActivityDAO();
        return activityDAO.getAllActivities();
    }

    public Child viewChildInfo(String idcode) throws SQLException, ClassNotFoundException {
        ChildDAO childDAO = new ChildDAO();
        return childDAO.getChild(idcode);
    }


}
