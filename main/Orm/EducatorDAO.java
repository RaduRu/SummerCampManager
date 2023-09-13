package Orm;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import DomainModel.Educator;

import java.sql.Connection;
import java.util.ArrayList;

public class EducatorDAO {

    public ArrayList<Educator> getAllEducators() throws SQLException {
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

    public ArrayList <Educator> getEducatorsShifts() throws SQLException {
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

}
