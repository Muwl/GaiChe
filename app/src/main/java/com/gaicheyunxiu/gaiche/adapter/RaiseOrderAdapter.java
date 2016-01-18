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
 * 众筹订单
 */
public class RaiseOrderAdapter extends BaseAdapter{

    private Context context;

    public RaiseOrderAdapter(Context context) {
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
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.raiseorder_item,null);
            holder.no= (TextView) convertView.findViewById(R.id.raiseorder_item_no);
            holder.time= (TextView) convertView.findViewById(R.id.raiseorder_item_time);
            holder.state= (TextView) convertView.findViewById(R.id.raiseorder_item_state);
            holder.imageView= (ImageView) convertView.findViewById(R.id.raiseorder_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.raiseorder_item_name);
            holder.num= (TextView) convertView.findViewById(R.id.raiseorder_item_num);
            holder.money= (TextView) convertView.findViewById(R.id.raiseorder_item_money);
            holder.m= (TextView) convertView.findViewById(R.id.raiseorder_item_m);
            holder.oldmoney= (TextView) convertView.findViewById(R.id.raiseorder_item_oldmoney);
            holder.volume= (TextView) convertView.findViewById(R.id.raiseorder_item_volume);
            holder.graybtn= (TextView) convertView.findViewById(R.id.raiseorder_item_gradbtn);
            holder.organbtn= (TextView) convertView.findViewById(R.id.shoporder_item_orangebtn);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        public TextView no;
        public TextView time;
        public TextView state;
        public ImageView imageView;
        public TextView name;
        public TextView num;
        public  TextView money;
        public TextView m;
        public TextView oldmoney;
        public TextView volume;
        public TextView graybtn;
        public TextView organbtn;
    }
}
