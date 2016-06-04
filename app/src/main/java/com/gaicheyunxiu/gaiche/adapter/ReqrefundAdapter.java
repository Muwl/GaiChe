package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.lidroid.xutils.BitmapUtils;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30.
 * 申请退货
 */
public class ReqrefundAdapter extends BaseAdapter {

    private Context context;
    private int width;
    private List<File> files;
    private BitmapUtils bitmapUtils;
    public ReqrefundAdapter(Context context,List<File> files, int width) {
        this.context = context;
        this.files=files;
        this.width = width;
        bitmapUtils=new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        if (files.size()<3){
            return files.size()+1;
        }else{
            return 3;
        }
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

        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) holder.imageView.getLayoutParams();
        layoutParams.width=(width- DensityUtil.dip2px(context,40))/3;
        layoutParams.height=(width- DensityUtil.dip2px(context,40))/3;
        holder.imageView.setLayoutParams(layoutParams);
        if (position<files.size()){
            bitmapUtils.display(holder.imageView,files.get(position).getAbsolutePath());
        }else{
            holder.imageView.setImageResource(R.drawable.refund_add_btn);
        }
        
        return convertView;
    }
    class  ViewHolder{
        public ImageView imageView;
    }
}
