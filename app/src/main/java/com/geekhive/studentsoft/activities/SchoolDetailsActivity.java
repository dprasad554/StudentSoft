package com.geekhive.studentsoft.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.geekhive.studentsoft.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SchoolDetailsActivity extends AppCompatActivity {

    @BindView(R.id.ll_syllabus)
    LinearLayout ll_syllabus;
    @BindView(R.id.ll_seatavailability)
    LinearLayout ll_seatavailability;
    @BindView(R.id.ll_feestructure)
    LinearLayout ll_feestructure;
    Dialog dialogSuccess;
    String[] classHW = {"Select Class", "Class 8", "Class 9", "Class 10", "Class 11", "Class 12"};
    String dbkey;
    String schoolname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);
        ButterKnife.bind(this);
        dbkey = getIntent().getStringExtra("dbkey");
        schoolname = getIntent().getStringExtra("Sname");
        System.out.println("dbkey:::::"+dbkey);
        ll_syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolDetailsActivity.this, SyllabusActivity.class);
                intent.putExtra("dbkey",dbkey);
                SchoolDetailsActivity.this.startActivity(intent);
            }
        });

        ll_seatavailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolDetailsActivity.this, SeatVacancy.class);
                intent.putExtra("dbkey",dbkey);
                intent.putExtra("id","1");
                SchoolDetailsActivity.this.startActivity(intent);
            }
        });
        ll_feestructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolDetailsActivity.this, GuestRegistration.class);
                intent.putExtra("Sname",schoolname);
                intent.putExtra("dbkey",dbkey);
                SchoolDetailsActivity.this.startActivity(intent);
            }
        });
    }
    private void Initializepopup() {
        dialogSuccess = new Dialog(this);
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_feestructure);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }
    private void initializdeletePopup() {
        dialogSuccess.setContentView(R.layout.popup_feestructure);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();

        Spinner class_spinner = dialogSuccess.findViewById(R.id.spinner_class);
        Button submit = dialogSuccess.findViewById(R.id.btn_submit_homework);

        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);



        ArrayAdapter classAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, classHW);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        class_spinner.setAdapter(classAdapter);

        class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SchoolDetailsActivity.this, classHW[position], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSuccess.isShowing()) {
                    dialogSuccess.dismiss();

                    Intent intent = new Intent(SchoolDetailsActivity.this, GuestRegistration.class);
                    SchoolDetailsActivity.this.startActivity(intent);
                }
            }
        });

        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }
}
