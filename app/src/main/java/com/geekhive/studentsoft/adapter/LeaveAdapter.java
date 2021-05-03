package com.geekhive.studentsoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;

public class LeaveAdapter extends RecyclerView.Adapter {

    Context context;
    GetStudentById getStudentById;
    public LeaveAdapter(Context context,GetStudentById getStudentById) {
        this.context = context;
        this.getStudentById = getStudentById;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_item_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
        ((ListViewHolder)holder).tvClass.setText("Date:"+getStudentById.getResult().getMessage().getAttendance().get(position).getDate());
        /*((ListViewHolder)holder).reason.setText("Reason:"+getStudentById.getResult().getMessage().getAttendance().get(position).getReason());
        ((ListViewHolder)holder).status.setText("Status:"+getStudentById.getResult().getMessage().getAttendance().get(position).getStatus());
   */ }

    @Override
    public int getItemCount() {
        return getStudentById.getResult().getMessage().getAttendance().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout llClass;
        TextView tvClass,reason,status;
        ImageView cancel_leave;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            llClass = itemView.findViewById(R.id.llClass);
            tvClass = itemView.findViewById(R.id.tvClass);
            cancel_leave = itemView.findViewById(R.id.cancel_leave);
        }

        public void bindView(int position){
        }

        @Override
        public void onClick(View view) {
        }
    }
}
