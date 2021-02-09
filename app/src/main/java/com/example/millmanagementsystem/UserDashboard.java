package com.example.millmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class UserDashboard extends AppCompatActivity implements View.OnClickListener{

    CardView cv_profile,cv_allmember,cv_millGive,cv_Notification,cv_amount_info;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_userdashboard);
        cv_profile=findViewById(R.id.id_cv_profile_auser);
        cv_allmember=findViewById(R.id.id_cv_allMember_auser);
        cv_millGive=findViewById(R.id.id_cv_millGive_auser);
        cv_Notification=findViewById(R.id.id_cv_notification_userDashboard);
        cv_amount_info=findViewById(R.id.id_cv_amountInfo_aUserDashboard);
        cv_profile.setOnClickListener(this);
        cv_allmember.setOnClickListener(this);
        cv_millGive.setOnClickListener(this);
        cv_Notification.setOnClickListener(this);
        cv_amount_info.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_cv_profile_auser:
                Intent intent=new Intent(UserDashboard.this,ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.id_cv_allMember_auser:
                Intent intent_allMember=new Intent(UserDashboard.this,AllMemberActivity.class);
                startActivity(intent_allMember);
                break;
            case R.id.id_cv_millGive_auser:
                Intent intent_millGive=new Intent(UserDashboard.this,ActivityMillGIve.class);
                startActivity(intent_millGive);
                break;
                case R.id.id_cv_notification_userDashboard:
                Intent intent_notification=new Intent(UserDashboard.this,ActivityAllNotifications.class);
                startActivity(intent_notification);
                break;case R.id.id_cv_amountInfo_aUserDashboard:
                    Intent intent_amount=new Intent(UserDashboard.this,ActivityAmountInformation.class);
                    startActivity(intent_amount);
                    break;
        }
    }
}
