package dao.implementation;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import dao.interfaces.FileAttachmentDAO;
import database.DatabaseConnection;
import message.board.entities.FileAttachment;
import message.board.entities.UserPost;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class FileAttachementDaoImpl implements FileAttachmentDAO {

    public static void main(String[] args) throws IOException {
      /*  String size = getFileSize("C:\\");
        String mediaType = getMediaType("C:\\");
        byte[] media = getByteArr("C:\\");

        FileAttachment post1 = new FileAttachment("OP",size,mediaType,media,2);
        FileAttachementDaoImpl dao = new FileAttachementDaoImpl();
        //dao.insertAttachment(post1);
        dao.deleteAttachment(1);*/
    }

    public static String getFileSize(File file){
        long bytes = file.length();
        return Long.toString(bytes/1024) + "kb";

    }

    public static String getMediaType(String fileDirectory) throws IOException {
       // Path path = Paths.get(fileDirectory);
        String mimeType = "JPG";//Files.probeContentType(path);
        return mimeType;
    }

    public static byte[] getByteArr(File file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
        BufferedImage img= ImageIO.read(file);
        ImageIO.write(img, "jpg", baos);
        baos.flush();

        String base64String= Base64.encode(baos.toByteArray());
        baos.close();

        return Base64.decode(base64String);
    }

    @Override
    public boolean insertAttachment(FileAttachment attachment) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            String query = "INSERT INTO t_attachment (file_name, file_size, media_type, media, post_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,attachment.getFilename());
            ps.setString(2,attachment.getFilesize());
            ps.setString(3,attachment.getMediaType());
            ps.setBytes(4, attachment.getMedia());
            ps.setInt(5, attachment.getPostId());

            int i = ps.executeUpdate();

            if(i == 1) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        attachment.setId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean updateAttachment(FileAttachment attachment) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE t_attachment SET file_name=?, file_size=?, media_type=?, media=? WHERE post_Id=?");

            ps.setString(1,attachment.getFilename());
            ps.setString(2,attachment.getFilesize());
            ps.setString(3,attachment.getMediaType());
            ps.setBytes(4, attachment.getMedia());
            ps.setInt(5, attachment.getPostId());

            int i = ps.executeUpdate();

            if(i == 1) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean deleteAttachment(int id) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate("DELETE FROM t_attachment WHERE post_id=" + id);

            if(i == 1) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }
}
