package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.BrandEntity;

import java.util.List;

import static com.gaicheyunxiu.gaiche.R.id.brand_item_name;

/**
 * Created by Administrator on 2016/3/20.
 */
public class BrandAdapter extends BaseAdapter {

    private Context context;
    public List<BrandEntity> entities;

    public BrandAdapter(Context context,List<BrandEntity> entities) {
        this.context = context;
        this.entities=entities;
    }

    @Override
    public int getCount() {
        return entities.size();
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
            convertView=View.inflate(context, R.layout.brand_item,null);
            holder.name= (TextView) convertView.findViewById(brand_item_name);
            holder.image= (ImageView) convertView.findViewById(R.id.brand_item_image);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.name.setText(entities.get(position).name);
        if (entities.get(position).isSel){
            holder.image.setVisibility(View.VISIBLE);
        }else{
            holder.image.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHolder{
        public TextView name;
        public ImageView image;
    }
}
