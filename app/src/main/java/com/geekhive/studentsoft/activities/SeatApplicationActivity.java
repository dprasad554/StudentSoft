package com.geekhive.studentsoft.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.applicationform.ApplicationForm;
import com.geekhive.studentsoft.beans.applyvecancy.ApplyVecancy;
import com.geekhive.studentsoft.beans.loginout.LoginOut;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeatApplicationActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.spinner_male)
    Spinner spinnerMale;
    @BindView(R.id.spinner_caste)
    Spinner spinnerCaste;
    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.ll_start_date)
    LinearLayout llStartDate;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.chooseClass)
    TextView chooseClass;
    @BindView(R.id.name)
    TextInputEditText name;
    @BindView(R.id.inWords)
    TextInputEditText inWords;
    @BindView(R.id.ageID)
    TextInputEditText ageID;
    @BindView(R.id.nationality)
    TextInputEditText nationality;
    @BindView(R.id.motherTongue)
    TextInputEditText motherTongue;
    @BindView(R.id.religion)
    TextInputEditText religion;
    @BindView(R.id.others)
    TextInputEditText others;
    @BindView(R.id.bGroup)
    TextInputEditText bGroup;
    @BindView(R.id.rAdd)
    TextInputEditText rAdd;
    @BindView(R.id.pAdd)
    TextInputEditText pAdd;
    @BindView(R.id.pinC)
    TextInputEditText pinC;
    @BindView(R.id.contactNo)
    TextInputEditText contactNo;
    @BindView(R.id.btn_submit_homework)
    Button btn_submit_homework;

    DatePickerDialog.OnDateSetListener setListenerStart;
    String dateS;
    String[] classMF = {"Male", "Female"};
    String[] classCaste = {"SC", "ST", "OBC", "General", "Others"};
    String cls;
    ConnectionDetector mDetector;
    ApplyVecancy applyVecancy;
    CheckBox checkbox_meat;

    @BindView(R.id.same_avobe)
    TextView same_avobe;
    String monthe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_seat_application);
        ButterKnife.bind(this);


        backPress.setOnClickListener(this);
        cls = getIntent().getStringExtra("class");
        mDetector = new ConnectionDetector(this);
        checkbox_meat = findViewById(R.id.checkbox_meat);

        ArrayAdapter maleAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, classMF);
        maleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerMale.setAdapter(maleAdapter);

        ArrayAdapter classCasteAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, classCaste);
        classCasteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerCaste.setAdapter(classCasteAdapter);

        checkbox_meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pAdd.setText(rAdd.getText().toString());
                System.out.println("radd::::::::"+rAdd.getText().toString());
            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        dateS = mdformat.format(calendar.getTime());


        tvFrom.setText(dateS);
        chooseClass.setText("Class Name : " + cls);
        llStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SeatApplicationActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerStart, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            }
        });

        setListenerStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
               if(month == 1){
                   String date = year + "/" + "Jan" + "/" + dayOfMonth;
                   tvFrom.setText(date);
               }else if(month == 2){
                    String date = year + "/" + "Feb" + "/" + dayOfMonth;
                    tvFrom.setText(date);
               }else if(month == 3){
                   String date = year + "/" + "Mar" + "/" + dayOfMonth;
                   tvFrom.setText(date);
               }else if(month == 4){
                   String date = year + "/" + "Apr" + "/" + dayOfMonth;
                   tvFrom.setText(date);
               }else if(month == 5){
                   String date = year + "/" + "May" + "/" + dayOfMonth;
                   tvFrom.setText(date);
               }else if(month == 6){
                   String date = year + "/" + "Jun" + "/" + dayOfMonth;
                   tvFrom.setText(date);
               }else if(month == 7){
                   String date = year + "/" + "Jul" + "/" + dayOfMonth;
                   tvFrom.setText(date);
               }else if(month == 8){
                   String date = year + "/" + "Aug" + "/" + dayOfMonth;
                   tvFrom.setText(date);
               }else if(month == 9){
                   String date = year + "/" + "Sep" + "/" + dayOfMonth;
                   tvFrom.setText(date);
               }else if(month == 10){
                   String date = year + "/" + "Oct" + "/" + dayOfMonth;
                   tvFrom.setText(date);
               }else if(month == 11){
                   String date = year + "/" + "Nov" + "/" + dayOfMonth;
                   tvFrom.setText(date);
               }else if(month == 12){
                   String date = year + "/" + "Dec" + "/" + dayOfMonth;
                   tvFrom.setText(date);
               }

            }
            /* monthe = String.valueOf(calendar.get(month));

                DateFormatSymbols dfs = new DateFormatSymbols();
                String[] months = dfs.getMonths();
                if (Integer.valueOf(monthe) >= 0 && Integer.valueOf(monthe) <= 11 ) {
                    monthe = months[Integer.valueOf(monthe)];
                }*/
        };
        btn_submit_homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallService();
            }
        });
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            if (!name.getText().toString().isEmpty() || !inWords.getText().toString().equals("")
                    || !ageID.getText().toString().isEmpty() || !nationality.getText().toString().equals("")
                    || !motherTongue.getText().toString().isEmpty() || !religion.getText().toString().equals("")
                    || !others.getText().toString().isEmpty() || !bGroup.getText().toString().equals("")
                    || !rAdd.getText().toString().isEmpty() || !pAdd.getText().toString().equals("")
                    || !pinC.getText().toString().isEmpty() || !contactNo.getText().toString().equals("")) {



                ApplyVecancy applyVecancy = new ApplyVecancy();
                applyVecancy.setApplicantName(name.getText().toString());
                applyVecancy.setSex(spinnerMale.getSelectedItem().toString());
                applyVecancy.setDateOfBirth(tvFrom.getText().toString());
                applyVecancy.setDateOfBirthInWords(inWords.getText().toString());
                applyVecancy.setAppliedForClass(cls);
                applyVecancy.setAge(ageID.getText().toString());
                applyVecancy.setNationality(nationality.getText().toString());
                applyVecancy.setReligion(religion.getText().toString());
                applyVecancy.setCaste(spinnerCaste.getSelectedItem().toString());
                applyVecancy.setBloodGroup(bGroup.getText().toString());
                applyVecancy.setResidentialAddress(rAdd.getText().toString());
                applyVecancy.setPermanentAddress(pAdd.getText().toString());
                applyVecancy.setPincode(pinC.getText().toString());
                applyVecancy.setContactNumber(contactNo.getText().toString());
                applyVecancy.setAppliedBy(Prefs.getPrefRefId(this));
                new WebServices(this).StudentApplication(WebServices.SM_Services,
                        WebServices.ApiType.studentapplication, applyVecancy,Prefs.getPrefUserAuthenticationkey(this));

                /*new WebServices(this).StudentApplication(WebServices.SM_Services,
                        WebServices.ApiType.studentapplication,name.getText().toString(),
                        spinnerMale.getSelectedItem().toString(),tvFrom.getText().toString(),
                        inWords.getText().toString(),cls,ageID.getText().toString(),nationality.getText().toString(),
                        religion.getText().toString(),spinnerCaste.getSelectedItem().toString(),bGroup.getText().toString(),
                        rAdd.getText().toString(),pAdd.getText().toString(),pinC.getText().toString(),contactNo.getText().toString());
           */
            } else {
                SnackBar.makeText(SeatApplicationActivity.this, "Please enter your details", SnackBar.LENGTH_SHORT).show();
            }
        } else {
            SnackBar.makeText(SeatApplicationActivity.this, "No internet connectivity", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.studentapplication) {
            if (!isSucces) {
                SnackBar.makeText(SeatApplicationActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final ApplicationForm applicationForm = (ApplicationForm) response;

                if (!isSucces || applicationForm.getResult().getMessage() == null) {
                    SnackBar.makeText(SeatApplicationActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    name.setText("");
                    inWords.setText("");
                    ageID.setText("");
                    nationality.setText("");
                    motherTongue.setText("");
                    religion.setText("");
                    others.setText("");
                    bGroup.setText("");
                    rAdd.setText("");
                    pAdd.setText("");
                    pinC.setText("");
                    contactNo.setText("");
                    SnackBar.makeText(SeatApplicationActivity.this, "Registration Sucessfull school will contact you back", SnackBar.LENGTH_SHORT).show();
                    startActivity(new Intent(this, GuestuserDashboard.class));
                    /*Prefs.setUserId(SeatApplicationActivity.this, null);
                    Prefs.setUserType(SeatApplicationActivity.this, null);
                    Prefs.setPrefUserAuthenticationkey(SeatApplicationActivity.this, null);
                    startActivity(new Intent(this, LoginActivity.class));*/
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

    public void doApplicationSubmit(View view) {
        finish();
    }
}
