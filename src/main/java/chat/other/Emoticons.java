package chat.other;

import java.util.HashMap;
import java.util.Map;

public class Emoticons {

    private Map<String, String> emoticons = new HashMap();

    public Emoticons()
    {
        emoticons.put(":)", "<img src=\"../VAADIN/themes/mytheme/emoticons/smile.png\">");
        emoticons.put(":D", "<img src=\"../VAADIN/themes/mytheme/emoticons/big-smile.png\">");
    }

    public String replaceEmots(String message)
    {
        String replaced = message;

        for(String key: emoticons.keySet()) {
            replaced = replaced.replace(key, emoticons.get(key));
        }

        //  String replaced = message.replace(":)", "<img src=\"../VAADIN/themes/mytheme/emoticons/smile.png\">");
        return replaced;
    }
}
