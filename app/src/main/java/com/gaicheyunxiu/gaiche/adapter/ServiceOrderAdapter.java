package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.OrderPayActivity;
import com.gaicheyunxiu.gaiche.activity.ServicePayActivity;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.model.SerOrderEntity;
import com.gaicheyunxiu.gaiche.view.MyListView;

import java.util.List;

import static com.gaicheyunxiu.gaiche.R.id.earning_m;
import static com.gaicheyunxiu.gaiche.R.id.serviceorder_item_gps;

/**
 * Created by Administrator on 2016/1/1.
 */
public class ServiceOrderAdapter extends BaseAdapter {

    private Context context;
    private List<SerOrderEntity> entities;
    private Handler handler;

    public ServiceOrderAdapter(Context context,List<SerOrderEntity> entities,Handler handler) {
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
    public View getView(final int position,  View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.serviceorder_item,null);
            holder=new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.serviceorder_item_name);
            holder.gps= (TextView) convertView.findViewById(serviceorder_item_gps);
            holder.listView= (MyListView) convertView.findViewById(R.id.serviceorder_item_list);
            holder.num= (TextView) convertView.findViewById(R.id.serviceorder_item_num);
            holder.div= (ImageView) convertView.findViewById(R.id.serviceorder_item_div);
            holder.div1= (ImageView) convertView.findViewById(R.id.serviceorder_item_div1);
            holder.money= (TextView) convertView.findViewById(R.id.serviceorder_item_money);
            holder.del= (TextView) convertView.findViewById(R.id.serviceorder_item_item_del);
            holder.pay= (TextView) convertView.findViewById(R.id.serviceorder_item_item_pay);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        ServiceOrderItemAdapter adapter=new ServiceOrderItemAdapter(context,entities.get(position).service,position,handler);
        holder.listView.setAdapter(adapter);
        holder.name.setText(entities.get(position).shopName);
        holder.num.setText("共" + entities.get(position).serviceNum + "项服务");
        holder.money.setText("￥" + entities.get(position).totalPrice + "元");

        if ("0".equals(entities.get(position).orderState)){
            holder.div1.setVisibility(View.VISIBLE);
            holder.del.setVisibility(View.VISIBLE);
            holder.del.setText("取消订单");
            holder.pay.setText("\u3000付款\u3000");
            holder.pay.setVisibility(View.VISIBLE);
        }else if("1".equals(entities.get(position).orderState)){
            holder.div1.setVisibility(View.GONE);
            holder.del.setVisibility(View.GONE);
            holder.del.setText("取消订单");
            holder.pay.setText("付款");
            holder.pay.setVisibility(View.GONE);
        }else if("2".equals(entities.get(position).orderState)){
            holder.div1.setVisibility(View.VISIBLE);
            holder.del.setVisibility(View.VISIBLE);
            holder.del.setText("删除订单");
            holder.pay.setText("付款");
            holder.pay.setVisibility(View.GONE);
        }else if("3".equals(entities.get(position).orderState)){
            holder.div1.setVisibility(View.GONE);
            holder.del.setVisibility(View.GONE);
            holder.del.setText("删除订单");
            holder.pay.setText("付款");
            holder.pay.setVisibility(View.GONE);
        }

        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderPayActivity.class);
                intent.putExtra("flag",3);
                intent.putExtra("money",entities.get(position).totalPrice);
                intent.putExtra("orderId",entities.get(position).id);
                context.startActivity(intent);
            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("2".equals(entities.get(position).orderState)){
                    CustomeDialog customeDialog=new CustomeDialog(context,handler,"确定要删除此订单？",position,-1);
                }else{
                    CustomeDialog customeDialog=new CustomeDialog(context,handler,"确定要取消此订单？",position,-2);
                }
            }
        });
        return convertView;
    }
    class ViewHolder{
        public TextView name;
        public TextView gps;
        public MyListView listView;
        public TextView num;
        public ImageView div;
        public ImageView div1;
        public TextView money;
        public TextView del;
        public TextView pay;
    }
}
