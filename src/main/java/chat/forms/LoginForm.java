package chat.forms;

import chat.MyUI;
import chat.bean.User;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import chat.windows.LoginPanelWindow;

public class LoginForm extends CustomComponent {

    private TextField login;
    private FormLayout formLayout;
    private FieldGroup binder;
    private BeanItem<User> item;
    private User user;
    private Button enter;

    public LoginForm() {
        formLayout = new FormLayout();
        formLayout.setMargin(true);
        user = new User();
        item = new BeanItem<>(user);
        binder = new BeanFieldGroup<>(User.class);
        binder.setItemDataSource(item);
        binder.setBuffered(true);
        this.setCompositionRoot(formLayout);

        login = binder.buildAndBind("Nick:", "nickname", TextField.class);
        login.setNullRepresentation("");
        login.focus();

        enter = new Button("Wejście");
        enter.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        enter.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    binder.commit();
                    MyUI.getCurrentSession().setAttribute("nickname", user.getNickname());

                    LoginPanelWindow parent = (LoginPanelWindow) getParent();

                    MyUI main = (MyUI) parent.getParent();

                    parent.close();
                    main.setChatContent();

     //               System.out.println(user.getNickname());
     //               getSession().setAttribute("nickname", login);

    //                System.out.println(MyUI.getCurrentSession());


                } catch (FieldGroup.CommitException e) {
                    e.printStackTrace();
                }
            }
        });

        formLayout.addComponents(login, enter);
    }
}
