package com.example.helvi.myecommercedemo.activities.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.fragment.ItemFragment;


public class ShoppingActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        ItemFragment fragment1=new ItemFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft1=fragmentManager.beginTransaction();
        ft1.replace(R.id.fragmentCategory,fragment1);
        ft1.commit();

    }
}
