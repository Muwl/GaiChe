package com.gaicheyunxiu.gaiche.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.navisdk.adapter.BNOuterLogUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.FOuletAdapter;
import com.gaicheyunxiu.gaiche.adapter.OutletDetailAdapter;
import com.gaicheyunxiu.gaiche.adapter.OutletdetailSerAdapter;
import com.gaicheyunxiu.gaiche.adapter.OutletdetailpinjiaAdapter;
import com.gaicheyunxiu.gaiche.dialog.BaiduDialog;
import com.gaicheyunxiu.gaiche.model.CommodityState;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.OuletEvaluaEntity;
import com.gaicheyunxiu.gaiche.model.OutSelDetailEntity;
import com.gaicheyunxiu.gaiche.model.OutSelDetailState;
import com.gaicheyunxiu.gaiche.model.OutSelDetailVo;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopEvaluationEntity;
import com.gaicheyunxiu.gaiche.model.ShopEvaluationState;
import com.gaicheyunxiu.gaiche.model.ShopServiceEntity;
import com.gaicheyunxiu.gaiche.model.ShopServiceState;
import com.gaicheyunxiu.gaiche.model.ShopState;
import com.gaicheyunxiu.gaiche.utils.CityDBUtils;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.gaicheyunxiu.gaiche.utils.LocalUtils;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.MyListView;
import com.gaicheyunxiu.gaiche.view.RatingBar;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mining.app.zxing.decoding.Intents;

import java.io.File;
import java.io.Serializable;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class OutletDetailActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;
    private TextView textView;
    private RadioGroup group;
    private RadioButton homeRb;
    private RadioButton infoRb;
    private RadioButton serviceRb;
    private RadioButton plaveRb;
    private View homeView;
    private ImageView himage;
    private TextView hname;
    private RatingBar hbar;
    private TextView hdiscussnum;
    private TextView haddress;
    private TextView hdistance;
    private TextView hdetailaddress;
    private View hphoneView;
    private TextView phoneNo;
    private View hmessage;
    private TextView map;
    private View hyangxiu;
    private View hmeirong;
    private TextView hpingjianum;
    private MyListView hlistview;

    private View infoview;
    private WebView webView;

    private List<OuletEvaluaEntity> ouletEvaluaEntities;

    private  View serview;
    private  TextView seryxnum;
    private  TextView sermrnum;
    private  MyListView seryangxiulist;
    private  MyListView sermeironglist;
    private  TextView sermoney;
    private  TextView serkehu;
    private  TextView serok;

    private PullToRefreshListView pinjialist;
    private View pro;

    private OutletDetailAdapter adapter;
    private OutletdetailSerAdapter seryxAdapter;
    private OutletdetailSerAdapter sermrAdapter;
    private OutletdetailpinjiaAdapter pinjiaAdapter;

    private String shopId;//门店id
//    private BDLocation bdLocation = null;
    private BitmapUtils bitmapUtils;
    private List<ShopServiceEntity> yxentities;
    private List<ShopServiceEntity> mrentities;
    private List<ShopEvaluationEntity> evaluationEntities;
    private int pageNo = 1;
    private OutSelDetailEntity outSelDetailEntity;

    private String mSDCardPath = null;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    public static final String SHOW_CUSTOM_ITEM = "showCustomItem";
    public static final String RESET_END_NODE = "resetEndNode";
    public static final String VOID_MODE = "voidMode";
    public static List<Activity> activityList = new LinkedList<Activity>();
    private static final String APP_FOLDER_NAME = "BNSDKSimpleDemo";

    private String type="2";

    private BaiduDialog baiduDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case LocalUtils.LOCAT_OK:
