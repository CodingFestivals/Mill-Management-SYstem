package com.example.millmanagementsystem;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;



public class HomePage extends AppCompatActivity implements View.OnClickListener {

    CardView cardView_createaccount,cardView_login,cardView_about,cardView_howto;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT<16){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        getSupportActionBar().hide();
        setContentView(R.layout.activity_homepage);
        cardView_createaccount=findViewById(R.id.id_btn_create_account_ahp);
        cardView_login=findViewById(R.id.id_btn_login_ahp);
        cardView_about=findViewById(R.id.id_btn_about_us_ahp);
        cardView_howto=findViewById(R.id.id_btn_howto_ahp);
        cardView_createaccount.setOnClickListener(this);
        cardView_login.setOnClickListener(this);
        cardView_howto.setOnClickListener(this);
        cardView_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_btn_create_account_ahp:
                Intent intent_Register=new Intent(HomePage.this,RegisterActivity.class);
                startActivity(intent_Register);
                break;
            case R.id.id_btn_login_ahp:
                Intent intent_Login=new Intent(HomePage.this,MainActivity.class);
                startActivity(intent_Login);
                break;
            case R.id.id_btn_howto_ahp:
                break;
            case R.id.id_btn_about_us_ahp:
                break;
        }
    }
}
