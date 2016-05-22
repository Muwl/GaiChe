package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.CornerPathEffect;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.ServiceEvaluteActivity;
import com.gaicheyunxiu.gaiche.model.SerOrderVo;
import com.gaicheyunxiu.gaiche.model.ServiceOrderVo;

import java.util.List;

/**
 * Created by Administrator on 2016/1/1.
 */
public class ServiceOrderItemAdapter extends BaseAdapter {

    private Context context;
    private List<SerOrderVo> entities;
    private int groupoi;
    private Handler handler;
    public ServiceOrderItemAdapter(Context context,List<SerOrderVo> entities,int groupoi,Handler handler) {
        this.context = context;
        this.entities=entities;
        this.groupoi=groupoi;
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
            convertView=View.inflate(context, R.layout.serviceorder_item_item,null);
            holder=new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.serviceorder_item_item_name);
            holder.money= (TextView) convertView.findViewById(R.id.serviceorder_item_item_money);
            holder.cricle= (ImageView) convertView.findViewById(R.id.serviceorder_item_item_circle);
            holder.bm= (TextView) convertView.findViewById(R.id.serviceorder_item_item_bm);
            holder.no= (TextView) convertView.findViewById(R.id.serviceorder_item_item_no);
            holder.state= (TextView) convertView.findViewById(R.id.serviceorder_item_item_state);
            holder.div= (ImageView) convertView.findViewById(R.id.serviceorder_item_item_div);
            holder.lin=convertView.findViewById(R.id.serviceorder_item_item_lin);
            holder.evalute= (TextView) convertView.findViewById(R.id.serviceorder_item_item_evalute);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.name.setText(entities.get(position).name);
        holder.money.setText("￥" + entities.get(position).price + "元\u3000" + entities.get(position).mvalue + "M");
        holder.no.setText(entities.get(position).serviceCode);
        if ("0".equals(entities.get(position).serviceState)){
            holder.state.setText("未生效");
            holder.lin.setVisibility(View.GONE);
            holder.div.setVisibility(View.GONE);
        }else if("1".equals(entities.get(position).serviceState)){
            holder.lin.setVisibility(View.GONE);
            holder.div.setVisibility(View.GONE);
            holder.state.setText("未使用");
        }else if("2".equals(entities.get(position).serviceState)){
            holder.state.setText("已使用");
            if ("0".equals(entities.get(position).isEvaluate)){
                holder.lin.setVisibility(View.VISIBLE);
                holder.div.setVisibility(View.VISIBLE);
            }else{
                holder.lin.setVisibility(View.GONE);
                holder.div.setVisibility(View.GONE);
            }

        }
        holder.evalute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message=new Message();
                message.what=1445;
                message.arg1=groupoi;
                message.arg2=position;
                handler.sendMessage(message);

            }
        });
        return convertView;
    }
    class ViewHolder{
        public TextView name;
        public TextView money;
        public ImageView cricle;
        public TextView bm;
        public TextView no;
        public TextView state;
        public ImageView div;
        public View lin;
        public TextView evalute;


    }
}
