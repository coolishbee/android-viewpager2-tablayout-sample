package com.viewpager.tablayout.sample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPagerDialog {

    public static class Builder {
        private Context context;

        private ViewPager2 sliderViewPager;
        private TabLayout  tabLayout;

        private String[] images = new String[] {
                "https://user-images.githubusercontent.com/20632507/108068287-3c637d80-70a5-11eb-94f6-4aa605df1f76.jpeg",
                "https://user-images.githubusercontent.com/20632507/108068287-3c637d80-70a5-11eb-94f6-4aa605df1f76.jpeg",
                "https://user-images.githubusercontent.com/20632507/108068287-3c637d80-70a5-11eb-94f6-4aa605df1f76.jpeg"
        };

        private ViewPagerDialogListener okListener;
        private ViewPagerDialogListener todayCloseListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder OnPositiveClicked(ViewPagerDialogListener okListener) {
            this.okListener = okListener;
            return this;
        }

        public Builder OnTodayCloseClicked(ViewPagerDialogListener todayCloseListener) {
            this.todayCloseListener = todayCloseListener;
            return this;
        }

        public void build() {
            final AlertDialog dialog = new AlertDialog.Builder(context).create();

            dialog.setCanceledOnTouchOutside(false);

            View v = LayoutInflater.from(context).inflate(R.layout.viewpager_dialog, null);
            dialog.setView(v);

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

            sliderViewPager = v.findViewById(R.id.sliderViewPager);
            tabLayout = v.findViewById(R.id.mainDots);
            Button btnClose = v.findViewById(R.id.button_close);
            CheckBox checkBoxToday = v.findViewById(R.id.checkbox_today);

            sliderViewPager.setOffscreenPageLimit(1);
            sliderViewPager.setAdapter(new ImageSliderAdapter(context, images));

            sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                }
            });

            new TabLayoutMediator(tabLayout, sliderViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                }
            }).attach();

            if (okListener != null) {
                btnClose.setVisibility(View.VISIBLE);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        okListener.OnClick(false);
                        dialog.dismiss();
                    }
                });

            } else {
                btnClose.setVisibility(View.GONE);
            }

            if(todayCloseListener != null) {
                checkBoxToday.setVisibility(View.VISIBLE);
                checkBoxToday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean test = ((CheckBox)view).isChecked();
                        todayCloseListener.OnClick(test);
                    }
                });
            }else{
                checkBoxToday.setVisibility(View.GONE);
            }

            dialog.show();
        }
    }
}
