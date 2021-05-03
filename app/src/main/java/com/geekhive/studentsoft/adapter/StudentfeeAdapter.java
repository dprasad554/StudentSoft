package com.geekhive.studentsoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.feestructure.StudentFeeS;

public class StudentfeeAdapter extends RecyclerView.Adapter {

    Context context;
    StudentFeeS studentFeeS;

    public StudentfeeAdapter(Context context, StudentFeeS studentFeeS) {
        this.context = context;
        this.studentFeeS = studentFeeS;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fee_adapter, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
        String mnth = studentFeeS.getResult().getMessage().get(position).getMonth() + " - " + studentFeeS.getResult().getMessage().get(position).getYear();
        ((ListViewHolder) holder).pMonth.setText(mnth);
        String dDate = "Due Date" + " : " + studentFeeS.getResult().getMessage().get(position).getDueDate();
        ((ListViewHolder) holder).cDate.setText(dDate);
        ((ListViewHolder) holder).fTFees.setText( "₹ " + studentFeeS.getResult().getMessage().get(position).getFinalTermFees());
        ((ListViewHolder) holder).tFees.setText( "₹ " + studentFeeS.getResult().getMessage().get(position).getTutionFees());
        ((ListViewHolder) holder).eFees.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getExaminationFees());

        ((ListViewHolder) holder).pFees.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getPracticalFees());
        ((ListViewHolder) holder).lFees.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getLibraryFees());

        ((ListViewHolder) holder).amFees.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getAnnualMaintenenceFees());
        ((ListViewHolder) holder).aFees.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getAdmissionFees());

        if (String.valueOf(studentFeeS.getResult().getMessage().get(position).getFeesPaid()).equals("0")){
            if (String.valueOf(studentFeeS.getResult().getMessage().get(position).getFeesPending()).equals("0")){
                ((ListViewHolder) holder).dueAmnt.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getTotalFees());
                ((ListViewHolder) holder).paidAmnt.setText("₹ " + "0");
                ((ListViewHolder) holder).blncAmnt.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getTotalFees());
            } else {
                ((ListViewHolder) holder).dueAmnt.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getFeesPending());
                ((ListViewHolder) holder).paidAmnt.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getFeesPaid());
                ((ListViewHolder) holder).blncAmnt.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getFeesPending());
            }
        } else {
            ((ListViewHolder) holder).dueAmnt.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getFeesPending());
            ((ListViewHolder) holder).paidAmnt.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getFeesPaid());
            ((ListViewHolder) holder).blncAmnt.setText("₹ " + studentFeeS.getResult().getMessage().get(position).getFeesPending());
        }


    }

    @Override
    public int getItemCount() {
        return studentFeeS.getResult().getMessage().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView pMonth, cDate, fTFees, tFees, eFees, pFees, lFees, amFees, aFees, dueAmnt, paidAmnt, blncAmnt;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            pMonth = itemView.findViewById(R.id.pMonth);
            cDate = itemView.findViewById(R.id.cDate);
            fTFees = itemView.findViewById(R.id.fTFees);
            tFees = itemView.findViewById(R.id.tFees);
            eFees = itemView.findViewById(R.id.eFees);
            pFees = itemView.findViewById(R.id.pFees);
            lFees = itemView.findViewById(R.id.lFees);
            amFees = itemView.findViewById(R.id.amFees);
            aFees = itemView.findViewById(R.id.aFees);
            dueAmnt = itemView.findViewById(R.id.dueAmnt);
            paidAmnt = itemView.findViewById(R.id.paidAmnt);
            blncAmnt = itemView.findViewById(R.id.blncAmnt);

        }

        public void bindView(int position) {
        }

        @Override
        public void onClick(View view) {
        }
    }
}
