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
import com.geekhive.studentsoft.beans.getstudenttimetable.GetStudentTimetable;

import java.util.ArrayList;
import java.util.Calendar;

public class TimeTableAdapter extends RecyclerView.Adapter {

    Context context;
    GetStudentTimetable getStudentTimetable;
    String day ;
    public TimeTableAdapter(Context context,GetStudentTimetable getStudentTimetable,String day) {
        this.context = context;
        this.getStudentTimetable = getStudentTimetable;
        this.day=day;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_item_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout llClass;
        TextView tvClass,tv_subjectname;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            llClass = itemView.findViewById(R.id.llClass);
            tvClass = itemView.findViewById(R.id.tvClass);
            tv_subjectname = itemView.findViewById(R.id.tv_subjectname);
        }

        public void bindView(int position){
            if (position % 2 == 1) {
                llClass.setBackgroundColor(context.getResources().getColor(R.color.gradient_1));
            } else {
                llClass.setBackgroundColor(context.getResources().getColor(R.color.inactive_dots));
            }
            if(day.equals("Monday")){
                if(getStudentTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getType().equals("class")){
                    tv_subjectname.setText(getStudentTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getSubject());
                    tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime() +" to "+
                            getStudentTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getEndTime());
                }else {
                    tv_subjectname.setText("Lunch Time");
                    tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime() +" to "+
                            getStudentTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getEndTime());

                }

            }if(day.equals("Tuesday")){
                if(getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getType().equals("class")){
                    tv_subjectname.setText(getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getSubject());
                    tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime() +" to "+
                            getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getEndTime());
                }else {
                    tv_subjectname.setText("Lunch Time");
                    tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime() +" to "+
                            getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getEndTime());

                }

            }if(day.equals("Wednesday")){
                if(getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getType().equals("class")){
                    tv_subjectname.setText(getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getSubject());
                    tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime() +" to "+
                            getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getEndTime());

                }else {
                    tv_subjectname.setText("Lunch Time");
                    tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime() +" to "+
                            getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getEndTime());

                }

            }if(day.equals("Thursday")){
                if(getStudentTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getType().equals("class")){
                    tv_subjectname.setText(getStudentTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getSubject());
                    tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime() +" to "+
                            getStudentTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getEndTime());

                }else {
                    tv_subjectname.setText("Lunch Time");
                    tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime() +" to "+
                            getStudentTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getEndTime());

                }

            }if(day.equals("Friday")){
                if(getStudentTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getType().equals("class")){
                    tv_subjectname.setText(getStudentTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getSubject());
                    tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime() +" to "+
                            getStudentTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getEndTime());

                }else {
                    tv_subjectname.setText("Lunch Time");
                    tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime() +" to "+
                            getStudentTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getEndTime());

                }

            }
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
