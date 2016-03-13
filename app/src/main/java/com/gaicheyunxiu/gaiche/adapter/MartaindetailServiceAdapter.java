package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2016/1/18.
 * 保养档案详情服务适配器
 */
public class MartaindetailServiceAdapter extends BaseAdapter {

    private Context context;

    public MartaindetailServiceAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.martaindetail_serviceitem,null);
            holder.name= (TextView) convertView.findViewById(R.id.martaindetail_serviceitem_name);
            holder.money= (TextView) convertView.findViewById(R.id.martaindetail_serviceitem_money);
            holder.bm= (TextView) convertView.findViewById(R.id.martaindetail_serviceitem_bm);
            holder.no= (TextView) convertView.findViewById(R.id.martaindetail_serviceitem_no);
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
        public TextView bm;
        public TextView no;
        public TextView state;

    }
}
