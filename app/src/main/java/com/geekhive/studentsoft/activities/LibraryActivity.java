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
import com.geekhive.studentsoft.adapter.LibraryAdapter;
import com.geekhive.studentsoft.adapter.SeatVacancyAdapter;
import com.geekhive.studentsoft.beans.seatvacancy.SeatVacancyResponce;
import com.geekhive.studentsoft.beans.studentlibrary.GetBookIssued;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LibraryActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {
    @BindView(R.id.vr_library)
    RecyclerView vrLibrary;
    @BindView(R.id.backPress)
    ImageView backPress;
    LibraryAdapter libraryAdapter;
    ConnectionDetector mDetector;
    GetBookIssued getBookIssued;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        CallService();
        backPress.setOnClickListener(this);
    }
    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetBookedIssue(WebServices.SM_Services,
                    WebServices.ApiType.studentbookedissue,Prefs.getPrefRefId(this),Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.studentbookedissue) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getBookIssued = (GetBookIssued) response;

                if (!isSucces || getBookIssued.getResult().getMessage() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    vrLibrary.setLayoutManager(linearLayoutManager);
                    libraryAdapter = new LibraryAdapter(this,getBookIssued);
                    vrLibrary.setAdapter(libraryAdapter);
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
