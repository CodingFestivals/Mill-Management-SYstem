package com.example.millmanagementsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAllNotification extends RecyclerView.Adapter<AdapterAllNotification.MyViewHolder> {
    ArrayList<MessageInformation> arrayList;
    Context context;

    public AdapterAllNotification(ArrayList<MessageInformation> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public AdapterAllNotification.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.single_item_allnotification,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterAllNotification.MyViewHolder myViewHolder, int i) {
        final MessageInformation information=arrayList.get(i);
        final String messages=information.getMessage();
        String senderId=information.getSenderId();
        final String sendDate=information.getSendingDate();

        FirebaseFirestore mFireStore=FirebaseFirestore.getInstance();
        mFireStore.collection("users").document(senderId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name=documentSnapshot.getString("name");
                        String image=documentSnapshot.getString("image");
                        myViewHolder.textViewSender.setText(name);
                        myViewHolder.textViewDate.setText(sendDate);
                        myViewHolder.textViewMessage.setText(messages);
                        Glide.with(context)
                                .load(image)
                                .placeholder(R.drawable.default_image)
                                .fitCenter()
                                .into(myViewHolder.imageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Fail "+e,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSender,textViewMessage,textViewDate;
        CircleImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.id_img_notification);
            textViewSender=itemView.findViewById(R.id.id_tv_sendername_notification);
            textViewMessage=itemView.findViewById(R.id.id_tv_message_notification);
            textViewDate=itemView.findViewById(R.id.id_tv_sendingdate_notification);
        }
    }
}
