package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.OrderEvaluteAvtivity;

/**
 * Created by Administrator on 2015/12/29.
 * 商品订单子类适配器
 */
public class ShoporderItemAdapter  extends BaseAdapter{

    private Context context;
    public ShoporderItemAdapter(Context context) {
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
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.shoporder_item_item,null);
            holder.icon= (ImageView) convertView.findViewById(R.id.shoporder_item_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.shoporder_item_item_name);
            holder.num= (TextView) convertView.findViewById(R.id.shoporder_item_item_num);
            holder.money= (TextView) convertView.findViewById(R.id.shoporder_item_item_money);
            holder.volume= (TextView) convertView.findViewById(R.id.shoporder_item_item_volume);
            holder.m= (TextView) convertView.findViewById(R.id.shoporder_item_item_m);
            holder.oldmoney= (TextView) convertView.findViewById(R.id.shoporder_item_item_oldmoney);
            holder.div= (ImageView) convertView.findViewById(R.id.shoporder_item_item_div);
            holder.lin=  convertView.findViewById(R.id.shoporder_item_item_lin);
            holder.graybtn= (TextView) convertView.findViewById(R.id.shoporder_item_item_gradbtn);
            holder.orgbtn= (TextView) convertView.findViewById(R.id.shoporder_item_item_orangebtn);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.orgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderEvaluteAvtivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        public ImageView icon;
        public TextView name;
        public TextView num;
        public TextView money;
        public TextView volume;
        public TextView m;
        public TextView oldmoney;
        public ImageView div;
        public View lin;
        public TextView graybtn;
        public TextView orgbtn;
    }
}
