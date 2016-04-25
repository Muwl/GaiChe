package com.gaicheyunxiu.gaiche.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.CarbrandActivity;
import com.gaicheyunxiu.gaiche.activity.SeriesActivity;
import com.gaicheyunxiu.gaiche.activity.fragment.HomeFragment;
import com.gaicheyunxiu.gaiche.model.CarBrandEntity;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/2/11.
 */
public class CarbrandAdapter extends BaseAdapter implements SectionIndexer {

    private Context context;
    public List<CarBrandEntity> entities;
    public BitmapUtils bitmapUtils;

    public CarbrandAdapter(Context context,List<CarBrandEntity> entities) {
        this.context = context;
        this.entities=entities;
        bitmapUtils=new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        return entities.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        bitmapUtils.display(viewHolder.tvImage,entities.get(position).logo);
        viewHolder.tvTitle.setText(entities.get(position).name);

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.lin.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(entities.get(position).letter);
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
            viewHolder.lin.setVisibility(View.GONE);
        }


        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,SeriesActivity.class);
                intent.putExtra("brand",entities.get(position));
                ((Activity)context).startActivityForResult(intent,1004);
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


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        if(ToosUtils.isStringEmpty(entities.get(position).letter)){
            entities.get(position).letter="#";
        }
        return entities.get(position).letter.charAt(0);

    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = entities.get(i).letter;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
