package com.gaicheyunxiu.gaiche.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.MainActivity;
import com.gaicheyunxiu.gaiche.model.CityModel;
import com.gaicheyunxiu.gaiche.model.DistrictModel;
import com.gaicheyunxiu.gaiche.model.ProvinceModel;
import com.gaicheyunxiu.gaiche.utils.CityDBUtils;
import com.gaicheyunxiu.gaiche.utils.CityEntity;
import com.gaicheyunxiu.gaiche.utils.XmlParserHandler;
import com.gaicheyunxiu.gaiche.view.widget.OnWheelChangedListener;
import com.gaicheyunxiu.gaiche.view.widget.WheelView;
import com.gaicheyunxiu.gaiche.view.widget.adapters.ArrayWheelAdapter;
import com.google.gson.FieldAttributes;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * @author Mu
 * @date 2015-8-1下午10:27:46
 * @description 
 */
public class AreaDialog implements OnClickListener,OnWheelChangedListener {
	private Dialog d = null;
	private View view = null;
	private Context context = null;
	int height;
	private Handler mhandler;
	private TextView ok;
	private TextView cancel;
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;

	public static final String SUB="---";
	public static final int OK_ADDRESS=8009;
//	/**
//	 * 所有省
//	 */
//	protected String[] mProvinceDatas;
//	/**
//	 * key - 省 value - 市
//	 */
//	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
//	/**
//	 * key - 市 values - 区
//	 */
//	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();


	private List<CityEntity> proEntities;
	private List<CityEntity> cityEntities;
	private List<CityEntity> areaEntities;

	/**
	 * key - 区 values - 邮编
	 */
//	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

	/**
	 * 当前省的名称
	 */
	protected String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	protected String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	protected String mCurrentDistrictName = "";

	/**
	 * 当前区的邮政编码
	 */
	protected String mCurrentZipCode = "";

	public AreaDialog(final Context context, Handler mhandler) {
		super();
		this.context = context;
		this.mhandler = mhandler;
		d = new Dialog(context);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		view = View.inflate(context, R.layout.area_dialog, null);
		d.setContentView(view);
		mViewProvince = (WheelView) d.findViewById(R.id.id_province);
		mViewCity = (WheelView) d.findViewById(R.id.id_city);
		mViewDistrict = (WheelView) d.findViewById(R.id.id_district);
		cancel = (TextView) d.findViewById(R.id.area_dialog_cancel);
		ok = (TextView) d.findViewById(R.id.area_dialog_ok);
		cancel.setOnClickListener(this);
		ok.setOnClickListener(this);
		// 添加change事件
		mViewProvince.addChangingListener(this);
		// 添加change事件
		mViewCity.addChangingListener(this);
		// 添加change事件
		mViewDistrict.addChangingListener(this);
		proEntities=CityDBUtils.getProvinces(context);
		String[] mProvinceDatas=new String[proEntities.size()];
		for (int i=0;i<proEntities.size();i++){
			mProvinceDatas[i]=proEntities.get(i).name;
		}
//		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mProvinceDatas));
		mViewProvince.setVisibleItems(5);
		mViewCity.setVisibleItems(5);
		mViewDistrict.setVisibleItems(5);
		updateCities();
		updateAreas();
		init();
	}

	private void init() {
		Window dialogWindow = d.getWindow();
		LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		lp.width = LayoutParams.MATCH_PARENT;
		// dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
		lp.height = LayoutParams.WRAP_CONTENT;
		dialogWindow
				.setBackgroundDrawableResource(R.drawable.background_dialog);
		height = lp.height;
		d.show();
		dialogAnimation(d, view, getWindowHeight(), height, false);
	}

	private int getWindowHeight() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}

	private void dialogAnimation(final Dialog d, View v, int from, int to,
								 boolean needDismiss) {

		Animation anim = new TranslateAnimation(0, 0, from, to);
		anim.setFillAfter(true);
		anim.setDuration(500);
		if (needDismiss) {
			anim.setAnimationListener(new AnimationListener() {

				public void onAnimationStart(Animation animation) {
				}

				public void onAnimationRepeat(Animation animation) {
				}

				public void onAnimationEnd(Animation animation) {
					d.dismiss();
				}
			});

		}
		v.startAnimation(anim);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.area_dialog_ok:
				String stemp=mCurrentProviceName+SUB+mCurrentCityName+SUB+mCurrentDistrictName;
				Message message=new Message();
				message.what=OK_ADDRESS;
				message.obj=stemp;
				mhandler.sendMessage(message);
				d.dismiss();
				break;
			case R.id.area_dialog_cancel:
				d.dismiss();
				break;

			default:
				break;
		}
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
//			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
//			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		} else if (wheel == mViewCity) {
			updateAreas();
