package com.geekhive.studentsoft.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.EventAdapter;
import com.geekhive.studentsoft.adapter.SeatVacancyAdapter;
import com.geekhive.studentsoft.adapter.StudentBHAdapter;
import com.geekhive.studentsoft.adapter.UpcommingEventAdapter;
import com.geekhive.studentsoft.beans.eventregistration.EventRegistration;
import com.geekhive.studentsoft.beans.getallevents.GetAllEvents;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.getupcommingevents.GetUpcommingEvents;
import com.geekhive.studentsoft.beans.seatvacancy.SeatVacancyResponce;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.vr_events)
    RecyclerView vrEvents;
    @BindView(R.id.up_events)
    RecyclerView up_events;
    UpcommingEventAdapter upcommingEventAdapter;
    EventAdapter eventAdapter;
    ConnectionDetector mDetector;
    GetAllEvents getAllEvents;
    GetUpcommingEvents getUpcommingEvents;
    GetStudentById getStudentById;
    EventRegistration eventRegistration;
    String event_id,cls,student_name,section;
    ArrayList<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        CallStudentbyidService();
        CallService();
        CallUpcommingService();
        backPress.setOnClickListener(this);
    }
    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetAllEvents(WebServices.SM_Services,
                    WebServices.ApiType.getallevents,Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    private void CallUpcommingService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetUpcommingEvents(WebServices.SM_Services,
                    WebServices.ApiType.getupcommingevents,Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    private void CallStudentbyidService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid, Prefs.getPrefRefId(this),Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    private void CallEventRegistration() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).EventRegistration(WebServices.SM_Services,
                    WebServices.ApiType.eventregistration,event_id,Prefs.getPrefRefId(this),student_name,cls,section,Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.getallevents) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getAllEvents = (GetAllEvents) response;

                if (!isSucces || getAllEvents == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    vrEvents.setLayoutManager(linearLayoutManager);
                    eventAdapter = new EventAdapter(this,getAllEvents);
                    vrEvents.setAdapter(eventAdapter);
                }
            }
        }if (apiType == WebServices.ApiType.getupcommingevents) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getUpcommingEvents = (GetUpcommingEvents) response;

                if (!isSucces || getUpcommingEvents.getResult() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
                    up_events.setLayoutManager(linearLayoutManager);
                    upcommingEventAdapter = new UpcommingEventAdapter(this,getUpcommingEvents);
                    up_events.setAdapter(upcommingEventAdapter);
                }
            }
        }if (apiType == WebServices.ApiType.getstudentbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getStudentById = (GetStudentById) response;

                if (!isSucces || getStudentById.getResult() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } if (!isSucces || getStudentById.getResult().getMessage() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    student_name=getStudentById.getResult().getMessage().getFirstName()+" "+getStudentById.getResult().getMessage().getLastName();
                    cls = getStudentById.getResult().getMessage().getCurrentClass().getClassName();
                    section = getStudentById.getResult().getMessage().getCurrentClass().getSectionName();
                }
            }
        }if (apiType == WebServices.ApiType.eventregistration) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                eventRegistration = (EventRegistration) response;

                if (!isSucces || eventRegistration.getResult().getMessage()== null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(this,"Registration successful", SnackBar.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class UpcommingEventAdapter extends RecyclerView.Adapter {
        Context context;
        GetUpcommingEvents getUpcommingEvents;
        Dialog dialogSuccess;

        public UpcommingEventAdapter(Context context,GetUpcommingEvents getUpcommingEvents) {
            this.context = context;
            this.getUpcommingEvents = getUpcommingEvents;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcomming_event_list,parent,false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ListViewHolder)holder).bindView(position);
            if(getUpcommingEvents.getResult().getMessage().get(position).getMediaUrls().size() !=0){
                String url = "http://"+getUpcommingEvents.getResult().getMessage().get(position).getMediaUrls().get(0).toString();
                if (!url.equals("")){
                    Picasso.get()
                            .load(url)//download URL
                            .error(R.drawable.event_school)//if failed
                            .into(((ListViewHolder)holder).event_image);
                }
            }

            ((ListViewHolder)holder).tv_event_name.setText("Name:"+getUpcommingEvents.getResult().getMessage().get(position).getEventDetails());
            ((ListViewHolder)holder).tv_event_date.setText("Event Date:"+getUpcommingEvents.getResult().getMessage().get(position).getEventDate());
            ((ListViewHolder)holder).tv_event_time.setText("Event Time:"+getUpcommingEvents.getResult().getMessage().get(position).getEventTime());
            ((ListViewHolder)holder).llClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    event_id = getUpcommingEvents.getResult().getMessage().get(position).getId();
                    Initializepopup();
                    initializdeletePopup();
                }
            });

        }
        private void Initializepopup() {
            dialogSuccess = new Dialog(context);
            dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialogSuccess.setContentView(R.layout.event_popup);
            dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogSuccess.setCancelable(true);
            dialogSuccess.setCanceledOnTouchOutside(true);
        }
        private void initializdeletePopup() {
            dialogSuccess.setContentView(R.layout.event_popup);
            dialogSuccess.setCancelable(true);
            dialogSuccess.setCanceledOnTouchOutside(true);
            dialogSuccess.show();

            Button btnSubmitLeave = dialogSuccess.findViewById(R.id.btn_submit_leave);
            EditText tv_name = dialogSuccess.findViewById(R.id.tv_name);
            EditText tv_class = dialogSuccess.findViewById(R.id.tv_class);
            EditText tv_section = dialogSuccess.findViewById(R.id.tv_section);
//        deletePopup.getWindow().setLayout(width, height);
            dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogSuccess.getWindow().setGravity(Gravity.CENTER);
            tv_name.setText(student_name);
            tv_class.setText(cls);
            tv_section.setText(section);
            btnSubmitLeave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogSuccess.isShowing()) {
                        dialogSuccess.dismiss();
                        CallEventRegistration();
                    }
                }
            });
            dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogSuccess.setCancelable(true);
            dialogSuccess.setCanceledOnTouchOutside(true);
            dialogSuccess.show();
        }

        @Override
        public int getItemCount() {
            return getUpcommingEvents.getResult().getMessage().size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            RelativeLayout llClass;
            ImageView event_image;
            TextView tv_event_name,tv_event_date,tv_event_time;
            public ListViewHolder(@NonNull View itemView) {
                super(itemView);
                llClass = (RelativeLayout) itemView.findViewById(R.id.llClass);
                event_image = (ImageView)itemView.findViewById(R.id.event_image);
                tv_event_name = (TextView)itemView.findViewById(R.id.tv_event_name);
                tv_event_date = (TextView)itemView.findViewById(R.id.tv_event_date);
                tv_event_time = (TextView)itemView.findViewById(R.id.tv_event_time);
            }

            public void bindView(int position){
            }

            @Override
            public void onClick(View view) {
            }
        }
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
