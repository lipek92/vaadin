package chat;

import javax.servlet.annotation.WebServlet;

import chat.other.Broadcaster;
import chat.other.Emoticons;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.WrappedSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import chat.windows.LoginPanelWindow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
@Theme("valo")
@Widgetset("chat.MyAppWidgetset")
@Push
public class MyUI extends UI implements Broadcaster.BroadcastListener {

    private final VerticalLayout layout = new VerticalLayout();
    private final Panel messagesPanel = new Panel();

    private Emoticons emoticons = new Emoticons();

    TextField nick = new TextField("Nick");

    @Override
    protected void init(VaadinRequest request) {

        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);


        boolean isLoggedIn = getCurrentSession().getAttribute("nickname") != null;

        if (!isLoggedIn)
        {
            LoginPanelWindow loginPanelWindow = new LoginPanelWindow();
            getUI().addWindow(loginPanelWindow);
            setContent(null);
        } else {
            nick.setValue(String.valueOf(getCurrentSession().getAttribute("nickname")));
            setContent(layout);
        }


        layout.setMargin(true);
        layout.addComponent(nick);

        nick.setEnabled(false);


        final TextArea message = new TextArea("Wiadomość", "");
        layout.addComponent(message);

        final Button send = new Button("Wyślij");
        layout.addComponent(send);
        send.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {

                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                String dateString = (dateFormat.format(date));
                String msg = emoticons.replaceEmots(message.getValue());

                Broadcaster.broadcast(dateString, nick.getValue(), msg);
            }
        });

        final Button logout = new Button("Wyloguj");
        layout.addComponent(logout);
        logout.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                getCurrentSession().setAttribute("nickname", null);

                LoginPanelWindow loginPanelWindow = new LoginPanelWindow();
                getUI().addWindow(loginPanelWindow);
                setContent(null);
            }
        });


        // Register broadcast listener
        Broadcaster.register(this);

    }

    private void addComponent(Component c) {
        layout.addComponent(c);
    }

    @Override
    public void detach() {
        Broadcaster.unregister(this);
        super.detach();
    }

    @Override
    public void receiveBroadcast(final String message, final String date, final String nick) {
        access(new Runnable() {
            @Override
            public void run() {
                //Label msg = new Label(date + "<b> "+nick+"</b> "+  message + "<img src=\"http://awesomemoticon.appspot.com/images/icon.png\">", ContentMode.HTML);
                Label msg = new Label(date + "<b> "+nick+"</b> "+  message, ContentMode.HTML);
                addComponent(msg);

            }
        });
    }

    public static WrappedSession getCurrentSession(){
        return VaadinService.getCurrentRequest().getWrappedSession();
    }

    public void setChatContent(){
        nick.setValue(String.valueOf(getCurrentSession().getAttribute("nickname")));
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
