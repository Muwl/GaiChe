package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.ButyServiceEntity;
import com.gaicheyunxiu.gaiche.model.SupportEntity;
import com.gaicheyunxiu.gaiche.utils.LogManager;

import java.util.List;

/**
 * Created by Administrator on 2016/3/27.
 */
public class FacialAdapter extends BaseAdapter {

    private Context context;
    private List<ButyServiceEntity> entities;
    public FacialAdapter(Context context,List<ButyServiceEntity> entities) {
        this.context = context;
        this.entities = entities;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.facial_item,null);
            holder.name= (TextView) convertView.findViewById(R.id.facial_item_name);
            holder.checkBox= (CheckBox) convertView.findViewById(R.id.facial_item_cb);
            holder.content= (TextView) convertView.findViewById(R.id.facial_item_content);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.name.setText((position+1)+"."+entities.get(position).name);
        holder.checkBox.setChecked(entities.get(position).isSelect);
        holder.content.setText(entities.get(position).remarks);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                entities.get(position).isSelect=isChecked;
            }
        });

        return convertView;
    }
    class ViewHolder{
        public TextView name;
        public CheckBox checkBox;
        public TextView content;
    }
}
