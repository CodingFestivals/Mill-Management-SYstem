package com.example.millmanagementsystem;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityAllNotifications extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    TextView txt_admin_notification,txt_manager_notification,txt_user_notification;
    ViewPager pager;
    ViewPagerAdapterAllNotification pagerAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_all_notifications);
        txt_admin_notification=findViewById(R.id.id_txt_allAdmin_AllNotification);
        txt_manager_notification=findViewById(R.id.id_txt_allManager_AllNotification);
        txt_user_notification=findViewById(R.id.id_txt_allUser_AllNotification);
        pager=findViewById(R.id.id_viewPager_AllNotification);
        pager.setOffscreenPageLimit(2);
        pagerAdapter=new ViewPagerAdapterAllNotification(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        txt_admin_notification.setOnClickListener(this);
        txt_manager_notification.setOnClickListener(this);
        txt_user_notification.setOnClickListener(this);
        pager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_txt_allAdmin_AllNotification:
                pager.setCurrentItem(0);
                break;
            case R.id.id_txt_allManager_AllNotification:
                pager.setCurrentItem(1);
                break;
            case R.id.id_txt_allUser_AllNotification:
                pager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        chageTabs(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
    public void chageTabs(int position){
        if(position==0) {
            txt_admin_notification.setTextColor(getResources().getColor(R.color.textTabBright));
            txt_admin_notification.setTextSize(22);
            txt_manager_notification.setTextColor(getResources().getColor(R.color.textTabLight));
            txt_manager_notification.setTextSize(16);
            txt_user_notification.setTextColor(getResources().getColor(R.color.textTabLight));
            txt_user_notification.setTextSize(16);
        }if(position==1){
            txt_manager_notification.setTextColor(getResources().getColor(R.color.textTabBright));
            txt_manager_notification.setTextSize(22);
            txt_admin_notification.setTextColor(getResources().getColor(R.color.textTabLight));
            txt_admin_notification.setTextSize(16);
            txt_user_notification.setTextColor(getResources().getColor(R.color.textTabLight));
            txt_user_notification.setTextSize(16);
        }if(position==2){
            txt_user_notification.setTextColor(getResources().getColor(R.color.textTabBright));
            txt_user_notification.setTextSize(22);
            txt_manager_notification.setTextColor(getResources().getColor(R.color.textTabLight));
            txt_manager_notification.setTextSize(16);
            txt_admin_notification.setTextColor(getResources().getColor(R.color.textTabLight));
            txt_admin_notification.setTextSize(16);
        }
    }
}
