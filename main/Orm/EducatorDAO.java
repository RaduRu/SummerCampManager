package main.Orm;
import java.sql.*;
import java.util.ArrayList;

import main.DomainModel.Educator;

import java.sql.Connection;

public class EducatorDAO {

    public ArrayList<Educator> getAllEducators() throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();

        String sql = "SELECT * FROM educators";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ArrayList <Educator> educators = new ArrayList<Educator>();

        while(rs.next()) {
              String email = rs.getString("email");
              String name = rs.getString("name");
              String surname = rs.getString("surname");
              Educator educator = new Educator(email, name, surname);
              educators.add(educator);
        }

        ps.close();
        return educators;
    }

    public ArrayList <Educator> getEducatorsShifts() throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();

        String sql = "SELECT * FROM educators ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ArrayList <Educator> educators = new ArrayList<Educator>();
        WorkshiftDAO workshiftDAO = new WorkshiftDAO();

        while (rs.next()) {
            String email = rs.getString("email");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            Educator educator = new Educator(email, name, surname);
            educator.setWorkshifts(workshiftDAO.getIndividualWorkshift(educator));
            educators.add(educator);
        }

        ps.close();
        return educators;
    }

    public Educator getEducatorbyemail(String email) throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();

        String sql = "SELECT * FROM educators WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        Educator educator = null;

        while(rs.next()) {
              String name = rs.getString("name");
              String surname = rs.getString("surname");
              educator = new Educator(email, name, surname);
        }

        ps.close();
        return educator;
    }

    public void addEducator(Educator educator) throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();
        String sql = "INSERT INTO educators (email, name, surname) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, educator.getEmail());
        ps.setString(2, educator.getName());
        ps.setString(3, educator.getSurname());
        ps.executeUpdate();
    }

    public void deleteEducator(String email) throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();
        String sql = "DELETE FROM educators WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ps.executeUpdate();
    }

}
