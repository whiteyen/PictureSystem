package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import main.java.action.BeautifyAction;
import main.java.service.ChangeService;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class PictureReaderController implements Initializable {
    private Image image;
    @FXML private Text hint;
    @FXML private StackPane show;
    @FXML private ImageView picture_show;
    @FXML private ScrollPane scrollPane;
    @FXML private JFXButton mirror;

    public static int faction=1;//放大缩小因子
    ChangeService console;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        console = new ChangeService();

    }
    private void init() {

        image = new Image(ChangeService.getFile());
        hint.setVisible(false);
        ChangeService.originHight = picture_show.getFitHeight();
        ChangeService.originWidth = picture_show.getFitWidth();
        picture_show.setPreserveRatio(true);
        picture_show.setSmooth(true);
        picture_show.setImage(image);
        //picture_show.setStyle("-fx-border-color:red;");
        //scrollPane.setPannable(true);
    }
    public void next(){
        if(console.next()){
            image = new Image(console.getFile());
            picture_show.setImage(image);
        }
        else {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    hint.setVisible(true);
                    try {
                        Thread.sleep(1000);//该线程睡眠5秒
                    } catch (InterruptedException ex) {
                    }
                    hint.setVisible(false);
                }
            });
            t.start();
        }
        picture_show.setRotate(0);
    }
    public void pre(){
        if(console.pre()){
            image = new Image(console.getFile());
            picture_show.setImage(image);
        }
        else {

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    hint.setVisible(true);
                    try {
                        Thread.sleep(1000);//该线程睡眠5秒
                    } catch (InterruptedException ex) {
                    }
                    hint.setVisible(false);
                }
            });
            t.start();
        }
        picture_show.setRotate(0);
    }
    private void CountDown(int second) {

        //开始时间
        long start = System.currentTimeMillis();
        //结束时间
        final long end = start + second * 1000;

        final Timer timer = new Timer();
        //延迟0毫秒（即立即执行）开始，每隔1000毫秒执行一次
        timer.schedule(new TimerTask() {
            public void run() {

            }
        }, 0, 1000);
        //计时结束时候，停止全部timer计时计划任务
        timer.schedule(new TimerTask() {
            public void run() {
                timer.cancel();
            }

        }, new Date(end));
    }
    public void enlarge(){
        System.out.println(show.getScaleX());
        show.setScaleX(show.getScaleX()+0.1);
        show.setScaleY(show.getScaleY()+0.1);

        //show.setPrefSize();
    }
    public void small(){
        System.out.println(show.getScaleX());
        show.setScaleX(show.getScaleX()-0.1);
        show.setScaleY(show.getScaleY()-0.1);
    }
    public void roate(){
        picture_show.setRotate((picture_show.getRotate()+90)%360);
    }
    public void back(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/fxml/MainUI.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(PictureReaderController.class.getResource("/resources/css/MainUI.css").toExternalForm());
            ChangeService.stage.setScene(scene);
            ChangeService.stage.setTitle("picture-system1.0");
            ChangeService.stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void setMirror(ActionEvent e) {
        if (picture_show.getNodeOrientation().name().equals("RIGHT_TO_LEFT")) {
            picture_show.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        } else {
            picture_show.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
    }
    public void beautify() {
        ChangeService.change = this.picture_show;
        new BeautifyAction();

    }
    public void play(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/fxml/ppt.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(PictureReaderController.class.getResource("/resources/css/PictureReader.css").toExternalForm());
            ChangeService.stage.setScene(scene);
            ChangeService.stage.setTitle("picture-system1.0");
            ChangeService.stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
