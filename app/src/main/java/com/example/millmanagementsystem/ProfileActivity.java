package com.example.millmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore mFirestore;
    Button logout;
    CircleImageView imageView;
    TextView txtView;
    String userId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txtView=findViewById(R.id.id_prifile_name);
        imageView=findViewById(R.id.profile_image);
        firebaseAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();

        userId=firebaseAuth.getCurrentUser().getUid();
        txtView.setText("Loading..");
        logout=findViewById(R.id.id_btn_pf_log_out);
        mFirestore.collection("users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name=documentSnapshot.getString("name");
                String imgUri=documentSnapshot.getString("image");
                txtView.setText(name);
                Glide.with(getApplicationContext())
                        .load(imgUri)
                        .placeholder(R.drawable.default_image)
                        .fitCenter()
                        .into(imageView);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,Object> tokenRemove=new HashMap<>();
                //tokenRemove.put("token_id","");
                tokenRemove.put("token_id", FieldValue.delete());
                mFirestore.collection("users").document(userId).update(tokenRemove).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        firebaseAuth.signOut();
                        Intent intent=new Intent(ProfileActivity.this,HomePage.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
