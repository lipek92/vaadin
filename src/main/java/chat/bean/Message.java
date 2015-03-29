package chat.bean;

public class Message {
    private String date;
    private String nick;
    private String message;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(String date, String nick, String message)
    {
        this.date = date;
        this.nick = nick;
        this.message = message;
    }
}
