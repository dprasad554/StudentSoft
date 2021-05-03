package com.geekhive.studentsoft.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.SeatVacancyAdapter;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.seatvacancy.SeatVacancyResponce;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeatVacancy extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.vR_seats_vacancy)
    RecyclerView vRSeatsVacancy;
    @BindView(R.id.backPress)
    ImageView backPress;
    SeatVacancyAdapter seatVacancyAdapter;
    ConnectionDetector mDetector;
    SeatVacancyResponce seatVacancy;
    String dbkey;
    String id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_seat_vacancy);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        dbkey = getIntent().getStringExtra("dbkey");
        id = getIntent().getStringExtra("id");
        if(dbkey !=null ){
            CallSchoolwiseService();

        }else {
            CallService();
        }
        backPress.setOnClickListener(this);
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetSeatVacancies(WebServices.SM_Services,
                    WebServices.ApiType.getsetvacancies,Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    private void CallSchoolwiseService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetSeatVacancies(WebServices.SM_Services,
                    WebServices.ApiType.getsetvacancies,dbkey);
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.getsetvacancies) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                seatVacancy = (SeatVacancyResponce) response;

                if (!isSucces || seatVacancy.getResult() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    seatVacancyAdapter = new SeatVacancyAdapter(this,seatVacancy);
                    vRSeatsVacancy.setLayoutManager(new LinearLayoutManager(this));
                    vRSeatsVacancy.setAdapter(seatVacancyAdapter);
                }
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
    public class SeatVacancyAdapter extends RecyclerView.Adapter {

        Context context;
        SeatVacancyResponce seatVacancy;
        public SeatVacancyAdapter(Context context, SeatVacancyResponce seatVacancy) {
            this.context = context;
            this.seatVacancy = seatVacancy;
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vacany_item_layout,parent,false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ListViewHolder)holder).bindView(position);
            ((ListViewHolder)holder).tvClass.setText("Class:"+seatVacancy.getResult().getMessage().get(position).getClassName());
            ((ListViewHolder)holder).tv_startdate.setText("Session starts from "+seatVacancy.getResult().getMessage().get(position).getStartApplication());
            ((ListViewHolder)holder).tv_available.setText("Available seats: "+seatVacancy.getResult().getMessage().get(position).getSeatsAvailable());
            ((ListViewHolder)holder).tv_enddate.setText("Last date of submission:"+seatVacancy.getResult().getMessage().get(position).getEndApplication());

        }

        @Override
        public int getItemCount() {
            return seatVacancy.getResult().getMessage().size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            LinearLayout llClass;
            TextView tvClass,tv_startdate,tv_available,tv_enddate;
            public ListViewHolder(@NonNull View itemView) {
                super(itemView);
                llClass = itemView.findViewById(R.id.llClass);
                tvClass = itemView.findViewById(R.id.tvClass);
                tv_startdate = itemView.findViewById(R.id.tv_startdate);
                tv_enddate = itemView.findViewById(R.id.tv_enddate);
                tv_available = itemView.findViewById(R.id.tv_available);
            }

            public void bindView(int position){
                llClass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Prefs.getUserType(SeatVacancy.this).equals("guest")){
                            Intent intent = new Intent(context, SeatApplicationActivity.class);
                            intent.putExtra("class", seatVacancy.getResult().getMessage().get(position).getClassName());
                            //intent.putExtra("dbkey", seatVacancy.getResult().getMessage().get(position).ge());
                            context.startActivity(intent);
                        }else {
                            SnackBar.makeText(SeatVacancy.this, "Please register to apply", SnackBar.LENGTH_SHORT).show();
                        }
                   /* */
                    }
                });
            }

            @Override
            public void onClick(View view) {
            }
        }

    /*public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        historyList.clear();
        if (charText.length() == 0) {
            historyList.addAll(arraylist);
        } else {
            for (Message wp : arraylist) {

                //String name = wp.getManf_name() + wp.getModel_name();
                String name2 = wp.getPhoneNumber();
                String name3 = wp.getGuestName();

                if (wp.getPhoneNumber().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getPhoneNumber().toLowerCase(Locale.getDefault())
                        .contains(charText) || name2.toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getGuestName().toLowerCase(Locale.getDefault())
                        .contains(charText) || name3.toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    historyList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }*/
    }

}
