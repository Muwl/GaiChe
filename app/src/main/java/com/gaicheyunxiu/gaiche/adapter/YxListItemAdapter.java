package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.AfterSaleActivity;
import com.gaicheyunxiu.gaiche.dialog.CustomeConDialog;
import com.gaicheyunxiu.gaiche.model.CommodityEntity;
import com.gaicheyunxiu.gaiche.model.ShopOrderVo;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/12/29.
 * 养修
 */
public class YxListItemAdapter extends BaseAdapter{

    private Context context;
    private List<CommodityEntity> entities;
    private BitmapUtils bitmapUtils;
    public YxListItemAdapter(Context context, List<CommodityEntity> entities) {
        this.context = context;
        this.entities = entities;
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
            convertView=View.inflate(context, R.layout.yxlist_item_item,null);
            holder.icon= (ImageView) convertView.findViewById(R.id.yxlist_item_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.yxlist_item_item_name);
            holder.num= (TextView) convertView.findViewById(R.id.yxlist_item_item_num);
            holder.money= (TextView) convertView.findViewById(R.id.yxlist_item_item_money);
            holder.volume= (TextView) convertView.findViewById(R.id.yxlist_item_item_volume);
            holder.m= (TextView) convertView.findViewById(R.id.yxlist_item_item_m);
            holder.oldmoney= (TextView) convertView.findViewById(R.id.yxlist_item_item_oldmoney);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        bitmapUtils.display(holder.icon, entities.get(position).briefImage);
        holder.name.setText(entities.get(position).name);
        holder.money.setText("￥" + entities.get(position).presentPrice + "元");
        holder.m.setText("￥"+entities.get(position).mValue+"M");
        holder.oldmoney.setText("￥" + entities.get(position).originalPrice);
        holder.num.setText("x" + entities.get(position).num);
        holder.oldmoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
        holder.volume.setText("销量：" + entities.get(position).sales + "件");

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
    }
}
