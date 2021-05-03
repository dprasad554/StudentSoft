package com.geekhive.studentsoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.EventGallery;
import com.geekhive.studentsoft.activities.EventsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventgalleryAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<String> images ;
    public EventgalleryAdapter(Context context,  ArrayList<String> images) {
        this.context = context;
        this.images =  images;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_gellary_list,parent,false);
        return new EventgalleryAdapter.ListViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((EventgalleryAdapter.ListViewHolder)holder).bindView(position);
        String url = "http://"+images.get(position);
        if (!url.equals("")){
            Picasso.get()
                    .load(url)//download URL
                    .error(R.drawable.event_school)//if failed
                    .into(((ListViewHolder)holder).g_image);
        }
    }
    @Override
    public int getItemCount() {
        return images.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        RelativeLayout llClass;
        ImageView g_image;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            llClass = (RelativeLayout) itemView.findViewById(R.id.llClass);
            g_image = (ImageView) itemView.findViewById(R.id.g_image);
        }

        public void bindView(int position){
        }

        @Override
        public void onClick(View view) {
        }
    }
}
