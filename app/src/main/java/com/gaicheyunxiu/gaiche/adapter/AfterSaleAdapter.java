package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.RefunddetailActivity;

/**
 * Created by Administrator on 2015/12/30.
 * 退款售后
 */
public class AfterSaleAdapter extends BaseAdapter{

    private Context context;

    public AfterSaleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.aftersale_item,null);
            holder=new ViewHolder();
            holder.orderNo= (TextView) convertView.findViewById(R.id.aftersale_item_no);
            holder.imageView= (ImageView) convertView.findViewById(R.id.aftersale_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.aftersale_item_name);
            holder.num= (TextView) convertView.findViewById(R.id.aftersale_item_num);
            holder.money= (TextView) convertView.findViewById(R.id.aftersale_item_money);
            holder.m= (TextView) convertView.findViewById(R.id.aftersale_item_m);
            holder.state= (TextView) convertView.findViewById(R.id.aftersale_item_state);
            holder.detail= (TextView) convertView.findViewById(R.id.aftersale_item_detail);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, RefunddetailActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder{
        public TextView orderNo;
        public ImageView imageView;
        public TextView name;
        public TextView num;
        public TextView money;
        public TextView m;
        public TextView state;
        public TextView detail;
    }
}
