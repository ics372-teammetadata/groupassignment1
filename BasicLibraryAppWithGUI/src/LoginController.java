import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Created by Andrew on 4/23/2017.
 */
public class LoginController {
    @FXML
    TextField userText;

    @FXML
    private void Login() throws IOException {
        UI.showDetailsPage(userText.getText());
    }
}
