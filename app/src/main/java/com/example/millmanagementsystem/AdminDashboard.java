package com.example.millmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class AdminDashboard extends AppCompatActivity implements View.OnClickListener {

    CardView cv_profile,cv_allmember,cv_notifications,cv_millGive,cv_amountInfo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admindashboard);
        cv_profile=findViewById(R.id.id_cv_profile_aadmin);
        cv_allmember=findViewById(R.id.id_cv_allMember_aadmin);
        cv_notifications=findViewById(R.id.id_cv_notification_adminDashboard);
        cv_millGive=findViewById(R.id.id_cv_millGive_aAdmin);
        cv_amountInfo=findViewById(R.id.id_cv_amountInfo_aAdminDashboard);
        cv_profile.setOnClickListener(this);
        cv_allmember.setOnClickListener(this);
        cv_notifications.setOnClickListener(this);
        cv_millGive.setOnClickListener(this);
        cv_amountInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_cv_profile_aadmin:
                Intent intent=new Intent(AdminDashboard.this,ProfileActivity.class);
                startActivity(intent);
                break;
                case R.id.id_cv_allMember_aadmin:
                Intent intent_allMember=new Intent(AdminDashboard.this,AllMemberActivity.class);
                startActivity(intent_allMember);
                break;
                case R.id.id_cv_notification_adminDashboard:
                 Intent intent_notifications=new Intent(AdminDashboard.this,ActivityAllNotifications.class);
                 startActivity(intent_notifications);
                break;
                case R.id.id_cv_millGive_aAdmin:
                 Intent intent_admin=new Intent(AdminDashboard.this,ActivityMillGIve.class);
                 startActivity(intent_admin);
                break;
            case R.id.id_cv_amountInfo_aAdminDashboard:
                Intent intent_amountInfo=new Intent(AdminDashboard.this,ActivityAmountInformation.class);
                startActivity(intent_amountInfo);
        }
    }
}
