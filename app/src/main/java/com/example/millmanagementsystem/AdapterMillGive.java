package com.example.millmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.Nullable;

public class AdapterMillGive extends BaseAdapter {
    Context context;
    Intent intent;
    ArrayList<SingleRowDataMillGive> arrayList;
    FirebaseFirestore mFireStore;
    public AdapterMillGive(Context context, ArrayList<SingleRowDataMillGive> arrayList,Intent intent) {
        this.context=context;
        this.arrayList=arrayList;
        this.intent=intent;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row=convertView;
        MyViewHolder holder;
        if(row==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.single_row_mill_give,parent,false);
            holder=new MyViewHolder(row);
            row.setTag(holder);

        }
        final Button delete_btn=row.findViewById(R.id.id_btn_MilGive);
        delete_btn.setTag(position);
       // final View rowFinal=row;
        holder= (MyViewHolder) row.getTag();
        final MyViewHolder tempHolder=holder;
        final SingleRowDataMillGive temp= (SingleRowDataMillGive) arrayList.get(position);
        holder.textView.setText(temp.getDate());


        /*try {
            mFireStore=FirebaseFirestore.getInstance();
            mFireStore.collection("users").document("khCRNqq659bXB8c9IraQ1yRmuJl1")
                    .collection("Mills")
                    .document(temp.getDate())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            temp.setSelectedMorning(documentSnapshot.getBoolean("Morning"));
                            temp.setSelectedLaunch(documentSnapshot.getBoolean("Lunch"));
                            temp.setSelectedDinner(documentSnapshot.getBoolean("Dinner"));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context,"Exception "+e,Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
        }*/


        //for checkbox morning
        holder.checkBox_morning.setOnCheckedChangeListener(null);
        holder.checkBox_morning.setChecked(temp.isSelectedMorning());
        holder.checkBox_morning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    temp.setSelectedMorning(true);
                }
                else
                {
                    temp.setSelectedMorning(false);
                }
            }
        });

        //for checkbox launch
        holder.checkBox_launch.setOnCheckedChangeListener(null);
        holder.checkBox_launch.setChecked(temp.isSelectedLaunch());
        holder.checkBox_launch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    temp.setSelectedLaunch(true);
                }
                else
                {
                    temp.setSelectedLaunch(false);
                }
            }
        });

        //for checkbox dinner
        holder.checkBox_dinner.setOnCheckedChangeListener(null);
        holder.checkBox_dinner.setChecked(temp.isSelectedDinner());
        holder.checkBox_dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    temp.setSelectedDinner(true);
                }
                else
                {
                    temp.setSelectedDinner(false);
                }
            }
        });
        holder.btn_MillGive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(context,"Error From Adapter "+task.getException(),Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Map<String,Object> millData=new HashMap<>();
                                millData.put("Morning",tempHolder.checkBox_morning.isChecked());
                                millData.put("Lunch",tempHolder.checkBox_launch.isChecked());
                                millData.put("Dinner",tempHolder.checkBox_dinner.isChecked());
                                mFireStore=FirebaseFirestore.getInstance();
                                mFireStore.collection("users").document("khCRNqq659bXB8c9IraQ1yRmuJl1")
                                        .collection("Mills")
                                        .document(arrayList.get(position).getDate())
                                        .set(millData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context,"Insert Success",Toast.LENGTH_SHORT).show();
                                                arrayList.remove(position);
                                                arrayList.clear();
                                                notifyDataSetChanged();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context,"Insert Fail"+e,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
            }
        });
        return row;
    }
}
