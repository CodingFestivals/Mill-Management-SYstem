package com.example.millmanagementsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterAmountInformation extends RecyclerView.Adapter<AdapterAmountInformation.MyViewHolder> {
    Context context;
    ArrayList<SingleRowDataAmountInformation> arrayList;

    public AdapterAmountInformation(Context context, ArrayList<SingleRowDataAmountInformation> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.single_item_amountinfo,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        SingleRowDataAmountInformation information=arrayList.get(i);
        String date=information.getSendingDate();
        String taka=information.getTaka();
        myViewHolder.textView_date.setText(date);
        myViewHolder.textView_taka.setText(taka);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_date,textView_taka;
        View singleItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            singleItem=itemView;
            textView_date=itemView.findViewById(R.id.id_tv_date_amountInfo);
            textView_taka=itemView.findViewById(R.id.id_tv_tk_amountInfo);
        }
    }
}
