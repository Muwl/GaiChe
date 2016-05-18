package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.BankCardEntity;

import java.util.List;

/**
 * Created by Mu on 2016/1/7.
 * 提现页面
 */
public class DepositAdapter extends BaseAdapter {

    private Context context;
    private List<BankCardEntity> entities;

    public DepositAdapter(Context context,List<BankCardEntity> entities) {
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
        holder.brandName.setText(entities.get(position).bank);
        holder.brandStype.setText(entities.get(position).type);
        holder.brandNo.setText(entities.get(position).bankCardNo);

        holder.cb.setChecked(entities.get(position).isCheck);
        return convertView;
    }
    class ViewHolder{
        public TextView brandName;
        public TextView brandStype;
        public TextView brandNo;
        public CheckBox cb;
    }
}
