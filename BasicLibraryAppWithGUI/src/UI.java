import com.metadata.LibraryDomain.*;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Andrew on 4/18/2017.
 */
public class UI extends Application {

    @FXML
    TextField userText;
    @FXML
    PasswordField passwordText;

    MemberList memberList;
    StaffList staffList;
    Library localLib;
    Library offsiteLib;

    private Stage primaryStage;
    private static BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UI.class.getResource("Main.fxml"));
        mainLayout = loader.load();
        this.primaryStage.getIcons().add(new Image("/images.jpg"));
        this.primaryStage.setTitle("Library");
        Scene scene = new Scene(mainLayout);
        this.primaryStage.setScene(scene);
        showLoginPage();
        this.primaryStage.show();
    }

    static void showLoginPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UI.class.getResource("Login.fxml"));
        GridPane pane = loader.load();
        mainLayout.setCenter(pane);
    }

    static void showDetailsPage(String user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UI.class.getResource("Details.fxml"));
        DetailsController c = new DetailsController(user);
        loader.setController(c);
        BorderPane pane = loader.load();
        mainLayout.setCenter(pane);
        c.initialize();
    }



}
