package com.example.hp.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
private int id;
    private String Name;
    private String Image;
    public Category(){
    }

    public Category(int id, String name, String image) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
