package com.geekhive.studentsoft.fragment;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.baoyachi.stepview.VerticalStepView;
import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.BehaviourList;
import com.geekhive.studentsoft.activities.DriverDashboard;
import com.geekhive.studentsoft.activities.LoginActivity;
import com.geekhive.studentsoft.adapter.HomeSliderViewPagerAdapter;
import com.geekhive.studentsoft.adapter.NonTeachingLeaveAdapter;
import com.geekhive.studentsoft.adapter.SchoollistAdapter;
import com.geekhive.studentsoft.adapter.StationareyOwnerAdapter;
import com.geekhive.studentsoft.adapter.StudentListAdapter;
import com.geekhive.studentsoft.beans.behaviourlist.BehaviourListResponce;
import com.geekhive.studentsoft.beans.getallbus.GetAllBus;
import com.geekhive.studentsoft.beans.getallbus.Stop;
import com.geekhive.studentsoft.beans.getallbus.Stop_;
import com.geekhive.studentsoft.beans.getallbus.Student;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.nonteachingstaffbyid.NonTeachingStaffById;
import com.geekhive.studentsoft.beans.pickupnotification.PickupNotification;
import com.geekhive.studentsoft.beans.stationarycategory.StationaryCategory;
import com.geekhive.studentsoft.trackingbackground.Servicecall;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import static android.content.Context.ALARM_SERVICE;

public class DriverHomeFragment extends Fragment implements OnResponseListner {


    ViewPager viewPager;
    DotsIndicator dot1;
    SchoollistAdapter schoollistAdapter;
    HomeSliderViewPagerAdapter viewPagerAdapter;
    Timer timer1;
    Dialog dialogSuccess;
    NonTeachingLeaveAdapter leaveAdapter;
    VerticalStepView mSetpview0;

