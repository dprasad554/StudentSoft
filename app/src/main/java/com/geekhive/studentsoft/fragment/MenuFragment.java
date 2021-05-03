package com.geekhive.studentsoft.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.TimeTableAdapter;
import com.geekhive.studentsoft.beans.getstudenttimetable.GetStudentTimetable;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import java.util.ArrayList;
import java.util.Calendar;

public class MenuFragment extends Fragment implements OnResponseListner {

    View v;
    RecyclerView menuRecycler;
    String id;
    int quantity = 0;

    String price_;
    String mrp;
    String foodId;
    TimeTableAdapter menuAdapter;
    ConnectionDetector mDetector;
    String clas,sec;
    private ArrayList<String> day = new ArrayList<String>();


    public static MenuFragment getInstance(String cls,String section,String url) {
        MenuFragment topRated = new MenuFragment();
        // Supply index input as an argument
        Bundle args = new Bundle();
        args.putString("id", url);
        args.putString("cls", cls);
        args.putString("section", section);

        //args.putString("name", name);
        topRated.setArguments(args);
        return topRated;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            id = args.getString("id", "0");
            clas = args.getString("cls","0");
            sec = args.getString("section","0");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDetector = new ConnectionDetector(getActivity());
        v = inflater.inflate(R.layout.fragment_menu, container, false);
        initialiseView(v, this.getActivity());
        setHasOptionsMenu(false);
        CallTimetable(clas,sec);
        return v;
    }
    private void CallTimetable(String clas,String section) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetTimeTable(WebServices.SM_Services,
                    WebServices.ApiType.getstudenttimetable,clas,section, Prefs.getPrefUserAuthenticationkey(getActivity()));
        }
        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.getstudenttimetable) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final GetStudentTimetable getStudentTimetable = (GetStudentTimetable) response;

                if (!isSucces || getStudentTimetable == null) {

                }if (!isSucces || getStudentTimetable.getResult().getMessage() == null) {

                } else {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    menuRecycler.setLayoutManager(linearLayoutManager);
                    menuAdapter = new TimeTableAdapter(getActivity(),getStudentTimetable,id);
                    menuRecycler.setAdapter(menuAdapter);
                    /*Calendar calander = Calendar.getInstance();
                    int day = calander.get(Calendar.DAY_OF_WEEK);*/
                }
            }
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (menuAdapter != null) {
                menuAdapter.notifyDataSetChanged();
            }
        }
    }

    void initialiseView(View v, final Context ctx) {
        /*mDetector = new ConnectionDetector(getActivity());*/
        menuRecycler = v.findViewById(R.id.menulist);
        Bundle args = getArguments();
        id = args.getString("id", "0");

        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        menuRecycler.setLayoutManager(linearLayoutManager);
        menuAdapter = new TimeTableAdapter(getActivity());
        menuRecycler.setAdapter(menuAdapter);*/
        /*res_id = args.getString("res_id", "0");
        if (foodList.size() == 0){
            CallService(res_id, id);
        }*/

    }
}
