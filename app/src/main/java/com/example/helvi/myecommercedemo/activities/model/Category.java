package com.example.helvi.myecommercedemo.activities.model;


public class Category {

    public String ID;
    public String CatagoryName;
    public String CatagoryDiscription;
    public android.graphics.Bitmap CatagoryImage;

    public Category(String ID, String name, String description){
        this.ID = ID;
        this.CatagoryName = name;
        this.CatagoryDiscription = "Description: " + description;
    }
    public void setImageBitmap(android.graphics.Bitmap image){
        this.CatagoryImage = image;
    }
}
