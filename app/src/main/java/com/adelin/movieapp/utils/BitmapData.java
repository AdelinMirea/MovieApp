package com.adelin.movieapp.utils;

import java.io.Serializable;

public class BitmapData implements Serializable {
    private byte[] byteArray;

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }
}
