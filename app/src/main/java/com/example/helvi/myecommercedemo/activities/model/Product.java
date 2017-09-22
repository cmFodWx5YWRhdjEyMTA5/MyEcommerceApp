package com.example.helvi.myecommercedemo.activities.model;


public class Product {
    public String ID;
    public String ProductName;
    public String Quantity;
    public String Price;
    public String Description;
    public String Image;

    public Product(String ID, String ProductName, String Quantity, String Price, String Description,String Image)
    {
        this.ID = ID;
        this.ProductName = ProductName;
        this.Quantity =  Quantity;
        this.Price =   Price;
        this.Description = "Description: " + Description;
        this.Image = Image;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}





