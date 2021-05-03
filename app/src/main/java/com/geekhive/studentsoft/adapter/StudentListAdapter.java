package com.geekhive.studentsoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.SchoolDetailsActivity;
import com.geekhive.studentsoft.beans.schoollist.Message;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter {

    Context context;

    public StudentListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist_adapter, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout school_name;
        TextView tv_studentname;
        ImageView iv_categoryImage;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_studentname = (TextView) itemView.findViewById(R.id.tv_studentname);
        }

        public void bindView(int position) {

        }

        @Override
        public void onClick(View view) {
        }
    }
}