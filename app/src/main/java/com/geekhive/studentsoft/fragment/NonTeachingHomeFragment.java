package com.geekhive.studentsoft.fragment;

/*public class NonTeachingHomeFragment {
}*/


import android.app.Dialog;
import android.content.Context;
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
import com.geekhive.studentsoft.activities.LeaveManagementStudent;
import com.geekhive.studentsoft.activities.StudentBehaviourActivity;
import com.geekhive.studentsoft.activities.StudentHomeWorkActivity;
import com.geekhive.studentsoft.activities.StudentResultsActivity;
import com.geekhive.studentsoft.activities.TeacherDashboard;
import com.geekhive.studentsoft.activities.UpcomingActivity;
import com.geekhive.studentsoft.adapter.EventAdapter;
import com.geekhive.studentsoft.adapter.HomeSliderViewPagerAdapter;
import com.geekhive.studentsoft.adapter.NonTeachingLeaveAdapter;
import com.geekhive.studentsoft.adapter.SchoollistAdapter;
import com.geekhive.studentsoft.adapter.SliderImageAdapter;
import com.geekhive.studentsoft.adapter.StationaryAdapter;
import com.geekhive.studentsoft.beans.addhomework.AddHomework;
import com.geekhive.studentsoft.beans.edithomework.EditHomeWork;
import com.geekhive.studentsoft.beans.homebanner.Homebanner;
import com.geekhive.studentsoft.beans.homeworklistresponce.HomeworkListResponce;
import com.geekhive.studentsoft.beans.schoollist.SchoolList;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NonTeachingHomeFragment extends Fragment  {


    RecyclerView vr_schoollist;
    ViewPager viewPager;
    DotsIndicator dot1;
    SchoollistAdapter schoollistAdapter;
    HomeSliderViewPagerAdapter viewPagerAdapter;
    Timer timer1;
    Dialog dialogSuccess;
    RecyclerView vrLeaves;
    NonTeachingLeaveAdapter leaveAdapter;

    ConnectionDetector mDetector;
    public NonTeachingHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nonteaching_home, container, false);
        mDetector = new ConnectionDetector(getActivity());
        vr_schoollist = view.findViewById(R.id.vr_schoollist);
        vrLeaves = view.findViewById(R.id.vr_leaves);

        dot1 = view.findViewById(R.id.dot1);
        viewPager = (ViewPager)view.findViewById(R.id.pager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        vrLeaves.setLayoutManager(linearLayoutManager);
        leaveAdapter = new NonTeachingLeaveAdapter(getContext());
        vrLeaves.setAdapter(leaveAdapter);

        return view;
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


}

