package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.LogisticDetailActivity;

/**
 * Created by Mu on 2016/1/18.
 * 物流列表适配器
 */
public class LogisticItemAdapter extends BaseAdapter{

    private Context context;

    public LogisticItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.logistics_item_item,null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.logistics_item_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.logistics_item_item_name);
            holder.num= (TextView) convertView.findViewById(R.id.logistics_item_item_num);
            holder.money= (TextView) convertView.findViewById(R.id.logistics_item_item_money);
            holder.m= (TextView) convertView.findViewById(R.id.logistics_item_item_m);
            holder.oldmoney= (TextView) convertView.findViewById(R.id.logistics_item_item_oldmoney);
            holder.volume= (TextView) convertView.findViewById(R.id.logistics_item_item_volume);
            holder.div= (ImageView) convertView.findViewById(R.id.logistics_item_item_div);
            holder.lin=convertView.findViewById(R.id.logistics_item_item_lin);
            holder.organbtn= (TextView) convertView.findViewById(R.id.logistics_item_item_orangebtn);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        holder.organbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, LogisticDetailActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class  ViewHolder{
        public ImageView imageView;
        public TextView name;
        public TextView num;
        public TextView money;
        public TextView m;
        public TextView oldmoney;
        public TextView volume;
        public ImageView div;
        public View lin;
        public TextView organbtn;
    }
}
