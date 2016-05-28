package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.MartaindetailServiceAdapter;
import com.gaicheyunxiu.gaiche.adapter.MartaindetailShopAdapter;
import com.gaicheyunxiu.gaiche.model.MaintainEntity;
import com.gaicheyunxiu.gaiche.model.MartainServiceEntity;
import com.gaicheyunxiu.gaiche.model.MartainServiceState;
import com.gaicheyunxiu.gaiche.model.MartainShopeEntity;
import com.gaicheyunxiu.gaiche.model.MartainShopeState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopOrderEntity;
import com.gaicheyunxiu.gaiche.model.ShopOrderState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mu on 2016/1/18.
 *  保养档案详情
 */
public class MartaindetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private TextView brand;

    private RadioGroup group;

    private RadioButton goodrb;

    private RadioButton servicerb;

    private PullToRefreshListView listView;

    private TextView money;

    private ImageView icon;

    private MartaindetailServiceAdapter serviceAdapter;

    private MartaindetailShopAdapter shopAdapter;

    private MaintainEntity maintainEntity;

    private BitmapUtils bitmapUtils;

    private boolean proFlag = true;

    private int pageNo = 1;

    private List<MartainShopeEntity> shopeEntities;

    private List<MartainServiceEntity> serviceEntities;

    private View pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_martaindetail);
        maintainEntity= (MaintainEntity) getIntent().getSerializableExtra("entity");
        bitmapUtils=new BitmapUtils(this);
        initView();
    }

    private void initView() {
        shopeEntities=new ArrayList<>();
        serviceEntities=new ArrayList<>();
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        icon= (ImageView) findViewById(R.id.martaindetial_image);
        brand = (TextView) findViewById(R.id.martaindetial_car);
        group= (RadioGroup) findViewById(R.id.martaindetial_group);
        goodrb= (RadioButton) findViewById(R.id.martaindetial_good);
        servicerb= (RadioButton) findViewById(R.id.martaindetial_service);
        listView= (PullToRefreshListView) findViewById(R.id.martaindetial_list);
        money= (TextView) findViewById(R.id.martaindetial_money);
        pro= findViewById(R.id.martaindetial_pro);
        bitmapUtils.display(icon, maintainEntity.carBrandLogo);
        brand.setText(maintainEntity.carBrandName+maintainEntity.type+"\u2000"+maintainEntity.displacement+"\u2000"+maintainEntity.productionDate);
        title.setText("保养档案");
        back.setOnClickListener(this);
        serviceAdapter=new MartaindetailServiceAdapter(this,serviceEntities);
        shopAdapter=new MartaindetailShopAdapter(this,shopeEntities);
        listView.setAdapter(shopAdapter);
        group.check(R.id.martaindetial_good);

        money.setText(maintainEntity.commodityTotal);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                closePro();
                if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_START)) {
                    getOrder(1);

                } else if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                    getOrder(pageNo + 1);

                }
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.martaindetial_good) {
                    listView.setAdapter(shopAdapter);
                    openPro();
                    getOrder(1);
                    money.setText(maintainEntity.commodityTotal);
                } else {
                    listView.setAdapter(serviceAdapter);
                    openPro();
                    getOrder(1);
                    money.setText(maintainEntity.serviceTotal);
                }
            }
        });
        getOrder(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }



    private void openPro() {
        proFlag = true;
    }

    private void closePro() {
        proFlag = false;
    }


    /**
     * 查询保养档案
     */
    private void getOrder(final int page) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("carTypeId", maintainEntity.carTypeId);
        if (group.getId()==R.id.martaindetial_good){
            rp.addBodyParameter("type","0");
        }else{
            rp.addBodyParameter("type", "1");
        }
        rp.addBodyParameter("pageNo",String.valueOf(page));
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "archive/query", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                if (proFlag) {
                    pro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(MartaindetailActivity.this);
                listView.onRefreshComplete();
                pro.setVisibility(View.GONE);
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
                        LogManager.LogShow("-----***111", arg0.result,
                                LogManager.ERROR);
                        pageNo = Integer.valueOf(page);
                        if (group.getId()==R.id.martaindetial_good){
                            MartainShopeState shopeState=gson.fromJson(arg0.result,MartainShopeState.class);
                            if (pageNo == 1) {
                                shopeEntities.clear();
                                shopAdapter.notifyDataSetChanged();
                            }
                            for (int i=0;i<shopeState.result.size();i++){
                                shopeEntities.add(shopeState.result.get(i));
                            }
                            shopAdapter.notifyDataSetChanged();
                        }else{
                            MartainServiceState serviceState=gson.fromJson(arg0.result,MartainServiceState.class);
                            if (pageNo == 1) {
                                serviceEntities.clear();
                                serviceAdapter.notifyDataSetChanged();
                            }
                            for (int i=0;i<serviceState.result.size();i++){
                                serviceEntities.add(serviceState.result.get(i));
                            }
                            serviceAdapter.notifyDataSetChanged();
                        }
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(MartaindetailActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(MartaindetailActivity.this);
                    } else {
                        ToastUtils.displayShortToast(MartaindetailActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(MartaindetailActivity.this);
                }

            }
        });
    }


}
