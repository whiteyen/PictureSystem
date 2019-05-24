package main.java.action;

import main.java.controller.MainUIController;
import main.java.model.PictureInstance;

public class DeleteAction {
    MainUIController mainUIController;

    public DeleteAction(MainUIController mainUI) {
        mainUIController = mainUI;
        for (PictureInstance pNode : PictureInstance.getSelectedPictures()) {
            pNode.getImageFile().delete();
        }
        mainUI.update();
    }
}

