package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.view.RatingBar;
import com.gaicheyunxiu.gaiche.view.RoundAngleImageView;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2016/3/20.
 */
public class ShopDetailAdapter extends BaseAdapter {

    private Context context;
    private BitmapUtils bitmapUtils;

    public ShopDetailAdapter(Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context,R.layout.shopdetail_item,null);
            holder.imageView= (RoundAngleImageView) convertView.findViewById(R.id.shopdetail_item_icon);
            holder.name= (TextView) convertView.findViewById(R.id.shopdetail_item_name);
            holder.bar= (RatingBar) convertView.findViewById(R.id.shopdetail_item_bar);
            holder.textView= (TextView) convertView.findViewById(R.id.shopdetail_item_content);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        public RoundAngleImageView imageView;
        public TextView name;
        public RatingBar bar;
        public TextView textView;
    }
}
