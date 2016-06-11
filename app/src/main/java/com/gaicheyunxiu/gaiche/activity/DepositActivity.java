package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.DepositAdapter;
import com.gaicheyunxiu.gaiche.model.BankCardEntity;
import com.gaicheyunxiu.gaiche.model.MyWalletState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.WalletNoComState;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mu on 2016/1/7.
 * 提现页面
 */
public class DepositActivity  extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private MyListView listView;

    private EditText money;

    private TextView ok;

    private DepositAdapter adapter;

    private List<BankCardEntity> entities;

    private String smoney;

    private View pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        smoney=getIntent().getStringExtra("money");
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        listView= (MyListView) findViewById(R.id.deposit_list);
        money= (EditText) findViewById(R.id.deposit_money);
        ok= (TextView) findViewById(R.id.deposit_ok);
        pro=  findViewById(R.id.deposit_pro);
        back.setOnClickListener(this);
        title.setText("提现");
        ok.setOnClickListener(this);
        entities=new ArrayList<>();

        adapter=new DepositAdapter(this,entities);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < entities.size(); i++) {
                    if (i == position) {
                        entities.get(position).isCheck = true;
                    } else {
                        entities.get(i).isCheck = false;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        money.setHint("当前收益余额" + smoney + "元");
        getDefaultAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.deposit_ok:
                if (entities==null){
                    ToastUtils.displayShortToast(DepositActivity.this,"请添加银行卡！");
                    return;
                }
                if (ToosUtils.isTextEmpty(money)){
                    ToastUtils.displayShortToast(DepositActivity.this,"请填写提现金额！");
                    return;
                }
                if (Double.valueOf(smoney)<Double.valueOf(ToosUtils.getTextContent(money))){
                    ToastUtils.displayShortToast(DepositActivity.this,"提现金额不能大于余额！");
                    return;
                }
                boolean flag=false;
                for (int i=0;i<entities.size();i++){
                    if (entities.get(i).isCheck){
                        flag=true;
                    }
                }
                if (!flag){
                    ToastUtils.displayShortToast(DepositActivity.this,"请选择银行卡！");
                    return;
                }
                getapplyWithdraw();

                break;
        }
    }

    /**
     *获取钱包余额和银行卡信息
     */
    private void getDefaultAddress() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url="user/wallet";
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + url, rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(DepositActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                LogManager.LogShow("-----", arg0.result,
                        LogManager.ERROR);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        entities.clear();
                        adapter.notifyDataSetChanged();
                        MyWalletState walletState = gson.fromJson(arg0.result, MyWalletState.class);
                        for (int i = 0; i < walletState.result.bankCards.size(); i++) {
                            walletState.result.bankCards.get(i).isCheck = false;
                            entities.add(walletState.result.bankCards.get(i));
                        }
                        adapter.notifyDataSetChanged();

                        if (entities.size() == 0) {
                            ToastUtils.displayShortToast(DepositActivity.this,"请添加银行卡！");
                            Intent intent = new Intent(DepositActivity.this, AddbrandActivity.class);
                            startActivityForResult(intent, 1552);
                        }
                    } else if (Constant.INFO_NOCOM.equals(state.msg)) {

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(DepositActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(DepositActivity.this);
                    } else {
                        ToastUtils.displayShortToast(DepositActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(DepositActivity.this);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1552 && resultCode==RESULT_OK){
            getDefaultAddress();
        }
    }

    /**
     *提现申请
     */
    private void getapplyWithdraw() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url="user/applyWithdraw";
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("money", ToosUtils.getTextContent(money));
        for (int i=0;i<entities.size();i++){
            if (entities.get(i).isCheck){
                rp.addBodyParameter("bankCardId", entities.get(i).id);
            }
        }
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + url, rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(DepositActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                LogManager.LogShow("-----", arg0.result,
                        LogManager.ERROR);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        ToastUtils.displayShortToast(DepositActivity.this,
                                "提现成功！");
                        finish();
                    } else if (Constant.INFO_NOCOM.equals(state.msg)) {

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(DepositActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(DepositActivity.this);
                    } else {
                        ToastUtils.displayShortToast(DepositActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(DepositActivity.this);
                }

            }
        });
    }



}
