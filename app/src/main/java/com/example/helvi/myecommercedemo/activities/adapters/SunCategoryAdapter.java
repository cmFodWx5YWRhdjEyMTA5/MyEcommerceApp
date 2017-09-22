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
import com.example.helvi.myecommercedemo.activities.model.SubCategory;

import java.util.ArrayList;


public class SunCategoryAdapter extends RecyclerView.Adapter<SunCategoryAdapter.RViewHolder> implements View.OnClickListener
{

    private ArrayList<SubCategory> sub_categories;
    private LayoutInflater layoutInflater;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }
    private SunCategoryAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(SunCategoryAdapter.OnRecyclerViewItemClickListener listener) {
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
        TextView id, name, description;
        ImageView image;

        public RViewHolder(View itemView) {
            super(itemView);
            this.id =  itemView.findViewById(R.id.sub_category_id);
            this.name =  itemView.findViewById(R.id.sub_category_name);
            this.image =  itemView.findViewById(R.id.sub_category_image);
            this.description =  itemView.findViewById(R.id.sub_category_description);
        }
    }

    public SunCategoryAdapter(Context context, ArrayList<SubCategory> sub_categories){
        this.sub_categories = sub_categories;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.subcategory_view, parent, false);
        RViewHolder rViewHolder = new RViewHolder(view);
        view.setOnClickListener(this);
        return rViewHolder;
    }

    @Override
    public void onBindViewHolder(final RViewHolder holder, int position) {
        holder.id.setText(sub_categories.get(position).ID);
        holder.name.setText(sub_categories.get(position).SubCatagoryName);
        holder.description.setText(sub_categories.get(position).SubCatagoryDiscription);
        holder.image.setImageBitmap(sub_categories.get(position).CatagoryImage);
        holder.itemView.setTag(sub_categories.get(position).ID);
    }
    @Override
    public int getItemCount() {
        return sub_categories.size();
    }

    public void notifyData(ArrayList<SubCategory> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.sub_categories = myList;
        notifyDataSetChanged();
    }

}

