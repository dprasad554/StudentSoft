package com.geekhive.studentsoft.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.google.android.material.snackbar.Snackbar;

public class SplashScreen extends AppCompatActivity {
    private static final int REQUEST_FOR_STORAGE_PERMISSION = 123;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 88;
    Handler mHandlerTime;
    Runnable mRunnableTimeOut;

    private int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_splash_screen);
        setvalues();

    }

    private void setvalues() {
        if (isTaskRoot() || !getIntent().hasCategory("android.intent.category.LAUNCHER") || getIntent().getAction() == null || !getIntent().getAction().equals("android.intent.action.MAIN")) {
            if (mayRequestReadWriteSd()) {
                if (checkLocationPermission()){
                    /* if (checkAccountsPermission()){*/
                    splashScreenCall();
                    /*}*/
                }

                return;
            } else {
                Log.e(" Permission", "enteredwrong");
                return;
            }
        }
        finish();
    }
    private void splashScreenCall() {
        mHandlerTime = new Handler();
        mRunnableTimeOut = new Runnable() {
            @Override
            public void run() {
                if (Prefs.getUserType(SplashScreen.this).equals("teacher")){
                    Intent i = new Intent(SplashScreen.this, TeacherDashboard.class);
                    startActivity(i);
                    finish();
                }if(Prefs.getUserType(SplashScreen.this).equals("student")) {
                    Intent i = new Intent(SplashScreen.this, StudentDashboard.class);
                    startActivity(i);
                    finish();
                }if(Prefs.getUserType(SplashScreen.this).equals("guest")) {
                    Intent i = new Intent(SplashScreen.this, GuestuserDashboard.class);
                    startActivity(i);
                    finish();
                } if(Prefs.getUserType(SplashScreen.this).equals("parent")) {
                    Intent i = new Intent(SplashScreen.this, ParentsDashboard.class);
                    startActivity(i);
                    finish();
                } if(Prefs.getUserType(SplashScreen.this).equals("driver")) {
                    Intent i = new Intent(SplashScreen.this, DriverDashboard.class);
                    startActivity(i);
                    finish();
                } if(Prefs.getUserType(SplashScreen.this).equals("Stationary")) {
                    Intent i = new Intent(SplashScreen.this, StationeryOwnerDashboard.class);
                    startActivity(i);
                    finish();
                }  else if (Prefs.getUserType(SplashScreen.this).equals("")){
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        };
    }
    private boolean mayRequestReadWriteSd() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(SplashScreen.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        requestPermissions(
                new String[]{Manifest.permission
                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_FOR_STORAGE_PERMISSION);
        return false;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_permission)
                        //.setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(SplashScreen.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FOR_STORAGE_PERMISSION:
                Log.e("Permission", "entered");
                if (grantResults.length > 0) {
                    Log.e("Permission", "entered1");
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        if (checkLocationPermission()){
                            splashScreenCall();
                        }
                        Log.e("Permission", "entered2");
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showPermissionRationaleSnackBar();
                        } else {
                            Toast.makeText(SplashScreen.this, "Go to settings and enable permission", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
            case MY_PERMISSIONS_REQUEST_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                        /*if (checkAccountsPermission()){*/
                        splashScreenCall();
                        /*  }*/



                    }

                } else {

                    SnackBar.makeText(SplashScreen.this, "Please accept location permission", SnackBar.LENGTH_SHORT).setAction("Accept", new LocationListener()).show();


                    return;
                }
                break;
            /*case MY_PERMISSIONS_REQUEST_ACCOUNTS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.GET_ACCOUNTS)
                            == PackageManager.PERMISSION_GRANTED) {



                        splashScreenCall();

                    }

                } else {

                    SnackBar.makeText(SplashScreenActivity.this, "Please accept location permission", SnackBar.LENGTH_SHORT).setAction("Accept", new LocationListener()).show();


                    return;
                }
                break;*/
        }
    }
    private void showPermissionRationaleSnackBar() {
        Snackbar.make(findViewById(android.R.id.content),
                "Please Grant Permissions",
                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(SplashScreen.this,
                                new String[]{Manifest.permission
                                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_FOR_STORAGE_PERMISSION);
                    }
                }).show();
    }
    public class LocationListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (checkLocationPermission()){
                splashScreenCall();
            }
        }
    }
    protected void onPause() {
        if (this.mHandlerTime != null) {
            this.mHandlerTime.removeCallbacks(this.mRunnableTimeOut);
        }
        super.onPause();
    }

    protected void onResume() {
        if (this.mHandlerTime != null) {
            this.mHandlerTime.postDelayed(this.mRunnableTimeOut, (long) this.SPLASH_TIME_OUT);
        }
        super.onResume();
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
}
