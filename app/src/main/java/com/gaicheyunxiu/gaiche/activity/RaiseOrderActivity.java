package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.RaiseOrderAdapter;
import com.gaicheyunxiu.gaiche.adapter.ShopOrderAdapter;
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
 * 众筹订单
 */
public class RaiseOrderActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private PullToRefreshListView listView;

    private RaiseOrderAdapter adapter;

    private RadioGroup group;

    private RadioButton all;

    private RadioButton paying;

    private RadioButton state;

    private RadioButton evaluating;

    private View pro;

    private boolean proFlag = true;

    private int pageNo = 1;

    private String orderState="";

    private List<ShopOrderEntity> entities;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raiseorder);
        initView();
    }

    private void initView() {

        title= (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        listView= (PullToRefreshListView) findViewById(R.id.raiseorder_list);
        group= (RadioGroup) findViewById(R.id.raiseorder_group);
        all= (RadioButton) findViewById(R.id.raiseorder_all);
        paying= (RadioButton) findViewById(R.id.raiseorder_paying);
        state= (RadioButton) findViewById(R.id.raiseorder_state);
        evaluating = (RadioButton) findViewById(R.id.raiseorder_evaluating);
        pro= findViewById(R.id.raiseorder_pro);


        title.setText("众筹订单");
        back.setOnClickListener(this);
        entities=new ArrayList<>();
        adapter=new RaiseOrderAdapter(this,entities,handler);
        listView.setAdapter(adapter);
        group.check(R.id.raiseorder_all);

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

        getOrder(1);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.raiseorder_all) {
                    openPro();
                    orderState = "";
                    entities.clear();
                    adapter.notifyDataSetChanged();
                    getOrder(1);
                } else if (checkedId == R.id.raiseorder_paying) {
                    openPro();
                    orderState = "0";
                    entities.clear();
                    adapter.notifyDataSetChanged();
                    getOrder(1);
                } else if (checkedId == R.id.raiseorder_state) {
                    openPro();
                    orderState = "4";
                    entities.clear();
                    adapter.notifyDataSetChanged();
                    getOrder(1);
                } else if (checkedId == R.id.raiseorder_evaluating) {
                    openPro();
                    orderState = "2";
                    entities.clear();
                    adapter.notifyDataSetChanged();
                    getOrder(1);
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
        }
    }

    /**
     * 查询商品订单
     */
    private void getOrder(final int page) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("pageNum", String.valueOf(page));
        if (!ToosUtils.isStringEmpty(orderState)){
            rp.addBodyParameter("orderState",orderState);
        }
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "crowdfundingOrder/findByState", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                if (proFlag) {
                    pro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(RaiseOrderActivity.this);
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
                        ShopOrderState shopOrderState=gson.fromJson(arg0.result,ShopOrderState.class);
                        pageNo = Integer.valueOf(page);
                        if (pageNo == 1) {
                            entities.clear();
                            adapter.notifyDataSetChanged();
                        }
                        for (int i=0;i<shopOrderState.result.size();i++){
                            entities.add(shopOrderState.result.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(RaiseOrderActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(RaiseOrderActivity.this);
                    } else {
                        ToastUtils.displayShortToast(RaiseOrderActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(RaiseOrderActivity.this);
                }

            }
        });
    }
}
