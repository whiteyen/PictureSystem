package main.java.action;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.service.ChangeService;

public class OpenAction {
    public OpenAction() {
        // openStage=Main.stage;
        if (ChangeService.files == null) {
            String text = "没有选中图片";
            Button button = new Button(text);
            Pane root = new Pane(button);
            Scene scene = new Scene(root);
            Stage Stage = null;
            Stage = new Stage();
            Stage.setTitle("提示");
            Stage.setScene(scene);
            Stage.show();
            return;
        } else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/resources/fxml/PictureReader.fxml"));
                Parent root = (Parent)loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(OpenAction.class.getResource("/resources/css/PictureReader.css").toExternalForm());
                ChangeService.stage.setScene(scene);
                ChangeService.stage.setTitle("picture-system1.0");
                ChangeService.stage.setResizable(false);
                ChangeService.stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
