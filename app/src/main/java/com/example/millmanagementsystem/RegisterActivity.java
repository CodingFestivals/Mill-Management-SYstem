package com.example.millmanagementsystem;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner spinner;
    TextView tv_email_ad,tv_pass_ad,tv_mess_id,tv_name;
    EditText et_email,et_pass,et_email_ad,et_pass_ad,et_mess_id,et_name;
    Button btn_back,btn_create_account;
    String email,ad_email,pass,ad_pass,messId,selectedItem,name;

    int selectedPosition=0;
    int x=0;
    CircleImageView imgButton;
    private  static final int PICK_IMAGE=1;
    Uri imageUri;
    ProgressBar progressBar;
    FirebaseAuth mAuth,mAuth1;
    StorageReference mStorageRef;
    FirebaseFirestore mFirestore,mFirestore1;
    boolean findMessId=false,findAdmin=false,findMessIdAdmin=false,findAdminAdmin=false,clicked=false,findMessIdManager=false,findAdminManager=false,findMessIdUser=false,findAdminUser=false;
    StringBuffer buffer=new StringBuffer();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT<16){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        progressBar=findViewById(R.id.id_pb_ar);
        imageUri=null;
        spinner=findViewById(R.id.id_spinner);
        AdapterSpinner adapterSpinner=new AdapterSpinner(this);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(this);

        mStorageRef = FirebaseStorage.getInstance().getReference().child("images");
        mAuth=FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore1 = FirebaseFirestore.getInstance();

        et_name=findViewById(R.id.id_et_name_ar);

        et_email=findViewById(R.id.id_et_email_ar);
        et_email_ad=findViewById(R.id.id_et_email_ad_ar);

        et_pass=findViewById(R.id.id_et_pass_ar);
        et_pass_ad=findViewById(R.id.id_et_password_ad_ar);

        tv_email_ad=findViewById(R.id.id_tv_email_ad);
        tv_pass_ad=findViewById(R.id.id_tv_pass_ad);


        tv_mess_id=findViewById(R.id.id_tv_mess_id_ar);
        et_mess_id=findViewById(R.id.id_et_mess_id_ar);

        btn_back=findViewById(R.id.id_btn_back_ar);
        btn_create_account=findViewById(R.id.id_btn_create_account_ar);

        btn_create_account.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        imgButton=findViewById(R.id.id_iv_reg);
        imgButton.setOnClickListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ViewHolder viewHolder= (ViewHolder) view.getTag();
        SingleRowData singleRowData= (SingleRowData) viewHolder.imageView.getTag();
        selectedItem=singleRowData.userType;
        selectedPosition=position;
        if(position==0){
            tv_email_ad.setVisibility(View.GONE);
            et_email_ad.setVisibility(View.GONE);
            tv_pass_ad.setVisibility(View.GONE);
            et_pass_ad.setVisibility(View.GONE);
        }if(position==1){
            messIdCheck();
            checkUser();

            /*tv_email_ad.setVisibility(View.VISIBLE);
            et_email_ad.setVisibility(View.VISIBLE);
            tv_pass_ad.setVisibility(View.VISIBLE);
            et_pass_ad.setVisibility(View.VISIBLE);*/
        }if(position==2){
            tv_email_ad.setVisibility(View.VISIBLE);
            et_email_ad.setVisibility(View.VISIBLE);
            tv_pass_ad.setVisibility(View.VISIBLE);
            et_pass_ad.setVisibility(View.VISIBLE);
        }if(position==3){
            tv_email_ad.setVisibility(View.VISIBLE);
            et_email_ad.setVisibility(View.VISIBLE);
            tv_pass_ad.setVisibility(View.VISIBLE);
            et_pass_ad.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_iv_reg:
                imageChooser();
                break;
            case R.id.id_btn_create_account_ar:
                clicked=true;
                createAccount();
                break;
            case R.id.id_btn_back_ar:
                Intent intent=new Intent(RegisterActivity.this,HomePage.class);
                startActivity(intent);
                finish();
                break;
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE){
            imageUri=data.getData();
            imgButton.setImageURI(imageUri);
        }
    }
    public void createAccount(){
        name=et_name.getText().toString();
        email=et_email.getText().toString();
        ad_email=et_email_ad.getText().toString();
        pass=et_pass.getText().toString();
        ad_pass=et_pass_ad.getText().toString();
        messId=et_mess_id.getText().toString();
        if(name.isEmpty()){
            et_name.setError("Please Enter Your Email");
            et_name.requestFocus();
            return;
        }if(email.isEmpty()){
            et_email.setError("Please Enter Your Email");
            et_email.requestFocus();
            return;
        }if(pass.isEmpty()){
            et_pass.setError("Please Enter Your Password");
            et_pass.requestFocus();
            return;
        }if(messId.isEmpty()){
            et_mess_id.setError("Please Enter Your Password");
            et_mess_id.requestFocus();
            return;
        }if(selectedPosition==0){
            Toast.makeText(this,"Please Select User",Toast.LENGTH_SHORT).show();
            return;
        }if(et_email_ad.isShown()&& ad_email.isEmpty()&& (selectedPosition==1||selectedPosition==2||selectedPosition==3)){
            et_email_ad.setError("Please Enter Your Admin Email");
            et_email_ad.requestFocus();
            return;
        }
        if(et_pass_ad.isShown()&& ad_pass.isEmpty()&& (selectedPosition==1||selectedPosition==2||selectedPosition==3)){
            et_email_ad.setError("Please Enter Your Admin Password");
            et_email_ad.requestFocus();
            return;
        }
        if(imageUri==null){
            Toast.makeText(getApplicationContext(),"Please Select Image",Toast.LENGTH_SHORT).show();
            return;
        }
        if(selectedPosition==1){
            createAccountConfirmAdmin();
        }if(selectedPosition==2){
            createAccountConfirmManager();
        }if(selectedPosition==3){
            createAccountConfirmUser();
        }
    }
    public void createAccountConfirmUser(){
        progressBar.setVisibility(View.VISIBLE);
        mFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Error "+e,Toast.LENGTH_SHORT).show();
                    return;
                }
                for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                    UserCheckInformation user=doc.getDocument().toObject(UserCheckInformation.class);
                    String messIds=user.getMessid();
                    String authEmails=user.getAuthEmail();
                    String authPasss=user.getAuthPass();
                    if(messIds.equals(messId)){
                        findMessIdUser=true;
                    }else{findMessIdUser=false;}
                    if(messIds.equals(messId)&&authEmails.equals(ad_email)&&authPasss.equals(ad_pass)&&findMessIdUser){
                        Toast.makeText(getApplicationContext(),"Data "+messIds+authEmails+authPasss,Toast.LENGTH_LONG).show();
                        findAdminUser=true;
                        uploadUserInformation();
                        break;
                    }
                }
            }
        });
        try{
            Thread.sleep(1000);
            if(findAdminUser==false){
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Not Found Admin or MessId",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){

        }
        findAdminUser=false;
        findMessIdUser=false;
    }
    public void createAccountConfirmManager(){
        progressBar.setVisibility(View.VISIBLE);
        mFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Error "+e,Toast.LENGTH_SHORT).show();
                    return;
                }
                for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                    UserCheckInformation user=doc.getDocument().toObject(UserCheckInformation.class);
                    String messIds=user.getMessid();
                    String authEmails=user.getAuthEmail();
                    String authPasss=user.getAuthPass();
                    if(messIds.equals(messId)){
                        findMessIdManager=true;
                    }else{findMessIdManager=false;}
                    if(messIds.equals(messId)&&authEmails.equals(ad_email)&&authPasss.equals(ad_pass)&&findMessIdManager){
                        //Toast.makeText(getApplicationContext(),"Data "+messIds+authEmails+authPasss,Toast.LENGTH_LONG).show();
                        findAdminManager=true;
                        uploadManagerInformation();
                        break;
                    }
                }
            }
        });
        try{
            Thread.sleep(1000);
            if(findAdminManager==false){
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Not Found Admin or MessId",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){

        }
        findAdminManager=false;
        findMessIdManager=false;
    }
    public void createAccountConfirmAdmin(){
        progressBar.setVisibility(View.VISIBLE);
        if(ad_email.length()>0&&ad_pass.length()>0){
            mFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                    if(e!=null){
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Error "+e,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                        UserCheckInformation user=doc.getDocument().toObject(UserCheckInformation.class);
                        String messIds=user.getMessid();
                        String authEmails=user.getAuthEmail();
                        String authPasss=user.getAuthPass();
                        if(messIds.equals(messId)){
                            findMessIdAdmin=true;
                        }else{findMessIdAdmin=false;}
                        if(messIds.equals(messId)&&authEmails.equals(ad_email)&&authPasss.equals(ad_pass)&&findMessIdAdmin){
                            findAdminAdmin=true;
                            uploadAdminInformation();
                            Toast.makeText(getApplicationContext(),"Data "+messIds+authEmails+authPasss,Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }
            });
        }
        if(et_email_ad.isShown()==false&&et_pass_ad.isShown()==false){
            findAdminAdmin=true;
            uploadAdminInformation();
        }
        if(findAdminAdmin==false){
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Admin Or Mess Id Not Found",Toast.LENGTH_LONG).show();
        }
        findAdminAdmin=false;
        findMessIdAdmin=false;
    }
    public void sendToAdminDashboard(){
        Intent intentAdmin=new Intent(RegisterActivity.this,AdminDashboard.class);
        startActivity(intentAdmin);
        finish();
    }
    public void sendToManagerDashboard(){
       // Toast.makeText(getApplicationContext(),"Successfully Login ",Toast.LENGTH_SHORT).show();
        Intent intentManager=new Intent(RegisterActivity.this,ManagerDashboard.class);
        startActivity(intentManager);
        finish();
    }
    public void sendToUserDashboard(){
        Intent intentUser=new Intent(RegisterActivity.this,UserDashboard.class);
        startActivity(intentUser);
        finish();
    }
    //get Extension of image
    public String getFileExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public void checkUser(){
        mFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    progressBar.setVisibility(View.INVISIBLE);
                    //Toast.makeText(getApplicationContext(),"Error From checkUser Method"+e,Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    /*For Select All,same of above
                               StringBuffer buffer=new StringBuffer();
                               for(DocumentSnapshot doc:queryDocumentSnapshots){
                                   UserCheckInformation user = doc.toObject(UserCheckInformation.class);

                                   //Toast.makeText(getApplicationContext(),user.getMessid(),Toast.LENGTH_SHORT).show();
                                   //buffer.append(user.getMessid());
                               }
                               //et_email_ad.setText(buffer.toString());*/

                    for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                        //Toast.makeText(getApplicationContext(),"Doument Changes ",Toast.LENGTH_SHORT).show();
                        if(doc.getType()==DocumentChange.Type.ADDED){
                            UserCheckInformation user=doc.getDocument().toObject(UserCheckInformation.class);
                            if(user.getMessid().equals(messId)){
                                findMessId=true;
                                break;
                            }
                        }
                    }
                    if(findMessId){
                        //Toast.makeText(getApplicationContext(),"Find Mess Id",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                        builder.setTitle(R.string.title);
                        builder.setIcon(R.drawable.question);
                        builder.setMessage(R.string.message)
                                .setCancelable(false)
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(),"This Mess  Already Created",Toast.LENGTH_LONG).show();
                                        spinner.setSelection(0);
                                        dialog.dismiss();
                                        tv_email_ad.setVisibility(View.GONE);
                                        et_email_ad.setVisibility(View.GONE);
                                        tv_pass_ad.setVisibility(View.GONE);
                                        et_pass_ad.setVisibility(View.GONE);
                                    }
                                })
                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        tv_email_ad.setVisibility(View.VISIBLE);
                                        et_email_ad.setVisibility(View.VISIBLE);
                                        tv_pass_ad.setVisibility(View.VISIBLE);
                                        et_pass_ad.setVisibility(View.VISIBLE);
                                    }
                                });
                        AlertDialog dialog=builder.create();
                        if(!clicked)
                            dialog.show();
                    }else{
                       // Toast.makeText(getApplicationContext(),"Not Find Mess Id",Toast.LENGTH_SHORT).show();
                        tv_email_ad.setVisibility(View.GONE);
                        et_email_ad.setVisibility(View.GONE);
                        tv_pass_ad.setVisibility(View.GONE);
                        et_pass_ad.setVisibility(View.GONE);
                    }
                }
            }
        });
        findMessId=false;
    }
    public void messIdCheck(){
        messId=et_mess_id.getText().toString();
        if(messId.isEmpty()){
            et_mess_id.setError("Please Enter Your MessId");
            et_mess_id.requestFocus();
            return;
        }
    }
    public void imageChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Your Image"),PICK_IMAGE);
    }
    public void uploadUserInformation(){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(getApplicationContext(),"User Create Successfully",Toast.LENGTH_LONG).show();
                            final String user_id = mAuth.getCurrentUser().getUid();
                            final StorageReference storageReference=mStorageRef.child(user_id+"."+getFileExtension(imageUri));
                            storageReference.putFile(imageUri).continueWithTask(new Continuation<TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"Error From Storage Reference",Toast.LENGTH_SHORT).show();
                                        throw task.getException();
                                    }

                                    // Continue with the task to get the download URL
                                    return storageReference.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        // Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();
                                        Uri downloadUri = task.getResult();
                                        final String downlodUriString=downloadUri.toString();
                                        // Create a new user with a first and last name

                                        FirebaseInstanceId.getInstance().getInstanceId()
                                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                        if (!task.isSuccessful()) {
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            Toast.makeText(getApplicationContext(),"Error From FirebaseInstance",Toast.LENGTH_SHORT).show();
                                                            //Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            return;
                                                        }
                                                        String tokenId=task.getResult().getToken();
                                                        Map<String, Object> userMap = new HashMap<>();
                                                        userMap.put("name",name);
                                                        userMap.put("authEmail",email);
                                                        userMap.put("authPass",pass);
                                                        userMap.put("messid",messId);
                                                        if(ad_email.isEmpty())ad_email="NONE";
                                                        if(ad_pass.isEmpty())ad_pass="NONE";
                                                        userMap.put("adminEmail",ad_email);
                                                        userMap.put("adminPass",ad_pass);
                                                        userMap.put("type",selectedItem);
                                                        userMap.put("image", downlodUriString);
                                                        userMap.put("token_id",tokenId);

                                                        mFirestore.collection("users")
                                                                .document(user_id)
                                                                .set(userMap)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        //Toast.makeText(getApplicationContext(),"3",Toast.LENGTH_LONG).show();
                                                                        //progressBar.setVisibility(View.INVISIBLE);
                                                                        sendToUserDashboard();
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                                Toast.makeText(getApplicationContext(),"Error From put file",Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                });
                                    } else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"Error In Storage File "+task.getException().toString(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error : " +"Can't Store User "+task.getException(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
    public void uploadManagerInformation(){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(getApplicationContext(),"User Create Successfully",Toast.LENGTH_LONG).show();
                            final String user_id = mAuth.getCurrentUser().getUid();
                            final StorageReference storageReference=mStorageRef.child(user_id+"."+getFileExtension(imageUri));
                            storageReference.putFile(imageUri).continueWithTask(new Continuation<TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"Error From Storage Reference",Toast.LENGTH_SHORT).show();
                                        throw task.getException();
                                    }

                                    // Continue with the task to get the download URL
                                    return storageReference.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                       // Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();
                                        Uri downloadUri = task.getResult();
                                        final String downlodUriString=downloadUri.toString();
                                        // Create a new user with a first and last name

                                        FirebaseInstanceId.getInstance().getInstanceId()
                                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                        if (!task.isSuccessful()) {
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            Toast.makeText(getApplicationContext(),"Error From FirebaseInstance",Toast.LENGTH_SHORT).show();
                                                            //Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            return;
                                                        }
                                                        String tokenId=task.getResult().getToken();
                                                        Map<String, Object> userMap = new HashMap<>();
                                                        userMap.put("name",name);
                                                        userMap.put("authEmail",email);
                                                        userMap.put("authPass",pass);
                                                        userMap.put("messid",messId);
                                                        if(ad_email.isEmpty())ad_email="NONE";
                                                        if(ad_pass.isEmpty())ad_pass="NONE";
                                                        userMap.put("adminEmail",ad_email);
                                                        userMap.put("adminPass",ad_pass);
                                                        userMap.put("type",selectedItem);
                                                        userMap.put("image", downlodUriString);
                                                        userMap.put("token_id",tokenId);

                                                        mFirestore.collection("users")
                                                                .document(user_id)
                                                                .set(userMap)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        //Toast.makeText(getApplicationContext(),"3",Toast.LENGTH_LONG).show();
                                                                        //progressBar.setVisibility(View.INVISIBLE);
                                                                        sendToManagerDashboard();
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                                Toast.makeText(getApplicationContext(),"Error From put file",Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                });
                                    } else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"Error In Storage File "+task.getException().toString(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error : " +"Can't Store User "+task.getException(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
    public void uploadAdminInformation(){
        progressBar.setVisibility(View.VISIBLE);
        //Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(getApplicationContext(),"User Create Successfully",Toast.LENGTH_LONG).show();
                            final String user_id = mAuth.getCurrentUser().getUid();
                            final StorageReference storageReference=mStorageRef.child(user_id+"."+getFileExtension(imageUri));
                            storageReference.putFile(imageUri).continueWithTask(new Continuation<TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"Error From Storage Reference",Toast.LENGTH_SHORT).show();
                                        throw task.getException();
                                    }

                                    // Continue with the task to get the download URL
                                    return storageReference.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        //Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();
                                        Uri downloadUri = task.getResult();
                                        final String downlodUriString=downloadUri.toString();
                                        // Create a new user with a first and last name

                                        FirebaseInstanceId.getInstance().getInstanceId()
                                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                        if (!task.isSuccessful()) {
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            Toast.makeText(getApplicationContext(),"Error From FirebaseInstance",Toast.LENGTH_SHORT).show();
                                                            //Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            return;
                                                        }
                                                        String tokenId=task.getResult().getToken();
                                                        Map<String, Object> userMap = new HashMap<>();
                                                        userMap.put("name",name);
                                                        userMap.put("authEmail",email);
                                                        userMap.put("authPass",pass);
                                                        userMap.put("messid",messId);
                                                        if(ad_email.isEmpty())ad_email="NONE";
                                                        if(ad_pass.isEmpty())ad_pass="NONE";
                                                        userMap.put("adminEmail",ad_email);
                                                        userMap.put("adminPass",ad_pass);
                                                        userMap.put("type",selectedItem);
                                                        userMap.put("image", downlodUriString);
                                                        userMap.put("token_id",tokenId);

                                                        mFirestore.collection("users")
                                                                .document(user_id)
                                                                .set(userMap)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        //Toast.makeText(getApplicationContext(),"3",Toast.LENGTH_LONG).show();
                                                                        //progressBar.setVisibility(View.INVISIBLE);
                                                                        if(selectedPosition==1){
                                                                            sendToAdminDashboard();
                                                                        }
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                                Toast.makeText(getApplicationContext(),"Error From put file",Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                });
                                    } else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"Error In Storage File "+task.getException().toString(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error : " +"Can't Store User "+task.getException(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
}
