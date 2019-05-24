package main.java.model;

import java.io.File;
import java.net.URL;

public class PictureFile {
    private String imageName;
    private File imageFile;
    private URL imageURL;
    public PictureFile(File imageFile){
        this.imageFile = imageFile;
        imageName = imageFile.getName();
        if(imageName.equals("")){
            imageName = imageFile.getPath();
        }
    }
    public PictureFile(String imagePath){
        this(new File(imagePath));
    }
    public PictureFile[] listPictures(){
        File [ ]files =this.imageFile.listFiles();
        if(files==null||files.length==0)return null;
        PictureFile[] pictureFiles = new PictureFile[files.length];
        for(int i = 0; i<files.length;i++) {
            pictureFiles[i] = new PictureFile(files[i]);
        }
        return pictureFiles;
    }
    public URL toURL() {
        return imageURL;
    }

    public boolean isPicture() {
        if(imageName.toLowerCase().endsWith(".jpg")||
                imageName.toLowerCase().endsWith(".jpge")||
                imageName.toLowerCase().endsWith(".png")||
                imageName.toLowerCase().endsWith(".bmp")||
                imageName.toLowerCase().endsWith(".gif")	) {
            return true;
        }
        return false;
    }

    public boolean isDirectory() {
        return imageFile.isDirectory();
    }

    public boolean isFile() {
        return imageFile.isFile();
    }

    public boolean isHidden() {
        return imageFile.isHidden();
    }

    public long length() {
        return imageFile.length();
    }

    public String toString() {
        return imageName;
    }
    public void renameFile(String path,String oldname,String newname) {
        if (!oldname.equals(newname)) {//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(path + "/" + oldname);
            File newfile = new File(path + "/" + newname);
            if (!oldfile.exists()) {
                return;//重命名文件不存在
            }
            if (newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname + "已经存在！");
            else {
                oldfile.renameTo(newfile);
            }
        } else {
            System.out.println("新文件名和旧文件名相同...");
        }
    }
    //------------------getter and setter-----------

    public File getImageFile() {
        return imageFile;
    }
    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName){this.imageName=imageName;}
    public void setImageFile(File imageFile){this.imageFile=imageFile;}
}
