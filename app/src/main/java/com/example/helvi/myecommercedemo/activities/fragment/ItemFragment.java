package com.example.helvi.myecommercedemo.activities.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.model.Item;
import com.example.helvi.myecommercedemo.activities.util.MyDB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ItemFragment extends Fragment
{
    private TextView txt_product_item,txt_quantity_item,txt_prize_item,txt_description_item;
    private Button btn_addtocart,btn_add,btn_sub;
    private ImageView img_product;
    public String TAG = "";

    SharedPreferences spref;

    EditText edit_quantity;
    ArrayList<Item> items = new ArrayList<>();
    private int currentId,categoryId;
    String dataId;

    public String productId,imageId,Quantity,Prize,Discription,Name;

    private String input_quantity;
    private int i,quantity, availableQuantity;
    private int price;
    private MyDB myHelper;
    private SQLiteDatabase db;
    ArrayList<String> data = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        productId=getArguments().getString("productId");
        Name=getArguments().getString("ProductName");
        Quantity=getArguments().getString("Quantity");
        Prize=getArguments().getString("Prize");
        Log.d("Prize",Prize);
        Discription=getArguments().getString("Discription");
        imageId=getArguments().getString("Image");

        price=Integer.valueOf(Prize);
        quantity=Integer.valueOf(Quantity);

        myHelper=new MyDB(getContext());
        db=myHelper.getWritableDatabase();

        spref=  getActivity().getSharedPreferences("file1", Context.MODE_PRIVATE);
        Log.i("Image",imageId);

        View view = inflater.inflate(R.layout.item_view,container,false);

        txt_product_item=view.findViewById(R.id.textViewProductName);
        txt_quantity_item=view.findViewById(R.id.textViewQuantity);
        txt_prize_item=view.findViewById(R.id.textViewPrize);
        txt_description_item=view.findViewById(R.id.textViewDescription);
        img_product=view.findViewById(R.id.imageViewProduct);
        btn_addtocart=view.findViewById(R.id.buttonAddCart);
        btn_add=view.findViewById(R.id.buttonAdd);
        btn_sub=view.findViewById(R.id.buttonSub);
        edit_quantity=view.findViewById(R.id.editTextQuantity);




        txt_product_item.setText(Name);
        txt_quantity_item.setText(Quantity);
        txt_prize_item.setText(Prize);
        txt_description_item.setText(Discription);
        Picasso.with(getContext())
                .load(imageId)
                .into(img_product);
        edit_quantity.setText("1");


        input_quantity= edit_quantity.getText().toString();
        quantity = Integer.parseInt(input_quantity);
         i=Integer.parseInt(input_quantity);

        availableQuantity=Integer.parseInt(Quantity);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(quantity<availableQuantity)
                {
                    quantity = i++;
                    edit_quantity.setText(quantity + "");
                    price = Integer.parseInt(Prize) * quantity;
                    Log.i("AddPrice:", price + "");
                }
                else
                {
                    Toast.makeText(getContext(),"Product OutofStock",Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_sub.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(quantity>=2)
                {
                    quantity = --i;
                    edit_quantity.setText(quantity + "");
                    price = Integer.parseInt(Prize) * quantity;
                    Log.i("SubPrice:", price + "");
                }
                else
                {
                    Toast.makeText(getContext(),"Quantity must be 1",Toast.LENGTH_LONG).show();
                }
            }
        });




        btn_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

              ContentValues cv=new ContentValues();
                cv.put(myHelper.PID,productId);
                cv.put(myHelper.PNAME,Name);
                cv.put(myHelper.PQUANTITY,String.valueOf(quantity));
                cv.put(myHelper.PRIZE,String.valueOf(price));
                cv.put(myHelper.IMAGE,imageId);

                Log.d("Data",productId+price);
               // db.insert(myHelper.TABLE,null,cv);
                db.insertWithOnConflict(myHelper.TABLE, null,cv, SQLiteDatabase.CONFLICT_IGNORE);

                Toast.makeText(getContext(),"Added to Cart:"+Name,Toast.LENGTH_LONG).show();

            }
        });


        return view;
    }

  }

