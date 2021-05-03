package com.geekhive.studentsoft.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.EventGallery;
import com.geekhive.studentsoft.activities.LeaveManagementStudent;
import com.geekhive.studentsoft.beans.getallevents.GetAllEvents;
import com.geekhive.studentsoft.beans.getupcommingevents.GetUpcommingEvents;
import com.geekhive.studentsoft.utils.WebServices;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.geekhive.studentsoft.utils.util.REQUEST_TAKE_PHOTO_PROFILE_PIC;
import static com.geekhive.studentsoft.utils.util.SELECT_FILE_PROFILE_PIC;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item_list,parent,false);
        return new UpcommingEventAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((UpcommingEventAdapter.ListViewHolder)holder).bindView(position);
        String url = "http://"+getUpcommingEvents.getResult().getMessage().get(position).getMediaUrls().get(1).toString();
        if (!url.equals("")){
            Picasso.get()
                    .load(url)//download URL
                    .error(R.drawable.event_school)//if failed
                    .into(((UpcommingEventAdapter.ListViewHolder)holder).event_image);
        }
        ((ListViewHolder)holder).tv_event_name.setText("Name:"+getUpcommingEvents.getResult().getMessage().get(position).getEventDetails());
        ((ListViewHolder)holder).tv_event_date.setText("Event Date:"+getUpcommingEvents.getResult().getMessage().get(position).getEventDate());
        ((ListViewHolder)holder).tv_event_time.setText("Event Time:"+getUpcommingEvents.getResult().getMessage().get(position).getEventTime());
        ((ListViewHolder)holder).llClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);

        btnSubmitLeave.setOnClickListener(new View.OnClickListener() {
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
