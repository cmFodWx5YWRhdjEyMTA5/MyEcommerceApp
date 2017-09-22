package com.example.helvi.myecommercedemo.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.fragment.CategoryFragment;

public class ShopActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shop, menu);

        CategoryFragment fragment1=new CategoryFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft1=fragmentManager.beginTransaction();
        ft1.replace(R.id.container,fragment1);
        ft1.commit();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout)
        {
            Intent logoutIntent=new Intent(ShopActivity.this,HomeActivity.class);
            startActivity(logoutIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Home)
        {
            Intent homeIntent=new Intent(ShopActivity.this, ShopActivity.class);
            startActivity(homeIntent);
        }
        else if (id == R.id.Category)
        {
            Intent categoryIntent=new Intent(ShopActivity.this, ShopActivity.class);
            startActivity(categoryIntent);
        }

        else if (id == R.id.OrderHistory)
        {
            Intent orderIntent=new Intent(ShopActivity.this, OrderHistoryActivity.class);
            startActivity(orderIntent);

        }
        else if (id == R.id.TopSellers)
        {
            Intent topsellerIntent=new Intent(ShopActivity.this,TopSellerActivity.class);
            startActivity(topsellerIntent);

        }
        else if (id == R.id.Logout)
        {
            Intent logoutIntent=new Intent(ShopActivity.this,HomeActivity.class);
            startActivity(logoutIntent);
        }
        else if (id == R.id.AddToCart)
        {
            Intent cartIntent=new Intent(ShopActivity.this,CartActivity.class);
            startActivity(cartIntent);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