    ConnectionDetector mDetector;
    List<String> statusList = new ArrayList<>();
    NonTeachingStaffById nonTeachingStaffById;
    String bus_number;
    TextView driver_name,Bus_number;
    GetAllBus getAllBus;
    List<Stop_> stops;
    StopsAdapter stopsAdapter;
    RecyclerView vr_stops;
    List<Student> students;
    StudentListAdapter studentListAdapter;
    List<String> studentList = new ArrayList<>();
    List<String> studentId = new ArrayList<>();
    String stop_name;
    public DriverHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_home, container, false);
        mDetector = new ConnectionDetector(getActivity());
        vr_stops = view.findViewById(R.id.vr_stops);
               // mSetpview0 = (VerticalStepView) view.findViewById(R.id.step_view0);
        mDetector = new ConnectionDetector(getActivity());
        CallService();
        driver_name = view.findViewById(R.id.driver_name);
        Bus_number = view.findViewById(R.id.Bus_number);

        return view;
    }
    private void Initializepopup() {
        dialogSuccess = new Dialog(getActivity());
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_studentlist);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }
    private void initializdeletePopup() {
        dialogSuccess.setContentView(R.layout.popup_studentlist);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;

        final TextView vT_psd_yes = (TextView) dialogSuccess.findViewById(R.id.vT_psd_yes);
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);


        vT_psd_yes.setOnClickListener(new View.OnClickListener() {
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


    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Getnonteachingstaffbyid(WebServices.SM_Services,
                    WebServices.ApiType.getnonteachingstapbyid,Prefs.getPrefRefId(getActivity()),Prefs.getPrefUserAuthenticationkey(getActivity()));
        } else {
            SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }





    private void CallallbusService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetAllBus(WebServices.SM_Services,
                    WebServices.ApiType.getallbus,Prefs.getPrefUserAuthenticationkey(getActivity()));
        } else {
            SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }

    private void PickUpNotification(String studentId) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Pickupnotification(WebServices.SM_Services,
                    WebServices.ApiType.picknotification,"pickup",studentId,Prefs.getPrefUserAuthenticationkey(getActivity()));
        } else {
            SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }



    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.getnonteachingstapbyid) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                nonTeachingStaffById = (NonTeachingStaffById) response;

                if (!isSucces || nonTeachingStaffById == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } if(nonTeachingStaffById.getResult() == null){
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }else {
                    if (nonTeachingStaffById.getResult().getMessage().getBusDetails() != null){
                        bus_number = nonTeachingStaffById.getResult().getMessage().getBusDetails().getBusNumber().toString();
                        SnackBar.makeText(getActivity(),bus_number, SnackBar.LENGTH_SHORT).show();
                        driver_name.setText(" "+nonTeachingStaffById.getResult().getMessage().getName());
                        Bus_number.setText("Bus Number: "+bus_number);
                        CallallbusService();
                    } else {
                        SnackBar.makeText(getActivity(), "No bus assigned!!!", SnackBar.LENGTH_SHORT).show();
                    }

                }
            }
        }if (apiType == WebServices.ApiType.getallbus) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getAllBus = (GetAllBus) response;

                if (!isSucces || getAllBus == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } if(getAllBus.getResult().getMessage() == null){
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }else {
                    stops = new ArrayList<>();
                    students = new ArrayList<>();
                    for (int i = 0; i < getAllBus.getResult().getMessage().size(); i++) {
                        if(bus_number.equals(String.valueOf(getAllBus.getResult().getMessage().get(i).getBusNumber()))){
                            stops.addAll(getAllBus.getResult().getMessage().get(i).getStops());
                            students.addAll(getAllBus.getResult().getMessage().get(i).getStudents());
                            for (int j = 0; j < getAllBus.getResult().getMessage().size(); j++) {
                                statusList.add(getAllBus.getResult().getMessage().get(i).getStops().get(j).getStopName());
                            }
                        }
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.add(Calendar.SECOND, 1);
                    Intent intent = new Intent(getActivity(), Servicecall.class);
                    PendingIntent pendingIntent = PendingIntent.getService(getActivity(), 1212, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                    if (alarmManager != null) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5000, pendingIntent);
                    }
                    getActivity().startService(intent);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    vr_stops.setLayoutManager(linearLayoutManager);
                    stopsAdapter = new StopsAdapter(getActivity());
                    vr_stops.setAdapter(stopsAdapter);
                }
            }
        }if (apiType == WebServices.ApiType.picknotification) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                PickupNotification pickupNotification = (PickupNotification) response;

                if (!isSucces || pickupNotification == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } if(pickupNotification.getResult() == null){
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }if(pickupNotification.getResult().getMessage() == null){
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }else {
                    SnackBar.makeText(getActivity(), "Student Pickup", SnackBar.LENGTH_SHORT).show();
                }
            }
        }
    }
    public class StopsAdapter extends RecyclerView.Adapter {

        Context context;
        StationaryCategory stationaryCategory;
        GetAllBus getAllBus;
        String url;
        public StopsAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stop_adapter,parent,false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ListViewHolder)holder).bindView(position);

            ((ListViewHolder) holder).tv_stopname.setText(stops.get(position).getStopName());
            ((ListViewHolder) holder).tv_reached.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doMap(stops.get(position).getLatitude(),stops.get(position).getLongitude(),stops.get(position).getStopName());
                }
            });
            ((ListViewHolder) holder).tv_studentlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Initializepopupmarks();
                    initializdeletePopupMarks(stops.get(position).getStopName());
                }
            });
        }

        @Override
        public int getItemCount() {
            return stops.size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            LinearLayout VL_psd_menu;
            ImageView iv_stationary,tv_reached,tv_studentlist;
            TextView tv_upnext,tv_stopname;
            public ListViewHolder(@NonNull View itemView) {
                super(itemView);
                VL_psd_menu = (LinearLayout) itemView.findViewById(R.id.VL_psd_menu);
                tv_upnext = (TextView)itemView.findViewById(R.id.tv_upnext);
                tv_reached = (ImageView)itemView.findViewById(R.id.tv_reached);
                tv_stopname = (TextView)itemView.findViewById(R.id.tv_stopname);
                iv_stationary = (ImageView) itemView.findViewById(R.id.iv_stationary);
                tv_studentlist = (ImageView)itemView.findViewById(R.id.tv_studentlist);

            }

            public void bindView(int position){

            }

            @Override
            public void onClick(View view) {
            }
        }
    }

    public void doMap(double latitude,double longitude,String name) {
        double lat = latitude;
        double longi = longitude;
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = lat + "," + longi + "(" + name + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(mapIntent);
    }

    private void Initializepopupmarks() {
        dialogSuccess = new Dialog(getActivity());
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.studentlist);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(false);
        dialogSuccess.setCanceledOnTouchOutside(false);
    }

    private void initializdeletePopupMarks(String stopname) {
        dialogSuccess.setContentView(R.layout.studentlist);
        dialogSuccess.setCancelable(false);
        dialogSuccess.setCanceledOnTouchOutside(false);
        dialogSuccess.show();
        RecyclerView studentlist_recyclerView = (RecyclerView)dialogSuccess.findViewById(R.id.studentlist_recyclerView);
        stop_name = stopname;
        studentList = new ArrayList<>();
        studentId = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            if(stopname.equals(students.get(i).getStop().getStopName())){
                studentList.add(students.get(i).getName());
                studentId.add(students.get(i).getId());
            }

        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        studentlist_recyclerView.setLayoutManager(linearLayoutManager);
        studentListAdapter = new StudentListAdapter(getActivity(),studentList,studentId);
        studentlist_recyclerView.setAdapter(studentListAdapter);

        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }

    public class StudentListAdapter extends RecyclerView.Adapter {

        Context context;
        List<String> studentList;
        List<String> studentId;


        public StudentListAdapter(Context context,List<String> studentList,List<String> studentId) {
            this.context = context;
            this.studentList = studentList;
            this.studentId = studentId;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist_adapter, parent, false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ListViewHolder) holder).bindView(position);
            ((ListViewHolder) holder).tv_studentname.setText(studentList.get(position));
            ((ListViewHolder) holder).checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if ( isChecked )
                    {
                        PickUpNotification(studentId.get(position).toString());
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return studentList.size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            LinearLayout school_name;
            TextView tv_studentname;
            ImageView iv_categoryImage;
            CheckBox checkbox;

            public ListViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_studentname = (TextView) itemView.findViewById(R.id.tv_studentname);
                checkbox = (CheckBox)itemView.findViewById(R.id.checkbox);
            }

            public void bindView(int position) {

            }

            @Override
            public void onClick(View view) {
            }
        }
    }










}


