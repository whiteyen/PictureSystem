package main.java.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class UserService {
        public static boolean user_match(String user,String password){
            boolean flag=false;
            Properties prop = new Properties();
            try {
                if (new File("user.properties").exists()) {
                    InputStream in = new BufferedInputStream(new FileInputStream("user.properties"));
                    prop.load(in);
                    Iterator<String> it = prop.stringPropertyNames().iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        if(key.equals(user)){
                            if(password.equals(prop.getProperty(key))){
                                flag=true;
                            }
                        }
                    }
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(flag)return true;
            else return false;
        }
}
