package com.geekhive.studentsoft.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.AttendanceManagement;
import com.geekhive.studentsoft.activities.ExamDetailsActivity;
import com.geekhive.studentsoft.activities.ExamListActivity;
import com.geekhive.studentsoft.activities.StudentDashboard;
import com.geekhive.studentsoft.beans.getallexam.GetAllExamlist;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.getstudentbyid.Result_;

import java.util.ArrayList;
import java.util.List;

public class ExamlistAdapter extends RecyclerView.Adapter {

    Context context;
    Dialog dialogSuccess;
    GetAllExamlist getAllExamlist;
    int sum = 0;
    Result_ result_;
    private ArrayList<Integer> marks = new ArrayList<Integer>();
    String Total_mark;
    List<Result_> examResults;
    public ExamlistAdapter(Context context, GetAllExamlist getAllExamlist) {
        this.context = context;
        this.getAllExamlist = getAllExamlist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_list_adapter, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).cv_results_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(context, ExamDetailsActivity.class);
                context.startActivity(j);
            }
        });
        if(getAllExamlist.getResult().getMessage().get(position).getName() != null){
            ((ListViewHolder) holder).tv_exmname.setText("Exam Name : "+getAllExamlist.getResult().getMessage().get(position).getName());
        }

        if(getAllExamlist.getResult().getMessage().get(position).getEndDate() != null){
            ((ListViewHolder) holder).end_date.setText("End Date : "+getAllExamlist.getResult().getMessage().get(position).getEndDate());
        }

        if(getAllExamlist.getResult().getMessage().get(position).getStartDate() != null){
            ((ListViewHolder) holder).start_date.setText("Start Date : "+getAllExamlist.getResult().getMessage().get(position).getStartDate());
        }



        /*((ListViewHolder) holder).tv_mark.setText(getAllExamlist.getResult().getMessage().get(position).getName());
        ((ListViewHolder) holder).tv_exmname.setText(getAllExamlist.getResult().getMessage().get(position).getStartDate());
        ((ListViewHolder) holder).end_date.setText(getAllExamlist.getResult().getMessage().get(position).getEndDate());
    */}

    @Override
    public int getItemCount() {
        return getAllExamlist.getResult().getMessage().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout llClass;
        TextView tv_exmname, tv_mark,end_date,start_date;
        CardView cv_results_ad;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            llClass = itemView.findViewById(R.id.llClass);
            tv_exmname = itemView.findViewById(R.id.tv_exmname);
            tv_mark = itemView.findViewById(R.id.tv_mark);
            cv_results_ad = itemView.findViewById(R.id.cv_results_ad);
            end_date = itemView.findViewById(R.id.end_date);
            start_date = itemView.findViewById(R.id.start_date);
        }

        @Override
        public void onClick(View view) {
        }
    }


    private void Initializepopup() {
        dialogSuccess = new Dialog(context);
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_student_result_rv);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializdeletePopup(List<Result_> examResultsList) {
        dialogSuccess.setContentView(R.layout.popup_student_result_rv);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();

        RecyclerView vrStudentResults = dialogSuccess.findViewById(R.id.vr_student_rp);
        int width = context.getResources().getDisplayMetrics().widthPixels - 100;
        int height = context.getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 2);
        vrStudentResults.setLayoutManager(linearLayoutManager);
        StudentPopupResultsAdapter studentResultsAdapter = new StudentPopupResultsAdapter(context,examResultsList);
        vrStudentResults.setAdapter(studentResultsAdapter);

        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }
}
