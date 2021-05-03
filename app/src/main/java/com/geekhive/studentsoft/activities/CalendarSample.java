package com.geekhive.studentsoft.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.holidaylist.HolidayList;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.DrawableUtils;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarSample extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.backPress)
    ImageView backPress;
    List<String> dates = new ArrayList<>();
    List<EventDay> events = new ArrayList<>();
    Dialog dialogSuccess;
    ConnectionDetector mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.calendar_sample);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        backPress.setOnClickListener(this);
        CallService();
    }
    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Holiday(WebServices.SM_Services,
                    WebServices.ApiType.holidaylist, Prefs.getPrefUserAuthenticationkey(this));
        }
        else {
            SnackBar.makeText(CalendarSample.this, "No internet connectivity", SnackBar.LENGTH_SHORT).show();
        }

        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.holidaylist) {
            if (!isSucces) {
                SnackBar.makeText(CalendarSample.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final HolidayList holidayList = (HolidayList) response;

                if (!isSucces || holidayList.getResult().getMessage() == null) {
                    SnackBar.makeText(CalendarSample.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < holidayList.getResult().getMessage().size(); i++) {

                        dates.add(holidayList.getResult().getMessage().get(i).getDate());
                        String s = dates.get(i);
                        String[] data = s.split("-", 2);
                        String dat = data[1];
                        String[] date = dat.split("-", 2);
                        Log.e("Date", date[0]);
                        Calendar calendar = Calendar.getInstance();
                        if (Integer.parseInt(date[0]) == (Calendar.MONTH)){
                            int d1 = Integer.parseInt(date[1]);
                            int d2 =  d1 - (Calendar.DAY_OF_MONTH + 11);  //(Calendar.DAY_OF_MONTH) - d1+3
                            calendar.add(Calendar.DAY_OF_MONTH, d2);
                        } else {
                            int d1 = Integer.parseInt(date[1]);
                            int d2 =  d1 - (Calendar.DAY_OF_MONTH - 11);  //(Calendar.DAY_OF_MONTH) - d1+3
                            calendar.add(Calendar.DAY_OF_MONTH, d2);
                        }
                        events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(this, "H")));
                    }
                    calendarView.setEvents(events);

                    calendarView.setOnDayClickListener(new OnDayClickListener() {
                        @Override
                        public void onDayClick(EventDay eventDay) {
                            String myDate = eventDay.getCalendar().getTime().toString();
                            Log.e("Date Selected", myDate);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                            String dateString = formatter.format(new Date(myDate));
                            Initializepopup();
                            initializdeletePopup(dateString);
                        }
                    });

                }
            }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backPress:
                finish();
                break;
        }
    }


    private void Initializepopup() {
        dialogSuccess = new Dialog(this);
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_holiday_data);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializdeletePopup(String date) {
        dialogSuccess.setContentView(R.layout.popup_holiday_data);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
        TextView dt_selected = dialogSuccess.findViewById(R.id.dt_selected);
        TextView vT_psd_yes = dialogSuccess.findViewById(R.id.vT_psd_yes);
        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);

        String dt = "Date: " + date;
        dt_selected.setText(dt);

        vT_psd_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSuccess.isShowing()){
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