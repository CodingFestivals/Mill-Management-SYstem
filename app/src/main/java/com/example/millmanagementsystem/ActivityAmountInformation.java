package com.example.millmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Nullable;

import static java.lang.Double.*;

public class ActivityAmountInformation extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView txt_totalTk;
    ArrayList<String> allDate;
    ArrayList<SingleRowDataAmountInformation> arrayList;
    FirebaseFirestore mFireStore;
    FirebaseAuth mAuth;
    TextView textView;
    AdapterAmountInformation adapter;
    double totalTk=0.0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_amount_information);

        allDate=new ArrayList<>();
        arrayList=new ArrayList<>();

        mAuth=FirebaseAuth.getInstance();
        mFireStore=FirebaseFirestore.getInstance();

        recyclerView=findViewById(R.id.id_rv_aAmountInformation);
        recyclerView.setHasFixedSize(true);
        adapter=new AdapterAmountInformation(this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        textView=findViewById(R.id.tv_totalTk_aAmountInfo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        arrayList.clear();
        Date startDate=getFirstDate();
        Date lastDate=getLastDate();
        //First Date Of Month(1)
        GregorianCalendar cal=new GregorianCalendar();
        cal.setTime(startDate);
        int startdate=cal.get(Calendar.DAY_OF_MONTH);
        //System.out.println(date);
        //Last Date Of Month(31)
        GregorianCalendar cal1=new GregorianCalendar();
        cal1.setTime(lastDate);
         int lastdate=cal1.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat format=new SimpleDateFormat("EEE dd-MMMM-yyyy");
        for(int i=startdate;i<=lastdate;i++){
            String findDate=format.format(cal.getTime());
            allDate.add(findDate);
            //up=true date increament
            boolean up=true;
            //for increament date(1,2,3,4,5,.........)
            cal.roll(Calendar.DAY_OF_YEAR, up);
        }
        try{

            String currentUserId=mAuth.getCurrentUser().getUid();
            mFireStore.collection("users").document(currentUserId).collection("MoneyRecive")
                    .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                           for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                               if(doc.getType()==DocumentChange.Type.ADDED){
                                   double total=0;
                                   SingleRowDataAmountInformation info= doc.getDocument().toObject(SingleRowDataAmountInformation.class);
                                   String moneyReciveDate=info.getSendingDate();
                                   String taka=info.getTaka();
                                   if(allDate.contains(moneyReciveDate)){
                                       arrayList.add(info);
                                       total=total+Double.parseDouble(taka);
                                       GlobalVariableDeclare.totalTk=GlobalVariableDeclare.totalTk+Double.parseDouble(taka);
                                       //totalTk=totalTk+ Double.parseDouble(taka);
                                       //textView.setText(textView.getText()+" "+taka);
                                      // Toast.makeText(getApplicationContext()," "+Double.parseDouble(taka)+totalTk,Toast.LENGTH_LONG).show();
                                       adapter.notifyDataSetChanged();
                                   }

                               }
                           }
                        }
                    });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error "+e,Toast.LENGTH_LONG).show();
        }
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