//                    bdLocation = (BDLocation) msg.obj;
//                    getshopDetail();
//                    break;
                case 1001:
                    setMoney();
                    break;

                case 1235:
                    BNOuterLogUtil.setLogSwitcher(true);
                    if (initDirs()) {
                        initNavi();
                    }
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outletdetail);
        initView();
        handler.sendEmptyMessageDelayed(1235,100);
    }

    private void initView() {
        shopId=getIntent().getStringExtra("shopId");
        bitmapUtils=new BitmapUtils(this);
        back= (ImageView) findViewById(R.id.title_back);
        textView= (TextView) findViewById(R.id.title_text);
        group= (RadioGroup) findViewById(R.id.outletdetail_group);
        homeRb= (RadioButton) findViewById(R.id.outletdetail_home);
        infoRb= (RadioButton) findViewById(R.id.outletdetail_info);
        serviceRb= (RadioButton) findViewById(R.id.outletdetail_service);
        plaveRb= (RadioButton) findViewById(R.id.outletdetail_plave);
        homeView= findViewById(R.id.outletdetail_homeview);
        himage= (ImageView) findViewById(R.id.outletdetail_himage);
        hname= (TextView) findViewById(R.id.outletdetail_hname);
        hbar= (RatingBar) findViewById(R.id.outletdetail_hbar);
        hdiscussnum= (TextView) findViewById(R.id.outletdetail_hdiscussnum);
        haddress= (TextView) findViewById(R.id.outletdetail_haddress);
        hdistance= (TextView) findViewById(R.id.outletdetail_hdistance);
        hdetailaddress= (TextView) findViewById(R.id.outletdetail_hdetailaddress);
        hphoneView= findViewById(R.id.outletdetail_hphone);
        phoneNo= (TextView) findViewById(R.id.outletdetail_phoneno);
        hmessage= findViewById(R.id.outletdetail_hmessage);
        map= (TextView) findViewById(R.id.outletdetail_map);
        hyangxiu= findViewById(R.id.outletdetail_hyangxiu);
        hmeirong= findViewById(R.id.outletdetail_hmeirong);
        hpingjianum= (TextView) findViewById(R.id.outletdetail_hpingjianum);
        hlistview= (MyListView) findViewById(R.id.outletdetail_hlistview);

        infoview= findViewById(R.id.outletdetail_infoview);
        webView= (WebView) findViewById(R.id.outletdetail_webview);
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        webView.loadUrl(Constant.ROOT_PATH + "/shop/intro?shopId=" + shopId);


        serview= findViewById(R.id.outletdetail_serview);
        seryxnum= (TextView) findViewById(R.id.outletdetail_seryxnum);
        seryangxiulist= (MyListView) findViewById(R.id.outletdetail_seryangxiulist);
        sermrnum= (TextView) findViewById(R.id.outletdetail_sermrnum);
        sermeironglist= (MyListView) findViewById(R.id.outletdetail_sermeironglist);
        sermoney= (TextView) findViewById(R.id.outletdetail_sermoney);
        serkehu= (TextView) findViewById(R.id.outletdetail_serkehu);
        serkehu.setVisibility(View.INVISIBLE);
        serok= (TextView) findViewById(R.id.outletdetail_serok);
        pinjialist= (PullToRefreshListView) findViewById(R.id.outletdetail_pinjialist);

        ouletEvaluaEntities=new ArrayList<>();
        pro=  findViewById(R.id.outletdetail_pro);
        back.setOnClickListener(this);
        textView.setText("门店");
        group.check(R.id.outletdetail_home);
        adapter=new OutletDetailAdapter(this,ouletEvaluaEntities);
        yxentities=new ArrayList<>();
        mrentities=new ArrayList<>();
        seryxAdapter=new OutletdetailSerAdapter(this,yxentities,handler);
        sermrAdapter=new OutletdetailSerAdapter(this,mrentities,handler);
        evaluationEntities=new ArrayList<>();
        pinjiaAdapter=new OutletdetailpinjiaAdapter(this,evaluationEntities);
        seryangxiulist.setAdapter(seryxAdapter);
        sermeironglist.setAdapter(sermrAdapter);


        pinjialist.setMode(PullToRefreshBase.Mode.BOTH);
        pinjialist.setAdapter(pinjiaAdapter);

        hlistview.setAdapter(adapter);

        serok.setOnClickListener(this);
        hyangxiu.setOnClickListener(this);
        hpingjianum.setOnClickListener(this);
        hmeirong.setOnClickListener(this);
        map.setOnClickListener(this);
        hphoneView.setOnClickListener(this);

        LocalUtils localUtils = new LocalUtils(this, handler);
        localUtils.startLocation();

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.outletdetail_home) {
                    homeView.setVisibility(View.VISIBLE);
                    infoview.setVisibility(View.GONE);
                    serview.setVisibility(View.GONE);
                    pinjialist.setVisibility(View.GONE);

                } else if (checkedId == R.id.outletdetail_info) {
                    homeView.setVisibility(View.GONE);
                    infoview.setVisibility(View.VISIBLE);
                    serview.setVisibility(View.GONE);
                    pinjialist.setVisibility(View.GONE);
                } else if (checkedId == R.id.outletdetail_service) {
                    if (serviceRb.isPressed()==true){
                        type="2";
                    }
                    homeView.setVisibility(View.GONE);
                    infoview.setVisibility(View.GONE);
                    serview.setVisibility(View.VISIBLE);
                    pinjialist.setVisibility(View.GONE);
                    getshopService();
                } else if (checkedId == R.id.outletdetail_plave) {
                    homeView.setVisibility(View.GONE);
                    infoview.setVisibility(View.GONE);
                    serview.setVisibility(View.GONE);
                    pinjialist.setVisibility(View.VISIBLE);
                    getAdShop(1);

                }
            }
        });

        pinjialist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_START)) {
                    getAdShop(1);
                } else if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                    getAdShop(pageNo + 1);
                }

            }

        });

        pinjialist.setVisibility(View.GONE);

        if (ToosUtils.goBrand(OutletDetailActivity.this,0)){
            return;
        }
        getshopDetail();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode!= Activity.RESULT_OK && requestCode==8856 ){
            finish();
            return;
        }

        if (requestCode==8856 && resultCode== Activity.RESULT_OK){
            getshopDetail();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.outletdetail_hyangxiu:
                type="0";
                group.check(R.id.outletdetail_service);
                break;
            case R.id.outletdetail_hmeirong:
                type="1";
                group.check(R.id.outletdetail_service);
                break;

            case R.id.outletdetail_map:
                BNRoutePlanNode sNode = new BNRoutePlanNode(MyApplication.getInstance().getBdLocation().getLongitude(), MyApplication.getInstance().getBdLocation().getLatitude(), "我的位置", null, BNRoutePlanNode.CoordinateType.BD09LL);
                BNRoutePlanNode eNode = new BNRoutePlanNode(Double.valueOf(outSelDetailEntity.longitude), Double.valueOf(outSelDetailEntity.latitude), outSelDetailEntity.name, null, BNRoutePlanNode.CoordinateType.BD09LL);
                if (sNode != null && eNode != null) {
                    List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
                    list.add(sNode);
                    list.add(eNode);
                    baiduDialog=new BaiduDialog(OutletDetailActivity.this);
                    BaiduNaviManager.getInstance().launchNavigator(OutletDetailActivity.this, list, 1, true, new DemoRoutePlanListener(sNode));
                }
                break;
            case R.id.title_back:
                finish();
                break;

            case R.id.outletdetail_hpingjianum:
                group.check(R.id.outletdetail_plave);
                break;
            case R.id.outletdetail_hphone:
                ToosUtils.callPhone(OutletDetailActivity.this,outSelDetailEntity.phone);
                break;
            case R.id.outletdetail_serok:
                if (ToosUtils.isStringEmpty(ShareDataTool.getToken(OutletDetailActivity.this))){
                    ToosUtils.goReLogin(OutletDetailActivity.this);
                    return;
                }
                List<ShopServiceEntity> entityList=new ArrayList<>();
                for (int i=0;i<yxentities.size();i++){
                    if (yxentities.get(i).flag){
                        entityList.add(yxentities.get(i));
                    }
                }
                for (int i=0;i<mrentities.size();i++){
                    if (mrentities.get(i).flag){
                        entityList.add(mrentities.get(i));
                    }
                }
                if (entityList.size()==0){
                    ToastUtils.displayShortToast(OutletDetailActivity.this,"请选择服务项目！");
                    return;
                }
                Intent intent=new Intent(OutletDetailActivity.this,ServicePayActivity.class);
                intent.putExtra("entities", (Serializable) entityList);
                intent.putExtra("flag",1);
                intent.putExtra("shopId",shopId);
                startActivity(intent);
                break;
        }
    }

    /**
     * 查询热门门店
     */
    private void getshopDetail() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("shopId", shopId);
        rp.addBodyParameter("longitude", String.valueOf(MyApplication.getInstance().getBdLocation().getLongitude()));
        rp.addBodyParameter("latitude", String.valueOf(MyApplication.getInstance().getBdLocation().getLatitude()));
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "user/shopDetail", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(OutletDetailActivity.this);
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
                        OutSelDetailState outSelDetailState=gson.fromJson(arg0.result,OutSelDetailState.class);
                        OutSelDetailVo outSelDetailVo=outSelDetailState.result;
                        outSelDetailEntity=outSelDetailVo.shopVo;
                        bitmapUtils.display(himage,outSelDetailEntity.icon);
                        hname.setText(outSelDetailEntity.name);
                        hbar.setStar(Double.parseDouble(outSelDetailEntity.score));
                        hdiscussnum.setText(outSelDetailEntity.evaluateAmount + "条");
                        haddress.setText(outSelDetailEntity.district);
                        hdistance.setText(outSelDetailEntity.distance);
                        hdetailaddress.setText("地址："+outSelDetailEntity.address);
                        phoneNo.setText(outSelDetailEntity.phone);
                        hpingjianum.setText("用户评论（"+outSelDetailEntity.evaluateAmount+"）");
                        if (outSelDetailState.result.evaluation!=null){
                            for (int i=0;i<outSelDetailState.result.evaluation.size();i++){
                                ouletEvaluaEntities.add(outSelDetailState.result.evaluation.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(OutletDetailActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(OutletDetailActivity.this);
                    } else {
                        ToastUtils.displayShortToast(OutletDetailActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(OutletDetailActivity.this);
                }

            }
        });
    }

    /**
     *查询门店服务
     */
    private void getshopService() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("shopId", shopId);
        rp.addBodyParameter("type", type);
        MyCarEntity myCarEntity=MyApplication.getInstance().getCarEntity();
        if (myCarEntity!=null){
            rp.addBodyParameter("carTypeId",myCarEntity.carTypeId);
        }
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "shop/shopService", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(OutletDetailActivity.this);
                pro.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                mrentities.clear();
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        ShopServiceState shopServiceState=gson.fromJson(arg0.result,ShopServiceState.class);
                        mrentities.clear();
                        yxentities.clear();
                        for (int i=0;i<shopServiceState.result.beautyList.size();i++){
                            shopServiceState.result.beautyList.get(i).num=1;
                            shopServiceState.result.beautyList.get(i).flag=false;
                            mrentities.add(shopServiceState.result.beautyList.get(i));
                        }
                        for (int i=0;i<shopServiceState.result.repairList.size();i++){
                            shopServiceState.result.repairList.get(i).num=1;
                            shopServiceState.result.repairList.get(i).flag=false;
                            yxentities.add(shopServiceState.result.repairList.get(i));
                        }
                        sermrAdapter.notifyDataSetChanged();
                        seryxAdapter.notifyDataSetChanged();
                        seryxnum.setText("养修项目（" + yxentities.size() + "）");
                        sermrnum.setText("美容项目（" + mrentities.size() + "）");
                        setMoney();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(OutletDetailActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(OutletDetailActivity.this);
                    } else {
                        ToastUtils.displayShortToast(OutletDetailActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(OutletDetailActivity.this);
                }

            }
        });
    }

    private void setMoney(){
        double d=0;
        for (int i=0;i<yxentities.size();i++){
            if (yxentities.get(i).flag){
                d=d+yxentities.get(i).num*Double.valueOf(yxentities.get(i).price);
            }
        }

        for (int i=0;i<mrentities.size();i++){
            if (mrentities.get(i).flag){
                d=d+mrentities.get(i).num*Double.valueOf(mrentities.get(i).price);
            }
        }
        sermoney.setText("￥" + d);

    }


    /**
     * 获取广告商品
     */
    private void getAdShop(final int page ) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url="shop/shopEvaluation";
        rp.addBodyParameter("shopId",shopId);
        rp.addBodyParameter("pageNo",String.valueOf(page));
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + url, rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pinjialist.onRefreshComplete();
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                pinjialist.onRefreshComplete();
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        ShopEvaluationState evaluationState = gson.fromJson(arg0.result, ShopEvaluationState.class);
                        pageNo = Integer.valueOf(page);
                        if (pageNo == 1) {
                            evaluationEntities.clear();
                            pinjiaAdapter.notifyDataSetChanged();
                        }
                        if (evaluationState.result != null && evaluationState.result.size() > 0) {
                            for (int i = 0; i < evaluationState.result.size(); i++) {
                                evaluationEntities.add(evaluationState.result.get(i));
                            }
                            pinjiaAdapter.notifyDataSetChanged();
                        }
                        LogManager.LogShow("-------", "**********" + evaluationEntities.toString(), LogManager.ERROR);

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(OutletDetailActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(OutletDetailActivity.this);
                    } else {
                        ToastUtils.displayShortToast(OutletDetailActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(OutletDetailActivity.this);
                }

            }
        });
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private void initNavi() {

        BNOuterTTSPlayerCallback ttsCallback = null;

        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {

            }

            public void initSuccess() {
//                Toast.makeText(BNDemoMainActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                initSetting();
            }

            public void initStart() {
//                Toast.makeText(BNDemoMainActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
//                Toast.makeText(BNDemoMainActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }


        },  null, ttsHandler, null);

    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
