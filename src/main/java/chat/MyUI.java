package chat;

import javax.servlet.annotation.WebServlet;

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

import java.util.Date;

/**
 *
 */
@Theme("mytheme")
@Widgetset("chat.MyAppWidgetset")
@Push
public class MyUI extends UI implements Broadcaster.BroadcastListener {

    private final VerticalLayout layout = new VerticalLayout();
    private VerticalLayout mainContent;
    TextField nick = new TextField();

    @Override
    protected void init(VaadinRequest request) {

        mainContent = new VerticalLayout();
        mainContent.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);


        boolean isLoggedIn = getCurrentSession().getAttribute("nickname") != null;
 //       System.out.println(getSession() +"lolol");

        if (!isLoggedIn)
        {
            LoginPanelWindow loginPanelWindow = new LoginPanelWindow();
            getUI().addWindow(loginPanelWindow);
            setContent(null);
        } else {
      //      System.out.println(String.valueOf(getCurrentSession().getAttribute("nickname")) + "weeeeeeeeeeeeee");
            nick.setValue(String.valueOf(getCurrentSession().getAttribute("nickname")));
            setContent(layout);
        }


        layout.setMargin(true);
    //    setContent(layout);

      //  String nickname = String.valueOf(getSession().getAttribute("nickname"));

  //       nick = new TextField(String.valueOf(getCurrentSession().getAttribute("nickname")));

        layout.addComponent(nick);


        final TextArea message = new TextArea("",
                "Your message...");
        layout.addComponent(message);
        final Date date = new Date();
        date.getTime();
        final Button button = new Button("Send");
        layout.addComponent(button);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Broadcaster.broadcast(date,nick.getValue(), message.getValue());
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
    public void receiveBroadcast(final String message, final Date date, final String nick) {
        access(new Runnable() {
            @Override
            public void run() {
                Label msg = new Label(date + "<b> "+nick+"</b> "+  message + "<img src=\"http://awesomemoticon.appspot.com/images/icon.png\">", ContentMode.HTML);
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
