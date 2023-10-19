package com.example.hp.myapplication.Common;

import com.example.hp.myapplication.Model.Food;

public class DataHolder {
    private static DataHolder instance = null;
    private Food sharedData;

    private DataHolder() {
        // Private constructor to prevent instantiation
    }

    public static DataHolder getInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public Food getSharedData() {
        return sharedData;
    }

    public void setSharedData(Food data) {
        sharedData = data;
    }
}

