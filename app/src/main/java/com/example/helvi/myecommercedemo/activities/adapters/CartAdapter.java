package com.example.helvi.myecommercedemo.activities.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
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
import com.example.helvi.myecommercedemo.activities.model.Cart;
import com.example.helvi.myecommercedemo.activities.util.MyDB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import static com.example.helvi.myecommercedemo.activities.util.MyDB.PID;
import static com.example.helvi.myecommercedemo.activities.util.MyDB.TABLE;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.RViewHolder> implements View.OnClickListener {

    ArrayList<Cart> carts;
    public String TAG = "";

    Context context;
    LayoutInflater layoutInflater;
    public MyDB myHelper;
    public SQLiteDatabase db;
    //define interface
    public static interface OnRecyclerViewItemClickListener
    {
        void onItemClick(View view , String data);
    }
    private CartAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(CartAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view,String.valueOf(view.getTag()));
        }
        else{
            Log.e("CLICK", "ERROR");
        }
    }


    public static class RViewHolder extends RecyclerView.ViewHolder{
        TextView ID, name,price,quantity,delete;
        ImageView image;
        Button btn_add,btn_sub;
        EditText edit_quantity;
        public RViewHolder(View itemView)
        {
            super(itemView);
            this.ID = itemView.findViewById(R.id.cart_product_id);
            this.name =  itemView.findViewById(R.id.cart_product_name);
            this.price =  itemView.findViewById(R.id.cart_product_price);
            this.quantity =  itemView.findViewById(R.id.cart_product_quantity);
            this.image = itemView.findViewById(R.id.cart_product_image);
            this.btn_add=  itemView.findViewById(R.id.button_add_cart);
            this.btn_sub= itemView.findViewById(R.id.button_add_sub);
            this.edit_quantity=  itemView.findViewById(R.id.editTextQuantityCart);
            this.delete= itemView.findViewById(R.id.editTextDelete);

        }
    }

    public CartAdapter(Context context, ArrayList<Cart> carts){
        this.carts = carts;
        this.context=context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public CartAdapter.RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.items_cart, parent, false);
        CartAdapter.RViewHolder rViewHolder = new CartAdapter.RViewHolder(view);

        myHelper=new MyDB(context);
        db=myHelper.getWritableDatabase();

        view.setOnClickListener(this);
        return rViewHolder;
    }

    @Override
    public void onBindViewHolder(final RViewHolder holder, final int position)
    {
        holder.ID.setText(carts.get(position).OrderID);
        holder.name.setText(carts.get(position).ItemName);
        holder.quantity.setText(carts.get(position).ItemQuantity);
        holder.price.setText(carts.get(position).FinalPrice);
        holder.itemView.setTag(carts.get(position).OrderID);

        Picasso.with(context)
                .load(carts.get(position).Image)
                .into(holder.image);

        holder.edit_quantity.setText(carts.get(position).ItemQuantity);


        final int posDel=Integer.valueOf(carts.get(position).OrderID);
        final int unitPrice=Integer.valueOf(carts.get(position).FinalPrice)/Integer.valueOf(carts.get(position).ItemQuantity);


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.i("IDHelvi",posDel+"");

               db.execSQL("DELETE FROM " + TABLE+ " WHERE "+PID+"='"+posDel+"'");
                carts.remove(position);
                CartAdapter.this.notifyDataSetChanged();

                Toast.makeText(context,"Item Removed from Cart",Toast.LENGTH_LONG).show();

            }
        });


        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                    int n=Integer.valueOf(holder.edit_quantity.getText().toString());


                    int number = Integer.valueOf(holder.edit_quantity.getText().toString()) + 1;
                    holder.edit_quantity.setText(number + "");

                    int priceChange= unitPrice*number;
                    String price=(priceChange+"");

                    db.execSQL("UPDATE " + myHelper.TABLE+ " SET "+myHelper.PRIZE+ "='"+price+"'"+" WHERE "+PID+"='"+posDel+"'");
                    db.execSQL("UPDATE " + myHelper.TABLE+ " SET "+myHelper.PQUANTITY+ "='"+number+"'"+" WHERE "+PID+"='"+posDel+"'");
                    holder.price.setText(priceChange+"");
                    Toast.makeText(context,"Record Updated",Toast.LENGTH_LONG).show();


            }
        });


        holder.btn_sub.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int n=Integer.valueOf(holder.edit_quantity.getText().toString());

                if(n>=2)
                {

                    int number = Integer.valueOf(holder.edit_quantity.getText().toString()) - 1;
                    holder.edit_quantity.setText(number + "");

                    int priceChange= unitPrice*number;
                    String price=(priceChange+"");

                    db.execSQL("UPDATE " + myHelper.TABLE+ " SET "+myHelper.PRIZE+ "='"+price+"'"+" WHERE "+PID+"='"+posDel+"'");
                    db.execSQL("UPDATE " + myHelper.TABLE+ " SET "+myHelper.PQUANTITY+ "='"+number+"'"+" WHERE "+PID+"='"+posDel+"'");

                    holder.price.setText(priceChange+"");
                    Toast.makeText(context,"Record Updated",Toast.LENGTH_LONG).show();

                 }
                else
                {

                    Toast.makeText(context,"Qunatity should be atleast 1",Toast.LENGTH_LONG).show();
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        return carts.size();
    }



}


