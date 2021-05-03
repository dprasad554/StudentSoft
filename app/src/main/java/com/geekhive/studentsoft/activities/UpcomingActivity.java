package com.geekhive.studentsoft.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.UpcomingAdapter;
import com.geekhive.studentsoft.beans.teachertimetable.TeacherTimetable;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpcomingActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.vR_all_classes)
    RecyclerView vRAllClasses;
    UpcomingAdapter upcomingAdapter;
    @BindView(R.id.backPress)
    ImageView backPress;
    ConnectionDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_upcoming);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        CallService();


        backPress.setOnClickListener(this);
    }
    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Teachertimetable(WebServices.SM_Services,
                    WebServices.ApiType.teachertimetable, Prefs.getPrefRefId(this),Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.teachertimetable) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final TeacherTimetable teacherTimetable = (TeacherTimetable) response;

                if (!isSucces || teacherTimetable.getResult().getMessage() == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    LinearLayoutManager layoutShopManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                    vRAllClasses.setLayoutManager(layoutShopManager);
                    upcomingAdapter = new UpcomingAdapter(this,teacherTimetable);
                    vRAllClasses.setAdapter(upcomingAdapter);

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
