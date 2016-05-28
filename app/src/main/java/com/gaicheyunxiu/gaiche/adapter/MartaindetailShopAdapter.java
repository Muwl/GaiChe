package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.MartainShopeEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Mu on 2016/1/18.
 * 保养档案详情商品适配器
 */
public class MartaindetailShopAdapter  extends BaseAdapter{

    private Context context;
    private List<MartainShopeEntity> entities;
    private BitmapUtils bitmapUtils;

    public MartaindetailShopAdapter(Context context,List<MartainShopeEntity> entities) {
        this.context = context;
        this.entities=entities;
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
            convertView=View.inflate(context, R.layout.martaindetail_shopitem,null);
            holder=new ViewHolder();
            holder.imageView= (ImageView) convertView.findViewById(R.id.martaindetail_shopitem_image);
            holder.name= (TextView) convertView.findViewById(R.id.martaindetail_shopitem_name);
            holder.num= (TextView) convertView.findViewById(R.id.martaindetail_shopitem_num);
            holder.money= (TextView) convertView.findViewById(R.id.martaindetail_shopitem_money);
            holder.m= (TextView) convertView.findViewById(R.id.martaindetail_shopitem_m);
            holder.time= (TextView) convertView.findViewById(R.id.martaindetail_shopitem_time);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        bitmapUtils.display(holder.imageView,entities.get(position).briefImage);
        holder.name.setText(entities.get(position).name);
        holder.num.setText("X"+entities.get(position).num);
        holder.money.setText("￥"+entities.get(position).price+"元");
        holder.m.setText(entities.get(position).mvalue+"M");
        holder.time.setText(entities.get(position).buyDate);
        return convertView;
    }

    class ViewHolder{
        public ImageView imageView;
        public TextView name;
        public TextView num;
        public TextView money;
        public TextView m;
        public TextView time;

    }
}
