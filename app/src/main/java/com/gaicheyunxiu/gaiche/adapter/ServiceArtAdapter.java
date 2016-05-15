package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.ServiceArtEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/15.
 */
public class ServiceArtAdapter extends BaseAdapter{

    private Context context;
    private List<ServiceArtEntity> entities;
    private Handler handler;

    public ServiceArtAdapter(Context context, List<ServiceArtEntity> entities, Handler handler) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.serviceart_item,null);
            holder.name= (TextView) convertView.findViewById(R.id.serviceart_item_name);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.name.setText(entities.get(position).name);
        return convertView;
    }
    class ViewHolder{
        public TextView name;
    }

}
