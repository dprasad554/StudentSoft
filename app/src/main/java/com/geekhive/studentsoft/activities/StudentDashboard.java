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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.LibraryAdapter;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.fragment.BehaviourFragment;
import com.geekhive.studentsoft.fragment.DriverHomeFragment;
import com.geekhive.studentsoft.fragment.HomeFragment;
import com.geekhive.studentsoft.fragment.HomeWorkFragment;
import com.geekhive.studentsoft.fragment.ParentsHomeFragment;
import com.geekhive.studentsoft.fragment.StudentHomeFragment;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class StudentDashboard extends AppCompatActivity implements OnResponseListner {
    BottomAppBar bar;
    BottomSheetDialog bottomSheetDialog;
    Dialog dialogSuccess;
    GetStudentById getStudentById;
    ConnectionDetector mDetector;
    private ArrayList<String> dates = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_student_dashboard);
        mDetector = new ConnectionDetector(this);
        if (Prefs.getUserType(this).equals("")){
            String User_ID = Prefs.getUserType(this);
            System.out.println("User ID:::::"+User_ID );
            startActivity(new Intent(this,LoginActivity.class));
            StudentDashboard.this.finish();
            return;
        }

        bar = findViewById(R.id.bar);

        //This will add the OptionMenu to BottomAppBar
        bar.replaceMenu(R.menu.bottom_bar_student_menu);

        //This will handle the onClick Action for the menu item
        bar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Fragment fragment = null;
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.attendance:
                        Intent j = new Intent(StudentDashboard.this, AttendanceManagement.class);
                        startActivity(j);
                        break;
                    case R.id.upcoming_class:
                        Intent i = new Intent(StudentDashboard.this, ClassTimeTable.class);
                        if(getStudentById != null){
                            i.putExtra("class", getStudentById.getResult().getMessage().getCurrentClass().getClassName());
                            i.putExtra("section", getStudentById.getResult().getMessage().getCurrentClass().getSectionName());
                            StudentDashboard.this.startActivity(i);
                        }else {
                            SnackBar.makeText(StudentDashboard.this, "No Time Table Added", SnackBar.LENGTH_SHORT).show();
                        }
                         break;
                    case R.id.notification:
                        startActivity(new Intent(StudentDashboard.this, NotificationActivity.class));
                        break;
                }
                return loadFragment(fragment);
            }
        });

        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavigationMenu();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboard.this, StudentDashboard.class));
               /* Fragment fragment = null;
                fragment = new StudentHomeFragment();
                loadFragment(fragment);*/
            }
        });
        Fragment fragment = null;
        fragment = new StudentHomeFragment();
        loadFragment(fragment);
        bar = findViewById(R.id.bar);
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(DriverDashboard.this, DriverDashboard.class));
                openNavigationMenu();
            }
        });
        CallService();

    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid, Prefs.getPrefRefId(this),Prefs.getPrefUserAuthenticationkey(this));
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

                if (!isSucces || getStudentById == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (getStudentById.getResult() != null){
                        if (getStudentById.getResult().getMessage() != null){
                            SnackBar.makeText(this, getStudentById.getResult().getMessage().getFirstName(), SnackBar.LENGTH_SHORT).show();
                        }
                    }

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

    private void openNavigationMenu() {

        //this will get the menu layout
        final View bootomNavigation = getLayoutInflater().inflate(R.layout.student_navigation_menu,null);
        bottomSheetDialog = new BottomSheetDialog(StudentDashboard.this);
        bottomSheetDialog.setContentView(bootomNavigation);
        bottomSheetDialog.show();

        //this will find NavigationView from id
        NavigationView navigationView = bootomNavigation.findViewById(R.id.navigation_menu);

        //This will handle the onClick Action for the menu item
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.hw:
                        Intent j = new Intent(StudentDashboard.this, StudentHomeWorkActivity.class);
                        j.putExtra("class", getStudentById.getResult().getMessage().getCurrentClass().getClassName());
                        j.putExtra("section", getStudentById.getResult().getMessage().getCurrentClass().getSectionName());
                        startActivity(j);
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.results:
                        startActivity(new Intent(StudentDashboard.this, StudentResultsActivity.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.exam:
                        startActivity(new Intent(StudentDashboard.this, ExamListActivity.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.syllabus:
                        startActivity(new Intent(StudentDashboard.this, SyllabusActivity.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.teachertt:
                        startActivity(new Intent(StudentDashboard.this, TeacherTimeTableActivity.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.scalendar:
                        startActivity(new Intent(StudentDashboard.this, SchoolCalendar.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.station:
                        startActivity(new Intent(StudentDashboard.this, StationaryActivity.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.history:
                        startActivity(new Intent(StudentDashboard.this, StationaryHistory.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.libry:
                        startActivity(new Intent(StudentDashboard.this, LibraryActivity.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.events:
                        startActivity(new Intent(StudentDashboard.this, EventsActivity.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.leave:
                        startActivity(new Intent(StudentDashboard.this, LeaveManagementStudent.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.chngpwd:
                        startActivity(new Intent(StudentDashboard.this, ChangePassword.class));
                        bottomSheetDialog.dismiss();
                        break;

                    case R.id.notice:
                        startActivity(new Intent(StudentDashboard.this, NoticeActivity.class));
                        bottomSheetDialog.dismiss();
                        break;

                    case R.id.logout:
                        Initializepopup();
                        initializdeletePopup();
                        bottomSheetDialog.dismiss();
                        break;
                }
                return loadFragment(fragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    private void Initializepopup() {
        dialogSuccess = new Dialog(this);
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_success);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializdeletePopup() {
        dialogSuccess.setContentView(R.layout.popup_success);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
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
                    Prefs.setUserId(StudentDashboard.this, null);
                    Prefs.setUserType(StudentDashboard.this, null);
                    startActivity(new Intent(StudentDashboard.this, LoginActivity.class));
                    StudentDashboard.this.finish();
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

