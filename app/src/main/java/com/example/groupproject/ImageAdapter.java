package com.example.groupproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import entities.UserProfile;

public class ImageAdapter extends RecyclerView.Adapter {

    private Integer[] mData;
    private LayoutInflater inflater;
    private Integer[] bookIDs;
    private String[] bookTitles;
    private String[] bookStatus;
    private String[] bookOwner;
    private ItemClickListener mClicListener;
    UserProfile userprofile;

    public ImageAdapter(Context context, Integer[] data, String[] bTitles,
                        String[] bStatus, String[] bOwners,Integer[] bIDs){
        inflater = LayoutInflater.from(context);
        mData = data;
        bookTitles = bTitles;
        bookStatus = bStatus;
        bookOwner = bOwners;
        bookIDs = bIDs;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerviewitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).imageView.setImageResource(mData[position]);
        ((ViewHolder)holder).lblBookName.setText(bookTitles[position]);
        ((ViewHolder)holder).lblBookStatus.setText(bookStatus[position]);
        ((ViewHolder)holder).lblBookOwner.setText("Owned By : " +bookOwner[position]);
        ((ViewHolder)holder).lblBookID.setText(String.valueOf(bookIDs[position]));

        if(((ViewHolder)holder).lblBookStatus.getText().equals("Available"))
        {
            ((ViewHolder)holder).lblBookStatus.setTextColor(Color.parseColor("#4d932a"));
        }
        else
        {
            ((ViewHolder)holder).lblBookStatus.setTextColor(Color.parseColor("#ff0000"));
        }
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    void setClickListener(ItemClickListener itemClickListener){
        this.mClicListener = itemClickListener;
    }

    Integer getImage(int id){
        return mData[id];
    }

    //inner class
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        ImageView imageView;
        TextView lblBookName;
        TextView lblBookStatus;
        TextView lblBookOwner;
        TextView lblBookID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgSmall);
            lblBookName = itemView.findViewById(R.id.lblBookName);
            lblBookOwner = itemView.findViewById(R.id.lblBookOwner);
            lblBookStatus = itemView.findViewById(R.id.lblBookStatus);
            lblBookID = itemView.findViewById(R.id.lblBookID);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClicListener != null)
            {
                int position = getAdapterPosition();
                mClicListener.onItemClick(v,getAdapterPosition());
            }


        }


    }

    //inner interface
    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
