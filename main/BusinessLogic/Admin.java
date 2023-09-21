package BusinessLogic;

import DomainModel.Activity;
import DomainModel.Educator;
import DomainModel.Workshift;
import Orm.ActivityDAO;
import Orm.EducatorDAO;
import Orm.WorkshiftDAO;

import com.google.common.collect.Lists;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Admin {
    private final Notifier notifier;

    public Admin() {
        notifier = Notifier.getInstance();
    }

    public void setActivity(Activity activity) throws SQLException, ParseException {
        ActivityDAO activityDAO = new ActivityDAO();
        activityDAO.insert(activity);
    }

    public void createWorkshifts() throws SQLException, ParseException, MessagingException {
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




}
