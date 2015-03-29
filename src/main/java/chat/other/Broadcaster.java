package chat.other;

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

    public static void broadcast(String date, String nick, final String message) {
        for (BroadcastListener listener : listeners) {
            listener.receiveBroadcast(message, date, nick);
        }
    }

    public static void broadcast(String infoMessage) {
        for (BroadcastListener listener : listeners) {
            listener.receiveBroadcast(infoMessage);
        }
    }

    public interface BroadcastListener {
        public void receiveBroadcast(String date, String nick, String message);
        public void receiveBroadcast(String infoMessage);
    }

}