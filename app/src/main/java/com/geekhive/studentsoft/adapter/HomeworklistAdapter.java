package com.geekhive.studentsoft.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.homeworklistresponce.HomeworkListResponce;
import com.geekhive.studentsoft.fragment.HomeWorkFragment;

public class HomeworklistAdapter extends RecyclerView.Adapter {

    Context context;
    HomeworkListResponce homeworkList;
    public HomeworklistAdapter(Context context, HomeworkListResponce homeworkList) {
        this.context = context;
        this.homeworkList=homeworkList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_work_list,parent,false);
        return new HomeworklistAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HomeworklistAdapter.ListViewHolder)holder).bindView(position);
        ((HomeworklistAdapter.ListViewHolder)holder).tvClass.setText(homeworkList.getResult().getMessage().get(position).getSubject());
        ((HomeworklistAdapter.ListViewHolder)holder).title.setText(homeworkList.getResult().getMessage().get(position).getTitle());
        ((HomeworklistAdapter.ListViewHolder)holder).details.setText(homeworkList.getResult().getMessage().get(position).getDetails());
        ((HomeworklistAdapter.ListViewHolder)holder).llClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Fragment fragment = new HomeWorkFragment();
               *//* Bundle args = new Bundle();
                args.putString("classname",class_spinner.getSelectedItem().toString());
                args.putString("section",section_spinner.getSelectedItem().toString() );
                args.putString("subject",subject_spinner.getSelectedItem().toString() );
                fragment.setArguments(args);*//*
                loadFragment(fragment);*/
            }
        });

    }
    @Override
    public int getItemCount() {
        return homeworkList.getResult().getMessage().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvClass,details,title;
        LinearLayout llClass;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClass = itemView.findViewById(R.id.tvClass);
            details = itemView.findViewById(R.id.details);
            title = itemView.findViewById(R.id.title);
            llClass = itemView.findViewById(R.id.llClass);

        }

        public void bindView(int position){

        }

        @Override
        public void onClick(View view) {
        }
    }
}