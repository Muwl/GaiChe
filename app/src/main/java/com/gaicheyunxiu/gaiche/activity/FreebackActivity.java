package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
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

/**
 * Created by Administrator on 2016/2/11.
 */
public class FreebackActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private EditText content;

    private TextView ok;

    private View pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeback);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        content= (EditText) findViewById(R.id.freeback_content);
        ok= (TextView) findViewById(R.id.freeback_ok);
        pro= findViewById(R.id.freeback_pro);

        back.setOnClickListener(this);
        title.setText("意见反馈");
        ok.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;

            case R.id.freeback_ok:
                feedback();
                break;
        }
    }


    /**
     * 查询所有品牌
     */
    private void feedback() {
        if (ToosUtils.isTextEmpty(content)){
            ToastUtils.displayShortToast(this,"反馈内容不能为空！");
            return;
        }
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("content",ToosUtils.getTextContent(content));
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "feedback/add", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(FreebackActivity.this);
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
                        ToastUtils.displayShortToast(FreebackActivity.this,
                                "提交成功");
                        finish();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(FreebackActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(FreebackActivity.this);
                    } else {
                        ToastUtils.displayShortToast(FreebackActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(FreebackActivity.this);
                }

            }
        });
    }
}
