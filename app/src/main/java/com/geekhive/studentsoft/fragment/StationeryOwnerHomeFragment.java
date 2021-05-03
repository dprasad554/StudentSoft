package com.geekhive.studentsoft.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.StationaryOwnerOrderHistory;
import com.geekhive.studentsoft.adapter.HomeSliderViewPagerAdapter;
import com.geekhive.studentsoft.adapter.NonTeachingLeaveAdapter;
import com.geekhive.studentsoft.adapter.SchoollistAdapter;
import com.geekhive.studentsoft.adapter.StationareyOwnerAdapter;
import com.geekhive.studentsoft.adapter.StationaryAdapter;
import com.geekhive.studentsoft.beans.stationarycategory.StationaryCategory;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.Timer;

public class StationeryOwnerHomeFragment extends Fragment implements OnResponseListner {


    RecyclerView vr_stationary;
    ViewPager viewPager;
    DotsIndicator dot1;
    SchoollistAdapter schoollistAdapter;
    HomeSliderViewPagerAdapter viewPagerAdapter;
    Timer timer1;
    Dialog dialogSuccess;
    RecyclerView vrLeaves;
    NonTeachingLeaveAdapter leaveAdapter;
    StationareyOwnerAdapter stationaryAdapter;
    Button btn_orderPlace;
    //StationaryAdapter stationaryAdapter;
    StationaryCategory stationaryCategory;
    ConnectionDetector mDetector;
    public StationeryOwnerHomeFragment() {
        // Required empty public constructor
    }
//fragment_sowner_home
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sowner_home, container, false);
        mDetector = new ConnectionDetector(getActivity());
        vr_stationary = view.findViewById(R.id.vr_stationary);
        vrLeaves = view.findViewById(R.id.vr_leaves);
        btn_orderPlace = view.findViewById(R.id.btn_orderPlace);

        dot1 = view.findViewById(R.id.dot1);
        viewPager = (ViewPager)view.findViewById(R.id.pager);

        mDetector = new ConnectionDetector(getActivity());

        btn_orderPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StationaryOwnerOrderHistory.class);
                startActivity(intent);
            }
        });

        CallstonaryCategory();

        return view;
    }

    private void CallstonaryCategory() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).StonaryCategory(WebServices.SM_Services,
                    WebServices.ApiType.staonarycategory, "main", Prefs.getPrefUserAuthenticationkey(getActivity()));
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.staonarycategory) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                stationaryCategory = (StationaryCategory) response;

                if (!isSucces || stationaryCategory.getResult().getMessage() == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    /*GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
                    vrStationary.setLayoutManager(linearLayoutManager);
                    stationaryAdapter = new StationaryAdapter(this,stationaryCategory);
                    vrStationary.setAdapter(stationaryAdapter);*/

                    GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
                    vr_stationary.setLayoutManager(linearLayoutManager);
                    stationaryAdapter = new StationareyOwnerAdapter(getActivity(), stationaryCategory);
                    vr_stationary.setAdapter(stationaryAdapter);
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


}


