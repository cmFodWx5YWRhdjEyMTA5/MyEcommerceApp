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
import com.example.helvi.myecommercedemo.activities.model.Category;

import java.util.ArrayList;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.RViewHolder> implements View.OnClickListener {

    //    PersonalInfo[] categories;
    private ArrayList<Category> categories;
    public String TAG = "";


   private final LayoutInflater layoutInflater;

    //define interface
    public static interface OnRecyclerViewItemClickListener
    {
        void onItemClick(View view , String data);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
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
       final TextView _ID, name, description;
       final ImageView image;

        public RViewHolder(View itemView)
        {
            super(itemView);
            this._ID =  itemView.findViewById(R.id.category_id);
            this.name =  itemView.findViewById(R.id.category_name);
            this.image =  itemView.findViewById(R.id.category_image);
            this.description =  itemView.findViewById(R.id.category_description);
        }
    }

    public CategoryAdapter(Context context, ArrayList<Category> categories){
        this.categories = categories;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.category_view, parent, false);
        RViewHolder rViewHolder = new RViewHolder(view);
        view.setOnClickListener(this);
        return rViewHolder;
    }

    @Override
    public void onBindViewHolder(final RViewHolder holder, int position) {
        holder._ID.setText(categories.get(position).ID);
        holder.name.setText(categories.get(position).CatagoryName);
        holder.description.setText(categories.get(position).CatagoryDiscription);
        holder.image.setImageBitmap(categories.get(position).CatagoryImage);
        holder.itemView.setTag(categories.get(position).ID);
    }
    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void notifyData(ArrayList<Category> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.categories = myList;
        notifyDataSetChanged();
    }

}

