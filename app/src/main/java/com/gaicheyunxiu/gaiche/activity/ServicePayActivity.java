package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ServiceOrderAdapter;
import com.gaicheyunxiu.gaiche.dialog.PaymentDialog;
import com.gaicheyunxiu.gaiche.model.AddAdressState;
import com.gaicheyunxiu.gaiche.model.AddressVo;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.OutSelEntity;
import com.gaicheyunxiu.gaiche.model.PayResult;
import com.gaicheyunxiu.gaiche.model.PayState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.SerOrderEntity;
import com.gaicheyunxiu.gaiche.model.ServiceOrderUp;
import com.gaicheyunxiu.gaiche.model.ServiceOrderVo;
import com.gaicheyunxiu.gaiche.model.ShopEvaluationState;
import com.gaicheyunxiu.gaiche.model.ShopServiceEntity;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
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
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/2.
 */
public class ServicePayActivity extends  BaseActivity implements View.OnClickListener {
    private ImageView back;

    private TextView title;

    private TextView person;

    private TextView phone;

    private View lin;

    private TextView num;

    private View wallet;

    private CheckBox walletcb;

    private View zhifubao;

    private CheckBox zhifubaocb;

    private View weixin;

    private CheckBox weixincb;

    private View yinlian;

    private CheckBox yinliancb;

    private TextView money;

    private TextView submit;

    private View pro;

    private int checkIndex=0;//0钱包 1支付宝 2 微信 3银联

    private int flag;//1门店详情进入  2从服务订单付款

    private String shopId;

    private List<ShopServiceEntity> shopServiceEntityList;

    private AddressVo addressVo;

    private double totalMoney;

    private SerOrderEntity serOrderEntity;

    private View lin1;

    private ImageView div1;

    private ImageView div2;

    public static final int SDK_PAY_FLAG = 4462;

