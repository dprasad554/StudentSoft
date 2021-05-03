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
import com.geekhive.studentsoft.fragment.BehaviourFragment;
import com.geekhive.studentsoft.fragment.GuestHomeFragment;
import com.geekhive.studentsoft.fragment.HomeFragment;
import com.geekhive.studentsoft.fragment.HomeWorkFragment;
import com.geekhive.studentsoft.fragment.NonTeachingHomeFragment;
import com.geekhive.studentsoft.fragment.StudentHomeFragment;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class NonTeachingStaffDashboard extends AppCompatActivity {
    BottomAppBar bar;
    BottomSheetDialog bottomSheetDialog;
    Dialog dialogSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_guest_dashboard);
        //SnackBar.makeText(NonTeachingStaffDashboard.this, "Non Teaching Login", SnackBar.LENGTH_SHORT).show();
        if (Prefs.getUserType(this).equals("")) {
            String User_ID = Prefs.getUserType(this);
            System.out.println("User ID:::::" + User_ID);
            startActivity(new Intent(this, LoginActivity.class));
            NonTeachingStaffDashboard.this.finish();
            return;
        }

        FloatingActionButton fab = findViewById(R.id.fav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new NonTeachingHomeFragment();
                loadFragment(fragment);
                NonTeachingStaffDashboard.this.finish();
            }
        });

        Fragment fragment = null;
        fragment = new NonTeachingHomeFragment();
        loadFragment(fragment);
        bar = findViewById(R.id.bar);
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavigationMenu();
            }
        });
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
        final View bootomNavigation = getLayoutInflater().inflate(R.layout.nonteaching_navigation_menu, null);
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

                    case R.id.attendance:
                        startActivity(new Intent(NonTeachingStaffDashboard.this, AttendanceManagement.class));
                        bottomSheetDialog.dismiss();
                        break;

                    case R.id.notice:
                        startActivity(new Intent(NonTeachingStaffDashboard.this, NoticeActivity.class));
                        bottomSheetDialog.dismiss();
                        break;

                    case R.id.leave:
                        startActivity(new Intent(NonTeachingStaffDashboard.this, LeaveManagement.class));
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.item6:
                        startActivity(new Intent(NonTeachingStaffDashboard.this, ChangePassword.class));
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
                    Prefs.setUserId(NonTeachingStaffDashboard.this, null);
                    Prefs.setUserType(NonTeachingStaffDashboard.this, null);
                    startActivity(new Intent(NonTeachingStaffDashboard.this, LoginActivity.class));
                    NonTeachingStaffDashboard.this.finish();
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

