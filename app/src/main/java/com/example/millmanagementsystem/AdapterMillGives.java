package com.example.millmanagementsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AdapterMillGives extends RecyclerView.Adapter<AdapterMillGives.MyViewHolder> {
    Context context;
    ArrayList<SingleRowDataMillGive> arrayList;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    public AdapterMillGives(Context context, ArrayList<SingleRowDataMillGive> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public AdapterMillGives.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(R.layout.single_row_mill_give,viewGroup,false);
        return new AdapterMillGives.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterMillGives.MyViewHolder myViewHolder, int i) {
        final SingleRowDataMillGive temp= (SingleRowDataMillGive) arrayList.get(i);
        final AdapterMillGives.MyViewHolder tempHolder=myViewHolder;
        final int position=i;
        final String value=temp.getDate();
        myViewHolder.textView.setText(temp.getDate());
        myViewHolder.checkBox_morning.setOnCheckedChangeListener(null);
        myViewHolder.checkBox_morning.setChecked(temp.isSelectedMorning());
        myViewHolder.checkBox_morning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        myViewHolder.checkBox_launch.setOnCheckedChangeListener(null);
        myViewHolder.checkBox_launch.setChecked(temp.isSelectedLaunch());
        myViewHolder.checkBox_launch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        myViewHolder.checkBox_dinner.setOnCheckedChangeListener(null);
        myViewHolder.checkBox_dinner.setChecked(temp.isSelectedDinner());
        myViewHolder.checkBox_dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        myViewHolder.btn_MillGive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=mAuth.getCurrentUser().getUid();
                Map<String,Object> millData=new HashMap<>();
                millData.put("Morning",tempHolder.checkBox_morning.isChecked());
                millData.put("Lunch",tempHolder.checkBox_launch.isChecked());
                millData.put("Dinner",tempHolder.checkBox_dinner.isChecked());
                FirebaseFirestore mFireStore=FirebaseFirestore.getInstance();
                mFireStore.collection("users").document(user)
                        .collection("Mills")
                        .document(value)
                        .set(millData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Toast.makeText(context,"Insert Success",Toast.LENGTH_LONG).show();
                                //arrayList.remove(position);
                               // notifyDataSetChanged();
                                //tempHolder.singleView.setVisibility(View.GONE);

                                    ActivityMillGIve.btn_millGive.performClick();


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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View singleView;
        TextView textView;
        CheckBox checkBox_morning,checkBox_launch,checkBox_dinner;
        Button btn_MillGive;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            singleView=itemView;
            textView=itemView.findViewById(R.id.id_tv_date_MilGive);
            checkBox_morning=itemView.findViewById(R.id.id_cb_mill_morning);
            checkBox_launch=itemView.findViewById(R.id.id_cb_mill_launch);
            checkBox_dinner=itemView.findViewById(R.id.id_cb_mill_dinner);
            btn_MillGive=itemView.findViewById(R.id.id_btn_MilGive);
        }
    }
}
