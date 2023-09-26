package main.Orm;
import java.sql.*;
import java.util.ArrayList;

import main.DomainModel.Parent;

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

    public void addParent(Parent parent) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "INSERT INTO parents (idcode, email, name, surname, cellphone) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, parent.getIdcode());
        ps.setString(2, parent.getEmail());
        ps.setString(3, parent.getName());
        ps.setString(4, parent.getSurname());
        ps.setString(5, parent.getCellphone());
        ps.executeUpdate();
        ps.close();
    }

    public void deleteParent(String idcode) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "DELETE FROM parents WHERE idcode = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, idcode);
        ps.executeUpdate();
    }

}
