package com.gaicheyunxiu.gaiche.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.tech.IsoDep;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.ShipaddressActivity;
import com.gaicheyunxiu.gaiche.activity.ShipaddressaddActivity;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.model.AddressVo;
import com.gaicheyunxiu.gaiche.model.BaseDataAdapter;

import java.util.List;

/**
 * Created by Mu on 2016/1/8.
 * 收货地址适配器
 */
public class ShipaddressAdapter extends BaseDataAdapter<AddressVo> {

    private Context context;
    private Handler handler;
    public ShipaddressAdapter(Context context,Handler handler) {
        this.context = context;
        this.handler=handler;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        final List<AddressVo> data = getDatas();
        if (holder==null){
            convertView=View.inflate(context, R.layout.shipaddress_item,null);
            holder=new ViewHolder();
            holder.imageView= (ImageView) convertView.findViewById(R.id.shipaddress_item_image);
            holder.name= (TextView) convertView.findViewById(R.id.shipaddress_item_name);
            holder.phone= (TextView) convertView.findViewById(R.id.shipaddress_item_phone);
            holder.address= (TextView) convertView.findViewById(R.id.shipaddress_item_address);
            holder.edit= (TextView) convertView.findViewById(R.id.shipaddress_item_edit);
            holder.del= (TextView) convertView.findViewById(R.id.shipaddress_item_del);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.name.setText(data.get(position).getName());
        holder.phone.setText(data.get(position).getMobile());
        holder.address.setText(data.get(position).getProvince() + data.get(position).getCity() + data.get(position).getDistrict() + data.get(position).getAddress());
        if (data.get(position).isDefault()){
            holder.imageView.setImageResource(R.mipmap.address_select);
        }else{
            holder.imageView.setImageResource(R.mipmap.address_noselect);
        }

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomeDialog dialog=new CustomeDialog(context,handler,"确定删除此地址?",position,-1);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message=new Message();
                message.what=5336;
                message.arg1=position;
                handler.sendMessage(message);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShipaddressaddActivity.class);
                intent.putExtra("entity",data.get(position));
                ((Activity)context).startActivityForResult(intent, ShipaddressActivity.ADDRESS_ADD);
            }
        });
        return convertView;
    }
    class ViewHolder{
        public ImageView imageView;
        public TextView name;
        public TextView phone;
        public TextView address;
        public TextView edit;
        public TextView del;

    }
}
