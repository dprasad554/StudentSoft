package com.geekhive.studentsoft.fragment;

/*public class NonTeachingHomeFragment {
}*/

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.AttendanceManagement;
import com.geekhive.studentsoft.activities.ClassTimeTable;
import com.geekhive.studentsoft.activities.StudentBehaviourActivity;
import com.geekhive.studentsoft.activities.StudentHomeWorkActivity;
import com.geekhive.studentsoft.activities.StudentResultsActivity;
import com.geekhive.studentsoft.adapter.HomeSliderViewPagerAdapter;
import com.geekhive.studentsoft.beans.getparentsbyid.GetParentsByID;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.getstudenttimetable.GetStudentTimetable;
import com.geekhive.studentsoft.beans.homebanner.Homebanner;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ParentsHomeFragment extends Fragment implements View.OnClickListener, OnResponseListner {


    Unbinder unbinder;
    @BindView(R.id.cv_homework)
    CardView cvHomeWork;
    @BindView(R.id.cv_behaviour)
    CardView cvBehaviour;
    @BindView(R.id.vC_attendance)
    CardView vcAttendance;
    @BindView(R.id.cvUpcoming)
    CardView cvUpcoming;
    @BindView(R.id.vC_result)
    CardView vCResult;
    @BindView(R.id.tv_subjects)
    TextView tv_subjects;
    @BindView(R.id.todaysdate)
    TextView todaysdate;
    Timer timer1;
    Dialog dialogSuccess;
    DotsIndicator dot1;
    ViewPager viewPager;
    HomeSliderViewPagerAdapter viewPagerAdapter;

    String[] classHW = {"Select Class", "Class 8", "Class 9", "Class 10", "Class 11", "Class 12"};
    String[] sectionHW = {"Select Section", "Section A", "Section B"};
    String[] subjectHW = {"Select Subject", "Maths", "Social Studies", "Science", "English"};
    String[] studentBH = {"Select Student", "K Rohit Kumar", "Vikash Yadav", "Abhijeet Aryan", "Devi Prasad"};
    private ArrayList<String> subject = new ArrayList<String>();
    private ArrayList<String> subject_list = new ArrayList<String>();
    ConnectionDetector mDetector;
    String clas, section;
    GetStudentById getStudentById;
    String currentDateandTime;
    String studentId = "";
    GetParentsByID getParentsByID;

    public ParentsHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parents_home, container, false);
        mDetector = new ConnectionDetector(getActivity());
        unbinder = ButterKnife.bind(this, view);

        cvHomeWork.setOnClickListener(this);
        cvBehaviour.setOnClickListener(this);
        dot1 = view.findViewById(R.id.dot1);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        vcAttendance.setOnClickListener(this);
        cvUpcoming.setOnClickListener(this);
        vCResult.setOnClickListener(this);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat time = new SimpleDateFormat("HH:MM", Locale.getDefault());

        currentDateandTime = sdf.format(new Date());
        String currenttime = time.format(new Time(currentTime.getTime()));

        System.out.println("currentTime::::::" + currentTime);
        todaysdate.setText(currentDateandTime);
        CallbannerService();

        CallStudentService();
        return view;
    }

    private void CallbannerService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Callbanner(WebServices.SM_Services,
                    WebServices.ApiType.allbanner, Prefs.getPrefUserAuthenticationkey(getActivity()));
            return;
        }

    }

    private void CallStudentService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetParentsByID(WebServices.SM_Services,
                    WebServices.ApiType.getparentsbyid, Prefs.getPrefRefId(getActivity()), Prefs.getPrefUserAuthenticationkey(getActivity()));
        } else {
            SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid, studentId, Prefs.getPrefUserAuthenticationkey(getActivity()));
        }
        return;
    }

    private void CallTimetable(String clas, String section) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetTimeTable(WebServices.SM_Services,
                    WebServices.ApiType.getstudenttimetable, clas, section, Prefs.getPrefUserAuthenticationkey(getActivity()));
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.getparentsbyid) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getParentsByID = (GetParentsByID) response;

                if (!isSucces || getParentsByID == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }
                if (getParentsByID.getResult() == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (getParentsByID.getResult().getMessage() != null) {
                        if (getParentsByID.getResult().getMessage().getStudentId() != null) {
                            studentId = getParentsByID.getResult().getMessage().getStudentId();
                            CallService();
                        }
                    }


                }
            }
        }
        if (apiType == WebServices.ApiType.getstudentbyid) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getStudentById = (GetStudentById) response;

                if (!isSucces || getStudentById == null) {
                    SnackBar.makeText(getActivity(), getActivity().getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (getStudentById.getResult() != null) {
                        if (getStudentById.getResult().getMessage() != null) {
                            CallTimetable(getStudentById.getResult().getMessage().getCurrentClass().getClassName(),
                                    getStudentById.getResult().getMessage().getCurrentClass().getSectionName());
                        }
                    }
                }
            }
        }
        if (apiType == WebServices.ApiType.getstudenttimetable) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final GetStudentTimetable getStudentTimetable = (GetStudentTimetable) response;

                if (!isSucces || getStudentTimetable.getResult().getMessage() == null) {
                    SnackBar.makeText(getActivity(), getActivity().getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    Calendar calander = Calendar.getInstance();
                    int day = calander.get(Calendar.DAY_OF_WEEK);
                    switch (day) {
                        case Calendar.SUNDAY:
                            tv_subjects.setText("Holiday");
                            break;
                        case Calendar.MONDAY:
                            for (int i = 0; i < getStudentTimetable.getResult().getMessage().getTimetable().getMonday().size(); i++) {
                                if (getStudentTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getType().equals("class")) {
                                    subject.add(getStudentTimetable.getResult().getMessage().getTimetable().getMonday().get(i).getSubject());
                                }
                                String listString = "";
                                for (String s : subject) {
                                    listString += s + ",";
                                }
                                tv_subjects.setText(listString);
                            }
                            break;
                        case Calendar.TUESDAY:
                            for (int i = 0; i < getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().size(); i++) {
                                if (getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getType().equals("class")) {
                                    subject.add(getStudentTimetable.getResult().getMessage().getTimetable().getTuesday().get(i).getSubject());
                                }
                                String listString = "";
                                for (String s : subject) {
                                    listString += s + ",";
                                }
                                tv_subjects.setText(listString);
                            }
                            break;
                        case Calendar.WEDNESDAY:
                            for (int i = 0; i < getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().size(); i++) {
                                if (getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getType().equals("class")) {
                                    subject.add(getStudentTimetable.getResult().getMessage().getTimetable().getWednesday().get(i).getSubject());
                                }
                                String listString = "";
                                for (String s : subject) {
                                    listString += s + ",";
                                }
                                tv_subjects.setText(listString);
                            }
                            break;
                        case Calendar.THURSDAY:
                            for (int i = 0; i < getStudentTimetable.getResult().getMessage().getTimetable().getThursday().size(); i++) {
                                if (getStudentTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getType().equals("class")) {
                                    subject.add(getStudentTimetable.getResult().getMessage().getTimetable().getThursday().get(i).getSubject());
                                }
                                String listString = "";
                                for (String s : subject) {
                                    listString += s + ",";
                                }
                                tv_subjects.setText(listString);
                            }
                            break;
                        case Calendar.FRIDAY:
                            for (int i = 0; i < getStudentTimetable.getResult().getMessage().getTimetable().getFriday().size(); i++) {
                                if (getStudentTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getType().equals("class")) {
                                    subject.add(getStudentTimetable.getResult().getMessage().getTimetable().getFriday().get(i).getSubject());
                                }
                                String listString = "";
                                for (String s : subject) {
                                    listString += s + ",";
                                }
                                tv_subjects.setText(listString);
                            }
                            break;
                        case Calendar.SATURDAY:
                            tv_subjects.setText("Holiday");
                            break;
                    }
                }
            }
        }
        if (apiType == WebServices.ApiType.allbanner) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final Homebanner homebanner = (Homebanner) response;

                if (!isSucces || homebanner == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    //For First Slide
                    viewPagerAdapter = new HomeSliderViewPagerAdapter(getActivity(), homebanner);
                    viewPager.setAdapter(viewPagerAdapter);
                    dot1.setViewPager(viewPager);

                    //For First Slider Timmer
                    TimerTask timerTask1 = new TimerTask() {
                        @Override
                        public void run() {
                            viewPager.post(new Runnable() {

                                @Override
                                public void run() {
                                    viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % homebanner.getResult().getMessage().getSlider().size());
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
                if (getStudentById == null) {
                    SnackBar.makeText(getActivity(), "Student Not Assign ", SnackBar.LENGTH_SHORT).show();
                } else {
                    if (getStudentById.getResult() == null) {
                    }
                    if (getStudentById.getResult().getMessage() == null) {
                    }
                    if (getStudentById.getResult().getMessage().getBusDetails() == null) {
                    } else {
                        Intent j = new Intent(getActivity(), StudentHomeWorkActivity.class);
                        j.putExtra("class", getStudentById.getResult().getMessage().getCurrentClass().getClassName());
                        j.putExtra("section", getStudentById.getResult().getMessage().getCurrentClass().getSectionName());
                        getActivity().startActivity(j);
                    }
                }
                break;
            case R.id.cv_behaviour:
                startActivity(new Intent(getActivity(), StudentBehaviourActivity.class));
                break;
            case R.id.vC_attendance:
                startActivity(new Intent(getActivity(), AttendanceManagement.class));
                break;
            case R.id.cvUpcoming:
                if (getStudentById == null) {
                    SnackBar.makeText(getActivity(), "Student Not Assign ", SnackBar.LENGTH_SHORT).show();
                } else {
                    if (getStudentById.getResult() == null) {
                    }
                    if (getStudentById.getResult().getMessage() == null) {
                    }
                    if (getStudentById.getResult().getMessage().getBusDetails() == null) {
                    } else {
                        Intent i = new Intent(getActivity(), ClassTimeTable.class);
                        i.putExtra("class", getStudentById.getResult().getMessage().getCurrentClass().getClassName());
                        i.putExtra("section", getStudentById.getResult().getMessage().getCurrentClass().getSectionName());
                        getActivity().startActivity(i);
                    }
                }
                break;
            case R.id.vC_result:
                startActivity(new Intent(getActivity(), StudentResultsActivity.class));
                /*Initializepopupresult();
                initializdeletePopupResult();*/
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
                Toast.makeText(getActivity(), classHW[position], Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(), sectionHW[position], Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(), subjectHW[position], Toast.LENGTH_LONG).show();
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
                    startActivity(new Intent(getActivity(), StudentHomeWorkActivity.class));
                   /* Fragment fragment = new HomeWorkFragment();
                    loadFragment(fragment);*/
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
        Spinner student_spinner = dialogSuccess.findViewById(R.id.spinner_student);
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
                Toast.makeText(getActivity(), classHW[position], Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(), sectionHW[position], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter studentAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, studentBH);
        studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        student_spinner.setAdapter(studentAdapter);

        student_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), studentBH[position], Toast.LENGTH_LONG).show();
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

                    Fragment fragment = new BehaviourFragment();
                    loadFragment(fragment);
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
        Spinner student_spinner = dialogSuccess.findViewById(R.id.spinner_student);
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
                Toast.makeText(getActivity(), classHW[position], Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(), sectionHW[position], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter studentAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, studentBH);
        studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        student_spinner.setAdapter(studentAdapter);

        student_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), studentBH[position], Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(), subjectHW[position], Toast.LENGTH_LONG).show();
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
                    Initializepopupmarks();
                    initializdeletePopupMarks();
                }
            }
        });

        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }

    private void Initializepopupmarks() {
        dialogSuccess = new Dialog(getActivity());
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_marks);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializdeletePopupMarks() {
        dialogSuccess.setContentView(R.layout.popup_marks);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();

        Button btn_submit_marks = dialogSuccess.findViewById(R.id.btn_submit_marks);
        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);

        btn_submit_marks.setOnClickListener(new View.OnClickListener() {
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

