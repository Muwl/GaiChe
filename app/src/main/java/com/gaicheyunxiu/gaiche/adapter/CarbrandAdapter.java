package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.CarbrandActivity;
import com.gaicheyunxiu.gaiche.activity.SeriesActivity;
import com.gaicheyunxiu.gaiche.activity.fragment.HomeFragment;
import com.gaicheyunxiu.gaiche.utils.LogManager;

/**
 * Created by Administrator on 2016/2/11.
 */
public class CarbrandAdapter extends BaseAdapter{

    private Context context;

    public CarbrandAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.carbrand_item, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
            viewHolder.view=convertView.findViewById(R.id.carbrand_item_view);
            viewHolder.tvImage = (ImageView) convertView
                    .findViewById(R.id.index_image);
            viewHolder.lin = (ImageView) convertView.findViewById(R.id.index_lin);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogManager.LogShow("11111111","22222222222222",LogManager.ERROR);
                Intent intent=new Intent(context,SeriesActivity.class);
                context.startActivity(intent);
            }
        });

        return  convertView;
    }

  static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        ImageView tvImage;
        View  view;
        ImageView lin;
    }
}
