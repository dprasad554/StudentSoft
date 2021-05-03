package com.geekhive.studentsoft.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.LibraryAdapter;
import com.geekhive.studentsoft.beans.addfirebaseid.AddFirebaseID;
import com.geekhive.studentsoft.beans.loginout.LoginOut;
import com.geekhive.studentsoft.beans.studentlibrary.GetBookIssued;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements OnResponseListner {

    @BindView(R.id.userId)
    TextInputEditText userId;
    @BindView(R.id.usrPwd)
    TextInputEditText usrPwd;
    @BindView(R.id.usrKey)
    TextInputEditText usrKey;
    ConnectionDetector mDetector;
    TextView tv_guest;
    String firebase_id;
    AddFirebaseID addFirebaseID;
    LoginOut loginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_login);
        if (Prefs.getUserType(this).equals("teacher")) {
            startActivity(new Intent(this, TeacherDashboard.class));
            LoginActivity.this.finish();
        }
        if (Prefs.getUserType(this).equals("student")) {
            startActivity(new Intent(this, StudentDashboard.class));
            LoginActivity.this.finish();
        }
        if (Prefs.getUserType(this).equals("guest")) {
            startActivity(new Intent(this, GuestuserDashboard.class));
            LoginActivity.this.finish();
        }
        if (Prefs.getUserType(this).equals("nonteaching")) {
            startActivity(new Intent(this, NonTeachingStaffDashboard.class));
            LoginActivity.this.finish();
        }
        if (Prefs.getUserType(this).equals("parents")) {
            startActivity(new Intent(this, ParentsDashboard.class));
            LoginActivity.this.finish();
        }
        if (Prefs.getUserType(this).equals("driver")) {
            startActivity(new Intent(this, DriverDashboard.class));
            LoginActivity.this.finish();
        }
        if (Prefs.getUserType(this).equals("Stationary")) {
            startActivity(new Intent(this, StationeryOwnerDashboard.class));
            LoginActivity.this.finish();
        }
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        tv_guest = findViewById(R.id.tv_guest);
        tv_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, GuestuserDashboard.class));
                Prefs.setPrefRefId(LoginActivity.this, "");
                Prefs.setUserId(LoginActivity.this, "");
                Prefs.setUserType(LoginActivity.this, "");
                Prefs.setPrefUserAuthenticationkey(LoginActivity.this, "");
                LoginActivity.this.finish();
            }
        });
        FirebaseApp.initializeApp(this);
    }

    private void CallAddFirebaseService(String token) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).AddFireBase(WebServices.SM_Services,
                    WebServices.ApiType.addfirebase, loginOut.getResult().getMessage().getUserType(), loginOut.getResult().getMessage().getRefId(), token, loginOut.getResult().getMessage().getDbKey());
        }
        return;
    }

    public void doLogin(View view) {
        CallService();
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

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            if (!userId.getText().toString().isEmpty() || !usrPwd.getText().toString().equals("") || !usrKey.getText().toString().equals("")) {
                    new WebServices(this).Login(WebServices.SM_Services,
                            WebServices.ApiType.login, userId.getText().toString(), usrPwd.getText().toString(), usrKey.getText().toString());
            } else {
                SnackBar.makeText(LoginActivity.this, "Please enter your ID,Password and Authentication Key", SnackBar.LENGTH_SHORT).show();
            }
        } else {
            SnackBar.makeText(LoginActivity.this, "No internet connectivity", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.login) {
            if (!isSucces) {
                SnackBar.makeText(LoginActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                loginOut = (LoginOut) response;

                if (!isSucces || loginOut == null) {
                    SnackBar.makeText(LoginActivity.this, "something_wrong", SnackBar.LENGTH_SHORT).show();
                }if (!isSucces || loginOut.getResult() == null) {
                    SnackBar.makeText(LoginActivity.this, "something_wrong", SnackBar.LENGTH_SHORT).show();
                } else {
                    initializeFirebase();
                   /* if (loginOut.getResult().getMessage().getUserType().equals("parent")) {

                        Prefs.setPrefRefId(this, loginOut.getResult().getMessage().getRefId());
                        Prefs.setUserId(this, loginOut.getResult().getMessage().getId());
                        Prefs.setUserType(this, loginOut.getResult().getMessage().getUserType());
                        Prefs.setPrefUserAuthenticationkey(this, loginOut.getResult().getMessage().getDbKey());
                        startActivity(new Intent(this, ParentsDashboard.class));
                        LoginActivity.this.finish();
                    }else {

                    }*/


                }
            }
        }
        if (apiType == WebServices.ApiType.addfirebase) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                addFirebaseID = (AddFirebaseID) response;

                if (!isSucces || addFirebaseID.getResult().getMessage() == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (addFirebaseID.getResult() != null) {
                        if (addFirebaseID.getResult().getMessage() != null) {
                            if (loginOut.getResult().getMessage().getUserType().equals("teacher")) {
                                Prefs.setPrefRefId(this, loginOut.getResult().getMessage().getRefId());
                                Prefs.setUserId(this, loginOut.getResult().getMessage().getId());
                                Prefs.setUserType(this, loginOut.getResult().getMessage().getUserType());
                                Prefs.setPrefUserAuthenticationkey(this, loginOut.getResult().getMessage().getDbKey());
                                startActivity(new Intent(this, TeacherDashboard.class));
                                LoginActivity.this.finish();
                            } else if (loginOut.getResult().getMessage().getUserType().equals("student")) {

                                Prefs.setPrefRefId(this, loginOut.getResult().getMessage().getRefId());
                                Prefs.setUserId(this, loginOut.getResult().getMessage().getId());
                                Prefs.setUserType(this, loginOut.getResult().getMessage().getUserType());
                                Prefs.setPrefUserAuthenticationkey(this, loginOut.getResult().getMessage().getDbKey());
                                startActivity(new Intent(this, StudentDashboard.class));
                                LoginActivity.this.finish();
                            } else if (loginOut.getResult().getMessage().getUserType().equals("guest")) {

                                Prefs.setPrefRefId(this, loginOut.getResult().getMessage().getRefId());
                                Prefs.setUserId(this, loginOut.getResult().getMessage().getId());
                                Prefs.setUserType(this, loginOut.getResult().getMessage().getUserType());
                                Prefs.setPrefUserAuthenticationkey(this, loginOut.getResult().getMessage().getDbKey());
                                startActivity(new Intent(this, GuestuserDashboard.class));
                                LoginActivity.this.finish();
                            } else if (loginOut.getResult().getMessage().getUserType().equals("driver")) {
                                Prefs.setPrefRefId(this, loginOut.getResult().getMessage().getRefId());
                                Prefs.setUserId(this, loginOut.getResult().getMessage().getId());
                                Prefs.setUserType(this, loginOut.getResult().getMessage().getUserType());
                                Prefs.setPrefUserAuthenticationkey(this, loginOut.getResult().getMessage().getDbKey());
                                startActivity(new Intent(this, DriverDashboard.class));
                                LoginActivity.this.finish();
                            }else if (loginOut.getResult().getMessage().getUserType().equals("parent")) {
                                Prefs.setPrefRefId(this, loginOut.getResult().getMessage().getRefId());
                                Prefs.setUserId(this, loginOut.getResult().getMessage().getId());
                                Prefs.setUserType(this, loginOut.getResult().getMessage().getUserType());
                                Prefs.setPrefUserAuthenticationkey(this, loginOut.getResult().getMessage().getDbKey());
                                startActivity(new Intent(this, ParentsDashboard.class));
                                LoginActivity.this.finish();
                            }else if (loginOut.getResult().getMessage().getUserType().equals("Stationary")){
                                Prefs.setPrefRefId(this, loginOut.getResult().getMessage().getRefId());
                                Prefs.setUserId(this, loginOut.getResult().getMessage().getId());
                                Prefs.setUserType(this, loginOut.getResult().getMessage().getUserType());
                                Prefs.setPrefUserAuthenticationkey(this, loginOut.getResult().getMessage().getDbKey());
                                startActivity(new Intent(this, StationeryOwnerDashboard.class));
                                LoginActivity.this.finish();
                            }
                        } else {
                            SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                        }
                    } else {
                        SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void initializeFirebase() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Home Activity", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("Home Activiy", msg);
                        //firebase_id = token;
                        CallAddFirebaseService(token);
                        //CallVerifyService(otp, token);
                        //Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END retrieve_current_token]
    }

    public void doForgot(View view) {
        startActivity(new Intent(this, ForgotPassword.class));
        LoginActivity.this.finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            LoginActivity.this.finish();

        }
        return super.onKeyDown(keyCode, event);
    }
}
