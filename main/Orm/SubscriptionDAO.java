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
}

