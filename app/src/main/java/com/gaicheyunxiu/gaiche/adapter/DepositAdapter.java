package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2016/1/7.
 * 提现页面
 */
public class DepositAdapter extends BaseAdapter {

    private Context context;

    public DepositAdapter(Context context) {
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.deposit_item,null);
            holder=new ViewHolder();
            holder.brandName= (TextView) convertView.findViewById(R.id.deposit_item_brandname);
            holder.brandStype= (TextView) convertView.findViewById(R.id.deposit_item_brandstype);
            holder.brandNo= (TextView) convertView.findViewById(R.id.deposit_item_brandno);
            holder.cb= (CheckBox) convertView.findViewById(R.id.deposit_item_cb);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        public TextView brandName;
        public TextView brandStype;
        public TextView brandNo;
        public CheckBox cb;
    }
}
