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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.NoticeAdapter;
import com.geekhive.studentsoft.beans.getparentsbyid.GetParentsByID;
import com.geekhive.studentsoft.beans.notice.NoticeData;
import com.geekhive.studentsoft.fragment.ParentsHomeFragment;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class ParentsDashboard extends AppCompatActivity implements OnResponseListner {
    BottomAppBar bar;
    BottomSheetDialog bottomSheetDialog;
    Dialog dialogSuccess;
    ConnectionDetector mDetector;
    GetParentsByID getParentsByID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_guest_dashboard);
        mDetector = new ConnectionDetector(this);
        //SnackBar.makeText(NonTeachingStaffDashboard.this, "Non Teaching Login", SnackBar.LENGTH_SHORT).show();
        if (Prefs.getUserType(this).equals("")) {
            String User_ID = Prefs.getUserType(this);
            System.out.println("User ID:::::" + User_ID);
            startActivity(new Intent(this, LoginActivity.class));
            ParentsDashboard.this.finish();
            return;
        }
        CallParentsById();
        FloatingActionButton fab = findViewById(R.id.fav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentsDashboard.this, ParentsDashboard.class));
            }
        });

        Fragment fragment = null;
        fragment = new ParentsHomeFragment();
        loadFragment(fragment);
        bar = findViewById(R.id.bar);
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavigationMenu();
            }
        });
    }
    private void CallParentsById() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetParentsByID(WebServices.SM_Services,
                    WebServices.ApiType.getparentsbyid, Prefs.getPrefRefId(this), Prefs.getPrefUserAuthenticationkey(this));
        } else {
            SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.getparentsbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getParentsByID = (GetParentsByID) response;

                if (!isSucces || getParentsByID == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }
                if (getParentsByID.getResult() == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (getParentsByID.getResult().getMessage() != null) {
                        if (getParentsByID.getResult().getMessage().getStudentId() != null) {

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
        final View bootomNavigation = getLayoutInflater().inflate(R.layout.parents_navigation_menu, null);
        bottomSheetDialog = new BottomSheetDialog(this);
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
                switch (id) {
                    case R.id.logout:
                        Initializepopup();
                        initializdeletePopup();
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.feesummary:
                        startActivity(new Intent(ParentsDashboard.this, SudentFeeStructure.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.stationary:
                        startActivity(new Intent(ParentsDashboard.this, StationaryActivity.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.history:
                        startActivity(new Intent(ParentsDashboard.this, StationaryHistory.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.events:
                        startActivity(new Intent(ParentsDashboard.this, EventsActivity.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.exam_results:
                        Intent i = new Intent(ParentsDashboard.this, StudentResultsActivity.class);
                        i.putExtra("id", getParentsByID.getResult().getMessage().getStudentId());
                        startActivity(i);
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.notification:
                        startActivity(new Intent(ParentsDashboard.this, NotificationActivity.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.bustracking:
                        startActivity(new Intent(ParentsDashboard.this, DriverDetailsForParents.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.attendance:
                        Intent j = new Intent(ParentsDashboard.this, AttendanceManagement.class);
                        j.putExtra("id", getParentsByID.getResult().getMessage().getStudentId());
                        startActivity(j);
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.leave:
                        Intent leave = new Intent(ParentsDashboard.this, LeaveManagementStudent.class);
                        leave.putExtra("id", getParentsByID.getResult().getMessage().getStudentId());
                        startActivity(leave);
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.homework:
                        Intent homework = new Intent(ParentsDashboard.this, StudentHomeWorkActivity.class);
                        homework.putExtra("id", getParentsByID.getResult().getMessage().getStudentId());
                        startActivity(homework);
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.behaviour:
                        Intent behaviour = new Intent(ParentsDashboard.this, StudentBehaviourActivity.class);
                        behaviour.putExtra("id", getParentsByID.getResult().getMessage().getStudentId());
                        startActivity(behaviour);
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.item6:
                        startActivity(new Intent(ParentsDashboard.this, ChangePassword.class));
                        bottomSheetDialog.dismiss();
                        break;

                    case R.id.notice:
                        startActivity(new Intent(ParentsDashboard.this, NoticeActivity.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.profile:
                        Intent intent = new Intent(ParentsDashboard.this, StudentProfile.class);
                        intent.putExtra("id", getParentsByID.getResult().getMessage().getStudentId());
                        startActivity(intent);
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
                    Prefs.setUserId(ParentsDashboard.this, null);
                    Prefs.setUserType(ParentsDashboard.this, null);
                    startActivity(new Intent(ParentsDashboard.this, LoginActivity.class));
                    ParentsDashboard.this.finish();
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            ParentsDashboard.this.finish();

        }
        return super.onKeyDown(keyCode, event);
    }
}

