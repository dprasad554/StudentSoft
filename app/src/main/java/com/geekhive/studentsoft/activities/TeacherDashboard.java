package com.geekhive.studentsoft.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.loginout.LoginOut;
import com.geekhive.studentsoft.beans.teacherbyid.TeacherByID;
import com.geekhive.studentsoft.fragment.BehaviourFragment;
import com.geekhive.studentsoft.fragment.HomeFragment;
import com.geekhive.studentsoft.fragment.HomeWorkFragment;
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

public class TeacherDashboard extends AppCompatActivity implements OnResponseListner {
    BottomAppBar bar;
    BottomSheetDialog bottomSheetDialog;
    Dialog dialogSuccess;
    private ArrayList<String> classHW = new ArrayList<String>();
    private ArrayList<String> sectionHW = new ArrayList<String>();
    private ArrayList<String> subjectHW = new ArrayList<String>();
    ConnectionDetector mDetector;
    TeacherByID teacherByID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_teacher_dashboard);
        mDetector = new ConnectionDetector(this);
        String User_type = Prefs.getUserType(this);
        System.out.println("User ID:::::"+User_type );

        if (Prefs.getUserType(this).equals("")){
            String User_ID = Prefs.getUserType(this);
            System.out.println("User ID:::::"+User_ID );
            startActivity(new Intent(this,LoginActivity.class));
            TeacherDashboard.this.finish();
            return;
        }

        bar = findViewById(R.id.bar);

        //This will add the OptionMenu to BottomAppBar
        bar.replaceMenu(R.menu.bottom_bar_menu);

        //This will handle the onClick Action for the menu item
        bar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Fragment fragment = null;
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.attendance:
                        startActivity(new Intent(TeacherDashboard.this, TeacherAttendanceManagement.class));
                        break;
                    case R.id.upcoming_class:
                        startActivity(new Intent(TeacherDashboard.this, UpcomingActivity.class));
                        break;
                    case R.id.notification:
                        startActivity(new Intent(TeacherDashboard.this, NoticeActivity.class));
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
                Fragment fragment = null;
                fragment = new HomeFragment();
                loadFragment(fragment);
            }
        });

        Fragment fragment = null;
        fragment = new HomeFragment();
        loadFragment(fragment);
        CallTeacherbyIdService();
    }

    private void CallTeacherbyIdService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Teacherbyid(WebServices.SM_Services,
                    WebServices.ApiType.teacherbyid,Prefs.getPrefRefId(this),Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.teacherbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
               teacherByID = (TeacherByID) response;

                if (!isSucces || teacherByID == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (teacherByID != null){
                        if (teacherByID.getResult() != null){
                            if (teacherByID.getResult().getMessage() != null){
                                classHW.add("Choose Class");
                                sectionHW.add("Choose Section");
                                subjectHW.add("Choose Subject");
                                for (int i = 0; i < teacherByID.getResult().getMessage().getClassesAlloted().size();i++) {
                                    classHW.add(teacherByID.getResult().getMessage().getClassesAlloted().get(i).getClassName());
                                }
                                for (int i = 0; i < teacherByID.getResult().getMessage().getClassesAlloted().size();i++) {
                                    sectionHW.add(teacherByID.getResult().getMessage().getClassesAlloted().get(i).getSectionName());
                                }
                                for (int i = 0; i < teacherByID.getResult().getMessage().getSpecialization().size();i++) {
                                    subjectHW.add(teacherByID.getResult().getMessage().getSpecialization().get(i));
                                }
                            }
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
        final View bootomNavigation = getLayoutInflater().inflate(R.layout.navigation_menu,null);
        bottomSheetDialog = new BottomSheetDialog(TeacherDashboard.this);
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
                    case R.id.item1:
                        Initializepopup();
                        initializdeletePopup();
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.item2:
                        startActivity(new Intent(TeacherDashboard.this, BehaviourList.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.item3:
                        Intent i = new Intent(TeacherDashboard.this, UpcomingActivity.class);
                        TeacherDashboard.this.startActivity(i);
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.item4:
                        startActivity(new Intent(TeacherDashboard.this, LeaveManagement.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.item5:
                        startActivity(new Intent(TeacherDashboard.this, SchoolCalendar.class));
                        // startActivity(new Intent(TeacherDashboard.this, SchoolCalendar.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.station:
                        startActivity(new Intent(TeacherDashboard.this, StationaryActivity.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.history:
                        startActivity(new Intent(TeacherDashboard.this, StationaryHistory.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.item6:
                        startActivity(new Intent(TeacherDashboard.this, ChangePassword.class));
                        bottomSheetDialog.dismiss();
                        break;

                    case R.id.notice:
                        startActivity(new Intent(TeacherDashboard.this, NoticeActivity.class));
                        bottomSheetDialog.dismiss();
                        break;

                    case R.id.item7:
                        Initializelogoutpopup();
                        initializlogoutPopup();
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


        ArrayAdapter classAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, classHW);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        class_spinner.setAdapter(classAdapter);

        class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TeacherDashboard.this, classHW.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter sectionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sectionHW);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        section_spinner.setAdapter(sectionAdapter);

        section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TeacherDashboard.this, sectionHW.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter subjectAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, subjectHW);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        subject_spinner.setAdapter(subjectAdapter);

        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TeacherDashboard.this, subjectHW.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSuccess.isShowing()) {
                    Intent intent = new Intent(TeacherDashboard.this, Homeworklist.class);
                    intent.putExtra("clas", class_spinner.getSelectedItem().toString());
                    intent.putExtra("section", section_spinner.getSelectedItem().toString());
                    intent.putExtra("subject", subject_spinner.getSelectedItem().toString());
                    startActivity(intent);
                    dialogSuccess.dismiss();
                }
            }
        });
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }


    private void Initializelogoutpopup() {
        dialogSuccess = new Dialog(this);
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_success);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializlogoutPopup() {
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
                    Prefs.setUserId(TeacherDashboard.this, null);
                    Prefs.setUserType(TeacherDashboard.this, null);
                    startActivity(new Intent(TeacherDashboard.this, LoginActivity.class));
                    TeacherDashboard.this.finish();
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

