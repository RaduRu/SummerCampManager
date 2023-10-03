package main.Orm;
import java.sql.*;
import java.util.ArrayList;

import main.DomainModel.*;

public class ChildDAO {
    public Parent getParent(String idcode) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT parentid FROM children WHERE idcode = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, idcode);
        ResultSet rs = ps.executeQuery();

        ParentDAO parentdao = new ParentDAO();
        if (rs.next()) {
            Parent parent = parentdao.getParent(rs.getString("parentid"));
            return parent;
        }
    return null;
    }

    public ArrayList<Child> getAllChildren() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT idcode, name, surname, age, details  FROM children";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ArrayList<Child> children = new ArrayList<Child>();
        while (rs.next()) {
            String idcode = rs.getString("idcode");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            int age = rs.getInt("age");
            String details = rs.getString("details");
            Child child = new Child(idcode, name, surname, age, details);
            children.add(child);
        }
        return children;
    }

    public Child getChild(String idcode) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT idcode, name, surname, age, details  FROM children WHERE idcode = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, idcode);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            int age = rs.getInt("age");
            String details = rs.getString("details");
            Child child = new Child(idcode, name, surname, age, details);
            return child;
        }
        return null;
    }

    public ArrayList<Child> getChildrenbyParent(String parentID) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT idcode, name, surname, age, details FROM children WHERE parentid = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, parentID);
        ResultSet rs = ps.executeQuery();

        ArrayList<Child> children = new ArrayList<Child>();
        SubscriptionDAO subscriptiondao = new SubscriptionDAO();
        while (rs.next()) {
            String idcode = rs.getString("idcode");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            int age = rs.getInt("age");
            String details = rs.getString("details");
            Child child = new Child(idcode, name, surname, age, details);
            Subscription subscription = subscriptiondao.getChildInfo(idcode);
            child.setSubscription(subscription);
            children.add(child);
        }
        return children;
    }

    public void insert(Child child, Subscription subscription, String parentid) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "INSERT INTO children (name, surname, age, details, weeknum, idstrategy, idcode, parentid, feepaid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(7, child.getIdcode());
        ps.setString(1, child.getName());
        ps.setString(2, child.getSurname());
        ps.setInt(3, child.getAge());
        ps.setString(4, child.getDetails());
        ps.setString(8, parentid);
        ps.setInt(5, subscription.getWeeksnum());
        if(subscription.getFeeStrategy() instanceof SiblingFee) {
            ps.setInt(6, 1);
        }
        else if(subscription.getFeeStrategy() instanceof OnlyChildFee) {
            ps.setInt(6, 2);
        }
        ps.setBoolean(9, false);
        ps.executeUpdate();
        ps.close();
    }

    public void delete(Child child) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "DELETE FROM children WHERE idcode = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, child.getIdcode());
        ps.executeUpdate();
        ps.close();
    }
}

