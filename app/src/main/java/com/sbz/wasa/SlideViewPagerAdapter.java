package com.sbz.wasa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideViewPagerAdapter extends PagerAdapter {
    Context ctx;

    public SlideViewPagerAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater =
                (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_screen, container, false);

        ImageView logo = view.findViewById(R.id.onBoardImage);
        ImageView indicatorOne = view.findViewById(R.id.indicatorOne);
        ImageView indicatorTwo = view.findViewById(R.id.indicatorTwo);
        ImageView indicatorThree = view.findViewById(R.id.indicatorThree);
        ImageView indicatorFour = view.findViewById(R.id.indicatorFour);
        ImageView imgOnBoardBack = view.findViewById(R.id.imgOnBoardBack);
        ImageView imgOnBoardNext = view.findViewById(R.id.imgOnBoardNext);

        TextView onBoardTitle = view.findViewById(R.id.onBoardTitle);

        switch (position) {
            case 0:
                logo.setImageResource(R.drawable.pic_one_demo);
                indicatorOne.setImageResource(R.drawable.selected);
                indicatorTwo.setImageResource(R.drawable.unselected);
                indicatorThree.setImageResource(R.drawable.unselected);
                indicatorFour.setImageResource(R.drawable.unselected);
                break;
            case 1:
                logo.setImageResource(R.drawable.pic_two_demo);
                indicatorOne.setImageResource(R.drawable.unselected);
                indicatorTwo.setImageResource(R.drawable.selected);
                indicatorThree.setImageResource(R.drawable.unselected);
                indicatorFour.setImageResource(R.drawable.unselected);
                break;
            case 2:
                logo.setImageResource(R.drawable.pic_three_demo);
                indicatorOne.setImageResource(R.drawable.unselected);
                indicatorTwo.setImageResource(R.drawable.unselected);
                indicatorThree.setImageResource(R.drawable.selected);
                indicatorFour.setImageResource(R.drawable.unselected);
                break;
            case 3:
                logo.setImageResource(R.drawable.pic_one_demo);
                indicatorOne.setImageResource(R.drawable.unselected);
                indicatorTwo.setImageResource(R.drawable.unselected);
                indicatorThree.setImageResource(R.drawable.unselected);
                indicatorFour.setImageResource(R.drawable.selected);
                break;
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
