package Orm;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import DomainModel.Educator;
import DomainModel.Workshift;


import java.util.ArrayList;

public class WorkshiftDAO {

    public ArrayList<Workshift> getWorkshifts() throws SQLException {
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


    public  ArrayList <Workshift> getIndividualWorkshift (Educator educator) throws SQLException {
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


    void insert (Educator educator, Workshift workshift) throws SQLException, ParseException {
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

    void modify (Educator educator, Workshift newworkshift, Workshift oldworkshift) throws SQLException, ParseException {
        Connection con = ConnectionManager.getConnection();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String sql = "UPDATE workshifts_educator SET date = ?, time = ? WHERE email = ? AND date = ? AND time = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        java.util.Date utilDate = format.parse(newworkshift.getDate());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        LocalTime localTime = LocalTime.parse(newworkshift.getTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        java.util.Date oldutilDate= format.parse(oldworkshift.getDate());
        java.sql.Date oldsqlDate = new java.sql.Date(oldutilDate.getTime());
        LocalTime oldlocalTime = LocalTime.parse(oldworkshift.getTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        ps.setDate(1, sqlDate);
        ps.setTime(2, java.sql.Time.valueOf(localTime));
        ps.setString(3, educator.getEmail());
        ps.setDate(4, oldsqlDate);
        ps.setTime(5, java.sql.Time.valueOf(oldlocalTime));
        ps.executeUpdate();

        ps.close();
    }



}
