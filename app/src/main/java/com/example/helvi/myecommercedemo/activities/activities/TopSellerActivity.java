package com.example.helvi.myecommercedemo.activities.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.adapters.TopSellerAdapter;
import com.example.helvi.myecommercedemo.activities.model.TopSeller;
import com.example.helvi.myecommercedemo.activities.net.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TopSellerActivity extends AppCompatActivity
{

    public String TAG = "";
    public static String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_top_sellers.php?";

    ArrayList<TopSeller> items = new ArrayList<>();

    private RecyclerView recyclerView;
    private TopSellerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_seller);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_topseller);
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        objRequestMethod();
    }
    private void objRequestMethod() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d(TAG, jsonObject.toString());

                try{
                    JSONArray contacts = jsonObject.getJSONArray("Top Sellers");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("SellerId");
                        String name = c.getString("SellerName");
                        String deal = c.getString("SellerDeal");
                        String rating = c.getString("SellerRating");
                        String sellerlogo = c.getString("SellerLogo");

                        final TopSeller cty = new TopSeller(id, name, deal, rating,sellerlogo);

                        items.add(cty);




                    }
                    adapter = new TopSellerAdapter(getApplicationContext(), items);
                    recyclerView.setAdapter(adapter);

                }catch (Exception e){
                    System.out.println(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "ERROR" + volleyError.getMessage());


            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.cartmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout)
        {
            Intent logoutIntent=new Intent(TopSellerActivity.this,HomeActivity.class);
            startActivity(logoutIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

