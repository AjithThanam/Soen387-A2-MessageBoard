package userPostDaoImpl;

import dao.implementation.UserPostDaoImpl;
import message.board.entities.UserPost;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserPostDaoImplTest {

    static UserPostDaoImpl userPostDao;
    static List<Integer> mockPostIDs;

    @BeforeClass
    public static void setUp() {
        userPostDao = new UserPostDaoImpl();
        mockPostIDs = new ArrayList<Integer>();

        UserPost userPost = new UserPost("Stolen christmas", "#Merci M. Legualt", "user1@gmail.com", "private");
        userPost.setDateTime(convert("2000-12-25"));
        userPostDao.insertPost(userPost);
        mockPostIDs.add(userPost.getPostId());

        UserPost userPost2 = new UserPost("NEW YEAR!!!!", "Merci M. Legualt", "user1@gmail.com", "private");
        userPost2.setDateTime(convert("2001-01-01"));
        userPostDao.insertPost(userPost2);
        mockPostIDs.add(userPost2.getPostId());

        UserPost userPost3 = new UserPost("Testing hashtags", "This is a #hashtag post", "user2@gmail.com", "group1");
        userPost3.setDateTime(convert("2002-01-01"));
        userPostDao.insertPost(userPost3);
        mockPostIDs.add(userPost3.getPostId());

        UserPost userPost4 = new UserPost("Testing hashtags 2", "This is another #hashtag post", "user2@gmail.com", "conU");
        userPost4.setDateTime(convert("2004-01-01"));
        userPostDao.insertPost(userPost4);
        mockPostIDs.add(userPost4.getPostId());

        UserPost userPost5 = new UserPost("Hello", "Welcome to the #concordia group.", "user2@gmail.com", "conU");
        userPost5.setDateTime(convert("2005-01-01"));
        userPostDao.insertPost(userPost5);
        mockPostIDs.add(userPost5.getPostId());
    }

    @Test
    public void testGetPostsByGroup() {
        List<String> groups = new ArrayList<String>();
        groups.add("group1");
        try {
            Assert.assertEquals(1, userPostDao.getPosts(null, null, null, groups).size());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testGetPostsByHashtag() {
        List<String> groups = new ArrayList<String>();
        groups.add("conU");
        groups.add("group1");
        try {
            Assert.assertEquals(2, userPostDao.getPosts("#hashtag", null, null, groups).size());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testGetPostsWithinDateRange() {
        List<String> groups = new ArrayList<String>();
        groups.add("conU");
        groups.add("group1");
        groups.add("private");
        try {
            Assert.assertEquals(4, userPostDao.getPosts(null, convert("2000-01-01"), convert("2004-02-02"), groups).size());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @AfterClass
    public static void cleanUp(){

        for (int i = 0; i < mockPostIDs.size(); i++)
            userPostDao.deletePost(mockPostIDs.get(i));
    }

    public static Date convert(String date){
        return Date.valueOf(date);
    }
}
