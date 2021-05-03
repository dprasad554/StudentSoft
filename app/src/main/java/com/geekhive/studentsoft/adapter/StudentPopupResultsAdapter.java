package com.geekhive.studentsoft.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.getstudentbyid.Result_;

import java.util.List;

public class StudentPopupResultsAdapter extends RecyclerView.Adapter {

    Context context;
    Dialog dialogSuccess;
    List<Result_> examResults;
    public StudentPopupResultsAdapter(Context context, List<Result_> examResultsList) {
        this.context = context;
        this.examResults = examResultsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_student_results,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
        ((ListViewHolder)holder).vT_subj.setText(examResults.get(position).getSubjectName());
        ((ListViewHolder)holder).subj_mark.setText(String.valueOf(examResults.get(position).getMark()));
    }

    @Override
    public int getItemCount() {
        return examResults.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView vT_subj,subj_mark;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            vT_subj = itemView.findViewById(R.id.vT_subj);
            subj_mark = itemView.findViewById(R.id.subj_mark);

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
