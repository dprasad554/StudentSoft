package com.geekhive.studentsoft.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.teachertimetable.TeacherTimetable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class UpcomingAdapter extends RecyclerView.Adapter {

    Context context;
    TeacherTimetable teacherTimetable;

    public UpcomingAdapter(Context context, TeacherTimetable teacherTimetable) {
        this.context = context;
        this.teacherTimetable = teacherTimetable;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
        Calendar calander = Calendar.getInstance();
        int day = calander.get(Calendar.DAY_OF_WEEK);

        Date curenttime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentDateTimeString = sdf.format(curenttime);
        Date time = null;
        try {
            time = new SimpleDateFormat("HH:mm").parse(currentDateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        System.out.println("time1::::" + time);


        switch (day) {
            case Calendar.SUNDAY:
                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getClassName() + "   ON MONDAY");
                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getSectionName());
                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getStartTime());
                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getSubject());
                break;
            case Calendar.MONDAY:
                try {
                    String string1 = teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime();
                    Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(time1);
                    calendar1.add(Calendar.DATE, 1);

                    String string2 = teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getEndTime();
                    Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(time2);
                    calendar2.add(Calendar.DATE, 1);

                    String someRandomTime = currentDateTimeString;
                    Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTime(d);
                    calendar3.add(Calendar.DATE, 1);

                    Date x = calendar3.getTime();
                    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).tvClass.setText("Current class");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getSubject());

                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }
                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime());
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                        }

                    } else if (x.after(calendar2.getTime())) {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).tvClass.setText("Completed class");
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getSubject());
                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }
                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime());

                        }
                    } else {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).tvClass.setText("Upcomming class");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.inactive_dots));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getSubject());
                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }

                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(position).getStartTime());
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case Calendar.TUESDAY:
                try {
                    String string1 = teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime();
                    Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(time1);
                    calendar1.add(Calendar.DATE, 1);

                    String string2 = teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getEndTime();
                    Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(time2);
                    calendar2.add(Calendar.DATE, 1);

                    String someRandomTime = currentDateTimeString;
                    Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTime(d);
                    calendar3.add(Calendar.DATE, 1);

                    Date x = calendar3.getTime();
                    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).tvClass.setText("Current class");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getSubject());

                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }
                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime());
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                        }

                    } else if (x.after(calendar2.getTime())) {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).tvClass.setText("Completed class");
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getSubject());
                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }
                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime());

                        }


                    } else {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).tvClass.setText("Upcomming class");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.inactive_dots));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getSubject());
                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }

                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getTuesday().get(position).getStartTime());
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case Calendar.WEDNESDAY:
                try {
                    String string1 = teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime();
                    Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(time1);
                    calendar1.add(Calendar.DATE, 1);

                    String string2 = teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getEndTime();
                    Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(time2);
                    calendar2.add(Calendar.DATE, 1);

                    String someRandomTime = currentDateTimeString;
                    Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTime(d);
                    calendar3.add(Calendar.DATE, 1);

                    Date x = calendar3.getTime();
                    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).tvClass.setText("Current class");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getSubject());

                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }
                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime());
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                        }

                    } else if (x.after(calendar2.getTime())) {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).tvClass.setText("Completed class");
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getSubject());
                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }
                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime());

                        }
                    } else {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).tvClass.setText("Upcomming class");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.inactive_dots));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getSubject());
                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }

                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getWednesday().get(position).getStartTime());
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case Calendar.THURSDAY:
                try {
                    String string1 = teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime();
                    Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(time1);
                    calendar1.add(Calendar.DATE, 1);

                    String string2 = teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getEndTime();
                    Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(time2);
                    calendar2.add(Calendar.DATE, 1);

                    String someRandomTime = currentDateTimeString;
                    Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTime(d);
                    calendar3.add(Calendar.DATE, 1);

                    Date x = calendar3.getTime();
                    System.out.println("Current time:::::"+x.toString()+":::"+calendar2.getTime()+":::"+calendar1.getTime());
                    if (x.before(calendar2.getTime()) && x.after(calendar1.getTime())) {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).tvClass.setText("Current class");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getSubject());

                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }
                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime());
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                        }

                    } else if (x.after(calendar2.getTime())) {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).tvClass.setText("Completed class");
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getSubject());
                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }
                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime());

                        }
                    } else {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).tvClass.setText("Upcomming class");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.inactive_dots));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getSubject());
                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }

                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getThursday().get(position).getStartTime());
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case Calendar.FRIDAY:
                try {
                    String string1 = teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime();
                    Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(time1);
                    calendar1.add(Calendar.DATE, 1);

                    String string2 = teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getEndTime();
                    Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(time2);
                    calendar2.add(Calendar.DATE, 1);

                    String someRandomTime = currentDateTimeString;
                    Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTime(d);
                    calendar3.add(Calendar.DATE, 1);

                    Date x = calendar3.getTime();
                    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).tvClass.setText("Current class");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getSubject());

                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }
                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime());
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                        }

                    } else if (x.after(calendar2.getTime())) {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).tvClass.setText("Completed class");
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getSubject());
                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }
                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime());

                        }
                    } else {
                        if (teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getType().equals("class")) {
                            if (!teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getClassName().equals("")) {
                                ((ListViewHolder) holder).tvClass.setText("Upcomming class");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.inactive_dots));
                                ((ListViewHolder) holder).classname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).subject.setVisibility(View.VISIBLE);
                                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getClassName());
                                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getSectionName());
                                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getSubject());
                            } else {
                                ((ListViewHolder) holder).tvClass.setText("No Class assigned");
                                ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                                ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                                ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime());
                                ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                            }

                        } else {
                            ((ListViewHolder) holder).tvClass.setText("Leisure");
                            ((ListViewHolder) holder).llClass.setBackgroundColor(context.getResources().getColor(R.color.un_selected));
                            ((ListViewHolder) holder).classname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).sectionname.setVisibility(View.GONE);
                            ((ListViewHolder) holder).time.setText(teacherTimetable.getResult().getMessage().getTimetable().getFriday().get(position).getStartTime());
                            ((ListViewHolder) holder).subject.setVisibility(View.GONE);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case Calendar.SATURDAY:
                ((ListViewHolder) holder).classname.setText("Class Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getClassName() + "   ON MONDAY");
                ((ListViewHolder) holder).sectionname.setText("Section Name :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getSectionName());
                ((ListViewHolder) holder).time.setText("Class Time :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getStartTime());
                ((ListViewHolder) holder).subject.setText("Subject :" + teacherTimetable.getResult().getMessage().getTimetable().getMonday().get(0).getSubject());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout llClass;
        TextView tvClass, classname, sectionname, subject, time;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            llClass = itemView.findViewById(R.id.llClass);
            tvClass = itemView.findViewById(R.id.tvClass);
            classname = itemView.findViewById(R.id.classname);
            sectionname = itemView.findViewById(R.id.sectionname);
            subject = itemView.findViewById(R.id.subject);
            time = itemView.findViewById(R.id.time);
        }

        public void bindView(int position) {

        }

        @Override
        public void onClick(View view) {
        }
    }
}
