package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.utils.CityEntity;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class CityListAdapter extends BaseAdapter implements SectionIndexer{
    private Context context;
    private List<CityEntity> entities;

    public CityListAdapter(Context context, List<CityEntity> entities) {
        this.context = context;
        this.entities = entities;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.citysel_listitem,null);
            holder=new ViewHolder();
            holder.title= (TextView) convertView.findViewById(R.id.catalog);
            holder.content= (TextView) convertView.findViewById(R.id.title);
            holder.lin= (ImageView) convertView.findViewById(R.id.index_lin);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
			holder.title.setVisibility(View.VISIBLE);
			holder.lin.setVisibility(View.VISIBLE);
            if (!ToosUtils.isStringEmpty(entities.get(position).pinyin)){
                holder.title.setText(entities.get(position).pinyin.substring(0,1));
            }

		} else {
            holder.title.setVisibility(View.GONE);
            holder.lin.setVisibility(View.GONE);
		}
        holder.content.setText(entities.get(position).name);
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = entities.get(i).pinyin;
            if (!ToosUtils.isStringEmpty(sortStr)){
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
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
    public int getSectionForPosition(int position) {
        if (!ToosUtils.isStringEmpty( entities.get(position).pinyin)){
            return entities.get(position).pinyin.charAt(0);
        }else{
            return 0;
        }

    }

    class ViewHolder{
        public TextView title;
        public TextView content;
        public ImageView lin;
    }
}
