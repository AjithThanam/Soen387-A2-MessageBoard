package message.board.entities;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MessageBoard {
 /*   private static int id;
    private List<UserPost> messages;

    public MessageBoard(){
        this.id = this.id + 1;
        this.messages = new ArrayList<>();
        loadmock();
    }

    public void loadmock(){

        Month month = Month.of(1);
        Month month2 = Month.of(2);
        Month month3 = Month.of(3);
        Month month4 = Month.of(4);
        Month month5 = Month.of(5);
        Month month6 = Month.of(6);
        Month month7 = Month.of(7);
        Month month8 = Month.of(8);
        Month month9 = Month.of(9);
        Month month10 = Month.of(10);

        LocalDateTime date = LocalDateTime.of(2020, month, 2, 3,12, 45, 45);
        LocalDateTime date2 = LocalDateTime.of(2020, month, 2, 3,12, 45, 45);
        LocalDateTime date3 = LocalDateTime.of(2020, month, 2, 3,12, 45, 45);
        LocalDateTime date4 = LocalDateTime.of(2020, month, 2, 3,12, 45, 45);
        LocalDateTime date5 = LocalDateTime.of(2020, month, 2, 6,12, 45, 45);
        LocalDateTime date6 = LocalDateTime.of(2020, month6, 2, 2,12, 45, 45);
        LocalDateTime date7 = LocalDateTime.of(2020, month6, 2, 3,12, 45, 45);
        LocalDateTime date8 = LocalDateTime.of(2020, month6, 2, 3,12, 45, 45);
        LocalDateTime date9 = LocalDateTime.of(2020, month6, 2, 6,12, 45, 45);
        LocalDateTime date10 = LocalDateTime.of(2020, month6, 2, 7,12, 45, 45);

        UserPost msg1 = new UserPost("Hi Guys are we working on this tonight.", "AJ", date);
        UserPost msg2 = new UserPost("Ya I have time this afternoon.", "Ren", date2);
        UserPost msg3 = new UserPost("Same here.", "Dim", date3);
        UserPost msg4 = new UserPost("I won't have time. But I'll work on it later.", "Mun", date4);
        UserPost msg5 = new UserPost("Okay, great just send me a message.", "AJ", date5);
        UserPost msg6 = new UserPost("Guys finals are coming up", "Ren", date6);
        UserPost msg7 = new UserPost("Ya gotta study for SOEN 423.", "Dim", date7);
        UserPost msg8 = new UserPost("Same, but need to complete my assignments first.", "Mun", date8);
        UserPost msg9 = new UserPost("I think I'm done, just need to review one section.", "AJ", date9);
        UserPost msg10 = new UserPost("Ayt, sounds good", "Mun", date10);

        messages.add(msg1);
        messages.add(msg2);
        messages.add(msg3);
        messages.add(msg4);
        messages.add(msg5);
        messages.add(msg6);
        messages.add(msg7);
        messages.add(msg8);
        messages.add(msg9);
        messages.add(msg10);

    }

    public void addMessage(UserPost message){
        this.messages.add(message);
    }

    public List<UserPost> getMessages(LocalDateTime start, LocalDateTime end){

        if(start == null || end == null)
            return messages;
        else if(start.isAfter(end))
            return messages;

        List<UserPost> tempList = new ArrayList<>();
        if (start.isBefore(end) && !start.equals(end)) {
            for (UserPost msg : this.messages) {
                if (isWithinRange(msg, start, end))
                    tempList.add(msg);
            }
        }
        return tempList;
    }

    public void clearAllMessages(){
        this.messages = new ArrayList<>();
    }

    public void clearMessages(LocalDateTime start, LocalDateTime end) throws Exception {
        List<UserPost> tempList = new ArrayList<>();
        if (start.isBefore(end) && !start.equals(end)) {
            for (UserPost msg : this.messages) {
                if (!isWithinRange(msg, start, end))
                    tempList.add(msg);
            }

            messages = tempList;
        }
        else
            throw new Exception("Nothing to delete within given dates.");
    }

    public boolean isWithinRange(UserPost msg, LocalDateTime start, LocalDateTime end){
        if(msg.getDateTime().isAfter(start) && msg.getDateTime().isBefore(end))
            return true;
        else
            return false;
    }*/
}
