package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ShopOrderAdapter;
import com.gaicheyunxiu.gaiche.model.CrowCommityState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopOrderEntity;
import com.gaicheyunxiu.gaiche.model.ShopOrderState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
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
 * Created by Administrator on 2015/12/29.
 * 商品订单页面
 */
public class ShopOrderActivity extends  BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private ListView listView;

    private ShopOrderAdapter adapter;

    private RadioGroup group;

    private RadioButton all;

    private RadioButton paying;

    private RadioButton receiving;

    private RadioButton evaluating;

    private View pro;

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
        setContentView(R.layout.activity_shoporder);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        listView= (ListView) findViewById(R.id.shoporder_list);
        group= (RadioGroup) findViewById(R.id.shoporder_group);
        all= (RadioButton) findViewById(R.id.shoporder_all);
        paying= (RadioButton) findViewById(R.id.shoporder_paying);
        receiving= (RadioButton) findViewById(R.id.shoporder_receiving);
        evaluating= (RadioButton) findViewById(R.id.shoporder_evaluating);
        pro= findViewById(R.id.shoporder_pro);

        title.setText("商品订单");
        group.check(R.id.shoporder_all);
        back.setOnClickListener(this);
        entities=new ArrayList<>();
        adapter=new ShopOrderAdapter(this,entities,handler);
        listView.setAdapter(adapter);
        group.check(R.id.shoporder_all);

        getOrder();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.shoporder_all) {
                    orderState="";
                    entities.clear();
                    adapter.notifyDataSetChanged();
                    getOrder();
                } else if (checkedId == R.id.shoporder_paying) {
                    orderState="0";
                    entities.clear();
                    adapter.notifyDataSetChanged();
                    getOrder();
                } else if (checkedId == R.id.shoporder_receiving) {
                    orderState="2";
                    entities.clear();
                    adapter.notifyDataSetChanged();
                    getOrder();
                } else if (checkedId == R.id.shoporder_evaluating) {
                    orderState="4";
                    entities.clear();
                    adapter.notifyDataSetChanged();
                    getOrder();
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
     * 查询商品订单
     */
    private void getOrder() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        if (!ToosUtils.isStringEmpty(orderState)){
            rp.addBodyParameter("orderState",orderState);
        }
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "commodityOrder/find", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(ShopOrderActivity.this);
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
                        LogManager.LogShow("-----***111", arg0.result,
                                LogManager.ERROR);
                        entities.clear();
                        ShopOrderState shopOrderState=gson.fromJson(arg0.result,ShopOrderState.class);
                        for (int i=0;i<shopOrderState.result.size();i++){
                            entities.add(shopOrderState.result.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ShopOrderActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ShopOrderActivity.this);
                }

            }
        });
    }


    /**
     *取消订单
     */
    private void cancelOrder(final int position) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("orderId",entities.get(position).orderId);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "commodityOrder/cancelOrder", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(ShopOrderActivity.this);
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
                        LogManager.LogShow("-----***111", arg0.result,
                                LogManager.ERROR);
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                "取消成功！");
                        entities.remove(position);
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ShopOrderActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ShopOrderActivity.this);
                }

            }
        });
    }


    /**
     *取消订单
     */
    private void delOrder(final int position) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("orderId",entities.get(position).orderId);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "commodityOrder/del", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(ShopOrderActivity.this);
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
                        LogManager.LogShow("-----***111", arg0.result,
                                LogManager.ERROR);
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                "删除成功！");
                        entities.remove(position);
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ShopOrderActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ShopOrderActivity.this);
                }

            }
        });
    }


    /**
     *申请退货
     */
    private void refundOrder(final int position,int poi) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("orderId",entities.get(position).orderId);
        if ("0".equals(entities.get(position).split)){
            rp.addBodyParameter("commodityId",entities.get(position).orderListVos.get(poi).id);
        } else {
            rp.addBodyParameter("commodityId",entities.get(position).vos.get(poi).id);
        }

        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "commodityOrder/applyRefund", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(ShopOrderActivity.this);
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
                        LogManager.LogShow("-----***111", arg0.result,
                                LogManager.ERROR);
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                "退货申请成功！");

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ShopOrderActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ShopOrderActivity.this);
                }

            }
        });
    }

    /**
     *商品确认收货
     */
    private void confirmOrder(final int position,int poi) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("orderId",entities.get(position).orderId);
        if ("0".equals(entities.get(position).split)){
            rp.addBodyParameter("commodityId",entities.get(position).orderListVos.get(poi).id);
        } else {
            rp.addBodyParameter("commodityId",entities.get(position).vos.get(poi).id);
        }

        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "commodityOrder/confirmReceipt", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(ShopOrderActivity.this);
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
                        LogManager.LogShow("-----***111", arg0.result,
                                LogManager.ERROR);
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                "确认收货成功！");

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ShopOrderActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ShopOrderActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ShopOrderActivity.this);
                }

            }
        });
    }
}
