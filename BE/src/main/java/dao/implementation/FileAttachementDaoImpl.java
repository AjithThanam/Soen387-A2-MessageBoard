package dao.implementation;

import dao.interfaces.FileAttachmentDAO;
import database.DatabaseConnection;
import message.board.entities.FileAttachment;

import java.sql.*;

public class FileAttachementDaoImpl implements FileAttachmentDAO {
    @Override
    public boolean insertAttachment(FileAttachment attachment) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            String query = "INSERT INTO attachment_details (file_name, file_size, media_type, media, post_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,attachment.getFilename());
            ps.setString(2,attachment.getFilesize());
            ps.setString(3,attachment.getMediaType());
            ps.setBytes(4, attachment.getMedia());
            //im assuming this isn't how you set up a foreign key?
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
            PreparedStatement ps = connection.prepareStatement("UPDATE attachment_details SET file_name=?, file_size=?, media_type=?, media=?, last_modified=?, hashtags=? WHERE user_id=?");

            ps.setString(1,attachment.getFilename());
            ps.setString(2,attachment.getFilesize());
            ps.setString(3,attachment.getMediaType());
            ps.setBytes(4, attachment.getMedia());
            //im assuming this isn't how you set up a foreign key?
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
        return false;
    }
}
