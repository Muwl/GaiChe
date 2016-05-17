package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.ShopServiceEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class OutletdetailSerAdapter extends BaseAdapter {

    private Context context;
    private List<ShopServiceEntity> entities;
    private Handler handler;

    public OutletdetailSerAdapter(Context context,List<ShopServiceEntity> entities,Handler handler) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.outletdetail_seritem,null);
            holder=new ViewHolder();
            holder.nameView= (TextView) convertView.findViewById(R.id.outletdetail_seritem_name);
            holder.moneyView= (TextView) convertView.findViewById(R.id.outletdetail_seritem_monney);
            holder.increamView= (ImageView) convertView.findViewById(R.id.outletdetail_seritem_incream);
            holder.addView= (ImageView) convertView.findViewById(R.id.outletdetail_seritem_add);
            holder.numView= (TextView) convertView.findViewById(R.id.outletdetail_seritem_num);
            holder.checkBox= (CheckBox) convertView.findViewById(R.id.outletdetail_seritem_cb);
            holder.contentView= (TextView) convertView.findViewById(R.id.outletdetail_seritem_content);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.nameView.setText(entities.get(position).name);
        holder.moneyView.setText("￥:"+entities.get(position).price+"元\u3000"+entities.get(position).mValue+"M");
        holder.numView.setText(entities.get(position).num+"");
        holder.checkBox.setChecked(entities.get(position).flag);
        holder.contentView.setText(entities.get(position).remark);


        holder.increamView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entities.get(position).num > 1) {
                    entities.get(position).num = entities.get(position).num - 1;
                }
                handler.sendEmptyMessage(1001);
                notifyDataSetChanged();
            }
        });

        holder.addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entities.get(position).num=entities.get(position).num+1;
                handler.sendEmptyMessage(1001);
                notifyDataSetChanged();
            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()){
                    entities.get(position).flag=isChecked;
                    handler.sendEmptyMessage(1001);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }
    class ViewHolder{
        public TextView nameView;
        public TextView moneyView;
        public ImageView increamView;
        public ImageView addView;
        public TextView numView;
        public CheckBox checkBox;
        public TextView contentView;
    }
}
