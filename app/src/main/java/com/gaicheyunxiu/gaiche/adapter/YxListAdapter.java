package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.YxEntity;
import com.gaicheyunxiu.gaiche.view.MyListView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 */
public class YxListAdapter extends BaseAdapter {

    private Context context;
    private List<YxEntity> entities;
    private Handler handler;

    public YxListAdapter(Context context, List<YxEntity> entities,Handler handler) {
        this.context = context;
        this.entities = entities;
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
            convertView=View.inflate(context, R.layout.yxlist_item,null);
            holder=new ViewHolder();
            holder.sername= (TextView) convertView.findViewById(R.id.yxlist_item_name);
            holder.edit= (TextView) convertView.findViewById(R.id.yxlist_item_edit);
            holder.myListView= (MyListView) convertView.findViewById(R.id.yxlist_item_list);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.sername.setText(entities.get(position).name);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 1662;
                message.obj = position;
                handler.sendMessage(message);
            }
        });

        YxListItemAdapter itemAdapter=new YxListItemAdapter(context,entities.get(position).vos);
        holder.myListView.setAdapter(itemAdapter);
        return convertView;
    }
    class ViewHolder{
        public TextView sername;
        public TextView edit;
        public MyListView myListView;
    }
}
