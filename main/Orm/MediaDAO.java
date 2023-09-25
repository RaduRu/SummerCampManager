package main.Orm;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import main.DomainModel.*;
//codice di upload:
//File file = new File("myimage.gif");
//FileInputStream fis = new FileInputStream(file);
//PreparedStatement ps = conn.prepareStatement("INSERT INTO images VALUES (?, ?)");
//ps.setString(1, file.getName());
//ps.setBinaryStream(2, fis, (int) file.length());
/*ps.executeUpdate();
ps.close();
fis.close();*/



    /* codice di selezione:
    n.prepaPreparedStatement ps = conreStatement("SELECT img FROM images WHERE imgname = ?");
ps.setString(1, "myimage.gif");
ResultSet rs = ps.executeQuery();
while (rs.next()) {
    byte[] imgBytes = rs.getBytes(1);
    // use the data in some way here
}
rs.close();
ps.close();
     */

public class MediaDAO {

    public ArrayList<Media> getAllMedia() throws SQLException {
        Connection con = ConnectionManager.getConnection();

        String sql = "SELECT * FROM media";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ArrayList<Media> media = new ArrayList<Media>();
        EducatorDAO educatorDAO = new EducatorDAO();
        while (rs.next()) {
            String date = rs.getDate("date").toString();
            String time = rs.getTime("time").toString();
            String filename = rs.getString("filename");
            byte[] file = rs.getBytes("mediabytes");
            Educator uploader = educatorDAO.getEducatorbyemail(rs.getString("ed_email"));
            if(rs.getInt("type") == 1){
                Photo photo = new Photo(file, filename, date, time, uploader);
                media.add(photo);}
            else{
                Video video = new Video(file, filename, date, time, uploader);
                media.add(video);
                }
        }
        return media;
    }

    public void uploadMedia(String filename, String ed_email, boolean isPhoto) throws IOException, SQLException, ParseException {
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        Connection con = ConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO media VALUES (?, ?, ?, ?, ?, ?)");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        ps.setBinaryStream(1, fis, (int) file.length());
        ps.setString(2, file.getName());
        java.util.Date utilDate = format.parse(LocalDate.now().toString());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        ps.setDate(3, sqlDate);
        LocalTime localTime = LocalTime.parse(LocalTime.now().toString(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        ps.setTime(4, java.sql.Time.valueOf(localTime));
        ps.setString(5, ed_email);
        if(isPhoto)
            ps.setInt(6, 1);
        else
            ps.setInt(6, 2);
        ps.executeUpdate();
        ps.close();
        fis.close();
    }

    public void deleteMedia(String filename) throws SQLException{
        Connection con = ConnectionManager.getConnection();
        String sql = "DELETE FROM media WHERE filename = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, filename);
        ps.executeUpdate();
        ps.close();
    }
}

