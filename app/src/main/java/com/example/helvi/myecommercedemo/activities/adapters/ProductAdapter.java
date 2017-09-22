package com.example.helvi.myecommercedemo.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.RViewHolder> implements View.OnClickListener{

    private  ArrayList<Product> products;

    private  Context context;
    private  LayoutInflater layoutInflater;

    //define interface
    @SuppressWarnings("UnnecessaryInterfaceModifier")
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }
    private ProductAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(ProductAdapter.OnRecyclerViewItemClickListener listener) {
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
         TextView id, name, quantity, description, price;
         ImageView image;

        public RViewHolder(View itemView) {
            super(itemView);
            this.id =  itemView.findViewById(R.id.products_id);
            this.name =  itemView.findViewById(R.id.products_name);
            this.quantity =  itemView.findViewById(R.id.products_quantity);
            this.price =  itemView.findViewById(R.id.products_prize);
            this.image =  itemView.findViewById(R.id.products_image);
            this.description =  itemView.findViewById(R.id.products_description);
        }
    }

    public ProductAdapter(Context context, ArrayList<Product> products){
        this.products = products;
        layoutInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.product_view, parent, false);
        RViewHolder rViewHolder = new RViewHolder(view);
        view.setOnClickListener(this);
        return rViewHolder;
    }

    @Override
    public void onBindViewHolder(final RViewHolder holder, int position) {
        holder.id.setText(products.get(position).ID);
        holder.name.setText(products.get(position).ProductName);
        holder.quantity.setText(products.get(position).Quantity);
        holder.price.setText(products.get(position).Price);
        holder.description.setText(products.get(position).Description);
         holder.itemView.setTag(products.get(position).ID);
        Picasso.with(context)
                .load(products.get(position).Image)
                .into(holder.image);

    }
    @Override
    public int getItemCount() {
        return products.size();
    }

}

