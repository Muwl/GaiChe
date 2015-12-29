package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.view.MyListView;

/**
 * Created by Administrator on 2015/12/29.
 * 商品订单适配器
 */
public class ShopOrderAdapter extends BaseAdapter{

    private Context context;

    public ShopOrderAdapter(Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.shoporder_item,null);
            holder.orderno= (TextView) convertView.findViewById(R.id.shoporder_item_no);
            holder.time= (TextView) convertView.findViewById(R.id.shoporder_item_time);
            holder.state= (TextView) convertView.findViewById(R.id.shoporder_item_state);
            holder.listView= (MyListView) convertView.findViewById(R.id.shoporder_item_listview);
            holder.money= (TextView) convertView.findViewById(R.id.shoporder_item_money);
            holder.graybtn= (TextView) convertView.findViewById(R.id.shoporder_item_gradbtn);
            holder.orgbtn= (TextView) convertView.findViewById(R.id.shoporder_item_orangebtn);
            holder.lin=  convertView.findViewById(R.id.shoporder_item_lin);
            holder.div= (ImageView) convertView.findViewById(R.id.shoporder_item_div);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        ShoporderItemAdapter adapter=new ShoporderItemAdapter(context);
        holder.listView.setAdapter(adapter);
        return convertView;
    }
    class ViewHolder{
        public TextView orderno;
        public TextView time;
        public TextView state;
        public MyListView listView;
        public TextView money;
        public TextView graybtn;
        public TextView orgbtn;
        public View lin;
        public ImageView div;
    }
}
