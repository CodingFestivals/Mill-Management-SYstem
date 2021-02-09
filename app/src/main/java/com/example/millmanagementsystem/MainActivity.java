package com.example.millmanagementsystem;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore mFireStore;
    TextView tv_welcome,tv_login;
    EditText et_email,et_pass;
    Toolbar toolbar;
    ScrollView mScrollview;
    Button btn_login,btn_back;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    String email,pass;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //toolbar=findViewById(R.id.id_bgHeader);
        //getSupportActionBar().setTitle("");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_email=findViewById(R.id.id_et_email_am);
        et_pass=findViewById(R.id.id_et_pass_am);
        mFireStore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        btn_login=findViewById(R.id.id_btn_login_am);
        btn_back=findViewById(R.id.id_btn_back_am);
        tv_login=findViewById(R.id.id_tv_login);
        tv_welcome=findViewById(R.id.id_tv_welcome);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/BLKCHCRY.TTF");
        tv_login.setTypeface(typeface);
        tv_welcome.setTypeface(typeface);
        //AdapterSpinner adapterSpinner=new AdapterSpinner(this);
        //spinner.setAdapter(adapterSpinner);
        progressBar=findViewById(R.id.id_pb_am);
        btn_login.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_btn_login_am:
               // Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
                loginWork();
                break;
            case R.id.id_btn_back_am:
                Intent intent_back=new Intent(MainActivity.this,HomePage.class);
                startActivity(intent_back);
                finish();
                break;
        }
    }
    public void loginWork(){
        progressBar.setVisibility(View.VISIBLE);
        email=et_email.getText().toString();
        pass=et_pass.getText().toString();
        if(email.isEmpty()){
            et_email.setError("Please Enter Your Email");
            et_email.requestFocus();
            return;
        }if(pass.isEmpty()){
            et_pass.setError("Please Enter Your Email");
            et_pass.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Email Or Password Wrong"+task.getException(),Toast.LENGTH_SHORT).show();
                    return;
                }
                String userId=mAuth.getCurrentUser().getUid();
                mFireStore.collection("users").document(userId)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String type=documentSnapshot.getString("type");
                                if(type.equals("Admin")){
                                    Intent intent=new Intent(MainActivity.this,AdminDashboard.class);
                                    startActivity(intent);
                                    finish();
                                }if(type.equals("Manager")){
                                    Intent intent=new Intent(MainActivity.this,ManagerDashboard.class);
                                    startActivity(intent);
                                    finish();
                                }if(type.equals("User")){
                                    Intent intent=new Intent(MainActivity.this,UserDashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
