package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/2/11.
 */
public class CarManagerAdapter extends BaseAdapter {

    private Context context;
    private List<MyCarEntity> entities;
    private Handler handler;
    private BitmapUtils bitmapUtils;
    public CarManagerAdapter(Context context,List<MyCarEntity> entities,Handler handler) {
        this.context = context;
        this.entities=entities;
        this.handler=handler;
        bitmapUtils=new BitmapUtils(context);
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
            convertView=View.inflate(context, R.layout.carmanager_item,null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.carmanager_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.carmanager_item_name);
            holder.cb= (CheckBox) convertView.findViewById(R.id.carmanager_item_cb);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        bitmapUtils.display(holder.imageView, entities.get(position).carBrandLogo);
        String temp="进口";
        if ("0".equals(entities.get(position).productionPlace)){
            temp="国产";
        }else{
            temp="进口";
        }
        holder.cb.setClickable(entities.get(position).isDefault);
        holder.name.setText(entities.get(position).carBrandName+"\u2000"+entities.get(position).displacement+"\u2000"+entities.get(position).productionDate+"\n（"+temp+"）");

        return convertView;
    }
    class ViewHolder{
        public ImageView imageView;
        public TextView name;
        public CheckBox cb;
    }
}
