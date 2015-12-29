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
import com.gaicheyunxiu.gaiche.view.RatingBar;

/**
 * Created by Mu on 2015/12/24.
 *
 */
public class OuletSelAdapter extends BaseAdapter {

    private Context context;

    private int width;

    public OuletSelAdapter(Context context,int width) {
        this.context = context;
        this.width=width;

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

        holder.bar.setLayoutParams(new LinearLayout.LayoutParams((int)(0.30*width),(int)(0.045*width)));
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
