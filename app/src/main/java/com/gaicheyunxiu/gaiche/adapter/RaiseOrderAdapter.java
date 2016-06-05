package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.CustomeConDialog;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.model.ShopOrderEntity;
import com.gaicheyunxiu.gaiche.model.ShopOrderVo;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Mu on 2016/1/18.
 * 众筹订单
 */
public class RaiseOrderAdapter extends BaseAdapter{

    private Context context;
    private List<ShopOrderEntity> entities;
    private Handler handler;
    private BitmapUtils bitmapUtils;

    public RaiseOrderAdapter(Context context, List<ShopOrderEntity> entities, Handler handler) {
        this.context = context;
        this.entities = entities;
        this.handler = handler;
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
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
            holder.lin= convertView.findViewById(R.id.raiseorder_item_lin);
            holder.delbtn= (TextView) convertView.findViewById(R.id.raiseorder_item_delbtn);
            holder.graybtn= (TextView) convertView.findViewById(R.id.raiseorder_item_gradbtn);
            holder.organbtn= (TextView) convertView.findViewById(R.id.raiseorder_item_orangebtn);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        ShopOrderEntity shopOrderEntity=entities.get(position);
        holder.no.setText("订单号："+shopOrderEntity.orderNo);
        holder.time.setText(shopOrderEntity.createDate);
        if ("0".equals(shopOrderEntity.orderState)){
            holder.state.setText("未支付");
        }else if("1".equals(shopOrderEntity.orderState)){
            holder.state.setText("已支付");
        }else if("2".equals(shopOrderEntity.orderState)){
            holder.state.setText("已完成");
        }else if("3".equals(shopOrderEntity.orderState)){
            holder.state.setText("已取消");
        }
        if (shopOrderEntity.vos!=null && shopOrderEntity.vos.size()>0){
            ShopOrderVo shopOrderVo=shopOrderEntity.vos.get(0);
            bitmapUtils.display(holder.imageView, shopOrderVo.briefImage);
            holder.name.setText(shopOrderVo.name);
            holder.money.setText("￥" + shopOrderVo.presentPrice + "元");
            holder.m.setText(shopOrderVo.mValue + "M");
            holder.oldmoney.setText("￥" + shopOrderVo.originalPrice);
            holder.oldmoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线

            String orderState=shopOrderEntity.orderState;
            String shopState=shopOrderVo.state;
            if ("0".endsWith(orderState)){
               holder.delbtn.setVisibility(View.GONE);
                holder.graybtn.setVisibility(View.GONE);
                holder.organbtn.setVisibility(View.VISIBLE);
                holder.lin.setVisibility(View.VISIBLE);
                holder.organbtn.setText("\u3000付款\u3000");
                holder.organbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message message = new Message();
                        message.what = 1556;
                        message.arg1 = position;
                        handler.sendMessage(message);
                    }
                });
            }else if ("1".equals(orderState)){
                if ("0".equals(shopState)){
                    holder.lin.setVisibility(View.GONE);
                }else if("1".equals(shopState)){
                    holder.lin.setVisibility(View.VISIBLE);
                    holder.delbtn.setVisibility(View.GONE);
                    holder.graybtn.setVisibility(View.VISIBLE);
                    holder.organbtn.setVisibility(View.VISIBLE);
                    holder.organbtn.setText("确认收货");
                    holder.graybtn.setText("查看物流");

                    holder.organbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomeConDialog customeConDialog = new CustomeConDialog(context, handler, "确定收货？", position, 0, -1);
                        }
                    });

                    holder.graybtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message message = new Message();
                            message.what = 1336;
                            message.arg1 = position;
                            message.arg2 = 0;
                            handler.sendMessage(message);
                        }
                    });
                }
            }

            if ("2".equals(shopState)){
                holder.lin.setVisibility(View.VISIBLE);
                holder.delbtn.setVisibility(View.VISIBLE);
                holder.graybtn.setVisibility(View.VISIBLE);
                holder.organbtn.setVisibility(View.VISIBLE);
                holder.delbtn.setText("删除订单");
                holder.organbtn.setText("评价订单");
                holder.graybtn.setText("申请退货");
                holder.delbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomeDialog customeDialog = new CustomeDialog(context, handler, "确定删除订单？", position, -2);
                    }
                });

                holder.organbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message message = new Message();
                        message.what = 1337;
                        message.arg1 = position;
                        message.arg2 = 0;
                        handler.sendMessage(message);
                    }
                });

                holder.graybtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomeConDialog customeConDialog = new CustomeConDialog(context, handler, "确定申请退货？", position, 0, -2);
                    }
                });
            }else if("3".equals(shopState)){
                holder.lin.setVisibility(View.VISIBLE);
                holder.delbtn.setVisibility(View.VISIBLE);
                holder.graybtn.setVisibility(View.VISIBLE);
                holder.organbtn.setVisibility(View.GONE);
                holder.delbtn.setText("删除订单");
                holder.organbtn.setText("评价订单");
                holder.graybtn.setText("申请退货");

                holder.delbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomeDialog customeDialog = new CustomeDialog(context, handler, "确定删除订单？", position, -2);
                    }
                });

                holder.organbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message message = new Message();
                        message.what = 1337;
                        message.arg1 = position;
                        message.arg2 = 0;
                        handler.sendMessage(message);
                    }
                });

                holder.graybtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomeConDialog customeConDialog = new CustomeConDialog(context, handler, "确定申请退货？", position, 0, -2);
                    }
                });
            }else if("4".equals(shopState)) {
                holder.lin.setVisibility(View.GONE);
            }else if("5".equals(shopState)) {
                holder.lin.setVisibility(View.VISIBLE);
                holder.delbtn.setVisibility(View.VISIBLE);
                holder.graybtn.setVisibility(View.VISIBLE);
                holder.organbtn.setVisibility(View.GONE);
                holder.delbtn.setText("删除订单");
                holder.graybtn.setText("钱款去向");
                holder.delbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomeDialog customeDialog = new CustomeDialog(context, handler, "确定删除订单？", position, -2);
                    }
                });
                holder.graybtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message message = new Message();
                        message.what = 1339;
                        message.arg1 = position;
                        message.arg2 = 0;
                        handler.sendMessage(message);
                    }
                });
            }else if("6".equals(shopState)) {
                holder.lin.setVisibility(View.VISIBLE);
                holder.delbtn.setVisibility(View.VISIBLE);
                holder.graybtn.setVisibility(View.VISIBLE);
                holder.organbtn.setVisibility(View.GONE);
                holder.delbtn.setText("删除订单");
                holder.graybtn.setText("钱款去向");
                holder.delbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomeDialog customeDialog = new CustomeDialog(context, handler, "确定删除订单？", position, -2);
                    }
                });
                holder.graybtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message message = new Message();
                        message.what = 1339;
                        message.arg1 = position;
                        message.arg2 = 0;
                        handler.sendMessage(message);
                    }
                });
            }else if("7".equals(shopState)) {
                holder.lin.setVisibility(View.VISIBLE);
                holder.delbtn.setVisibility(View.GONE);
                holder.graybtn.setVisibility(View.VISIBLE);
                holder.organbtn.setVisibility(View.GONE);
                holder.graybtn.setText("钱款去向");
                holder.graybtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message message = new Message();
                        message.what = 1339;
                        message.arg1 = position;
                        message.arg2 = 0;
                        handler.sendMessage(message);
                    }
                });
            }




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
        public View lin;
        public TextView delbtn;
        public TextView graybtn;
        public TextView organbtn;
    }
}
