package com.example.helvi.myecommercedemo.activities.model;

public class SubCategory
{
    public String ID;
    public String SubCatagoryName;
    public String SubCatagoryDiscription;
    public android.graphics.Bitmap CatagoryImage;
    public SubCategory(String ID, String SubCatagoryName, String SubCatagoryDiscription){
        this.ID = ID;
        this.SubCatagoryName = SubCatagoryName;
        this.SubCatagoryDiscription = "Category Description: " + SubCatagoryDiscription;
    }
    public void setImageBitmap(android.graphics.Bitmap image){
        this.CatagoryImage = image;
    }



}


