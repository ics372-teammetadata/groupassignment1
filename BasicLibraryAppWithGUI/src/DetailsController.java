import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Created by Andrew on 4/23/2017.
 */
public class DetailsController {

    public DetailsController(String userName, String userID, PrivilegeType privilege){
        this.userName = userName;
        this.userID = userID;
        this.privilege = privilege;
    }

    void initialize(){
        userDisplay.setText(String.format("Logged in as %s", userName));
    }

    @FXML
    Label userDisplay;

    String userName;
    String userID;
    PrivilegeType privilege;

    @FXML
    private void Logout() throws IOException {
        UI.showLoginPage();
    }

}
