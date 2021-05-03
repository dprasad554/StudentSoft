package com.geekhive.studentsoft.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.LeaveAdapter;
import com.geekhive.studentsoft.adapter.StudentHWAdapter;
import com.geekhive.studentsoft.adapter.StudentResultsAdapter;
import com.geekhive.studentsoft.beans.gethomework.GetHomeWork;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentHomeWorkActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.vr_student_hw)
    RecyclerView studentHW;
    StudentHWAdapter studentHWAdapter;
    Dialog dialogSuccess;
    ConnectionDetector mDetector;
    GetHomeWork getHomeWork;
    String cls,section;
    GetStudentById getStudentById;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_student_home_work);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        cls = getIntent().getStringExtra("class");
        section = getIntent().getStringExtra("section");
        id = getIntent().getStringExtra("id");

        if(cls != null || section!= null ){
            if(!cls.equals("") || !section.equals("")){
                CallService();
            }else {
                CallStudentbyidService(id);
            }
        }

        backPress.setOnClickListener(this);
    }
    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetHomework(WebServices.SM_Services,
                    WebServices.ApiType.gethomework,cls,section,Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    private void CallStudentbyidService(String id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid, id, Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.gethomework) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getHomeWork = (GetHomeWork) response;

                if (!isSucces || getHomeWork.getResult().getMessage() == null) {
                    SnackBar.makeText(this,"No Home Work Assigned", SnackBar.LENGTH_SHORT).show();
                } else {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    studentHW.setLayoutManager(linearLayoutManager);
                    studentHWAdapter = new StudentHWAdapter(this,getHomeWork);
                    studentHW.setAdapter(studentHWAdapter);
                }
            }
        } if (apiType == WebServices.ApiType.getstudentbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getStudentById = (GetStudentById) response;

                if (!isSucces || getStudentById.getResult().getMessage() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (getStudentById.getResult().getMessage() != null){
                        if (getStudentById.getResult().getMessage().getExamResult() != null){
                            if (getStudentById.getResult().getMessage().getExamResult().size() != 0){
                                cls = getStudentById.getResult().getMessage().getCurrentClass().getClassName();
                                section = getStudentById.getResult().getMessage().getCurrentClass().getSectionName();
                                CallService();
                            }
                        }
                    }


                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backPress:
                finish();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.login_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }
}
