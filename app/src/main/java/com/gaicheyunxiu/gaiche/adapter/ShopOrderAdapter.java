package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.model.ShopOrderEntity;
import com.gaicheyunxiu.gaiche.model.ShopOrderVo;
import com.gaicheyunxiu.gaiche.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/29.
 * 商品订单适配器
 */
public class ShopOrderAdapter extends BaseAdapter{

    private Context context;
    private List<ShopOrderEntity> entities;
    private Handler handler;

    public ShopOrderAdapter(Context context,List<ShopOrderEntity> entities,Handler handler) {
        this.context = context;
        this.entities=entities;
        this.handler=handler;
    }
    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public Object getItem(int position) {
        return entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        holder.orderno.setText("订单号："+entities.get(position).orderId);
        holder.money.setText("￥"+entities.get(position).price+"元");
        holder.time.setText(entities.get(position).createDate);

        if ("0".equals(entities.get(position).orderState)){
            holder.state.setText("代付款");
            holder.money.setVisibility(View.VISIBLE);
            holder.div.setVisibility(View.VISIBLE);
            holder.graybtn.setVisibility(View.VISIBLE);
            holder.orgbtn.setVisibility(View.VISIBLE);
            holder.graybtn.setText("取消订单");
            holder.orgbtn.setText("\u3000付款\u3000");
            holder.graybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomeDialog customeDialog = new CustomeDialog(context, handler, "确定取消订单？", position, -1);
                }
            });

            holder.orgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message message=new Message();
                    message.what=1556;
                    message.arg1=position;
                    handler.sendMessage(message);
                }
            });

        }else if("1".equals(entities.get(position).orderState)){
            holder.state.setText("待收货");
            holder.money.setVisibility(View.GONE);
            holder.div.setVisibility(View.GONE);
            holder.graybtn.setVisibility(View.GONE);
            holder.orgbtn.setVisibility(View.GONE);
        }else if("2".equals(entities.get(position).orderState)){
            holder.state.setText("交易成功");
            holder.money.setVisibility(View.GONE);
            holder.div.setVisibility(View.GONE);
            holder.graybtn.setVisibility(View.VISIBLE);
            holder.orgbtn.setVisibility(View.GONE);
            holder.graybtn.setText("删除订单");

            holder.graybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomeDialog customeDialog = new CustomeDialog(context, handler, "确定删除订单？", position, -2);
                }
            });
        }else if("3".equals(entities.get(position).orderState)){
            holder.state.setText("已取消");
            holder.money.setVisibility(View.GONE);
            holder.div.setVisibility(View.GONE);
            holder.graybtn.setVisibility(View.GONE);
            holder.orgbtn.setVisibility(View.GONE);
        }
        List<ShopOrderVo> shopOrderVos=null;
        if ("0".equals(entities.get(position).split)){
            shopOrderVos=entities.get(position).orderListVos;
        }else{
            shopOrderVos=entities.get(position).vos;
        }
        ShoporderItemAdapter adapter=new ShoporderItemAdapter(context,shopOrderVos,position,handler,entities.get(position).orderState);
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
