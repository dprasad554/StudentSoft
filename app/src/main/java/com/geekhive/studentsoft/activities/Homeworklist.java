package com.geekhive.studentsoft.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.HomeworklistAdapter;
import com.geekhive.studentsoft.beans.homeworklistresponce.HomeworkListResponce;
import com.geekhive.studentsoft.fragment.HomeWorkFragment;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class Homeworklist extends AppCompatActivity implements  View.OnClickListener,OnResponseListner {

    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.vR_all_classes)
    RecyclerView vRAllClasses;

    HomeworklistAdapter homeworklistAdapter;
    HomeworkListResponce homeworkListResponce;
    ConnectionDetector mDetector;
    String clas,section,subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.homework_activity);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        backPress.setOnClickListener(this);
        clas = getIntent().getStringExtra("clas");
        section = getIntent().getStringExtra("section");
        subject = getIntent().getStringExtra("subject");
        CallService();
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
    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
                    new WebServices(this).Homeworklist(WebServices.SM_Services,
                            WebServices.ApiType.homeworklist,clas,section,subject, Prefs.getPrefUserAuthenticationkey(this));
        } else {
            SnackBar.makeText(Homeworklist.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.homeworklist) {
            if (!isSucces) {
                SnackBar.makeText(Homeworklist.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                homeworkListResponce = (HomeworkListResponce) response;

                if (!isSucces || homeworkListResponce.getResult() == null) {
                    SnackBar.makeText(Homeworklist.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }else {
                    LinearLayoutManager layoutShopManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                    vRAllClasses.setLayoutManager(layoutShopManager);
                    homeworklistAdapter = new HomeworklistAdapter(this,homeworkListResponce);
                    vRAllClasses.setAdapter(homeworklistAdapter);
                }
            }
        }
    }
    public class HomeworklistAdapter extends RecyclerView.Adapter {

        Context context;
        HomeworkListResponce homeworkList;
        public HomeworklistAdapter(Context context, HomeworkListResponce homeworkList) {
            this.context = context;
            this.homeworkList=homeworkList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_work_list,parent,false);
            return new HomeworklistAdapter.ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((HomeworklistAdapter.ListViewHolder)holder).bindView(position);
            ((HomeworklistAdapter.ListViewHolder)holder).tvClass.setText(homeworkList.getResult().getMessage().get(position).getSubject());
            ((HomeworklistAdapter.ListViewHolder)holder).title.setText("Title : "+homeworkList.getResult().getMessage().get(position).getTitle());
            ((HomeworklistAdapter.ListViewHolder)holder).details.setText("Details : "+homeworkList.getResult().getMessage().get(position).getDetails());
            ((HomeworklistAdapter.ListViewHolder)holder).llClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new HomeWorkFragment();
                Bundle args = new Bundle();
                args.putString("classname",clas);
                args.putString("section",section);
                args.putString("subject",subject);
                args.putString("title",homeworkList.getResult().getMessage().get(position).getTitle());
                args.putString("details",homeworkList.getResult().getMessage().get(position).getDetails());
                fragment.setArguments(args);
                    loadFragment(fragment);
                }
            });

        }
        @Override
        public int getItemCount() {
            return homeworkList.getResult().getMessage().size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView tvClass,details,title;
            LinearLayout llClass;
            public ListViewHolder(@NonNull View itemView) {
                super(itemView);
                tvClass = itemView.findViewById(R.id.tvClass);
                details = itemView.findViewById(R.id.details);
                title = itemView.findViewById(R.id.title);
                llClass = itemView.findViewById(R.id.llClass);

            }

            public void bindView(int position){

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