package com.gaicheyunxiu.gaiche.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.WalletdetailAdapter;
import com.gaicheyunxiu.gaiche.model.CommodityState;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.TrainingRecordEntity;
import com.gaicheyunxiu.gaiche.model.TrainingRecordState;
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

/**
 * Created by Mu on 2016/1/6.
 */
public class WalletdetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private PullToRefreshListView listView;

    private WalletdetailAdapter adapter;

    private View pro;

    private boolean proFlag = true;

    private int pageNo = 1;

    private List<TrainingRecordEntity> entities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walletdetail);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        listView= (PullToRefreshListView) findViewById(R.id.walletdetail_list);
        pro= findViewById(R.id.walletdetail_pro);

        title.setText("钱包明细");
        back.setOnClickListener(this);
        entities=new ArrayList<>();
        adapter=new WalletdetailAdapter(this,entities);
        listView.setAdapter(adapter);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                closePro();
                if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_START)) {
                    getTrainingDetail(1);
                } else if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                    getTrainingDetail(pageNo + 1);
                }

            }

        });
        getTrainingDetail(1);
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
        }
    }


    /**
     * 钱包交易明细
     */
    private void getTrainingDetail(final int page ) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url="user/trainingDetail";
        rp.addBodyParameter("sign",ShareDataTool.getToken(this));
        rp.addBodyParameter("pageNo",String.valueOf(page));

        LogManager.LogShow("-----", Constant.ROOT_PATH+ url+"?sign="+ShareDataTool.getToken(this)+"&pageNo="+String.valueOf(page),
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
                ToastUtils.displayFailureToast(WalletdetailActivity.this);
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
                        TrainingRecordState trainingRecordState=gson.fromJson(arg0.result,TrainingRecordState.class);
                        pageNo = Integer.valueOf(page);
                        if (pageNo == 1) {
                            entities.clear();
                            adapter.notifyDataSetChanged();
                        }
                        if (trainingRecordState.result != null && trainingRecordState.result.size() > 0) {
                            for (int i = 0; i < trainingRecordState.result.size(); i++) {
                                entities.add(trainingRecordState.result.get(i));
                            }
                            adapter.notifyDataSetChanged();
                        }

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(WalletdetailActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(WalletdetailActivity.this);
                    } else {
                        ToastUtils.displayShortToast(WalletdetailActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(WalletdetailActivity.this);
                }

            }
        });
    }

}
