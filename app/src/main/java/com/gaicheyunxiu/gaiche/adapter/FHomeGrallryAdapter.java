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
import com.gaicheyunxiu.gaiche.activity.PartActivity;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.mining.app.zxing.decoding.Intents;

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
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_parts:
                Intent intent=new Intent(context, PartActivity.class);
                context.startActivity(intent);
                break;
        }
    }
}
