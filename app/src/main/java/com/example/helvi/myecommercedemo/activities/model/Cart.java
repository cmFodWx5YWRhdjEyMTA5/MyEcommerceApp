package com.example.helvi.myecommercedemo.activities.model;


public class Cart
{

    public String OrderID;
    public String ItemName;
    public String ItemQuantity;
    public String FinalPrice;
    public String Image;

    public Cart(String ID, String ProductName, String Quantity, String Prize, String Image) {
        this.OrderID = ID;
        this.ItemName = ProductName;
        this.ItemQuantity = Quantity;
        this.FinalPrice =  Prize;
        this.Image = Image;

    }
}