//                    showToastMsg("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
//                    showToastMsg("Handler : TTS play end");
                    break;
                }
                default :
                    break;
            }
        }
    };


    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
			/*
			 * 设置途径点以及resetEndNode会回调该接口
			 */

            for (Activity ac : activityList) {

                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {

                    return;
                }
            }
            if (baiduDialog!=null){
                baiduDialog.dismiss();
            }
            Intent intent = new Intent(OutletDetailActivity.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            if (baiduDialog!=null){
                baiduDialog.dismiss();
            }
            Toast.makeText(OutletDetailActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initSetting(){
        BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

    private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {

        @Override
        public void stopTTS() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "stopTTS");
        }

        @Override
        public void resumeTTS() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "resumeTTS");
        }

        @Override
        public void releaseTTSPlayer() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "releaseTTSPlayer");
        }

        @Override
        public int playTTSText(String speech, int bPreempt) {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "playTTSText" + "_" + speech + "_" + bPreempt);

            return 1;
        }

        @Override
        public void phoneHangUp() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "phoneHangUp");
        }

        @Override
        public void phoneCalling() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "phoneCalling");
        }

        @Override
        public void pauseTTS() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "pauseTTS");
        }

        @Override
        public void initTTSPlayer() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "initTTSPlayer");
        }

        @Override
        public int getTTSState() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "getTTSState");
            return 1;
        }
    };

}
