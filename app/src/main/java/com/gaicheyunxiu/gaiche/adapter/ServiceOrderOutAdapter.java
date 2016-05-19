package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.ShopServiceEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/5/20.
 */
public class ServiceOrderOutAdapter extends BaseAdapter {

    private Context context;
    private List<ShopServiceEntity> entities;

    public ServiceOrderOutAdapter(Context context, List<ShopServiceEntity> entities) {
        this.context = context;
        this.entities = entities;
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
            convertView=View.inflate(context, R.layout.service_order_item,null);
            holder.name= (TextView) convertView.findViewById(R.id.servicorder_item_name);
            holder.money= (TextView) convertView.findViewById(R.id.servicorder_item_money);
            holder.num= (TextView) convertView.findViewById(R.id.servicorder_item_num);
            holder.content= (TextView) convertView.findViewById(R.id.servicorder_item_content);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.name.setText(entities.get(position).name);
        holder.money.setText("￥:"+entities.get(position).price+"元\u3000"+entities.get(position).mValue+"M");
        holder.num.setText("X"+entities.get(position).num);
        holder.content.setText(entities.get(position).remark);
        return convertView;
    }
    class ViewHolder{
        public TextView name;
        public TextView money;
        public TextView num;
        public TextView content;
    }
}
