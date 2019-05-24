package main.java;
//import main.Login;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.java.service.ChangeService;
import java.awt.Toolkit;
public class LoginApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ChangeService.stage = primaryStage;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/fxml/login.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(LoginApp.class.getResource("/resources/css/LoginApp.css").toExternalForm());
            ChangeService.stage.setScene(scene);
            ChangeService.stage.setTitle("picture-system 1.0");
            ChangeService.stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}