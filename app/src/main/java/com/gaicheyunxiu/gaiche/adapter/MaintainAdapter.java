package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.MaintainEntity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.http.client.multipart.content.ContentBody;

import java.util.List;

/**
 * Created by Mu on 2016/1/8.
 * 保养档案适配器
 */
public class MaintainAdapter extends BaseAdapter {

    private Context context;
    private List<MaintainEntity> entities;
    private BitmapUtils bitmapUtils;

    public MaintainAdapter(Context context,List<MaintainEntity> entities) {
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.maintain_item,null);
            holder=new ViewHolder();
            holder.imageView= (ImageView) convertView.findViewById(R.id.maintain_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.maintain_item_name);
            holder.content= (TextView) convertView.findViewById(R.id.maintain_item_content);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        bitmapUtils.display(holder.imageView, entities.get(position).carBrandLogo);
        holder.name.setText(entities.get(position).carBrandName+entities.get(position).type+"\u2000"+entities.get(position).displacement+"\u2000"+entities.get(position).productionDate+"\u2000("+entities.get(position).productionPlace+")");
        return convertView;
    }
    class ViewHolder{
        public ImageView imageView;
        public TextView name;
        public TextView content;
    }
}
