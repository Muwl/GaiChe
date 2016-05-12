package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

import java.util.List;

/**
 * Created by Administrator on 2016/4/4.
 */
public class SerchAdapter extends BaseAdapter {

    private Context context;
    private List<String> strings;

    public SerchAdapter(Context context,List<String> strings) {
        this.context = context;
        this.strings=strings;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.serch_item,null);
            holder=new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.serch_item_name);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.name.setText(strings.get(position));
        return convertView;
    }
    class ViewHolder{
        public TextView name;
    }
}
