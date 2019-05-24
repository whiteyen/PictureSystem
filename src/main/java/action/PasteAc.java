package main.java.action;

import main.java.controller.MainUIController;
import main.java.model.PictureInstance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class PasteAc {
    MainUIController mainUIController;
    public PasteAc(MainUIController mainUI) {
        this.mainUIController = mainUI;

        ArrayList<File> files = PictureInstance.getSelectedPictureFiles();
        if (files.size() <= 0) {
            return;
        }
        for(File oldFile : files) {

            String newName = Pasterename(mainUI.theFilePath,oldFile.getName());
            System.out.println(mainUI.getFiles().get(0).getName());
            File newFile = new File(mainUI.theFilePath+File.separator+newName);
           try {
                newFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(newFile.exists()) {
                try {
                    copyFile(oldFile,newFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            mainUI.update();
        }
    }
    private void copyFile(File fromFile, File toFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(fromFile);
        FileOutputStream outputStream = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int byteRead;
        while ((byteRead = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, byteRead);
        }
        inputStream.close();
        outputStream.close();

    }
    private String Pasterename(String theFilePath, String name) {
        String newName = name;
        File fatherPathFile = new File(theFilePath);
        File[] filesInFatherPath = fatherPathFile.listFiles();
        for (File fileInFatherPath : filesInFatherPath) {
            String fileName = fileInFatherPath.getName();
            int cmp = newName.compareTo(fileName);
            if (cmp == 0) {
                String str = null;
                int end = newName.lastIndexOf("."), start = newName.lastIndexOf("_副本");
                if (start != -1) {
                    str = newName.substring(start, end);
                    int num = 1;
                    try {
                        num = Integer.parseInt(str.substring(str.lastIndexOf("_副本") + 3)) + 1;
                        int cnt = 0, d = num - 1;
                        while (d != 0) {
                            d /= 10;
                            cnt++;
                        }
                        newName = newName.substring(0, end - cnt) + num + newName.substring(end);
                    } catch (Exception e) {
                        newName = newName.substring(0, end) + "_副本1" + newName.substring(end);
                    }

                } else {
                    newName = newName.substring(0, end) + "_副本1" + newName.substring(end);
                }
            }
        }
        return newName;
    }


}