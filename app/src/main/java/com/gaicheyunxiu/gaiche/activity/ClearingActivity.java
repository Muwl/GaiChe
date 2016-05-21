package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.PaymentDialog;
import com.gaicheyunxiu.gaiche.model.AddAdressState;
import com.gaicheyunxiu.gaiche.model.AddressVo;
import com.gaicheyunxiu.gaiche.model.CommodifyOrderEntity;
import com.gaicheyunxiu.gaiche.model.CommodifyOrderVo;
import com.gaicheyunxiu.gaiche.model.OrderCommodityDigestEntity;
import com.gaicheyunxiu.gaiche.model.OrderCommodityVo;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopCartCommodityEntity;
import com.gaicheyunxiu.gaiche.model.ShopCartEntity;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mu on 2015/12/28.
 * 结算界面
 */
public class ClearingActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView person;

    private TextView phone;

    private TextView address;

    private TextView delivery;

    private View addressview;

    private View wallet;

    private CheckBox walletcb;

    private View zhifubao;

    private CheckBox zhifubaocb;

    private View weixin;

    private CheckBox weixincb;

    private View yinlian;

    private CheckBox yinliancb;

    private TextView installcost;

    private TextView shopPrice;

    private TextView freightcost;

    private TextView money;

    private TextView submit;

    private View pro;

    private AddressVo addressVo;

    private ShopCartEntity shopCartEntity;

    private int checkIndex=1;//0钱包 1支付宝 2 微信 3银联

    private int flag;//1代表购物车  2代表商城

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clearing);
        flag=getIntent().getIntExtra("flag",0);
        if (flag==1){
            shopCartEntity= (ShopCartEntity) getIntent().getSerializableExtra("entity");
        }else if(flag==2){

        }
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        person= (TextView) findViewById(R.id.clearing_person);
        phone= (TextView) findViewById(R.id.clearing_phone);
        address= (TextView) findViewById(R.id.clearing_address);
        addressview=findViewById(R.id.clearing_addview);
        delivery= (TextView) findViewById(R.id.clearing_delivery);
        wallet=findViewById(R.id.pay_wallet);
        walletcb= (CheckBox) findViewById(R.id.pay_wallet_cb);
        zhifubao=findViewById(R.id.pay_zhifubao);
        zhifubaocb= (CheckBox) findViewById(R.id.pay_zhifubao_cb);
        weixin=findViewById(R.id.pay_weixin);
        weixincb= (CheckBox) findViewById(R.id.pay_weixin_cb);
        yinlian=findViewById(R.id.pay_yinlian);
        yinliancb= (CheckBox) findViewById(R.id.pay_yinlian_cb);
        installcost= (TextView) findViewById(R.id.pay_install_cost);
        shopPrice= (TextView) findViewById(R.id.pay_shop_cost);
        freightcost= (TextView) findViewById(R.id.pay_freight_cost);
        money= (TextView) findViewById(R.id.clearing_money);
        submit= (TextView) findViewById(R.id.clearing_ok);
        pro=  findViewById(R.id.pay_freight_pro);

        title.setText("去结算");
        back.setOnClickListener(this);
        addressview.setOnClickListener(this);
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

        int m=0;
        double smoney=0;
        for (int i=0;i<shopCartEntity.cartCommodityVOs.size();i++){
            int num=Integer.valueOf(shopCartEntity.cartCommodityVOs.get(i).amount);
            m=m+num;
            smoney=smoney+Double.valueOf(shopCartEntity.cartCommodityVOs.get(i).commodityPrice)*num;
        }

        shopPrice.setText("商品价格￥"+smoney);
        installcost.setText("安装费￥"+0);
        freightcost.setText("运费￥"+0);
        money.setText("￥"+smoney);
        getDefaultAddress();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.clearing_addview:
                break;

            case R.id.pay_zhifubao:
                walletcb.setChecked(false);
                zhifubaocb.setChecked(true);
                weixincb.setChecked(false);
                yinliancb.setChecked(false);
                checkIndex=1;

                break;

            case R.id.pay_wallet:
                walletcb.setChecked(true);
                zhifubaocb.setChecked(false);
                weixincb.setChecked(false);
                yinliancb.setChecked(false);
                checkIndex=0;
                break;

            case R.id.pay_weixin:
                walletcb.setChecked(false);
                zhifubaocb.setChecked(false);
                weixincb.setChecked(true);
                yinliancb.setChecked(false);
                checkIndex=2;
                break;

            case R.id.pay_yinlian:
                walletcb.setChecked(false);
                zhifubaocb.setChecked(false);
                weixincb.setChecked(false);
                yinliancb.setChecked(true);
                checkIndex=3;
                break;

            case R.id.clearing_ok:
                CommiteOrder();

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
                ToastUtils.displayFailureToast(ClearingActivity.this);
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
                        person.setText("收货人："+addressVo.name);
                        phone.setText(addressVo.phone);
                        address.setText("收货地址："+addressVo.province+addressVo.city+addressVo.district+addressVo.address);
                        getCost();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ClearingActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ClearingActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ClearingActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.displaySendFailureToast(ClearingActivity.this);
                }

            }
        });
    }



    /**
     * 查询默认收货地址
     */
    private void CommiteOrder() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url="commodityOrder/save";
        if (addressVo==null){
            ToastUtils.displayShortToast(this,"请选择收货地址！");
            return;
        }
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("commodityOrderVosStr", new Gson().toJson(getOrderEntity()));
        LogManager.LogShow("------", Constant.ROOT_PATH + url + "?sign=" + ShareDataTool.getToken(this) + "&commodityOrderVosStr=" + new Gson().toJson(getOrderEntity()), LogManager.ERROR);

        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + url, rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(ClearingActivity.this);
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
                        ToastUtils.displayShortToast(ClearingActivity.this, "提交成功！");
                        PaymentDialog dialog=new PaymentDialog(ClearingActivity.this);
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ClearingActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ClearingActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ClearingActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.displaySendFailureToast(ClearingActivity.this);
                }

            }
        });
    }

    public CommodifyOrderEntity getOrderEntity(){

        CommodifyOrderEntity commodifyOrderEntity=new CommodifyOrderEntity();
        commodifyOrderEntity.payWay= String.valueOf(checkIndex);
        commodifyOrderEntity.carTypeId=shopCartEntity.carTypeId;
        commodifyOrderEntity.receiveProvince=addressVo.province;
        commodifyOrderEntity.receiveCity=addressVo.city;
        commodifyOrderEntity.receiveDistrict=addressVo.district;
        commodifyOrderEntity.receiveDetail=addressVo.address;
        commodifyOrderEntity.name=addressVo.name;
        commodifyOrderEntity.mobile=addressVo.mobile;
        commodifyOrderEntity.isShoppingCart= String.valueOf(flag);
        List<CommodifyOrderVo> commodifyOrderVos=new ArrayList<>();
        for (int i=0;i<shopCartEntity.cartCommodityVOs.size();i++){
            ShopCartCommodityEntity shopCartCommodityEntity=shopCartEntity.cartCommodityVOs.get(i);
            CommodifyOrderVo commodifyOrderVo=new CommodifyOrderVo();
            commodifyOrderVo.commodityId=shopCartCommodityEntity.commodityId;
            commodifyOrderVo.price=shopCartCommodityEntity.commodityPrice;
            commodifyOrderVo.mVaule=shopCartCommodityEntity.mValue;
            commodifyOrderVo.num= Double.parseDouble(shopCartCommodityEntity.amount);
            commodifyOrderVos.add(commodifyOrderVo);
        }
        commodifyOrderEntity.vos=commodifyOrderVos;
        return commodifyOrderEntity;
    }

    /**
     * 获取商品订单费用
     */
    private void getCost() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url="commodityOrder/getCost";
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));

        OrderCommodityVo orderCommodityVo=new OrderCommodityVo();
        orderCommodityVo.receiveProvince=addressVo.province;
        orderCommodityVo.receiveCity=addressVo.city;
        orderCommodityVo.receiveDistrict=addressVo.district;
        orderCommodityVo.receiveDetail=addressVo.address;
        List<OrderCommodityDigestEntity> commodityDigestEntities=new ArrayList<>();
        for (int i=0;i<shopCartEntity.cartCommodityVOs.size();i++) {
            ShopCartCommodityEntity shopCartCommodityEntity = shopCartEntity.cartCommodityVOs.get(i);
            OrderCommodityDigestEntity digestEntity=new OrderCommodityDigestEntity();
            digestEntity.commodityId=shopCartCommodityEntity.commodityId;
            digestEntity.num=shopCartCommodityEntity.amount;
            commodityDigestEntities.add(digestEntity);
        }
        orderCommodityVo.list=commodityDigestEntities;
        rp.addBodyParameter("orderDigestVoStr", new Gson().toJson(orderCommodityVo));

        LogManager.LogShow("------",Constant.ROOT_PATH+ url+"?sign="+ ShareDataTool.getToken(this)+"&orderDigestVoStr="+new Gson().toJson(orderCommodityVo),LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + url, rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(ClearingActivity.this);
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

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ClearingActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ClearingActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ClearingActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ClearingActivity.this);
                }

            }
        });
    }





}
