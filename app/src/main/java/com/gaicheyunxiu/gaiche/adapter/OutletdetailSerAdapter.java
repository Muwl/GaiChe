package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Administrator on 2016/5/16.
 */
public class OutletdetailSerAdapter extends BaseAdapter {

    private Context context;

    public OutletdetailSerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
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
            convertView=View.inflate(context, R.layout.outletdetail_seritem,null);
            holder=new ViewHolder();
            holder.nameView= (TextView) convertView.findViewById(R.id.outletdetail_seritem_name);
            holder.moneyView= (TextView) convertView.findViewById(R.id.outletdetail_seritem_monney);
            holder.increamView= (ImageView) convertView.findViewById(R.id.outletdetail_seritem_incream);
            holder.addView= (ImageView) convertView.findViewById(R.id.outletdetail_seritem_add);
            holder.numView= (EditText) convertView.findViewById(R.id.outletdetail_seritem_num);
            holder.checkBox= (CheckBox) convertView.findViewById(R.id.outletdetail_seritem_cb);
            holder.contentView= (TextView) convertView.findViewById(R.id.outletdetail_seritem_content);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        public TextView nameView;
        public TextView moneyView;
        public ImageView increamView;
        public ImageView addView;
        public EditText numView;
        public CheckBox checkBox;
        public TextView contentView;
    }
}
