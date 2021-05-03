package com.geekhive.studentsoft.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.getstudenttimetable.GetStudentTimetable;
import com.geekhive.studentsoft.fragment.FixturesTabsFragment;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassTimeTable extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.backPress)
    ImageView backPress;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    String cls,section;
    ConnectionDetector mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_class_time_table);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        cls = getIntent().getStringExtra("class");
        section = getIntent().getStringExtra("section");
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("res_id", "2");
        bundle.putString("class", cls);
        bundle.putString("section", section);
        // set Fragmentclass Arguments
        FixturesTabsFragment fragobj = new FixturesTabsFragment();
        fragobj.setArguments(bundle);
        mFragmentTransaction.replace(R.id.containerView, fragobj).commit();
        backPress.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backPress:
                finish();
                break;
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
}
