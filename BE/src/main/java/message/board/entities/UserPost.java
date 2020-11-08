package message.board.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserPost {
    private int postId;
    private String title;
    private String message;
    private String username;
    private LocalDateTime dateTime;
    private LocalDateTime lastModified;
    private List<String> hashtags;

    public UserPost(String title, String mes, String user){
        this.title = title;
        this.message = mes;
        this.username = user;
        this.dateTime = LocalDateTime.now();
        this.lastModified = this.dateTime;
        retrieveHastags(mes);
    }

    //This constructor was made to add mock data, may not be needed later on
    public UserPost(String message, String username, LocalDateTime dateTime) {
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

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
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
