package com.geekhive.studentsoft.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.OrdersAr.OrdersAr;
import com.geekhive.studentsoft.beans.confirmorder.ConfirmStatonaryOrder;
import com.geekhive.studentsoft.beans.getallorder.GetAllOrders;
import com.geekhive.studentsoft.beans.orderhistory.OrderHistory;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StationaryOwnerOrderHistory extends AppCompatActivity implements OnResponseListner {

    RecyclerView shoppingHistory_RecyclerView;
    ConnectionDetector mDetector;
    OwnerOrderRecyclerViewAdapter ownerOrderRecyclerViewAdapter;
    GetAllOrders getAllOrders;
    ConfirmStatonaryOrder confirmStatonaryOrder;
    Dialog dialogSuccess;
    OrdersAr ordersAr;
    private ArrayList<String> order_id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationaryhistory);
        shoppingHistory_RecyclerView = (RecyclerView)findViewById(R.id.shoppingHistory_RecyclerView);
        setValues();
        CallService();

    }
    private void setValues() {
        mDetector = new ConnectionDetector(this);
    }
    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            String User_id = Prefs.getPrefRefId(this);
            String db_key = Prefs.getPrefUserAuthenticationkey(this);
            new WebServices(this).GetAllOrders(WebServices.SM_Services,
                    WebServices.ApiType.allorders,db_key);
        }else {
            SnackBar.makeText(this, "Please enter  mobile number", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }
    private void CallConfirmService(String id) {
        if (this.mDetector.isConnectingToInternet()) {
            String User_id = Prefs.getPrefRefId(this);
            String db_key = Prefs.getPrefUserAuthenticationkey(this);
            order_id = new ArrayList<>();
            ordersAr = new OrdersAr();
            order_id.add(id);
            ordersAr.setOrdersArray(order_id);
            new WebServices(this).ConfirmOrders(WebServices.SM_Services,
                    WebServices.ApiType.confirm,ordersAr,db_key);
        }else {
            SnackBar.makeText(this, "Please enter  mobile number", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.allorders) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getAllOrders = (GetAllOrders) response;

                if (!isSucces || getAllOrders == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if(getAllOrders.getResult().getMessage() == null){
                        SnackBar.makeText(this,"No produts are order", SnackBar.LENGTH_SHORT).show();
                    }else {
                        LinearLayoutManager layoutHistoryManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                        shoppingHistory_RecyclerView.setLayoutManager(layoutHistoryManager);
                        ownerOrderRecyclerViewAdapter = new OwnerOrderRecyclerViewAdapter(this,getAllOrders);
                        shoppingHistory_RecyclerView.setAdapter(ownerOrderRecyclerViewAdapter);
                    }

                }
            }
        }if (apiType == WebServices.ApiType.confirm) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                confirmStatonaryOrder = (ConfirmStatonaryOrder) response;

                if (!isSucces || confirmStatonaryOrder == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(this, "Order Delivered", SnackBar.LENGTH_SHORT).show();
                    Intent j = new Intent(StationaryOwnerOrderHistory.this, StationaryOwnerOrderHistory.class);
                    startActivity(j);
                }
            }
        }
    }

    public class OwnerOrderRecyclerViewAdapter extends RecyclerView.Adapter<OwnerOrderRecyclerViewAdapter.ViewHolder> {

        //Variables
        private Context mcontext;
        GetAllOrders getAllOrders;

        Boolean checked = true;

        public OwnerOrderRecyclerViewAdapter(Context context,GetAllOrders getAllOrders) {
            this.mcontext = context;
            this.getAllOrders = getAllOrders;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sowner_order_list,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.card_view.setVisibility(View.VISIBLE);
            holder.productQty.setText("Quantity: " + getAllOrders.getResult().getMessage().get(position).getQuantity());
            holder.productName.setText("Name :"+getAllOrders.getResult().getMessage().get(position).getProductDetails().getName());
            holder.productSize.setText( "Order ID :"+getAllOrders.getResult().getMessage().get(position).getId());
            holder.tv_price.setText("Rs. " + getAllOrders.getResult().getMessage().get(position).getQuantity() * getAllOrders.getResult().getMessage().get(position).getTotalProductPrice());
            holder.productContent.setText("Student ID :"+getAllOrders.getResult().getMessage().get(position).getPersonId());
            if(getAllOrders.getResult().getMessage().get(position).getStatus().equals("created")){
                holder.tv_pStatus.setText("Order Status : "+"New Orders");
                holder.tv_confirm.setVisibility(View.GONE);
                holder.card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Initializepopup();
                        initializdeletePopup(getAllOrders.getResult().getMessage().get(position).getId());
                        //CallConfirmService(getAllOrders.getResult().getMessage().get(position).getId());
                    }
                });
            }if(getAllOrders.getResult().getMessage().get(position).getStatus().equals("placed")){
                holder.tv_pStatus.setText("Order Status : "+"Delivered");
                holder.tv_confirm.setVisibility(View.GONE);
            }if(getAllOrders.getResult().getMessage().get(position).getStatus().equals("confirmed")){
                holder.tv_pStatus.setText("Order Status : "+"Confirmed");
                holder.tv_confirm.setVisibility(View.GONE);
            }

          }

        @Override
        public int getItemCount() {
            return getAllOrders.getResult().getMessage().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView productImage;
            TextView productName,productContent,productSize,productQty,tv_price,tv_pStatus,tv_confirm;
            LinearLayout history_layout;
            CardView card_view;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                productName = (TextView)itemView.findViewById(R.id.tv_pName);
                productContent = (TextView)itemView.findViewById(R.id.tv_pContent);
                productSize = (TextView)itemView.findViewById(R.id.tv_pSize);
                productQty = (TextView)itemView.findViewById(R.id.tv_pQty);
                tv_price = (TextView)itemView.findViewById(R.id.tv_Pprice);
                card_view = (CardView)itemView.findViewById(R.id.card_view);
                tv_pStatus = (TextView)itemView.findViewById(R.id.tv_pStatus);
                tv_confirm = (TextView)itemView.findViewById(R.id.tv_confirm);
            }
        }
    }
    private void Initializepopup() {
        dialogSuccess = new Dialog(this);
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.order_popup);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializdeletePopup(String order_id) {
        dialogSuccess.setContentView(R.layout.order_popup);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
        final TextView vT_psd_yes = (TextView) dialogSuccess.findViewById(R.id.vT_psd_yes);
        final TextView vT_psd_no = (TextView) dialogSuccess.findViewById(R.id.vT_psd_no);
        final TextView vT_sucess = dialogSuccess.findViewById(R.id.vT_sucess);
        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);

        vT_psd_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSuccess.isShowing()) {
                    dialogSuccess.dismiss();
                    CallConfirmService(order_id);
                }
            }
        });

        vT_psd_no.setOnClickListener(new View.OnClickListener() {
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
