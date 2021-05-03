package com.geekhive.studentsoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.holidaylist.HolidayList;
import com.geekhive.studentsoft.beans.notification.Notification;

public class NotificationAdapter extends RecyclerView.Adapter {

    Context context;
    Notification notification;
    public NotificationAdapter(Context context,Notification notification) {
        this.context = context;
        this.notification = notification;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return  notification.getResult().getMessage().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout llClass;
        TextView tvClass,tv_date,tv_details;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            llClass = itemView.findViewById(R.id.llClass);
            tvClass = itemView.findViewById(R.id.tvClass);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_details = itemView.findViewById(R.id.tv_details);
        }

        public void bindView(int position){
            tvClass.setText("Name :"+notification.getResult().getMessage().get(position).getName());
            tv_date.setText("Date :"+notification.getResult().getMessage().get(position).getDate()+"\n"+"Time :"+
                    notification.getResult().getMessage().get(position).getTime());
            tv_details.setText("Details :"+notification.getResult().getMessage().get(position).getDetails());
        }

        @Override
        public void onClick(View view) {

        }
    }

}
