package com.example.millmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class ManagerDashboard extends AppCompatActivity implements View.OnClickListener{

    CardView cv_profile,cv_allmember,cv_millGive,cv_notification,cv_moneyRecive;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_managerdashboard);
        cv_profile=findViewById(R.id.id_cv_profile_amanager);
        cv_allmember=findViewById(R.id.id_cv_allMember_amanager);
        cv_millGive=findViewById(R.id.id_cv_millGive_aManager);
        cv_notification=findViewById(R.id.id_cv_notification_managerDashboard);
        cv_moneyRecive=findViewById(R.id.id_cv_moneyRecive_aManger);
        cv_profile.setOnClickListener(this);
        cv_allmember.setOnClickListener(this);
        cv_millGive.setOnClickListener(this);
        cv_notification.setOnClickListener(this);
        cv_moneyRecive.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_cv_profile_amanager:
                Intent intent=new Intent(ManagerDashboard.this,ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.id_cv_allMember_amanager:
                Intent intent_allMember=new Intent(ManagerDashboard.this,AllMemberActivity.class);
                startActivity(intent_allMember);
                break;
            case R.id.id_cv_millGive_aManager:
                Intent intent_millGice=new Intent(ManagerDashboard.this,ActivityMillGIve.class);
                startActivity(intent_millGice);
                break;
            case R.id.id_cv_notification_managerDashboard:
                Intent intent_notification=new Intent(ManagerDashboard.this,ActivityAllNotifications.class);
                startActivity(intent_notification);
                break;
            case R.id.id_cv_moneyRecive_aManger:
                Intent intent_moneyRecive=new Intent(ManagerDashboard.this,ActivityMoneyReciveAllMember.class);
                startActivity(intent_moneyRecive);
                break;
        }
    }
}
