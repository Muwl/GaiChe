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
 * Created by Administrator on 2016/3/14.
 * 配件列表适配器
 */
public class PartsAdapter extends BaseAdapter {

    private Context context;
    private BitmapUtils bitmapUtils;

    public PartsAdapter(Context context) {
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
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.part_item,null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.part_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.part_item_name);
            holder.newPrice= (TextView) convertView.findViewById(R.id.part_item_money);
            holder.m= (TextView) convertView.findViewById(R.id.part_item_m);
            holder.oldPrice= (TextView) convertView.findViewById(R.id.part_item_oldmoney);
            holder.volume= (TextView) convertView.findViewById(R.id.part_item_volume);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        public ImageView imageView;
        public TextView name;
        public TextView newPrice;
        public TextView m;
        public TextView oldPrice;
        public TextView volume;
    }
}
