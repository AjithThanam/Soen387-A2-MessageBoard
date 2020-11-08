package dao.implementation;

import dao.interfaces.UserPostDAO;
import database.DatabaseConnection;
import message.board.entities.UserPost;

import java.sql.*;

public class UserPostDaoImpl implements UserPostDAO{

    public static void main(String[] args){
        UserPost post1 = new UserPost("First Post","Ayy sent my first post using my #WAP #ganggang", "ren");
        UserPostDaoImpl dao = new UserPostDaoImpl();
        //dao.insertPost(post1);
        dao.deletePost(1);
    }
    
    @Override
    public boolean insertPost(UserPost post) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            String query = "INSERT INTO t_post (title, message, username, date_time, last_modified, hashtags) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,post.getTitle());
            ps.setString(2,post.getMessage());
            ps.setString(3,post.getUsername());
            ps.setDate(4,java.sql.Date.valueOf("2020-10-10"));
            ps.setDate(5,java.sql.Date.valueOf("2020-10-10"));
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

    @Override
    public boolean updatePost(UserPost post) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE t_post SET title=?, message=?, username=?, date_time=?, last_modified=?, hashtags=? WHERE user_id=?");

            ps.setString(1,post.getTitle());
            ps.setString(2, post.getMessage());
            ps.setString(3,post.getUsername());
            ps.setDate(4,java.sql.Date.valueOf(post.getDateTime().toString()));
            ps.setDate(5,java.sql.Date.valueOf(post.getLastModified().toString()));
            ps.setString(6,retrieveHashtagList(post));
            ps.setInt(7,post.getPostId());

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
            int i = stmt.executeUpdate("DELETE FROM t_post WHERE id=" + id);

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

    public static String retrieveHashtagList(UserPost post){
        String listOfHashtags = "";

        if(post.getHashtags().size() == 0)
            return null;

        for(int i = 0; i < post.getHashtags().size(); i++)
            listOfHashtags = listOfHashtags.concat(post.getHashtags().get(i));

        return listOfHashtags;
    }
}
