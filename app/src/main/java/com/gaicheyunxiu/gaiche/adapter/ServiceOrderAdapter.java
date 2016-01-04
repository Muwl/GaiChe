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
import com.gaicheyunxiu.gaiche.activity.ServicePayActivity;
import com.gaicheyunxiu.gaiche.view.MyListView;

import static com.gaicheyunxiu.gaiche.R.id.serviceorder_item_gps;

/**
 * Created by Administrator on 2016/1/1.
 */
public class ServiceOrderAdapter extends BaseAdapter {

    private Context context;

    public ServiceOrderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
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
            convertView=View.inflate(context, R.layout.serviceorder_item,null);
            holder=new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.serviceorder_item_name);
            holder.gps= (TextView) convertView.findViewById(serviceorder_item_gps);
            holder.listView= (MyListView) convertView.findViewById(R.id.serviceorder_item_list);
            holder.num= (TextView) convertView.findViewById(R.id.serviceorder_item_num);
            holder.div= (ImageView) convertView.findViewById(R.id.serviceorder_item_div);
            holder.div1= (ImageView) convertView.findViewById(R.id.serviceorder_item_div1);
            holder.money= (TextView) convertView.findViewById(R.id.serviceorder_item_money);
            holder.del= (TextView) convertView.findViewById(R.id.serviceorder_item_item_del);
            holder.pay= (TextView) convertView.findViewById(R.id.serviceorder_item_item_pay);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        ServiceOrderItemAdapter adapter=new ServiceOrderItemAdapter(context);
        holder.listView.setAdapter(adapter);

        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ServicePayActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        public TextView name;
        public TextView gps;
        public MyListView listView;
        public TextView num;
        public ImageView div;
        public ImageView div1;
        public TextView money;
        public TextView del;
        public TextView pay;
    }
}