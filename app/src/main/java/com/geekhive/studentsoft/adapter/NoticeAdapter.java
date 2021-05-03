package com.geekhive.studentsoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.notice.NoticeData;
import com.geekhive.studentsoft.beans.notification.Notification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class NoticeAdapter extends RecyclerView.Adapter {

    Context context;
    NoticeData noticeData;
    String formattedDate;
    public NoticeAdapter(Context context, NoticeData noticeData) {
        this.context = context;
        this.noticeData = noticeData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return  noticeData.getResult().getMessage().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout llClass;
        TextView tvClass,tv_date;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            llClass = itemView.findViewById(R.id.llClass);
            tvClass = itemView.findViewById(R.id.tvClass);
            tv_date = itemView.findViewById(R.id.tv_date);
        }

        public void bindView(int position){
            tvClass.setText(noticeData.getResult().getMessage().get(position).getNoticeDetails());

           /* DateTimeFormatter inputFormatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
            }
            DateTimeFormatter outputFormatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
            }
            LocalDate date = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                date = LocalDate.parse(noticeData.getResult().getMessage().get(position).getDate(), inputFormatter);
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formattedDate = outputFormatter.format(date);
            }*/

            tv_date.setText("Date : "+noticeData.getResult().getMessage().get(position).getDate());
        }

        @Override
        public void onClick(View view) {

        }
    }

}
