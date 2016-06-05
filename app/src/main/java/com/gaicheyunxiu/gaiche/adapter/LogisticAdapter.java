package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.ShopOrderEntity;
import com.gaicheyunxiu.gaiche.model.ShopOrderVo;

import java.util.List;

/**
 * Created by Mu on 2016/1/18.
 * 物流列表适配器
 */
public class LogisticAdapter  extends BaseAdapter{

    private Context context;
    private List<ShopOrderEntity> entities;
    private Handler handler;

    public LogisticAdapter(Context context, List<ShopOrderEntity> entities, Handler handler) {
        this.context = context;
        this.entities = entities;
        this.handler = handler;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.logistics_item,null);
            holder=new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.logistic_item_no);
            holder.listView= (ListView) convertView.findViewById(R.id.logistic_item_listview);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.name.setText("订单号："+entities.get(position).orderNo);
        List<ShopOrderVo> shopOrderVos=null;
        if ("0".equals(entities.get(position).split)){
            shopOrderVos=entities.get(position).orderListVos;
        }else{
            shopOrderVos=entities.get(position).vos;
        }
        LogisticItemAdapter adapter=new LogisticItemAdapter(context,shopOrderVos,position,handler);
        holder.listView.setAdapter(adapter);
        return convertView;
    }

    class ViewHolder{
        public TextView name;
        public ListView listView;
    }
}
