package com.gaicheyunxiu.gaiche.activity;

import android.graphics.drawable.Drawable;
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
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.OuletSelAdapter;
import com.gaicheyunxiu.gaiche.model.CommodityState;
import com.gaicheyunxiu.gaiche.model.OueSelState;
import com.gaicheyunxiu.gaiche.model.OuletHeadEntity;
import com.gaicheyunxiu.gaiche.model.OutSelEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.gaicheyunxiu.gaiche.utils.LocalUtils;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mu on 2015/12/24.
 * 选择门店页面
 */
public class OultSelActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private View map;

    private RadioGroup group;

    private RadioButton rdefault;

    private RadioButton moods;

    private RadioButton technology;

    private RadioButton price;

    private PullToRefreshListView listView;

    private OuletSelAdapter adapter;

    private int width;

    private boolean proFlag = true;

    private int pageNo = 1;

    private String serviceIds;

    private List<OutSelEntity> entities;

    private String sort;

    private View pro;

    private int flag;//0代表首页进去 1代表从美容进入

    private BDLocation bdLocation = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LocalUtils.LOCAT_OK:
                    bdLocation = (BDLocation) msg.obj;
                    getOulet(1);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouletsel);
        width = DensityUtil.getScreenWidth(this);
        initView();
    }

    private void initView() {
        flag = getIntent().getIntExtra("flag", 0);
        if (flag == 1) {
            serviceIds = getIntent().getStringExtra("ids");
        }
        back = (ImageView) findViewById(R.id.title_back);
        title = (TextView) findViewById(R.id.title_text);
        map = findViewById(R.id.title_map);
        group = (RadioGroup) findViewById(R.id.ouletsel_rb);
        rdefault = (RadioButton) findViewById(R.id.ouletsel_default);
        moods = (RadioButton) findViewById(R.id.ouletsel_moods);
        technology = (RadioButton) findViewById(R.id.ouletsel_technology);
        price = (RadioButton) findViewById(R.id.ouletsel_price);
        listView = (PullToRefreshListView) findViewById(R.id.ouletsel_list);
        pro = findViewById(R.id.ouletsel_pro);

        back.setOnClickListener(this);
        entities = new ArrayList<>();
        title.setText("选择门店");
        map.setVisibility(View.VISIBLE);
        map.setOnClickListener(this);
        adapter = new OuletSelAdapter(this, entities, width);
        listView.setAdapter(adapter);
        group.check(R.id.ouletsel_default);
        back.setOnClickListener(this);
        moods.setOnClickListener(this);
        technology.setOnClickListener(this);
        price.setOnClickListener(this);
        sort = "0";
        LocalUtils localUtils = new LocalUtils(this, handler);
        localUtils.startLocation();


        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.ouletsel_default) {
                    sort = "0";
                    getOulet(1);
                }
            }
        });

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                closePro();
                if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_START)) {
                    getOulet(1);
                } else if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                    getOulet(pageNo + 1);
                }

            }

        });


    }

    private void openPro() {
        proFlag = true;
    }

    private void closePro() {
        proFlag = false;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.ouletsel_moods:
                if ("11".equals(sort)) {
                    sort = "12";
                    Drawable drawable = getResources().getDrawable(R.mipmap.sort_nor);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    moods.setCompoundDrawables(null, null, drawable, null);

                    Drawable drawable2 = getResources().getDrawable(R.mipmap.sort_down);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());//必须设置图片大小，否则不显示
                    moods.setCompoundDrawables(null, null, drawable2, null);

                } else {
                    sort = "11";
                    Drawable drawable = getResources().getDrawable(R.mipmap.sort_nor);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    moods.setCompoundDrawables(null, null, drawable, null);

                    Drawable drawable2 = getResources().getDrawable(R.mipmap.sort_top);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());//必须设置图片大小，否则不显示
                    moods.setCompoundDrawables(null, null, drawable2, null);
                }
                getOulet(1);
                break;

            case R.id.ouletsel_technology:
                if ("21".equals(sort)) {
                    sort = "22";
                    Drawable drawable = getResources().getDrawable(R.mipmap.sort_nor);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    technology.setCompoundDrawables(null, null, drawable, null);

                    Drawable drawable2 = getResources().getDrawable(R.mipmap.sort_down);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());//必须设置图片大小，否则不显示
                    technology.setCompoundDrawables(null, null, drawable2, null);
                } else {
                    sort = "21";

                    Drawable drawable = getResources().getDrawable(R.mipmap.sort_nor);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    technology.setCompoundDrawables(null, null, drawable, null);

                    Drawable drawable2 = getResources().getDrawable(R.mipmap.sort_top);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());//必须设置图片大小，否则不显示
                    technology.setCompoundDrawables(null, null, drawable2, null);
                }
                getOulet(1);
                break;


            case R.id.ouletsel_price:
                if ("31".equals(sort)) {
                    sort = "32";
                    Drawable drawable = getResources().getDrawable(R.mipmap.sort_nor);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    price.setCompoundDrawables(null, null, drawable, null);

                    Drawable drawable2 = getResources().getDrawable(R.mipmap.sort_down);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());//必须设置图片大小，否则不显示
                    price.setCompoundDrawables(null, null, drawable2, null);
                } else {
                    sort = "31";

                    Drawable drawable = getResources().getDrawable(R.mipmap.sort_nor);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    price.setCompoundDrawables(null, null, drawable, null);

                    Drawable drawable2 = getResources().getDrawable(R.mipmap.sort_top);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());//必须设置图片大小，否则不显示
                    price.setCompoundDrawables(null, null, drawable2, null);
                }
                getOulet(1);
                break;
        }

    }


    /**
     * 获取门店
     */
    private void getOulet(final int page) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sort", sort);
        rp.addBodyParameter("carTypeId", "1");
        rp.addBodyParameter("pageNo", pageNo + "");
        rp.addBodyParameter("longitude", String.valueOf(bdLocation.getLongitude()));
        rp.addBodyParameter("latitude", String.valueOf(bdLocation.getLatitude()));
        String url = "service/shop";
        if (flag == 1) {
            rp.addBodyParameter("serviceIds", serviceIds);
            LogManager.LogShow("-----", serviceIds,
                    LogManager.ERROR);
            url = "service/shop";
        }

        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + url, rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                if (proFlag) {
                    pro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(OultSelActivity.this);
                pro.setVisibility(View.GONE);
                listView.onRefreshComplete();
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                listView.onRefreshComplete();
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        OueSelState selState = gson.fromJson(arg0.result, OueSelState.class);
                        pageNo = Integer.valueOf(page);
                        if (pageNo == 1) {
                            entities.clear();
                            adapter.notifyDataSetChanged();
                        }
                        if (selState.result != null && selState.result.size() > 0) {
                            for (int i = 0; i < selState.result.size(); i++) {
                                entities.add(selState.result.get(i));
                            }
                            adapter.notifyDataSetChanged();
                        }

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(OultSelActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(OultSelActivity.this);
                    } else {
                        ToastUtils.displayShortToast(OultSelActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(OultSelActivity.this);
                }

            }
        });
    }
}
