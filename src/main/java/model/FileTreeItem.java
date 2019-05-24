package main.java.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class FileTreeItem extends TreeItem<PictureFile> {

    private boolean isLeaf;
    private boolean isFirstChildren = true;
    private boolean isFirstLeaf = true;
    private PictureFile pictureFile;
    public FileTreeItem(PictureFile pictureFile) {
        super(pictureFile);
        this.pictureFile = pictureFile;
    }

    public PictureFile getPictureFile() {
        return pictureFile;
    }

    @Override
    public ObservableList<TreeItem<PictureFile>> getChildren() {
        if (isFirstChildren) {
            isFirstChildren = false;
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {

        return false;
    }

    private ObservableList<TreeItem<PictureFile>> buildChildren(TreeItem<PictureFile> TreeItem) {
        PictureFile pictureFile = TreeItem.getValue();

        if (pictureFile != null && pictureFile.isDirectory()) {
            PictureFile[] pictureFiles = pictureFile.listPictures();
            if (pictureFiles != null && pictureFiles.length != 0) {
                ObservableList<TreeItem<PictureFile>> children = FXCollections.observableArrayList();
                for (PictureFile childFile : pictureFiles) {
                    if (childFile.isHidden() || childFile.isFile()) {
                        continue;
                    }
                    children.add(new FileTreeItem(childFile));
                }
                return children;
            }
        }
        return FXCollections.emptyObservableList();
    }
}