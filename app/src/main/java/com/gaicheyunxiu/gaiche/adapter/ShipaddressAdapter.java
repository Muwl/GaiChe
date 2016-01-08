package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2016/1/8.
 * 收货地址适配器
 */
public class ShipaddressAdapter extends BaseAdapter {

    private Context context;

    public ShipaddressAdapter(Context context) {
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (holder==null){
            convertView=View.inflate(context, R.layout.shipaddress_item,null);
            holder=new ViewHolder();
            holder.imageView= (ImageView) convertView.findViewById(R.id.shipaddress_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.shipaddress_item_name);
            holder.phone= (TextView) convertView.findViewById(R.id.shipaddress_item_phone);
            holder.address= (TextView) convertView.findViewById(R.id.shipaddress_item_address);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
    class ViewHolder{
        public ImageView imageView;
        public TextView name;
        public TextView phone;
        public TextView address;

    }
}
