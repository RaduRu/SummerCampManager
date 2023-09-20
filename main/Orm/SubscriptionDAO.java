package Orm;

import DomainModel.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionDAO {
    public Subscription getChildInfo(String idcode) throws SQLException {
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

    public void editSubscription(Subscription subscription) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "UPDATE children SET weeknum = ?, idstrategy = ?, feepaid = ? WHERE idcode = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, subscription.getWeeksnum());
        if(subscription.getFeeStrategy() instanceof SiblingFee) {
            ps.setInt(2, 1);
        }
        else if(subscription.getFeeStrategy() instanceof OnlyChildFee) {
            ps.setInt(2, 2);
        }
        ps.setBoolean(3, subscription.isPaid());
        ps.setString(4, subscription.getChild().getIdcode());
        ps.executeUpdate();
        ps.close();
    }
}

