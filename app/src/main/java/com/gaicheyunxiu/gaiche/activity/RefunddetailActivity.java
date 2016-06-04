package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.RefunddetailAdapter;
import com.gaicheyunxiu.gaiche.dialog.AreaDialog;
import com.gaicheyunxiu.gaiche.model.QRState;
import com.gaicheyunxiu.gaiche.model.ReFundDetailState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopOrderVo;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.MyListView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2015/12/30.
 * 退款详情页面
 */
public class RefunddetailActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView money;

    private TextView style;

    private TextView tstate;

    private MyListView listView;

    private RefunddetailAdapter adapter;

    private String orderId;

    private ShopOrderVo entity;

    private String orderNo;

    private int flag;//（0：普通商品，1：众筹商品）

    private View pro;

    private TextView help;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refunddetail);

        orderId=getIntent().getStringExtra("orderId");
        entity= (ShopOrderVo) getIntent().getSerializableExtra("entity");
        flag=getIntent().getIntExtra("flag",0);

        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        money= (TextView) findViewById(R.id.refunddetail_money);
        style= (TextView) findViewById(R.id.refunddetail_style);
        tstate= (TextView) findViewById(R.id.refunddetail_time);
        help= (TextView) findViewById(R.id.refunddetail_help);
        listView= (MyListView) findViewById(R.id.refunddetail_list);
        pro= findViewById(R.id.refunddetail_pro);
        title.setText("退款详情");
        back.setOnClickListener(this);
        help.setOnClickListener(this);

        getrefundProgress();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.refunddetail_help:
                Intent intent=new Intent(RefunddetailActivity.this,FundInfoActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * 钱款去向
     */
    private void getrefundProgress() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("type", String.valueOf(flag));
        rp.addBodyParameter("orderId", orderId);
        rp.addBodyParameter("commodityId", entity.id);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "refundProgress/query", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(RefunddetailActivity.this);
                pro.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                try {
                    pro.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        ReFundDetailState detailState=gson.fromJson(arg0.result,ReFundDetailState.class);
                        money.setText(detailState.result.money);
                        if ("0".equals(detailState.result.account)){
                            style.setText("钱包");
                        }else  if ("1".equals(detailState.result.account)){
                            style.setText("支付宝");
                        }else  if ("2".equals(detailState.result.account)){
                            style.setText("微信");
                        }else  if ("3".equals(detailState.result.account)){
                            style.setText("银联");
                        }
                        adapter=new RefunddetailAdapter(RefunddetailActivity.this,detailState.result.detail);
                        listView.setAdapter(adapter);
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(RefunddetailActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(RefunddetailActivity.this);
                    } else {
                        ToastUtils.displayShortToast(RefunddetailActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(RefunddetailActivity.this);
                }

            }
        });
    }
}
