package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.RouteInfo;
import java.util.List;

/**
 * 物流信息适配器
 * @author Cool
 *
 */
public class LogisticDetailAdapter extends BaseAdapter {
	
	private Context context;
	private List<RouteInfo> infos;
	public LogisticDetailAdapter(Context context, List<RouteInfo> infos){
		this.context = context;
		this.infos = infos;
	}
	@Override
	public int getCount() {
		return infos.size();
	}
	@Override
	public Object getItem(int position) {
		return infos.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = View.inflate(context, R.layout.logisticdetail_item, null);
			holder = new ViewHolder();
			holder.icon =  (ImageView) convertView.findViewById(R.id. logisticdetail_item_icon );
			holder.icon_top_line =  convertView.findViewById(R.id.logisticdetail_item_line);
			holder.icon_bottom_line =  convertView.findViewById(R.id.logisticdetail_item_bottom_line );
			holder.ll_bottom_line =  (LinearLayout) convertView.findViewById(R.id.logisticdetail_itemll_bottom_line );
			holder.info =  (TextView) convertView.findViewById(R.id.logisticdetail_item_info );
			holder.info.setAutoLinkMask(Linkify.ALL);
			holder.time =  (TextView) convertView.findViewById(R.id.logisticdetail_item_time );
		    convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (position == 0) {
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.logistic_checked));
			convertView.setBackgroundColor(Color.WHITE);
			holder.info.setTextColor(context.getResources().getColor(R.color.logistic_cur));
			holder.time.setTextColor(context.getResources().getColor(R.color.logistic_cur));
			holder.icon_top_line.setVisibility(View.INVISIBLE);
			holder.icon_bottom_line.setVisibility(View.VISIBLE);
			holder.ll_bottom_line.setVisibility(View.VISIBLE);
		}else if (position == infos.size()-1) {
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.logistic_normal));
			holder.info.setTextColor(context.getResources().getColor(R.color.logistic_nor));
			holder.time.setTextColor(context.getResources().getColor(R.color.logistic_nor));
			holder.icon_bottom_line.setVisibility(View.INVISIBLE);
			holder.ll_bottom_line.setVisibility(View.INVISIBLE);
			holder.icon_top_line.setVisibility(View.VISIBLE);
		}else {
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.logistic_normal));
			holder.info.setTextColor(context.getResources().getColor(R.color.logistic_nor));
			holder.time.setTextColor(context.getResources().getColor(R.color.logistic_nor));
			holder.icon_top_line.setVisibility(View.VISIBLE);
			holder.icon_bottom_line.setVisibility(View.VISIBLE);
			holder.ll_bottom_line.setVisibility(View.VISIBLE);
		}
		
		holder.info.setText(infos.get(position).getInfo());
		holder.time.setText(infos.get(position).getTime());
		return convertView;
	}
	
	static class ViewHolder{
		private ImageView icon;
		private View icon_top_line;
		private View icon_bottom_line;
		private LinearLayout ll_bottom_line;
		private TextView info;
		private TextView time;
	}
}
