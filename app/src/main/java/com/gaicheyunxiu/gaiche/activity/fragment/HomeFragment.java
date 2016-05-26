package com.gaicheyunxiu.gaiche.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.CarbrandActivity;
import com.gaicheyunxiu.gaiche.activity.CarmanagerActivity;
import com.gaicheyunxiu.gaiche.activity.CrowdFundActivity;
import com.gaicheyunxiu.gaiche.activity.MainActivity;
import com.gaicheyunxiu.gaiche.activity.QRScanActivity;
import com.gaicheyunxiu.gaiche.activity.ShopDetailActivity;
import com.gaicheyunxiu.gaiche.activity.ShopListActivity;
import com.gaicheyunxiu.gaiche.adapter.FHomeGrallryAdapter;
import com.gaicheyunxiu.gaiche.adapter.GalleryAdapter;
import com.gaicheyunxiu.gaiche.adapter.PartsAdapter;
import com.gaicheyunxiu.gaiche.model.AdEntity;
import com.gaicheyunxiu.gaiche.model.AdState;
import com.gaicheyunxiu.gaiche.model.CarTypeEntity;
import com.gaicheyunxiu.gaiche.model.CommodityEntity;
import com.gaicheyunxiu.gaiche.model.CommodityState;
import com.gaicheyunxiu.gaiche.model.CrowdfundingProjectEntity;
import com.gaicheyunxiu.gaiche.model.CrowdfundingProjectState;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.PersonDynamicEntity;
import com.gaicheyunxiu.gaiche.model.PopularCateEntity;
import com.gaicheyunxiu.gaiche.model.PopularCateState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.gaicheyunxiu.gaiche.utils.HttpPostUtils;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.MyGallery;
import com.gaicheyunxiu.gaiche.view.MyListView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2015/12/19.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    private ImageView code;

    private ImageView message;

    private TextView title;

    private MyGallery gallery1;

    private View carLin;

    private ImageView carImage;

    private ImageView carAddImage;

    private TextView carName;

    private FHomeGrallryAdapter adapter;

    private  int width;

    private LinearLayout lin = null;

    private GalleryAdapter galleryAdapter;

    private ImageView crowShop1;

    private ImageView crowShop2;

    private Timer timer;

    private TimerTask task;

    protected final int UPDATE_GALLERY = 10; // Grallery更新

    private MyGallery gallery = null;

    private LinearLayout galllin = null;

    private int screenWidth = 0;

    private BitmapUtils bitmapUtils;

    private List<CommodityEntity> commodityEntities;

    private PartsAdapter partsAdapter;

    private View typeView;

    private ImageView hotshop1View;

    private ImageView hotshop2View;

    private ImageView hotshop3View;

    private ImageView hotshop4View;

    private MyListView mylistView;

    private List<CrowdfundingProjectEntity> crowdfundingProjectEntities;

    private  List<PopularCateEntity> popularCateEntities;//首页热门分类的四大模块


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_GALLERY:
                    if (gallery.getSelectedItemPosition() == 0) {
                        gallery.setSelection(gallery.getFirstVisiblePosition() + 1);
                    } else {
                        gallery.setSelection(gallery.getFirstVisiblePosition() + 2);
                    }
                    break;
                case HttpPostUtils.FIND_MYCAR:
                    MyCarEntity carEntity=MyApplication.getInstance().getCarEntity();
                    if (carEntity!=null){
                        bitmapUtils.display(carImage,carEntity.carBrandLogo);
                        carAddImage.setVisibility(View.GONE);
                        carName.setText(carEntity.carBrandName+carEntity.displacement+carEntity.productionDate);
                    }
                    break;
            }
        };
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        code= (ImageView) view.findViewById(R.id.title_code);
        message= (ImageView) view.findViewById(R.id.title_message);
        title= (TextView) view.findViewById(R.id.title_text);
        gallery1= (MyGallery) view.findViewById(R.id.home_grally);
        carLin=view.findViewById(R.id.main_carlin);
        carImage= (ImageView) view.findViewById(R.id.main_carimage);
        carAddImage= (ImageView) view.findViewById(R.id.main_caraddimage);
        carName= (TextView) view.findViewById(R.id.main_carbrand);
        crowShop1= (ImageView) view.findViewById(R.id.home_shop);
        crowShop2= (ImageView) view.findViewById(R.id.home_shop2);



        lin = (LinearLayout) view.findViewById(R.id.home_lin);
        gallery = (MyGallery) view.findViewById(R.id.gallery);
        galllin = (LinearLayout) view.findViewById(R.id.lin);
        typeView =  view.findViewById(R.id.home_type);
        hotshop1View = (ImageView) view.findViewById(R.id.home_hotshop1);
        hotshop2View = (ImageView) view.findViewById(R.id.home_hotshop2);
        hotshop3View = (ImageView) view.findViewById(R.id.home_hotshop3);
        hotshop4View = (ImageView) view.findViewById(R.id.home_hotshop4);
        mylistView = (MyListView) view.findViewById(R.id.home_mylist);
        code.setVisibility(View.VISIBLE);
        message.setVisibility(View.VISIBLE);
        view.findViewById(R.id.title_back).setVisibility(View.GONE);
        title.setText("盖车云修");
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        width= DensityUtil.getScreenWidth(getActivity());
        commodityEntities=new ArrayList<>();
        adapter=new FHomeGrallryAdapter(getActivity(),width);
        partsAdapter=new PartsAdapter(getActivity(),commodityEntities);
        bitmapUtils=new BitmapUtils(getActivity());
        gallery1.setAdapter(adapter);
        mylistView.setAdapter(partsAdapter);
        carLin.setOnClickListener(this);
        code.setOnClickListener(this);
        typeView.setOnClickListener(this);
        crowShop1.setOnClickListener(this);
        crowShop2.setOnClickListener(this);
        hotshop1View.setOnClickListener(this);
        hotshop2View.setOnClickListener(this);
        hotshop3View.setOnClickListener(this);
        hotshop4View.setOnClickListener(this);
        int m = DensityUtil.dip2px(getActivity(), 3);
        for (int i = 0; i <3; i++) {
            ImageView image = (ImageView) LayoutInflater.from(getActivity())
                    .inflate(R.layout.banner_point, null);
            image.setPadding(m, 0, m, 0);
            lin.addView(image);
        }
        gallery1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ImageView iv;
                int count = lin.getChildCount();
                for (int i = 0; i < count; i++) {
                    iv = (ImageView) lin.getChildAt(i);
                    if (i == position % 3) {
                        iv.setImageResource(R.drawable.circle_select);
                    } else {
                        iv.setImageResource(R.drawable.circle_normal);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), ShopDetailActivity.class);
                intent.putExtra("id",commodityEntities.get(position).id);
                startActivity(intent);
            }
        });

        getAd();
        getHotSale();
        getpopularShop();
        getFundProject();
        mylistView.setFocusable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        MyCarEntity carEntity=MyApplication.getInstance().getCarEntity();
        if (carEntity!=null){
            bitmapUtils.display(carImage,carEntity.carBrandLogo);
            carAddImage.setVisibility(View.GONE);
            carName.setText(carEntity.carBrandName+carEntity.displacement+carEntity.productionDate);
        }else{
            HttpPostUtils.getMyCar(getActivity(), handler);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_code:
                Intent intent=new Intent(getActivity(), QRScanActivity.class);
                startActivity(intent);
                break;
            case R.id.main_carlin:
                MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
                if (carEntity==null){
                    ToosUtils.goBrand(getActivity(),1);
                }else{
                    Intent intent1=new Intent(getActivity(), CarmanagerActivity.class);
                    startActivityForResult(intent1, 8856);
                }

                break;

            case R.id.home_type:
                ((MainActivity)getActivity()).checkIndex(1);

                break;

            case R.id.home_hotshop1:
                if (popularCateEntities==null || popularCateEntities.size()==0){
                    return;
                }
                Intent intent1=new Intent(getActivity(), ShopListActivity.class);
                intent1.putExtra("commodityTypeId",popularCateEntities.get(0).commodityTypeId);
                intent1.putExtra("comeFlag",6);
                startActivity(intent1);
                break;
            case R.id.home_hotshop2:
                if (popularCateEntities==null || popularCateEntities.size()==0){
                    return;
                }
                Intent intent2=new Intent(getActivity(), ShopListActivity.class);
                intent2.putExtra("commodityTypeId",popularCateEntities.get(1).commodityTypeId);
                intent2.putExtra("comeFlag",6);
                startActivity(intent2);
                break;
            case R.id.home_hotshop3:
                if (popularCateEntities==null || popularCateEntities.size()==0){
                    return;
                }
                Intent intent3=new Intent(getActivity(), ShopListActivity.class);
                intent3.putExtra("commodityTypeId",popularCateEntities.get(2).commodityTypeId);
                intent3.putExtra("comeFlag",6);
                startActivity(intent3);
                break;
            case R.id.home_hotshop4:
                if (popularCateEntities==null || popularCateEntities.size()==0){
                    return;
                }
                Intent intent4=new Intent(getActivity(), ShopListActivity.class);
                intent4.putExtra("commodityTypeId",popularCateEntities.get(3).commodityTypeId);
                intent4.putExtra("comeFlag",6);
                startActivity(intent4);
                break;

            case R.id.home_shop:
                if (crowdfundingProjectEntities!=null && crowdfundingProjectEntities.size()>0){
                    Intent intent5=new Intent(getActivity(), CrowdFundActivity.class);
                    intent5.putExtra("entity",crowdfundingProjectEntities.get(0));
                    startActivity(intent5);
                }
                break;

            case R.id.home_shop2:
                if (crowdfundingProjectEntities!=null && crowdfundingProjectEntities.size()>1){
                    Intent intent6=new Intent(getActivity(), CrowdFundActivity.class);
                    intent6.putExtra("entity",crowdfundingProjectEntities.get(1));
                    startActivity(intent6);
                }
                break;
        }
    }

    public void onRefush(){
        MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
        if (carEntity!=null){
            bitmapUtils.display(carImage,carEntity.carBrandLogo);
            carAddImage.setVisibility(View.GONE);
            carName.setText(carEntity.carBrandName+carEntity.type+carEntity.displacement+carEntity.productionDate);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }





    /**
     * 获取广告
     */
    private void getAd() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "advertisement/getAd", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(getActivity());
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        AdState adState = gson.fromJson(arg0.result, AdState.class);
                        addBunne(adState.result);
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

    /**
     * 加载bunne图片
     */
    private void addBunne(final List<AdEntity> images) {
        int m = DensityUtil.dip2px(getActivity(), 3);
        for (int i = 0; i < images.size(); i++) {
            ImageView image = (ImageView) LayoutInflater.from(getActivity()).inflate(
                    R.layout.detail_point, null);
            image.setPadding(m, 0, m, 0);
            galllin.addView(image);
        }
        galleryAdapter = new GalleryAdapter(getActivity(), gallery, images, width);
        gallery.setAdapter(galleryAdapter);

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("1".equals(images.get(position % images.size()).type)) {
                    ToosUtils.openUrl(getActivity(), Constant.ROOT_PATH+"advertisement/getContent?id="+images.get(position % images.size()).id);
                } else {
                    Intent intent=new Intent(getActivity(), ShopListActivity.class);
                    intent.putExtra("id",images.get(position % images.size()).id);
                    intent.putExtra("comeFlag",1);
                    getActivity().startActivity(intent);
                }
            }
        });
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ImageView iv;
                int count = lin.getChildCount();
                for (int i = 0; i < count; i++) {
                    iv = (ImageView) galllin.getChildAt(i);
                    if (iv!=null){
                        if (i == position % images.size()) {
                            iv.setImageResource(R.mipmap.circle_select);
                        } else {
                            iv.setImageResource(R.mipmap.circle_normal);
                        }
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = UPDATE_GALLERY;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(task, 1000, 3000);
    }

    /**
     * 查询10款热销商品
     */
    private void getHotSale() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
       MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
        if (carEntity!=null){
            rp.addBodyParameter("carTypeId",carEntity.carTypeId);
        }
        if (!ToosUtils.isStringEmpty(ShareDataTool.getToken(getActivity()))){
            rp.addBodyParameter("sign",ShareDataTool.getToken(getActivity()));
        }

        LogManager.LogShow("*********", Constant.ROOT_PATH
                        + "commodity/hotsales",
                LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "commodity/hotsales", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
//                ToastUtils.displayFailureToast(getActivity());
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("11111111111-----", arg0.result,
                                LogManager.ERROR);
                        CommodityState commodityState = gson.fromJson(arg0.result, CommodityState.class);
                        if (commodityState.result != null && commodityState.result.size() > 0) {
                            for (int i = 0; i < commodityState.result.size(); i++) {
                                commodityEntities.add(commodityState.result.get(i));
                            }
                            partsAdapter.notifyDataSetChanged();
                        }
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


    /**
     * 查询热门商品分类，用于APP首页，商城下方的版块。
     */
    private void getpopularShop() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "popularCategories/query", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
//                ToastUtils.displayFailureToast(getActivity());
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    LogManager.LogShow("2222222222-----", arg0.result,
                            LogManager.ERROR);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        PopularCateState popularCateState=gson.fromJson(arg0.result, PopularCateState.class);
                       popularCateEntities=popularCateState.result;
                        setPopValue(popularCateEntities);
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

    private void setPopValue(List<PopularCateEntity> popularCateEntities){
        if (popularCateEntities==null || popularCateEntities.size()==0){
            return;
        }
        for (int i=0;i<popularCateEntities.size();i++){
            switch (popularCateEntities.get(i).plateNo){
                case "1":
                    bitmapUtils.display(hotshop1View,popularCateEntities.get(i).image);
                    break;
                case "2":
                    bitmapUtils.display(hotshop2View,popularCateEntities.get(i).image);
                    break;
                case "3":
                    bitmapUtils.display(hotshop3View,popularCateEntities.get(i).image);
                    break;
                case "4":
                    bitmapUtils.display(hotshop4View,popularCateEntities.get(i).image);
                    break;
            }
        }
    }

    /**
     * 众筹项目查询
     */
    private void getFundProject() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "crowdfundingProject/find", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(getActivity());
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----***111", arg0.result,
                                LogManager.ERROR);
                        CrowdfundingProjectState projectState=gson.fromJson(arg0.result,CrowdfundingProjectState.class);
                        crowdfundingProjectEntities=projectState.result;
                        if (crowdfundingProjectEntities.size()==0){
                            crowShop1.setVisibility(View.GONE);
                            crowShop2.setVisibility(View.GONE);
                        }
                        if (crowdfundingProjectEntities.size()>0){
                            bitmapUtils.display(crowShop1,crowdfundingProjectEntities.get(0).mobileImg);
                            crowShop1.setVisibility(View.VISIBLE);
                            crowShop2.setVisibility(View.INVISIBLE);
                        }
                        if (crowdfundingProjectEntities.size()>1){
                            bitmapUtils.display(crowShop2,crowdfundingProjectEntities.get(1).mobileImg);
                            crowShop2.setVisibility(View.VISIBLE);
                        }
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
