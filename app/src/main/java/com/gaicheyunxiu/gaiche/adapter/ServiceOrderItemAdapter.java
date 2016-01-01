package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.graphics.CornerPathEffect;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Administrator on 2016/1/1.
 */
public class ServiceOrderItemAdapter extends BaseAdapter {

    private Context context;
    public ServiceOrderItemAdapter(Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.serviceorder_item_item,null);
            holder=new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.serviceorder_item_item_name);
            holder.money= (TextView) convertView.findViewById(R.id.serviceorder_item_item_money);
            holder.cricle= (ImageView) convertView.findViewById(R.id.serviceorder_item_item_circle);
            holder.bm= (TextView) convertView.findViewById(R.id.serviceorder_item_item_bm);
            holder.no= (TextView) convertView.findViewById(R.id.serviceorder_item_item_no);
            holder.state= (TextView) convertView.findViewById(R.id.serviceorder_item_item_state);
            holder.div= (ImageView) convertView.findViewById(R.id.serviceorder_item_item_div);
            holder.lin=convertView.findViewById(R.id.serviceorder_item_item_lin);
            holder.evalute= (TextView) convertView.findViewById(R.id.serviceorder_item_item_evalute);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        public TextView name;
        public TextView money;
        public ImageView cricle;
        public TextView bm;
        public TextView no;
        public TextView state;
        public ImageView div;
        public View lin;
        public TextView evalute;


    }
}
