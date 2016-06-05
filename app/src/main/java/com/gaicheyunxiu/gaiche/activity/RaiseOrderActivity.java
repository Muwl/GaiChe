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
import com.gaicheyunxiu.gaiche.adapter.RaiseOrderAdapter;
import com.gaicheyunxiu.gaiche.adapter.ShopOrderAdapter;
import com.gaicheyunxiu.gaiche.dialog.CustomeConDialog;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
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
            switch (msg.what){
                case 1336:
                    //查看物流
                    int groupPoi=msg.arg2;
                    int position=msg.arg1;
                    Intent intent8 = new Intent(RaiseOrderActivity.this, ReqrefundActivity.class);
                    intent8.putExtra("orderId",entities.get(groupPoi).orderId);
                    if ("0".equals(entities.get(groupPoi).split)){
                        intent8.putExtra("commodityId", entities.get(groupPoi).orderListVos.get(position).id);
                        intent8.putExtra("money",Double.valueOf(entities.get(groupPoi).orderListVos.get(position).presentPrice)*Double.valueOf(entities.get(groupPoi).orderListVos.get(position).sales));
                    }else{
                        intent8.putExtra("commodityId", entities.get(groupPoi).vos.get(position).id);
                        intent8.putExtra("money",Double.valueOf(entities.get(groupPoi).orderListVos.get(position).presentPrice)*Double.valueOf(entities.get(groupPoi).orderListVos.get(position).sales));
                    }
                    startActivity(intent8);
                    break;

                case 1339:
                    //钱款去向
                    int groupPoi5=msg.arg2;
                    int position5=msg.arg1;
                    Intent intent9 = new Intent(RaiseOrderActivity.this, AfterSaleActivity.class);
                    intent9.putExtra("flag",1);
                    intent9.putExtra("orderId",entities.get(groupPoi5).orderId);
                    intent9.putExtra("orderNo",entities.get(groupPoi5).orderNo);
                    if ("0".equals(entities.get(groupPoi5).split)){
                        intent9.putExtra("entity", entities.get(groupPoi5).orderListVos.get(position5));
                        intent9.putExtra("money",Double.valueOf(entities.get(groupPoi5).orderListVos.get(position5).presentPrice)*Double.valueOf(entities.get(groupPoi5).orderListVos.get(position5).sales));
                    }else{
                        intent9.putExtra("entity", entities.get(groupPoi5).vos.get(position5));
                        intent9.putExtra("money",Double.valueOf(entities.get(groupPoi5).orderListVos.get(position5).presentPrice)*Double.valueOf(entities.get(groupPoi5).orderListVos.get(position5).sales));
                    }
                    startActivity(intent9);
                    break;
                case 1337:
                    //评价
                    int groupPoi1=msg.arg2;
                    int position1=msg.arg1;

                    Intent intent = new Intent(RaiseOrderActivity.this, OrderEvaluteAvtivity.class);
                    intent.putExtra("orderId",entities.get(groupPoi1).orderId);
                    intent.putExtra("createDate", entities.get(groupPoi1).createDate);
                    if ("0".equals(entities.get(groupPoi1).split)){
                        intent.putExtra("entity", entities.get(groupPoi1).orderListVos.get(position1));
                    }else{
                        intent.putExtra("entity", entities.get(groupPoi1).vos.get(position1));
                    }
                    startActivity(intent);
                    break;
                case 1556:
                    //付款
                    int position3=msg.arg1;
                    Intent intent2=new Intent(RaiseOrderActivity.this,OrderPayActivity.class);
                    intent2.putExtra("flag",2);
                    intent2.putExtra("money",entities.get(position3).price);
                    intent2.putExtra("orderId",entities.get(position3).orderId);
                    startActivity(intent2);
                    break;

                case CustomeDialog.RET_OK:
                    int flag2=msg.arg2;
                    if (flag2==-1){
                        //确定取消订单
//                        int position2=msg.arg1;
//                        cancelOrder(position2);
                    }else if(flag2==-2){
                        //确定删除订单
                        int position2=msg.arg1;
                        delOrder(position2);
                    }

                    break;

                case CustomeConDialog.RET__OK:
                    int flag= (int) msg.obj;
                    if (flag==-1){
                        //确定收货
                        int groupPoi4=msg.arg2;
                        int position4=msg.arg1;
                        confirmOrder(groupPoi4,position4);

                    }else if(flag==-2){
                        //申请退货
                        int groupPoi4=msg.arg2;
                        int position4=msg.arg1;
                        refundOrder(groupPoi4,position4);

                    }

                    break;
            }
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
            rp.addBodyParameter("state",orderState);
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



    /**
     *删除订单
     */
    private void delOrder(final int position) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("orderId",entities.get(position).orderId);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "crowdfundingOrder/del", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(RaiseOrderActivity.this);
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
                        ToastUtils.displayShortToast(RaiseOrderActivity.this,
                                "删除成功！");
                        entities.remove(position);
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
                ToastUtils.displayFailureToast(RaiseOrderActivity.this);
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
                        ToastUtils.displayShortToast(RaiseOrderActivity.this,
                                "退货申请成功！");

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
                + "crowdfundingOrder/confirmReceipt", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(RaiseOrderActivity.this);
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
                        ToastUtils.displayShortToast(RaiseOrderActivity.this,
                                "确认收货成功！");

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
