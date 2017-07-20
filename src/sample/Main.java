package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Media music=new Media(new File("3.mp3").toURI().toString());
        MediaPlayer play=new MediaPlayer(music);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        play.play();


        primaryStage.setTitle("Tetris");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
