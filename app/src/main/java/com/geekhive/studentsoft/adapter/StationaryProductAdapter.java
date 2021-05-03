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
import com.geekhive.studentsoft.beans.stonaryprouct.StonaryProduct;
import com.squareup.picasso.Picasso;

public class StationaryProductAdapter extends RecyclerView.Adapter {

    Context context;
    String url;
    StonaryProduct stonaryProduct;
    public StationaryProductAdapter(Context context,StonaryProduct stonaryProduct) {
        this.context = context;
        this.stonaryProduct = stonaryProduct;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stationary_item_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
        ((ListViewHolder) holder).vT_name.setText(stonaryProduct.getResult().getMessage().get(position).getName());
        ((ListViewHolder) holder).vT_price.setText("Price :"+stonaryProduct.getResult().getMessage().get(position).getPrice());
        if(stonaryProduct.getResult().getMessage().get(position).getMediaUrls().equals("") ||
               stonaryProduct.getResult().getMessage().get(position).getMediaUrls().size() != 0) {
            //url = "http://" + productbyID.getResult().getMessage().getMedia().get(position).getUrl();
            url ="http://" +stonaryProduct.getResult().getMessage().get(position).getMediaUrls().get(0).toString();
        }
        if (!url.equals("") ){
            Picasso.get()
                    .load(url)//download URL
                    .error(R.drawable.book)//if failed
                    .into(((ListViewHolder) holder).vI_image);
        }
        ((ListViewHolder) holder).vL_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return stonaryProduct.getResult().getMessage().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout VL_psd_menu,vL_add_to_cart;
        ImageView vI_image;
        TextView vT_name,vT_price;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            VL_psd_menu = (LinearLayout) itemView.findViewById(R.id.VL_psd_menu);
            vT_name = (TextView)itemView.findViewById(R.id.vT_name);
            vT_price = (TextView)itemView.findViewById(R.id.vT_price);
            vI_image = (ImageView) itemView.findViewById(R.id.vI_image);
            vL_add_to_cart = (LinearLayout)itemView.findViewById(R.id.vL_add_to_cart);
        }

        public void bindView(int position){

        }

        @Override
        public void onClick(View view) {
        }
    }
}
