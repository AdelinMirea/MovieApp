package com.adelin.movieapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class User implements Serializable {
    static final long serialVersionUID =-2837600818760228151L;
    private String userName;
    private String userMail;
    private BitmapData photo;


    public String getUserName() {
        return userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public Bitmap getPhoto() {
        if (photo == null || photo.getByteArray() == null){
            return null;
        }
        return BitmapFactory.decodeByteArray(photo.getByteArray(),0, photo.getByteArray().length);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public void setPhoto(Bitmap photo) {
        photo = Bitmap.createScaledBitmap(photo, 90, 90, false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        this.photo.setByteArray(stream.toByteArray());
    }

    public void initializePhoto(){
        this.photo = new BitmapData();
    }
}
