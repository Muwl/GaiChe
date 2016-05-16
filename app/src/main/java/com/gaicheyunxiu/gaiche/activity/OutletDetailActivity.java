package com.gaicheyunxiu.gaiche.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.FOuletAdapter;
import com.gaicheyunxiu.gaiche.adapter.OutletDetailAdapter;
import com.gaicheyunxiu.gaiche.adapter.OutletdetailSerAdapter;
import com.gaicheyunxiu.gaiche.adapter.OutletdetailpinjiaAdapter;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.OutSelDetailEntity;
import com.gaicheyunxiu.gaiche.model.OutSelDetailState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopState;
import com.gaicheyunxiu.gaiche.utils.CityDBUtils;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.gaicheyunxiu.gaiche.utils.LocalUtils;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.MyListView;
import com.gaicheyunxiu.gaiche.view.RatingBar;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.security.acl.Group;

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
    private ImageView infoimage;
    private  TextView infoname;
    private  RatingBar infobar;
    private  TextView infoscore;
    private  TextView infoContent;
    private  ImageView imagecontent;

    private  View serview;
    private  TextView seryxnum;
    private  TextView sermrnum;
    private  MyListView seryangxiulist;
    private  MyListView sermeironglist;
    private  TextView sermoney;
    private  TextView serkehu;
    private  TextView serok;

    private ListView pinjialist;
    private View pro;

    private OutletDetailAdapter adapter;
    private OutletdetailSerAdapter serAdapter;
    private OutletdetailpinjiaAdapter pinjiaAdapter;

    private String shopId;//门店id
    private BDLocation bdLocation = null;
    private BitmapUtils bitmapUtils;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LocalUtils.LOCAT_OK:
                    bdLocation = (BDLocation) msg.obj;
                    getshopDetail();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outletdetail);
        initView();
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
        infoimage= (ImageView) findViewById(R.id.outletdetail_infoimage);
        infoname= (TextView) findViewById(R.id.outletdetail_infoname);
        infobar= (RatingBar) findViewById(R.id.outletdetail_infobar);
        infoscore= (TextView) findViewById(R.id.outletdetail_infoscore);
        infoContent= (TextView) findViewById(R.id.outletdetail_infocontent);
        imagecontent= (ImageView) findViewById(R.id.outletdetail_imagecontent);


        serview= findViewById(R.id.outletdetail_serview);
        seryxnum= (TextView) findViewById(R.id.outletdetail_seryxnum);
        seryangxiulist= (MyListView) findViewById(R.id.outletdetail_seryangxiulist);
        sermrnum= (TextView) findViewById(R.id.outletdetail_sermrnum);
        sermeironglist= (MyListView) findViewById(R.id.outletdetail_sermeironglist);
        sermoney= (TextView) findViewById(R.id.outletdetail_sermoney);
        serkehu= (TextView) findViewById(R.id.outletdetail_serkehu);
        serok= (TextView) findViewById(R.id.outletdetail_serok);
        pinjialist= (ListView) findViewById(R.id.outletdetail_pinjialist);

        pro=  findViewById(R.id.outletdetail_pro);
        back.setOnClickListener(this);
        textView.setText("门店");
        group.check(R.id.outletdetail_home);
        adapter=new OutletDetailAdapter(this);
        serAdapter=new OutletdetailSerAdapter(this);
        pinjiaAdapter=new OutletdetailpinjiaAdapter(this);
        seryangxiulist.setAdapter(serAdapter);
        sermeironglist.setAdapter(serAdapter);
        pinjialist.setAdapter(pinjiaAdapter);
        hlistview.setAdapter(adapter);

        LocalUtils localUtils = new LocalUtils(this, handler);
        localUtils.startLocation();

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.outletdetail_home){
                    homeView.setVisibility(View.VISIBLE);
                    infoview.setVisibility(View.GONE);
                    serview.setVisibility(View.GONE);
                    pinjialist.setVisibility(View.GONE);

                }else if(checkedId==R.id.outletdetail_info){
                    homeView.setVisibility(View.GONE);
                    infoview.setVisibility(View.VISIBLE);
                    serview.setVisibility(View.GONE);
                    pinjialist.setVisibility(View.GONE);
                    getshopIntro();

                }else if(checkedId==R.id.outletdetail_service){
                    homeView.setVisibility(View.GONE);
                    infoview.setVisibility(View.GONE);
                    serview.setVisibility(View.VISIBLE);
                    pinjialist.setVisibility(View.GONE);

                }else if(checkedId==R.id.outletdetail_plave){
                    homeView.setVisibility(View.GONE);
                    infoview.setVisibility(View.GONE);
                    serview.setVisibility(View.GONE);
                    pinjialist.setVisibility(View.VISIBLE);

                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
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
        rp.addBodyParameter("longitude", String.valueOf(bdLocation.getLongitude()));
        rp.addBodyParameter("latitude", String.valueOf(bdLocation.getLatitude()));
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
                        OutSelDetailEntity outSelDetailEntity=outSelDetailState.result;
                        bitmapUtils.display(himage,outSelDetailEntity.icon);
                        hname.setText(outSelDetailEntity.name);
                        hbar.setStar(Double.parseDouble(outSelDetailEntity.score));
                        hdiscussnum.setText(outSelDetailEntity.evaluateAmount + "条");
                        haddress.setText(outSelDetailEntity.district);
                        hdistance.setText(outSelDetailEntity.distance+"km");
                        hdetailaddress.setText("地址："+outSelDetailEntity.address);
                        phoneNo.setText(outSelDetailEntity.phone);
                        hpingjianum.setText("用户评论（"+outSelDetailEntity.phone+"）");


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
     *查看门店站点简介
     */
    private void getshopIntro() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("shopId", shopId);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "shop/intro", rp, new RequestCallBack<String>() {
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
     *查看门店站点简介
     */
    private void getshopService() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("shopId", shopId);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "shop/intro", rp, new RequestCallBack<String>() {
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
}
