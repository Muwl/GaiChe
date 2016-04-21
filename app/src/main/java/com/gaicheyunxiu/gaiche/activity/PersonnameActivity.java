package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
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
import com.mining.app.zxing.decoding.Intents;

import java.io.File;

import static com.gaicheyunxiu.gaiche.R.id.personname_cancel;
import static com.gaicheyunxiu.gaiche.R.id.title_service;

/**
 * Created by Mu on 2015/12/22.
 * 设置昵称
 */
public class PersonnameActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView save;

    private EditText name;

    private ImageView cancel;

    private String username;

    private View pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personname);
        initView();
    }

    private void initView() {
        username=getIntent().getStringExtra("name");
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        save= (TextView) findViewById(title_service);
        name= (EditText) findViewById(R.id.personname_name);
        cancel= (ImageView) findViewById(R.id.personname_cancel);
        pro=  findViewById(R.id.personname_pro);

        name.setText(username);

        back.setOnClickListener(this);
        title.setText("昵称");
        save.setOnClickListener(this);
        save.setText("保存");
        save.setVisibility(View.VISIBLE);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;

            case  R.id.title_service:
                if (ToosUtils.isTextEmpty(name)){
                    ToastUtils.displayShortToast(PersonnameActivity.this,"昵称不能为空");
                    return;
                }
                UpdateUserInfo();
                break;

            case personname_cancel:
                name.setText("");
                break;
        }
    }


    /**
     * 修改个人信息
     */
    private void UpdateUserInfo() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("nickname", ToosUtils.getTextContent(name));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "user/update", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(PersonnameActivity.this);
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
                        ToastUtils.displayShortToast(PersonnameActivity.this, "保存成功");
                        Intent intent=new Intent();
                        intent.putExtra("name",ToosUtils.getTextContent(name));
                        setResult(RESULT_OK, intent);
                        finish();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(PersonnameActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(PersonnameActivity.this);
                    } else {
                        ToastUtils.displayShortToast(PersonnameActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(PersonnameActivity.this);
                }

            }
        });
    }
}
