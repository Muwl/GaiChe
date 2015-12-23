package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.view.MyListView;

/**
 * Created by Mu on 2015/12/23.
 *
 */
public class CartAdapter extends BaseAdapter{

    private Context context;

    public CartAdapter(Context context) {
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
            convertView=View.inflate(context, R.layout.activity_cart_item,null);
            holder.brand= (TextView) convertView.findViewById(R.id.cart_item_brand);
            holder.clear= (TextView) convertView.findViewById(R.id.cart_item_clear);
            holder.listView= (MyListView) convertView.findViewById(R.id.cart_item_list);
            holder.outlet=convertView.findViewById(R.id.cart_item_outletselect);
            holder.num= (TextView) convertView.findViewById(R.id.cart_item_num);
            holder.money= (TextView) convertView.findViewById(R.id.cart_item_money);
            holder.m= (TextView) convertView.findViewById(R.id.cart_item_m);
            holder.ok= (TextView) convertView.findViewById(R.id.cart_item_ok);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        CartItemAdapter adapter=new CartItemAdapter(context);
        holder.listView.setAdapter(adapter);
        return convertView;
    }
    class ViewHolder{
        public TextView brand;
        public TextView clear;
        public MyListView listView;
        public View outlet;
        public TextView num;
        public TextView money;
        public TextView m;
        public TextView ok;
    }
}
