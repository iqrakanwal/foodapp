package com.example.hp.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category  {
    private int id;
    private String Name;
    private String Image;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Category(){
    }

    public Category(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName(){
        return Name;
    }
    public void setName(String name)
    {
        Name = name;
    }
    public String getImage(){
        return Image;
    }
    public void setImage(String image)
    {
        Image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
