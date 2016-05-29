package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.utils.CityEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class CityGridAdapter extends BaseAdapter {

    private Context context;
    private List<CityEntity> entities;

    public CityGridAdapter(Context context, List<CityEntity> entities) {
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
            convertView=View.inflate(context, R.layout.citysel_griditem,null);
            holder.textView= (TextView) convertView.findViewById(R.id.griditem_btn);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(entities.get(position).name);
        return convertView;
    }
    class ViewHolder{
        public TextView textView;
    }
}
