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
import com.geekhive.studentsoft.utils.SnackBar;

import java.util.Calendar;

public class TeacherTTAdapter extends RecyclerView.Adapter {

    Context context;
    GetStudentTimetable getStudentTimetable;
    public TeacherTTAdapter(Context context,GetStudentTimetable getStudentTimetable) {
        this.context = context;
        this.getStudentTimetable=getStudentTimetable;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_tt_item_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
        Calendar calander = Calendar.getInstance();
        int day = calander.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                SnackBar.makeText(context,"Holiday", SnackBar.LENGTH_SHORT).show();
                break;
            case Calendar.MONDAY:
                if(getStudentTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getType().equals("class")){
                    ((ListViewHolder)holder).tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getSubject());
                    ((ListViewHolder)holder).tv_teachername.setText("By "+getStudentTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getTeacherName());
                    ((ListViewHolder)holder).tv_time.setText("Start Time "+getStudentTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime());
                }
                break;
            case Calendar.TUESDAY:
                if(getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getType().equals("class")){
                    ((ListViewHolder)holder).tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getSubject());
                    ((ListViewHolder)holder).tv_teachername.setText("By "+getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getTeacherName());
                    ((ListViewHolder)holder).tv_time.setText("Start Time "+getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime());
                }
                break;
            case Calendar.WEDNESDAY:
                if(getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getType().equals("class")){
                    ((ListViewHolder)holder).tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getSubject());
                    ((ListViewHolder)holder).tv_teachername.setText("By "+getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getTeacherName());
                    ((ListViewHolder)holder).tv_time.setText("Start Time "+getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime());
                }
                break;
            case Calendar.THURSDAY:
                if(getStudentTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getType().equals("class")){
                    ((ListViewHolder)holder).tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getSubject());
                    ((ListViewHolder)holder).tv_teachername.setText("By "+getStudentTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getTeacherName());
                    ((ListViewHolder)holder).tv_time.setText("Start Time "+getStudentTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime());
                }
                break;
            case Calendar.FRIDAY:
                if(getStudentTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getType().equals("class")){
                    ((ListViewHolder)holder).tvClass.setText(getStudentTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getSubject());
                    ((ListViewHolder)holder).tv_teachername.setText("By "+getStudentTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getTeacherName());
                    ((ListViewHolder)holder).tv_time.setText("Start Time "+getStudentTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime());
                }
                break;
            case Calendar.SATURDAY:
                SnackBar.makeText(context,"Holiday", SnackBar.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return getStudentTimetable.getResult().getMessage().getTimetable().getMonday().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout llClass;
        TextView tvClass,tv_teachername,tv_time;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            llClass = itemView.findViewById(R.id.llClass);
            tvClass = itemView.findViewById(R.id.tvClass);
            tv_teachername = itemView.findViewById(R.id.tv_teachername);
            tv_time = itemView.findViewById(R.id.tv_time);
        }

        public void bindView(int position){

        }

        @Override
        public void onClick(View view) {
        }
    }
}
