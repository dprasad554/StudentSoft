package com.geekhive.studentsoft.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.behaviourlist.BehaviourListResponce;
import com.geekhive.studentsoft.beans.getallbus.GetAllBus;
import com.geekhive.studentsoft.beans.getallbus.Stop;
import com.geekhive.studentsoft.beans.getallbus.Stop_;
import com.geekhive.studentsoft.beans.getallbus.Student;
import com.geekhive.studentsoft.beans.getparentsbyid.GetParentsByID;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.schoollist.SchoolList;
import com.geekhive.studentsoft.beans.stationarycategory.StationaryCategory;
import com.geekhive.studentsoft.beans.usercurrentlocation.UserLocation;
import com.geekhive.studentsoft.fragment.BehaviourFragment;
import com.geekhive.studentsoft.fragment.DriverHomeFragment;
import com.geekhive.studentsoft.trackingbackground.Servicecall;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.GitApi;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverDetailsForParents extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.schoolName)
    TextView schoolNa;
    @BindView(R.id.driver_name)
    TextView driverName;
    BehaviourListResponce behaviourListResponce;
    ConnectionDetector mDetector;
    String clas, section, subject;
    String student_name;
    RecyclerView vr_stops;
    ParentDriverAdapter parentDriverAdapter;

    Retrofit retrofit;
    GitApi apiService;
    Disposable disposable;
    GetParentsByID getParentsByID;
    String student_id;
    GetStudentById getStudentById;
    GetAllBus getAllBus;
    String bus_number;
    String driver_id;
    String lat = "";
    String lon = "";
    List<Stop_> stops;
    List<Student> students;
    List<String> statusList = new ArrayList<>();
    TextView bsnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_parents_driver);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        backPress.setOnClickListener(this);
        vr_stops = findViewById(R.id.vr_stops);
        bsnumber = findViewById(R.id.bsnumber);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        vr_stops.setLayoutManager(linearLayoutManager);

        /*parentDriverAdapter = new ParentDriverAdapter(this);
        vr_stops.setAdapter(parentDriverAdapter);*/
        CallSchoollist();


    }

    private void HttpCall() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        retrofit = new Retrofit.Builder()
                .baseUrl(WebServices.SM_Services)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(GitApi.class);

        disposable = Observable.interval(1000, 10000,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::callLocationUpdate, this::onError);

    }

    private void onError(Throwable throwable) {
        /*Toast.makeText(this, "OnError in Observable Timer",
                Toast.LENGTH_LONG).show();*/
        Log.e("Error: ", "OnError in Observable Timer");
    }

    private void CallSchoollist() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Schoollist(WebServices.SM_Services,
                    WebServices.ApiType.getallschool);
            return;
        }

    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetParentsByID(WebServices.SM_Services,
                    WebServices.ApiType.getparentsbyid, Prefs.getPrefRefId(this), Prefs.getPrefUserAuthenticationkey(this));
        } else {
            SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }

    private void CallGeTStudentService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid, student_id, Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }

    private void CallallbusService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetAllBus(WebServices.SM_Services,
                    WebServices.ApiType.getallbus, Prefs.getPrefUserAuthenticationkey(this));
        } else {
            SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }

    private void CallUpdateBusService(String busNumber, String stop_number) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).UpdateBus(WebServices.SM_Services,
                    WebServices.ApiType.updatebus, busNumber, stop_number, Prefs.getPrefUserAuthenticationkey(this));
        } else {
            SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }

    private void CallResetBusService(String busNumber) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).ResetBus(WebServices.SM_Services,
                    WebServices.ApiType.resetbus, busNumber, Prefs.getPrefUserAuthenticationkey(this));
        } else {
            SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.getallschool) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final SchoolList schoolList = (SchoolList) response;

                if (!isSucces || schoolList.getResult().getMessage().size() == 0) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    for (int j = 0; j < schoolList.getResult().getMessage().size(); j++) {
                        if (Prefs.getPrefUserAuthenticationkey(this).equals(schoolList.getResult().getMessage().get(j).getDbKey())) {
                            schoolNa.setText(schoolList.getResult().getMessage().get(j).getName());
                        }
                    }
                    CallService();

                }
            }
        }
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
                    student_id = getParentsByID.getResult().getMessage().getStudentId();

                    CallGeTStudentService();
                }
            }
        }
        if (apiType == WebServices.ApiType.getstudentbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getStudentById = (GetStudentById) response;

                if (!isSucces || getStudentById == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }else {
                    if (!isSucces || getStudentById.getResult() == null) {
                        SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                    }if (!isSucces || getStudentById.getResult().getMessage() == null) {
                        SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                    }if (!isSucces || getStudentById.getResult().getMessage().getBusDetails()== null) {
                        SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                    }else {
                        bus_number = String.valueOf(getStudentById.getResult().getMessage().getBusDetails().getBusNumber());
                        String bNum = "Bus Number: " + bus_number;
                        bsnumber.setText(bNum);
                        //getStudentById.getResult().getMessage().get
                        //  callLocationUpdate();G
                        CallallbusService();
                    }

                }
            }
        }
        if (apiType == WebServices.ApiType.getallbus) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getAllBus = (GetAllBus) response;

                if (!isSucces || getAllBus == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }
                if (getAllBus.getResult().getMessage() == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    stops = new ArrayList<>();

                    for (int i = 0; i < getAllBus.getResult().getMessage().size(); i++) {
                        if (bus_number.equals(String.valueOf(getAllBus.getResult().getMessage().get(i).getBusNumber()))) {
                            driverName.setText(" "+getAllBus.getResult().getMessage().get(i).getDriverDetails().getName());
                            driver_id = getAllBus.getResult().getMessage().get(i).getDriverDetails().getId();
                            stops.addAll(getAllBus.getResult().getMessage().get(i).getStops());

                        } else {
                            SnackBar.makeText(this, "No bus assigned to you", SnackBar.LENGTH_SHORT).show();
                        }
                    }
                    if (stops != null) {
                        if (stops.size() != 0) {
                            parentDriverAdapter = new ParentDriverAdapter(this);
                            vr_stops.setAdapter(parentDriverAdapter);
                            HttpCall();
                        }
                    }
                }
            }
        }

        if (apiType == WebServices.ApiType.updatebus) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getAllBus = (GetAllBus) response;

                if (!isSucces || getAllBus == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }
                if (getAllBus.getResult().getMessage() == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    CallService();
                   /* stops = new ArrayList<>();
                    for (int i = 0; i < getAllBus.getResult().getMessage().size(); i++) {
                        if (bus_number.equals(String.valueOf(getAllBus.getResult().getMessage().get(i).getBusNumber()))) {
                            driverName.setText(getAllBus.getResult().getMessage().get(i).getDriverDetails().getName());
                            driver_id = getAllBus.getResult().getMessage().get(i).getDriverDetails().getId();
                            stops.addAll(getAllBus.getResult().getMessage().get(i).getStops());
                        } else {
                            SnackBar.makeText(this, "No bus assigned to you", SnackBar.LENGTH_SHORT).show();
                        }
                    }
                    if (stops != null) {
                        if (stops.size() != 0) {
                            parentDriverAdapter = new ParentDriverAdapter(this);
                            vr_stops.setAdapter(parentDriverAdapter);
                            HttpCall();
                        }
                    }*/
                }
            }
        }

        if (apiType == WebServices.ApiType.resetbus) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getAllBus = (GetAllBus) response;

                if (!isSucces || getAllBus == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }
                if (getAllBus.getResult().getMessage() == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    CallService();
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

    private void callLocationUpdate(Long aLong) {


        Observable<UserLocation> observable = apiService.getUserLocation(driver_id, Prefs.getPrefUserAuthenticationkey(this));
        observable.subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(this::handleResults, this::handleError);
    }

    private void handleResults(UserLocation userLocation) {


        if (userLocation != null) {
            if (userLocation.getResult() != null) {
                lat = String.valueOf(userLocation.getResult().getMessage().getCurrentLocation().getLatitude());
                lon = String.valueOf(userLocation.getResult().getMessage().getCurrentLocation().getLongitude());
                CallService();
            }

        } else {
            SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }
    }

    private void handleError(Throwable t) {

        //Add your error here.
    }

    public class ParentDriverAdapter extends RecyclerView.Adapter {

        Context context;

        public ParentDriverAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stop_adapter_parents, parent, false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ListViewHolder) holder).bindView(position);
            if (!lat.equals("") || !lat.isEmpty()) {
                if (Math.floor(Integer.parseInt(lat) * 1000) == Math.floor(Double.parseDouble(stops.get(position).getLatitude() + "") * 1000) || Math.floor(Integer.parseInt(lon) * 1000) == Math.floor(Double.parseDouble(stops.get(position).getLongitude() + "") * 1000)) {
                    ((ListViewHolder) holder).tv_reached.setText("Reached");
                    ((ListViewHolder) holder).tv_reached.setVisibility(View.VISIBLE);
                    ((ListViewHolder) holder).bus_img.setVisibility(View.VISIBLE);
                    ((ListViewHolder) holder).rchd_img.setVisibility(View.GONE);

                    if (position == (stops.size() - 1)){
                        CallResetBusService(bus_number);
                    } else {
                        CallUpdateBusService(bus_number, stops.get(position).getStopNumber());
                    }
                } else {
                    ((ListViewHolder) holder).tv_reached.setText("Yet To Reach");
                    ((ListViewHolder) holder).tv_reached.setVisibility(View.VISIBLE);
                    ((ListViewHolder) holder).bus_img.setVisibility(View.GONE);
                    ((ListViewHolder) holder).rchd_img.setVisibility(View.GONE);
                }

            }
            ((ListViewHolder) holder).tv_stopname.setText(stops.get(position).getStopName());

            if (String.valueOf(stops.get(position).getStatus()).equals("0")){
                ((ListViewHolder) holder).ll_bg.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                ((ListViewHolder) holder).tv_reached.setText("Yet To Reach");
                ((ListViewHolder) holder).bus_img.setVisibility(View.INVISIBLE);
                ((ListViewHolder) holder).rchd_img.setVisibility(View.INVISIBLE);
            }

            else if(String.valueOf(stops.get(position).getStatus()).equals("1")){
                ((ListViewHolder) holder).tv_reached.setText("Reached");
                ((ListViewHolder) holder).ll_bg.setBackground(context.getResources().getDrawable(R.drawable.background_gradient));
                ((ListViewHolder) holder).bus_img.setVisibility(View.GONE);
                ((ListViewHolder) holder).rchd_img.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public int getItemCount() {
            return stops.size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            LinearLayout VL_psd_menu, ll_bg;
            ImageView iv_stationary, bus_img, rchd_img;
            TextView tv_upnext, tv_reached, tv_stopname;

            public ListViewHolder(@NonNull View itemView) {
                super(itemView);
                VL_psd_menu = (LinearLayout) itemView.findViewById(R.id.VL_psd_menu);
                tv_reached = (TextView) itemView.findViewById(R.id.tv_reached);
                tv_stopname = (TextView) itemView.findViewById(R.id.tv_stopname);
                bus_img = itemView.findViewById(R.id.bus_img);
                rchd_img = itemView.findViewById(R.id.rchd_img);
                ll_bg = itemView.findViewById(R.id.ll_bg);
            }

            public void bindView(int position) {

            }

            @Override
            public void onClick(View view) {
            }
        }
    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backPress:
                finish();
                break;
        }
    }
}