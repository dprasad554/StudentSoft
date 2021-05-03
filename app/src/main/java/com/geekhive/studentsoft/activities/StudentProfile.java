package com.geekhive.studentsoft.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.EventAdapter;
import com.geekhive.studentsoft.beans.eventregistration.EventRegistration;
import com.geekhive.studentsoft.beans.getallevents.GetAllEvents;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.getupcommingevents.GetUpcommingEvents;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentProfile extends AppCompatActivity implements View.OnClickListener,OnResponseListner {
    ConnectionDetector mDetector;
    GetStudentById getStudentById;
    TextView name,blood_group,class_name,year,parents_details,phn;
    String id;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        mDetector = new ConnectionDetector(this);
        id = getIntent().getStringExtra("id");
        CallStudentbyidService(id);
        name = findViewById(R.id.name);
        blood_group = findViewById(R.id.blood_group);
        class_name = findViewById(R.id.class_name);
        year = findViewById(R.id.year);
        parents_details = findViewById(R.id.parents_details);
        blood_group = findViewById(R.id.blood_group);
        phn = findViewById(R.id.phn);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);



    }
    private void CallStudentbyidService(String id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid, id,Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
       if (apiType == WebServices.ApiType.getstudentbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getStudentById = (GetStudentById) response;

                if (!isSucces || getStudentById.getResult() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } if (!isSucces || getStudentById.getResult().getMessage() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    name.setText(getStudentById.getResult().getMessage().getFirstName()+" "+getStudentById.getResult().getMessage().getLastName());
                    blood_group.setText("Blood Group "+getStudentById.getResult().getMessage().getBloodGroup());
                    class_name.setText("Class: "+getStudentById.getResult().getMessage().getCurrentClass().getClassName()+" Section: "+
                            getStudentById.getResult().getMessage().getCurrentClass().getSectionName());
                    year.setText("Year :"+getStudentById.getResult().getMessage().getAdmissionYear().getStart()+"-"+getStudentById.getResult().getMessage().getAdmissionYear().getEnd());
                    parents_details.setText("Father's Name :"+getStudentById.getResult().getMessage().getParentDetails().getFatherName()+"\n"+
                            "Mother's Name :"+getStudentById.getResult().getMessage().getParentDetails().getMotherName());
                    phn.setText(getStudentById.getResult().getMessage().getPhoneNumber());
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

}
