package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
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
        ShopOrderEntity shopOrderEntity=entities.get(position);
        holder.no.setText("订单号："+shopOrderEntity.orderId);
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
            bitmapUtils.display(holder.imageView,shopOrderVo.briefImage);
            holder.name.setText(shopOrderVo.name);
            holder.money.setText("￥" + shopOrderVo.presentPrice + "元");
            holder.m.setText(shopOrderVo.mValue + "M");
            holder.oldmoney.setText("￥"+shopOrderVo.originalPrice);
            holder.oldmoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
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
