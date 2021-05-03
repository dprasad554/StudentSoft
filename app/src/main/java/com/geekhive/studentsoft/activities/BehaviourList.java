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
import com.geekhive.studentsoft.adapter.StudentBHAdapter;
import com.geekhive.studentsoft.beans.behaviourlist.BehaviourListResponce;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.homeworklistresponce.HomeworkListResponce;
import com.geekhive.studentsoft.fragment.BehaviourFragment;
import com.geekhive.studentsoft.fragment.HomeWorkFragment;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BehaviourList extends AppCompatActivity implements  View.OnClickListener,OnResponseListner {

    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.vR_all_classes)
    RecyclerView vRAllClasses;
    BehaviourListResponce behaviourListResponce;
    ConnectionDetector mDetector;
    String clas,section,subject;
    BehaviourAdapter behaviourAdapter;
    GetStudentById getStudentById;
    String student_id;
    String student_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.homework_activity);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        backPress.setOnClickListener(this);
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
            new WebServices(this).BehaviourList(WebServices.SM_Services,
                    WebServices.ApiType.behaviourlist, Prefs.getPrefRefId(this),Prefs.getPrefUserAuthenticationkey(this));
        } else {
            SnackBar.makeText(BehaviourList.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }
    private void GetStudentbyid() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid,student_id,Prefs.getPrefUserAuthenticationkey(this));
        } else {
            SnackBar.makeText(BehaviourList.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }

        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.behaviourlist) {
            if (!isSucces) {
                SnackBar.makeText(BehaviourList.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                behaviourListResponce = (BehaviourListResponce) response;

                if (!isSucces || behaviourListResponce == null) {
                    SnackBar.makeText(BehaviourList.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } if(behaviourListResponce.getResult().getMessage() == null){
                    SnackBar.makeText(BehaviourList.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }else {
                    LinearLayoutManager layoutShopManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                    vRAllClasses.setLayoutManager(layoutShopManager);
                    behaviourAdapter = new BehaviourAdapter(this,behaviourListResponce);
                    vRAllClasses.setAdapter(behaviourAdapter);
                }
            }
        }if (apiType == WebServices.ApiType.getstudentbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getStudentById = (GetStudentById) response;

                if (!isSucces || getStudentById.getResult().getMessage().getBehaviourNote() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    clas = getStudentById.getResult().getMessage().getCurrentClass().getClassName();
                    section = getStudentById.getResult().getMessage().getCurrentClass().getSectionName();
                    student_name = getStudentById.getResult().getMessage().getFirstName()+getStudentById.getResult().getMessage().getFirstName();

                }
            }
        }
    }
    public class BehaviourAdapter extends RecyclerView.Adapter {

        Context context;
        BehaviourListResponce behaviourListResponce;
        public BehaviourAdapter(Context context,  BehaviourListResponce behaviourListResponce) {
            this.context = context;
            this.behaviourListResponce=behaviourListResponce;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_work_list,parent,false);
            return new BehaviourAdapter.ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((BehaviourAdapter.ListViewHolder)holder).bindView(position);
            ((BehaviourAdapter.ListViewHolder)holder).tvClass.setText(behaviourListResponce.getResult().getMessage().get(position).getDate());
            ((BehaviourAdapter.ListViewHolder)holder).title.setText(behaviourListResponce.getResult().getMessage().get(position).getTitle());
            ((BehaviourAdapter.ListViewHolder)holder).details.setText(behaviourListResponce.getResult().getMessage().get(position).getDetails());
            student_id = behaviourListResponce.getResult().getMessage().get(position).getStudentId();
            GetStudentbyid();
            ((BehaviourAdapter.ListViewHolder)holder).llClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new BehaviourFragment();
                    Bundle args = new Bundle();
                    args.putString("classname",clas);
                    args.putString("section",section);
                    args.putString("student_id",behaviourListResponce.getResult().getMessage().get(position).getStudentId());
                    args.putString("title",behaviourListResponce.getResult().getMessage().get(position).getTitle());
                    args.putString("details",behaviourListResponce.getResult().getMessage().get(position).getDetails());
                    args.putString("id",behaviourListResponce.getResult().getMessage().get(position).getId());
                    args.putString("student",student_name);
                    fragment.setArguments(args);
                    loadFragment(fragment);
                }
            });

        }
        @Override
        public int getItemCount() {
            return behaviourListResponce.getResult().getMessage().size();
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