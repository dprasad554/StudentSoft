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
import com.geekhive.studentsoft.adapter.StudentBHAdapter;
import com.geekhive.studentsoft.adapter.StudentHWAdapter;
import com.geekhive.studentsoft.adapter.StudentResultsAdapter;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentBehaviourActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.vr_student_bh)
    RecyclerView studentBh;
    StudentBHAdapter studentBHAdapter;
    Dialog dialogSuccess;
    ConnectionDetector mDetector;
    GetStudentById getStudentById;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_student_behaviour);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        id = getIntent().getStringExtra("id");

        if(id != null){
            if(!id.equals("")){
                CallService(id);
            }else {
                CallService(Prefs.getPrefRefId(this));
            }
        }



        backPress.setOnClickListener(this);
    }

    private void CallService(String id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid,id, Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }

    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.getstudentbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getStudentById = (GetStudentById) response;
                if (getStudentById.getResult() != null) {
                    if (getStudentById.getResult().getMessage() != null) {
                        if (!isSucces || getStudentById.getResult().getMessage().getBehaviourNote() == null) {
                            SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                        } else {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                            studentBh.setLayoutManager(linearLayoutManager);
                            studentBHAdapter = new StudentBHAdapter(this, getStudentById);
                            studentBh.setAdapter(studentBHAdapter);
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
