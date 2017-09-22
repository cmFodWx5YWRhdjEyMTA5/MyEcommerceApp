package com.example.helvi.myecommercedemo.activities.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.model.Order;
import com.example.helvi.myecommercedemo.activities.adapters.OrderAdapter;
import com.example.helvi.myecommercedemo.activities.net.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity
{
    private String ORDERURL = "http://rjtmobile.com/ansari/shopingcart/androidapp/order_history.php?mobile=";
    ArrayList<Order> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public String mobileShed;

    private int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhistory);


        SharedPreferences prefs = getSharedPreferences("file1", MODE_PRIVATE);
        mobileShed=prefs.getString("mobile",null);
        ORDERURL=ORDERURL+mobileShed;

        init();
        recyclerView = (RecyclerView) findViewById(R.id.order_recycler_view);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OrderAdapter(this, items);
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
        adapter.setOnItemClickListener(new OrderAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                currentId = Integer.valueOf(data);
                registerForContextMenu(view);
            }
        });

    }

    private void init(){
        final String TAG_ORDERHISTORY = "ORDERHISTORY";
        ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,ORDERURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG_ORDERHISTORY, response);
                        try {
                            JSONObject historyObj = new JSONObject(response);
                            JSONArray ordersJsonArray = historyObj.getJSONArray("Order History");
                            for (int i = 0; i < ordersJsonArray.length(); i++) {
                                JSONObject c = ordersJsonArray.getJSONObject(i);
                                String ID = c.getString("OrderID");

                                String ProductName = c.getString("ItemName");
                                String Quantity = c.getString("ItemQuantity");
                                String Prize = c.getString("FinalPrice");

                                if (c.getString("OrderStatus").equals("1")){
                                    String Status = "Order  Confirm";
                                    final Order cty = new Order(ID, ProductName, Quantity, Prize, Status);
                                    items.add(cty);
                                    adapter.notifyData(items);
                                }
                                else if (c.getString("OrderStatus").equals("2")){
                                    String Status = "Order Dispatch";
                                    final Order cty = new Order(ID, ProductName, Quantity, Prize, Status);
                                    items.add(cty);
                                    adapter.notifyData(items);
                                }
                                else if (c.getString("OrderStatus").equals("3")){
                                    String Status = "Order On the Way";
                                    final Order cty = new Order(ID, ProductName, Quantity, Prize, Status);
                                    items.add(cty);
                                    adapter.notifyData(items);
                                }
                                else {
                                    String Status = "Order Delivered";
                                    final Order cty = new Order(ID, ProductName, Quantity, Prize, Status);
                                    items.add(cty);
                                    adapter.notifyData(items);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG_ORDERHISTORY, error.getMessage(), error);

            }
        }) {

        };
        AppController.getInstance().addToRequestQueue(stringRequest, TAG_ORDERHISTORY);
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
            Intent logoutIntent=new Intent(OrderHistoryActivity.this,HomeActivity.class);
            startActivity(logoutIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

