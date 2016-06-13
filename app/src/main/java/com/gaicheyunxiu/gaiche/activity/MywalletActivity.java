package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.MywalletAdapter;
import com.gaicheyunxiu.gaiche.dialog.BrandDelDialog;
import com.gaicheyunxiu.gaiche.model.AddAdressState;
import com.gaicheyunxiu.gaiche.model.BankCardEntity;
import com.gaicheyunxiu.gaiche.model.BrandCatyState;
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
 * Created by Mu on 2016/1/6.
 * 我的钱包页面
 */
public class MywalletActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private View walletlin;

    private TextView money;

    private MyListView listView;

    private View addBrandlin;

    private ImageView div;

    private MywalletAdapter adapter;

    private View pro;

    private List<BankCardEntity> entities;

    private MyWalletState walletState;




    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 89:
                    int position = msg.arg1;
                    delBankCard(position);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);
        initView();
    }

    private void initView() {

        back = (ImageView) findViewById(R.id.title_back);
        title = (TextView) findViewById(R.id.title_text);
        walletlin = findViewById(R.id.mywallet_wallet);
        money = (TextView) findViewById(R.id.mywallet_money);
        listView = (MyListView) findViewById(R.id.mywallet_list);
        div = (ImageView) findViewById(R.id.mywallet_div);
        addBrandlin = findViewById(R.id.mywallet_addbrandlin);
        pro = findViewById(R.id.mywallet_pro);

        back.setOnClickListener(this);
        title.setText("我的钱包");
        walletlin.setOnClickListener(this);
        addBrandlin.setOnClickListener(this);

        entities = new ArrayList<>();
        adapter = new MywalletAdapter(this, entities);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                BrandDelDialog dialog = new BrandDelDialog(MywalletActivity.this, handler, i);
                return true;
            }
        });

        getDefaultAddress();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.mywallet_addbrandlin:
                Intent intent1 = new Intent(MywalletActivity.this, AddbrandcheckActivity.class);
                startActivityForResult(intent1, 1338);
                break;
            case R.id.mywallet_wallet:
                if (walletState == null) {
                    return;
                }
                Intent intent = new Intent(MywalletActivity.this, WalletActivity.class);
                intent.putExtra("money", walletState.result.balance);
                if (entities.size()==0){
                    intent.putExtra("flag", 1);
                }
                startActivity(intent);
                break;
        }
    }


    /**
     * 获取钱包余额和银行卡信息
     */
    private void getDefaultAddress() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url = "user/wallet";
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
                ToastUtils.displayFailureToast(MywalletActivity.this);
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
                        walletState = gson.fromJson(arg0.result, MyWalletState.class);
                        money.setText("￥" + walletState.result.balance);
                        for (int i = 0; i < walletState.result.bankCards.size(); i++) {
                            entities.add(walletState.result.bankCards.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.INFO_NOCOM.equals(state.msg)) {
                        WalletNoComState noComState = gson.fromJson(arg0.result, WalletNoComState.class);
                        if (ToosUtils.isStringEmpty(noComState.result.isHaveMobile) || "0".equals(noComState.result.isHaveMobile)) {
                            Intent intent = new Intent(MywalletActivity.this, UpdatePhoneActivity.class);
                            intent.putExtra("flag", 1);
                            startActivityForResult(intent, 1336);
                            ToastUtils.displayShortToast(MywalletActivity.this, "请先绑定手机号！");
                            return;
                        }

                        if (ToosUtils.isStringEmpty(noComState.result.isHavePayPwd) || "0".equals(noComState.result.isHavePayPwd)) {
                            Intent intent = new Intent(MywalletActivity.this, PaymentPwdActivity.class);
                            startActivityForResult(intent, 1337);
                            ToastUtils.displayShortToast(MywalletActivity.this, "请先设置支付密码！");
                            return;
                        }


                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(MywalletActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(MywalletActivity.this);
                    } else {
                        ToastUtils.displayShortToast(MywalletActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(MywalletActivity.this);
                }

            }
        });
    }


    /**
     * 删除银行卡
     */
    private void delBankCard(final int position) {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("bankCardId", entities.get(position).id);
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "user/delBankCard", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(MywalletActivity.this);
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
                        ToastUtils.displayShortToast(MywalletActivity.this, "删除成功");
                        entities.remove(position);
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(MywalletActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(MywalletActivity.this);
                    } else {
                        ToastUtils.displayShortToast(MywalletActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(MywalletActivity.this);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1336) {
            getDefaultAddress();
        } else if (requestCode == 1337) {
            getDefaultAddress();
        } else if (requestCode == 1338) {
            getDefaultAddress();
        }

    }
}
