package dao.implementation;

import dao.interfaces.UserPostDAO;
import database.DatabaseConnection;
import message.board.entities.FileAttachment;
import message.board.entities.UserPost;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserPostDaoImpl implements UserPostDAO{

    @Override
    public boolean insertPost(UserPost post) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            String query = "INSERT INTO t_post (title, message, username, date_time, last_modified, hashtags, sys_group) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,post.getTitle());
            ps.setString(2,post.getMessage());
            ps.setString(3,post.getUsername());
            ps.setDate(4,post.getDateTime());
            ps.setDate(5,post.getLastModified());
            ps.setString(6,retrieveHashtagList(post));
            ps.setString(7,post.getGroup());

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
            PreparedStatement ps = connection.prepareStatement("UPDATE t_post SET title=?, message=?, username=?, last_modified=?, hashtags=?, sys_group=? WHERE id=?");

            ps.setString(1,post.getTitle());
            ps.setString(2, post.getMessage());
            ps.setString(3,post.getUsername());
            ps.setDate(4,java.sql.Date.valueOf(post.getLastModified().toString().substring(0,10)));
            ps.setString(5,retrieveHashtagList(post));
            ps.setString(6,post.getGroup());
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

        if(post.getHashtags() == null || post.getHashtags().size() == 0)
            return null;

        for(int i = 0; i < post.getHashtags().size(); i++)
            listOfHashtags = listOfHashtags.concat(post.getHashtags().get(i));

        return listOfHashtags;
    }

    @Override
    public List<UserPost> getPosts(String hashtag, Date startDate, Date endDate, List<String> groups) throws SQLException
    {

        Connection con = DatabaseConnection.getConnection();

        String query = "SELECT * FROM t_post";
        Boolean dateChecked = false;
        Boolean hashtagChecked = false;

        if(startDate != null && endDate != null) {
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

        if(!hashtagChecked && !dateChecked)
            query = query + " WHERE sys_group IN ('" + groups.get(0) + "'";
        else
            query = query + " AND sys_group IN ('" + groups.get(0) + "'";

        for (int i = 1; i < groups.size(); i++) {
            query = query + ",'" + groups.get(i) + "'";
        }
        query = query + ") ORDER BY last_modified DESC, id DESC;";

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
                String group = rs.getString("sys_group");

                UserPost userPost = new UserPost(id, title, message, name, dateTime, lastModified, group);

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

    @Override
    public UserPost getPost(int postID) {

        Connection connection = DatabaseConnection.getConnection();

        String query = "SELECT * FROM t_post WHERE id = ? ";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, postID);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Date dateTime = rs.getDate("date_time");
                Date lastModified = rs.getDate("last_modified");
                String message = rs.getString("message");
                String name = rs.getString("username");
                String group = rs.getString("sys_group");

                UserPost userPost = new UserPost(id, title, message, name, dateTime, lastModified, group);

                return userPost;
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
        return null;

    }

}
