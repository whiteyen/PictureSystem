package main.java.model;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import main.java.action.CopyAction;
import main.java.action.DeleteAction;
import main.java.action.PasteAc;
import main.java.action.RenameAction;
import main.java.controller.MainUIController;

import java.io.File;
import java.util.ArrayList;

public class PictureContextMenu {
    MainUIController mainUI;
    private int preop;
    public  PictureContextMenu(Node node , MainUIController mainUI,boolean flag){
        this.mainUI = mainUI;
        if(flag){
            PictureMenu(node);
        }
        nullMenu(node);
    }
    public void PictureMenu(Node node) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem open = new MenuItem("打开");
        MenuItem copy = new MenuItem("复制");
        MenuItem cut = new MenuItem("剪切");
        MenuItem rename = new MenuItem("重命名");
        MenuItem delete = new MenuItem("删除");
        copy.setOnAction(e->{
            new CopyAction();
        });
        delete.setOnAction(e->{
            new DeleteAction(mainUI);
        });
        rename.setOnAction(e->{
            new RenameAction(mainUI);
        });

        contextMenu.getItems().addAll(open, copy, delete, cut, rename);
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Node clickNode = e.getPickResult().getIntersectedNode();
            if( (clickNode instanceof PictureInstance)){
                if (e.getButton() == MouseButton.SECONDARY)
                    contextMenu.show(node, e.getScreenX(), e.getScreenY());
                else {
                    if (contextMenu.isShowing())
                        contextMenu.hide();
                }

            }
            else {
                if (contextMenu.isShowing()) {
                    contextMenu.hide();
                }
            }

        });
    }
    public void nullMenu(Node node){
            ContextMenu mouseRightClickMenu = new ContextMenu();
            MenuItem paste = new MenuItem("粘贴");
            MenuItem all = new MenuItem("全选");
            Menu sort = new Menu("排序方式");

            //添加排序子菜单的元素
            MenuItem time = new MenuItem("日期");
            MenuItem name = new MenuItem("名称");
            MenuItem big = new MenuItem("大小");
            MenuItem up = new MenuItem("升序");
            MenuItem down  = new MenuItem("降序");
            sort.getItems().addAll(time,name,big,new SeparatorMenuItem(),up,down);

            //排序
            name.setOnAction(e->{
                mainUI.sortByNameUpdate();
                preop=1;
            });
            big.setOnAction(e->{
                mainUI.sortByLengthUpdate();
                preop=2;
            });
            up.setOnAction(e->{
                mainUI.setup(true);
                if(preop==1){
                    mainUI.sortByNameUpdate();
                }
                else if(preop==2){
                    mainUI.sortByLengthUpdate();
                }
            });
            down.setOnAction(e->{
                mainUI.setup(false);
                if(preop==1){
                    mainUI.sortByNameUpdate();
                }
                else if(preop==2){
                    mainUI.sortByLengthUpdate();
                }
            });


            paste.setOnAction(e->{

                new PasteAc(mainUI);
            });

            mouseRightClickMenu.getItems().add(sort);
            mouseRightClickMenu.getItems().add(paste);
            mouseRightClickMenu.getItems().add(all);


            node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                Node clickNode = e.getPickResult().getIntersectedNode();
                // System.out.println(clickNode.toString());
                if (clickNode instanceof FlowPane) {// 鼠标点击非图片节点
                    if (e.getButton() == MouseButton.SECONDARY) {// 鼠标右键
                        ArrayList<File> files = PictureInstance.getSelectedPictureFiles();
                        if (files.size() <= 0) {
                            paste.setDisable(true);
                        } else {
                            paste.setDisable(false);
                        }
                        mouseRightClickMenu.show(node, e.getScreenX(), e.getScreenY());
                    } else {
                        if (mouseRightClickMenu.isShowing()) {
                            mouseRightClickMenu.hide();
                        }
                    }
                } else {
                    if (mouseRightClickMenu.isShowing()) {
                        mouseRightClickMenu.hide();
                    }
                }
            });
        all.setOnAction(e->{
            boolean flag=false;
            for (Node childrenNode :  mainUI.getFlowPane().getChildren()) {
                if (childrenNode instanceof PictureInstance) {
                    if(((PictureInstance) childrenNode).selected.getValue()==true){
                        flag=true;
                    }
                }
            }
            for (Node childrenNode :  mainUI.getFlowPane().getChildren()) {
                if (childrenNode instanceof PictureInstance) {
                    ((PictureInstance) childrenNode).setSelected(true);
                }
            }
            if(flag){
                for (Node childrenNode :  mainUI.getFlowPane().getChildren()) {
                    if (childrenNode instanceof PictureInstance) {
                        ((PictureInstance) childrenNode).setSelected(false);
                    }
                }
            }
        });
        }
}
