package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.ShopEvaluationEntity;
import com.gaicheyunxiu.gaiche.model.ShopServiceEntity;
import com.gaicheyunxiu.gaiche.view.RatingBar;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class OutletdetailpinjiaAdapter extends BaseAdapter{

    private Context context;
    private BitmapUtils bitmapUtils;
    private List<ShopEvaluationEntity> entities;
    public OutletdetailpinjiaAdapter(Context context,List<ShopEvaluationEntity> entities) {
        this.context = context;
        bitmapUtils=new BitmapUtils(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context,R.layout.outletdetail_pingjiaitem,null);
            holder.iconView= (ImageView) convertView.findViewById(R.id.outletdetail_pjitem_icon);
            holder.noView= (TextView) convertView.findViewById(R.id.outletdetail_pjitem_no);
            holder.timeView= (TextView) convertView.findViewById(R.id.outletdetail_pjitem_time);
            holder.barView= (RatingBar) convertView.findViewById(R.id.outletdetail_pjitem_bar);
            holder.pingjiaView= (TextView) convertView.findViewById(R.id.outletdetail_pjitem_pingjia);
            holder.contentView= (TextView) convertView.findViewById(R.id.outletdetail_pjitem_content);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        bitmapUtils.display(holder.iconView, entities.get(position).userIcon);
        holder.noView.setText(entities.get(position).name);
        holder.timeView.setText(entities.get(position).createDate);
        holder.barView.setStar(Double.parseDouble(entities.get(position).star));
        holder.pingjiaView.setText("服务："+entities.get(position).serviceScore+"\u3000"+"技术："+entities.get(position).technologyScore+"\u3000"+"环境："+entities.get(position).environmentScore);
        holder.contentView.setText(entities.get(position).content);
        return convertView;
    }

    class ViewHolder{
        public ImageView iconView;
        public TextView noView;
        public TextView timeView;
        public RatingBar barView;
        public TextView pingjiaView;
        public TextView contentView;

    }
}
