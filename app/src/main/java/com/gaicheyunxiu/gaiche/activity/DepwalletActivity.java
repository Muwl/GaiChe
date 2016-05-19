package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.EarnIncomeState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
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

/**
 * Created by Mu on 2016/1/6.
 * 存入钱包
 */
public class DepwalletActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private EditText money;

    private TextView ok;

    private View pro;

    private int flag;//1代表保证金  2 代表M值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depwallet);
        flag=getIntent().getIntExtra("flag",0);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        money= (EditText) findViewById(R.id.depwallet_money);
        ok= (TextView) findViewById(R.id.depwallet_ok);
        pro= findViewById(R.id.depwallet_pro);

        back.setOnClickListener(this);
        title.setText("存入钱包");
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.depwallet_ok:
                if (ToosUtils.isTextEmpty(money)){
                    ToastUtils.displayShortToast(DepwalletActivity.this,"请输入金额！");
                    return;
                }
                getSaveToWallet();
                break;
        }

    }

    /**
     * 查询我的商品保证金和M值总收益
     */
    private void getSaveToWallet() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("money", ToosUtils.getTextContent(money));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url="earnings/mvalueSaveToWallet";
        if (flag==1){
            url="earnings/equitySaveToWallet";
        }else{
            url="earnings/mvalueSaveToWallet";
        }
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + url, rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(DepwalletActivity.this);
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
                        ToastUtils.displayShortToast(DepwalletActivity.this,
                                "提交成功！");
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(DepwalletActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(DepwalletActivity.this);
                    } else {
                        ToastUtils.displayShortToast(DepwalletActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(DepwalletActivity.this);
                }

            }
        });
    }
}
