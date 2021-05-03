package com.geekhive.studentsoft.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.StudentfeeAdapter;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HostelActivity extends AppCompatActivity implements View.OnClickListener {

    ConnectionDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.backPress:
                finish();
                break;*/
        }
    }
}


