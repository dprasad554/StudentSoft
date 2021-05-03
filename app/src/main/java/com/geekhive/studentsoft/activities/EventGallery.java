package com.geekhive.studentsoft.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.EventAdapter;
import com.geekhive.studentsoft.adapter.EventgalleryAdapter;
import com.geekhive.studentsoft.adapter.NotificationAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventGallery extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.vrEvents)
    RecyclerView vrEvents;
    NotificationAdapter notificationAdapter;
    @BindView(R.id.backPress)
    ImageView backPress;
    EventgalleryAdapter eventgalleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_event_gallery);
        ButterKnife.bind(this);
        ArrayList<String> imageslist = (ArrayList<String>) getIntent().getSerializableExtra("key");
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        vrEvents.setLayoutManager(linearLayoutManager);
        eventgalleryAdapter = new EventgalleryAdapter(this,imageslist);
        vrEvents.setAdapter(eventgalleryAdapter);
        backPress.setOnClickListener(this);
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
