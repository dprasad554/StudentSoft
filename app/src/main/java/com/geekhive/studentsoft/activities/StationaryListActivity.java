package com.geekhive.studentsoft.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.NotificationAdapter;
import com.geekhive.studentsoft.adapter.StationaryAdapter;
import com.geekhive.studentsoft.adapter.StationaryProductAdapter;
import com.geekhive.studentsoft.beans.addtocart.AddToCart;
import com.geekhive.studentsoft.beans.stationarycategory.StationaryCategory;
import com.geekhive.studentsoft.beans.stonaryprouct.StonaryProduct;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StationaryListActivity extends AppCompatActivity  implements View.OnClickListener,OnResponseListner{

    @BindView(R.id.vR_all_list)
    RecyclerView vR_all_list;
    NotificationAdapter notificationAdapter;
    @BindView(R.id.backPress)
    ImageView backPress;
    ConnectionDetector mDetector;
    String main_category;
    StonaryProduct stonaryProduct;
    AddToCart addToCart;
    RecyclerView vrStationary;
    StationaryProductAdapter stationarylistAdapter;
    @BindView(R.id.shopping_cart)
    ImageView shopping_cart;
    String p_id,product_price,total_product_price;
    String s_owner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_stationary_list);
        ButterKnife.bind(this);
        main_category = getIntent().getStringExtra("main_category");
        s_owner = getIntent().getStringExtra("s_ownerlogin");
        mDetector = new ConnectionDetector(this);
        if(!s_owner.equals("") || s_owner.equals("s_owner")){
            shopping_cart.setVisibility(View.GONE);
        }else {
            shopping_cart.setVisibility(View.VISIBLE);
        }
        vrStationary = findViewById(R.id.vR_all_list);
        CallProductlist();
        backPress.setOnClickListener(this);
        shopping_cart.setOnClickListener(this);

    }

    private void CallProductlist() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Callstonaryproduct(WebServices.SM_Services,
                    WebServices.ApiType.staonaryproduct, "",main_category,"", "",Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }

    private void CallAddtocart(String qty,String total_product_price,String id,String product_price ) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Calladdtocart(WebServices.SM_Services,
                    WebServices.ApiType.addtocart, Prefs.getPrefRefId(this),Prefs.getUserType(this),
                    id,"","",qty,product_price,total_product_price,
                    Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }


    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.staonaryproduct) {
            if (!isSucces) {
                SnackBar.makeText(StationaryListActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                stonaryProduct = (StonaryProduct) response;

                if (!isSucces || stonaryProduct.getResult().getMessage() == null) {
                    SnackBar.makeText(StationaryListActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
                    vrStationary.setLayoutManager(linearLayoutManager);
                    stationarylistAdapter = new StationaryProductAdapter(this,stonaryProduct);
                    vrStationary.setAdapter(stationarylistAdapter);
                }
            }
        } if (apiType == WebServices.ApiType.addtocart) {
            if (!isSucces) {
                SnackBar.makeText(StationaryListActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                addToCart = (AddToCart) response;

                if (!isSucces || addToCart.getResult().getMessage() == null) {
                    SnackBar.makeText(StationaryListActivity.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(StationaryListActivity.this, "Item Added into cart", SnackBar.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backPress:
                finish();
                break;
            case R.id.shopping_cart:
                startActivity(new Intent(StationaryListActivity.this, CartlistActivicty.class));
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

    public class StationaryProductAdapter extends RecyclerView.Adapter {

        Context context;
        String url;
        StonaryProduct stonaryProduct;
        public StationaryProductAdapter(Context context,StonaryProduct stonaryProduct) {
            this.context = context;
            this.stonaryProduct = stonaryProduct;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stationary_item_list,parent,false);
            return new StationaryProductAdapter.ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ListViewHolder)holder).bindView(position);
            if(!s_owner.equals("") || s_owner.equals("s_owner")){
                ((ListViewHolder) holder).vL_add_to_cart.setVisibility(View.GONE);
            }else {
                ((ListViewHolder) holder).vL_add_to_cart.setVisibility(View.VISIBLE);
            }
            ((ListViewHolder) holder).vT_name.setText(stonaryProduct.getResult().getMessage().get(position).getName());
            ((ListViewHolder) holder).vT_price.setText("Price :"+stonaryProduct.getResult().getMessage().get(position).getPrice());

            if(stonaryProduct.getResult().getMessage().get(position).getMediaUrls().equals("") ||
                    stonaryProduct.getResult().getMessage().get(position).getMediaUrls().size() != 0) {
                if(stonaryProduct.getResult().getMessage().get(position).getMediaUrls() != null){
                    url ="http://" +stonaryProduct.getResult().getMessage().get(position).getMediaUrls().get(0).toString();

                }
            }
            Picasso.get()
                    .load(url)//download URL
                    .error(R.drawable.book)//if failed
                    .into(((ListViewHolder) holder).vI_image);

            ((ListViewHolder) holder).qtyM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.parseInt( ((ListViewHolder) holder).qty.getText().toString()) != 1) {
                        int qaunt = Integer.parseInt( ((ListViewHolder) holder).qty.getText().toString()) - 1;
                        ((ListViewHolder) holder).qty.setText(String.valueOf(qaunt));
                        // holder.vL_add_to_cart.setVisibility(View.VISIBLE);

                    } else {
                        ((ListViewHolder) holder).vL_add_to_cart.setVisibility(View.VISIBLE);
                        int qaunt = Integer.parseInt( ((ListViewHolder) holder).qty.getText().toString()) - 1;
                        ((ListViewHolder) holder).qty.setText(String.valueOf(qaunt));
                        ((ListViewHolder) holder).vL_count.setVisibility(View.GONE);


                    }
                }
            });

            ((ListViewHolder) holder).vT_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ListViewHolder) holder).vL_count.setVisibility(View.VISIBLE);
                    ((ListViewHolder) holder).vL_add_to_cart.setVisibility(View.GONE);
                   /* int qaunt = Integer.parseInt(((ListViewHolder) holder).qty.getText().toString()) + 1;
                    ((ListViewHolder) holder).qty.setText(String.valueOf(qaunt));
                    if (Integer.parseInt(((ListViewHolder) holder).qty.getText().toString()) != 0) {
                        CallAddtocart(((ListViewHolder) holder).qty.getText().toString(),total_product_price,
                                stonaryProduct.getResult().getMessage().get(position).getId(),
                                String.valueOf(stonaryProduct.getResult().getMessage().get(position).getPrice()));
                    } else {
                        SnackBar.makeText(StationaryListActivity.this, "Invalid quantity", SnackBar.LENGTH_SHORT).show();
                    }
*/
                    ((ListViewHolder) holder).qtyA.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int qaunt = Integer.parseInt(((ListViewHolder) holder).qty.getText().toString()) + 1;
                            ((ListViewHolder) holder).qty.setText(String.valueOf(qaunt));
                            CallAddtocart(((ListViewHolder) holder).qty.getText().toString(),total_product_price,
                                    stonaryProduct.getResult().getMessage().get(position).getId(),
                                    String.valueOf(stonaryProduct.getResult().getMessage().get(position).getPrice()));
                        }
                    });

                }
            });

        }
        @Override
        public int getItemCount() {
            return stonaryProduct.getResult().getMessage().size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            LinearLayout VL_psd_menu,vL_add_to_cart,vL_count;
            ImageView vI_image,qtyA,qtyM;
            TextView vT_name,vT_price,vT_add,qty;
            public ListViewHolder(@NonNull View itemView) {
                super(itemView);
                VL_psd_menu = (LinearLayout) itemView.findViewById(R.id.VL_psd_menu);
                vT_name = (TextView)itemView.findViewById(R.id.vT_name);
                vT_price = (TextView)itemView.findViewById(R.id.vT_price);
                vI_image = (ImageView) itemView.findViewById(R.id.vI_image);
                vL_add_to_cart = (LinearLayout)itemView.findViewById(R.id.vL_add_to_cart);
                vL_count = (LinearLayout)itemView.findViewById(R.id.vL_count);
                vT_add = (TextView)itemView.findViewById(R.id.vT_add);
                qty = (TextView)itemView.findViewById(R.id.qty);
                qtyA = (ImageView) itemView.findViewById(R.id.qtyA);
                qtyM = (ImageView) itemView.findViewById(R.id.qtyM);
            }

            public void bindView(int position){

            }

            @Override
            public void onClick(View view) {
            }
        }
    }
}
