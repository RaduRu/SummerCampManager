package Orm;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import DomainModel.Parent;

import DomainModel.Activity;
import DomainModel.TypeOfActivity;

public class ParentDAO {

    public Parent getParent(String idcode) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT * FROM parents WHERE idcode = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, idcode);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String email = rs.getString("email");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String cellphone = rs.getString("cellphone");
            Parent parent = new Parent(idcode, email, name, surname, cellphone);
            return parent;
        }
        return null;
    }

    public ArrayList<Parent> getAllParents() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT * FROM parents";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Parent> parents = new ArrayList<Parent>();
        while (rs.next()) {
            String idcode = rs.getString("idcode");
            String email = rs.getString("email");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String cellphone = rs.getString("cellphone");
            Parent parent = new Parent(idcode, email, name, surname, cellphone);
            parents.add(parent);
        }
        return parents;
    }

}
