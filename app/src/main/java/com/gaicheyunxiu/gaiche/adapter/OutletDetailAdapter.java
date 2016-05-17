package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.OuletEvaluaEntity;
import com.gaicheyunxiu.gaiche.view.RatingBar;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class OutletDetailAdapter extends BaseAdapter{
    private Context context;
    private BitmapUtils bitmapUtils;
    private List<OuletEvaluaEntity> entities;

    public OutletDetailAdapter(Context context,List<OuletEvaluaEntity> entities) {
        this.context = context;
        this.entities=entities;
        bitmapUtils=new BitmapUtils(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.outletdetail_hitem,null);
            holder.iconView= (ImageView) convertView.findViewById(R.id.outletdetail_hitem_icon);
            holder.noView= (TextView) convertView.findViewById(R.id.outletdetail_hitem_no);
            holder.barView= (RatingBar) convertView.findViewById(R.id.outletdetail_hitem_bar);
            holder.contentView= (TextView) convertView.findViewById(R.id.outletdetail_hitem_content);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        bitmapUtils.display(holder.iconView,entities.get(position).userIcon );
        holder.noView.setText(entities.get(position).name);
        holder.barView.setStar(Double.parseDouble(entities.get(position).environmentScore));
        holder.contentView.setText(entities.get(position).content);
        return convertView;
    }
    class ViewHolder{
        public ImageView iconView;
        public TextView  noView;
        public RatingBar barView;
        public TextView  contentView;
    }
}