    private IWXAPI api;

//    String strUrl=Constant.ROOT_PATH+ url+"?sign="+ ShareDataTool.getToken(context)+"&paySign="+payEntity.paySign+"&payType="+payEntity.payType +"&payPwd="+ToosUtils.getEncrypt(ToosUtils.getTextContent(pwd) + ToosUtils.getEncryptto(payEntity.content);
private Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){

            case SDK_PAY_FLAG:
                PayResult payResult = new PayResult((String) msg.obj);
                /**
                 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                 * docType=1) 建议商户依赖异步通知
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    Intent intent1=new Intent(ServicePayActivity.this, PaySuccessActivity.class);
                    intent1.putExtra("money",totalMoney);
                    startActivity(intent1);
                } else {
                    // 判断resultStatus 为非"9000"则代表可能支付失败
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        Toast.makeText(ServicePayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        Toast.makeText(ServicePayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                    }
                }
                break;
        }
    }
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicepay);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        api.registerApp(Constant.APP_ID);
        initView();
    }

    private void initView() {
        flag=getIntent().getIntExtra("flag",0);
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        wallet=findViewById(R.id.servicepay_wallet);
        person= (TextView) findViewById(R.id.service_pay_person);
        phone= (TextView) findViewById(R.id.service_pay_phone);
        lin1=findViewById(R.id.service_pay_lin1);
        num= (TextView) findViewById(R.id.servicepay_num);
        walletcb= (CheckBox) findViewById(R.id.servicepay_wallet_cb);
        zhifubao=findViewById(R.id.servicepay_zhifubao);
        zhifubaocb= (CheckBox) findViewById(R.id.servicepay_zhifubao_cb);
        weixin=findViewById(R.id.servicepay_weixin);
        weixincb= (CheckBox) findViewById(R.id.servicepay_weixin_cb);
        yinlian=findViewById(R.id.servicepay_yinlian);
        yinliancb= (CheckBox) findViewById(R.id.servicepay_yinlian_cb);
        money= (TextView) findViewById(R.id.servicepay_money);
        submit= (TextView) findViewById(R.id.servicepay_ok);
        pro= findViewById(R.id.servicepay_pro);
        div1= (ImageView) findViewById(R.id.service_pay_div1);
        div2= (ImageView) findViewById(R.id.service_pay_div2);
        lin= findViewById(R.id.servicepay_lin);

        phone.setEnabled(false);
        person.setEnabled(false);
        title.setText("去结算");
        lin1.setOnClickListener(this);
        back.setOnClickListener(this);
        lin.setOnClickListener(this);
        zhifubao.setOnClickListener(this);
        weixin.setOnClickListener(this);
        wallet.setOnClickListener(this);
        yinlian.setOnClickListener(this);
        submit.setOnClickListener(this);

        walletcb.setChecked(true);
        zhifubaocb.setChecked(false);
        weixincb.setChecked(false);
        yinliancb.setChecked(false);
        checkIndex=0;
        if (flag==1){
            shopId=getIntent().getStringExtra("shopId");
            shopServiceEntityList= (List<ShopServiceEntity>) getIntent().getSerializableExtra("entities");
            num.setText("共"+shopServiceEntityList.size()+"项服务");
            totalMoney=calMoney(shopServiceEntityList);
            money.setText("￥"+totalMoney);
            div1.setVisibility(View.VISIBLE);
            lin.setVisibility(View.VISIBLE);
            lin1.setVisibility(View.VISIBLE);
            div2.setVisibility(View.VISIBLE);
        }else if(flag==2){
            serOrderEntity= (SerOrderEntity) getIntent().getSerializableExtra("entity");
            div1.setVisibility(View.GONE);
            div2.setVisibility(View.GONE);
            lin.setVisibility(View.GONE);
            lin1.setVisibility(View.GONE);
            totalMoney= Double.parseDouble(serOrderEntity.totalPrice);
            money.setText("￥" + serOrderEntity.totalPrice);
        }
        if (flag!=2){
            getDefaultAddress();
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.service_pay_lin1:
                Intent intent2=new Intent(ServicePayActivity.this,ShipaddressActivity.class);
                intent2.putExtra("flag",2);
                startActivityForResult(intent2, 1663);

                break;

            case R.id.servicepay_lin:
                Intent intent=new Intent(ServicePayActivity.this,ServiceOrderOutActivity.class);
                intent.putExtra("entities", (Serializable) shopServiceEntityList);
                startActivity(intent);
                break;
            
            case R.id.servicepay_zhifubao:
                walletcb.setChecked(false);
                zhifubaocb.setChecked(true);
                weixincb.setChecked(false);
                yinliancb.setChecked(false);
                checkIndex=1;

                break;

            case R.id.servicepay_wallet:
                walletcb.setChecked(true);
                zhifubaocb.setChecked(false);
                weixincb.setChecked(false);
                yinliancb.setChecked(false);
                checkIndex=0;
                break;

            case R.id.servicepay_weixin:
                walletcb.setChecked(false);
                zhifubaocb.setChecked(false);
                weixincb.setChecked(true);
                yinliancb.setChecked(false);
                checkIndex=2;
                break;

            case R.id.servicepay_yinlian:
                walletcb.setChecked(false);
                zhifubaocb.setChecked(false);
                weixincb.setChecked(false);
                yinliancb.setChecked(true);
                checkIndex=3;
                break;

            case R.id.servicepay_ok:
                getServiceOrder();
//                PaymentDialog dialog=new PaymentDialog(ServicePayActivity.this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1663 && resultCode==RESULT_OK){
            addressVo= (AddressVo) data.getSerializableExtra("entity");
            person.setText(addressVo.name);
            phone.setText(addressVo.mobile);
        }

    }



    /**
     * 查询默认收货地址
     */
    private void getDefaultAddress() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url="address/findDefault";
        rp.addBodyParameter("sign",ShareDataTool.getToken(this));
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
                        AddAdressState addAdressState= gson.fromJson(arg0.result, AddAdressState.class);
                        addressVo=addAdressState.result;
                        person.setText(addressVo.name);
                        phone.setText(addressVo.mobile);
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ServicePayActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ServicePayActivity.this);
                    } else {
                        phone.setEnabled(true);
                        person.setEnabled(true);
                        ToastUtils.displayShortToast(ServicePayActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    phone.setEnabled(true);
                    person.setEnabled(true);
//                    ToastUtils.displaySendFailureToast(ServicePayActivity.this);
                }

            }
        });
    }



    /**
     * 结算服务订单
     */
    private void getServiceOrder() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        String url="serviceOrder/add";
        if (flag==1){
            if (addressVo==null && (ToosUtils.isTextEmpty(person) || ToosUtils.isTextEmpty(phone))){
                ToastUtils.displayShortToast(this,"请输入收货信息！");
            }
            ServiceOrderUp serviceOrderUp=new ServiceOrderUp();
            serviceOrderUp.shopId=shopId;
            MyCarEntity myCarEntity= MyApplication.getInstance().getCarEntity();
            if (myCarEntity!=null){
                serviceOrderUp.carTypeId=myCarEntity.carTypeId;
            }
            if (addressVo!=null){
                serviceOrderUp.defAddId=addressVo.id;
            }else{
                serviceOrderUp.name=ToosUtils.getTextContent(person);
                serviceOrderUp.phone=ToosUtils.getTextContent(phone);
            }

            serviceOrderUp.payment=String.valueOf(checkIndex);
            List<ServiceOrderVo> serviceOrderVos=new ArrayList<>();
            for (int i=0;i<shopServiceEntityList.size();i++){
                ServiceOrderVo serviceOrderVo=new ServiceOrderVo();
                serviceOrderVo.serviceId=shopServiceEntityList.get(i).id;
                serviceOrderVo.num= String.valueOf(shopServiceEntityList.get(i).num);
                serviceOrderVos.add(serviceOrderVo);
            }
            serviceOrderUp.service=serviceOrderVos;
            rp.addBodyParameter("order",new Gson().toJson(serviceOrderUp));
            LogManager.LogShow("------", Constant.ROOT_PATH + url + "?sign=" + ShareDataTool.getToken(this) + "&order=" + new Gson().toJson(serviceOrderUp), LogManager.ERROR);
        }else if (flag==2){

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
                        PayState payState=gson.fromJson(arg0.result,PayState.class);
                        if (checkIndex==0){
                            PaymentDialog dialog=new PaymentDialog(ServicePayActivity.this,payState.result,String.valueOf(totalMoney));
                        }else if(checkIndex==1){
                            zhifubaoPay(ToosUtils.getEncryptto(payState.result.content));
                        }else if(checkIndex==2){
                            MyApplication.getInstance().setWeixinmoney(String.valueOf(totalMoney));
                            PayReq request = new PayReq();
                            request.appId = payState.result.appid;
                            request.partnerId =  payState.result.parentId;
                            request.prepayId= ToosUtils.getEncryptto(payState.result.content);
                            request.packageValue = "Sign=WXPay";
                            request.nonceStr= payState.result.noncestr;
                            request.timeStamp= payState.result.timestamp;
                            request.sign= payState.result.paySign;
                            api.sendReq(request);
                            LogManager.LogShow("-----", ToosUtils.getEncryptto(payState.result.content),
                                    LogManager.ERROR);
                        }

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ServicePayActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ServicePayActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ServicePayActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ServicePayActivity.this);
                }

            }
        });
    }

    /**
     * 计算价格
     * @param entities
     * @return
     */
    private double calMoney(List<ShopServiceEntity> entities){
        double d=0;
        for (int i=0;i<entities.size();i++){
            if (entities.get(i).flag){
                d=d+entities.get(i).num*Double.valueOf(entities.get(i).price);
            }
        }
        return d;

    }

    private void zhifubaoPay(final String sign){
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(ServicePayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(sign, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
