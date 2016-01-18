package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2016/1/18.
 * 物流列表适配器
 */
public class LogisticAdapter  extends BaseAdapter{

    private Context context;

    public LogisticAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 6;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.logistics_item,null);
            holder=new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.logistic_item_no);
            holder.listView= (ListView) convertView.findViewById(R.id.logistic_item_listview);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        LogisticItemAdapter adapter=new LogisticItemAdapter(context);
        holder.listView.setAdapter(adapter);

        return convertView;
    }

    class ViewHolder{
        public TextView name;
        public ListView listView;
    }
}
