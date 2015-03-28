package chat;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Broadcaster {

    private static final List<BroadcastListener> listeners = new CopyOnWriteArrayList<BroadcastListener>();

    public static void register(BroadcastListener listener) {
        listeners.add(listener);
    }

    public static void unregister(BroadcastListener listener) {
        listeners.remove(listener);
    }

    public static void broadcast(Date date, String nick, final String message) {
        for (BroadcastListener listener : listeners) {
            listener.receiveBroadcast(message, date, nick);
        }
    }

    public interface BroadcastListener {
        public void receiveBroadcast(String message, Date date, String nick);
    }

}