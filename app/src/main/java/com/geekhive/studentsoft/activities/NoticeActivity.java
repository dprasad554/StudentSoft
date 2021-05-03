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
import com.geekhive.studentsoft.adapter.NoticeAdapter;
import com.geekhive.studentsoft.adapter.NotificationAdapter;
import com.geekhive.studentsoft.beans.notice.NoticeData;
import com.geekhive.studentsoft.beans.notification.Notification;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.vR_all_classes)
    RecyclerView vRAllClasses;
    NoticeAdapter noticeAdapter;
    @BindView(R.id.backPress)
    ImageView backPress;
    ConnectionDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        ButterKnife.bind(this);

        mDetector = new ConnectionDetector(this);
        backPress.setOnClickListener(this);

        CallService();
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetNotice(WebServices.SM_Services,
                    WebServices.ApiType.notice,Prefs.getPrefUserAuthenticationkey(this));
            return;
        }
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.notice) {
            if (!isSucces) {
                SnackBar.makeText(NoticeActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final NoticeData noticeData = (NoticeData) response;

                if (!isSucces || noticeData.getResult() == null) {
                    SnackBar.makeText(NoticeActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }if (!isSucces || noticeData.getResult().getMessage() == null) {
                    SnackBar.makeText(NoticeActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    LinearLayoutManager layoutShopManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                    vRAllClasses.setLayoutManager(layoutShopManager);
                    noticeAdapter = new NoticeAdapter(this,noticeData);
                    vRAllClasses.setAdapter(noticeAdapter);
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
