package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.ClearingActivity;
import com.gaicheyunxiu.gaiche.activity.OultSelActivity;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.dialog.OutSelDialog;
import com.gaicheyunxiu.gaiche.model.ShopCartCommodityEntity;
import com.gaicheyunxiu.gaiche.model.ShopCartEntity;
import com.gaicheyunxiu.gaiche.utils.MathUtils;
import com.gaicheyunxiu.gaiche.view.MyListView;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Mu on 2015/12/23.
 *
 */
public class CartAdapter extends BaseAdapter{

    private Context context;
    private List<ShopCartEntity> entities;
    private Handler handler;
    public CartAdapter(Context context, List<ShopCartEntity> entities, Handler handler) {
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.activity_cart_item,null);
            holder.brand= (TextView) convertView.findViewById(R.id.cart_item_brand);
            holder.clear= (TextView) convertView.findViewById(R.id.cart_item_clear);
            holder.listView= (MyListView) convertView.findViewById(R.id.cart_item_list);
            holder.outlet=convertView.findViewById(R.id.cart_item_outletselect);
            holder.outText= (TextView) convertView.findViewById(R.id.cart_item_outlet);
            holder.num= (TextView) convertView.findViewById(R.id.cart_item_num);
            holder.money= (TextView) convertView.findViewById(R.id.cart_item_money);
            holder.installmoney= (TextView) convertView.findViewById(R.id.cart_item_installmon);
            holder.m= (TextView) convertView.findViewById(R.id.cart_item_m);
            holder.ok= (TextView) convertView.findViewById(R.id.cart_item_ok);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.brand.setText(entities.get(position).carBrand+entities.get(position).carType+"\u2000"+entities.get(position).displacement+"\u2000"+entities.get(position).productionDate);


        holder.outlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OultSelActivity.class);
                context.startActivity(intent);
            }
        });
        CartItemAdapter adapter=new CartItemAdapter(context,entities.get(position).cartCommodityVOs,handler,position);
        holder.listView.setAdapter(adapter);

        holder.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClearingActivity.class);
                intent.putExtra("entity",entities.get(position));
                intent.putExtra("flag",1);
                context.startActivity(intent);
            }
        });

        holder.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomeDialog customeDialog=new CustomeDialog(context,handler,"确定要清空商品？",position,-1);
            }
        });

        int m=0;
        double smoney=0;
        double mva=0;
        for (int i=0;i<entities.get(position).cartCommodityVOs.size();i++){
            int num=Integer.valueOf(entities.get(position).cartCommodityVOs.get(i).amount);
            m=m+num;
            smoney=smoney+Double.valueOf(entities.get(position).cartCommodityVOs.get(i).commodityPrice)*num;
            mva=mva+Double.valueOf(entities.get(position).cartCommodityVOs.get(i).mValue)*num;
        }

        holder.outlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutSelDialog outSelDialog=new OutSelDialog(context,position,handler);
            }
        });
        if (entities.get(position).outSelEntity!=null){
            holder.outText.setText(entities.get(position).outSelEntity.name);
        }

        if (entities.get(position).outFlag==0){
            holder.outText.setText("请选择安装门店");
            holder.installmoney.setVisibility(View.GONE);
        }else if(entities.get(position).outFlag==1 && entities.get(position).outSelEntity!=null){
            holder.outText.setText(entities.get(position).outSelEntity.name);
            holder.installmoney.setVisibility(View.VISIBLE);
            smoney=smoney+Double.valueOf(entities.get(position).outSelEntity.sumPrice);
            holder.installmoney.setText("安装费：￥"+entities.get(position).outSelEntity.sumPrice);
        }else{
            holder.outText.setText("无法确认,暂不选择门店");
            holder.installmoney.setVisibility(View.GONE);
        }
        holder.num.setText("共"+m+"件商品");
        holder.money.setText("￥"+ MathUtils.getMathDem(smoney));
        holder.m.setText(MathUtils.getMathDem(mva)+"M");
        return convertView;
    }
    class ViewHolder{
        public TextView brand;
        public TextView clear;
        public MyListView listView;
        public View outlet;
        public TextView outText;
        public TextView num;
        public TextView money;
        public TextView installmoney;
        public TextView m;
        public TextView ok;
    }
}
