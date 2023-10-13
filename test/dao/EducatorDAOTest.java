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

public class EducatorDAOTest {

    @Test
    public void getAllEducators(){
        EducatorDAO educatorDAO = new EducatorDAO();
        ArrayList<Educator> educators;
        try{
            educators = educatorDAO.getAllEducators();
            assertEquals(educators.size(), 1);
            assertEquals(educators.get(0).getEmail(), "matteo.lotti3@edu.unifi.it");
            assertEquals(educators.getClass(), ArrayList.class);

    } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
        }
        }

    @Test
    public void getEducatorbyEmail(){
        EducatorDAO educatorDAO = new EducatorDAO();
        try{
            Educator educators = educatorDAO.getEducatorbyemail("matteo.lotti3@edu.unifi.it");
            assertEquals(educators.getEmail(), "matteo.lotti3@edu.unifi.it");
            assertEquals(educators.getName(), "Matteo");
            assertEquals(educators.getSurname(), "Lotti");
        } catch (SQLException|ClassNotFoundException e) {
            e.printStackTrace();
        }
        }

    @Test
    public void addtEducator(){
        EducatorDAO educatorDAO = new EducatorDAO();
        Educator educator = new Educator("radu.ursu@edu.unifi.it", "Radu", "Ursu");
        Educator educator1 = new Educator("marina@edu.unifi.it", "Marina", "Carovani");
        try{
            educatorDAO.addEducator( educator);;
            educatorDAO.addEducator(educator1);;
            Educator educator2 = educatorDAO.getEducatorbyemail("radu.ursu@edu.unifi.it");
            Educator educator3 = educatorDAO.getEducatorbyemail("marina@edu.unifi.it");
            assertEquals(educator2.getEmail(), educator.getEmail());
            assertEquals(educator2.getName(), educator.getName());
            assertEquals(educator2.getSurname(), educator.getSurname());
            assertEquals(educator3.getEmail(), educator1.getEmail());
            assertEquals(educator3.getName(), educator1.getName());
            assertEquals(educator3.getSurname(), educator1.getSurname());
        } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                educatorDAO.deleteEducator("radu.ursu@edu.unifi.it");
                educatorDAO.deleteEducator("marina@edu.unifi.it");
            } catch (SQLException |ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        }

    @Test
    public void deleteEducator() {
        EducatorDAO educatorDAO = new EducatorDAO();
        Educator educator = new Educator("radu.ursu@edu.unifi.it", "Radu", "Ursu");
        try {
            educatorDAO.addEducator(educator);
            Educator educator1 = educatorDAO.getEducatorbyemail("radu.ursu@edu.unifi.it");
            assertEquals(educator1.getEmail(), educator.getEmail());
            assertEquals(educator1.getName(), educator.getName());
            assertEquals(educator1.getSurname(), educator.getSurname());
            educatorDAO.deleteEducator("radu.ursu@edu.unifi.it");
            educator1 = educatorDAO.getEducatorbyemail("radu.ursu@edu.unifi.it");
            assertEquals(educator1, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //@Test su getEducatorbyshift//TODO
}


