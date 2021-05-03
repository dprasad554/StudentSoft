package com.geekhive.studentsoft.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.getstudenttimetable.GetStudentTimetable;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class FixturesTabsFragment extends Fragment  {

    String rest_id;
    TabLayout tabs;
    ViewPager viewPager;
    ArrayList categoryName = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday","Friday"));
    String cls,section;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fixtures_new_tabs,container, false);
        // Setting ViewPager for each Tabs
        rest_id = getArguments().getString("res_id");
        cls = getArguments().getString("class");
        section = getArguments().getString("section");
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        // Set Tabs inside Toolbar
        tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);

        return view;

    }

    private void setupViewPager(ViewPager viewPager) {
        AdapterPager adapter = new AdapterPager(getChildFragmentManager());
        try {
            for(int j=0; j<categoryName.size(); j++){
                adapter.addFragment(MenuFragment.getInstance(cls,section,categoryName.get(j).toString()), categoryName.get(j).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
    }

    // pager adapter
    static class AdapterPager extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public AdapterPager(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
