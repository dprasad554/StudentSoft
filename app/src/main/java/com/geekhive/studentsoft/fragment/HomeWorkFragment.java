package com.geekhive.studentsoft.fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.TeacherDashboard;
import com.geekhive.studentsoft.beans.addhomework.AddHomework;
import com.geekhive.studentsoft.beans.edithomework.EditHomeWork;
import com.geekhive.studentsoft.beans.homework.Homework;
import com.geekhive.studentsoft.beans.homework.Homework_;
import com.geekhive.studentsoft.beans.homeworklistresponce.HomeworkListResponce;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeWorkFragment extends Fragment implements OnResponseListner {


    //BaseActivityInterface setToolBarTitle;
    String dateS;
    DatePickerDialog.OnDateSetListener setListenerEnd;
    ConnectionDetector mDetector;
    String classname, section, subject, title, details;
    Homework homework;
    Homework_ homework_;
    EditText et_details, et_title;
    TextView tv_upload,tvTo;
    HomeworkListResponce homeworkListResponce;
    String edit_title,edit_details;

    public HomeWorkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_work, container, false);

        mDetector = new ConnectionDetector(getActivity());
        classname = getArguments().getString("classname");
        section = getArguments().getString("section");
        subject = getArguments().getString("subject");
        edit_title = getArguments().getString("title");
        edit_details = getArguments().getString("details");



        System.out.println("edit_title::::"+edit_title);

        TextView tv_classname = view.findViewById(R.id.tv_classname);
        TextView tv_section = view.findViewById(R.id.tv_section);
        System.out.println("classname::::::" + classname);


        tv_upload = view.findViewById(R.id.tv_upload);
        tv_classname.setText("Class Name :" + classname);
        tv_section.setText("Section :" + section);
        et_details = view.findViewById(R.id.et_details);
        et_title = view.findViewById(R.id.et_title);

        TextView tvFrom = view.findViewById(R.id.tv_from);
        tvTo = view.findViewById(R.id.tv_to);
        LinearLayout toDate = view.findViewById(R.id.ll_end_date);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd");
        dateS = mdformat.format(calendar.getTime());
        tvTo.setText(dateS);

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerEnd, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            }
        });
        setListenerEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                tvTo.setText(date);
            }
        };


       /* if(title != null){
            title = edit_title;
            System.out.println("edit_title::::"+edit_title);
        }else {
            title =  et_title.getText().toString();
        }if(details != null){
            details = edit_details;
        }else {
            details = et_details.getText().toString();
        }*/


        //et_title.setText(title);
        et_details.setText(edit_details);
        et_title.setText(edit_title);
        CalllistService();
        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(edit_title !=null || edit_details != null){
                   EditCallService();
               }else {
                   CallService();
               }

            }
        });
        return view;
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            homework = new Homework();
            homework_ = new Homework_();
            homework.setClass_(classname);
            homework.setSection(section);
            homework.setSubject(subject);

            if (!et_details.getText().toString().equals("") || !et_title.getText().toString().equals("")) {
                homework_.setDetails(et_details.getText().toString());
                homework_.setTitle(et_title.getText().toString());
                homework_.setSubmissionDate(tvTo.getText().toString());
                homework.setHomework(homework_);
            } else {
                SnackBar.makeText(getActivity(), "Please Enter Title and Description", SnackBar.LENGTH_SHORT).show();
            }
            new WebServices(this).AddHomework(WebServices.SM_Services,
                    WebServices.ApiType.addhomework, homework,Prefs.getPrefUserAuthenticationkey(getActivity()));
        } else {
            SnackBar.makeText(getActivity(), "Something went wrong", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }

    private void CalllistService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Homeworklist(WebServices.SM_Services,
                    WebServices.ApiType.homeworklist,classname,section,subject,Prefs.getPrefUserAuthenticationkey(getActivity()));
        } else {
            SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }
        return;
    }

    private void EditCallService() {
        if (this.mDetector.isConnectingToInternet()) {
            homework = new Homework();
            homework_ = new Homework_();
            homework.setClass_(classname);
            homework.setSection(section);
            homework.setSubject(subject);

            if (!et_details.getText().toString().equals("") || !et_title.getText().toString().equals("")) {
                homework_.setDetails(et_details.getText().toString());
                homework_.setTitle(et_title.getText().toString());
                homework_.setSubmissionDate(tvTo.getText().toString());
                homework.setHomework(homework_);
            } else {
                SnackBar.makeText(getActivity(), "Please Enter Title and Description", SnackBar.LENGTH_SHORT).show();
            }
            new WebServices(this).EditHomework(WebServices.SM_Services,
                    WebServices.ApiType.edithomework, homework,Prefs.getPrefUserAuthenticationkey(getActivity()));
        } else {
            SnackBar.makeText(getActivity(), "Something went wrong", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.edithomework) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final EditHomeWork editHomeWork = (EditHomeWork) response;

                if (!isSucces || editHomeWork == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(getActivity(), "Home Work Edited", SnackBar.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), TeacherDashboard.class));
                }
            }
        }if (apiType == WebServices.ApiType.addhomework) {
            if (!isSucces) {
                //SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final AddHomework addHomework = (AddHomework) response;

                if (!isSucces || addHomework.getResult().getMessage() == null) {
                    SnackBar.makeText(getActivity(), "homework already assigned", SnackBar.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), TeacherDashboard.class));
                    getActivity().finish();
                } else {
                    SnackBar.makeText(getActivity(), "Home Work Added", SnackBar.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), TeacherDashboard.class));
                    getActivity().finish();

                }
            }
        } if (apiType == WebServices.ApiType.homeworklist) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                homeworkListResponce = (HomeworkListResponce) response;

                if (!isSucces || homeworkListResponce.getResult() == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {

                }
            }
        }
    }
}
