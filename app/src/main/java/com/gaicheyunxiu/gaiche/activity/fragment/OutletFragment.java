package com.gaicheyunxiu.gaiche.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.MainActivity;
import com.gaicheyunxiu.gaiche.activity.OultSelActivity;
import com.gaicheyunxiu.gaiche.activity.OutletDetailActivity;
import com.gaicheyunxiu.gaiche.activity.SerchActivity;
import com.gaicheyunxiu.gaiche.activity.ServiceArtActivity;
import com.gaicheyunxiu.gaiche.adapter.FOuletAdapter;
import com.gaicheyunxiu.gaiche.model.AdState;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.OuletHeadEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopEntity;
import com.gaicheyunxiu.gaiche.model.ShopState;
import com.gaicheyunxiu.gaiche.utils.CityDBUtils;
import com.gaicheyunxiu.gaiche.utils.CityEntity;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.gaicheyunxiu.gaiche.utils.HttpPostUtils;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/12/19.
 */
public class OutletFragment extends Fragment implements View.OnClickListener {

    public static final int NEAR_OULET = 1226;
    public static final String BAOYANG_FLAG = "f857fcd1926147609002a8d7e4049460";
    public static final String XICHE_FLAG = "337c6822d3374b3aa9c46781b05e40f6";
    public static final String TIEMO_FLAG = "16eca404d7b34acd8259f1c48a975d40";
    public static final String BANJIN_FLAG = "21bfc5aee10642abb327a09bcb51737f";
    public static final String PAOGUANG_FLAG = "6e106bce138b4b829a8151045c3aa883";
    public static final String LUOTAI_FLAG = "9452b8cb8183456e81200de33782fc62";
    public static final String SILUN_FLAG = "5ec60ac49579412fb77d3602b353f366";

    private TextView title;

    private View map;

    private TextView cityView;

    private ListView listView;

    private FOuletAdapter adapter;

    private List<ShopEntity> entities;

    private CityEntity cityEntity;

    private View pro;
    private OuletHeadEntity headEntity;
    private BDLocation bdLocation = null;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 16624:
                    Intent intent11=new Intent(getActivity(), SerchActivity.class);
                    intent11.putExtra("cityId",cityEntity.id);
                    startActivity(intent11);
                    break;
                case HttpPostUtils.FIND_MYCAR:
                    adapter.notifyDataSetChanged();
                    break;
                case NEAR_OULET:
                    if (cityEntity == null) {
                        return;
                    }
                    Intent intent = new Intent(getActivity(), OultSelActivity.class);
                    intent.putExtra("flag", 2);
                    intent.putExtra("city", cityEntity);
                    startActivity(intent);
                    break;
                case 1446:
                    Intent intent2 = new Intent(getActivity(), OultSelActivity.class);
                    intent2.putExtra("flag", 3);
                    intent2.putExtra("serviceId", BAOYANG_FLAG);
                    intent2.putExtra("title","保养安装");
                    startActivity(intent2);
                    break;
                case 1447:
                    Intent intent3 = new Intent(getActivity(), OultSelActivity.class);
                    intent3.putExtra("flag", 3);
                    intent3.putExtra("serviceId", XICHE_FLAG);
                    intent3.putExtra("title","普通洗车");
                    startActivity(intent3);
                    break;
                case 1448:
                    Intent intent4 = new Intent(getActivity(), OultSelActivity.class);
                    intent4.putExtra("flag", 3);
                    intent4.putExtra("serviceId", TIEMO_FLAG);
                    intent4.putExtra("title","贴膜");
                    startActivity(intent4);
                    break;
                case 1449:
                    Intent intent5 = new Intent(getActivity(), OultSelActivity.class);
                    intent5.putExtra("flag", 3);
                    intent5.putExtra("serviceId", BANJIN_FLAG);
                    intent5.putExtra("title","钣金喷漆");
                    startActivity(intent5);
                    break;
                case 1450:
                    Intent intent6 = new Intent(getActivity(), OultSelActivity.class);
                    intent6.putExtra("flag", 3);
                    intent6.putExtra("serviceId", PAOGUANG_FLAG);
                    intent6.putExtra("title","抛光封釉");
                    startActivity(intent6);
                    break;
                case 1451:
                    Intent intent7 = new Intent(getActivity(), OultSelActivity.class);
                    intent7.putExtra("flag", 3);
                    intent7.putExtra("serviceId", LUOTAI_FLAG);
                    intent7.putExtra("title","轮胎修补");
                    startActivity(intent7);
                    break;
                case 1452:
                    Intent intent8 = new Intent(getActivity(), OultSelActivity.class);
                    intent8.putExtra("flag", 3);
                    intent8.putExtra("serviceId", SILUN_FLAG);
                    intent8.putExtra("title","四轮定位");
                    startActivity(intent8);
                    break;
                case 1453:
                    Intent intent9 = new Intent(getActivity(), ServiceArtActivity.class);
                    startActivity(intent9);
                    break;


            }
        }

        ;
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outlet, container, false);
        title = (TextView) view.findViewById(R.id.title_text);
        view.findViewById(R.id.title_back).setVisibility(View.GONE);
        map = view.findViewById(R.id.title_map);
        cityView = (TextView) view.findViewById(R.id.title_city);
        pro = view.findViewById(R.id.foutlet_pro);
        listView = (ListView) view.findViewById(R.id.foutlet_listview);
        title.setText("门店");
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        headEntity = new OuletHeadEntity();
        map.setVisibility(View.VISIBLE);
        mLocationClient = new LocationClient(getActivity().getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    Intent intent = new Intent(getActivity(), OutletDetailActivity.class);
                    intent.putExtra("shopId", entities.get(position - 1).id);
                    startActivity(intent);
                }
            }
        });

        if (ToosUtils.goBrand(getActivity(),0)){
            return;
        }
        mLocationClient.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {

                bdLocation = location;
                headEntity.address = location.getAddrStr();
                String city = location.getCity();
                cityEntity = CityDBUtils.getCityIdFromName(getActivity(), city);
                cityEntity.locallongitude = location.getLongitude();
                cityEntity.locallatitude = location.getLatitude();
                cityView.setText(city);
                getHotShops();
                mLocationClient.stop();
            }

        }
    }

    @Override
    public void onStop() {
        mLocationClient.stop();
        super.onStop();
    }

    public void onRefush(){
        mLocationClient.start();
    }

    /**
     * 查询热门门店
     */
    private void getHotShops() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        MyCarEntity carEntity = MyApplication.getInstance().getCarEntity();
        if (carEntity != null) {
            rp.addBodyParameter("carTypeId", carEntity.carTypeId);
        }
        rp.addBodyParameter("longitude", String.valueOf(bdLocation.getLongitude()));
        rp.addBodyParameter("latitude", String.valueOf(bdLocation.getLongitude()));
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "shop/hotShops", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(getActivity());
                pro.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);

                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----++++", arg0.result,
                                LogManager.ERROR);
                        ShopState shopState = gson.fromJson(arg0.result, ShopState.class);
                        entities = shopState.result;
                        adapter = new FOuletAdapter(getActivity(), DensityUtil.getScreenWidth(getActivity()), entities, headEntity, handler);
                        listView.setAdapter(adapter);
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(getActivity(),
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(getActivity());
                    } else {
                        ToastUtils.displayShortToast(getActivity(),
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(getActivity());
                }

            }
        });
    }
}
