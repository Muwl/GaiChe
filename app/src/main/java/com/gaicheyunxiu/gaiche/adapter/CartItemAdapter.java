package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

import org.apache.http.params.HttpParams;

/**
 * Created by Mu on 2015/12/23.
 * 购物车之类适配器
 */
public class CartItemAdapter extends BaseAdapter{

    private Context context;

    public CartItemAdapter(Context context) {
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
            convertView=View.inflate(context, R.layout.cart_item_item,null);
            holder=new ViewHolder();
            holder.icon= (ImageView) convertView.findViewById(R.id.cart_item_item_icon);
            holder.name= (TextView) convertView.findViewById(R.id.cart_item_item_name);
            holder.money= (TextView) convertView.findViewById(R.id.cart_item_item_money);
            holder.m= (TextView) convertView.findViewById(R.id.cart_item_item_m);
            holder.incream = (ImageView) convertView.findViewById(R.id.cart_item_item_incream);
            holder.num= (TextView) convertView.findViewById(R.id.cart_item_item_num);
            holder.add= (ImageView) convertView.findViewById(R.id.cart_item_item_add);
            holder.del= (TextView) convertView.findViewById(R.id.cart_item_item_del);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        public ImageView icon;
        public TextView name;
        public TextView  money;
        public TextView m;
        public ImageView incream;
        public TextView num;
        public ImageView add;
        public TextView del;
    }
}
