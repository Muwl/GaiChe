package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.TrainingRecordEntity;

import java.util.List;

/**
 * Created by Mu on 2016/1/6.
 */
public class WalletdetailAdapter extends BaseAdapter {

    private Context context;
    private List<TrainingRecordEntity> entities;

    public WalletdetailAdapter(Context context,List<TrainingRecordEntity> entities) {
        this.context = context;
        this.entities=entities;
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

        holder.state.setText(entities.get(position).detail);
        holder.time.setText(entities.get(position).createDate);
        holder.money.setText(entities.get(position).amount);
        return convertView;
    }
    class ViewHolder{
        public TextView state;
        public TextView time;
        public TextView money;
    }
}
