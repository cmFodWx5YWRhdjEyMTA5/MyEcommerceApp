package com.example.helvi.myecommercedemo.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.model.Order;

import java.util.ArrayList;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.RViewHolder> implements View.OnClickListener{

    private ArrayList<Order> orders;

    private LayoutInflater layoutInflater;

    //define interface
    @SuppressWarnings("UnnecessaryInterfaceModifier")
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }
    private OrderAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OrderAdapter.OnRecyclerViewItemClickListener listener) {
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
      TextView id, name, quantity, status, prize;

        public RViewHolder(View itemView) {
            super(itemView);
            this.id =  itemView.findViewById(R.id.cart_product_id);
            this.name =  itemView.findViewById(R.id.cart_product_name);
            this.quantity =  itemView.findViewById(R.id.cart_product_quantity);
            this.prize =  itemView.findViewById(R.id.cart_product_prize);
            this.status =  itemView.findViewById(R.id.order_status);
        }
    }

    public OrderAdapter(Context context, ArrayList<Order> products){
        this.orders = products;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = layoutInflater.inflate(R.layout.order_view, parent, false);
        RViewHolder rViewHolder = new RViewHolder(view);
        view.setOnClickListener(this);
        return rViewHolder;
    }

    @Override
    public void onBindViewHolder(OrderAdapter.RViewHolder holder, int position)
    {
        holder.id.setText(orders.get(position).OrderID);
        holder.name.setText(orders.get(position).ItemName);
        holder.quantity.setText(orders.get(position).ItemQuantity);
        holder.prize.setText(orders.get(position).FinalPrice);
        holder.status.setText(orders.get(position).OrderStatus);
        holder.itemView.setTag(orders.get(position).OrderID);

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void notifyData(ArrayList<Order> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.orders = myList;
        notifyDataSetChanged();
    }
}
