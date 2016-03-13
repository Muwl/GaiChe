package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2016/3/12.
 */
public class MaintainDetailShopAdapter extends BaseAdapter {

    private Context context;
    private BitmapUtils bitmapUtils;

    public MaintainDetailShopAdapter(Context context) {
        this.context = context;
        bitmapUtils=new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        return 8;
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
            convertView=View.inflate(context, R.layout.martaindetail_shopitem,null);
            holder=new ViewHolder();
            holder.imageView= (ImageView) convertView.findViewById(R.id.martaindetail_shopitem_image);
            holder.name= (TextView) convertView.findViewById(R.id.martaindetail_shopitem_name);
            holder.num= (TextView) convertView.findViewById(R.id.martaindetail_shopitem_num);
            holder.money= (TextView) convertView.findViewById(R.id.martaindetail_shopitem_money);
            holder.m= (TextView) convertView.findViewById(R.id.martaindetail_shopitem_m);
            holder.time= (TextView) convertView.findViewById(R.id.martaindetail_shopitem_time);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        public ImageView imageView;
        public TextView name;
        public TextView num;
        public TextView money;
        public TextView m;
        public TextView time;
    }
}
