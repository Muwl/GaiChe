package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2016/1/6.
 * 商品保证金
 */
public class MarginAdapter extends BaseAdapter{

    private Context context;

    public MarginAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 36;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.margin_item,null);
            holder=new ViewHolder();
            holder.textView= (TextView) convertView.findViewById(R.id.margin_item_text);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        public TextView textView;
    }
}
