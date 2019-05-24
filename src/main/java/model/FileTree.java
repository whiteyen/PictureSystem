package main.java.model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.java.controller.MainUIController;

import java.io.File;

public class FileTree {

     private MainUIController mainUI;
     private TreeView<PictureFile> treeView;
     private TreeItem<PictureFile> root;
     private static File file = new File("C:/user");
     private static File[] rootPath =new File[5];
     private PictureFile[] pictureFiles;
     public FileTree(MainUIController mianUI, TreeView<PictureFile> treeView) {

          File file = new File("C:"+File.separator+"user"+File.separator+ MainUIController.user_id);
          if(!file.exists())file.mkdirs();
          System.out.println(file);
          rootPath[0]=file;
          this.mainUI = mianUI;
          // System.out.println(mianUI);
          this.treeView = treeView;

          buildFileTree();

     }

     private void buildFileTree() {
          root = new TreeItem<PictureFile>(new PictureFile("root"));
          root.setExpanded(true);
          treeView.setRoot(root);
          treeView.setShowRoot(false);
          treeView.setEditable(true);
          getSelected();
          treeView.setCellFactory((TreeView<PictureFile> p)->
                  new FileTreeCellImpl()
          );
          for (int i = 0; i < 1; i++) {
               FileTreeItem item = new FileTreeItem(new PictureFile(rootPath[i]));
               root.getChildren().add(item);
          }
          root.getChildren().get(0).setExpanded(true);
     }
     public TreeView<PictureFile> gettreeView() {
          return treeView;
     }

     //为目录树添加修改名字功能&&添加功能还未完善
     private final class FileTreeCellImpl extends TreeCell<PictureFile>{
          private TextField textField;
          // 添加菜单
          private final ContextMenu addMenu = new ContextMenu();
          private PictureFile pictureFile;
          // 构造方法,如果对儿子单元有特殊的需求可以在构造方法里实现
          public FileTreeCellImpl() {

               // 新增菜单栏目
               MenuItem addMenuItem = new MenuItem("新建");
               MenuItem deleteItem = new MenuItem("删除");
               addMenu.getItems().add(addMenuItem);
               addMenu.getItems().add(deleteItem);
               addMenuItem.setOnAction((ActionEvent ) -> {
                            PictureFile newPictureFile = new PictureFile(file);
                            TreeItem newEmployee =
                                    new TreeItem<>();
                            try {
                                 String parent = getTreeItem().getValue().getImageFile().getPath();

                                 String newDir = parent + File.separator+ "新建文件夹";

                                 File file = new File(newDir);
                                 if(!file.exists()) {
                                      file.mkdir();
                                 }
                                 System.out.println(newDir);
                            } catch (Exception e) {
                                 e.printStackTrace();
                            }
                            buildFileTree();

                       }
               );
               deleteItem.setOnAction((ActionEvent )->{
                    getTreeItem().getValue().getImageFile().delete();
                    buildFileTree();
               });
          }
          // 所有的TreeView的儿子控件都是支持Edit的,所有每次选中编辑的时候都会执行该方法
          @Override
          public void startEdit() {
               super.startEdit();

               if (textField == null) {
                    // 每个单元会拥有一个文本框
                    createTextField();
               }
               setText(null);
               setGraphic(textField);
               textField.selectAll();
          }
          @Override
          public void cancelEdit() {
               super.cancelEdit();
               // 清空编辑
               setText( getItem().getImageName());
               //setGraphic(getTreeItem().getGraphic());
          }
          @Override
          public void updateItem(PictureFile item, boolean empty) {
               super.updateItem(item, empty);
               // 为空不处理
               if (empty) {
                    setText(null);
                    setGraphic(null);
               } else {
                    // 如果正在编辑
                    if (isEditing()) {
                         if (textField != null) {
                              // 设置为原来Item的内容
                              textField.setText(item.getImageName());
                         }
                         setText(null);
                         // 设置显示为文本内容
                         setGraphic(textField);
                    } else {
                         setText(getString());
                         setGraphic(getTreeItem().getGraphic());
                         // 如果有儿子  并且 有父亲，则拥有添加顾客菜单栏
                         if (!getTreeItem().isLeaf() && getTreeItem().getParent() != null) {
                              setContextMenu(addMenu);
                         }else {
                              // 其他情况没有菜单栏
                              setContextMenu(null);
                         }
                    }
               }
          }
          // 创建文本区，名字为该item名
          private void createTextField() {
               textField = new TextField(getString());
               // 键盘事件处理，松开键盘时
               textField.setOnKeyReleased((KeyEvent t) -> {
                    if (t.getCode() == KeyCode.ENTER) {
                         // 输入回车时，单元编辑为文本框内容
                         String path =new String();
                         try{
                              path = getItem().getImageFile().getParentFile().toString();
                         }catch (Exception e){
                              e.printStackTrace();
                         }

                         getItem().renameFile(
                                 path, getItem().getImageFile().getName(), textField.getText());
                         getItem().setImageName(textField.getText());
                         commitEdit(getItem());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                         // 输入退出键时,清空编辑
                         cancelEdit();
                    }
               });
               textField.setOnMouseClicked((MouseEvent t)->{
                    if(t.getClickCount()==1){
                         String path =new String();
                         try{
                              path = getItem().getImageFile().getParentFile().toString();
                         }catch (Exception e){
                              e.printStackTrace();
                         }

                         getItem().renameFile(
                                 path, getItem().getImageFile().getName(), textField.getText());
                         getItem().setImageName(textField.getText());
                         commitEdit(getItem());
                    }
               });

          }
          // 获取该Item的名字
          private String getString() {
               return getItem() == null ? "" : getItem().getImageName();
          }
     }


     /*
       显示目录文件下的图片
      */
     private void getSelected(){
          treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<PictureFile>>() {
               @Override
               public void changed(ObservableValue<? extends TreeItem<PictureFile>> observable, TreeItem<PictureFile> oldValue, TreeItem<PictureFile> newValue) {
                    mainUI.getFlowPane().getChildren().remove(0,mainUI.getFlowPane().getChildren().size());
                    PictureFile pFile = treeView.getSelectionModel().getSelectedItem().getValue();
                    if(pFile.isDirectory()){

                         MainUIController.theFilePath = pFile.getImageFile().getAbsolutePath();
                         System.out.println(MainUIController.theFilePath);
                         int total = 0;

                         pictureFiles = pFile.listPictures();
                         showPicture();

                    }
               }
          });
     }
     private void showPicture(){
          mainUI.clearPictures();
          for(PictureFile pictureFile :pictureFiles){
               // System.out.println(pictureFile.getImageName());
               if(pictureFile.isPicture()){
                    try{
                         PictureInstance pictureInstance = new PictureInstance(pictureFile,mainUI);
                         mainUI.getFlowPane().getChildren().add(pictureInstance);
                         mainUI.addPictures(pictureInstance);
                    }catch (Exception e){
                         e.printStackTrace();
                    }
                    mainUI.show_picture();
               }
          }
     }

}