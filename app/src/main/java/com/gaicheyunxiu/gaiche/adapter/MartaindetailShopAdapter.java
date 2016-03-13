package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2016/1/18.
 * 保养档案详情商品适配器
 */
public class MartaindetailShopAdapter  extends BaseAdapter{

    private Context context;

    public MartaindetailShopAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
