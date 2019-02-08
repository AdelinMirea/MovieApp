package com.adelin.movieapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveUserData {

    private String userName;
    private String userMail;
    private Bitmap photo;
    private String fileName = "userData.txt";
    private String photoFile = "photo.txt";
    private Context context;

    public enum Type{
        NAME, MAIL, PHOTO
    }

    public SaveUserData(Context context){
        this.context = context;
        this.load();
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void update(Type type, String string){
        switch (type){
            case NAME:
                this.userName = string;
            case MAIL:
                this.userMail = string;
            default:
                break;
        }
        save();
    }

    public void updatePhoto(Bitmap photo){
        this.photo = photo;
        savePhoto();
    }

    public void update(String name, String mail){
        this.userName = name;
        this.userMail = mail;
        save();
    }

    private void save(){
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write((userName + "`" + userMail).getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        savePhoto();
    }

    private void savePhoto(){
        try(FileOutputStream out = context.openFileOutput(photoFile, Context.MODE_PRIVATE)){
            if(photo != null) {
                photo.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load(){
        try {
            FileInputStream inputStream = context.openFileInput(fileName);

            int chr;
            StringBuilder builder = new StringBuilder();
            while((chr = inputStream.read()) != -1){
                builder.append((char)chr);
            }
            this.userName = builder.toString().split("`")[0];
            this.userMail = builder.toString().split("`")[1];
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(FileInputStream fis = context.openFileInput(photoFile)){
            File file = new File(photoFile);
            if(file.exists()){
                photo = BitmapFactory.decodeStream(fis);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
