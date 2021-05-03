package com.geekhive.studentsoft.activities;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.NotificationAdapter;
import com.geekhive.studentsoft.beans.holidaylist.HolidayList;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.DrawableUtils;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SchoolCalendar extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    /*@BindView(R.id.calendarView)
    com.applandeo.materialcalendarview.CalendarView calendarView;*/
    @BindView(R.id.cur_month)
    TextView currentMonth;
    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;
    List<String> dates = new ArrayList<>();
    //List<EventDay> events = new ArrayList<>();
    Dialog dialogSuccess;
    ConnectionDetector mDetector;
    HolidayList holidayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_school_calendar);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        backPress.setOnClickListener(this);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat month_date = new SimpleDateFormat("MMMM - yyyy");
        String ma = month_date.format(cal.getTime());
        currentMonth.setText(ma);
        CallService();

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Log.e("Date Selected", dateClicked.toString());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String dateString = formatter.format(new Date(dateClicked.toString()));

                Initializepopup();
                initializdeletePopup(dateString);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                SimpleDateFormat formatter = new SimpleDateFormat("MMMM - yyyy");

                // Create a calendar object that will convert the date and time value in milliseconds to date.
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(firstDayOfNewMonth.getTime());
                String ma = formatter.format(calendar.getTime());
                currentMonth.setText(ma);
            }
        });
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Holiday(WebServices.SM_Services,
                    WebServices.ApiType.holidaylist, Prefs.getPrefUserAuthenticationkey(this));
        } else {
            SnackBar.makeText(SchoolCalendar.this, "No internet connectivity", SnackBar.LENGTH_SHORT).show();
        }

        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.holidaylist) {
            if (!isSucces) {
                SnackBar.makeText(SchoolCalendar.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                holidayList = (HolidayList) response;

                if (!isSucces || holidayList.getResult().getMessage() == null) {
                    SnackBar.makeText(SchoolCalendar.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    List<Event> eventList = new ArrayList<>();
                    for (int i = 0; i < holidayList.getResult().getMessage().size(); i++) {
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date gmt = (Date) formatter.parse(holidayList.getResult().getMessage().get(i).getDate());
                            /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZZZ");
                            Date gmt1 = format.parse("2011-07-19T18:23:20+0000");*/
                            long millisecondsSinceEpoch0 = gmt.getTime();
                            String asString = formatter.format(gmt);
                            Event event = new Event(Color.GREEN, millisecondsSinceEpoch0, "H");
                            eventList.add(event);
                            compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                                @Override
                                public void onDayClick(Date dateClicked) {
                                    Log.e("Date Selected", dateClicked.toString());
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                                    String dateString = formatter.format(new Date(dateClicked.toString()));
                                    try {
                                        Date gmt = (Date) formatter.parse(dateString);
                                        long millisecondsSinceEpoch0 = gmt.getTime();
                                        for (int i = 0; i < eventList.size(); i++) {
                                            if(eventList.get(i).getTimeInMillis() == millisecondsSinceEpoch0 ){
                                                Initializepopup();
                                                initializdeletePopup(dateString);
                                            }else {

                                            }

                                        }

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }



                                }

                                @Override
                                public void onMonthScroll(Date firstDayOfNewMonth) {
                                    SimpleDateFormat formatter = new SimpleDateFormat("MMMM - yyyy");

                                    // Create a calendar object that will convert the date and time value in milliseconds to date.
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTimeInMillis(firstDayOfNewMonth.getTime());
                                    String ma = formatter.format(calendar.getTime());
                                    currentMonth.setText(ma);
                                }
                            });
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    compactCalendarView.addEvents(eventList);
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
        TextView hol_dtl = dialogSuccess.findViewById(R.id.hol_dtl);
        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);
        String dt = "Date: " + date;
        dt_selected.setText(dt);
        for (int i = 0; i < holidayList.getResult().getMessage().size(); i++) {
            String Reason = holidayList.getResult().getMessage().get(i).getReason();
            System.out.println("Reason::::" + Reason);
            if (date.equals(holidayList.getResult().getMessage().get(i).getDate())) {
                hol_dtl.setText("Reason :" + Reason);
            }
        }
        vT_psd_yes.setOnClickListener(new View.OnClickListener() {
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
