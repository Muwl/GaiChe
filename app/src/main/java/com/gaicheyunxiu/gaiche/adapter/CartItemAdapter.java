package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.model.ShopCartCommodityEntity;
import com.gaicheyunxiu.gaiche.model.ShopCartEntity;
import com.lidroid.xutils.BitmapUtils;

import org.apache.http.params.HttpParams;

import java.util.List;

/**
 * Created by Mu on 2015/12/23.
 * 购物车之类适配器
 */
public class CartItemAdapter extends BaseAdapter{

    private Context context;
    private List<ShopCartCommodityEntity> entities;
    private Handler handler;
    private BitmapUtils bitmapUtils;
    private int grouppoi;
    public CartItemAdapter(Context context,List<ShopCartCommodityEntity> entities,Handler handler,int grouppoi) {
        this.context = context;
        this.entities=entities;
        this.handler=handler;
        this.grouppoi=grouppoi;
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.cart_item_item,null);
            holder=new ViewHolder();
            holder.icon= (ImageView) convertView.findViewById(R.id.cart_item_item_icon);
            holder.name= (TextView) convertView.findViewById(R.id.cart_item_item_name);
            holder.money= (TextView) convertView.findViewById(R.id.cart_item_item_money);
            holder.m= (TextView) convertView.findViewById(R.id.cart_item_item_m);
            holder.incream = (ImageView) convertView.findViewById(R.id.cart_item_item_incream);
            holder.num= (TextView) convertView.findViewById(R.id.cart_item_item_num);
            holder.add= (ImageView) convertView.findViewById(R.id.cart_item_item_add);
            holder.del= (TextView) convertView.findViewById(R.id.cart_item_item_del);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

//        bitmapUtils.display(holder.icon,entities.get(position).);
        holder.name.setText(entities.get(position).commodityName);
        holder.money.setText("￥"+entities.get(position).commodityPrice);
        holder.m.setText(entities.get(position).mValue+"M");
        holder.incream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(entities.get(position).amount) > 1) {
                    entities.get(position).amount = String.valueOf(Integer.valueOf(entities.get(position).amount) - 1);
                    handler.sendEmptyMessage(1774);
                    notifyDataSetChanged();
                }
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entities.get(position).amount = String.valueOf(Integer.valueOf(entities.get(position).amount) + 1);
                handler.sendEmptyMessage(1774);
                notifyDataSetChanged();
            }
        });

        holder.num.setText(entities.get(position).amount);
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomeDialog customeDialog=new CustomeDialog(context,handler,"确定要删除此商品？",position,grouppoi);
//                Message message=new Message();
//                message.what=10023;
//                message.obj=position;
//                message.arg1=grouppoi;
//                handler.sendMessage(message);
            }
        });

        return convertView;
    }
    class ViewHolder{
        public ImageView icon;
        public TextView name;
        public TextView  money;
        public TextView m;
        public ImageView incream;
        public TextView num;
        public ImageView add;
        public TextView del;
    }
}
