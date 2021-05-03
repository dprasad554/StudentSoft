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
import com.geekhive.studentsoft.beans.schoollist.SchoolList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SchoollistAdapter extends RecyclerView.Adapter {

    Context context;
    List<Message> schools;

    public SchoollistAdapter(Context context, List<Message> schools) {
        this.context = context;
        this.schools = schools;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);

        ((ListViewHolder) holder).school_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SchoolDetailsActivity.class);
                intent.putExtra("dbkey", schools.get(position).getDbKey());
                intent.putExtra("Sname", schools.get(position).getName());
                context.startActivity(intent);
            }
        });
        ((ListViewHolder) holder).tvClass.setText(schools.get(position).getName());
        ((ListViewHolder) holder).tv_location.setText("Address:" + schools.get(position).getAddress() +
                "\n" + "City :" + schools.get(position).getCity() + "\n" + "Pin  :" +
                schools.get(position).getPostalCode() + "\n" +
                "Email :" + schools.get(position).getEmail() + "\n" +
                "Mobile :" + schools.get(position).getMobileNumber() + "\n" +
                "Website :" + schools.get(position).getWebsiteUrl());
        if(schools.get(position).getLogo() !=null){
            if(!schools.get(position).getLogo().equals("")){
                String url = "http://"+schools.get(position).getLogo();
                if (!url.equals("")){
                    Picasso.get()
                            .load(url)//download URL
                            .error(R.drawable.event_school)//if failed
                            .into(((ListViewHolder) holder).iv_categoryImage);
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return schools.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout school_name;
        TextView tvClass, tv_location;
        ImageView iv_categoryImage;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            school_name = (LinearLayout) itemView.findViewById(R.id.school_name);
            tvClass = (TextView) itemView.findViewById(R.id.tvClass);
            tv_location = (TextView) itemView.findViewById(R.id.tv_location);
            iv_categoryImage = (ImageView)itemView.findViewById(R.id.iv_categoryImage);

        }

        public void bindView(int position) {

        }

        @Override
        public void onClick(View view) {
        }
    }
}
