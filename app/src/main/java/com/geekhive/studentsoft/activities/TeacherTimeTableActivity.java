package com.geekhive.studentsoft.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.TeacherTTAdapter;
import com.geekhive.studentsoft.adapter.UpcomingAdapter;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.getstudenttimetable.GetStudentTimetable;
import com.geekhive.studentsoft.beans.getstudenttimetable.Monday;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherTimeTableActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.vR_teacher_tt)
    RecyclerView vRTeacherTt;
    TeacherTTAdapter teacherTTAdapter;
    @BindView(R.id.backPress)
    ImageView backPress;
    ConnectionDetector mDetector;
    GetStudentById getStudentById;
    Monday monday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_teacher_tt);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);

        CallService();
        backPress.setOnClickListener(this);
    }
    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid, Prefs.getPrefRefId(this),Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    private void CallTimetable(String clas,String section) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetTimeTable(WebServices.SM_Services,
                    WebServices.ApiType.getstudenttimetable,clas,section,Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.getstudentbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getStudentById = (GetStudentById) response;

                if (!isSucces || getStudentById == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    CallTimetable(getStudentById.getResult().getMessage().getCurrentClass().getClassName(),
                            getStudentById.getResult().getMessage().getCurrentClass().getSectionName());
                }
            }
        }if (apiType == WebServices.ApiType.getstudenttimetable) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final GetStudentTimetable getStudentTimetable = (GetStudentTimetable) response;

                if (!isSucces || getStudentTimetable == null) {
                    //SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    teacherTTAdapter = new TeacherTTAdapter(this,getStudentTimetable);
                    vRTeacherTt.setLayoutManager(new LinearLayoutManager(this));
                    vRTeacherTt.setAdapter(teacherTTAdapter);
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
