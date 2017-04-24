import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Andrew on 4/23/2017.
 */
public class DetailsController {

    public DetailsController(String user){
        this.user = user;
    }

    void initialize(){
        testLabel.setText(user);
    }

    @FXML
    Label testLabel;

    String user;

    @FXML
    private void Logout() throws IOException {
        UI.showLoginPage();
    }

}
