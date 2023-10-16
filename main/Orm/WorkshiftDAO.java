package main.Orm;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import main.DomainModel.Educator;
import main.DomainModel.Workshift;

public class WorkshiftDAO {

    public ArrayList<Workshift> getWorkshifts() throws SQLException, ClassNotFoundException {
         Connection con = ConnectionManager.getConnection();

         String sql = "SELECT * FROM workshifts";
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery();

         ArrayList <Workshift> workshifts = new ArrayList<Workshift>();

         while (rs.next()) {
             String date = rs.getDate("date").toString();
             String time = rs.getTime("time").toString();
             Workshift workshift = new Workshift(date, time);
             workshifts.add(workshift);
         }

         ps.close();
         return workshifts;
    }


    public  ArrayList <Workshift> getIndividualWorkshift (Educator educator) throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();

        String sql = "SELECT * FROM workshifts_educator WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, educator.getEmail());
        ResultSet rs = ps.executeQuery();

        ArrayList <Workshift> workshifts = new ArrayList<Workshift>();

        while (rs.next()) {
            String date = rs.getDate("date").toString();
            String time = rs.getTime("time").toString();
            Workshift workshift = new Workshift(date, time);
            workshifts.add(workshift);
        }

        ps.close();
        return workshifts;
    }

    public ArrayList<AbstractMap.SimpleEntry<Workshift, Educator>> getAllIndividualWorkshift() throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();

        String sql = "SELECT * FROM workshifts_educator";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ArrayList<AbstractMap.SimpleEntry<Workshift, Educator>> workshifts = new ArrayList<>();

        while (rs.next()) {
            String date = rs.getDate("date").toString();
            String time = rs.getTime("time").toString();
            String ed_email = rs.getString("email");
            Workshift workshift = new Workshift(date, time);
            Educator educator = new EducatorDAO().getEducatorbyemail(ed_email);
            AbstractMap.SimpleEntry<Workshift, Educator> entry = new AbstractMap.SimpleEntry<Workshift, Educator>(workshift, educator);
            workshifts.add(entry);
        }
        ps.close();
        return workshifts;
    }


    public void insert (Educator educator, Workshift workshift) throws SQLException, ParseException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String sql = "INSERT INTO workshifts_educator (email, date, time) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        java.util.Date utilDate = format.parse(workshift.getDate());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        LocalTime localTime = LocalTime.parse(workshift.getTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        ps.setString(1, educator.getEmail());
        ps.setDate(2, sqlDate);
        ps.setTime(3, java.sql.Time.valueOf(localTime));
        ps.executeUpdate();

        ps.close();
    }

    public void delete(Workshift workshift, Educator educator) throws SQLException, ClassNotFoundException, ParseException {
        Connection con = ConnectionManager.getConnection();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String sql = "DELETE FROM workshifts_educator WHERE email = ? AND date = ? AND time = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        java.util.Date utilDate = format.parse(workshift.getDate());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        LocalTime localTime = LocalTime.parse(workshift.getTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        ps.setString(1, educator.getEmail());
        ps.setDate(2, sqlDate);
        ps.setTime(3, java.sql.Time.valueOf(localTime));
        ps.executeUpdate();

        ps.close();
    }

    public void modify (Educator educator, Workshift newworkshift, Workshift oldworkshift) throws SQLException, ParseException, ClassNotFoundException {
        delete(oldworkshift, educator);
        insert(educator, newworkshift);
    }

    public ArrayList<String> getDates() throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT DISTINCT date FROM workshifts";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ArrayList <String> dates = new ArrayList<String>();

        while (rs.next()) {
            String date = rs.getDate("date").toString();
            dates.add(date);
        }

        ps.close();
        return dates;
    }

    public void addWorkshift(Workshift workshift) throws SQLException, ClassNotFoundException, ParseException {
        Connection con = ConnectionManager.getConnection();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String sql = "INSERT INTO workshifts (date, time) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        java.util.Date utilDate = format.parse(workshift.getDate());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        LocalTime localTime = LocalTime.parse(workshift.getTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        ps.setDate(1, sqlDate);
        ps.setTime(2, java.sql.Time.valueOf(localTime));
        ps.executeUpdate();
        ps.close();
    }

    public void deleteWorkshift(Workshift workshift) throws SQLException, ClassNotFoundException, ParseException {
        Connection con = ConnectionManager.getConnection();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "DELETE FROM workshifts WHERE date = ? AND time = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        java.util.Date utilDate = format.parse(workshift.getDate());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        LocalTime localTime = LocalTime.parse(workshift.getTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        ps.setDate(1, sqlDate);
        ps.setTime(2, java.sql.Time.valueOf(localTime));
        ps.executeUpdate();
        ps.close();
    }
}
