package Orm;
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


    /* codice di visualizzazione:
    File outputFile = tempFolder.newFile("outputFile.jpg");
try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
    outputStream.write(dataForWriting);
}
     */


public class MediaDAO {
}
