package com.geekhive.studentsoft.activities;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.EventgalleryAdapter;
import com.geekhive.studentsoft.beans.getallorder.GetAllOrders;
import com.geekhive.studentsoft.beans.orderhistory.OrderHistory;
import com.geekhive.studentsoft.beans.orderhistoryfinal.Message;
import com.geekhive.studentsoft.beans.orderhistoryfinal.OrderHistoryFinal;
import com.geekhive.studentsoft.beans.orderhistoryfinal.ProductDetails;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StationaryHistory extends AppCompatActivity implements OnResponseListner {

    RecyclerView shoppingHistory_RecyclerView;
    ConnectionDetector mDetector;
    GetAllOrders getAllOrders;
    OrderHistory orderHistory;
    String id;
    String  p_name;
    ArrayList<String> titlesNames;
    OrderHistoryFinal orderHistoryFinal;
    List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationaryhistory);
        shoppingHistory_RecyclerView = (RecyclerView) findViewById(R.id.shoppingHistory_RecyclerView);
        setValues();
        GetAllOrders();
    }

    private void setValues() {
        mDetector = new ConnectionDetector(this);
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            String User_id = Prefs.getPrefRefId(this);
            String db_key = Prefs.getPrefUserAuthenticationkey(this);
            new WebServices(this).Orderhistory(WebServices.SM_Services,
                    WebServices.ApiType.orderhistory, User_id, db_key);
        } else {
            SnackBar.makeText(this, "Please enter  mobile number", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }

    private void GetAllOrders() {
        if (this.mDetector.isConnectingToInternet()) {
            String User_id = Prefs.getPrefRefId(this);
            String db_key = Prefs.getPrefUserAuthenticationkey(this);
            new WebServices(this).GetAllOrders(WebServices.SM_Services,
                    WebServices.ApiType.allorders, db_key);
        } else {
            SnackBar.makeText(this, "Please enter  mobile number", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.orderhistory) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                orderHistory = (OrderHistory) response;

                if (!isSucces || orderHistory == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (orderHistory.getResult().getMessage() == null) {
                        SnackBar.makeText(this, "No produts are order", SnackBar.LENGTH_SHORT).show();
                    } else {
                        orderHistoryFinal = new OrderHistoryFinal();
                        messageList = new ArrayList<>();
                        for (int j = 0; j < getAllOrders.getResult().getMessage().size(); j++) {
                            for(int i = 0; i < orderHistory.getResult().getMessage().size(); i++){
                                if(orderHistory.getResult().getMessage().get(i).getId().equals(getAllOrders.getResult().getMessage().get(j).getId())){
                                    Message message = new Message();
                                    ProductDetails productDetails = new ProductDetails();
                                    productDetails.setName(getAllOrders.getResult().getMessage().get(j).getProductDetails().getName());
                                    productDetails.setMediaUrls(getAllOrders.getResult().getMessage().get(j).getProductDetails().getMediaUrls());
                                    productDetails.setDescription(getAllOrders.getResult().getMessage().get(j).getProductDetails().getDescription());
                                    message.setId(orderHistory.getResult().getMessage().get(i).getId());
                                    message.setProductDetails(productDetails);
                                    message.setQuantity(orderHistory.getResult().getMessage().get(i).getQuantity());
                                    message.setProductPrice(getAllOrders.getResult().getMessage().get(j).getProductDetails().getPrice());
                                    message.setTotalProductPrice(orderHistory.getResult().getMessage().get(i).getQuantity() * getAllOrders.getResult().getMessage().get(j).getTotalProductPrice());
                                    message.setStatus(getAllOrders.getResult().getMessage().get(j).getStatus());
                                    message.setDeliveryDate(getAllOrders.getResult().getMessage().get(j).getDeliveryDate());
                                    messageList.add(message);
                                }
                            }

                        }
                        orderHistoryFinal.setMessage(messageList);
                        if(orderHistoryFinal.getMessage().size() != 0){
                            LinearLayoutManager layoutHistoryManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                            shoppingHistory_RecyclerView.setLayoutManager(layoutHistoryManager);
                            ShoppingHistoryRecyclerViewAdapter historyAdapter = new ShoppingHistoryRecyclerViewAdapter(this, orderHistoryFinal);
                            shoppingHistory_RecyclerView.setAdapter(historyAdapter);
                        }

                    }

                }
            }
        }
        if (apiType == WebServices.ApiType.allorders) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getAllOrders = (GetAllOrders) response;

                if (!isSucces || getAllOrders == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (getAllOrders.getResult().getMessage() == null) {
                        SnackBar.makeText(this, "No order", SnackBar.LENGTH_SHORT).show();
                    } else {
                        CallService();
                    }

                }
            }
        }
    }

    public class ShoppingHistoryRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingHistoryRecyclerViewAdapter.ViewHolder> {

        //Variables
        private Context mcontext;
        OrderHistory orderHistory;
        GetAllOrders getAllOrders;
        ArrayList<String> titlesNames;
        OrderHistoryFinal orderHistoryFinal;


        Boolean checked = true;

        public ShoppingHistoryRecyclerViewAdapter(Context context,OrderHistoryFinal orderHistoryFinal) {
            this.mcontext = context;
            this.orderHistoryFinal = orderHistoryFinal;
        }

        @NonNull
        @Override
        public ShoppingHistoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shoppinghistory, parent, false);
            return new ShoppingHistoryRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShoppingHistoryRecyclerViewAdapter.ViewHolder holder, final int position) {
           // holder.productSize.setText("Size: " + orderHistory.getResult().getMessage().get(position).getSize());
            holder.productQty.setText("Quantity: " + orderHistoryFinal.getMessage().get(position).getQuantity());
            holder.productDeliverDate.setText("Delivery Date:  " + orderHistoryFinal.getMessage().get(position).getDeliveryDate());
            holder.productName.setText("Name :"+orderHistoryFinal.getMessage().get(position).getProductDetails().getName());
            holder.tv_id.setText( "Order ID :"+orderHistoryFinal.getMessage().get(position).getId() );
            holder.tv_price.setText("Rs. " + orderHistoryFinal.getMessage().get(position).getTotalProductPrice());
            holder.productContent.setText(orderHistoryFinal.getMessage().get(position).getProductDetails().getDescription());
            String url = "http://"+orderHistoryFinal.getMessage().get(position).getProductDetails().getMediaUrls().get(0);
            if (!url.equals("")){
                Picasso.get()
                        .load(url)//download URL
                        .error(R.drawable.event_school)//if failed
                        .into(holder.productImage);
            }
        }

        @Override
        public int getItemCount() {
            return orderHistoryFinal.getMessage().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView productImage;
            TextView productName, productContent, productSize, productQty, productPrice,
                    productCut, productSave, productDeliver, productDeliverDate, tv_price,tv_id;
            LinearLayout history_layout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                productImage = (ImageView) itemView.findViewById(R.id.iv_productImage);
                productName = (TextView) itemView.findViewById(R.id.tv_productName);
                productContent = (TextView) itemView.findViewById(R.id.tv_productContent);
                productSize = (TextView) itemView.findViewById(R.id.tv_productSize);
                productQty = (TextView) itemView.findViewById(R.id.tv_productQty);
                productPrice = (TextView) itemView.findViewById(R.id.tv_productPrice);
                productCut = (TextView) itemView.findViewById(R.id.tv_productCut);
                productCut.setPaintFlags(productCut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                productSave = (TextView) itemView.findViewById(R.id.tv_productSave);
                productDeliverDate = (TextView) itemView.findViewById(R.id.tv_productDate);
                tv_price = (TextView) itemView.findViewById(R.id.tv_price);
                history_layout = (LinearLayout) itemView.findViewById(R.id.history_layout);
                tv_id = (TextView)itemView.findViewById(R.id.tv_id);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
