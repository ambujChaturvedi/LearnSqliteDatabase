package com.example.user.learnsqlitedatabase.model;

/**
 * Created by user on 23-02-2018.
 */

public class Contacts {
    String name;
    byte[] imageByte;


    public Contacts() {
    }

    public Contacts(String name, byte[] imageByte) {
        this.name = name;
        this.imageByte = imageByte;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }
}
