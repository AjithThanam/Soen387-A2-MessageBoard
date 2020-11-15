package dao.interfaces;

import message.board.entities.UserPost;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface UserPostDAO {

    boolean insertPost(UserPost post);
    boolean updatePost(UserPost post);
    boolean deletePost(int id);
    List<UserPost> getPosts(String username, String hashtag, Date startDate, Date endDate) throws SQLException;
}
