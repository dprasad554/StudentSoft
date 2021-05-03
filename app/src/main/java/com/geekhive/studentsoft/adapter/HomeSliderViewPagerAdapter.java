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
import com.geekhive.studentsoft.beans.homebanner.Homebanner;
import com.squareup.picasso.Picasso;

public class HomeSliderViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    int slide[];
    Homebanner homebanner;

    //Constructor Created

    public HomeSliderViewPagerAdapter(Context context, Homebanner homebanner) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.slide = slide;
        this.homebanner =  homebanner;
    }

    @Override
    public int getCount() {

        return homebanner.getResult().getMessage().getSlider().size();
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
        //imageView.setImageResource(slide[position]);
        container.addView(itemView,0);
        if(homebanner.getResult().getMessage().getSlider().get(position) != null) {
                url = "http://" + homebanner.getResult().getMessage().getSlider().get(position);
        }
        if (!url.equals("")){
            Picasso.get()
                    .load(url)//download URL
                    .error(R.drawable.backto)//if failed
                    .into(imageView);
        }
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
