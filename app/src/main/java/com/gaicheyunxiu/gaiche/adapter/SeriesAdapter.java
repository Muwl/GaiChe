package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.SeriesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/11.
 * 设置车型适配器
 */
public class SeriesAdapter extends BaseAdapter {

    private Context context;
    private Map<String,List<String>> listMap;
    private List<SeriesModel> models;
    List<String> glist;
    List<String> jlist;

    public SeriesAdapter(Context context, Map<String,List<String>> listMap) {
        this.context = context;
        this.listMap=listMap;
        glist=listMap.get("0");
       jlist=listMap.get("1");
        if (glist==null){
            glist=new ArrayList<>();
        }

        if (jlist==null){
            jlist=new ArrayList<>();
        }
        models=new ArrayList<>();
        for (int i=0;i<glist.size();i++){
            SeriesModel seriesModel=new SeriesModel();
            seriesModel.key="0";
            seriesModel.seriesval=glist.get(i);
            models.add(seriesModel);
        }

        for (int i=0;i<jlist.size();i++){
            SeriesModel seriesModel=new SeriesModel();
            seriesModel.key="1";
            seriesModel.seriesval=jlist.get(i);
            models.add(seriesModel);
        }
    }

    @Override
    public int getCount() {

        return models.size();
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
            convertView=View.inflate(context, R.layout.series_item,null);
            holder=new ViewHolder();
            holder.tip= (TextView) convertView.findViewById(R.id.series_item_tip);
            holder.content= (TextView) convertView.findViewById(R.id.series_item_text);
            holder.lin= (ImageView) convertView.findViewById(R.id.series_item_lin);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if ("0".equals(models.get(position).key)){
            holder.tip.setText("国内");
        }else {
            holder.tip.setText("国外");
        }


        if (position==0){
            holder.tip.setVisibility(View.VISIBLE);

        }
        return convertView;
    }

    class ViewHolder{
        public TextView tip;
        public TextView content;
        public ImageView lin;
    }
}
