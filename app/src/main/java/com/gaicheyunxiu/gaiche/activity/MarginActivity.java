package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.MarginAdapter;
import com.gaicheyunxiu.gaiche.model.CommodityState;
import com.gaicheyunxiu.gaiche.model.EarnIncomeEntity;
import com.gaicheyunxiu.gaiche.model.EarningsEntity;
import com.gaicheyunxiu.gaiche.model.EarningsState;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.MyGridView;
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
 * Created by Mu on 2016/1/6.
 * 商品保证金页面
 */
public class MarginActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView no;

    private TextView wallet;

    private TextView m;

    private TextView money;

    private MarginAdapter adapter;

    private List<EarningsEntity> earningsEntities;

    private View pro;

    private PullToRefreshListView listView;

    private boolean proFlag = true;

    private int pageNo = 1;

    private EarnIncomeEntity incomeEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_margin);
        initView();
    }

    private void initView() {
        incomeEntity= (EarnIncomeEntity) getIntent().getSerializableExtra("entity");
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        no= (TextView) findViewById(R.id.margin_no);
        wallet= (TextView) findViewById(R.id.margin_wallet);
        listView= (PullToRefreshListView) findViewById(R.id.margin_list);
        m= (TextView) findViewById(R.id.margin_m);
        pro= findViewById(R.id.margin_pro);
        money= (TextView) findViewById(R.id.margin_money);
        back.setOnClickListener(this);
        title.setText("充值卡");
        back.setOnClickListener(this);
        wallet.setOnClickListener(this);
        earningsEntities=new ArrayList<>();
        adapter=new MarginAdapter(this,earningsEntities);
        listView.setAdapter(adapter);

        no.setText(ShareDataTool.getRegiterEntity(this).gcCode);
        m.setText(incomeEntity.equityMvalue+"M");
        m.setText(incomeEntity.equityMoney);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                closePro();
                if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_START)) {
                    getEarnings(1);
                } else if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                    getEarnings(pageNo + 1);
                }

            }

        });
        getEarnings(1);
    }

    private void openPro(){
        proFlag=true;
    }

    private void closePro(){
        proFlag=false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.margin_wallet:
                Intent intent=new Intent(MarginActivity.this,DepwalletActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                break;
        }
    }

    /**
     *商品保证金收益明细
     */
    private void getEarnings(final int page) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("pageNo", String.valueOf(page));
        String url="earnings/equityEarnings";
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
                ToastUtils.displayFailureToast(MarginActivity.this);
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
                        EarningsState earningsState = gson.fromJson(arg0.result, EarningsState.class);
                        pageNo = Integer.valueOf(page);
                        if (pageNo == 1) {
                            earningsEntities.clear();
                            adapter.notifyDataSetChanged();
                        }
                        if (earningsState.result != null && earningsState.result.size() > 0) {
                            for (int i = 0; i < earningsState.result.size(); i++) {
                                earningsEntities.add(earningsState.result.get(i));
                            }
                            adapter.notifyDataSetChanged();
                        }

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(MarginActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(MarginActivity.this);
                    } else {
                        ToastUtils.displayShortToast(MarginActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(MarginActivity.this);
                }

            }
        });
    }



}
