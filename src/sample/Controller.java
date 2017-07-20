package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Node;


public class Controller  {

@FXML
private Button start, wyjscie;


public void startAction(ActionEvent e) throws IOException {

    Parent parent=FXMLLoader.load(this.getClass().getResource("Gra.fxml"));
    Scene scene=new Scene(parent);
    Stage mainStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
    mainStage.setScene(scene);
    mainStage.show();
}
public void wyjscieAction(ActionEvent e){
    System.exit(0);
}

}
