package com.geekhive.studentsoft.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.changepassword.ChangePasswordBean;
import com.geekhive.studentsoft.beans.loginout.LoginOut;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.old_password)
    TextInputEditText old_password;
    @BindView(R.id.new_password)
    TextInputEditText new_password;
    @BindView(R.id.conf_password)
    TextInputEditText conf_password;
    ConnectionDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        backPress.setOnClickListener(this);
        mDetector = new ConnectionDetector(this);
    }

    public void doChange(View view) {
        CallService();
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            if (!old_password.getText().toString().isEmpty()) {
                if (!new_password.getText().toString().equals("")) {
                    if (!conf_password.getText().toString().equals("")) {
                        if (conf_password.getText().toString().equals(new_password.getText().toString())) {
                            new WebServices(this).Changepassword(WebServices.SM_Services,
                                    WebServices.ApiType.changepassword, Prefs.getUserId(this), old_password.getText().toString(), new_password.getText().toString(),Prefs.getPrefUserAuthenticationkey(this));
                        } else {
                            SnackBar.makeText(ChangePassword.this, "Password missmatch", SnackBar.LENGTH_SHORT).show();
                        }
                    } else {
                        conf_password.setError("Please enter password");/*SnackBar.makeText(ChangePasswordBean.this, "Please enter Password", SnackBar.LENGTH_SHORT).show();*/
                    }
                } else {
                    new_password.setError("Please enter password");
                }
            } else {
                old_password.setError("Please enter password");
            }
        } else {
            SnackBar.makeText(ChangePassword.this, "No internet connectivity", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.changepassword) {
            if (!isSucces) {
                SnackBar.makeText(ChangePassword.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final ChangePasswordBean changePassword = (ChangePasswordBean) response;

                if (!isSucces || changePassword == null) {
                    SnackBar.makeText(ChangePassword.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    ChangePassword.this.finish();
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
}
