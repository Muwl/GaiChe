package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Administrator on 2016/4/4.
 */
public class FStoreAdapter extends BaseExpandableListAdapter {

    private Context context;

    public FStoreAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return 10;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 4;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewGroupHolder holder=null;
        if (convertView==null){
            holder=new ViewGroupHolder();
            convertView=View.inflate(context, R.layout.fstore_item,null);
            holder.name= (TextView) convertView.findViewById(R.id.fstore_item_name);
            holder.icon= (ImageView) convertView.findViewById(R.id.fstore_item_icon);
            convertView.setTag(holder);
        }else{
            holder= (ViewGroupHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewChildHolder holder=null;
        if (convertView==null){
            holder=new ViewChildHolder();
            convertView=View.inflate(context,R.layout.fstore_item_item,null);
            holder.name= (TextView) convertView.findViewById(R.id.fstore_item_item_name);
            convertView.setTag(holder);
        }else {
            holder= (ViewChildHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    class ViewGroupHolder{
        public ImageView icon;
        public TextView name;
    }

    class ViewChildHolder{
        public TextView name;
    }
}
