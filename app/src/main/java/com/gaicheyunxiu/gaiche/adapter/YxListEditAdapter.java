package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.CommodityEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/6/7.
 */
public class YxListEditAdapter extends BaseAdapter {

    private Context context;
    List<CommodityEntity> entities;
    private Handler handler;
    private BitmapUtils bitmapUtils;

    public YxListEditAdapter(Context context, List<CommodityEntity> entities, Handler handler) {
        this.context = context;
        this.entities = entities;
        this.handler = handler;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.yxlistedit_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.yxlist_item_image);
            holder.add = (ImageView) convertView.findViewById(R.id.yxlist_item_add);
            holder.num = (TextView) convertView.findViewById(R.id.yxlist_item_num);
            holder.incream = (ImageView) convertView.findViewById(R.id.yxlist_item_incream);
            holder.del = (TextView) convertView.findViewById(R.id.yxlist_item_del);
            holder.change = (TextView) convertView.findViewById(R.id.yxlist_item_change);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        bitmapUtils.display(holder.imageView, entities.get(position).briefImage);
        holder.num.setText(entities.get(position).sales);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entities.get(position).sales = Integer.valueOf(entities.get(position).sales) + 1 + "";
                notifyDataSetChanged();
            }
        });

        holder.num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(entities.get(position).sales) > 1) {
                    entities.get(position).sales = Integer.valueOf(entities.get(position).sales) - 1 + "";
                    notifyDataSetChanged();
                }
            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entities.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message=new Message();
                message.what=4546;
                message.what=position;
                handler.sendMessage(message);
            }
        });

        return null;
    }

    class ViewHolder {
        public ImageView imageView;
        public ImageView add;
        public TextView num;
        public ImageView incream;
        public TextView del;
        public TextView change;

    }
}
