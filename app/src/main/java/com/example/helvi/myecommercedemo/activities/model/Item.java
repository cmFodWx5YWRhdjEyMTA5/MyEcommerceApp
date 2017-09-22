package com.example.helvi.myecommercedemo.activities.model;

public class Item {

    public String id;
    public String productName;
    public String quantity;
    public String prize;
    public String discription;
    public android.graphics.Bitmap image;

    public Item(String id, String productName, String quantity, String prize, String discription) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.prize = prize;
        this.discription = discription;
     }

    public void setImageBitmap(android.graphics.Bitmap image){
        this.image = image;
    }
}
