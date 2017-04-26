import com.metadata.LibraryDomain.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controls the Login.fxml
 * Authenticates user's id and password before telling UI to change screen over to Details.fxml
 *
 * Created by Andrew on 4/23/2017.
 */
public class LoginController {
    private final String MEMBER_PRIVILEGE_CODE = "100";

    @FXML
    TextField userText;
    @FXML
    PasswordField passwordText;

    @FXML
    private void Login() {
        String id = userText.getText();
        String password = passwordText.getText();
        Member m = UI.memberList.getMemberByID(id);
        //Staff s = UI.staffList.getStaffByUsername(id);
        if (m == null /*&& s == null*/){
            showLoginError();
            return;
        }
        else if (m != null && password.equals(m.getPassword())){
            UI.showDetailsPage(m.getName(), m.getID(), m.getPrivelege().equals(MEMBER_PRIVILEGE_CODE)? PrivilegeType.Member : PrivilegeType.Staff);
            return;
        }
        /*else if (s != null && password.equals(s.getPassword())){
            UI.showDetailsPage(s.getName(), s.getUserName(), PrivilegeType.Staff);
            return;
        }*/

        showLoginError();
    }

    private void showLoginError(){
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Login Error");
        a.setContentText("Invalid Username/Password Combination");
        a.setHeaderText(null);
        a.showAndWait();
    }
}
