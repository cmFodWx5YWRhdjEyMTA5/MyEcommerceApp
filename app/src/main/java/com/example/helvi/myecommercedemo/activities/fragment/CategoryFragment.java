package com.example.helvi.myecommercedemo.activities.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.model.Category;
import com.example.helvi.myecommercedemo.activities.adapters.CategoryAdapter;
import com.example.helvi.myecommercedemo.activities.net.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/*
* Category page which contains all
* categories of products in Recycler view.
* @author:Helvi
* */
public class CategoryFragment extends Fragment {
    public String TAG = "";
    public static String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php";

    Category[] categories;

    ArrayList<Category> items = new ArrayList<>();

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private int currentId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_category, null);

         requestCategoryData();

         recyclerView= view.findViewById(R.id.my_recycler_view);
         recyclerView.setHasFixedSize(false);
         layoutManager=new LinearLayoutManager(getActivity());
         recyclerView.setLayoutManager(layoutManager);

         adapter=new CategoryAdapter(getActivity(),items);
         recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CategoryAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data)
            {
                currentId=Integer.valueOf(data);
                Bundle bundleCategory=new Bundle();
                bundleCategory.putInt("categoryId", currentId);
                Fragment subCateFrag = new SubCategoryFragment();
                subCateFrag.setArguments(bundleCategory);
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, subCateFrag);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
         return view;


    }


    void requestCategoryData()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try{
                    if (items.isEmpty()){
                        JSONArray contacts = jsonObject.getJSONArray("Category");
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);

                            String ID = c.getString("Id");
                            String name = c.getString("CatagoryName");
                            String description = c.getString("CatagoryDiscription");
                            String image = c.getString("CatagoryImage");

                            final Category category = new Category(ID, name, description);
                            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                            imageLoader.get(image, new ImageLoader.ImageListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(TAG, "Image Load Error: " + error.getMessage());
                                }
                                @Override
                                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                                    if (response.getBitmap() != null)
                                    {
                                        category.setImageBitmap(response.getBitmap());
                                        adapter.notifyData(items);
                                    }
                                }
                            });
                            items.add(category);

                        }
                    }

                }catch (Exception e){
                    System.out.println(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "ERROR" + volleyError.getMessage());
                Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }
}


