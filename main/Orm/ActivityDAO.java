package Orm;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import DomainModel.Activity;
import DomainModel.TypeOfActivity;

public class ActivityDAO {

    public int getId(TypeOfActivity type) throws SQLException {
        Connection con = ConnectionManager.getConnection();

        String sql = "SELECT * FROM typeofactivity WHERE name = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, type.toString());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            return id;
        }
        return -1; // o con eccezione?
    }

    public TypeOfActivity getTypeOfActivity(int id) throws SQLException {
        Connection con = ConnectionManager.getConnection();

        String sql = "SELECT * FROM typeofactivity WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String name = rs.getString("name");
            TypeOfActivity type = TypeOfActivity.valueOf(name);
            return type;
        }
        return null; // o con eccezione?
    }

    public ArrayList<Activity> getAll() throws SQLException {
        Connection con = ConnectionManager.getConnection();

        String sql = "SELECT * FROM activities";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ArrayList<Activity> activities = new ArrayList<Activity>();
        while (rs.next()) {
            String date = rs.getDate("date").toString();
            String time = rs.getTime("time").toString();
            String description = rs.getString("description");
            int id = rs.getInt("id_activities");
            TypeOfActivity type = getTypeOfActivity(rs.getInt("typeofactivity"));
            Activity activity = new Activity(date, time, description, id, type);
            activities.add(activity);
        }
        ps.close();
        return activities;
    }

    public void insert (Activity activity) throws SQLException, ParseException {
        Connection con = ConnectionManager.getConnection();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String sql = "INSERT INTO activities (date, time, description, typeofactivity) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        java.util.Date utilDate = format.parse(activity.getDate());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        LocalTime localTime = LocalTime.parse(activity.getTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        ps.setDate(1, sqlDate);
        ps.setTime(2, java.sql.Time.valueOf(localTime));
        ps.setString(3, activity.getDescription());
        ps.setInt(4, getId(activity.getType()));
        ps.executeUpdate();
        ps.close();

    }

    public void delete (Activity activity) throws SQLException {
        Connection con = ConnectionManager.getConnection();

        String sql = "DELETE FROM activities WHERE id_activities = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, activity.getId());
        ps.executeUpdate();
        ps.close();
    }

    public void modify (Activity activity) throws SQLException, ParseException {
        Connection con = ConnectionManager.getConnection();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String sql = "UPDATE activities SET date = ?, time = ?, description = ?, typeofactivity = ? WHERE id_activities = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        java.util.Date utilDate = format.parse(activity.getDate());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        LocalTime localTime = LocalTime.parse(activity.getTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        ps.setDate(1, sqlDate);
        ps.setTime(2, java.sql.Time.valueOf(localTime));
        ps.setString(3, activity.getDescription());
        ps.setInt(4, getId(activity.getType()));
        ps.setInt(5, activity.getId());
        ps.executeUpdate();
        ps.close();
    }
}
