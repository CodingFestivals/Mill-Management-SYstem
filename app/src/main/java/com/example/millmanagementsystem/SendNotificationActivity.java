package com.example.millmanagementsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SendNotificationActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_send,btn_back;
    ProgressBar pb;
    TextView textView;
    EditText et_message;
    String reciverName,reciverId;
    FirebaseUser mUser;
    FirebaseFirestore mFireStore;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_send_notification);
        btn_send=findViewById(R.id.id_btn_sendMessage);
        textView=findViewById(R.id.id_tv_sendTo);
        et_message=findViewById(R.id.id_et_message_sendNotification);

        btn_back=findViewById(R.id.id_btn_back_sendNotification);
        pb=findViewById(R.id.id_pb_sendNotification);

        btn_send.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        //Reciver Id
        reciverName=getIntent().getStringExtra("reciver_name");
        reciverId=getIntent().getStringExtra("reciver_id");

        //Sender Id
       // mUser=FirebaseAuth.getInstance().getCurrentUser();

        textView.setText("Send To "+reciverName);
        mFireStore=FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_btn_sendMessage:
                Date date=new Date();
                SimpleDateFormat format=new SimpleDateFormat("EEE dd-MMMM-yyyy HH:mm:ss");
                final String sendingDate=format.format(date);
                final String currentUserId=FirebaseAuth.getInstance().getCurrentUser().getUid();
                pb.setVisibility(View.VISIBLE);
                mFireStore.collection("users").document(currentUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String messages=et_message.getText().toString();
                        Map<String,Object> notificationMessage=new HashMap<>();
                        notificationMessage.put("message",messages);
                        notificationMessage.put("senderId",currentUserId);
                        notificationMessage.put("sendingDate",sendingDate);
                        notificationMessage.put("type",documentSnapshot.getString("type"));
                        mFireStore.collection("users").document(reciverId).collection("Notifications")
                                .add(notificationMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Data Send Successfully",Toast.LENGTH_SHORT).show();
                                pb.setVisibility(View.GONE);
                                et_message.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pb.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Data Can't Send "+e,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Data Can't Retrive",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.id_btn_back_sendNotification:
                Intent intent=new Intent(SendNotificationActivity.this,AllMemberActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
