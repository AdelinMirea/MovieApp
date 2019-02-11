package com.adelin.movieapp.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveUserData {

    private User user;
    private String fileName = "userData.txt";
    private Context context;

    public SaveUserData(Context context) {
        user = new User();
        this.context = context;
        this.load();
    }

    public User getUser() {
        return user;
    }

    public void updatePhoto(Bitmap photo) {
        user.setPhoto(photo);
        save();
    }

    public void update(String name, String mail) {
        user.setUserName(name);
        user.setUserMail(mail);
        save();
    }

    private void save() {
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
//            outputStream.write((userName + "`" + userMail).getBytes());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            user = (User) objectInputStream.readObject();
            if (user == null) {
                user = new User();
            }
            if (user.getUserName() == null) {
                user.setUserName("name");
            }
            if (user.getUserMail() == null) {
                user.setUserMail("mail");
            }
            if(user.getPhoto() == null){
                user.initializePhoto();
            }
            objectInputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
