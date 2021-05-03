package com.geekhive.studentsoft.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.StationaryAdapter;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.leavecancel.Leavecancel;
import com.geekhive.studentsoft.beans.leaveimage.LeaveImage;
import com.geekhive.studentsoft.beans.stationarycategory.StationaryCategory;
import com.geekhive.studentsoft.beans.studentapplyleave.StudentleaveApply;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StationaryActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.vr_stationary)
    RecyclerView vrStationary;
    StationaryAdapter stationaryAdapter;
    StationaryCategory stationaryCategory;
    ConnectionDetector mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_stationary);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        CallstonaryCategory();

       /* backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationaryActivity.this, StationeryOwnerDashboard.class);
                startActivity(intent);
            }
        });
        backPress.setOnClickListener(this);*/
    }
    private void CallstonaryCategory() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).StonaryCategory(WebServices.SM_Services,
                    WebServices.ApiType.staonarycategory, "main",Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.staonarycategory) {
            if (!isSucces) {
                SnackBar.makeText(StationaryActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                stationaryCategory = (StationaryCategory) response;

                if (!isSucces || stationaryCategory.getResult().getMessage() == null) {
                    SnackBar.makeText(StationaryActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
                    vrStationary.setLayoutManager(linearLayoutManager);
                    stationaryAdapter = new StationaryAdapter(this,stationaryCategory);
                    vrStationary.setAdapter(stationaryAdapter);
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
