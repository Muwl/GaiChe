package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2016/3/12.
 */
public class MaintainDetailServiceAdapter extends BaseAdapter {

    private Context context;

    public MaintainDetailServiceAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 8;
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
            convertView=View.inflate(context, R.layout.martaindetail_serviceitem,null);
            holder=new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.martaindetail_serviceitem_name);
            holder.no= (TextView) convertView.findViewById(R.id.martaindetail_serviceitem_no);
            holder.money= (TextView) convertView.findViewById(R.id.martaindetail_serviceitem_money);
            holder.cricle= (ImageView) convertView.findViewById(R.id.martaindetail_serviceitem_circle);
            holder.state= (TextView) convertView.findViewById(R.id.martaindetail_serviceitem_state);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        public TextView name;
        public TextView money;
        public ImageView cricle;
        public TextView no;
        public TextView state;
    }
}
