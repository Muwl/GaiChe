package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.CommdityVo;
import com.gaicheyunxiu.gaiche.model.CrowCommityEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/3/14.
 * 众筹适配器
 */
public class CrowdfundAdapter extends BaseAdapter {

    private Context context;
    private BitmapUtils bitmapUtils;
    private List<CommdityVo> entities;
    public CrowdfundAdapter(Context context,List<CommdityVo> entities) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.crowdfund_item,null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.crowdfund_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.crowdfund_item_name);
            holder.newPrice= (TextView) convertView.findViewById(R.id.crowdfund_item_money);
            holder.m= (TextView) convertView.findViewById(R.id.crowdfund_item_m);
            holder.oldPrice= (TextView) convertView.findViewById(R.id.crowdfund_item_oldmoney);
            holder.volume= (TextView) convertView.findViewById(R.id.crowdfund_item_earnings);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        bitmapUtils.display(holder.imageView,entities.get(position).briefImage);
        holder.name.setText(entities.get(position).name);
        holder.newPrice.setText(entities.get(position).presentPrice);
        holder.m.setText(entities.get(position).mValue);
        holder.oldPrice.setText(entities.get(position).originalPrice);
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
