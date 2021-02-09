package com.example.millmanagementsystem;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyViewHolder {
    TextView textView;
    CheckBox checkBox_morning,checkBox_launch,checkBox_dinner;
    Button btn_MillGive;
    public MyViewHolder(View row){
        textView=row.findViewById(R.id.id_tv_date_MilGive);
        checkBox_morning=row.findViewById(R.id.id_cb_mill_morning);
        checkBox_launch=row.findViewById(R.id.id_cb_mill_launch);
        checkBox_dinner=row.findViewById(R.id.id_cb_mill_dinner);
        btn_MillGive=row.findViewById(R.id.id_btn_MilGive);
    }
}
