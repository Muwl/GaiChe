package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.LogisticDetailActivity;
import com.gaicheyunxiu.gaiche.model.ShopOrderVo;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Mu on 2016/1/18.
 * 物流列表适配器
 */
public class LogisticItemAdapter extends BaseAdapter{

    private Context context;
    private List<ShopOrderVo> entities;
    private BitmapUtils bitmapUtils;
    private int groupoi;
    private Handler handler;

    public LogisticItemAdapter(Context context, List<ShopOrderVo> entities, int groupoi, Handler handler) {
        this.context = context;
        this.entities = entities;
        this.groupoi = groupoi;
        this.handler = handler;
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
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.logistics_item_item,null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.logistics_item_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.logistics_item_item_name);
            holder.num= (TextView) convertView.findViewById(R.id.logistics_item_item_num);
            holder.money= (TextView) convertView.findViewById(R.id.logistics_item_item_money);
            holder.m= (TextView) convertView.findViewById(R.id.logistics_item_item_m);
            holder.oldmoney= (TextView) convertView.findViewById(R.id.logistics_item_item_oldmoney);
            holder.volume= (TextView) convertView.findViewById(R.id.logistics_item_item_volume);
            holder.div= (ImageView) convertView.findViewById(R.id.logistics_item_item_div);
            holder.lin=convertView.findViewById(R.id.logistics_item_item_lin);
            holder.organbtn= (TextView) convertView.findViewById(R.id.logistics_item_item_orangebtn);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        bitmapUtils.display(holder.imageView, entities.get(position).briefImage);
        holder.name.setText(entities.get(position).name);
        holder.money.setText("￥" + entities.get(position).presentPrice + "元");
        holder.m.setText("￥"+entities.get(position).mValue+"M");
        holder.oldmoney.setText("￥" + entities.get(position).originalPrice);
        holder.oldmoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
        holder.volume.setText("销量：" + entities.get(position).sales + "件");

        holder.organbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message=new Message();
                message.what=1336;
                message.arg1=position;
                message.arg2=groupoi;
                handler.sendMessage(message);

            }
        });
        return convertView;
    }

    class  ViewHolder{
        public ImageView imageView;
        public TextView name;
        public TextView num;
        public TextView money;
        public TextView m;
        public TextView oldmoney;
        public TextView volume;
        public ImageView div;
        public View lin;
        public TextView organbtn;
    }
}
