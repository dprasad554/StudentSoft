package com.geekhive.studentsoft.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.forgetpassword.ForGetPassword;
import com.geekhive.studentsoft.beans.loginout.LoginOut;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPassword extends AppCompatActivity implements OnResponseListner {
    ConnectionDetector mDetector;
    @BindView(R.id.userId)
    TextInputEditText userId;
    @BindView(R.id.usrKey)
    TextInputEditText usrKey;
    ForGetPassword forGetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
    }
    public void doSubmit(View view) {
        CallService();
        /*startActivity(new Intent(this, TeacherDashboard.class));
        ForgotPassword.this.finish();*/
    }
    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            if (!userId.getText().toString().isEmpty() || !userId.getText().toString().equals("") || !usrKey.getText().toString().isEmpty() || !usrKey.getText().toString().equals("")) {
                new WebServices(this).ForgetPassword(WebServices.SM_Services,
                        WebServices.ApiType.login, userId.getText().toString(),usrKey.getText().toString());
            } else {
                SnackBar.makeText(ForgotPassword.this, "Please enter your ID,Password and Authentication Key", SnackBar.LENGTH_SHORT).show();
            }
        } else {
            SnackBar.makeText(ForgotPassword.this, "No internet connectivity", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }
    public void doLoginBack(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        ForgotPassword.this.finish();
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
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.login) {
            if (!isSucces) {
                SnackBar.makeText(ForgotPassword.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                forGetPassword = (ForGetPassword) response;

                if (!isSucces || forGetPassword.getResult() == null) {
                    SnackBar.makeText(ForgotPassword.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    ForgotPassword.this.finish();
                }
            }
        }
    }

}
