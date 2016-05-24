package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.EarnIncomeEntity;
import com.gaicheyunxiu.gaiche.model.EarnIncomeState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Mu on 2016/1/4.
 * 收益页面
 */
public class EarningActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private View marginlin;

    private TextView margin;

    private View  mlin;

    private TextView m;

    private View pro;

    private EarnIncomeEntity incomeEntity;

    private int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        margin= (TextView) findViewById(R.id.earning_margin);
        marginlin=findViewById(R.id.earning_marginlin);
        mlin=findViewById(R.id.earning_mlin);
        m= (TextView) findViewById(R.id.earning_m);
        pro= findViewById(R.id.earning_pro);

        back.setOnClickListener(this);
        title.setText("收益");
        marginlin.setOnClickListener(this);
        mlin.setOnClickListener(this);

        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
            ToosUtils.goReLogin(this);
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
            if (flag==1){
                finish();
            }
            flag=1;
            return;
        }
        flag=1;
        getIncome();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.earning_marginlin:
                Intent intent=new Intent(EarningActivity.this,MarginActivity.class);
                startActivity(intent);

                break;
            case R.id.earning_mlin:
                Intent intent2=new Intent(EarningActivity.this,MincomeActivity.class);
                startActivity(intent2);

                break;
        }

    }

    /**
     * 查询我的商品保证金和M值总收益
     */
    private void getIncome() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        LogManager.LogShow("-----",  Constant.ROOT_PATH
                        + "earnings/income?sign="+ShareDataTool.getToken(this),
                LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "earnings/income", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(EarningActivity.this);
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
                        EarnIncomeState earnIncomeState = gson.fromJson(arg0.result, EarnIncomeState.class);
                        incomeEntity = earnIncomeState.result;
                        margin.setText("￥" + incomeEntity.equityMoney);
                        m.setText("￥" + incomeEntity.earningsMvalue);
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(EarningActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(EarningActivity.this);
                    } else {
                        ToastUtils.displayShortToast(EarningActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(EarningActivity.this);
                }

            }
        });
    }
}
