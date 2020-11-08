package dao.implementation;

import dao.interfaces.UserPostDAO;
import database.DatabaseConnection;
import message.board.entities.UserPost;

import java.sql.*;

public class UserPostDaoImpl implements UserPostDAO{

    //replace the ?, still not sure what it is...
    @Override
    public boolean insertPost(UserPost post) {
        Connection connection = DatabaseConnection.getConnection();


        try {
            String query = "INSERT INTO post_details (title, message, username, date_created, last_modified, hashtags) VALUES (?, ?, ?, ?, ?, ?)";
            // Passing Statement.RETURN_GENERATED_KEYS to make getGeneratedKeys() work
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,post.getTitle());
            ps.setString(2,post.getMessage());
            ps.setString(3,post.getUsername());
            ps.setDate(4,java.sql.Date.valueOf(post.getDateTime().toString()));
            ps.setDate(5,java.sql.Date.valueOf(post.getLastModified().toString()));
            ps.setString(6,retrieveHashtagList(post));

            int i = ps.executeUpdate();

            if(i == 1) {

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        post.setPostId(generatedKeys.getInt(1));
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

    //replace the ?, still not sure what it is...
    @Override
    public boolean updatePost(UserPost post) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE post_details SET title=?, message=?, username=?, date_created=?, last_modified=?, hashtags=? WHERE user_id=?");

            ps.setString(1,post.getTitle());
            ps.setString(2, post.getMessage());
            ps.setString(3,post.getUsername());
            ps.setDate(4,java.sql.Date.valueOf(post.getDateTime().toString()));
            ps.setDate(5,java.sql.Date.valueOf(post.getLastModified().toString()));
            ps.setString(6,retrieveHashtagList(post));

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
    public boolean deletePost(int id) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate("DELETE FROM post_details WHERE post_id=" + id);

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

    public String retrieveHashtagList(UserPost post){
        String listOfHashtags = null;

        if(post.getHashtags().size() == 0)
            return null;

        for(int i = 0; i < post.getHashtags().size(); i++)
            listOfHashtags = listOfHashtags.concat(post.getHashtags().get(i));

        return listOfHashtags;
    }
}
