package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Administrator on 2015/12/30.
 * 申请退货
 */
public class ReqrefundAdapter extends BaseAdapter {

    private Context context;
    private int width;

    public ReqrefundAdapter(Context context, int width) {
        this.context = context;
        this.width = width;
    }

    @Override
    public int getCount() {
        return 1;
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
            convertView=View.inflate(context, R.layout.reqrefund_item,null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.reqrefund_item_image);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        
        return convertView;
    }
    class  ViewHolder{
        public ImageView imageView;
    }
}
