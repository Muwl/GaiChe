package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.SeriesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/11.
 * 设置车型适配器
 */
public class VolumeAdapter extends BaseAdapter {

    private Context context;
    private List<String> entities;

    public VolumeAdapter(Context context, List<String> entities) {
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
            convertView=View.inflate(context, R.layout.series_item,null);
            holder=new ViewHolder();
            holder.tip= (TextView) convertView.findViewById(R.id.series_item_tip);
            holder.content= (TextView) convertView.findViewById(R.id.series_item_text);
            holder.lin= (ImageView) convertView.findViewById(R.id.series_item_lin);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
            holder.tip.setVisibility(View.GONE);
        holder.content.setText(entities.get(position));
        return convertView;
    }

    class ViewHolder{
        public TextView tip;
        public TextView content;
        public ImageView lin;
    }
}
