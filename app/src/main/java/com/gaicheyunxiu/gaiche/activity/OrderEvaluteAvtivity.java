package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopOrderVo;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.RatingBar;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2015/12/29.
 * 评价商品订单
 */
public class OrderEvaluteAvtivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private TextView orderNo;

    private TextView time;

    private ImageView imageView;

    private TextView name;

    private TextView num;

    private TextView money;

    private TextView m;

    private EditText evalute;

    private CheckBox cb;

    private RatingBar bar;

    private TextView ok;

    private View pro;

    private String orderId;
    private String createDate;
    private String sorderNo;
    private ShopOrderVo entity;

    private BitmapUtils bitmapUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_evaluate);
        initView();
    }

    private void initView() {
        orderId=getIntent().getStringExtra("orderId");
        createDate=getIntent().getStringExtra("createDate");
        sorderNo=getIntent().getStringExtra("orderNo");
        entity= (ShopOrderVo) getIntent().getSerializableExtra("entity");
        bitmapUtils=new BitmapUtils(this);
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        orderNo= (TextView) findViewById(R.id.order_evaluate_no);
        time= (TextView) findViewById(R.id.order_evaluate_time);
        imageView= (ImageView) findViewById(R.id.order_evaluate_image);
        name= (TextView) findViewById(R.id.order_evaluate_name);
        num= (TextView) findViewById(R.id.order_evaluate_num);
        money= (TextView) findViewById(R.id.order_evaluate_money);
        m= (TextView) findViewById(R.id.order_evaluate_m);
        evalute= (EditText) findViewById(R.id.order_evaluate_text);
        cb= (CheckBox) findViewById(R.id.order_evaluate_cb);
        bar= (RatingBar) findViewById(R.id.order_evaluate_bar);
        ok= (TextView) findViewById(R.id.order_evaluate_ok);
        pro=findViewById(R.id.order_evaluate_pro);

        back.setOnClickListener(this);
        title.setText("评价商品订单");
        ok.setOnClickListener(this);
        orderNo.setText("订单号：" + sorderNo);
        time.setText(createDate);
        bitmapUtils.display(imageView,entity.briefImage);
        name.setText("【"+entity.name+"】"+entity.businessName);
        num.setText(entity.sales);
        money.setText("￥"+entity.presentPrice+"元");
        m.setText(entity.mValue+"M");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.order_evaluate_ok:
                if (ToosUtils.isTextEmpty(evalute)){
                    ToastUtils.displayShortToast(OrderEvaluteAvtivity.this,"请输入评价内容！");
                    return;
                }
                getEvaluate();
                break;
        }
    }

    /**
     * 评价商品
     */
    private void getEvaluate() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("orderId",orderId);
        rp.addBodyParameter("commodityId",entity.id);
        rp.addBodyParameter("score", String.valueOf((int)(bar.getStar())));
        rp.addBodyParameter("evaluation", ToosUtils.getTextContent(evalute));
        if (cb.isChecked()){
            rp.addBodyParameter("anonymous", "1");
        }else{
            rp.addBodyParameter("anonymous", "0");
        }
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "commodityEvaluation/save", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(OrderEvaluteAvtivity.this);
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
                        ToastUtils.displayShortToast(OrderEvaluteAvtivity.this,
                                "评价成功！");
                        finish();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(OrderEvaluteAvtivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(OrderEvaluteAvtivity.this);
                    } else {
                        ToastUtils.displayShortToast(OrderEvaluteAvtivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(OrderEvaluteAvtivity.this);
                }

            }
        });
    }
}
