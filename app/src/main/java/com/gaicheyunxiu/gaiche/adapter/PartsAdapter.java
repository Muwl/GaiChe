package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.CommodityEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/3/14.
 * 配件列表适配器
 */
public class PartsAdapter extends BaseAdapter {

    private Context context;
    private BitmapUtils bitmapUtils;
    private List<CommodityEntity> entities;

    public PartsAdapter(Context context,List<CommodityEntity> entities) {
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
            convertView=View.inflate(context, R.layout.part_item,null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.part_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.part_item_name);
            holder.newPrice= (TextView) convertView.findViewById(R.id.part_item_money);
            holder.m= (TextView) convertView.findViewById(R.id.part_item_m);
            holder.oldPrice= (TextView) convertView.findViewById(R.id.part_item_oldmoney);
            holder.volume= (TextView) convertView.findViewById(R.id.part_item_volume);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        bitmapUtils.display(holder.imageView,entities.get(position).briefImage);
        holder.name.setText(entities.get(position).name);
        holder.newPrice.setText("￥"+entities.get(position).presentPrice+"元");
        holder.oldPrice.setText("￥"+entities.get(position).originalPrice+"元");
        holder.m.setText(entities.get(position).mValue+"M");
        holder.volume.setText("月销"+entities.get(position).sales+"笔");
        holder.oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG ); //中间横线
        return convertView;
    }
    class ViewHolder{
        public ImageView imageView;
        public TextView name;
        public TextView newPrice;
        public TextView m;
        public TextView oldPrice;
        public TextView volume;
    }
}
