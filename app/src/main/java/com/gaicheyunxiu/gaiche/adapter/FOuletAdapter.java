package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.CarmanagerActivity;
import com.gaicheyunxiu.gaiche.activity.MainActivity;
import com.gaicheyunxiu.gaiche.activity.QRScanActivity;
import com.gaicheyunxiu.gaiche.activity.SerchActivity;
import com.gaicheyunxiu.gaiche.activity.fragment.OutletFragment;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.OuletHeadEntity;
import com.gaicheyunxiu.gaiche.model.ShopEntity;
import com.gaicheyunxiu.gaiche.utils.HttpPostUtils;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.RatingBar;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Mu on 2015/12/24.
 * 门店首页适配器
 */
public class FOuletAdapter extends BaseAdapter {

    private Context context;
    private List<ShopEntity> shopEntities;
    private int width;
    private int type0 = 1;
    private int type1 = 2;
    private BitmapUtils bitmapUtils;
    private OuletHeadEntity entity;
    private Handler handler;

    public FOuletAdapter(Context context, int width,List<ShopEntity> shopEntities,OuletHeadEntity entity,Handler handler) {
        this.context = context;
        this.width = width;
        this.handler=handler;
        this.entity=entity;
        this.shopEntities=shopEntities;
        bitmapUtils=new BitmapUtils(context);

    }

    @Override
    public int getCount() {
        return shopEntities.size()+1;
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
                holder1.carImage= (ImageView) convertView.findViewById(R.id.foutlet_carimage);
                holder1.carAddImage= (ImageView) convertView.findViewById(R.id.foutlet_caraddimage);
                holder1.carName= (TextView) convertView.findViewById(R.id.foutlet_carbrand);
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
                holder.lin = convertView.findViewById(R.id.foutlet_item_lin);
                holder.m = (TextView) convertView.findViewById(R.id.foutlet_item_m);
                holder.address = (TextView) convertView.findViewById(R.id.foutlet_item_address);
                holder.distance = (TextView) convertView.findViewById(R.id.foutlet_item_distance);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
        }
        if (type == type0) {
            holder1.address.setText(entity.address);
            holder1.address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(2663);
                }
            });
            holder1.serch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(16624);

                }
            });

            MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
            if (carEntity!=null){
                bitmapUtils.display(holder1.carImage, carEntity.carBrandLogo);
                holder1.carAddImage.setVisibility(View.GONE);
                holder1.carName.setText(carEntity.carBrandName + carEntity.type + carEntity.displacement + carEntity.productionDate+"年产");
            }
//            else{
//                HttpPostUtils.getMyCar(context, handler);
//            }

            holder1.brandlin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ToosUtils.goBrand(context,0)){
                        return;
                    }
                    Intent intent1=new Intent(context, CarmanagerActivity.class);
                    ((MainActivity)context).startActivityForResult(intent1, 8856);
                }
            });
            holder1.payCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, QRScanActivity.class);
                    context.startActivity(intent);
                }
            });

            holder1.nearbay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(OutletFragment.NEAR_OULET);
                }
            });
            holder1.maintainView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(1446);
                }
            });
            holder1.carwashView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(1447);
                }
            });
            holder1.bendksView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(1448);
                }
            });
            holder1.sprayView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(1449);
                }
            });
            holder1.buffingView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(1450);
                }
            });
            holder1.tyreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(1451);
                }
            });
            holder1.batteryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(1452);
                }
            });
            holder1.moreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(1453);
                }
            });

        }else{
            bitmapUtils.display(holder.imageView, shopEntities.get(position - 1).icon);
            holder.name.setText(shopEntities.get(position - 1).name);
            holder.num.setText(shopEntities.get(position - 1).evaluateAmount + "条");
            holder.money.setText(shopEntities.get(position - 1).sumPrice + "元");
            holder.m.setText(shopEntities.get(position - 1).sumMvalue + "M");
            holder.lin.setVisibility(View.GONE);
            if (!ToosUtils.isStringEmpty(shopEntities.get(position-1).score)){
                holder.bar.setStar(Double.parseDouble(shopEntities.get(position-1).score));
            }
            holder.bar.setClickable(false);
            holder.address.setText(shopEntities.get(position-1).district);
            holder.distance.setText(shopEntities.get(position-1).distance);
            holder.bar.setLayoutParams(new LinearLayout.LayoutParams((int) (0.30 * width), (int) (0.045 * width)));
        }

        return convertView;
    }

    class ViewHolder1 {
        public View brandlin;
        public ImageView carImage;
        public ImageView carAddImage;
        public TextView carName;
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
        public View lin;
        public TextView address;
        public TextView distance;
    }
}
