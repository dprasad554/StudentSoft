package com.geekhive.studentsoft.fragment;


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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.AttendanceManagement;
import com.geekhive.studentsoft.activities.ClassTimeTable;
import com.geekhive.studentsoft.activities.LoginActivity;
import com.geekhive.studentsoft.activities.StudentBehaviourActivity;
import com.geekhive.studentsoft.activities.StudentHomeWorkActivity;
import com.geekhive.studentsoft.activities.StudentResultsActivity;
import com.geekhive.studentsoft.activities.TeacherDashboard;
import com.geekhive.studentsoft.activities.UpcomingActivity;
import com.geekhive.studentsoft.adapter.HomeSliderViewPagerAdapter;
import com.geekhive.studentsoft.adapter.SchoollistAdapter;
import com.geekhive.studentsoft.adapter.SliderImageAdapter;
import com.geekhive.studentsoft.adapter.StationaryAdapter;
import com.geekhive.studentsoft.beans.addhomework.AddHomework;
import com.geekhive.studentsoft.beans.callapplication.AcademicYear;
import com.geekhive.studentsoft.beans.callapplication.CallApplication;
import com.geekhive.studentsoft.beans.edithomework.EditHomeWork;
import com.geekhive.studentsoft.beans.getallapplication.GetAllApplication;
import com.geekhive.studentsoft.beans.getallapplication.InterviewDetail;
import com.geekhive.studentsoft.beans.guestoparent.GuestParent;
import com.geekhive.studentsoft.beans.homebanner.Homebanner;
import com.geekhive.studentsoft.beans.homeworklistresponce.HomeworkListResponce;
import com.geekhive.studentsoft.beans.schoollist.Message;
import com.geekhive.studentsoft.beans.schoollist.SchoolList;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GuestHomeFragment extends Fragment implements OnResponseListner {

    Unbinder unbinder;
    @BindView(R.id.vr_schoollist)
    RecyclerView vr_schoollist;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.dot1)
    DotsIndicator dot1;
    SchoollistAdapter schoollistAdapter;
    HomeSliderViewPagerAdapter viewPagerAdapter;
    Timer timer1;
    Dialog dialogSuccess;

    CardView vC_attendance;
    TextView tv_mode, tv_date, tv_time, tv_results, tv_pay;
    String db_key;

    int end = 0;
    int sta = 0;

    ConnectionDetector mDetector;
    List<Message> schools;
    SchoolList schoolList;

    public GuestHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guest_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        mDetector = new ConnectionDetector(getActivity());
        db_key = Prefs.getPrefUserAuthenticationkey(getActivity());
        System.out.println("db_key:::" + Prefs.getPrefUserAuthenticationkey(getActivity()));
        vC_attendance = view.findViewById(R.id.vC_attendance);
        tv_mode = view.findViewById(R.id.tv_mode);
        tv_date = view.findViewById(R.id.tv_date);
        tv_time = view.findViewById(R.id.tv_time);
        tv_results = view.findViewById(R.id.tv_results);
        tv_pay = view.findViewById(R.id.tv_pay);
        if (!Prefs.getPrefUserAuthenticationkey(getActivity()).equals("")) {
            CallGetallApplication();
        } else {
            vC_attendance.setVisibility(View.GONE);
        }

        CallbannerService();
        CallSchoollist();



        return view;
    }

    private void CallbannerService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Callbanner(WebServices.SM_Services,
                    WebServices.ApiType.allbanner, Prefs.getPrefUserAuthenticationkey(getActivity()));
            return;
        }

    }

    private void CallSchoollist() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Schoollist(WebServices.SM_Services,
                    WebServices.ApiType.getallschool);
            return;
        }

    }

    private void CallGuestToParent() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).ConvertGuestoParent(WebServices.SM_Services,
                    WebServices.ApiType.gtop, Prefs.getPrefRefId(getActivity()), Prefs.getPrefUserAuthenticationkey(getActivity()));
            return;
        }

    }

    private void CallGetallApplication() {
        if (this.mDetector.isConnectingToInternet()) {
            CallApplication callApplication = new CallApplication();
            AcademicYear academicYear = new AcademicYear();
            academicYear.setEnd(end);
            academicYear.setStart(sta);
            callApplication.setAcademicYear(academicYear);
            callApplication.setAppliedForClass("");
            callApplication.setStatus("");
            callApplication.setAppliedBy(Prefs.getPrefRefId(getActivity()));
            new WebServices(this).GetallApplication(WebServices.SM_Services,
                    WebServices.ApiType.getallapplication, callApplication, Prefs.getPrefUserAuthenticationkey(getActivity()));
            return;
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.allbanner) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final Homebanner homebanner = (Homebanner) response;

                if (!isSucces || homebanner != null) {
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
        if (apiType == WebServices.ApiType.getallschool) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
               schoolList = (SchoolList) response;

                if (!isSucces || schoolList.getResult().getMessage().size() == 0) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    for(int j=0; j<schoolList.getResult().getMessage().size(); j++){
                        schools = new ArrayList<>();
                        if(db_key.equals(schoolList.getResult().getMessage().get(j).getDbKey())){

                        }else {
                            schools.addAll(schoolList.getResult().getMessage());
                        }

                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    vr_schoollist.setLayoutManager(linearLayoutManager);
                    schoollistAdapter = new SchoollistAdapter(getActivity(), schools);
                    vr_schoollist.setAdapter(schoollistAdapter);
                }
            }
        } if (apiType == WebServices.ApiType.gtop) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final GuestParent guestParent = (GuestParent) response;
                Initializlogoepopup();
                initializdeletePopup();
                /*if (guestParent.getResult().getMessage() == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    Initializlogoepopup();
                    initializdeletePopup();
                }*/
            }
        }
        if (apiType == WebServices.ApiType.getallapplication) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final GetAllApplication getAllApplication = (GetAllApplication) response;

                if (!isSucces || getAllApplication == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    for (int j = 0; j < getAllApplication.getResult().getMessage().size(); j++) {
                        if (getAllApplication.getResult().getMessage().get(j).getAppliedBy().equals(Prefs.getPrefRefId(getActivity()))) {
                            if (getAllApplication.getResult().getMessage().get(j).getInterviewDetails().size() != 0){
                                if(getAllApplication.getResult().getMessage().get(j).getInterviewDetails().get(0).getResult().equals("") ||
                                        getAllApplication.getResult().getMessage().get(j).getInterviewDetails().get(0).getResult().isEmpty()){
                                    vC_attendance.setVisibility(View.VISIBLE);
                                    tv_mode.setText("Interview Mode : " + getAllApplication.getResult().getMessage().get(j).getInterviewDetails().get(0).getMode());
                                    tv_date.setText("Date : " + getAllApplication.getResult().getMessage().get(j).getInterviewDetails().get(0).getDate());
                                    tv_time.setText("Time : " + getAllApplication.getResult().getMessage().get(j).getInterviewDetails().get(0).getTime());
                                    tv_results.setVisibility(View.GONE);
                                    //tv_results.setText("Result : " + getAllApplication.getResult().getMessage().get(j).getInterviewDetails().get(0).getResult());
                                } else {
                                    vC_attendance.setVisibility(View.VISIBLE);
                                    tv_mode.setText("Interview Mode : " + getAllApplication.getResult().getMessage().get(j).getInterviewDetails().get(0).getMode());
                                    tv_date.setText("Date : " + getAllApplication.getResult().getMessage().get(j).getInterviewDetails().get(0).getDate());
                                    tv_time.setText("Time : " + getAllApplication.getResult().getMessage().get(j).getInterviewDetails().get(0).getTime());
                                    tv_results.setText("Result : " + getAllApplication.getResult().getMessage().get(j).getInterviewDetails().get(0).getResult());
                                    if (getAllApplication.getResult().getMessage().get(j).getInterviewDetails().get(0).getResult().equals("Pass") || getAllApplication.getResult().getMessage().get(j).getInterviewDetails().get(0).getResult().equals("pass")) {
                                        Initializepopup();
                                        initializlogoutPopup();
                                    } else {
                                        tv_pay.setVisibility(View.VISIBLE);
                                        String payText = "Sorry! You could not pass the interview. Please try with some other schools available";
                                        tv_pay.setText(payText);
                                    }
                                }

                            } else {
                                vC_attendance.setVisibility(View.GONE);
                            }

                        }
                    }


                }
            }
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
        dialogSuccess.setContentView(R.layout.popup_interview);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializlogoutPopup() {
        dialogSuccess.setContentView(R.layout.popup_interview);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();

        final Button logout = dialogSuccess.findViewById(R.id.btn_submit_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallGuestToParent();
            }
        });

        int width = getActivity().getResources().getDisplayMetrics().widthPixels - 100;
        int height = getActivity().getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);


        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(false);
        dialogSuccess.setCanceledOnTouchOutside(false);
        dialogSuccess.show();
    }

    private void Initializlogoepopup() {
        dialogSuccess = new Dialog(getActivity());
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_success);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializdeletePopup() {
        dialogSuccess.setContentView(R.layout.popup_success);
        dialogSuccess.setCancelable(false);
        dialogSuccess.setCanceledOnTouchOutside(false);
        dialogSuccess.show();
        final TextView vT_psd_yes = (TextView) dialogSuccess.findViewById(R.id.vT_psd_yes);
        final TextView vT_psd_no = (TextView) dialogSuccess.findViewById(R.id.vT_psd_no);
        final TextView vT_sucess = dialogSuccess.findViewById(R.id.vT_sucess);
        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);

        vT_psd_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSuccess.isShowing()) {
                    dialogSuccess.dismiss();
                    Prefs.setUserId(getActivity(), null);
                    Prefs.setUserType(getActivity(), null);
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        vT_psd_no.setOnClickListener(new View.OnClickListener() {
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
