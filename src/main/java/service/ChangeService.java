package main.java.service;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.model.PictureFile;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ChangeService {
    public static Stage stage;
    public static ArrayList<File> files;
    public static File file ;
    public static ImageView origin,change;
    public static double originHight,originWidth;
    private static  int id=-1;
    public  static String getFile()  {
        try {
            return  file.toURI().toURL().toString();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean next(){
        if(id==files.size()-1){
            return false;
        }
        else {
            id++;
            file=files.get(id);
            return true;
        }
    }
    public static boolean pre(){
        if(id==0){
            return false;
        }
        else {
            id--;
            file = files.get(id);
            return true;

        }

    }



    public static void set_id(){
        for(int i = 0; i <files.size();i++){
            if(file.equals(files.get(i)))id=i;
        }
    }
}
