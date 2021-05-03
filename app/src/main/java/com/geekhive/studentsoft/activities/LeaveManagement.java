package com.geekhive.studentsoft.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.LeaveAdapter;
import com.geekhive.studentsoft.adapter.TimeTableAdapter;
import com.geekhive.studentsoft.beans.applyleave.ApplyLeave;
import com.geekhive.studentsoft.beans.leaveapply.Dates;
import com.geekhive.studentsoft.beans.leaveapply.Leaveapply;
import com.geekhive.studentsoft.beans.loginout.LoginOut;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LeaveManagement extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.vr_leaves)
    RecyclerView vrLeaves;
    LeaveAdapter leaveAdapter;
    @BindView(R.id.fab_add_leave)
    FloatingActionButton fabAddLeave;
    DatePickerDialog.OnDateSetListener setListenerStart;
    DatePickerDialog.OnDateSetListener setListenerEnd;
    String dateS;
    Dialog dialogSuccess;
    ConnectionDetector mDetector;
    List<String> datelist = new ArrayList<>();
    Dates dates;
    CalendarPickerView calendar;
    EditText reason_text;
    Date date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_leave_management);
        ButterKnife.bind(this);

      /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        vrLeaves.setLayoutManager(linearLayoutManager);
        leaveAdapter = new LeaveAdapter(this);
        vrLeaves.setAdapter(leaveAdapter);*/
        mDetector = new ConnectionDetector(this);
        fabAddLeave.setOnClickListener(this);
        backPress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backPress:
                finish();
                break;
            case R.id.fab_add_leave:
                Initializepopup();
                initializdeletePopup();
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

    private void Initializepopup() {
        dialogSuccess = new Dialog(this);
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_leave_apply);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializdeletePopup() {
        dialogSuccess.setContentView(R.layout.popup_leave_apply);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);
        calendar = dialogSuccess.findViewById(R.id.calendar_view);
        reason_text = dialogSuccess.findViewById(R.id.reason_text);
        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("YYYY, MMM ,DD", Locale.getDefault())) //
                .inMode(CalendarPickerView.SelectionMode.RANGE);
        calendar.scrollToDate(new Date());


        Button btnSubmitLeave = dialogSuccess.findViewById(R.id.btn_submit_leave);
        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);


        btnSubmitLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSuccess.isShowing()) {
                    dialogSuccess.dismiss();
                    String lunchTime2 = calendar.getSelectedDates().toString();

                    Toast.makeText(LeaveManagement.this, "list " + lunchTime2, Toast.LENGTH_LONG).show();
                    datelist = new ArrayList<>();
                    for (int i = 0; i < calendar.getSelectedDates().size(); i++) {
                        Log.e("Dates", String.valueOf(calendar.getSelectedDates().get(i).getTime()));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String cdate = dateFormat.format(calendar.getSelectedDates().get(i).getTime());
                        Log.e("cdate", cdate);
                        datelist.add(cdate);
                    }
                    if (datelist.size() != 0) {
                        CallService();
                    }

                }
            }
        });
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            if (!reason_text.getText().toString().isEmpty() || !reason_text.getText().toString().equals("")) {
                ApplyLeave applyLeave = new ApplyLeave();
                applyLeave.setDates(datelist);
                applyLeave.setReason(reason_text.getText().toString());
                new WebServices(this).ApplyLeave(WebServices.SM_Services,
                        WebServices.ApiType.applyleave, Prefs.getPrefRefId(this), applyLeave,Prefs.getPrefUserAuthenticationkey(this));
            } else {
                SnackBar.makeText(LeaveManagement.this, "Please enter reason of leave", SnackBar.LENGTH_SHORT).show();
            }
        } else {
            SnackBar.makeText(LeaveManagement.this, "No internet connectivity", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.applyleave) {
            if (!isSucces) {
                SnackBar.makeText(LeaveManagement.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final Leaveapply leaveapply = (Leaveapply) response;

                if (!isSucces || leaveapply == null) {
                    SnackBar.makeText(LeaveManagement.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(LeaveManagement.this, "Sucess", SnackBar.LENGTH_SHORT).show();
                }
            }
        }
    }
}
