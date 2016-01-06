package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2016/1/6.
 */
public class WalletdetailAdapter extends BaseAdapter {

    private Context context;

    public WalletdetailAdapter(Context context) {
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
        if (convertView==null){
            convertView=View.inflate(context, R.layout.walletdetail_item,null);
            holder=new ViewHolder();
            holder.state= (TextView) convertView.findViewById(R.id.walletdetail_state);
            holder.time= (TextView) convertView.findViewById(R.id.walletdetail_time);
            holder.money= (TextView) convertView.findViewById(R.id.walletdetail_money);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        public TextView state;
        public TextView time;
        public TextView money;
    }
}
