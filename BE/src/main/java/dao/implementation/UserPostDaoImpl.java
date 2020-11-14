package dao.implementation;

import dao.interfaces.UserPostDAO;
import database.DatabaseConnection;
import message.board.entities.UserPost;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserPostDaoImpl implements UserPostDAO{

    public static void main(String[] args){
        UserPost post1 = new UserPost("First Post","Ayy sent my first post using my #WAP #ganggang", "ren");
        UserPostDaoImpl dao = new UserPostDaoImpl();
        dao.insertPost(post1);
        //dao.deletePost(1);
        /*
        try {
            List<UserPost> up = dao.getPosts("test",null, null, null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
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
            ps.setDate(4,post.getDateTime());
            ps.setDate(5,post.getLastModified());
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
            PreparedStatement ps = connection.prepareStatement("UPDATE t_post SET title=?, message=?, username=?, last_modified=?, hashtags=? WHERE id=?");

            ps.setString(1,post.getTitle());
            ps.setString(2, post.getMessage());
            ps.setString(3,post.getUsername());
            ps.setDate(4,java.sql.Date.valueOf(post.getLastModified().toString().substring(0,10)));
            ps.setString(5,retrieveHashtagList(post));
            ps.setInt(6,post.getPostId());

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

        if(post.getHashtags() == null || post.getHashtags().size() == 0)
            return null;

        for(int i = 0; i < post.getHashtags().size(); i++)
            listOfHashtags = listOfHashtags.concat(post.getHashtags().get(i));

        return listOfHashtags;
    }

    @Override
    public List<UserPost> getPosts(String username, String hashtag, Date startDate, Date endDate) throws SQLException
    {

        Connection con = DatabaseConnection.getConnection();

        String query = "SELECT * FROM t_post";
        Boolean dateChecked = false;
        Boolean hashtagChecked = false;

        if(username != null) {
            query = query + " WHERE username = '" + username + "'";
        }
        else if(startDate != null && endDate != null) {
            query = query + " WHERE date_time BETWEEN '" + startDate + "' AND '" + endDate + "'";
            dateChecked = true;
        }
        else if(hashtag != null) {
            query = query + " WHERE hashtags LIKE '%" + hashtag + "%'";
            hashtagChecked = true;
        }

        if(!dateChecked && startDate != null && endDate != null)
            query = query + " AND date_time BETWEEN '" + startDate + "' AND '" + endDate + "'";

        if(!hashtagChecked && hashtag != null)
            query =  query + " AND hashtags = '" + hashtag + "'";
        query = query + " ORDER BY last_modified DESC;";

        try {
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            ArrayList<UserPost> posts = new ArrayList<UserPost>();

            while(rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Date dateTime = rs.getDate("date_time");
                Date lastModified = rs.getDate("last_modified");
                String message = rs.getString("message");
                String name = rs.getString("username");

                UserPost userPost = new UserPost(id, title, message, name, dateTime, lastModified);

                posts.add(userPost);
            }

            return posts;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(con != null)
                con.close();
        }

        return null;

    }
}
