package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
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
import com.gaicheyunxiu.gaiche.view.RatingBar;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import static com.gaicheyunxiu.gaiche.R.id.service_evaluate_cb;

/**
 * Created by Administrator on 2016/1/1.
 * 评价服务订单
 */
public class ServiceEvaluteActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private TextView store;

    private TextView name;

    private RatingBar bar1;

    private RatingBar bar2;

    private RatingBar bar3;

    private RatingBar bar4;

    private EditText content;

    private CheckBox cb;

    private RatingBar bar5;

    private TextView commite;

    private String shopId;

    private String serviceId;

    private String shopName;

    private String serviceName;

    private View pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_evalute);
        shopId=getIntent().getStringExtra("shopId");
        serviceId=getIntent().getStringExtra("serviceId");
        shopName=getIntent().getStringExtra("shopName");
        serviceName=getIntent().getStringExtra("name");
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        store= (TextView) findViewById(R.id.service_evalute_store);
        name= (TextView) findViewById(R.id.service_evalute_name);
        bar1= (RatingBar) findViewById(R.id.service_evalute_bar1);
        bar2= (RatingBar) findViewById(R.id.service_evalute_bar2);
        bar3= (RatingBar) findViewById(R.id.service_evalute_bar3);
        bar4= (RatingBar) findViewById(R.id.service_evalute_bar4);
        content= (EditText) findViewById(R.id.service_evaluate_text);
        cb= (CheckBox) findViewById(service_evaluate_cb);
        bar5= (RatingBar) findViewById(R.id.service_evaluate_bar);
        commite= (TextView) findViewById(R.id.service_evaluate_ok);
        pro=  findViewById(R.id.service_evaluate_pro);

        store.setText(shopName);
        name.setText(serviceName);
        back.setOnClickListener(this);
        title.setText("评价服务订单");
        commite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case  R.id.service_evaluate_ok:
                if (ToosUtils.isTextEmpty(content)){
                    ToastUtils.displayShortToast(ServiceEvaluteActivity.this,"请输入评价内容！");
                    return;
                }
                getEvaluate();
                break;

        }
    }

    /**
     * 取消服务订单
     */
    private void getEvaluate() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("shopId",shopId);
        rp.addBodyParameter("serviceId",serviceId);
        rp.addBodyParameter("serviceScore", String.valueOf(bar1.getStar()));
        rp.addBodyParameter("technologyScore", String.valueOf(bar2.getStar()));
        rp.addBodyParameter("environmentScore", String.valueOf(bar3.getStar()));
        rp.addBodyParameter("content", ToosUtils.getTextContent(content));
        if (cb.isChecked()){
            rp.addBodyParameter("anonymous", "1");
        }else{
            rp.addBodyParameter("anonymous", "0");
        }
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "serviceOrder/evaluate", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(ServiceEvaluteActivity.this);
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
                        ToastUtils.displayShortToast(ServiceEvaluteActivity.this,
                                "评价成功！");
                        finish();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ServiceEvaluteActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ServiceEvaluteActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ServiceEvaluteActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ServiceEvaluteActivity.this);
                }

            }
        });
    }
}
