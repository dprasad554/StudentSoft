package com.geekhive.studentsoft.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.getstudentbyid.ExamResult;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.getstudentbyid.Result_;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentResultsAdapter extends RecyclerView.Adapter {

    Context context;
    Dialog dialogSuccess;
    GetStudentById getStudentById;
    int sum = 0;
    Result_ result_;
    private ArrayList<Integer> marks = new ArrayList<Integer>();
    String Total_mark;
    List<Result_> examResults;
    public StudentResultsAdapter(Context context, GetStudentById getStudentById) {
        this.context = context;
        this.getStudentById = getStudentById;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sresults_item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).tv_exmname.setText(getStudentById.getResult().getMessage().getExamResult().get(position).getExam());
        ((ListViewHolder) holder).cv_results_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                examResults = getStudentById.getResult().getMessage().getExamResult().get(position).getResults();
                Initializepopup();
                initializdeletePopup(examResults);
            }
        });
        marks.add(Integer.valueOf(String.valueOf(getStudentById.getResult().getMessage().getExamResult().get(0).getResults().get(0).getMark())));
        sum = sum+marks.get(position);
        System.out.println("sum::::::"+sum);
        Total_mark = String.valueOf(sum);
        ((ListViewHolder) holder).tv_mark.setText(Total_mark);
    }

    @Override
    public int getItemCount() {
        return getStudentById.getResult().getMessage().getExamResult().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout llClass;
        TextView tv_exmname, tv_mark;
        CardView cv_results_ad;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            llClass = itemView.findViewById(R.id.llClass);
            tv_exmname = itemView.findViewById(R.id.tv_exmname);
            tv_mark = itemView.findViewById(R.id.tv_mark);
            cv_results_ad = itemView.findViewById(R.id.cv_results_ad);
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
