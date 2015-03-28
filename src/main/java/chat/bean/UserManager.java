package chat.bean;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static List<User> users = new ArrayList<User>();

    public void addUser(User u)
    {
        users.add(u);
    }

    public List<User> getUsers() {
        return users;
    }
}
