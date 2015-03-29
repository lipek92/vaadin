package chat;

import javax.servlet.annotation.WebServlet;

import chat.bean.Message;
import chat.bean.Messages;
import chat.other.Broadcaster;
import chat.other.Emoticons;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.event.ShortcutAction;
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
    private final VerticalLayout messagesPanel = new VerticalLayout();
    private final HorizontalLayout header = new HorizontalLayout();
    private final HorizontalLayout footer = new HorizontalLayout();

    Label nick = new Label("");
    final TextField message = new TextField("Wiadomość", "");

    private Emoticons emoticons = new Emoticons();
    static private Messages messagesBean = new Messages();

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
            message.focus();
        }
        layout.setMargin(true);

        Panel panel = new Panel("Chat");
        panel.setWidth("800px");
        panel.setHeight("500px");
        messagesPanel.setMargin(true);
        panel.setContent(messagesPanel);

        addMessagesFromList();

        header.setWidth("800px");
        footer.setWidth("800px");

        layout.addComponent(header);
        layout.addComponent(panel);
        layout.addComponent(footer);


       // layout.addComponent(messagesPanel);
        header.addComponent(nick);
        message.setWidth("600px");
        message.focus();
        footer.addComponent(message);

        final Button send = new Button("Wyślij");
        send.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        footer.addComponent(send);
        send.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {

                if (!message.isEmpty()) {
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date = new Date();
                    String dateString = (dateFormat.format(date));
                    String msg = emoticons.replaceEmots(message.getValue());

                    Broadcaster.broadcast(dateString, nick.getValue(), msg);

                    messagesBean.addMessage(new Message(dateString, nick.getValue(), msg));

                    message.setValue("");
                }
            }
        });

        final Button logout = new Button("Wyloguj");
        header.addComponent(logout);
        logout.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                getCurrentSession().setAttribute("nickname", null);

                LoginPanelWindow loginPanelWindow = new LoginPanelWindow();
                getUI().addWindow(loginPanelWindow);
                setContent(null);
                Broadcaster.broadcast("Uzytkownik "+nick.getValue()+" opuscil czat!");
            }
        });


        // Register broadcast listener
        Broadcaster.register(this);


    }

    private void addComponent(Component c) {
        messagesPanel.addComponentAsFirst(c);
    }

    private void addMessagesFromList()
    {
        for (Message m: messagesBean.getMessages())
        {
            Label msg = new Label("<i>"+ m.getDate() + "</i> <b>"+ m.getNick() +":</b> "+ m.getMessage(), ContentMode.HTML);
            messagesPanel.addComponentAsFirst(msg);
        }
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
                Label msg = new Label("<i>"+date+ "</i> <b>"+nick+":</b> "+  message, ContentMode.HTML);
                addComponent(msg);
            }
        });
    }
    @Override
    public void receiveBroadcast(final String infoMessage) {
        access(new Runnable() {
            @Override
            public void run() {
                Label msg = new Label("<i>"+infoMessage+"</i>", ContentMode.HTML);
                addComponent(msg);
            }
        });
    }

    public static WrappedSession getCurrentSession(){
        return VaadinService.getCurrentRequest().getWrappedSession();
    }

    public void setChatContent(){
        nick.setValue(String.valueOf(getCurrentSession().getAttribute("nickname")));
        Broadcaster.broadcast("Uzytkownik "+nick.getValue()+" wszedl na czat!");
        setContent(layout);
        message.focus();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
