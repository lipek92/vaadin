package chat.windows;

import chat.forms.LoginForm;
import com.vaadin.ui.Window;

public class LoginPanelWindow extends Window {

    private LoginForm loginForm;

    public LoginPanelWindow() {
        super("Podaj nick");
        loginForm = new LoginForm();
        this.setClosable(false);
        this.setModal(true);
        this.setResizable(false);
        setContent(loginForm);

    }
}