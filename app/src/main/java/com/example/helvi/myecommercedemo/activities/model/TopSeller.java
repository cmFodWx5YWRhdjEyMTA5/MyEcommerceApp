package com.example.helvi.myecommercedemo.activities.model;


public class TopSeller
{
    public String SellerId;
    public String SellerName;
    public String SellerDeal;
    public String SellerRating;
    public String Image;

    public TopSeller(String id, String name, String deal, String rating,String Image)
    {
        this.SellerId = id;
        this.SellerName = name;
        this.SellerDeal = "Deal: " + deal;
        this.SellerRating = "Rating: " + rating;
        this.Image=Image;

    }

}
