package main.java.action;

import main.java.model.PictureInstance;

public class CopyAction {
    public CopyAction(){


        PictureInstance.getSelectedPictureFiles().clear();
        for(PictureInstance pNode : PictureInstance.getSelectedPictures()) {
            PictureInstance.getSelectedPictureFiles().add(pNode.getImageFile());
        }
       // PictureInstance.getCutedPictures().

    }
}
