package main.Orm;
import java.sql.*;
import java.util.ArrayList;

import main.DomainModel.*;

public class ChildDAO {
    public Parent getParent(String idcode) throws SQLException, ClassNotFoundException {
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

    public ArrayList<Child> getAllChildren() throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT idcode, name, surname, age, details, weeknum, idstrategy, feepaid FROM children";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ArrayList<Child> children = new ArrayList<Child>();
        while (rs.next()) {
            String idcode = rs.getString("idcode");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            int age = rs.getInt("age");
            String details = rs.getString("details");
            int weeknum = rs.getInt("weeknum");
            boolean feepaid = rs.getBoolean("feepaid");
            FeeStrategy feestrategy;
            if(rs.getInt("idstrategy") == 1){
                feestrategy = new SiblingFee();
            }
            else{
                feestrategy = new OnlyChildFee();
            }
            Subscription subscription = new Subscription(weeknum, null, feestrategy, feepaid);
            Child child = new Child(idcode, name, surname, age, details);
            child.setSubscription(subscription);
            children.add(child);
        }
        return children;
    }

    public Child getChild(String idcode) throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT idcode, name, surname, age, details, weeknum, idstrategy, feepaid  FROM children WHERE idcode = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, idcode);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            int age = rs.getInt("age");
            String details = rs.getString("details");
            int weeknum = rs.getInt("weeknum");
            boolean feepaid = rs.getBoolean("feepaid");
            FeeStrategy feestrategy;
            if(rs.getInt("idstrategy") == 1){
                feestrategy = new SiblingFee();
            }
            else{
                feestrategy = new OnlyChildFee();
            }
            Subscription subscription = new Subscription(weeknum, null, feestrategy, feepaid);
            Child child = new Child(idcode, name, surname, age, details);
            child.setSubscription(subscription);
            return child;
        }
        return null;
    }

    public ArrayList<Child> getChildrenbyParent(String parentID) throws SQLException, ClassNotFoundException {
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

    public void insertChild(Child child, Subscription subscription, String parentid) throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();
        String sql = "INSERT INTO children (idcode, name, surname, age, details, parentid, weeknum, idstrategy, feepaid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, child.getIdcode());
        ps.setString(2, child.getName());
        ps.setString(3, child.getSurname());
        ps.setInt(4, child.getAge());
        ps.setString(5, child.getDetails());
        ps.setString(6, parentid);
        ps.setInt(7, subscription.getWeeksnum());
        if(subscription.getFeeStrategy() instanceof SiblingFee) {
            ps.setInt(8, 1);
        }
        else if(subscription.getFeeStrategy() instanceof OnlyChildFee) {
            ps.setInt(8, 2);
        }
        ps.setBoolean(9, false);
        ps.executeUpdate();
        ps.close();
    }

    public void delete(Child child) throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();
        String sql = "DELETE FROM children WHERE idcode = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, child.getIdcode());
        ps.executeUpdate();
        ps.close();
    }
}

