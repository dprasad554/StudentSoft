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
import com.geekhive.studentsoft.beans.gethomework.GetHomeWork;

public class StudentHWAdapter extends RecyclerView.Adapter {

    Context context;
    GetHomeWork getHomeWork;
    public StudentHWAdapter(Context context,GetHomeWork getHomeWork) {
        this.context = context;
        this.getHomeWork = getHomeWork;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shw_item_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
        ((ListViewHolder)holder).tvClass.setText(getHomeWork.getResult().getMessage().get(0).getHomework().get(position).getSubject());
        ((ListViewHolder)holder).tvHWDue.setText("Due Date: "+getHomeWork.getResult().getMessage().get(0).getHomework().get(position).getSubmissionDate());
        ((ListViewHolder)holder).tv_details.setText("Details: "+getHomeWork.getResult().getMessage().get(0).getHomework().get(position).getDetails());
        ((ListViewHolder)holder).tv_title.setText("Title: "+getHomeWork.getResult().getMessage().get(0).getHomework().get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return getHomeWork.getResult().getMessage().get(0).getHomework().size() ;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout llClass;
        TextView tvClass,tvHWDue,tv_details,tv_title;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            llClass = itemView.findViewById(R.id.llClass);
            tvClass = itemView.findViewById(R.id.tvHMS);
            tvHWDue = itemView.findViewById(R.id.tvHWDue);
            tv_details = itemView.findViewById(R.id.tv_details);
            tv_title = itemView.findViewById(R.id.tv_title);
        }

        public void bindView(int position){
        }

        @Override
        public void onClick(View view) {
        }
    }

    /*public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        historyList.clear();
        if (charText.length() == 0) {
            historyList.addAll(arraylist);
        } else {
            for (Message wp : arraylist) {

                //String name = wp.getManf_name() + wp.getModel_name();
                String name2 = wp.getPhoneNumber();
                String name3 = wp.getGuestName();

                if (wp.getPhoneNumber().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getPhoneNumber().toLowerCase(Locale.getDefault())
                        .contains(charText) || name2.toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getGuestName().toLowerCase(Locale.getDefault())
                        .contains(charText) || name3.toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    historyList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }*/
}
