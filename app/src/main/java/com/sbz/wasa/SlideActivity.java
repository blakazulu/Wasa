package com.sbz.wasa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class SlideActivity extends AppCompatActivity {
    ViewPager viewPager;
    SlideViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        viewPager = findViewById(R.id.viewPager);
        adapter = new SlideViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
    }
}