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

public class ParentDAOTest {

    @Test
    public void getAllParents() {
        ParentDAO parentDAO = new ParentDAO();
        ArrayList<Parent> parents;
        try {
            parents = parentDAO.getAllParents();
            assertEquals(parents.size(), 1);
            assertEquals(parents.getClass(), ArrayList.class);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getParent() {
        ParentDAO parentDAO = new ParentDAO();
        try {
            Parent parent = parentDAO.getParent("abc123");
            assertEquals(parent.getEmail(), "radu.ursu@edu.unifi.it");
            assertEquals(parent.getName(), "Radu");
            assertEquals(parent.getSurname(), "Ursu");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addParent() {
        ParentDAO parentDAO = new ParentDAO();
        Parent parent = new Parent("abc222", "gigi.gogi@edu.unifi.it", "Gigi", "Gogi", "334456733");
        Parent parent2 = new Parent("abc333", "dino.dani@edu.unifi.it", "Dino", "Dani", "334459933");
        try {
            parentDAO.addParent(parent);
            parentDAO.addParent(parent2);
            Parent parent1 = parentDAO.getParent("abc222");
            Parent parent3 = parentDAO.getParent("abc333");
            assertEquals(parent3.getEmail(), "dino.dani@edu.unifi.it");
            assertEquals(parent3.getName(), "Dino");
            assertEquals(parent3.getSurname(), "Dani");
            assertEquals(parent3.getCellphone(), "334459933");
            assertEquals(parent1.getEmail(), "gigi.gogi@edu.unifi.it");
            assertEquals(parent1.getName(), "Gigi");
            assertEquals(parent1.getSurname(), "Gogi");
            assertEquals(parent1.getCellphone(), "334456733");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                parentDAO.deleteParent("abc222");
                parentDAO.deleteParent("abc333");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void deleteParent() {
        ParentDAO parentDAO = new ParentDAO();
        Parent parent = new Parent("abc222", "gigi.gogi@edu.unifi.it", "Gigi", "Gogi", "334456733");
        try {
            parentDAO.addParent(parent);
            Parent parent0 = parentDAO.getParent("abc222");
            assertEquals(parent0.getEmail(), "gigi.gogi@edu.unifi.it");
            assertEquals(parent0.getName(), "Gigi");
            assertEquals(parent0.getSurname(), "Gogi");
            assertEquals(parent0.getCellphone(), "334456733");
            parentDAO.deleteParent("abc222");
            Parent parent1 = parentDAO.getParent("abc222");
            assertEquals(parent1, null);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}