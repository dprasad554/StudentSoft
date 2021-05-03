package com.geekhive.studentsoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.Homeworklist;
import com.geekhive.studentsoft.activities.SeatApplicationActivity;
import com.geekhive.studentsoft.beans.seatvacancy.SeatVacancyResponce;

public class SeatVacancyAdapter extends RecyclerView.Adapter {

    Context context;
    SeatVacancyResponce seatVacancy;
    public SeatVacancyAdapter(Context context, SeatVacancyResponce seatVacancy) {
        this.context = context;
        this.seatVacancy = seatVacancy;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vacany_item_layout,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
        ((ListViewHolder)holder).tvClass.setText("Class:"+seatVacancy.getResult().getMessage().get(position).getClassName());
        ((ListViewHolder)holder).tv_startdate.setText("Session starts from "+seatVacancy.getResult().getMessage().get(position).getStartApplication());
        ((ListViewHolder)holder).tv_available.setText("Available seats: "+seatVacancy.getResult().getMessage().get(position).getSeatsAvailable());
        ((ListViewHolder)holder).tv_enddate.setText("Last date of submission:"+seatVacancy.getResult().getMessage().get(position).getEndApplication());

    }

    @Override
    public int getItemCount() {
        return seatVacancy.getResult().getMessage().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout llClass;
        TextView tvClass,tv_startdate,tv_available,tv_enddate;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            llClass = itemView.findViewById(R.id.llClass);
            tvClass = itemView.findViewById(R.id.tvClass);
            tv_startdate = itemView.findViewById(R.id.tv_startdate);
            tv_enddate = itemView.findViewById(R.id.tv_enddate);
            tv_available = itemView.findViewById(R.id.tv_available);
        }

        public void bindView(int position){
            llClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Intent intent = new Intent(context, SeatApplicationActivity.class);
                    intent.putExtra("class", seatVacancy.getResult().getMessage().get(position).getClassName());
                    //intent.putExtra("class", seatVacancy.getResult().getMessage().get(position).getClassName());
                    context.startActivity(intent);*/
                }
            });
        }

        @Override
        public void onClick(View view) {
        }
    }
}
