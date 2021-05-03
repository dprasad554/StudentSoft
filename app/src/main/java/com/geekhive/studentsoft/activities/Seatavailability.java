package com.geekhive.studentsoft.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.SeatsavailabilityAdapter;
import com.geekhive.studentsoft.adapter.SyllabusAdapter;
import com.geekhive.studentsoft.utils.ConnectionDetector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Seatavailability extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.vr_seats)
    RecyclerView vr_seats;
    @BindView(R.id.backPress)
    ImageView backPress;
    SeatsavailabilityAdapter seatsavailabilityAdapter;
    ConnectionDetector mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_availability);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        vr_seats.setLayoutManager(linearLayoutManager);
        seatsavailabilityAdapter = new SeatsavailabilityAdapter(this);
        vr_seats.setAdapter(seatsavailabilityAdapter);
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
}
