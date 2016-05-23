package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.AfterSaleActivity;
import com.gaicheyunxiu.gaiche.activity.OrderEvaluteAvtivity;
import com.gaicheyunxiu.gaiche.activity.ReqrefundActivity;
import com.gaicheyunxiu.gaiche.dialog.CustomeConDialog;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.model.ShopOrderVo;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/12/29.
 * 商品订单子类适配器
 */
public class ShoporderItemAdapter  extends BaseAdapter{

    private Context context;
    private List<ShopOrderVo> entities;
    private BitmapUtils bitmapUtils;
    private int groupoi;
    private Handler handler;
    private String orderState;
    public ShoporderItemAdapter(Context context,List<ShopOrderVo> entities,int groupoi,Handler handler,String orderState) {
        this.context = context;
        this.entities = entities;
        this.groupoi=groupoi;
        this.handler=handler;
        this.orderState=orderState;
        bitmapUtils=new BitmapUtils(context);
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

        bitmapUtils.display(holder.icon, entities.get(position).briefImage);
        holder.name.setText("【" + entities.get(position).name + "】" + entities.get(position).businessName);
        holder.money.setText("￥" + entities.get(position).presentPrice + "元");
        holder.m.setText("￥"+entities.get(position).mVaule+"M");
        holder.oldmoney.setText("￥" + entities.get(position).originalPrice);
        holder.m.setText("销量：" + entities.get(position).sales + "件");
//        holder.orgbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, OrderEvaluteAvtivity.class);
//                context.startActivity(intent);
//            }
//        });
        holder.graybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AfterSaleActivity.class);
                context.startActivity(intent);
            }
        });

        if ("0".equals(orderState)){
            holder.div.setVisibility(View.GONE);
            holder.lin.setVisibility(View.GONE);
        }else if("2".equals(orderState)){
            holder.div.setVisibility(View.VISIBLE);
            holder.lin.setVisibility(View.VISIBLE);
            holder.orgbtn.setText("确认收货");
            holder.graybtn.setText("查看物流");

            holder.orgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomeConDialog customeConDialog = new CustomeConDialog(context, handler, "确定收货？", position, groupoi, -1);
                }
            });
            holder.graybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message message=new Message();
                    message.what=1336;
                    message.arg1=position;
                    message.arg2=groupoi;
                    handler.sendMessage(message);
                }
            });


        }else if("4".equals(orderState)){
            holder.div.setVisibility(View.VISIBLE);
            holder.lin.setVisibility(View.VISIBLE);
            holder.orgbtn.setText("申请退货");
            holder.graybtn.setText("评价");

            holder.orgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomeConDialog customeConDialog = new CustomeConDialog(context, handler, "确定申请退货？", position, groupoi, -2);
                }
            });

            holder.graybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message message=new Message();
                    message.what=1337;
                    message.arg1=position;
                    message.arg2=groupoi;
                    handler.sendMessage(message);
                }
            });
        }

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
