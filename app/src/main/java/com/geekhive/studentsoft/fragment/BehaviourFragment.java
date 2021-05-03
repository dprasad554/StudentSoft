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
import com.geekhive.studentsoft.beans.addbehaviour.AddBehaviour;
import com.geekhive.studentsoft.beans.addhomework.AddHomework;
import com.geekhive.studentsoft.beans.editbehaviour.EditBehaviour;
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

public class BehaviourFragment extends Fragment implements OnResponseListner {


    //BaseActivityInterface setToolBarTitle;
    ConnectionDetector mDetector;
    EditText note_heaad,note_Description;
    String classname,section,student,student_id,title, details,dateS,id,subject;
    TextView student_name,tv_class,tv_section,tvTo,tv_upload;
    DatePickerDialog.OnDateSetListener setListenerEnd;

    public BehaviourFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDetector = new ConnectionDetector(getActivity());
        View view = inflater.inflate(R.layout.fragment_behaviour, container, false);

        classname = getArguments().getString("classname");
        section = getArguments().getString("section");
        student = getArguments().getString("student");
        student_id = getArguments().getString("student_id");
        subject = getArguments().getString("subject");
        id = getArguments().getString("id");
        note_heaad = view.findViewById(R.id.note_heaad);
        note_Description = view.findViewById(R.id.note_Description);
        student_name = view.findViewById(R.id.student_name);
        tv_class = view.findViewById(R.id.tv_class);
        tv_section = view.findViewById(R.id.tv_section);
        tvTo = view.findViewById(R.id.tv_to);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd");
        dateS = mdformat.format(calendar.getTime());
        tvTo.setText(dateS);
        System.out.println("classname::::::" + classname);

        student_name.setText("Student Name: "+student);
        tv_class.setText("Class: "+classname);
        tv_section.setText("Section: "+section);
        tv_upload = view.findViewById(R.id.tv_upload);
        LinearLayout toDate = view.findViewById(R.id.ll_end_date);
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
        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id != null){
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
            if (!note_heaad.getText().toString().equals("") || !note_Description.getText().toString().equals("")) {
                new WebServices(this).AddBehaviour(WebServices.SM_Services,
                        WebServices.ApiType.addbehaviour,student_id, Prefs.getPrefRefId(getActivity()),note_heaad.getText().toString(),note_Description.getText().toString(),subject,Prefs.getPrefUserAuthenticationkey(getActivity()));
            } else {
                SnackBar.makeText(getActivity(), "Please Enter Title and Description", SnackBar.LENGTH_SHORT).show();
            }

        } else {
            SnackBar.makeText(getActivity(), "Something went wrong", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }
    private void EditCallService() {
        if (this.mDetector.isConnectingToInternet()) {
            if (!note_heaad.getText().toString().equals("") || !note_Description.getText().toString().equals("")) {
                new WebServices(this).EditBehaviour(WebServices.SM_Services,
                        WebServices.ApiType.editbehaviour,tvTo.getText().toString(),id,student_id,Prefs.getPrefRefId(getActivity()),note_heaad.getText().toString(),note_Description.getText().toString(),Prefs.getPrefUserAuthenticationkey(getActivity()));
            } else {
                SnackBar.makeText(getActivity(), "Please enter title and details", SnackBar.LENGTH_SHORT).show();
            }
        }else {
            SnackBar.makeText(getActivity(), "Something went wrong", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.addbehaviour) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final AddBehaviour addBehaviour = (AddBehaviour) response;

                if (!isSucces || addBehaviour == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(getActivity(), "Behaviour Added", SnackBar.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), TeacherDashboard.class));
                }
            }
        }if (apiType == WebServices.ApiType.editbehaviour) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final EditBehaviour editBehaviour = (EditBehaviour) response;

                if (!isSucces || editBehaviour.getResult().getMessage() == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(getActivity(), "Behaviour Edited", SnackBar.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), TeacherDashboard.class));
                }
            }
        }
    }
}
