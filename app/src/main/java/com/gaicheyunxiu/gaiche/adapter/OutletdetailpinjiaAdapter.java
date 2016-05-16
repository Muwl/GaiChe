package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.view.RatingBar;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2016/5/16.
 */
public class OutletdetailpinjiaAdapter extends BaseAdapter{

    private Context context;
    private BitmapUtils bitmapUtils;
    public OutletdetailpinjiaAdapter(Context context) {
        this.context = context;
        bitmapUtils=new BitmapUtils(context);
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
