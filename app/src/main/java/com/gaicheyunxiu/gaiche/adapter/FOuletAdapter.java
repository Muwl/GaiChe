package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.SerchActivity;
import com.gaicheyunxiu.gaiche.view.RatingBar;

/**
 * Created by Mu on 2015/12/24.
 * 门店首页适配器
 */
public class FOuletAdapter extends BaseAdapter {

    private Context context;

    private int width;
    private int type0 = 1;
    private int type1 = 2;

    public FOuletAdapter(Context context, int width) {
        this.context = context;
        this.width = width;

    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return type0;
        } else {
            return type1;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        ViewHolder1 holder1 = null;
        int type = getItemViewType(position);

        if (type == type0) {
            convertView = null;
            if (convertView == null
                    || !convertView.getTag().getClass()
                    .equals(ViewHolder1.class)) {
                convertView = View.inflate(context, R.layout.foutlet_head,
                        null);
                holder1 = new ViewHolder1();
                holder1.brandlin = convertView.findViewById(R.id.foutlet_carlin);
                holder1.brandtext = (TextView) convertView.findViewById(R.id.foutlet_carbrand);
                holder1.payCode = (TextView) convertView.findViewById(R.id.foutlet_pay);
                holder1.address = (TextView) convertView.findViewById(R.id.foutlet_address);
                holder1.nearbay = (TextView) convertView.findViewById(R.id.foutlet_nearbay);
                holder1.maintainView = (TextView) convertView.findViewById(R.id.foutlet_maintain);
                holder1.carwashView = (TextView) convertView.findViewById(R.id.foutlet_carwash);
                holder1.bendksView = (TextView) convertView.findViewById(R.id.foutlet_bendks);
                holder1.sprayView = (TextView) convertView.findViewById(R.id.foutlet_spray);
                holder1.buffingView = (TextView) convertView.findViewById(R.id.foutlet_buffing);
                holder1.tyreView = (TextView) convertView.findViewById(R.id.foutlet_tyre);
                holder1.batteryView = (TextView) convertView.findViewById(R.id.foutlet_battery);
                holder1.moreView = (TextView) convertView.findViewById(R.id.foutlet_more);
                holder1.serch = convertView.findViewById(R.id.foutlet_serchlin);
                convertView.setTag(holder1);
            } else {
                holder1 = (ViewHolder1) convertView.getTag();
            }
        } else if (type == type1) {

            if (convertView == null || !convertView.getTag().getClass()
                    .equals(ViewHolder.class)) {
                convertView = View.inflate(context, R.layout.foulet_item, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.foutlet_item_image);
                holder.name = (TextView) convertView.findViewById(R.id.foutlet_item_name);
                holder.bar = (RatingBar) convertView.findViewById(R.id.foutlet_item_bar);
                holder.num = (TextView) convertView.findViewById(R.id.foutlet_item_discussnum);
                holder.money = (TextView) convertView.findViewById(R.id.foutlet_item_money);
                holder.m = (TextView) convertView.findViewById(R.id.foutlet_item_m);
                holder.address = (TextView) convertView.findViewById(R.id.foutlet_item_address);
                holder.distance = (TextView) convertView.findViewById(R.id.foutlet_item_distance);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
        }
        if (type == type0) {
            holder1.serch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, SerchActivity.class);
                    context.startActivity(intent);
                }
            });
        }else{
            holder.bar.setLayoutParams(new LinearLayout.LayoutParams((int) (0.30 * width), (int) (0.045 * width)));
        }

        return convertView;
    }

    class ViewHolder1 {
        public View brandlin;
        public TextView brandtext;
        public TextView payCode;
        public TextView address;
        public TextView nearbay;
        public TextView maintainView;
        public TextView carwashView;
        public TextView bendksView;
        public TextView sprayView;
        public TextView buffingView;
        public TextView tyreView;
        public TextView batteryView;
        public TextView moreView;
        public View serch;
    }


    class ViewHolder {
        public ImageView imageView;
        public TextView name;
        public RatingBar bar;
        public TextView num;
        public TextView money;
        public TextView m;
        public TextView address;
        public TextView distance;
    }
}
