package com.geekhive.studentsoft.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.StationaryListActivity;
import com.geekhive.studentsoft.activities.StudentDashboard;
import com.geekhive.studentsoft.beans.stationarycategory.StationaryCategory;
import com.squareup.picasso.Picasso;

public class StationaryAdapter extends RecyclerView.Adapter {

    Context context;
    StationaryCategory stationaryCategory;
    String url;
    public StationaryAdapter(Context context, StationaryCategory stationaryCategory) {
        this.context = context;
        this.stationaryCategory = stationaryCategory;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stationary_items,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
        ((ListViewHolder) holder).VL_psd_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StationaryListActivity.class);
                String category = stationaryCategory.getResult().getMessage().get(position).getName();
                intent.putExtra("main_category",category);
                intent.putExtra("s_ownerlogin","");
                context.startActivity(intent);
            }
        });

        ((ListViewHolder) holder).vT_subj.setText(stationaryCategory.getResult().getMessage().get(position).getName());

        if(stationaryCategory.getResult().getMessage().get(position).getMediaUrl().equals("") ||
                stationaryCategory.getResult().getMessage().get(position).getMediaUrl() != null) {
            //url = "http://" + productbyID.getResult().getMessage().getMedia().get(position).getUrl();
            url ="http://" +stationaryCategory.getResult().getMessage().get(position).getMediaUrl();
        }
        if (!url.equals("") ){
            Picasso.get()
                    .load(url)//download URL
                    .error(R.drawable.book)//if failed
                    .into(((ListViewHolder) holder).iv_stationary);
        }
    }

    @Override
    public int getItemCount() {
        return stationaryCategory.getResult().getMessage().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout VL_psd_menu;
        ImageView iv_stationary;
        TextView vT_subj;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            VL_psd_menu = (LinearLayout) itemView.findViewById(R.id.VL_psd_menu);
            vT_subj = (TextView)itemView.findViewById(R.id.vT_subj);
            iv_stationary = (ImageView) itemView.findViewById(R.id.iv_stationary);
        }

        public void bindView(int position){

        }

        @Override
        public void onClick(View view) {
        }
    }
}
