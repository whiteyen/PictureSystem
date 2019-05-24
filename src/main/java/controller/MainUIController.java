package main.java.controller;


import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import main.java.model.FileTree;
import main.java.model.PictureContextMenu;
import main.java.model.PictureFile;
import main.java.model.PictureInstance;
import main.java.service.ChangeService;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MainUIController implements Initializable {
    @FXML
    private TreeView<PictureFile> tv;
    @FXML
    private FlowPane picture_show;
    @FXML
    private Button big_scene;
    @FXML
    private Button medium_scene;
    @FXML
    private Button small_scene;
    @FXML
    private  ImageView selected;
    @FXML
    private Pagination pagination;
    @FXML
    private Label selectLabel;
    @FXML
    public Label pictureName;
    @FXML
    private Label fileSize;
    @FXML
    private Label pictureType;
    @FXML
    private Label pictureSize;
    @FXML
    private JFXButton addLabel;//编辑标签按键
    @FXML
    private TextArea add;//编辑标签处
    @FXML
    private TextArea exist;//已有标签处
    @FXML
    private AnchorPane lableanchorpane;
    @FXML
    private Button ex;
    @FXML
    public TextField rename;
    @FXML
    public TextField labelText;
    private ArrayList<PictureInstance>pictures;
    private ArrayList<File>files;
    public static String theFilePath;
    private MainUIController mainUI;
    public  static  String user_id;
    private FlowPane flowPane;
    private int count = 32;
    private int size=0;
    public String picturename;
    public float picturesize;
    public static boolean up = true;
    public ArrayList<PictureInstance>phototemp=new ArrayList<PictureInstance>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDate();
    }
    private void initDate(){

        pictures = new ArrayList<>();
        tv =new FileTree(this,tv).gettreeView();
        new  PictureContextMenu(picture_show,this,true);
        pagination.setPrefSize(1131,665);
        selectLabel.setText("未选择图片");
        selectLabel.setStyle("-fx-text-fill: red;");
        lableanchorpane.setVisible(false);
        rename.setVisible(false);
        //System.out.println(pagination.getPrefWidth()+" "+pagination.getPrefHeight());

    }
    public void expand(){
        lableanchorpane.setVisible(true);
        pagination.setPrefSize(790,665);
        ex.setVisible(false);
        PictureInstance.flag=true;//如果已經按過則不會打開列表
        if(size==0) {
            count =20;
        }
        else if(size==1) {
            count =35;
        }
        else if(size==2) {
            count =15;
        }
        else {
            count =6;
        }
        setFactory();
    }
    public void setup(boolean tmp){
        up=tmp;
    }
    public void cccc(){
        lableanchorpane.setVisible(false);
        pagination.setPrefSize(1131,665);
        ex.setVisible(true);
        PictureInstance.flag=false;//如果已經按過則不會打開列表
        if(size==0) {
            count =32;
        }
        else if(size==1) {
            count =50;
        }
        else if(size==2) {
            count =21;
        }
        else {
            count =8;
        }
        setFactory();
    }
    public void clearPictures(){
        pictures.clear();
        setFactory();
    }
    public  void addPictures(PictureInstance pictureInstance){
        pictures.add(pictureInstance);
    }
    public ArrayList<File> getFiles() {
        return files;
    }

    public FlowPane getFlowPane() {
        return picture_show;
    }
    public ObservableList<Node> getFlowPaneChildren() {
        return picture_show.getChildren();
    }

    public void show_picture(){

        files = new ArrayList<File>();
        pagination.setPrefSize(1131,665);
        picture_show.getChildren().remove(0,picture_show.getChildren().size());
        for(PictureInstance tmp :pictures){
            picture_show.getChildren().add(tmp);
        }
        for(int i=0 ; i<pictures.size() ; i++){
            files.add(pictures.get(i).getImageFile());
        }
        ChangeService.files = files;
        setFactory();
        //pagination.setStyle("-fx-border-color:red;");
    }

    public void setFactory(){

        pagination.setPageFactory((Integer pageIndex) -> {
            if (pageIndex >= (pictures.size()/this.count)+1) {//限制分页的页数
                return null;
            } else {
                return createPage (pageIndex);
            }
        });
    }
    public  void update(){
        PictureFile pFile = new PictureFile(theFilePath);

        this.clearPictures();
        PictureFile[]pictureFiles = pFile.listPictures();
        for(PictureFile pictureFile :pictureFiles){
            if(pictureFile.isPicture()){
                try{
                    PictureInstance pictureInstance = new PictureInstance(pictureFile,this);
                    this.addPictures(pictureInstance);
                }catch (Exception e) {
                    e.printStackTrace();
                }//System.out.println("hhh");
            }
        }

        setFactory();
    }
    public void setSmall_scene()throws MalformedURLException {
        if(lableanchorpane.isVisible()){
            this.count=35;
        }
        else{
            this.count=50;
        }
        this.size=1;
        setFactory();
    }
    public void setMedium_scene()throws MalformedURLException {
        if(lableanchorpane.isVisible()){
            this.count=15;
        }
        else{
            this.count=21;
        }
        this.size=2;
        setFactory();
    }
    public void setBig_scene()throws MalformedURLException {
        if(lableanchorpane.isVisible()){
            this.count=6;
        }
        else{
            this.count=8;
        }
        this.size=3;
        setFactory();
    }
    public void setImageview(Image image){
        selected.setImage(image);
        selectLabel.setText("一张图片已选择");
        selectLabel.setStyle("-fx-text-fill:#07eb07;");
        pictureName.setText(picturename);
        double a =image.getHeight();
        double b =image.getWidth();
        pictureSize.setText(a+"px ,"+b+"px");
        pictureType.setText(picturename.substring(picturename.length()-4,picturename.length()));
        fileSize.setText(picturesize+" KB");
        add.setText(null);
        exist.setText(null);
        //
        try{
            File file = new File(picturename+".txt");
            FileReader in = new FileReader(file);
            StringBuffer s = new StringBuffer();
            char tom[] = new char[50];
            int n = -1;
            while ((n=in.read(tom,0,50))!=-1){
                String temp = new String(tom,0,n);
                s.append(temp);
            }
            in.close();
            exist.setText(new String(s));
            add.setText(new String(s));
        }
        catch (IOException e){

        }

    }

    public FlowPane createPage(int pageIndex){
        picture_show.getChildren().remove(0,picture_show.getChildren().size());//移除原有图片
        int j=0;
        for(PictureInstance tmp :pictures){
            //picture_show.getChildren().add(tmp);
            if(size==3){
                try{
                    tmp.setBig();
                }
                catch (Exception e){}
            }
            else if(size==2){
                try{
                    tmp.setMedium();
                }
                catch (Exception e){}
            }
            else if(size==1) {
                try {
                    tmp.setSmall();
                } catch (Exception e) {
                }
            }
            if(j>=pageIndex*count&&j<(pageIndex+1)*count){
                picture_show.getChildren().add(tmp);
            }
            j=j+1;//限制每次显示count个
        }
        flowPane = picture_show;
        return flowPane;
    }
    public void labelAdder (){
        String label;
        if(add.getText().isEmpty()){
            exist.setText(null);
            exist.setPromptText("图片无标签");
            label = null;
        }
        else {
            label = add.getText();
        }
        try{
            File file = new File(picturename+".txt");
            FileWriter out = new FileWriter(file);
            out.write(label);
            out.close();
            exist.setText(label);
        }
        catch (IOException e){

        }


    }
    public void sortByNameUpdate(){
        PictureFile pFile = new PictureFile(theFilePath);
        this.clearPictures();
        PictureFile[]pictureFiles = pFile.listPictures();
        Arrays.sort(pictureFiles, new Comparator<PictureFile>() {
            @Override
            public int compare(PictureFile o1, PictureFile o2) {
                if(!up)
                   return o2.getImageName().compareTo(o1.getImageName());
                else return o1.getImageName().compareTo(o2.getImageName());
            }
        });
        for(PictureFile pictureFile :pictureFiles){
            if(pictureFile.isPicture()){
                try{
                    PictureInstance pictureInstance = new PictureInstance(pictureFile,this);
                    this.addPictures(pictureInstance);
                }catch (Exception e) {
                    e.printStackTrace();
                }//System.out.println("hhh");
            }
        }
        setFactory();
    }
    public void sortByLengthUpdate(){
        PictureFile pFile = new PictureFile(theFilePath);
        this.clearPictures();
        PictureFile[]pictureFiles = pFile.listPictures();
       Arrays.sort(pictureFiles, new Comparator<PictureFile>() {
            @Override
            public int compare(PictureFile o1, PictureFile o2) {
                long l1= o1.length();
                long l2= o2.length();
                if(!up)
                    return (int) (l2-l1);
                else
                    return (int) (l1-l2);
            }
        });
        for(PictureFile pictureFile :pictureFiles){
            if(pictureFile.isPicture()){
                try{
                    PictureInstance pictureInstance = new PictureInstance(pictureFile,this);
                    this.addPictures(pictureInstance);
                }catch (Exception e) {
                    e.printStackTrace();
                }//System.out.println("hhh");
            }
        }
        setFactory();
    }
    public void searchPicture(){
        String text1;
        String label = "00";
        label = labelText.getText();
        phototemp.clear();
        for(PictureInstance tmp :pictures){
            phototemp.add(tmp);
        }//暂存数组更新，用于恢复搜索前状态
        ArrayList<PictureInstance>photo=new ArrayList<PictureInstance>();
        for(PictureInstance tmp :pictures) {
            text1 = "00";
        String temp = tmp.getPictureFile().getImageName()+".txt";
        try{
            File f = new File(temp);
            FileReader in = new FileReader(f);
            StringBuffer s = new StringBuffer();
            char text[] = new char[20];
            int n =-1;
            while ((n=in.read(text,0,20))!=-1){
                text1 = new String(text,0,n);
                s.append(text1);
            }
            in.close();
        }
        catch (IOException e){

        }
        if(text1.contains(label)){//若包含
            System.out.print("ko");
            photo.add(tmp);
        }
        }
        this.clearPictures();
        for(PictureInstance tmp :photo){
            pictures.add(tmp);
        }
        setFactory();
    }
    public void searchEnd(){
        labelText.clear();
        this.clearPictures();
        for(PictureInstance tmp :phototemp){
            pictures.add(tmp);
        }
        setFactory();
    }
}


