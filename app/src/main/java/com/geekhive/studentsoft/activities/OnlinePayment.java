package com.geekhive.studentsoft.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.getparentsbyid.GetParentsByID;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.studentfeeonline.StudentFeeOnline;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnlinePayment extends AppCompatActivity implements View.OnClickListener, OnResponseListner {
    @BindView(R.id.instMnth)
    TextView instalMnth;
    @BindView(R.id.feePaid)
    TextView feesPaid;
    @BindView(R.id.feeDue)
    TextView feesDue;
    StudentFeeOnline studentFeeOnline;
    ConnectionDetector mDetector;
    GetParentsByID getParentsByID;
    String student_id;
    GetStudentById getStudentById;
    String cls, section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinepayment);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
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

    private void CallStudentFees() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).StudentFeeOnline(WebServices.SM_Services,
                    WebServices.ApiType.studentfeeonli, student_id, cls, section, Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
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

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.studentfeeonli) {
            if (!isSucces) {
                SnackBar.makeText(OnlinePayment.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                studentFeeOnline = (StudentFeeOnline) response;

                if (!isSucces || studentFeeOnline.getResult().getMessage() == null) {
                    SnackBar.makeText(OnlinePayment.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    instalMnth.setText(studentFeeOnline.getResult().getMessage().get(0).getYear());
                    feesPaid.setText(String.valueOf(studentFeeOnline.getResult().getMessage().get(0).getFeesPaid()));
                    feesDue.setText(String.valueOf(studentFeeOnline.getResult().getMessage().get(0).getFeesPending()));
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
                    student_id = getParentsByID.getResult().getMessage().getStudentId();

                    CallGeTStudentService();
                }
            }
        }
        if (apiType == WebServices.ApiType.getstudentbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getStudentById = (GetStudentById) response;

                if (!isSucces || getStudentById.getResult().getMessage().getBehaviourNote() == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    cls = getStudentById.getResult().getMessage().getCurrentClass().getClassName();
                    section = getStudentById.getResult().getMessage().getCurrentClass().getSectionName();
                    CallStudentFees();
                }
            }
        }
    }
}

