package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.OutSelEntity;
import com.gaicheyunxiu.gaiche.view.RatingBar;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Mu on 2015/12/24.
 *
 */
public class OuletSelAdapter extends BaseAdapter {

    private Context context;
    private List<OutSelEntity> entities;
    private int width;
    private BitmapUtils bitmapUtils;

    public OuletSelAdapter(Context context,List<OutSelEntity> entities,int width) {
        this.context = context;
        this.width=width;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.ouletsel_item,null);
            holder=new ViewHolder();
            holder.imageView= (ImageView) convertView.findViewById(R.id.outlet_item_image);
            holder.cb= (CheckBox) convertView.findViewById(R.id.outlet_item_cb);
            holder.name= (TextView) convertView.findViewById(R.id.outlet_item_name);
            holder.bar= (RatingBar) convertView.findViewById(R.id.outlet_item_bar);
            holder.num= (TextView) convertView.findViewById(R.id.outlet_item_discussnum);
            holder.money= (TextView) convertView.findViewById(R.id.outlet_item_money);
            holder.m= (TextView) convertView.findViewById(R.id.outlet_item_m);
            holder.address= (TextView) convertView.findViewById(R.id.outlet_item_address);
            holder.distance= (TextView) convertView.findViewById(R.id.outlet_item_distance);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        bitmapUtils.display(holder.imageView,entities.get(position).icon);
        holder.name.setText(entities.get(position).name);
        holder.bar.setStar(Float.valueOf(entities.get(position).score));
        holder.bar.setLayoutParams(new LinearLayout.LayoutParams((int) (0.30 * width), (int) (0.045 * width)));
        holder.num.setText(entities.get(position).evaluateAmount + "条");
        holder.money.setText(entities.get(position).sumPrice+"元");
        holder.m.setText(entities.get(position).sumMvalue+"M");
        holder.address.setText(entities.get(position).district);
        holder.distance.setText(entities.get(position).distance+"km");
        return convertView;
    }
    class ViewHolder{
        public ImageView imageView;
        public CheckBox cb;
        public TextView name;
        public RatingBar bar;
        public TextView num;
        public TextView money;
        public TextView m;
        public TextView address;
        public TextView distance;
    }
}
