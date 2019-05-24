package main.java.model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import main.java.action.OpenAction;
import main.java.controller.MainUIController;
import main.java.service.ChangeService;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class PictureInstance extends javafx.scene.control.Label {
    public BooleanProperty selected = new SimpleBooleanProperty();
    private MainUIController mainUI;
    private PictureFile pictureFile;
    public Image image;
    private ImageView imageView;
    private Text pictureName;
    private  boolean select;
    public static boolean flag=true;
    private PictureInstance pictureInstance;
    protected static ArrayList<File> selectedPictureFiles = new  ArrayList<>();
    protected static ArrayList<PictureInstance> selectedPictures = new ArrayList<>();
    protected static ArrayList<PictureInstance> cutedPictures = new ArrayList<>();
    private static  PictureInstance pre;

    public PictureInstance(PictureFile pictureFile,MainUIController mainUIController)throws MalformedURLException{

        this.pictureFile=pictureFile;
        this.mainUI=mainUIController;
        this.pictureInstance=this;
        init();
    }
    private void init()throws MalformedURLException {
        this.setGraphicTextGap(10);
        this.setPadding(new Insets(1,1,1,1));
        this.setContentDisplay(ContentDisplay.TOP);
        this.setPrefSize(135,135);
        this.image = new Image(pictureFile.getImageFile().toURI().toURL().toString(),135,135,true,true);

        this.imageView = new ImageView(image);
        this.pictureName = new Text(pictureFile.getImageName());
        this.setText(pictureName.getText());
        this.setGraphic(imageView);
        addPictureListener();
    }
    public void setSmall()throws MalformedURLException{
        //this.setPadding(new Insets(1,1,1,1));
        this.setPrefSize(100,100);
        this.image = new Image(pictureFile.getImageFile().toURI().toURL().toString(),100,100,true,true);
        this.imageView = new ImageView(image);
        this.setText(pictureName.getText());
        this.setGraphic(imageView);
    }
    public void setMedium()throws MalformedURLException{
        //this.setPadding(new Insets(1,1,1,1));
        this.setPrefSize(150,150);
        this.image = new Image(pictureFile.getImageFile().toURI().toURL().toString(),150,150,true,true);
        this.imageView = new ImageView(image);
        this.setText(pictureName.getText());
        this.setGraphic(imageView);
    }
    public void setBig()throws MalformedURLException{
        //this.setPadding(new Insets(1,1,1,1));
        this.setPrefSize(250,220);
        this.image = new Image(pictureFile.getImageFile().toURI().toURL().toString(),250,220,true,true);
        this.imageView = new ImageView(image);
        this.setText(pictureName.getText());
        this.setGraphic(imageView);
    }
    public File getImageFile() {
        return this.pictureFile.getImageFile();
    }
    public PictureFile getPictureFile() {
        return pictureFile;
    }
    public String getURL() {
        try {
            return this.pictureFile.getImageFile().toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ImageView getImageView() {
        return this.imageView;
    }
    private void addPictureListener(){
        PictureInstance pictureInstance = this;
        selected.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if(selected.get()) {
                    pictureInstance.setStyle("-fx-background-color:red;");
                    // PictureInstance.getSelectedPictures().add(pictureInstance);
                    // PictureInstance.getSelectedPictureFiles().remove(pictureInstance.getImageFile());

                }else {
                    pictureInstance.setStyle("-fx-background-color:transparent;");
                    // PictureInstance.getSelectedPictures().remove(pictureInstance);
                    //  PictureInstance.getSelectedPictureFiles().remove(pictureInstance.getImageFile());
                }
            }
        });
        this.setOnMouseEntered((MouseEvent e) -> {
            this.setStyle("-fx-background-color:linear-gradient(to bottom,#666666 1%,  #666666 98%);");//to bottom,#ADFF2F 1%,  #a7a7a7 98%
        });
        this.setOnMouseExited((MouseEvent e) -> {
            if(selected.get()) {
                pictureInstance.setStyle("-fx-background-color:red;");

            }else {
                pictureInstance.setStyle("-fx-background-color:transparent;");
            }

        });
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(pictureInstance instanceof PictureInstance){
                    if(event.isControlDown()==false){

                        if(event.getButton()!= MouseButton.SECONDARY||((PictureInstance)pictureInstance).selected.getValue())
                            mainUI.picturename = pictureName.getText();
                        if(flag)
                            mainUI.expand();
                        mainUI.picturesize = pictureFile.length()/1024;
                        mainUI.setImageview(getImageView().getImage());
                        if(event.getClickCount()==1&&event.getButton()==MouseButton.PRIMARY) {
                            ((PictureInstance) pictureInstance).setSelected(!((PictureInstance) pictureInstance).selected.get());
                            if(pre!=null)
                            {((PictureInstance) pre).setSelected(!((PictureInstance) pre).selected.get());}
                            pre=pictureInstance;
                        }
                        else if(event.getClickCount()>=2&&event.getButton()==MouseButton.PRIMARY){
                            ((PictureInstance) pictureInstance).setSelected(true);
                            ChangeService.file=pictureFile.getImageFile();
                            ChangeService.set_id();
                            new OpenAction();
                        }

                    }
                    else if(event.isControlDown()&&event.getButton()==MouseButton.PRIMARY){
                        ((PictureInstance) pictureInstance).setSelected(!((PictureInstance) pictureInstance).selected.get());
                    }
                }
            }
        });
    }
    public void setSelected(boolean value) {
        boolean istrue = selected.get();
        selected.set(value);
        if (selected.get() && !istrue)
            selectedPictures.add(this);
        else if (istrue && !selected.get()){
            selectedPictures.remove(this);//System.out.println(selectedPictures.size());
        }

    }
    public static  ArrayList<PictureInstance> getSelectedPictures(){
        return  selectedPictures;
    }
    public static  ArrayList<PictureInstance> getCutedPictures(){
        return  cutedPictures;
    }
    public static ArrayList<File>getSelectedPictureFiles(){
        return selectedPictureFiles;
    }
    public static void clearSelected() {
        for (PictureInstance pNode : selectedPictures) {
            pNode.selected.set(false);
        }
        selectedPictures.removeAll(selectedPictures);
    }
}
