package test.dao;

import main.DomainModel.Activity;
import main.DomainModel.TypeOfActivity;
import main.Orm.*;
import org.junit.Test;
import main.BusinessLogic.Admin;
import main.DomainModel.*;
import main.Orm.ActivityDAO;
import org.junit.Test;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


import java.sql.SQLException;
import java.text.ParseException;

public class WorkshiftDAOTest {

    @Test
    public void getWorkshifts(){
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        ArrayList<Workshift> workshifts;
        Workshift workshift = new Workshift("2020-01-01", "08:00:00");
        try {
            workshifts = workshiftDAO.getWorkshifts();
            assertEquals(workshifts.size(), 0);
            assertEquals(workshifts.getClass(), ArrayList.class);
            workshiftDAO.addWorkshift(workshift);
            workshifts = workshiftDAO.getWorkshifts();
            assertEquals(workshifts.size(), 1);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                workshiftDAO.deleteWorkshift(workshift);
            } catch (SQLException | ClassNotFoundException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void getIndividualWorkshift() {
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        Workshift workshift = new Workshift("2020-01-01", "08:00:00");
        EducatorDAO educatorDAO = new EducatorDAO();
        Educator educator = new Educator("gigi.gogi@gmail.com", "Gigi", "Gogi");
        try {
            workshiftDAO.addWorkshift(workshift);
            educatorDAO.addEducator(educator);
            workshiftDAO.insert(educator, workshift);
            ArrayList<Workshift> workshifts = workshiftDAO.getIndividualWorkshift(educator);
            assertEquals(workshifts.size(), 1);
            assertEquals(workshifts.get(0).getDate(), workshift.getDate());
            assertEquals(workshifts.get(0).getTime(), workshift.getTime());
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                workshiftDAO.delete(workshift, educator);
                workshiftDAO.deleteWorkshift(workshift);
                educatorDAO.deleteEducator(educator.getEmail());
            } catch (SQLException | ClassNotFoundException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void getAllIndividualWorkshift(){
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        Workshift workshift = new Workshift("2020-01-01", "08:00:00");
        Workshift workshift1 = new Workshift("2020-01-02", "08:00:00");
        EducatorDAO educatorDAO = new EducatorDAO();
        Educator educator = new Educator("gigi.gogi@gmail.com", "Gigi", "Gogi");
        Educator educator1 = new Educator("dino.dini@gmail.com", "Dino", "Dini");
        try {
            workshiftDAO.addWorkshift(workshift);
            workshiftDAO.addWorkshift(workshift1);
            educatorDAO.addEducator(educator);
            educatorDAO.addEducator(educator1);
            workshiftDAO.insert(educator, workshift);
            workshiftDAO.insert(educator1, workshift1);
            ArrayList<AbstractMap.SimpleEntry<Workshift, Educator>> workshifts = workshiftDAO.getAllIndividualWorkshift();
            assertEquals(workshifts.size(), 2);
            assertEquals(workshifts.get(0).getKey().getDate(), workshift.getDate());
            assertEquals(workshifts.get(0).getKey().getTime(), workshift.getTime());
            assertEquals(workshifts.get(0).getValue().getEmail(), educator.getEmail());
            assertEquals(workshifts.get(1).getKey().getDate(), workshift1.getDate());
            assertEquals(workshifts.get(1).getKey().getTime(), workshift1.getTime());
            assertEquals(workshifts.get(1).getValue().getEmail(), educator1.getEmail());
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                workshiftDAO.delete(workshift, educator);
                workshiftDAO.delete(workshift1, educator1);
                workshiftDAO.deleteWorkshift(workshift);
                workshiftDAO.deleteWorkshift(workshift1);
                educatorDAO.deleteEducator(educator.getEmail());
                educatorDAO.deleteEducator(educator1.getEmail());
            } catch (SQLException | ClassNotFoundException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void insert() {
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        Workshift workshift = new Workshift("2020-01-01", "08:00:00");
        EducatorDAO educatorDAO = new EducatorDAO();
        Educator educator = new Educator("gigi.gogi@gmail.com", "Gigi", "Gogi");
        try {
            workshiftDAO.addWorkshift(workshift);
            educatorDAO.addEducator(educator);
            workshiftDAO.insert(educator, workshift);
            ArrayList<Workshift> workshifts = workshiftDAO.getIndividualWorkshift(educator);
            assertEquals(workshifts.size(), 1);
            assertEquals(workshifts.get(0).getDate(), workshift.getDate());
            assertEquals(workshifts.get(0).getTime(), workshift.getTime());
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                workshiftDAO.delete(workshift, educator);
                workshiftDAO.deleteWorkshift(workshift);
                educatorDAO.deleteEducator(educator.getEmail());
            } catch (SQLException | ClassNotFoundException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void delete() {
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        Workshift workshift = new Workshift("2020-01-01", "08:00:00");
        EducatorDAO educatorDAO = new EducatorDAO();
        Educator educator = new Educator("gigi.gogi@gmail.com", "Gigi", "Gogi");
        try {
            workshiftDAO.addWorkshift(workshift);
            educatorDAO.addEducator(educator);
            workshiftDAO.insert(educator, workshift);
            ArrayList<Workshift> workshifts = workshiftDAO.getIndividualWorkshift(educator);
            assertEquals(workshifts.size(), 1);
            assertEquals(workshifts.get(0).getDate(), workshift.getDate());
            assertEquals(workshifts.get(0).getTime(), workshift.getTime());
            workshiftDAO.delete(workshift, educator);
            workshifts = workshiftDAO.getIndividualWorkshift(educator);
            assertEquals(workshifts.size(), 0);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                workshiftDAO.deleteWorkshift(workshift);
                educatorDAO.deleteEducator(educator.getEmail());
            } catch (SQLException | ClassNotFoundException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void modify() {
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        Workshift workshift = new Workshift("2020-01-01", "08:00:00");
        Workshift workshift1 = new Workshift("2020-01-02", "09:00:00");
        EducatorDAO educatorDAO = new EducatorDAO();
        Educator educator = new Educator("gigi.gogi@gmail.com", "Gigi", "Gogi");
        try{
            workshiftDAO.addWorkshift(workshift);
            workshiftDAO.addWorkshift(workshift1);
            educatorDAO.addEducator(educator);
            workshiftDAO.insert(educator, workshift);
            ArrayList<Workshift> workshifts = workshiftDAO.getIndividualWorkshift(educator);
            assertEquals(workshifts.size(), 1);
            assertEquals(workshifts.get(0).getDate(), workshift.getDate());
            assertEquals(workshifts.get(0).getTime(), workshift.getTime());
            workshiftDAO.modify(educator, workshift1, workshift);
            workshifts = workshiftDAO.getIndividualWorkshift(educator);
            assertEquals(workshifts.size(), 1);
            assertEquals(workshifts.get(0).getDate(), workshift1.getDate());
            assertEquals(workshifts.get(0).getTime(), workshift1.getTime());
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                workshiftDAO.delete(workshift1, educator);
                workshiftDAO.deleteWorkshift(workshift);
                workshiftDAO.deleteWorkshift(workshift1);
                educatorDAO.deleteEducator(educator.getEmail());
            } catch (SQLException | ClassNotFoundException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void getDates() {
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        Workshift workshift = new Workshift("2020-01-01", "08:00:00");
        Workshift workshift1 = new Workshift("2020-01-02", "09:00:00");
        try{
            workshiftDAO.addWorkshift(workshift);
            workshiftDAO.addWorkshift(workshift1);
            ArrayList<String> dates = workshiftDAO.getDates();
            assertEquals(dates.size(), 2);
            assertEquals(dates.get(0), workshift.getDate());
            assertEquals(dates.get(1), workshift1.getDate());
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                workshiftDAO.deleteWorkshift(workshift);
                workshiftDAO.deleteWorkshift(workshift1);
            } catch (SQLException | ClassNotFoundException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void addWorkshift() {
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        Workshift workshift = new Workshift("2020-01-01", "08:00:00");
        Workshift workshift1 = new Workshift("2020-01-02", "09:00:00");
        try{
            workshiftDAO.addWorkshift(workshift);
            ArrayList<Workshift> workshifts = workshiftDAO.getWorkshifts();
            assertEquals(workshifts.size(), 1);
            assertEquals(workshifts.get(0).getDate(), workshift.getDate());
            assertEquals(workshifts.get(0).getTime(), workshift.getTime());
            workshiftDAO.addWorkshift(workshift1);
            workshifts = workshiftDAO.getWorkshifts();
            assertEquals(workshifts.size(), 2);
            assertEquals(workshifts.get(1).getDate(), workshift1.getDate());
            assertEquals(workshifts.get(1).getTime(), workshift1.getTime());
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                workshiftDAO.deleteWorkshift(workshift);
                workshiftDAO.deleteWorkshift(workshift1);
            } catch (SQLException | ClassNotFoundException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void deleteWorkshift() {
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();
        Workshift workshift = new Workshift("2020-01-01", "08:00:00");
        Workshift workshift1 = new Workshift("2020-01-02", "09:00:00");
        try{
            workshiftDAO.addWorkshift(workshift);
            workshiftDAO.addWorkshift(workshift1);
            ArrayList<Workshift> workshifts = workshiftDAO.getWorkshifts();
            assertEquals(workshifts.size(), 2);
            assertEquals(workshifts.get(0).getDate(), workshift.getDate());
            assertEquals(workshifts.get(0).getTime(), workshift.getTime());
            assertEquals(workshifts.get(1).getDate(), workshift1.getDate());
            assertEquals(workshifts.get(1).getTime(), workshift1.getTime());
            workshiftDAO.deleteWorkshift(workshift);
            workshifts = workshiftDAO.getWorkshifts();
            assertEquals(workshifts.size(), 1);
            assertEquals(workshifts.get(0).getDate(), workshift1.getDate());
            assertEquals(workshifts.get(0).getTime(), workshift1.getTime());
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                workshiftDAO.deleteWorkshift(workshift);
                workshiftDAO.deleteWorkshift(workshift1);
            } catch (SQLException | ClassNotFoundException | ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
