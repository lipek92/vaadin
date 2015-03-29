package chat.bean;


import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class User {

    @NotEmpty(message = "Pole nie może być puste!")
    @Size(min = 3, max = 12, message = "Nick musi mieć od 3 do 12 znaków!")
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
