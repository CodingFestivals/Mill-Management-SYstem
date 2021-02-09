package com.example.millmanagementsystem;

import android.content.Context;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.millmanagementsystem.ImageInformation;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMoneyRecive extends RecyclerView.Adapter<AdapterMoneyRecive.MyViewHolder>{

    Context context;
    ArrayList<ImageInformation> arrayList;

    public AdapterMoneyRecive(Context context, ArrayList<ImageInformation> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public AdapterMoneyRecive.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(R.layout.single_item_allmember,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMoneyRecive.MyViewHolder myViewHolder, int i) {
        //Name Set In Text View
        ImageInformation information=arrayList.get(i);
        myViewHolder.textView.setText(information.getName());
        myViewHolder.textView1.setText(information.getAuthEmail());
        final String reciverName=information.getName();
        final String reciverId=information.getUserId();


        //Image Set In ImageView
        //  Toast.makeText(context,information.getName(),Toast.LENGTH_LONG).show();
        Glide.with(context)
                .load(information.getImage())
                .placeholder(R.drawable.default_image)
                .fitCenter()
                .into(myViewHolder.imageView);

        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Clicked Money Recive",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,ActivityMoneyRecive.class);
                intent.putExtra("reciver_id",reciverId);
                intent.putExtra("reciver_name",reciverName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View singleView;
        TextView textView,textView1;
        CircleImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.singleView=itemView;
            textView1=itemView.findViewById(R.id.id_stextViewEmail);
            textView=itemView.findViewById(R.id.id_stextView);
            imageView=itemView.findViewById(R.id.id_simageView);
        }
    }
}