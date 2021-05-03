package com.geekhive.studentsoft.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.StudentfeeAdapter;
import com.geekhive.studentsoft.beans.feestructure.StudentFeeS;
import com.geekhive.studentsoft.beans.getparentsbyid.GetParentsByID;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.schoollist.SchoolList;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SudentFeeStructure extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.vr_feestructure)
    RecyclerView vr_feestructure;
    StudentfeeAdapter studentfeeAdapter;
    ConnectionDetector mDetector;
    StudentFeeS studentFeeS;
    String student_id = "";
    GetStudentById getStudentById;
    GetParentsByID getParentsByID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_feestructure);
        ButterKnife.bind(this);
        backPress.setOnClickListener(this);
        mDetector = new ConnectionDetector(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        vr_feestructure.setLayoutManager(linearLayoutManager);

        CallService();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backPress:
                finish();
                break;
        }
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetParentsByID(WebServices.SM_Services,
                    WebServices.ApiType.getparentsbyid, Prefs.getPrefRefId(this), Prefs.getPrefUserAuthenticationkey(this));
        } else {
            SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }

    private void CallGeTStudentService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid, student_id, Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }

    private void CallFeesService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).StudentFess(WebServices.SM_Services,
                    WebServices.ApiType.studFee, student_id , "2020",  Prefs.getPrefUserAuthenticationkey(this));

        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.studFee) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                studentFeeS = (StudentFeeS) response;

                if (!isSucces || studentFeeS== null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (studentFeeS.getResult() != null){
                        if (studentFeeS.getResult().getMessage() != null){
                            if (studentFeeS.getResult().getMessage().size() != 0){
                                studentfeeAdapter = new StudentfeeAdapter(this, studentFeeS);
                                vr_feestructure.setAdapter(studentfeeAdapter);
                            }
                        }
                    }

                }
            }
        }
        if (apiType == WebServices.ApiType.getparentsbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getParentsByID = (GetParentsByID) response;

                if (!isSucces || getParentsByID == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }
                if (getParentsByID.getResult() == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (getParentsByID.getResult().getMessage() != null) {
                        if (getParentsByID.getResult().getMessage().getStudentId() != null) {
                            student_id = getParentsByID.getResult().getMessage().getStudentId();
                            CallFeesService();
                            //CallGeTStudentService();
                        }

                    }

                }
            }
        }
    }

}

