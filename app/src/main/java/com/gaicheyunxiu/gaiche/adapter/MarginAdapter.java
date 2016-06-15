package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.EarningsEntity;

import java.util.List;

/**
 * Created by Mu on 2016/1/6.
 * 商品保证金
 */
public class MarginAdapter extends BaseAdapter{

    private Context context;
    private List<EarningsEntity> entities;

    public MarginAdapter(Context context, List<EarningsEntity> entities) {
        this.context = context;
        this.entities=entities;
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
            convertView=View.inflate(context, R.layout.margin_item,null);
            holder=new ViewHolder();
            holder.gcView= (TextView) convertView.findViewById(R.id.margin_item_gc);
            holder.money= (TextView) convertView.findViewById(R.id.margin_item_money);
            holder.earn= (TextView) convertView.findViewById(R.id.margin_item_earn);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.gcView.setText(entities.get(position).gcCode);
        holder.money.setText(entities.get(position).mvalue);
        holder.earn.setText(entities.get(position).incomeAmount);
        return convertView;
    }
    class ViewHolder{
        public TextView gcView;
        public TextView money;
        public TextView earn;
    }
}
