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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.NotificationAdapter;
import com.geekhive.studentsoft.adapter.UpcomingAdapter;
import com.geekhive.studentsoft.beans.holidaylist.HolidayList;
import com.geekhive.studentsoft.beans.loginout.LoginOut;
import com.geekhive.studentsoft.beans.notification.Notification;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.vR_all_classes)
    RecyclerView vRAllClasses;
    NotificationAdapter notificationAdapter;
    @BindView(R.id.backPress)
    ImageView backPress;
    ConnectionDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        mDetector = new ConnectionDetector(this);
        backPress.setOnClickListener(this);
        CallService();
    }
    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
                    new WebServices(this).Notification(WebServices.SM_Services,
                            WebServices.ApiType.notification,Prefs.getPrefRefId(this),Prefs.getPrefUserAuthenticationkey(this));
                }
        else {
            SnackBar.makeText(NotificationActivity.this, "No internet connectivity", SnackBar.LENGTH_SHORT).show();
        }

        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.notification) {
            if (!isSucces) {
                SnackBar.makeText(NotificationActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final Notification notification = (Notification) response;

                if (!isSucces || notification.getResult().getMessage() == null) {
                    SnackBar.makeText(NotificationActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    LinearLayoutManager layoutShopManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                    vRAllClasses.setLayoutManager(layoutShopManager);
                    notificationAdapter = new NotificationAdapter(this,notification);
                    vRAllClasses.setAdapter(notificationAdapter);
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
