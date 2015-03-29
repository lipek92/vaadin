package chat.bean;

import java.util.ArrayList;

public class Messages {

    private ArrayList<Message> messagesList = new ArrayList<Message>();

    public void addMessage(Message message)
    {
        messagesList.add(message);
    }

    public ArrayList<Message> getMessages()
    {
        return messagesList;
    }

}
