package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
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
 * Created by Mu on 2016/1/7.
 * 添加银行卡
 */
public class AddbrandActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private EditText brandName;

    private EditText brandNo;

    private EditText name;

    private EditText idno;

    private EditText phone;

    private CheckBox cb;

    private TextView ok;

    private View pro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbrand);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        brandName= (EditText) findViewById(R.id.addbrand_brandname);
        brandNo= (EditText) findViewById(R.id.addbrand_brandno);
        name= (EditText) findViewById(R.id.addbrand_name);
        idno= (EditText) findViewById(R.id.addbrand_certno);
        phone= (EditText) findViewById(R.id.addbrand_phone);
        cb= (CheckBox) findViewById(R.id.addbrand_cb);
        ok = (TextView) findViewById(R.id.addbrand_ok);
        pro =  findViewById(R.id.addbrand_pro);

        title.setText("添加银行卡");
        back.setOnClickListener(this);
        ok.setOnClickListener(this);
        cb.setText(Html.fromHtml("同意<font color=\"#566892\">《用户协议》</font>"));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.addbrand_ok:
                if(checkInput()){
                    addBankCard();
                }
                break;
        }
    }

    public boolean checkInput(){
        if (ToosUtils.isTextEmpty(brandName)){
            ToastUtils.displayShortToast(this,"发卡银行不能为空！");
            return  false;
        }

        if (ToosUtils.isTextEmpty(brandNo)){
            ToastUtils.displayShortToast(this,"银行卡号不能为空！");
            return  false;
        }

        if (ToosUtils.isTextEmpty(name)){
            ToastUtils.displayShortToast(this,"姓名不能为空！");
            return  false;
        }

        if (ToosUtils.isTextEmpty(idno)){
            ToastUtils.displayShortToast(this,"证件号不能为空！");
            return  false;
        }

        if (ToosUtils.isTextEmpty(phone)){
            ToastUtils.displayShortToast(this,"手机号不能为空！");
            return  false;
        }
        return true;
    }

    /**
     * 添加银行卡
     */
    private void addBankCard() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("type", "0");
        rp.addBodyParameter("bank", ToosUtils.getTextContent(brandName));
        rp.addBodyParameter("bankCardNo", ToosUtils.getTextContent(brandNo));
        rp.addBodyParameter("name", ToosUtils.getTextContent(name));
        rp.addBodyParameter("idNo", ToosUtils.getTextContent(idno));
        rp.addBodyParameter("mobile", ToosUtils.getTextContent(phone));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "user/addBankCard", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(AddbrandActivity.this);
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
                        ToastUtils.displayShortToast(AddbrandActivity.this, "添加成功");
                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        finish();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(AddbrandActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(AddbrandActivity.this);
                    } else {
                        ToastUtils.displayShortToast(AddbrandActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(AddbrandActivity.this);
                }

            }
        });
    }
}
