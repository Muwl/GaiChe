package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.FSupportActivity;
import com.gaicheyunxiu.gaiche.activity.FcialActivity;
import com.gaicheyunxiu.gaiche.activity.ShopListActivity;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;

/**
 * Created by Administrator on 2015/12/20.
 * 主页功能适配器
 */
public class FHomeGrallryAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private int width;
    private int eachWidth;

    public FHomeGrallryAdapter(Context context,int width) {
        this.context = context;
        this.width=width;
        eachWidth=((width- DensityUtil.dip2px(context, 158))/4);
    }

    @Override
    public int getCount() {
        return 3;
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
        if (position==0){
            convertView=View.inflate(context, R.layout.fhome_page1,null);
            View root=convertView.findViewById(R.id.home_parts1_root);
            ImageView parts= (ImageView) convertView.findViewById(R.id.home_parts);
            ImageView support= (ImageView) convertView.findViewById(R.id.home_support);
            ImageView beauty= (ImageView) convertView.findViewById(R.id.home_beauty);
            ImageView gps= (ImageView) convertView.findViewById(R.id.home_gps);
            ImageView earnings= (ImageView) convertView.findViewById(R.id.home_earnings);
            ImageView brake= (ImageView) convertView.findViewById(R.id.home_brake);
            ImageView ask= (ImageView) convertView.findViewById(R.id.home_ask);
            ImageView tire= (ImageView) convertView.findViewById(R.id.home_tire);

            root.setLayoutParams(new RelativeLayout.LayoutParams(width,width));
            parts.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            support.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            beauty.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            gps.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            earnings.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            brake.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            ask.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            tire.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));

            parts.setOnClickListener(this);
            support.setOnClickListener(this);
            beauty.setOnClickListener(this);
            gps.setOnClickListener(this);
            earnings.setOnClickListener(this);
            brake.setOnClickListener(this);
            ask.setOnClickListener(this);
            tire.setOnClickListener(this);
        }else if(position==1){
            convertView=View.inflate(context, R.layout.fhome_page2,null);
            View root=convertView.findViewById(R.id.home_parts2_root);
            ImageView airfilter= (ImageView) convertView.findViewById(R.id.home_airfilter);
            ImageView wiperwash= (ImageView) convertView.findViewById(R.id.home_wiperwash);
            ImageView brakeoil= (ImageView) convertView.findViewById(R.id.home_brakeoil);
            ImageView brakedisc= (ImageView) convertView.findViewById(R.id.home_brakedisc);
            ImageView pcmo= (ImageView) convertView.findViewById(R.id.home_pcmo);
            ImageView battery= (ImageView) convertView.findViewById(R.id.home_battery);
            ImageView headlamp= (ImageView) convertView.findViewById(R.id.home_headlamp);
            ImageView sparkplug= (ImageView) convertView.findViewById(R.id.home_sparkplug);

            root.setLayoutParams(new RelativeLayout.LayoutParams(width,width));
            airfilter.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            wiperwash.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            brakeoil.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            brakedisc.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            pcmo.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            battery.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            headlamp.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            sparkplug.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));

            airfilter.setOnClickListener(this);
            wiperwash.setOnClickListener(this);
            brakeoil.setOnClickListener(this);
            brakedisc.setOnClickListener(this);
            pcmo.setOnClickListener(this);
            battery.setOnClickListener(this);
            headlamp.setOnClickListener(this);
            sparkplug.setOnClickListener(this);
        }else{
            convertView=View.inflate(context, R.layout.fhome_page3,null);
            View root=convertView.findViewById(R.id.home_parts3_root);
            ImageView timstrap= (ImageView) convertView.findViewById(R.id.home_timstrap);
            ImageView strap= (ImageView) convertView.findViewById(R.id.home_strap);
            ImageView damper= (ImageView) convertView.findViewById(R.id.home_damper);
            ImageView engine= (ImageView) convertView.findViewById(R.id.home_engine);
            ImageView veer= (ImageView) convertView.findViewById(R.id.home_veer);
            ImageView air= (ImageView) convertView.findViewById(R.id.home_air);
            ImageView filtration= (ImageView) convertView.findViewById(R.id.home_filtration);
            ImageView exhaust= (ImageView) convertView.findViewById(R.id.home_exhaust);

            root.setLayoutParams(new RelativeLayout.LayoutParams(width,width));
            timstrap.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            strap.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            damper.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            engine.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            veer.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            air.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            filtration.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));
            exhaust.setLayoutParams(new LinearLayout.LayoutParams(eachWidth,eachWidth));

            timstrap.setOnClickListener(this);
            strap.setOnClickListener(this);
            damper.setOnClickListener(this);
            engine.setOnClickListener(this);
            veer.setOnClickListener(this);
            air.setOnClickListener(this);
            filtration.setOnClickListener(this);
            exhaust.setOnClickListener(this);
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_parts:
                Intent intent=new Intent(context, ShopListActivity.class);
                intent.putExtra("comeFlag",2);
                intent.putExtra("type","0");
                context.startActivity(intent);
                break;

            case R.id.home_earnings:
                Intent intent3=new Intent(context, ShopListActivity.class);
                intent3.putExtra("comeFlag",2);
                intent3.putExtra("type","1");
                context.startActivity(intent3);
                break;

            case R.id.home_brake:
                Intent intent4=new Intent(context, ShopListActivity.class);
                intent4.putExtra("comeFlag",2);
                intent4.putExtra("type","2");
                context.startActivity(intent4);
                break;

            case R.id.home_tire:
                Intent intent5=new Intent(context, ShopListActivity.class);
                intent5.putExtra("comeFlag",2);
                intent5.putExtra("type","3");
                context.startActivity(intent5);
                break;

            case R.id.home_airfilter:
                Intent intent6=new Intent(context, ShopListActivity.class);
                intent6.putExtra("comeFlag",2);
                intent6.putExtra("type","4");
                context.startActivity(intent6);
                break;

            case R.id.home_wiperwash:
                Intent intent7=new Intent(context, ShopListActivity.class);
                intent7.putExtra("comeFlag",2);
                intent7.putExtra("type","5");
                context.startActivity(intent7);
                break;

            case R.id.home_brakeoil:
                Intent intent8=new Intent(context, ShopListActivity.class);
                intent8.putExtra("comeFlag",2);
                intent8.putExtra("type","6");
                context.startActivity(intent8);
                break;

            case R.id.home_brakedisc:
                Intent intent9=new Intent(context, ShopListActivity.class);
                intent9.putExtra("comeFlag",2);
                intent9.putExtra("type","7");
                context.startActivity(intent9);
                break;

            case R.id.home_pcmo:
                Intent intent10=new Intent(context, ShopListActivity.class);
                intent10.putExtra("comeFlag",2);
                intent10.putExtra("type","8");
                context.startActivity(intent10);
                break;

            case R.id.home_battery:
                Intent intent11=new Intent(context, ShopListActivity.class);
                intent11.putExtra("comeFlag",2);
                intent11.putExtra("type","9");
                context.startActivity(intent11);
                break;

            case R.id.home_headlamp:
                Intent intent12=new Intent(context, ShopListActivity.class);
                intent12.putExtra("comeFlag",2);
                intent12.putExtra("type","10");
                context.startActivity(intent12);
                break;

            case R.id.home_sparkplug:
                Intent intent13=new Intent(context, ShopListActivity.class);
                intent13.putExtra("comeFlag",2);
                intent13.putExtra("type","11");
                context.startActivity(intent13);
                break;

            case R.id.home_timstrap:
                Intent intent14=new Intent(context, ShopListActivity.class);
                intent14.putExtra("comeFlag",2);
                intent14.putExtra("type","12");
                context.startActivity(intent14);
                break;

            case R.id.home_strap:
                Intent intent15=new Intent(context, ShopListActivity.class);
                intent15.putExtra("comeFlag",2);
                intent15.putExtra("type","13");
                context.startActivity(intent15);
                break;

            case R.id.home_damper:
                Intent intent16=new Intent(context, ShopListActivity.class);
                intent16.putExtra("comeFlag",2);
                intent16.putExtra("type","14");
                context.startActivity(intent16);
                break;

            case R.id.home_engine:
                Intent intent17=new Intent(context, ShopListActivity.class);
                intent17.putExtra("comeFlag",2);
                intent17.putExtra("type","15");
                context.startActivity(intent17);
                break;

            case R.id.home_veer:
                Intent intent18=new Intent(context, ShopListActivity.class);
                intent18.putExtra("comeFlag",2);
                intent18.putExtra("type","16");
                context.startActivity(intent18);
                break;

            case R.id.home_air:
                Intent intent19=new Intent(context, ShopListActivity.class);
                intent19.putExtra("comeFlag",2);
                intent19.putExtra("type","17");
                context.startActivity(intent19);
                break;

            case R.id.home_filtration:
                Intent intent20=new Intent(context, ShopListActivity.class);
                intent20.putExtra("comeFlag",2);
                intent20.putExtra("type","18");
                context.startActivity(intent20);
                break;

            case R.id.home_exhaust:
                Intent intent21=new Intent(context, ShopListActivity.class);
                intent21.putExtra("comeFlag",2);
                intent21.putExtra("type","19");
                context.startActivity(intent21);
                break;

            case R.id.home_beauty:
                Intent intent2=new Intent(context, FcialActivity.class);
                context.startActivity(intent2);
                break;
            case R.id.home_support:
                Intent intent22=new Intent(context, FSupportActivity.class);
                context.startActivity(intent22);
                break;
        }
    }
}
