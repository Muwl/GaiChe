package com.gaicheyunxiu.gaiche.model;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/25.
 * adapter基类
 */
public abstract class BaseDataAdapter<T> extends BaseAdapter {
    protected List<T> datas = new ArrayList<>();
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void update(boolean isClear, List<T> data) {
        if (isClear) {
            datas.clear();
            datas.addAll(data);
        } else {
            datas.addAll(data);
        }
        notifyDataSetChanged();
    }
    public List<T> getDatas(){
        return datas;
    }
}
