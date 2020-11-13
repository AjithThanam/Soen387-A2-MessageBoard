package message.board.entities;

import java.util.ArrayList;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class UserPost {
    private int postId;
    private String title;
    private String message;
    private String username;
    private Date dateTime;
    private Date lastModified;
    private List<String> hashtags;

    public UserPost(String title, String mes, String user){
        this.title = title;
        this.message = mes;
        this.username = user;
        this.dateTime = new Date(Calendar.getInstance().getTime().getTime());
        System.out.print(this.dateTime);
        this.lastModified = this.dateTime;
        retrieveHastags(mes);
    }

    public UserPost(int postId, String title, String message, String username, Date dateTime, Date lastModified) {
        this.postId = postId;
        this.title = title;
        this.message = message;
        this.username = username;
        this.dateTime = dateTime;
        this.lastModified = lastModified;
        retrieveHastags(message);
    }

    //This constructor was made to add mock data, may not be needed later on
    public UserPost(String message, String username, Date dateTime) {
        this.message = message;
        this.username = username;
        this.dateTime = dateTime;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    private void retrieveHastags(String message){
        if(message.contains("#")){
            String [] postWords = message.split(" ");
            List<String> templist = new ArrayList<>();
            for(int i = 0; i < postWords.length; i++){
                if(postWords[i].contains("#"))
                    templist.add(postWords[i]);
            }
            hashtags = templist;
        }
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "message='" + message + '\'' +
                ", username='" + username + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
