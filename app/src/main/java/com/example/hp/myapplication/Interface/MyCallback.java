package com.example.hp.myapplication.Interface;

import com.example.hp.myapplication.Model.Category;
import com.example.hp.myapplication.Model.Cheese;
import com.example.hp.myapplication.Model.Food;

import java.util.ArrayList;

public interface MyCallback {
    void onCallback(ArrayList<Category> animals);
}
