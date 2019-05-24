package main.java.action;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.controller.MainUIController;
import main.java.model.PictureInstance;
import main.java.model.alert;
import main.java.service.ChangeService;

import java.io.File;

public class RenameAction {
     public  RenameAction(MainUIController mainUI) {

        if(PictureInstance.getSelectedPictures().size()==1){
            mainUI.rename.setVisible(true);
            mainUI.rename.setText(PictureInstance.getSelectedPictures().get(0).getImageFile().getName());
            mainUI.rename.setOnKeyReleased((KeyEvent t) -> {
                if (t.getCode() == KeyCode.ENTER) {
                    // 输入回车时，单元编辑为文本框内容
                    String newString = mainUI.rename.getText();
                   // System.out.println(newString);
                    if(PictureInstance.getSelectedPictures().get(0).getImageFile().renameTo(new File(mainUI.theFilePath+ File.separator+newString))){

                    }
                    else {
                        alert.showAlert("文件名重名","请重新输入", ChangeService.stage);
                        return;
                    }
                    mainUI.rename.setVisible(false);
                    mainUI.pictureName.setText(newString);
                    mainUI.update();
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    // 输入退出键时,清空编辑
                    mainUI.rename.setText(PictureInstance.getSelectedPictures().get(0).getImageFile().getName());
                }
            });

        }


     }
}
