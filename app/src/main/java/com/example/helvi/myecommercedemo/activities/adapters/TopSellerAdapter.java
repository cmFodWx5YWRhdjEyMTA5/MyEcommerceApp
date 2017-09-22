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
import com.example.helvi.myecommercedemo.activities.model.TopSeller;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TopSellerAdapter extends RecyclerView.Adapter<TopSellerAdapter.RViewHolder> implements View.OnClickListener{

    private ArrayList<TopSeller> sellers;
    public String TAG = "";


    private Context context;
    private LayoutInflater layoutInflater;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }
    private TopSellerAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(TopSellerAdapter.OnRecyclerViewItemClickListener listener) {
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
        TextView id, name, deal, rating;
        ImageView image;

        public RViewHolder(View itemView) {
            super(itemView);
            this.id =  itemView.findViewById(R.id.cart_product_id);
            this.name =  itemView.findViewById(R.id.top_seller_name);
            this.deal =  itemView.findViewById(R.id.cart_prize_deal);
            this.rating =  itemView.findViewById(R.id.top_seller_rating);
            this.image =  itemView.findViewById(R.id.cart_product_image);
        }
    }

    public TopSellerAdapter(Context context, ArrayList<TopSeller> sellers){
        this.sellers = sellers;
        this.context=context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.items_topseller, parent, false);
        RViewHolder rViewHolder = new RViewHolder(view);
        view.setOnClickListener(this);
        return rViewHolder;
    }

    @Override
    public void onBindViewHolder(final RViewHolder holder, int position) {
        holder.id.setText(sellers.get(position).SellerId);
        holder.name.setText(sellers.get(position).SellerName);
        holder.deal.setText(sellers.get(position).SellerDeal);
        holder.rating.setText(sellers.get(position).SellerRating);
       // holder.image.setImageBitmap(sellers.get(position).SellerLogo);
        holder.itemView.setTag(sellers.get(position).SellerId);
        Picasso.with(context)
                .load(sellers.get(position).Image)
                .into(holder.image);
    }
    @Override
    public int getItemCount() {
        return sellers.size();
    }


    public void notifyData(ArrayList<TopSeller> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.sellers = myList;
        notifyDataSetChanged();
    }
}

