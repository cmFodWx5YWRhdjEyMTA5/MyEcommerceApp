package com.example.helvi.myecommercedemo.activities.model;


    public class Order
{

        public String OrderID;
        public String ItemName;
        public String ItemQuantity;
        public String FinalPrice;
        public String OrderStatus;

        public Order(String ID, String ProductName, String Quantity, String Prize, String Description){
            this.OrderID = ID;
            this.ItemName = ProductName;
            this.ItemQuantity = "Quantity: " + Quantity;
            this.FinalPrice = "Final Price: " + Prize;
            this.OrderStatus = "Order Status: " + Description;
        }
    }


