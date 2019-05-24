package main.java.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import main.java.service.ChangeService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class PPT implements Initializable{
    @FXML
    private ImageView imageview;
    private  int count = 1 ;
    Timeline timeline = new Timeline();
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyValue keyValue = new KeyValue(imageview.scaleXProperty(), 1.0);
        KeyValue keyValue2 = new KeyValue(imageview.scaleYProperty(), 1.0);

        Duration duration = Duration.millis(3000);

        try {
            imageview.setImage(new Image(ChangeService.files.get(0).toURI().toURL().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        EventHandler<ActionEvent> onFinished = (ActionEvent t) -> {
            if (count < ChangeService.files.size()) {
                try {
                    imageview.setImage(new Image(ChangeService.files.get(count).toURI().toURL().toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else if (count == ChangeService.files.size()) {
                count = 0;
                timeline.stop();
            }
            count++;
        };
        KeyFrame keyFrame1 = new KeyFrame(duration, onFinished, keyValue, keyValue2);
        //timeline.cycleDurationProperty().notify();
        timeline.getKeyFrames().add(keyFrame1);
        timeline.play();

    }
    public void back(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/fxml/PictureReader.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(LoginController.class.getResource("/resources/css/PictureReader.css").toExternalForm());
            ChangeService.stage.setScene(scene);
            ChangeService.stage.setTitle("picture-system1.0");
            ChangeService.stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void play(){timeline .play();}
    public void stop(){timeline.stop();}
}

