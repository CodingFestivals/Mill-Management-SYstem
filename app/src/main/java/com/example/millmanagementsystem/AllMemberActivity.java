package com.example.millmanagementsystem;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AllMemberActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    TextView txt_admin,txt_manager,txt_user;
    ViewPager pager;
    ViewPagerAdapter pagerAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_all_member);
        txt_admin=findViewById(R.id.id_txt_allAdmin);
        txt_manager=findViewById(R.id.id_txt_allManager);
        txt_user=findViewById(R.id.id_txt_allUser);
        pager=findViewById(R.id.id_viewPager);
        pager.setOffscreenPageLimit(2);
        pagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        txt_admin.setOnClickListener(this);
        txt_manager.setOnClickListener(this);
        txt_user.setOnClickListener(this);
        pager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_txt_allAdmin:
                pager.setCurrentItem(0);
                break;
            case R.id.id_txt_allManager:
                pager.setCurrentItem(1);
                break;
            case R.id.id_txt_allUser:
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
            txt_admin.setTextColor(getResources().getColor(R.color.textTabBright));
            txt_admin.setTextSize(22);
            txt_manager.setTextColor(getResources().getColor(R.color.textTabLight));
            txt_manager.setTextSize(16);
            txt_user.setTextColor(getResources().getColor(R.color.textTabLight));
            txt_user.setTextSize(16);
        }if(position==1){
            txt_manager.setTextColor(getResources().getColor(R.color.textTabBright));
            txt_manager.setTextSize(22);
            txt_admin.setTextColor(getResources().getColor(R.color.textTabLight));
            txt_admin.setTextSize(16);
            txt_user.setTextColor(getResources().getColor(R.color.textTabLight));
            txt_user.setTextSize(16);
        }if(position==2){
            txt_user.setTextColor(getResources().getColor(R.color.textTabBright));
            txt_user.setTextSize(22);
            txt_manager.setTextColor(getResources().getColor(R.color.textTabLight));
            txt_manager.setTextSize(16);
            txt_admin.setTextColor(getResources().getColor(R.color.textTabLight));
            txt_admin.setTextSize(16);
        }
    }
}
