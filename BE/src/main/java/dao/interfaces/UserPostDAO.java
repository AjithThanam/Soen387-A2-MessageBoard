package dao.interfaces;

import message.board.entities.UserPost;

public interface UserPostDAO {

    boolean insertPost(UserPost post);
    boolean updatePost(UserPost post);
    boolean deletePost(int id);
}
