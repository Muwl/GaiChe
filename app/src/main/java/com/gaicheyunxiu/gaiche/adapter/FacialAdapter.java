package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Administrator on 2016/3/27.
 */
public class FacialAdapter extends BaseAdapter {

    private Context context;

    public FacialAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.facial_item,null);
            holder.name= (TextView) convertView.findViewById(R.id.facial_item_name);
            holder.checkBox= (CheckBox) convertView.findViewById(R.id.facial_item_cb);
            holder.content= (TextView) convertView.findViewById(R.id.facial_item_content);
            convertView.setTag(holder);
        }else{
            holder=new ViewHolder();
        }

        return convertView;
    }
    class ViewHolder{
        public TextView name;
        public CheckBox checkBox;
        public TextView content;
    }
}