//			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
//			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		} else if (wheel == mViewDistrict) {
//			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentDistrictName = areaEntities.get(newValue).name;
//			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = cityEntities.get(pCurrent).name;
//		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		areaEntities=CityDBUtils.getAreas(context,cityEntities.get(pCurrent).id);
		String[] areas=new String[areaEntities.size()];
		for (int i=0;i<areaEntities.size();i++){
			areas[i]=areaEntities.get(i).name;
		}
//		String[] areas = mDistrictDatasMap.get(mCurrentCityName);
		if (areas == null) {
			areas = new String[]{""};
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context, areas));
		mViewDistrict.setCurrentItem(0);
		mCurrentDistrictName = areaEntities.get(0).name;
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
//		mCurrentProviceName = mProvinceDatas[pCurrent];
		mCurrentProviceName = proEntities.get(pCurrent).name;
		cityEntities=CityDBUtils.getCity(context,proEntities.get(pCurrent).id);
		String[] cities=new String[cityEntities.size()];
		for (int i=0;i<cityEntities.size();i++){
			cities[i]=cityEntities.get(i).name;
		}
//		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[]{""};
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}


//
//	protected void initProvinceDatas() {
//		List<CityEntity> proEntities= CityDBUtils.getProvinces(context);
//		mProvinceDatas=new String[proEntities.size()];
//		for (int i=0;i<proEntities.size();i++){
//			mProvinceDatas[i]=proEntities.get(i).name;
//			List<CityEntity> cityEntities=CityDBUtils.getCity(context,proEntities.get(i).id);
//			String[] citys=new String[cityEntities.size()];
//			for (int m=0;m<cityEntities.size();m++){
//				List<CityEntity> areaEntities=CityDBUtils.getAreas(context,cityEntities.get(m).id);
//				citys[m]=cityEntities.get(m).name;
//				String[] areas=new String[areaEntities.size()];
//				for (int j=0;j<areaEntities.size();j++){
//					areas[j]=areaEntities.get(j).name;
//				}
//				mDistrictDatasMap.put(citys[m],areas);
//			}
//			mCitisDatasMap.put(mProvinceDatas[i],citys);
//		}


//		List<ProvinceModel> provinceList = null;
//		AssetManager asset = context.getAssets();
//		try {
//			InputStream input = asset.open("province_data.xml");
//			// 创建一个解析xml的工厂对象
//			SAXParserFactory spf = SAXParserFactory.newInstance();
//			// 解析xml
//			SAXParser parser = spf.newSAXParser();
//			XmlParserHandler handler = new XmlParserHandler();
//			parser.parse(input, handler);
//			input.close();
//			// 获取解析出来的数据
//			provinceList = handler.getDataList();
//			//*/ 初始化默认选中的省、市、区
//			if (provinceList != null && !provinceList.isEmpty()) {
//				mCurrentProviceName = provinceList.get(0).getName();
//				List<CityModel> cityList = provinceList.get(0).getCityList();
//				if (cityList != null && !cityList.isEmpty()) {
//					mCurrentCityName = cityList.get(0).getName();
//					List<DistrictModel> districtList = cityList.get(0).getDistrictList();
//					mCurrentDistrictName = districtList.get(0).getName();
//					mCurrentZipCode = districtList.get(0).getZipcode();
//				}
//			}
//			//*/
//			mProvinceDatas = new String[provinceList.size()];
//			for (int i = 0; i < provinceList.size(); i++) {
//				// 遍历所有省的数据
//				mProvinceDatas[i] = provinceList.get(i).getName();
//				List<CityModel> cityList = provinceList.get(i).getCityList();
//				String[] cityNames = new String[cityList.size()];
//				for (int j = 0; j < cityList.size(); j++) {
//					// 遍历省下面的所有市的数据
//					cityNames[j] = cityList.get(j).getName();
//					List<DistrictModel> districtList = cityList.get(j).getDistrictList();
//					String[] distrinctNameArray = new String[districtList.size()];
//					DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
//					for (int k = 0; k < districtList.size(); k++) {
//						// 遍历市下面所有区/县的数据
//						DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
//						// 区/县对于的邮编，保存到mZipcodeDatasMap
//						mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
//						distrinctArray[k] = districtModel;
//						distrinctNameArray[k] = districtModel.getName();
//					}
//					// 市-区/县的数据，保存到mDistrictDatasMap
//					mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
//				}
//				// 省-市的数据，保存到mCitisDatasMap
//				mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
//			}
//		} catch (Throwable e) {
//			e.printStackTrace();
//		} finally {
//
//		}
//	}
}


