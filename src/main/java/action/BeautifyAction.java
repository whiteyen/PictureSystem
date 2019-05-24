package main.java.action;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.java.service.ChangeService;

public class BeautifyAction {
    public BeautifyAction() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/fxml/Beautify.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            //scene.getStylesheets().add("view/iVCSS.css");
            ChangeService.stage.setScene(scene);
            ChangeService.stage.setTitle("图片美化");
            //	Main.stage.setResizable(false);
            ChangeService.stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
