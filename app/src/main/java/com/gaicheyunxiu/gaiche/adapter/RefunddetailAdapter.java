package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.BaseActivity;

/**
 * Created by Administrator on 2015/12/30.
 * 退款详情适配器
 */
public class RefunddetailAdapter  extends BaseAdapter{

    private Context context;

    public RefunddetailAdapter(Context context) {
        this.context = context;
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
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.refunddetail_item,null);
            holder.state= (TextView) convertView.findViewById(R.id.refunddetail_item_state);
            holder.content= (TextView) convertView.findViewById(R.id.refunddetail_item_content);
            holder.time= (TextView) convertView.findViewById(R.id.refunddetail_item_time);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        public TextView state;
        public TextView content;
        public TextView time;
    }
}
