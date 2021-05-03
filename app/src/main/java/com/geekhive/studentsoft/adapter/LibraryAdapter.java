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
import com.geekhive.studentsoft.beans.studentlibrary.GetBookIssued;

public class LibraryAdapter extends RecyclerView.Adapter {

    Context context;
    GetBookIssued getBookIssued;
    public LibraryAdapter(Context context,GetBookIssued getBookIssued) {
        this.context = context;
        this.getBookIssued=getBookIssued;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
        ((LibraryAdapter.ListViewHolder)holder).tvBurrowed.setText("Book Name:"+getBookIssued.getResult().getMessage().get(position).getName());
        ((LibraryAdapter.ListViewHolder)holder).tvHMS.setText("Borrowed Date:"+getBookIssued.getResult().getMessage().get(position).getIssuedOn());
    }

    @Override
    public int getItemCount() {
        return getBookIssued.getResult().getMessage().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout llClass;
        TextView tvClass,tvBurrowed,tvHMS;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            llClass = itemView.findViewById(R.id.llClass);
            tvClass = itemView.findViewById(R.id.tvClass);
            tvBurrowed = itemView.findViewById(R.id.tvBurrowed);
            tvHMS = itemView.findViewById(R.id.tvHMS);
        }

        public void bindView(int position){
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
