package com.aquila.customWidget;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aquila.custom.widget.view.VerticalViewPager;

/**
 * Created by yulong_wang on 2017/11/7 11:06.
 */

public class SecondActivity extends Activity {

    VerticalViewPager verticalViewPager ;
    ViewPager viewPager ;
    SecondPagerAdapter secondPagerAdapter;

    SecondPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_layout);
        verticalViewPager = findViewById(R.id.second_vertical_ViewPager);
        viewPager = findViewById(R.id.second_normal_ViewPager);

        secondPagerAdapter = new SecondPagerAdapter();
        verticalViewPager.setAdapter(secondPagerAdapter);

        pagerAdapter = new SecondPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(2);

    }


    int color[] ={
            Color.GREEN,
            Color.RED,
            Color.BLACK,
            Color.BLUE,
            Color.GRAY,
            Color.LTGRAY,
    };

    class SecondPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_main, null);
            container.addView(linearLayout);
            linearLayout.setBackgroundColor(color[position % color.length]);
            return linearLayout;

        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);

        }
    }

}
