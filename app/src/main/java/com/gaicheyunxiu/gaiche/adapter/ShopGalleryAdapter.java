package com.gaicheyunxiu.gaiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.ShopListActivity;
import com.gaicheyunxiu.gaiche.model.AdEntity;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

public class ShopGalleryAdapter extends BaseAdapter {
	private Context context;
	private Gallery gallery;
	private List<String> images;
	private int width;
	private BitmapUtils utils;

	public ShopGalleryAdapter(Context context, Gallery gallery,
							  List<String> images, int width) {
		this.context = context;
		this.gallery = gallery;
		this.images = images;
		this.width = width;
		utils = new BitmapUtils(context);
		utils.configDefaultLoadFailedImage(R.mipmap.image1);
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(context);
		} else {
			imageView = (ImageView) convertView;
		}
		
		 imageView.setScaleType(ScaleType.FIT_XY);
		imageView.setLayoutParams(new Gallery.LayoutParams(width,
				ViewGroup.LayoutParams.MATCH_PARENT));
		if (images.size()!=0){
			utils.display(imageView, images.get(position % images.size()));
		}
		return imageView;
	}
}
