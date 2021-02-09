package com.example.millmanagementsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class ActivityMillGIve extends AppCompatActivity {

    public static ArrayList<SingleRowDataMillGive> arrayList;
    AdapterMillGives adapter;
   // ListView listView;
    RecyclerView listView;
    FirebaseFirestore mFireStore,mFireStore1;
    FirebaseAuth mAuth;
    public static Button btn_millGive;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mill_give);
        btn_millGive=findViewById(R.id.id_btn_refresh_miilGive);
        mAuth=FirebaseAuth.getInstance();
        mFireStore=FirebaseFirestore.getInstance();
        mFireStore1=FirebaseFirestore.getInstance();
        final FirebaseFirestore mFireStore2=FirebaseFirestore.getInstance();
        arrayList=new ArrayList<>();
        listView=findViewById(R.id.id_lv_millGive);
        listView.setHasFixedSize(true);
        adapter=new AdapterMillGives(this,arrayList);
        //adapter=new AdapterMillGives(this,GlobalVariableDeclare.arrayList);
        //adapter=new AdapterMillGive(this,arrayList,getIntent());
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
        btn_millGive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Insert Success ",Toast.LENGTH_LONG).show();
                Intent intent=getIntent();
                finish();
                startActivity(intent);
            }
        });
    }
    public void saveInformation(final String data,DocumentSnapshot ds){
        //Why Not added Data in Arraylist
        arrayList.add(new SingleRowDataMillGive(data,true,true,true));
       // GlobalVariableDeclare.arrayList.add(new SingleRowDataMillGive(data,true,true,true));
        MillInformation millInformation=ds.toObject(MillInformation.class);
       // Toast.makeText(getApplicationContext()," Good "+data+millInformation.isMorning()+millInformation.isDinner()+millInformation.isLunch(),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_LONG).show();
        arrayList.clear();
        //GlobalVariableDeclare.arrayList.clear();
        final ArrayList<String> millInfo=new ArrayList<String>();
        //final ArrayList<SingleRowDataMillGive> arrayList=new ArrayList<>();
        final ArrayList<Boolean> millInfoChecked=new ArrayList<Boolean>();
        //millInfo.add("Thu 04-July-2019");
        final StringBuffer buffer=new StringBuffer();
        final String userId=mAuth.getCurrentUser().getUid();
        try {
            mFireStore.collection("users").document(userId).collection("Mills")
                    .addSnapshotListener( new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            {
                                for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                                    //documents.add(new String(doc.getDocument().getId()));
                                    String s=doc.getDocument().getId();
                                    millInfo.add(s);
                                }
                                try{
                                    //Toast.makeText(getApplicationContext(),"Size "+documents.size(),Toast.LENGTH_LONG).show();
                                    //Thread.sleep(3000);
                                    Date startDate=getFirstDate();
                                    Date lastDate=getLastDate();
                                    //First Date Of Month(1)
                                    GregorianCalendar cal=new GregorianCalendar();
                                    cal.setTime(startDate);
                                    int date=cal.get(Calendar.DAY_OF_MONTH);
                                    //System.out.println(date);
                                    //Last Date Of Month(31)
                                    GregorianCalendar cal1=new GregorianCalendar();
                                    cal1.setTime(lastDate);
                                    final int date1=cal1.get(Calendar.DAY_OF_MONTH);
                                    //System.out.println(date1);

                                    //String dates[]=new String[date1+1];
                                    Date currentDate=new Date();
                                    SimpleDateFormat formatcurrentDate=new SimpleDateFormat("dd");
                                    int currenDateCheck=Integer.parseInt(formatcurrentDate.format(currentDate));

                                    SimpleDateFormat format=new SimpleDateFormat("EEE dd-MMMM-yyyy");
                                    final ArrayList<SingleRowDataMillGive> list1=new ArrayList<>();
                                    for(int i=date;i<=date1;i++) {
                                        if(i>currenDateCheck){
                                            //dates[i]=format.format(cal.getTime());
                                            final String data=format.format(cal.getTime());
                                            //Check This Date Is Stored In Firebase or not
                                            final boolean[] morning = new boolean[1];
                                            final boolean[] lunch = new boolean[1];
                                            final boolean[] dinner = new boolean[1];
                                            if(millInfo.contains(data)){
                                                mFireStore1.collection("users").document(userId)
                                                        .collection("Mills")
                                                        .document(data)
                                                        .get()
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                //arrayList.add(new SingleRowDataMillGive(data,true,true,true));
                                                                //GlobalVariableDeclare.arrayList.add(new SingleRowDataMillGive(data,true,true,true));
                                                                //saveInformation(data,documentSnapshot);
                                                            }
                                                        });

                                            }else{
                                                //Initially All Checkbox false
                                                arrayList.add(new SingleRowDataMillGive(data,false,false,false));
                                                //GlobalVariableDeclare.arrayList.add(new SingleRowDataMillGive(data,false,false,false));
                                                // arrayList.add(format.format(cal.getTime()));
                                            }
                                            adapter.notifyDataSetChanged();
                                        }
                                        //up=true date increament
                                        boolean up=true;
                                        //for increament date(1,2,3,4,5,.........)
                                        cal.roll(Calendar.DAY_OF_YEAR, up);

                                    }
                                }catch (Exception e1){
                                    Toast.makeText(getApplicationContext(),"BOss "+e1,Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"BOss Error",Toast.LENGTH_SHORT).show();
        }
    }
    public void dataGive(ArrayList<String> s){

    }
    public static Date getFirstDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
    public static Date getLastDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
}
