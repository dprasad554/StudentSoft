package com.geekhive.studentsoft.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.addfirebaseid.AddFirebaseID;
import com.geekhive.studentsoft.beans.guestregstration.Guestregistration;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuestRegistration extends AppCompatActivity implements View.OnClickListener, OnResponseListner {
    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.firstname)
    TextInputEditText firstname;
    @BindView(R.id.lastname)
    TextInputEditText lastname;
    @BindView(R.id.number)
    TextInputEditText number;
    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.usrPwd)
    TextInputEditText usrPwd;
    @BindView(R.id.confirmPwd)
    TextInputEditText confirmPwd;
    AddFirebaseID addFirebaseID;
    Guestregistration guestregistration;

    Dialog dialogSuccess;
    String[] classHW = {"Select Class", "Class 8", "Class 9", "Class 10", "Class 11", "Class 12"};
    String schoolname,db_key;
    TextView tv_sname;
    ConnectionDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feestructure);
        ButterKnife.bind(this);
        backPress.setOnClickListener(this);
        schoolname = getIntent().getStringExtra("Sname");
        db_key = getIntent().getStringExtra("dbkey");
        tv_sname = findViewById(R.id.tv_sname);
        tv_sname.setText("School Name "+schoolname);
        mDetector = new ConnectionDetector(this);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallRegister();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backPress:
                finish();
                break;
        }
    }
    private void CallRegister() {
        if (this.mDetector.isConnectingToInternet()) {
            if (!firstname.getText().toString().isEmpty() && !lastname.getText().toString().equals("") && !number.getText().toString().equals("")
            && !email.getText().equals("") && !usrPwd.getText().equals("") && !confirmPwd.getText().equals("")) {
                new WebServices(this).GuestRegistration(WebServices.SM_Services,
                        WebServices.ApiType.registration,firstname.getText().toString(),
                        lastname.getText().toString(),number.getText().toString(),email.getText().toString(),
                        usrPwd.getText().toString(),db_key);
                return;
            }else {
                SnackBar.makeText(GuestRegistration.this, "Please enter your Details", SnackBar.LENGTH_SHORT).show();
            }

        }else {
            SnackBar.makeText(GuestRegistration.this, "Something Went Wrong", SnackBar.LENGTH_SHORT).show();
        }
    }
    private void CallAddFirebaseService(String token) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).AddFireBase(WebServices.SM_Services,
                    WebServices.ApiType.addfirebase, "guest", guestregistration.getResult().getMessage().getId(), token, db_key);
        }
        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
       if (apiType == WebServices.ApiType.registration) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                 guestregistration = (Guestregistration) response;

                if (!isSucces || guestregistration.getResult().getMessage() == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    firstname.setText("");
                    lastname.setText("");
                    number.setText("");
                    email.setText("");
                    usrPwd.setText("");
                    initializeFirebase();
                }
            }
        } if (apiType == WebServices.ApiType.addfirebase) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                addFirebaseID = (AddFirebaseID) response;

                if (!isSucces || addFirebaseID.getResult().getMessage() == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (addFirebaseID.getResult() != null) {
                        if (addFirebaseID.getResult().getMessage() != null) {
                            //Intent intent = new Intent(GuestRegistration.this, SeatVacancy.class);
                            /*intent.putExtra("dbkey",db_key);
                            intent.putExtra("id","0");
                            GuestRegistration.this.startActivity(intent);*/
                            Initializepopup();
                            initializlogoutPopup();
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

    private void Initializepopup() {
        dialogSuccess = new Dialog(GuestRegistration.this);
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_gtop);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializlogoutPopup() {
        dialogSuccess.setContentView(R.layout.popup_gtop);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();

        final Button logout = dialogSuccess.findViewById(R.id.btn_submit_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Initializlogoepopup();
                initializdeletePopup();
            }
        });

        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);


        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }

    private void Initializlogoepopup() {
        dialogSuccess = new Dialog(GuestRegistration.this);
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_success);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializdeletePopup() {
        dialogSuccess.setContentView(R.layout.popup_success);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
        final TextView vT_psd_yes = (TextView) dialogSuccess.findViewById(R.id.vT_psd_yes);
        final TextView vT_psd_no = (TextView) dialogSuccess.findViewById(R.id.vT_psd_no);
        final TextView vT_sucess = dialogSuccess.findViewById(R.id.vT_sucess);
        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);

        vT_psd_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSuccess.isShowing()) {
                    dialogSuccess.dismiss();
                    Prefs.setUserId(GuestRegistration.this, null);
                    Prefs.setUserType(GuestRegistration.this, null);
                    startActivity(new Intent(GuestRegistration.this, LoginActivity.class));
                }
            }
        });

        vT_psd_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSuccess.isShowing()) {
                    dialogSuccess.dismiss();
                }
            }
        });

        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }
}
