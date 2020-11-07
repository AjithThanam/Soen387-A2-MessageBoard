package message.board.entities;

import java.time.LocalDateTime;

public class UserPost {
    private String message;
    private String username;
    private LocalDateTime datetime;
    private static int id;


    public UserPost(String mes, String user){
        this.id = this.id + 1;
        this.message = mes;
        this.username = user;
        this.datetime = LocalDateTime.now().withSecond(0).withNano(0);
    }

    public UserPost(String message, String username, LocalDateTime datetime) {
        this.message = message;
        this.username = username;
        this.datetime = datetime;
    }

    public int getId(){
        return id;
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

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "message='" + message + '\'' +
                ", username='" + username + '\'' +
                ", datetime=" + datetime +
                '}';
    }
}
