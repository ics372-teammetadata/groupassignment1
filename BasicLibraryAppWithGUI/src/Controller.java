import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import com.metadata.LibraryDomain.*;
import javafx.scene.layout.Priority;

/**
 * Created by Andrew on 4/18/2017.
 */
public class Controller implements Initializable {

    MemberList memberList;
    StaffList staffList;
    Library localLib;
    Library offsiteLib;


    @FXML
    TextField idBox;
    @FXML
    PasswordField pwBox;
    @FXML
    GridPane loginPane;
    @FXML
    GridPane displayPane;

    @FXML
    public void Login(){
        loginPane.setVisible(false);
        displayPane.setVisible(true);
    }

    public void showErrorDialog(Exception e){
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FileProcessor processor = new FileProcessor(new File(System.getProperty("user.dir") + "/BasicLibraryAppWithGUI/src/members.xml"));
            memberList = processor.processXMLMemberList();
            staffList = processor.processXMLStaffList(new FileInputStream(new File(System.getProperty("user.dir") + "/BasicLibraryAppWithGUI/src/staff.xml")));
            localLib = processor.processJSONData(new FileInputStream(new File(System.getProperty("user.dir") + "/BasicLibraryAppWithGUI/src/JSONLib.json")));
            offsiteLib = processor.processXMLData(new FileInputStream(new File(System.getProperty("user.dir") + "/BasicLibraryAppWithGUI/src/testLib.xml")));
        } catch (Exception e) {
            showErrorDialog(e);
            Platform.exit();
        }



    }
}
