package com.geekhive.studentsoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.activities.EventGallery;
import com.geekhive.studentsoft.activities.StationaryListActivity;
import com.geekhive.studentsoft.beans.getallevents.GetAllEvents;
import com.geekhive.studentsoft.utils.SnackBar;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends RecyclerView.Adapter {

    Context context;
    GetAllEvents getAllEvents;
    ArrayList<String> images = new ArrayList<>();
    String currentDateandTime;
    public EventAdapter(Context context,GetAllEvents getAllEvents) {
        this.context = context;
        this.getAllEvents = getAllEvents;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat time = new SimpleDateFormat("HH:MM", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        System.out.println("currentTime::::::" + currentDateandTime);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        currentDateandTime = mdformat.format(calendar.getTime());
        Date date1 = getDate(currentDateandTime);
        Date date2 = getDate(getAllEvents.getResult().getMessage().get(position).getEventDate());

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);



        ((ListViewHolder)holder).llClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                images = new ArrayList<>();

                if(getAllEvents.getResult().getMessage().get(position).getMediaUrls().size()!=0){
                    if(c1.after(c2)){
                        images.add(getAllEvents.getResult().getMessage().get(position).getMediaUrls().get(2).toString());
                        Intent intent = new Intent(context, EventGallery.class);
                        intent.putExtra("key", images);
                        context.startActivity(intent);
                    }else {

                    }

                }


            }
        });
        if(getAllEvents.getResult().getMessage().get(position).getMediaUrls().size() !=0){
            String url = "http://"+getAllEvents.getResult().getMessage().get(position).getMediaUrls().get(0).toString();
            if (!url.equals("")){
                Picasso.get()
                        .load(url)//download URL
                        .error(R.drawable.event_school)//if failed
                        .into(((ListViewHolder)holder).event_image);
            }
        }else {
            Picasso.get()
                    .load(R.drawable.event_school)//if failed
                    .into(((ListViewHolder) holder).event_image);
        }
        ((ListViewHolder)holder).tv_event_name.setText("Name:"+getAllEvents.getResult().getMessage().get(position).getEventDetails());
        ((ListViewHolder)holder).tv_event_date.setText("Event Date:"+getAllEvents.getResult().getMessage().get(position).getEventDate());
        ((ListViewHolder)holder).tv_event_time.setText("Event Time:"+getAllEvents.getResult().getMessage().get(position).getEventTime());
    }

    @Override
    public int getItemCount() {
        return getAllEvents.getResult().getMessage().size();
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
    public static Date getDate(String s) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
}
