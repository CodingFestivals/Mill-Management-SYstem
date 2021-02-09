package com.example.millmanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class  ViewHolder{
    TextView textView;
    ImageView imageView;
    public ViewHolder(View view){
        textView=view.findViewById(R.id.id_tv_in);
        imageView=view.findViewById(R.id.id_im_img);
    }
}
class  SingleRowData{
    String userType;
    int userImage;
    public SingleRowData(String ut,int ui){
        this.userType=ut;
        this.userImage=ui;
    }
}
public class AdapterSpinner extends BaseAdapter {
    ArrayList<SingleRowData> arrayList;
    Context context;
    public AdapterSpinner(Context context){
        this.context=context;
        arrayList=new ArrayList<>();
        int img[]={R.drawable.forward,R.drawable.administrator1,R.drawable.moneybag1,R.drawable.user32};
        String imageName[]=context.getResources().getStringArray(R.array.imagename);
        for (int i=0;i<img.length;i++){
            arrayList.add(new SingleRowData(imageName[i],img[i]));
        }
    }
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        ViewHolder holder;
        if(row==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.single_item_spinner,parent,false);
            holder=new ViewHolder(row);
            row.setTag(holder);
        }
        holder= (ViewHolder) row.getTag();
        SingleRowData singleRowData=arrayList.get(position);
        holder.imageView.setImageResource(singleRowData.userImage);
        holder.textView.setText(singleRowData.userType);
        holder.imageView.setTag(singleRowData);
        return row;
    }
}
