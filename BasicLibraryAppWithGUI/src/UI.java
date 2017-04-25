import com.metadata.LibraryDomain.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.*;

/**
 * Created by Andrew on 4/18/2017.
 */
public class UI extends Application {

    @FXML
    TextField userText;
    @FXML
    PasswordField passwordText;

    static MemberList memberList;
    static StaffList staffList;
    static Library localLib;
    static Library offsiteLib;

    static FileProcessor localProcessor;
    static FileProcessor offsiteProcessor;
    static final String localLibPath = "Data/JSONLib.json";
    static final String offsitLibPath = "Data/testLib.xml";
    static final String membersFilePath = "Data/members.xml";
    static final String staffFilePath = "Data/staff.xml";

    private Stage primaryStage;
    private static BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialize();

        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UI.class.getResource("Main.fxml"));
        try {
            mainLayout = loader.load();
        } catch (IOException e){}
        this.primaryStage.getIcons().add(new Image("/images.jpg"));
        this.primaryStage.setTitle("Library");
        Scene scene = new Scene(mainLayout);
        this.primaryStage.setScene(scene);
        showLoginPage();
        this.primaryStage.show();
    }

    private void initialize(){
        try {
            localProcessor = new FileProcessor(new File(localLibPath));
            offsiteProcessor = new FileProcessor(new File(offsitLibPath));
            memberList = localProcessor.processXMLMemberList(new FileInputStream( new File(membersFilePath)));
            staffList = localProcessor.processXMLStaffList(new FileInputStream(new File(staffFilePath)));
            localLib = localProcessor.processJSONData();
            offsiteLib = offsiteProcessor.processXMLData();
        } catch (Exception e) {
            showErrorDialog(e);
            Platform.exit();
        }
    }

    public static void showErrorDialog(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(e.getClass().toString());
        alert.setTitle("An Exception Occurred");
        alert.setContentText(e.getMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        Label label = new Label("Stack Trace:");

        javafx.scene.control.TextArea textArea = new TextArea(sw.toString());

        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(label, 0,0);
        content.add(textArea,0,1);

        alert.getDialogPane().setExpandableContent(content);

        alert.showAndWait();
    }

    static void showLoginPage() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UI.class.getResource("Login.fxml"));
        try {
            GridPane pane = loader.load();
            mainLayout.setCenter(pane);
        } catch (IOException e){}

    }

    static void showDetailsPage(String userName, String userID, PrivilegeType privilege) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UI.class.getResource("Details.fxml"));
        DetailsController c = new DetailsController(userName, userID, privilege);
        loader.setController(c);
        try {
            BorderPane pane = loader.load();
            mainLayout.setCenter(pane);
        }catch (IOException e){}

        c.initialize();
    }



}
