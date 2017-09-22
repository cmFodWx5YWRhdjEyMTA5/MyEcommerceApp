package com.example.helvi.myecommercedemo.activities.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.model.Product;
import com.example.helvi.myecommercedemo.activities.adapters.ProductAdapter;
import com.example.helvi.myecommercedemo.activities.net.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class ProductFragment extends Fragment
{
    private String TAG = "";
    private static String urlTemp = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_product.php?Id=";
    private String url;

    private ArrayList<Product> items = new ArrayList<>();

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private int currentId,categoryId;
    private int productId;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        categoryId=getArguments().getInt("itemId");
        url=new String(urlTemp+categoryId);
        View view = inflater.inflate(R.layout.fragment_category, null);

        recyclerView= view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(false);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Method calling
        requestCategoryData();

        return view;

    }


    void requestCategoryData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                // Log.d(TAG, jsonObject.toString());

                try {
                    JSONArray contacts = jsonObject.getJSONArray("Product");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String ID = c.getString("Id");

                        String ProductName = c.getString("ProductName");
                        String Quantity = c.getString("Quantity");
                        String Price = c.getString("Prize");
                        String Description = c.getString("Discription");
                        String Image = c.getString("Image");

                        final Product cty = new Product(ID, ProductName, Quantity, Price, Description, Image);
                        items.add(cty);
                        adapter = new ProductAdapter(getContext(), items);
                        recyclerView.setAdapter(adapter);

                    }

                    adapter.setOnItemClickListener(new ProductAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, String data) {
                                currentId = Integer.valueOf(data);
                                Bundle bundle = new Bundle();

                                for (Product lst : items)
                                {
                                    if (lst.ID.equals(data)) {
                                        bundle.putString("productId", lst.ID);
                                        bundle.putString("Quantity", lst.Quantity);
                                        bundle.putString("Prize", lst.Price);
                                        bundle.putString("Discription", lst.Description);
                                        bundle.putString("Image", lst.Image);
                                        bundle.putString("ProductName",lst.ProductName);
                                    }
                                }

                                ItemFragment itemFragment = new ItemFragment();
                                itemFragment.setArguments(bundle);
                                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.container, itemFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();

                            }
                    });
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(TAG, "ERROR" + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}


