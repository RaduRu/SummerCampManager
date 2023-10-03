package main.Orm;

import main.DomainModel.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubscriptionDAO {
    public Subscription getChildInfo(String idcode) throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT weeknum, idstrategy, feepaid  FROM children WHERE idcode = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, idcode);
        ResultSet rs = ps.executeQuery();

        ChildDAO childdao = new ChildDAO();
        Child child = childdao.getChild(idcode);
        if (rs.next()) {
            int weeknum = rs.getInt("weeknum");
            boolean feepaid = rs.getBoolean("feepaid");
            FeeStrategy feestrategy;
            if(rs.getInt("idstrategy") == 1){
                feestrategy = new SiblingFee();
            }
            else{
                feestrategy = new OnlyChildFee();
            }
            Subscription subscription = new Subscription(weeknum, child, feestrategy, feepaid);
            return subscription;
        }
        return null;
    }

    public void editFeeStrategy(String idcode, FeeStrategy feeStrategy) throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();
        String sql = "UPDATE children SET idstrategy = ? WHERE idcode = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        if(feeStrategy instanceof SiblingFee) {
            ps.setInt(1, 1);
        }
        else if(feeStrategy instanceof OnlyChildFee) {
            ps.setInt(1, 2);
        }
        ps.setString(2, idcode);
        ps.executeUpdate();
        ps.close();
    }

    public void editFeePaid(String idcode, boolean feepaid) throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();
        String sql = "UPDATE children SET feepaid = ? WHERE idcode = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setBoolean(1, feepaid);
        ps.setString(2, idcode);
        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<Parent> getParentsNotPaid() throws SQLException, ClassNotFoundException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT DISTINCT parents.idcode, parents.email, parents.name, parents.surname, parents.cellphone FROM parents, children WHERE parents.idcode = children.parentid AND children.feepaid = false";
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

