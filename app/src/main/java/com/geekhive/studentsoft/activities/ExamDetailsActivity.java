package com.geekhive.studentsoft.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
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
import com.geekhive.studentsoft.adapter.ExamSubjectlistAdapter;
import com.geekhive.studentsoft.adapter.ExamlistAdapter;
import com.geekhive.studentsoft.adapter.StudentResultsAdapter;
import com.geekhive.studentsoft.beans.getstudentbyid.ExamResult;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamDetailsActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.exam_subject_list)
    RecyclerView exam_subject_list;
    ExamSubjectlistAdapter examSubjectlistAdapter;
    Dialog dialogSuccess;
    ConnectionDetector mDetector;
    GetStudentById getStudentById;
    ExamResult examResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_exam_list);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        backPress.setOnClickListener(this);
        CallService();
    }
    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid, Prefs.getPrefRefId(this),Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }

    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.getstudentbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getStudentById = (GetStudentById) response;

                if (!isSucces || getStudentById.getResult().getMessage().getExamResult() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    exam_subject_list.setLayoutManager(linearLayoutManager);
                    examSubjectlistAdapter = new ExamSubjectlistAdapter(this,getStudentById);
                    exam_subject_list.setAdapter(examSubjectlistAdapter);
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
