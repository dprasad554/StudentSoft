package com.geekhive.studentsoft.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.TeacherAttendanceManagement;
import com.geekhive.studentsoft.activities.UpcomingActivity;
import com.geekhive.studentsoft.adapter.HomeSliderViewPagerAdapter;
import com.geekhive.studentsoft.beans.getexamnames.GetExamNames;
import com.geekhive.studentsoft.beans.homebanner.Homebanner;
import com.geekhive.studentsoft.beans.sectionbyname.SectionByName;
import com.geekhive.studentsoft.beans.sectionbyname.Student;
import com.geekhive.studentsoft.beans.teacherbyid.TeacherByID;
import com.geekhive.studentsoft.beans.teacherpresentattendance.TeacherAttendance;
import com.geekhive.studentsoft.beans.teachertimetable.TeacherTimetable;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.GPSTracker;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.io.File;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements View.OnClickListener, OnResponseListner {

    Unbinder unbinder;
    @BindView(R.id.cv_homework)
    CardView cvHomeWork;
    @BindView(R.id.cv_behaviour)
    CardView cvBehaviour;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.dot1)
    DotsIndicator dot1;
    @BindView(R.id.vC_attendance)
    ImageView vcAttendance;
    @BindView(R.id.check_attendance)
    ImageView check_attendance;
    @BindView(R.id.cvUpcoming)
    CardView cvUpcoming;
    @BindView(R.id.vC_result)
    CardView vCResult;
    @BindView(R.id.classname)
    TextView classname;
    @BindView(R.id.sectionname)
    TextView sectionname;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.subject)
    TextView subject;
    @BindView(R.id.todaysdate)
    TextView todaysdate;
    @BindView(R.id.tv_upcomming)
    TextView tv_upcomming;


    HomeSliderViewPagerAdapter viewPagerAdapter;
    Timer timer1;
    Dialog dialogSuccess;
    private ArrayList<String> classHW = new ArrayList<String>();
    private ArrayList<String> sectionHW = new ArrayList<String>();
    private ArrayList<String> subjectHW = new ArrayList<String>();
    private ArrayList<String> examHW = new ArrayList<String>();
    private ArrayList<String> studentlist = new ArrayList<String>();
    private ArrayList<String> starttime = new ArrayList<String>();

    ConnectionDetector mDetector;
    double lat;
    double lang;
    String cls, sec, student_id;
    SectionByName getSectionbyID;
    Spinner student_spinner;
    List<Student> students = new ArrayList<>();
    GetExamNames getExamNames;

    public HomeFragment() {
        // Required empty public constructor
    }

    TeacherTimetable teacherTimetable;
    String schoollat,schoollon;
    Date currentTime;
    String currenttime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        mDetector = new ConnectionDetector(getActivity());
        cvHomeWork.setOnClickListener(this);
        cvBehaviour.setOnClickListener(this);
       /* SliderImageAdapter imageAdapter = new SliderImageAdapter(getActivity());
        viewPager.setAdapter(imageAdapter);
        dot1.setViewPager(viewPager);*/
        check_attendance.setOnClickListener(this);
        vcAttendance.setOnClickListener(this);
        cvUpcoming.setOnClickListener(this);
        vCResult.setOnClickListener(this);

        /*//For First Slider Timmer
        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable() {

                    @Override
                    public void run() {
                        viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % 4);
                    }
                });
            }
        };
        timer1 = new Timer();
        timer1.schedule(timerTask1, 3400, 3400);*/
        CallService();
        checkLocationService();
        CallTeacherbyIdService();
        CallExamName();
        CallbannerService();

        currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat time = new SimpleDateFormat("HH:MM", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        currenttime = time.format(new Time(currentTime.getTime()));

        System.out.println("currentTime::::::" + currentTime);
        todaysdate.setText(currentDateandTime);
        System.out.println("formateddate::::::" + currentDateandTime);
        System.out.println("formatedtime::::::" + currenttime);




        return view;
    }
    private void CallbannerService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Callbanner(WebServices.SM_Services,
                    WebServices.ApiType.allbanner,Prefs.getPrefUserAuthenticationkey(getActivity()));
            return;
        }

    }
    private void checkLocationService() {
        GPSTracker gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {
            this.lat = gps.getLatitude();
            this.lang = gps.getLongitude();
            Log.e("latitude:::", "" + this.lat);
            Log.e("longitude:::", "" + this.lang);
        }
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Teachertimetable(WebServices.SM_Services,
                    WebServices.ApiType.teachertimetable, Prefs.getPrefRefId(getActivity()),Prefs.getPrefUserAuthenticationkey(getActivity()));
        }
        return;
    }

    private void CallTeacherbyIdService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Teacherbyid(WebServices.SM_Services,
                    WebServices.ApiType.teacherbyid, Prefs.getPrefRefId(getActivity()),Prefs.getPrefUserAuthenticationkey(getActivity()));
        }
        return;
    }

    private void CallTeacherattendanceService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Teacherattendance(WebServices.SM_Services,
                    WebServices.ApiType.teacherpresentattendance, Prefs.getPrefRefId(getActivity()),Prefs.getPrefUserAuthenticationkey(getActivity()));
        }
        return;
    }

    private void CallsectionbyID(String clas, String section) {
        if (this.mDetector.isConnectingToInternet()) {
            String db_key =  Prefs.getPrefUserAuthenticationkey(getActivity());
            new WebServices(this).SectionByID(WebServices.SM_Services,
                    WebServices.ApiType.sectionbyName, clas, section,db_key);
        }
        return;
    }

    private void CallExamName() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Callexamname(WebServices.SM_Services,
                    WebServices.ApiType.callexamnames,Prefs.getPrefUserAuthenticationkey(getActivity()));
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.teachertimetable) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                teacherTimetable = (TeacherTimetable) response;

                if (!isSucces || teacherTimetable == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (teacherTimetable.getResult() != null){
                        if (teacherTimetable.getResult().getMessage() != null){

                            Calendar calander = Calendar.getInstance();
                            int day = calander.get(Calendar.DAY_OF_WEEK);
                            Date curenttime = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            String currentDateTimeString = sdf.format(curenttime);

                            switch (day) {
                                case Calendar.SUNDAY:
                                    classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getClassName() + "   ON MONDAY");
                                    sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getSectionName());
                                    time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getStartTime());
                                    subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getSubject());
                                    break;
                                case Calendar.MONDAY:
                                    for (int i = 0; i < teacherTimetable.getResult().getMessage().getTimetable().getMonday().size(); i++) {
                                        try {
                                            String string1 = teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getStartTime();
                                            Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
                                            Calendar calendar1 = Calendar.getInstance();
                                            calendar1.setTime(time1);
                                            calendar1.add(Calendar.DATE, 1);


                                            String string2 = teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getEndTime();
                                            Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
                                            Calendar calendar2 = Calendar.getInstance();
                                            calendar2.setTime(time2);
                                            calendar2.add(Calendar.DATE, 1);

                                            String someRandomTime = String.valueOf(currentDateTimeString);
                                            Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
                                            Calendar calendar3 = Calendar.getInstance();
                                            calendar3.setTime(d);
                                            calendar3.add(Calendar.DATE, 1);

                                            Date x = calendar3.getTime();
                                            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getType().equals("class")) {
                                                    if (i + 1 == teacherTimetable.getResult().getMessage().getTimetable().getMonday().size()) {
                                                        if (!teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getClassName().equals("")) {
                                                            classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getClassName());
                                                            sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getSectionName());
                                                            time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getStartTime());
                                                            subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getSubject());
                                                            tv_upcomming.setText("Your Last class");
                                                        } else {
                                                            break;
                                                        }
                                                        return;
                                                    } else {
                                                        if (!teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getClassName().equals("")) {
                                                            classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getClassName());
                                                            sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getSectionName());
                                                            time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getStartTime());
                                                            subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getSubject());
                                                        } else {
                                                            classname.setText("No Classes Available");
                                                            sectionname.setVisibility(View.GONE);
                                                            time.setVisibility(View.GONE);
                                                            subject.setVisibility(View.GONE);
                                                        }
                                                    }


                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getType().equals("lunch")) {
                                                    classname.setText("No Classes Available");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setVisibility(View.GONE);
                                                    subject.setVisibility(View.GONE);
                                                }

                                            } else if (x.equals(calendar1.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getType().equals("class")) {
                                                    if (!teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getClassName().equals("")) {
                                                        classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getClassName());
                                                        sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getSectionName());
                                                        time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getStartTime());
                                                        subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getSubject());
                                                    } else {
                                                        return;
                                                    }
                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getType().equals("lunch")) {
                                                    classname.setText("Leisure");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getStartTime());
                                                    subject.setVisibility(View.GONE);
                                                }
                                            } else if (x.equals(calendar2.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getType().equals("class")) {
                                                    if (!teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getClassName().equals("")) {
                                                        classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getClassName());
                                                        sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getSectionName());
                                                        time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getStartTime());
                                                        subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i + 1).getSubject());
                                                    } else {
                                                        return;
                                                    }
                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getType().equals("lunch")) {
                                                    classname.setText("Leisure");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getStartTime());
                                                    subject.setVisibility(View.GONE);
                                                }
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case Calendar.TUESDAY:
                                    for (int i = 0; i < teacherTimetable.getResult().getMessage().getTimetable().getTuesday().size(); i++) {
                                        try {
                                            String string1 = teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getStartTime();
                                            Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
                                            Calendar calendar1 = Calendar.getInstance();
                                            calendar1.setTime(time1);
                                            calendar1.add(Calendar.DATE, 1);

                                            String string2 = teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getEndTime();
                                            Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
                                            Calendar calendar2 = Calendar.getInstance();
                                            calendar2.setTime(time2);
                                            calendar2.add(Calendar.DATE, 1);

                                            String someRandomTime = String.valueOf(currentDateTimeString);
                                            Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
                                            Calendar calendar3 = Calendar.getInstance();
                                            calendar3.setTime(d);
                                            calendar3.add(Calendar.DATE, 1);

                                            Date x = calendar3.getTime();
                                            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getType().equals("class")) {
                                                    if (i + 1 == teacherTimetable.getResult().getMessage().getTimetable().getTuesday().size()) {
                                                        if (!teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getClassName().equals("")) {
                                                            classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getClassName());
                                                            sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getSectionName());
                                                            time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getStartTime());
                                                            subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getSubject());
                                                            tv_upcomming.setText("Your Last class");
                                                        }
                                                        return;
                                                    } else {
                                                        if (!teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getClassName().equals("")) {
                                                            classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getClassName());
                                                            sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getSectionName());
                                                            time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getStartTime());
                                                            subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getSubject());
                                                        } else {
                                                            classname.setText("No Classes Available");
                                                            sectionname.setVisibility(View.GONE);
                                                            time.setVisibility(View.GONE);
                                                            subject.setVisibility(View.GONE);
                                                        }
                                                    }

                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getType().equals("lunch")) {
                                                    classname.setText("No Classes Available");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setVisibility(View.GONE);
                                                    subject.setVisibility(View.GONE);
                                                }

                                            } else if (x.equals(calendar1.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getType().equals("class")) {
                                                    if (!teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getClassName().equals("")) {
                                                        classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getClassName());
                                                        sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getSectionName());
                                                        time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getStartTime());
                                                        subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getSubject());
                                                    } else {
                                                        return;
                                                    }
                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getType().equals("lunch")) {
                                                    classname.setText("Leisure");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getStartTime());
                                                    subject.setVisibility(View.GONE);
                                                }
                                            } else if (x.equals(calendar2.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getType().equals("class")) {
                                                    if (!teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getClassName().equals("")) {
                                                        classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getClassName());
                                                        sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getSectionName());
                                                        time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getStartTime());
                                                        subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i + 1).getSubject());
                                                    } else {
                                                        return;
                                                    }
                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getType().equals("lunch")) {
                                                    classname.setText("Leisure");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getStartTime());
                                                    subject.setVisibility(View.GONE);
                                                }
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case Calendar.WEDNESDAY:
                                    for (int i = 0; i < teacherTimetable.getResult().getMessage().getTimetable().getWednesday().size(); i++) {
                                        try {
                                            String string1 = teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getStartTime();
                                            Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
                                            Calendar calendar1 = Calendar.getInstance();
                                            calendar1.setTime(time1);
                                            calendar1.add(Calendar.DATE, 1);


                                            String string2 = teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getEndTime();
                                            Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
                                            Calendar calendar2 = Calendar.getInstance();
                                            calendar2.setTime(time2);
                                            calendar2.add(Calendar.DATE, 1);

                                            String someRandomTime = String.valueOf(currentDateTimeString);
                                            Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
                                            Calendar calendar3 = Calendar.getInstance();
                                            calendar3.setTime(d);
                                            calendar3.add(Calendar.DATE, 1);

                                            Date x = calendar3.getTime();
                                            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getType().equals("class")) {
                                                    if (i + 1 == teacherTimetable.getResult().getMessage().getTimetable().getWednesday().size()) {
                                                        if (!teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getClassName().equals("")) {
                                                            classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getClassName());
                                                            sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getSectionName());
                                                            time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getStartTime());
                                                            subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getSubject());
                                                            tv_upcomming.setText("Your Last class");
                                                        }
                                                        return;
                                                    } else {
                                                        if (!teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getClassName().equals("")) {
                                                            classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getClassName());
                                                            sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getSectionName());
                                                            time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getStartTime());
                                                            subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getSubject());
                                                        } else {
                                                            classname.setText("No Classes Available");
                                                            sectionname.setVisibility(View.GONE);
                                                            time.setVisibility(View.GONE);
                                                            subject.setVisibility(View.GONE);
                                                        }
                                                    }


                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getType().equals("lunch")) {
                                                    classname.setText("No Classes Available");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setVisibility(View.GONE);
                                                    subject.setVisibility(View.GONE);
                                                }

                                            } else if (x.equals(calendar1.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getType().equals("class")) {
                                                    if (!teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getClassName().equals("")) {
                                                        classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getClassName());
                                                        sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getSectionName());
                                                        time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getStartTime());
                                                        subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getSubject());
                                                    } else {
                                                        return;
                                                    }
                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getType().equals("lunch")) {
                                                    classname.setText("Leisure");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getStartTime());
                                                    subject.setVisibility(View.GONE);
                                                }
                                            } else if (x.equals(calendar2.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getType().equals("class")) {
                                                    if (!teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getClassName().equals("")) {
                                                        classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getClassName());
                                                        sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getSectionName());
                                                        time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getStartTime());
                                                        subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i + 1).getSubject());
                                                    } else {
                                                        return;
                                                    }
                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getType().equals("lunch")) {
                                                    classname.setText("Leisure");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getStartTime());
                                                    subject.setVisibility(View.GONE);
                                                }
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case Calendar.THURSDAY:
                                    for (int i = 0; i < teacherTimetable.getResult().getMessage().getTimetable().getThursday().size(); i++) {
                                        try {
                                            String string1 = teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getStartTime();
                                            Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
                                            Calendar calendar1 = Calendar.getInstance();
                                            calendar1.setTime(time1);
                                            calendar1.add(Calendar.DATE, 1);


                                            String string2 = teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getEndTime();
                                            Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
                                            Calendar calendar2 = Calendar.getInstance();
                                            calendar2.setTime(time2);
                                            calendar2.add(Calendar.DATE, 1);

                                            String someRandomTime = String.valueOf(currentDateTimeString);
                                            Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
                                            Calendar calendar3 = Calendar.getInstance();
                                            calendar3.setTime(d);
                                            calendar3.add(Calendar.DATE, 1);

                                            Date x = calendar3.getTime();
                                            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getType().equals("class")) {
                                                    if (i + 1 == teacherTimetable.getResult().getMessage().getTimetable().getThursday().size()) {
                                                        if (!teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getClassName().equals("")) {
                                                            classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getClassName());
                                                            sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getSectionName());
                                                            time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getStartTime());
                                                            subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getSubject());
                                                            tv_upcomming.setText("Your Last class");
                                                        }
                                                        return;
                                                    } else {
                                                        if (!teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getType().equals("lunch")) {
                                                            classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getClassName());
                                                            sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getSectionName());
                                                            time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getStartTime());
                                                            subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getSubject());
                                                        } else {
                                                            classname.setText("No Classes Available");
                                                            sectionname.setVisibility(View.GONE);
                                                            time.setVisibility(View.GONE);
                                                            subject.setVisibility(View.GONE);
                                                        }

                                                    }


                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getType().equals("lunch")) {
                                                    classname.setText("No Classes Available");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setVisibility(View.GONE);
                                                    subject.setVisibility(View.GONE);
                                                }

                                            } else if (x.equals(calendar1.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getType().equals("class")) {
                                                    if (!teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getClassName().equals("")) {
                                                        classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getClassName());
                                                        sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getSectionName());
                                                        time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getStartTime());
                                                        subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getSubject());
                                                    } else {
                                                        return;
                                                    }
                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getType().equals("lunch")) {
                                                    classname.setText("Leisure");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getStartTime());
                                                    subject.setVisibility(View.GONE);
                                                }
                                            } else if (x.equals(calendar2.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getType().equals("class")) {
                                                    if (!teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getClassName().equals("")) {
                                                        classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getClassName());
                                                        sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getSectionName());
                                                        time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getStartTime());
                                                        subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i + 1).getSubject());
                                                    } else {
                                                        return;
                                                    }
                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getType().equals("lunch")) {
                                                    classname.setText("Leisure");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getStartTime());
                                                    subject.setVisibility(View.GONE);
                                                }
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case Calendar.FRIDAY:
                                    for (int i = 0; i < teacherTimetable.getResult().getMessage().getTimetable().getFriday().size(); i++) {
                                        try {
                                            String string1 = teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getStartTime();
                                            Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
                                            Calendar calendar1 = Calendar.getInstance();
                                            calendar1.setTime(time1);
                                            calendar1.add(Calendar.DATE, 1);


                                            String string2 = teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getEndTime();
                                            Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
                                            Calendar calendar2 = Calendar.getInstance();
                                            calendar2.setTime(time2);
                                            calendar2.add(Calendar.DATE, 1);

                                            String someRandomTime = String.valueOf(currentDateTimeString);
                                            Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
                                            Calendar calendar3 = Calendar.getInstance();
                                            calendar3.setTime(d);
                                            calendar3.add(Calendar.DATE, 1);

                                            Date x = calendar3.getTime();
                                            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getType().equals("class")) {
                                                    if (i + 1 == teacherTimetable.getResult().getMessage().getTimetable().getFriday().size()) {
                                                        if (!teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getClassName().equals("")) {
                                                            classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getClassName());
                                                            sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getSectionName());
                                                            time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getStartTime());
                                                            subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getSubject());
                                                            tv_upcomming.setText("Your Last class");
                                                        }
                                                        return;
                                                    } else {
                                                        if (!teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getType().equals("lunch")) {
                                                            classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getClassName());
                                                            sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getSectionName());
                                                            time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getStartTime());
                                                            subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getSubject());
                                                        } else {
                                                            classname.setText("No Classes Available");
                                                            sectionname.setVisibility(View.GONE);
                                                            time.setVisibility(View.GONE);
                                                            subject.setVisibility(View.GONE);
                                                        }
                                                    }


                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getType().equals("lunch")) {
                                                    classname.setText("No Classes Available");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setVisibility(View.GONE);
                                                    subject.setVisibility(View.GONE);
                                                }

                                            } else if (x.equals(calendar1.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getType().equals("class")) {
                                                    if (!teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getClassName().equals("")) {
                                                        classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getClassName());
                                                        sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getSectionName());
                                                        time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getStartTime());
                                                        subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getSubject());
                                                    } else {
                                                        return;
                                                    }
                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getType().equals("lunch")) {
                                                    classname.setText("Leisure");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getStartTime());
                                                    subject.setVisibility(View.GONE);
                                                }
                                            } else if (x.equals(calendar2.getTime())) {
                                                if (teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getType().equals("class")) {
                                                    if (!teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getClassName().equals("")) {
                                                        classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getClassName());
                                                        sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getSectionName());
                                                        time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getStartTime());
                                                        subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i + 1).getSubject());
                                                    } else {
                                                        return;
                                                    }
                                                } else if (teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getType().equals("lunch")) {
                                                    classname.setText("Leisure");
                                                    sectionname.setVisibility(View.GONE);
                                                    time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getStartTime());
                                                    subject.setVisibility(View.GONE);
                                                }
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case Calendar.SATURDAY:
                                    classname.setText("ON MONDAY" + "\n" + "Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getClassName());
                                    sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getSectionName());
                                    time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getStartTime());
                                    subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getSubject());
                                    break;
                            }
                        }
                    }




                }
            }
        }
        if (apiType == WebServices.ApiType.teacherpresentattendance) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final TeacherAttendance teacherAttendance = (TeacherAttendance) response;

                if (!isSucces || teacherAttendance == null) {
                    SnackBar.makeText(getActivity(), getActivity().getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(getActivity(), "Attendance Updated", SnackBar.LENGTH_SHORT).show();
                    check_attendance.setVisibility(View.GONE);
                }
            }
        }
        if (apiType == WebServices.ApiType.teacherbyid) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final TeacherByID teacherByID = (TeacherByID) response;

                if (!isSucces || teacherByID == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (teacherByID.getResult() != null){
                        if (teacherByID.getResult().getMessage() != null){
                            classHW.add("Choose Class");
                            sectionHW.add("Choose Section");
                            subjectHW.add("Choose Subject");
                            for (int i = 0; i < teacherByID.getResult().getMessage().getClassesAlloted().size(); i++) {
                                classHW.add(teacherByID.getResult().getMessage().getClassesAlloted().get(i).getClassName());
                                sectionHW.add(teacherByID.getResult().getMessage().getClassesAlloted().get(i).getSectionName());
                            }
                            for (int i = 0; i < teacherByID.getResult().getMessage().getSpecialization().size(); i++) {
                               subjectHW.add(teacherByID.getResult().getMessage().getSpecialization().get(i));
                            }
                        }
                    }


                }
            }
        }
        if (apiType == WebServices.ApiType.sectionbyName) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getSectionbyID = (SectionByName) response;

                if (!isSucces || getSectionbyID.getResult().getMessage() == null) {
                    SnackBar.makeText(getActivity(), getActivity().getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    student_spinner.setVisibility(View.VISIBLE);
                    //SnackBar.makeText(getActivity(), "Attendance Updated", SnackBar.LENGTH_SHORT).show();
                    studentlist = new ArrayList<>();
                    studentlist.add("Select Student");
                    for (int i = 0; i < getSectionbyID.getResult().getMessage().getStudents().size(); i++) {
                        studentlist.add(getSectionbyID.getResult().getMessage().getStudents().get(i).getFirstName()
                                + " " + getSectionbyID.getResult().getMessage().getStudents().get(i).getLastName());
                    }

                    if (studentlist.size() != 0) {
                        students = getSectionbyID.getResult().getMessage().getStudents();
                        ArrayAdapter studentAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, studentlist);
                        studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        student_spinner.setAdapter(studentAdapter);
                    }
                }
            }
        }
        if (apiType == WebServices.ApiType.callexamnames) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getExamNames = (GetExamNames) response;

                if (!isSucces || getExamNames.getResult().getMessage() == null) {
                    SnackBar.makeText(getActivity(), getActivity().getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    examHW.add("Chose Exam");
                    for (int i = 0; i < getExamNames.getResult().getMessage().size(); i++) {
                        if(getExamNames.getResult().getMessage().get(i).getName() != null){
                            examHW.add(getExamNames.getResult().getMessage().get(i).getName());
                        }

                    }
                }
            }
        }if (apiType == WebServices.ApiType.allbanner) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final Homebanner homebanner = (Homebanner) response;

                if (!isSucces ||  homebanner == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    schoollat = String.valueOf(homebanner.getResult().getMessage().getLocation().getLatitude());
                    schoollon = String.valueOf(homebanner.getResult().getMessage().getLocation().getLongitude());
                    if (!schoollat.equals("") || !schoollat.isEmpty()) {
                        if (Math.floor(Double.parseDouble(schoollat) * 1000) == Math.floor(Double.parseDouble( lat+ "") * 1000) || Math.floor(Double.parseDouble(schoollon) * 1000) == Math.floor(Double.parseDouble(lang + "") * 1000)) {
                            if (!String.valueOf(lat).equals("") || currentTime.equals(teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(1).getStartTime())) {
                                check_attendance.setVisibility(View.GONE);
                            }  else {
                                check_attendance.setVisibility(View.VISIBLE);
                            }
                        } else {
                            check_attendance.setVisibility(View.VISIBLE);
                        }

                    }
                    //For First Slide
                    viewPagerAdapter = new HomeSliderViewPagerAdapter(getActivity(),homebanner);
                    viewPager.setAdapter(viewPagerAdapter);
                    dot1.setViewPager(viewPager);

                    //For First Slider Timmer
                    TimerTask timerTask1 = new TimerTask() {
                        @Override
                        public void run() {
                            viewPager.post(new Runnable(){

                                @Override
                                public void run() {
                                    viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%homebanner.getResult().getMessage().getSlider().size());
                                }
                            });
                        }
                    };
                    timer1 = new Timer();
                    timer1.schedule(timerTask1, 4400, 4400);
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.cv_homework:
                Initializepopup();
                initializdeletePopup();
                break;
            case R.id.cv_behaviour:
                Initializepopupbehaviour();
                initializdeletePopupBehaviour();
                break;
            case R.id.vC_attendance:
                startActivity(new Intent(getActivity(), TeacherAttendanceManagement.class));
                break;
            case R.id.check_attendance:
                CallTeacherattendanceService();
                break;
            case R.id.cvUpcoming:
                startActivity(new Intent(getActivity(), UpcomingActivity.class));
                break;
            case R.id.vC_result:
                Initializepopupresult();
                initializdeletePopupResult();
                break;
        }
    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void Initializepopup() {
        dialogSuccess = new Dialog(getActivity());
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_home_work);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }
    private void initializdeletePopup() {
        dialogSuccess.setContentView(R.layout.popup_home_work);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();

        Spinner class_spinner = dialogSuccess.findViewById(R.id.spinner_class);
        Spinner section_spinner = dialogSuccess.findViewById(R.id.spinner_section);
        Spinner subject_spinner = dialogSuccess.findViewById(R.id.spinner_subject);
        Button submit = dialogSuccess.findViewById(R.id.btn_submit_homework);

        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
        //        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);

        ArrayAdapter classAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, classHW);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        class_spinner.setAdapter(classAdapter);

        class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), classHW.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter sectionAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, sectionHW);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        section_spinner.setAdapter(sectionAdapter);

        section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), sectionHW.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter subjectAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, subjectHW);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        subject_spinner.setAdapter(subjectAdapter);

        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), subjectHW.get(position), Toast.LENGTH_LONG).show();
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
                    if(class_spinner.getSelectedItem().toString().equals("Choose Class")
                            ||section_spinner.getSelectedItem().toString().equals("Choose Section")||
                            subject_spinner.getSelectedItem().toString().equals("Choose Subject")){
                        SnackBar.makeText(getActivity(), "Please select class,section and subject", SnackBar.LENGTH_SHORT).show();
                    }else {
                        Fragment fragment = new HomeWorkFragment();
                        Bundle args = new Bundle();
                        args.putString("classname", class_spinner.getSelectedItem().toString());
                        args.putString("section", section_spinner.getSelectedItem().toString());
                        args.putString("subject", subject_spinner.getSelectedItem().toString());
                        fragment.setArguments(args);
                        loadFragment(fragment);
                    }

                }
            }
        });
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }


    private void Initializepopupbehaviour() {
        dialogSuccess = new Dialog(getActivity());
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_behaviour);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializdeletePopupBehaviour() {
        dialogSuccess.setContentView(R.layout.popup_behaviour);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();

        Spinner class_spinner = dialogSuccess.findViewById(R.id.spinner_class);
        Spinner section_spinner = dialogSuccess.findViewById(R.id.spinner_section);
        student_spinner = dialogSuccess.findViewById(R.id.spinner_student);
        Spinner subject_spinner = dialogSuccess.findViewById(R.id.spinner_subject);
        Button submit = dialogSuccess.findViewById(R.id.btn_submit_homework);
        student_spinner.setVisibility(View.GONE);

        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);


        ArrayAdapter classAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, classHW);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        class_spinner.setAdapter(classAdapter);

        class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cls = classHW.get(position).toString();
                Toast.makeText(getActivity(), classHW.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter sectionAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, sectionHW);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        section_spinner.setAdapter(sectionAdapter);

        section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!class_spinner.getSelectedItem().equals("Choose Class")) {
                    sec = sectionHW.get(position).toString();
                    CallsectionbyID(cls, sec);
                    Toast.makeText(getActivity(), sectionHW.get(position), Toast.LENGTH_LONG).show();
                } else {
                    SnackBar.makeText(getActivity(), "Please choose class to continue", SnackBar.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        student_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), studentlist.get(position), Toast.LENGTH_LONG).show();
                if (position != 0) {
                    student_id = students.get(position - 1).getStudentId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter subjectAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, subjectHW);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        subject_spinner.setAdapter(subjectAdapter);

        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), subjectHW.get(position), Toast.LENGTH_LONG).show();
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
                    if(class_spinner.getSelectedItem().toString().equals("Choose Class")
                            ||section_spinner.getSelectedItem().toString().equals("Choose Section")||
                            subject_spinner.getSelectedItem().toString().equals("Choose Subject")||
                            subject_spinner.getSelectedItem().toString().equals("Select Student")){
                        SnackBar.makeText(getActivity(), "Please select class,section,student and subject", SnackBar.LENGTH_SHORT).show();
                    }else {
                        Fragment fragment = new BehaviourFragment();
                        Bundle args = new Bundle();
                        args.putString("classname", class_spinner.getSelectedItem().toString());
                        args.putString("section", section_spinner.getSelectedItem().toString());
                        args.putString("student", student_spinner.getSelectedItem().toString());
                        args.putString("subject", subject_spinner.getSelectedItem().toString());
                        args.putString("student_id", student_id);
                        fragment.setArguments(args);
                        loadFragment(fragment);
                    }

                }
            }
        });

        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }


    private void Initializepopupresult() {
        dialogSuccess = new Dialog(getActivity());
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_result);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializdeletePopupResult() {
        dialogSuccess.setContentView(R.layout.popup_result);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();

        Spinner class_spinner = dialogSuccess.findViewById(R.id.spinner_class);
        Spinner section_spinner = dialogSuccess.findViewById(R.id.spinner_section);
        Spinner exam_spinner = dialogSuccess.findViewById(R.id.spinner_exam);
        Spinner subject_spinner = dialogSuccess.findViewById(R.id.spinner_subject);
        Button submit = dialogSuccess.findViewById(R.id.btn_submit_homework);

        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);


        ArrayAdapter classAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, classHW);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        class_spinner.setAdapter(classAdapter);

        class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), classHW.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter sectionAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, sectionHW);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        section_spinner.setAdapter(sectionAdapter);

        section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), sectionHW.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter subjectAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, subjectHW);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        subject_spinner.setAdapter(subjectAdapter);

        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), subjectHW.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter examAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, examHW);
        examAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        exam_spinner.setAdapter(examAdapter);

        exam_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), subjectHW.get(position), Toast.LENGTH_LONG).show();
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
                    showFileChooser();
                }
            }
        });

        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedFileURI = data.getData();
                File file = new File(selectedFileURI.getPath().toString());
                Log.d("", "File :::::::: " + file.getName());
                //CallUploadResults();
            }
        }
    }
}
