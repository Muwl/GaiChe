package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ServiceOrderAdapter;
import com.gaicheyunxiu.gaiche.dialog.PaymentDialog;
import com.gaicheyunxiu.gaiche.model.AddAdressState;
import com.gaicheyunxiu.gaiche.model.AddressVo;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
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

//    String strUrl=Constant.ROOT_PATH+ url+"?sign="+ ShareDataTool.getToken(context)+"&paySign="+payEntity.paySign+"&payType="+payEntity.payType +"&payPwd="+ToosUtils.getEncrypt(ToosUtils.getTextContent(pwd) + ToosUtils.getEncryptto(payEntity.content);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicepay);
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

        title.setText("去结算");
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
                        phone.setText(addressVo.phone);
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
     * 结算服务订单
     */
    private void getServiceOrder() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        String url="serviceOrder/add";
        if (flag==1){
            if (addressVo==null){
                ToastUtils.displayShortToast(this,"请选择收货地址！");
            }
            ServiceOrderUp serviceOrderUp=new ServiceOrderUp();
            serviceOrderUp.shopId=shopId;
            MyCarEntity myCarEntity= MyApplication.getInstance().getCarEntity();
            if (myCarEntity!=null){
                serviceOrderUp.carTypeId=myCarEntity.carTypeId;
            }
            serviceOrderUp.defAddId=addressVo.id;
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
                        PaymentDialog dialog=new PaymentDialog(ServicePayActivity.this,null,null);
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
}
