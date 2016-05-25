package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
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
import com.gaicheyunxiu.gaiche.adapter.ServiceOrderAdapter;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.model.CommodityState;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.SerOrderEntity;
import com.gaicheyunxiu.gaiche.model.SerOrderState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
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

import static com.gaicheyunxiu.gaiche.R.id.serviceorder_paying;

/**
 * Created by Administrator on 2016/1/1.
 * 服务订单页面
 */
public class ServiceOrderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private RadioGroup group;

    private RadioButton all;

    private RadioButton paying;

    private RadioButton use;

    private RadioButton evalute;

    private ServiceOrderAdapter adapter;

    private List<SerOrderEntity> entities;

    private PullToRefreshListView listView;

    private boolean proFlag = true;

    private int pageNo = 1;

    private View pro;

    private int type = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CustomeDialog.RET_OK:
                    int cusFlag=msg.arg2;
                    int position=msg.arg1;
                    if (cusFlag==-1){
                        delOrder(position);
                    }else{
                        cancelOrder(position);
                    }
                break;

                case 1445:
                    int groupPoi=msg.arg1;
                    int position1=msg.arg2;
                    Intent intent=new Intent(ServiceOrderActivity.this,ServiceEvaluteActivity.class);
                    intent.putExtra("shopId",entities.get(groupPoi).shopId);
                    intent.putExtra("serviceId",entities.get(groupPoi).service.get(position1).id);
                    intent.putExtra("shopName",entities.get(groupPoi).shopName);
                    intent.putExtra("name",entities.get(groupPoi).service.get(position1).name);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceorder);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.title_back);
        title = (TextView) findViewById(R.id.title_text);
        group = (RadioGroup) findViewById(R.id.serviceorder_group);
        all = (RadioButton) findViewById(R.id.serviceorder_all);
        use = (RadioButton) findViewById(R.id.serviceorder_use);
        paying = (RadioButton) findViewById(R.id.serviceorder_paying);
        evalute = (RadioButton) findViewById(R.id.serviceorder_evaluating);
        listView = (PullToRefreshListView) findViewById(R.id.serviceorder_list);
        pro = findViewById(R.id.serviceorder_pro);

        entities = new ArrayList<>();
        back.setOnClickListener(this);
        group.check(R.id.serviceorder_all);
        title.setText("服务订单");
        adapter = new ServiceOrderAdapter(this, entities, handler);
        listView.setAdapter(adapter);

        getserviceOrder(1);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                closePro();
                if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_START)) {
                    getserviceOrder(1);
                } else if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                    getserviceOrder(pageNo + 1);
                }

            }

        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.serviceorder_all) {
                    openPro();
                    type=0;
                    entities.clear();
                    adapter.notifyDataSetChanged();
                    getserviceOrder(1);
                } else if (checkedId == R.id.serviceorder_use) {
                    openPro();
                    type=2;
                    entities.clear();
                    adapter.notifyDataSetChanged();
                    getserviceOrder(1);
                } else if (checkedId == R.id.serviceorder_paying) {
                    openPro();
                    type=1;
                    entities.clear();
                    adapter.notifyDataSetChanged();
                    getserviceOrder(1);
                } else if (checkedId == R.id.serviceorder_evaluating) {
                    openPro();
                    type=3;
                    entities.clear();
                    adapter.notifyDataSetChanged();
                    getserviceOrder(1);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }


    /**
     * 查询服务订单
     */
    private void getserviceOrder(final int page) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url = "serviceOrder/query";
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("type", String.valueOf(type));
        rp.addBodyParameter("pageNo", String.valueOf(page));
        LogManager.LogShow("-----", Constant.ROOT_PATH+url+"?sign="+ShareDataTool.getToken(this)+"&type="+String.valueOf(type)+"&pageNo="+String.valueOf(page),
                LogManager.ERROR);
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
                ToastUtils.displayFailureToast(ServiceOrderActivity.this);
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
                        SerOrderState serOrderState = gson.fromJson(arg0.result, SerOrderState.class);
                        pageNo = Integer.valueOf(page);
                        if (pageNo == 1) {
                            entities.clear();
                            adapter.notifyDataSetChanged();
                        }
                        if (serOrderState.result != null && serOrderState.result.size() > 0) {
                            for (int i = 0; i < serOrderState.result.size(); i++) {
                                entities.add(serOrderState.result.get(i));
                            }
                            adapter.notifyDataSetChanged();
                        }

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ServiceOrderActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ServiceOrderActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ServiceOrderActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ServiceOrderActivity.this);
                }

            }
        });
    }


    /**
     * 取消服务订单
     */
    private void cancelOrder(final int position) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("orderId",entities.get(position).id);
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "serviceOrder/cancel", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(ServiceOrderActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        ToastUtils.displayShortToast(ServiceOrderActivity.this,
                                "取消成功");
                        entities.get(position).orderState="3";
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ServiceOrderActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ServiceOrderActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ServiceOrderActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ServiceOrderActivity.this);
                }

            }
        });
    }


    /**
     * 取消服务订单
     */
    private void delOrder(final int position) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("orderId",entities.get(position).id);
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "serviceOrder/delete", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(ServiceOrderActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        ToastUtils.displayShortToast(ServiceOrderActivity.this,
                                "删除成功");
                        entities.remove(position);
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ServiceOrderActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ServiceOrderActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ServiceOrderActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ServiceOrderActivity.this);
                }

            }
        });
    }
}
