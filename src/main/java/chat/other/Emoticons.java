package chat.other;

import java.util.HashMap;
import java.util.Map;

public class Emoticons {

    private Map<String, String> emoticons = new HashMap();

    public Emoticons()
    {
        emoticons.put(":D", "<img src=\"../VAADIN/themes/mytheme/emoticons/big-smile.png\">");
        emoticons.put(":d", "<img src=\"../VAADIN/themes/mytheme/emoticons/big-smile.png\">");
        emoticons.put(";(", "<img src=\"../VAADIN/themes/mytheme/emoticons/cry.png\">");
        emoticons.put(":*", "<img src=\"../VAADIN/themes/mytheme/emoticons/kiss.png\">");
        emoticons.put(":(", "<img src=\"../VAADIN/themes/mytheme/emoticons/sad.png\">");
        emoticons.put(":)", "<img src=\"../VAADIN/themes/mytheme/emoticons/smile.png\">");
        emoticons.put(":o", "<img src=\"../VAADIN/themes/mytheme/emoticons/surprised.png\">");
        emoticons.put(":O", "<img src=\"../VAADIN/themes/mytheme/emoticons/surprised.png\">");
        emoticons.put(":p", "<img src=\"../VAADIN/themes/mytheme/emoticons/tongue.png\">");
        emoticons.put(":P", "<img src=\"../VAADIN/themes/mytheme/emoticons/tongue.png\">");
        emoticons.put(";)", "<img src=\"../VAADIN/themes/mytheme/emoticons/wink.png\">");
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
