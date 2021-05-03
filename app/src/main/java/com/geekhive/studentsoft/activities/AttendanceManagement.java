package com.geekhive.studentsoft.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
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

import com.applandeo.materialcalendarview.EventDay;
import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.DrawableUtils;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttendanceManagement extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    /* @BindView(R.id.calendarView)
     com.applandeo.materialcalendarview.CalendarView calendarView;*/
    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;
    @BindView(R.id.todaysdate)
    TextView todaysdate;
    @BindView(R.id.tv_attendance)
    TextView tv_attendance;
    @BindView(R.id.cur_month)
    TextView currentMonth;
    List<String> date = new ArrayList<String>();
    ConnectionDetector mDetector;
    GetStudentById getStudentById;
    String currentDateandTime;
    Dialog dialogSuccess;
    String id;

    String[] dates = {"12/01/2019", "12/02/2019", "12/03/2019", "12/04/2019", "12/05/2019", "12/06/2019", "12/07/2019", "12/08/2019", "12/09/2019",
            "12/10/2019", "12/11/2019", "12/12/2019", "12/13/2019", "12/14/2019", "12/15/2019", "12/16/2019", "12/17/2019", "12/18/2019", "12/19/2019", "12/20/2019"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_attendance_management);
        ButterKnife.bind(this);
        backPress.setOnClickListener(this);
        mDetector = new ConnectionDetector(this);
        id = getIntent().getStringExtra("id");

        if(id != null){
            if(!id.equals("")){
                CallService(id);
            }else {
                CallService(Prefs.getPrefRefId(this));
            }
        }

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat time = new SimpleDateFormat("HH:MM", Locale.getDefault());

        currentDateandTime = sdf.format(new Date());
        String currenttime = time.format(new Time(currentTime.getTime()));

        System.out.println("currentTime::::::" + currentTime);
        todaysdate.setText(currentDateandTime);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Log.e("Date Selected", dateClicked.toString());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String dateString = formatter.format(new Date(dateClicked.toString()));
                Initializepopup();
                initializdeletePopup(dateString);
                Log.e("Event Data", compactCalendarView.getEvents(dateClicked).get(0).getData().toString());
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

    private void CallService(String id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid, id, Prefs.getPrefUserAuthenticationkey(this));
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

                if (!isSucces || getStudentById.getResult().getMessage() == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (getStudentById.getResult().getMessage() != null) {
                        if (getStudentById.getResult().getMessage().getAttendance() != null) {
                            if (getStudentById.getResult().getMessage().getAttendance().size() != 0) {
                                for (int i = 0; i < getStudentById.getResult().getMessage().getAttendance().size(); i++) {
                                    if (getStudentById.getResult().getMessage().getAttendance().get(i).getType().equals("present")) {
                                        List<Event> eventList = new ArrayList<>();
                                        try {
                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                            Date gmt = (Date) formatter.parse(getStudentById.getResult().getMessage().getAttendance().get(i).getDate());
                                            long millisecondsSinceEpoch0 = gmt.getTime();
                                            String asString = formatter.format(gmt);
                                            Event event = new Event(Color.GREEN, millisecondsSinceEpoch0, "P");
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
                                                            if (eventList.get(i).getTimeInMillis() == millisecondsSinceEpoch0) {
                                                                Initializepopup();
                                                                initializdeletePopup(dateString);
                                                            } else {

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
                                        compactCalendarView.addEvents(eventList);
                                    } else if (getStudentById.getResult().getMessage().getAttendance().get(i).getType().equals("absent")) {
                                        if (currentDateandTime.equals(getStudentById.getResult().getMessage().getAttendance().get(i).getDate())) {
                                            tv_attendance.setText("You are Absent");
                                            tv_attendance.setTextColor(Color.parseColor("#FF0000"));
                                        }
                                        List<Event> eventList = new ArrayList<>();
                                        try {
                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                            Date gmt = (Date) formatter.parse(getStudentById.getResult().getMessage().getAttendance().get(i).getDate());
                                            long millisecondsSinceEpoch0 = gmt.getTime();
                                            String asString = formatter.format(gmt);
                                            Event event = new Event(Color.RED, millisecondsSinceEpoch0, "A");
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
                                                            if (eventList.get(i).getTimeInMillis() == millisecondsSinceEpoch0) {
                                                                Initializepopup();
                                                                initializdeletePopup(dateString);
                                                            } else {

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
                                        compactCalendarView.addEvents(eventList);
                                    } else {
                                        List<Event> eventList = new ArrayList<>();
                                        try {
                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                            Date gmt = (Date) formatter.parse(getStudentById.getResult().getMessage().getAttendance().get(i).getDate());
                                            long millisecondsSinceEpoch0 = gmt.getTime();
                                            String asString = formatter.format(gmt);
                                            Event event = new Event(Color.BLACK, millisecondsSinceEpoch0, "A");
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
                                                            if (eventList.get(i).getTimeInMillis() == millisecondsSinceEpoch0) {
                                                                Initializepopup();
                                                                initializdeletePopup(dateString);
                                                            } else {

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
                                        compactCalendarView.addEvents(eventList);
                                    }
                                }
                            }
                        }

                    }

                }
            }
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
        for (int i = 0; i < getStudentById.getResult().getMessage().getAttendance().size(); i++) {
            String Reason = getStudentById.getResult().getMessage().getAttendance().get(i).getType();
            System.out.println("Reason::::" + Reason);
            if (date.equals(getStudentById.getResult().getMessage().getAttendance().get(i).getDate())) {
                hol_dtl.setText(Reason);
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
}
