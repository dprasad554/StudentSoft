package com.geekhive.studentsoft.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.NotificationAdapter;
import com.geekhive.studentsoft.adapter.StationaryAdapter;
import com.geekhive.studentsoft.adapter.StationaryProductAdapter;
import com.geekhive.studentsoft.beans.addonlinepayment.CreateOnlinePayment;
import com.geekhive.studentsoft.beans.allcartitem.AllCartItem;
import com.geekhive.studentsoft.beans.allcartitem.Detail;
import com.geekhive.studentsoft.beans.createorder.CreateOrder;
import com.geekhive.studentsoft.beans.getcartbyuser.GetCartByUser;
import com.geekhive.studentsoft.beans.postpayment.PostPaymentOnline;
import com.geekhive.studentsoft.beans.removeproductfromcart.RemoveProductFromCart;
import com.geekhive.studentsoft.beans.stationarycategory.StationaryCategory;
import com.geekhive.studentsoft.beans.stonaryprouct.StonaryProduct;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartlistActivicty extends AppCompatActivity  implements View.OnClickListener,OnResponseListner{

    @BindView(R.id.vR_all_list)
    RecyclerView vR_all_list;
    NotificationAdapter notificationAdapter;
    @BindView(R.id.backPress)
    ImageView backPress;
    ConnectionDetector mDetector;
    String main_category;
    GetCartByUser getCartByUser;
    RecyclerView vrStationary;
    StationaryProductAdapter stationarylistAdapter;
    Button orderPlace;
    Dialog paypopup;
    CreateOrder createOrder;
    AllCartItem allCartItem;
    List<Detail> orderList = new ArrayList<>();
    List<String> orderLists;
    int totalAmountPayable;
    String orderId;
    Double total_price = 0.0;
    Double final_value = 0.0;
    TextView final_Price,tv_total;
    LinearLayout price_bar,payment_bar;
    RemoveProductFromCart removeProductFromCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_cart_list);
        ButterKnife.bind(this);
        main_category = getIntent().getStringExtra("main_category");
        mDetector = new ConnectionDetector(this);
        vrStationary = findViewById(R.id.vR_all_list);
        CallCartByuser();
        backPress.setOnClickListener(this);
        orderPlace = findViewById(R.id.btn_orderPlace);
        final_Price = findViewById(R.id.final_Price);
        tv_total = findViewById(R.id.tv_total);
        price_bar = findViewById(R.id.price_bar);
        payment_bar = findViewById(R.id.payment_bar);

        orderPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allCartItem = new AllCartItem();
                orderList = new ArrayList<>();
                double total;
                for (int i =0;i<getCartByUser.getResult().getMessage().size();i++){
                    Detail order = new Detail();
                    order.setColor(getCartByUser.getResult().getMessage().get(i).getColor());
                    order.setId(getCartByUser.getResult().getMessage().get(i).getId());
                    order.setPersonId(getCartByUser.getResult().getMessage().get(i).getPersonId());
                    order.setPersonType(getCartByUser.getResult().getMessage().get(i).getPersonType());
                    order.setProductId(getCartByUser.getResult().getMessage().get(i).getProductId());
                    order.setSize(getCartByUser.getResult().getMessage().get(i).getSize());
                    order.setQuantity(Integer.valueOf(String.valueOf(getCartByUser.getResult().getMessage().get(i).getQuantity())));
                    order.setProductPrice(Integer.valueOf(getCartByUser.getResult().getMessage().get(i).getProductDetails().getPrice()));
                    order.setTotalProductPrice(Integer.valueOf(getCartByUser.getResult().getMessage().get(i).getProductDetails().getPrice()));
                    orderList.add(order);
                    totalAmountPayable = Integer.parseInt(String.valueOf(getCartByUser.getResult().getMessage().get(i).getProductPrice()));
                }
                    allCartItem.setDetails(orderList);
                InitializePaypopup();
                initializePopup();
            }
        });
    }

    private void CallCartByuser() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetCartByUser(WebServices.SM_Services,
                    WebServices.ApiType.getcartbyuser,Prefs.getPrefRefId(this),Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }

    private void CallRemoveService(String id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).RemoveFromCart(WebServices.SM_Services,
                    WebServices.ApiType.removefromcart,id,Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }



    private void CallCreateOrder() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Createorder(WebServices.SM_Services,
                    WebServices.ApiType.createorder,allCartItem,Prefs.getPrefUserAuthenticationkey(this));
        }else {
            SnackBar.makeText(this, "Please enter  mobile number", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }
    private void CallPayOnline() {
        if (this.mDetector.isConnectingToInternet()) {
            orderLists = new ArrayList<>();
            for(int j=0; j<createOrder.getResult().getMessage().size(); j++){
                orderLists.add(createOrder.getResult().getMessage().get(j).getId());
            }
            CreateOnlinePayment createOnlinePayment = new CreateOnlinePayment();
            createOnlinePayment.setAmount(totalAmountPayable);
            createOnlinePayment.setOrderIds(orderLists);
            createOnlinePayment.setPaymentType("ONLINE");
            createOnlinePayment.setPersonId(Prefs.getPrefRefId(this));
            createOnlinePayment.setPersonType(Prefs.getUserType(this));
            String db_key = Prefs.getPrefUserAuthenticationkey(this);
            new WebServices(this).PayOnline(WebServices.SM_Services,
                    WebServices.ApiType.payonline,createOnlinePayment,db_key);
        }else {
            SnackBar.makeText(this, "Please enter  mobile number", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.getcartbyuser) {
            if (!isSucces) {
                SnackBar.makeText(CartlistActivicty.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getCartByUser = (GetCartByUser) response;

                if (!isSucces || getCartByUser.getResult().getMessage() == null || getCartByUser.getResult().getMessage().size() == 0) {
                    SnackBar.makeText(CartlistActivicty.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                    price_bar.setVisibility(View.GONE);
                    payment_bar.setVisibility(View.GONE);
                } else {
                    price_bar.setVisibility(View.VISIBLE);
                    payment_bar.setVisibility(View.VISIBLE);
                    for (int i=0; i < getCartByUser.getResult().getMessage().size(); i++) {
                        total_price = Double.parseDouble(String.valueOf(getCartByUser.getResult().getMessage().get(i).getProductPrice()*getCartByUser.getResult().getMessage().get(i).getQuantity()));
                        final_value += total_price;
                        final_Price.setText("Rs."+final_value);
                        tv_total .setText("Grand Total  Rs. "+final_value);
                    }
                    LinearLayoutManager layoutCartManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                    vrStationary.setLayoutManager(layoutCartManager);
                    ShoppingCartRecyclerViewAdapter cartAdapter = new ShoppingCartRecyclerViewAdapter(this,getCartByUser);
                    vrStationary.setAdapter(cartAdapter);
                    vrStationary.setNestedScrollingEnabled(false);
                }
            }
        }else if (apiType == WebServices.ApiType.createorder) {
            if (!isSucces) {
                SnackBar.makeText(CartlistActivicty.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                createOrder = (CreateOrder) response;

                if (!isSucces || createOrder == null) {
                    SnackBar.makeText(CartlistActivicty.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(CartlistActivicty.this, "Suceess", SnackBar.LENGTH_SHORT).show();
                    startActivity(new Intent(this, StationaryHistory.class));
                    CartlistActivicty.this.finish();
                    //CallPayOnline();
                      /*orderId = createOrder.getResult().getMessage().get(0).getId();
                      Intent intent = new Intent(CreateOrderActivity.this, OrderSucessActivity.class);
                      intent.putExtra("name",name);
                      intent.putExtra("area",area);
                      intent.putExtra("city",city);
                      intent.putExtra("state",state);
                      intent.putExtra("pincode",pincode);
                      intent.putExtra("phone",mobile);
                      intent.putExtra("type",type);
                      intent.putExtra("addressid",id);
                      startActivity(intent);*/
                }
            }
        }else if (apiType == WebServices.ApiType.payonline) {
            if (!isSucces) {
                SnackBar.makeText(CartlistActivicty.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final PostPaymentOnline paymentOnline = (PostPaymentOnline) response;

                if (!isSucces || paymentOnline == null) {
                    SnackBar.makeText(CartlistActivicty.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(CartlistActivicty.this, "Suceess", SnackBar.LENGTH_SHORT).show();
                    startActivity(new Intent(this, StationaryHistory.class));
                    CartlistActivicty.this.finish();
                }
            }
        }else if (apiType == WebServices.ApiType.removefromcart) {
            if (!isSucces) {
                SnackBar.makeText(CartlistActivicty.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                removeProductFromCart = (RemoveProductFromCart) response;

                if (!isSucces || removeProductFromCart == null) {
                    SnackBar.makeText(CartlistActivicty.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } if (!isSucces || removeProductFromCart.getResult() == null) {
                    SnackBar.makeText(CartlistActivicty.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(CartlistActivicty.this, "Suceess", SnackBar.LENGTH_SHORT).show();
                    startActivity(new Intent(this, CartlistActivicty.class));
                    CartlistActivicty.this.finish();
                }
            }
        }
    }

    private void InitializePaypopup() {
        paypopup = new Dialog(this);
        paypopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        paypopup.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        paypopup.setContentView(R.layout.popup_pay);
        paypopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        paypopup.setCancelable(true);
        paypopup.setCanceledOnTouchOutside(true);
    }
    private void initializePopup() {
        paypopup.setContentView(R.layout.popup_pay);
        paypopup.setCancelable(true);
        paypopup.setCanceledOnTouchOutside(true);
        paypopup.show();

        final TextView vT_psd_pay = (TextView) paypopup.findViewById(R.id.vT_psd_pay);
        final TextView vT_psd_cod = (TextView) paypopup.findViewById(R.id.vT_psd_cod);
        final TextView vT_psd_online = (TextView) paypopup.findViewById(R.id.vT_psd_online);
        final TextView vT_psd_Emi = (TextView) paypopup.findViewById(R.id.vT_psd_Emi);

        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
        paypopup.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paypopup.getWindow().setGravity(Gravity.BOTTOM);
        vT_psd_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paypopup.isShowing()) {
                    paypopup.dismiss();
                    CallCreateOrder();
                }
            }
        });

        paypopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        paypopup.setCancelable(true);
        paypopup.setCanceledOnTouchOutside(true);
        paypopup.show();
    }

    public class ShoppingCartRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingCartRecyclerViewAdapter.ViewHolder>{

        //Variables
        private Context mcontext;
        GetCartByUser cartbyuser;
        ConnectionDetector mDetector;

        Boolean checked = true;
        String item_id ;

        public ShoppingCartRecyclerViewAdapter(Context context,GetCartByUser cartbyuser) {
            this.mcontext = context;
            this.cartbyuser = cartbyuser;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            setValues();
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shoppingcart,parent,false);
            return new ViewHolder(view);
        }

        private void setValues() {
            mDetector = new ConnectionDetector(mcontext);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            String url ="";
            holder.productName.setText(cartbyuser.getResult().getMessage().get(position).getProductDetails().getName());
            holder.productContent.setText(cartbyuser.getResult().getMessage().get(position).getProductDetails().getDescription());
            holder.productPrice.setText("Rs "+cartbyuser.getResult().getMessage().get(position).getProductPrice() * cartbyuser.getResult().getMessage().get(position).getQuantity() );
            holder.tv_quantity.setText("Qty :"+cartbyuser.getResult().getMessage().get(position).getQuantity());
            item_id=cartbyuser.getResult().getMessage().get(position).getProductDetails().getId();

            holder.btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CallRemoveService(cartbyuser.getResult().getMessage().get(position).getId());
                }
            });


            if(cartbyuser.getResult().getMessage().get(position).getProductDetails().getMediaUrls().equals("") ||
                    cartbyuser.getResult().getMessage().get(position).getProductDetails().getMediaUrls().size() != 0) {
                if(cartbyuser.getResult().getMessage().get(position).getProductDetails().getMediaUrls() != null){
                    url ="http://" +cartbyuser.getResult().getMessage().get(position).getProductDetails().getMediaUrls().get(0).toString();
                    //url = "http://" + productbyID.getResult().getMessage().getMedia().get(position).getUrl();
                    /*if (!url.equals("") ) {
                        if (!url.equals(null)) {

                        }
                    }*/
                }
            }
            Picasso.get()
                    .load(url)//download URL
                    .error(R.drawable.book)//if failed
                    .into(holder.productImage);

        }

        @Override
        public int getItemCount() {
            return cartbyuser.getResult().getMessage().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView productImage;
            TextView productName,productContent,productSize,productQty,productPrice,
                    productCut,productSave,productReturn,btn_remove,btn_edit,btn_wishlist,tv_quantity;
            LinearLayout linearHistory,product_d;
            CardView card_visible;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                productImage = (ImageView)itemView.findViewById(R.id.iv_productImage);
                productName = (TextView)itemView.findViewById(R.id.tv_productName);
                productContent = (TextView)itemView.findViewById(R.id.tv_productContent);
                productSize = (TextView)itemView.findViewById(R.id.tv_productSize);
                productQty = (TextView)itemView.findViewById(R.id.tv_productQty);
                productPrice = (TextView)itemView.findViewById(R.id.tv_productPrice);
                productCut = (TextView)itemView.findViewById(R.id.tv_productCut);
                productCut.setPaintFlags(productCut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                productSave = (TextView)itemView.findViewById(R.id.tv_productSave);
                productReturn = (TextView)itemView.findViewById(R.id.tv_productReturn);
                btn_remove = (TextView)itemView.findViewById(R.id.btn_remove);
                btn_edit = (TextView)itemView.findViewById(R.id.btn_edit);
                btn_wishlist = (TextView)itemView.findViewById(R.id.btn_wishlist);
                tv_quantity = (TextView)itemView.findViewById(R.id.tv_quantity);
                product_d = (LinearLayout)itemView.findViewById(R.id.product_d);
            }
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backPress:
                finish();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.login_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }
}

