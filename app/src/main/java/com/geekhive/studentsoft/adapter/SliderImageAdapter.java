package com.geekhive.studentsoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.geekhive.studentsoft.R;


public class SliderImageAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    //ImageSlider imageSlider;

    //Constructor Created

    public SliderImageAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.imageSlider = imageSlider;
    }

    @Override
    public int getCount() {

        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View)object);  //object parameter
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        String url = "";
        //LayoutInflater to use image
        View itemView = layoutInflater.inflate(R.layout.home_layout_imageslider,container, false);

        ImageView imageView = (ImageView)itemView.findViewById(R.id.myImageView);
        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.backto));
        //imageView.setImageResource(slide[position]);
        container.addView(itemView,0);
        /*if(imageSlider.getResult().getMessage().get(position).getUrl() != null) {
            url = "http://" + imageSlider.getResult().getMessage().get(position).getUrl();
            Picasso.with(context).load(url).error(R.drawable.grayhatwhite).fit().into(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ProductActivity.class_));
            }
        });*/
        return itemView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //container.removeView((View) object);
        ViewPager viewPager = (ViewPager)container;
        View view = (View)object;
        viewPager.removeView(view);
    }

}
