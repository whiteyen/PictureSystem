package main.java.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.java.model.alert;
import main.java.service.ChangeService;

import java.awt.*;
import java.io.*;
import java.util.Iterator;
import java.util.Properties;

public class RegisterController {
    @FXML
    private JFXTextField user_name;
    @FXML
    private JFXPasswordField user_password;
    @FXML
    private JFXPasswordField password;
    public void register(){
        String name = user_name.getText();
        String pass = user_password.getText();
        if(name.equals("")||pass.equals("")||password.getText().equals(""))
            alert.showAlert("用户名或密码没输入完整","请输入", ChangeService.stage);
        else if(!(user_password.getText().equals(password.getText())))
            alert.showAlert("第一次密码与第二次密码不一致","请重新输入", ChangeService.stage);
        else {
            boolean flag=false;
            Properties prop = new Properties();
            try {
                if (new File("user.properties").exists()) {
                    InputStream in = new BufferedInputStream(new FileInputStream("user.properties"));
                    prop.load(in);
                    Iterator<String> it = prop.stringPropertyNames().iterator();
                        if(prop.getProperty(name)==null){
                                    try {
                                        FileOutputStream oFile = new FileOutputStream("user.properties", true);
                                        prop.setProperty(name, pass);
                                        prop.store(oFile, null);
                                        oFile.close();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                         }
                        else alert.showAlert("用户名已存在","请重新输入用户名", ChangeService.stage);
                    in.close();
                }
                else {
                    try {
                        FileOutputStream oFile = new FileOutputStream("user.properties", false);
                        prop.setProperty(name, pass);
                        prop.store(oFile, null);
                        oFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/fxml/login.fxml"));

            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(LoginController.class.getResource("/resources/css/LoginApp.css").toExternalForm());

            ChangeService.stage.setScene(scene);
            ChangeService.stage.setTitle("picture-system1.0");
            ChangeService.stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void back(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/fxml/login.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(LoginController.class.getResource("/resources/css/LoginApp.css").toExternalForm());
            ChangeService.stage.setScene(scene);
            ChangeService.stage.setTitle("picture-system1.0");
            ChangeService.stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
