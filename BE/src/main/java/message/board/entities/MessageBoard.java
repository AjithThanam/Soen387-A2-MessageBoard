package message.board.entities;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MessageBoard {
    private static int id;
    private List<ChatMessage> messages;

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

        ChatMessage msg1 = new ChatMessage("Hi Guys are we working on this tonight.", "AJ", date);
        ChatMessage msg2 = new ChatMessage("Ya I have time this afternoon.", "Ren", date2);
        ChatMessage msg3 = new ChatMessage("Same here.", "Dim", date3);
        ChatMessage msg4 = new ChatMessage("I won't have time. But I'll work on it later.", "Mun", date4);
        ChatMessage msg5 = new ChatMessage("Okay, great just send me a message.", "AJ", date5);
        ChatMessage msg6 = new ChatMessage("Guys finals are coming up", "Ren", date6);
        ChatMessage msg7 = new ChatMessage("Ya gotta study for SOEN 423.", "Dim", date7);
        ChatMessage msg8 = new ChatMessage("Same, but need to complete my assignments first.", "Mun", date8);
        ChatMessage msg9 = new ChatMessage("I think I'm done, just need to review one section.", "AJ", date9);
        ChatMessage msg10 = new ChatMessage("Ayt, sounds good", "Mun", date10);

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

    public void addMessage(ChatMessage message){
        this.messages.add(message);
    }

    public List<ChatMessage> getMessages(LocalDateTime start, LocalDateTime end){

        if(start == null || end == null)
            return messages;
        else if(start.isAfter(end))
            return messages;

        List<ChatMessage> tempList = new ArrayList<>();
        if (start.isBefore(end) && !start.equals(end)) {
            for (ChatMessage msg : this.messages) {
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
        List<ChatMessage> tempList = new ArrayList<>();
        if (start.isBefore(end) && !start.equals(end)) {
            for (ChatMessage msg : this.messages) {
                if (!isWithinRange(msg, start, end))
                    tempList.add(msg);
            }

            messages = tempList;
        }
        else
            throw new Exception("Nothing to delete within given dates.");
    }

    public boolean isWithinRange(ChatMessage msg, LocalDateTime start, LocalDateTime end){
        if(msg.getDatetime().isAfter(start) && msg.getDatetime().isBefore(end))
            return true;
        else
            return false;
    }
}
