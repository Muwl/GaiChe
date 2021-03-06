package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.OutSelDialog;
import com.gaicheyunxiu.gaiche.dialog.PaymentDialog;
import com.gaicheyunxiu.gaiche.model.AddAdressState;
import com.gaicheyunxiu.gaiche.model.AddressVo;
import com.gaicheyunxiu.gaiche.model.CommodifyOrderEntity;
import com.gaicheyunxiu.gaiche.model.CommodifyOrderVo;
import com.gaicheyunxiu.gaiche.model.CommodityEntity;
import com.gaicheyunxiu.gaiche.model.CostEntity;
import com.gaicheyunxiu.gaiche.model.CostState;
import com.gaicheyunxiu.gaiche.model.OrderCommodityDigestEntity;
import com.gaicheyunxiu.gaiche.model.OrderCommodityVo;
import com.gaicheyunxiu.gaiche.model.OutSelEntity;
import com.gaicheyunxiu.gaiche.model.PayResult;
import com.gaicheyunxiu.gaiche.model.PayState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopCartCommodityEntity;
import com.gaicheyunxiu.gaiche.model.ShopCartEntity;
import com.gaicheyunxiu.gaiche.model.ShopDetailEntity;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MathUtils;
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
import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mu on 2015/12/28.
 * 结算界面
 */
public class ClearingActivity extends BaseActivity implements View.OnClickListener{

    public static final int SDK_PAY_FLAG = 4462;

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

    private TextView outSel;

    private View outSelView;

    private ShopDetailEntity shopDetailEntity;

    private List<CommodityEntity> commodityEntities;

    private int checkIndex=1;//0钱包 1支付宝 2 微信 3银联

    private int flag;//1代表购物车  2代表普通商城  3代表众筹商品  4代表养修订单

    double smoney=0;

    private OutSelEntity outSelEntity;

    private IWXAPI api;

    private CostEntity costEntity;

    private String projectId;

    private TextView shopNum;

    private View  shopLin;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 89:
                    Intent intent3=new Intent(ClearingActivity.this,OultOrderSelActivity.class);
                    if (outSelEntity!=null){
                        intent3.putExtra("shopId",outSelEntity.id);
                    }
                    if (MyApplication.getInstance().getCarEntity()!=null){
                        intent3.putExtra("carTypeId",MyApplication.getInstance().getCarEntity().carTypeId);
                    }
                    if (flag==1){
                        String temp="";
                        List<ShopCartCommodityEntity> shopCartCommodityEntities=shopCartEntity.cartCommodityVOs;
                        for (int i=0;i<shopCartCommodityEntities.size();i++){
                            if (i<shopCartCommodityEntities.size()-1){
                                temp=temp+shopCartCommodityEntities.get(i).commodityId+",";
                            }else{
                                temp=temp+shopCartCommodityEntities.get(i).commodityId;
                            }
                        }
                        intent3.putExtra("commoditys",temp);
                    }else if(flag==4){
                        String temp="";
                        for (int i=0;i<commodityEntities.size();i++){
                            if (i<commodityEntities.size()-1){
                                temp=temp+commodityEntities.get(i).id+",";
                            }else{
                                temp=temp+commodityEntities.get(i).id;
                            }
                        }
                        intent3.putExtra("commoditys",temp);
                    }else{
                        intent3.putExtra("commoditys",shopDetailEntity.id);
                    }
                    startActivityForResult(intent3,5664);
                    break;
                case 90:
                    outSel.setText("无法确认,暂不选择门店");
                    break;

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
                        Intent intent1=new Intent(ClearingActivity.this, PaySuccessActivity.class);
                        intent1.putExtra("money",smoney);
                        startActivity(intent1);
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(ClearingActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(ClearingActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clearing);
        flag=getIntent().getIntExtra("flag",0);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        api.registerApp(Constant.APP_ID);
        if (flag==1){
            shopCartEntity= (ShopCartEntity) getIntent().getSerializableExtra("entity");
            if (shopCartEntity.outSelEntity!=null){
                outSelEntity=shopCartEntity.outSelEntity;
            }

        }else if(flag==2 ){
            shopDetailEntity= (ShopDetailEntity) getIntent().getSerializableExtra("entity");
        }else if(flag==3){
            shopDetailEntity= (ShopDetailEntity) getIntent().getSerializableExtra("entity");
            projectId=getIntent().getStringExtra("projectId");
        }else if(flag==4){
            commodityEntities= (List<CommodityEntity>) getIntent().getSerializableExtra("entity");
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
        outSel= (TextView) findViewById(R.id.clearing_outsel);
        outSelView=  findViewById(R.id.clearing_outselview);
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
        shopNum= (TextView) findViewById(R.id.clearing_shopnum);
        shopLin= findViewById(R.id.clearing_shoplist);
        money= (TextView) findViewById(R.id.clearing_money);
        submit= (TextView) findViewById(R.id.clearing_ok);
        pro=  findViewById(R.id.pay_freight_pro);

        if (flag==1){
            title.setText("去结算");
            outSelView.setVisibility(View.GONE);
        }else if(flag==2){
            title.setText("立即购买");
            outSel.setVisibility(View.VISIBLE);
            if ("0".equals(shopDetailEntity.distributionType)){
                delivery.setText("物流");
            } else{
                delivery.setText("自主配送");
            }
        }else if(flag==3){
            title.setText("立即购买");
            outSelView.setVisibility(View.GONE);
            installcost.setVisibility(View.GONE);
            if ("0".equals(shopDetailEntity.distributionType)){
                delivery.setText("物流");
            }else{
                delivery.setText("自主配送");
            }

        }else if(flag==4){
            title.setText("订单确认");
            outSel.setVisibility(View.VISIBLE);
            shopLin.setVisibility(View.VISIBLE);
            shopNum.setText("共"+commodityEntities.size()+"件商品");
        }
        outSelView.setOnClickListener(this);
        back.setOnClickListener(this);
        addressview.setOnClickListener(this);
        shopLin.setOnClickListener(this);
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


        if (flag==1) {
            for (int i = 0; i < shopCartEntity.cartCommodityVOs.size(); i++) {
                int num = Integer.valueOf(shopCartEntity.cartCommodityVOs.get(i).amount);
                m = m + num;
                smoney = smoney + Double.valueOf(shopCartEntity.cartCommodityVOs.get(i).commodityPrice) * num;
            }
            shopPrice.setText("商品价格￥"+smoney);
            installcost.setText("安装费￥"+0);
            freightcost.setText("运费￥"+0);
            money.setText("￥"+smoney);
        }
//        else if(flag==2){
//            m=shopDetailEntity.num;
//            smoney =  Double.valueOf(shopDetailEntity.presentPrice) * m;
//        }


        getDefaultAddress();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.clearing_outselview:
                OutSelDialog dialog=new OutSelDialog(ClearingActivity.this,-1,handler);
                break;

            case R.id.clearing_addview:
                Intent intent=new Intent(ClearingActivity.this,ShipaddressActivity.class);
                intent.putExtra("flag",2);
                startActivityForResult(intent, 1663);
                break;
            case R.id.clearing_shoplist:
                Intent intent9=new Intent(ClearingActivity.this,ShopClearInfoActivity.class);
                intent9.putExtra("entity", (Serializable) commodityEntities);
                startActivity(intent9);
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
                        phone.setText(addressVo.mobile);
                        address.setText("收货地址：" + addressVo.province + addressVo.city + addressVo.district + addressVo.address);
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
     * 保存订单
     */
    private void CommiteOrder() {
        if (costEntity==null){
            ToastUtils.displayShortToast(this,"请获取信息");
            return;
        }
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url="commodityOrder/save";
        if (flag==3){
            url="crowdfundingOrder/save";
            rp.addBodyParameter("projectId", projectId);
        }
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
                        PayState payState=gson.fromJson(arg0.result,PayState.class);
                        ToastUtils.displayShortToast(ClearingActivity.this, "提交成功！");
                        if (checkIndex==0){
                            PaymentDialog dialog=new PaymentDialog(ClearingActivity.this,payState.result,String.valueOf(smoney));
                        }else if(checkIndex==1){
                            zhifubaoPay(ToosUtils.getEncryptto(payState.result.content));
                        }else if(checkIndex==2){
                            MyApplication.getInstance().setWeixinmoney(String.valueOf(smoney));
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
        commodifyOrderEntity.receiveProvince=addressVo.province;
        commodifyOrderEntity.receiveCity=addressVo.city;
        commodifyOrderEntity.receiveDistrict=addressVo.district;
        commodifyOrderEntity.receiveDetail=addressVo.address;
        commodifyOrderEntity.name=addressVo.name;
        commodifyOrderEntity.mobile=addressVo.mobile;
        commodifyOrderEntity.isShoppingCart= String.valueOf(flag);
        commodifyOrderEntity.shopServicePrice= costEntity.shopServicePrice;
        if (outSelEntity!=null){
            commodifyOrderEntity.shopId=outSelEntity.id;
        }
        if (flag==1){
            commodifyOrderEntity.carTypeId=shopCartEntity.carTypeId;
            List<CommodifyOrderVo> commodifyOrderVos=new ArrayList<>();
            for (int i=0;i<shopCartEntity.cartCommodityVOs.size();i++){
                commodifyOrderEntity.isShoppingCart="0";
                ShopCartCommodityEntity shopCartCommodityEntity=shopCartEntity.cartCommodityVOs.get(i);
                CommodifyOrderVo commodifyOrderVo=new CommodifyOrderVo();
                commodifyOrderVo.commodityId=shopCartCommodityEntity.commodityId;
                commodifyOrderVo.price=shopCartCommodityEntity.commodityPrice;
                commodifyOrderVo.mVaule=shopCartCommodityEntity.mValue;
                commodifyOrderVo.num= Double.parseDouble(shopCartCommodityEntity.amount);
                commodifyOrderVo.freight= "0";
                for (int j=0;j<costEntity.freightVo.size();j++){
                    if (commodifyOrderVo.commodityId.equals(costEntity.freightVo.get(j).commodityId)){
                        commodifyOrderVo.freight=  String.valueOf(costEntity.freightVo.get(j).freight);
                    }
                }

                commodifyOrderVos.add(commodifyOrderVo);
            }
            commodifyOrderEntity.vos=commodifyOrderVos;
        }else if(flag==4){
            commodifyOrderEntity.carTypeId=MyApplication.getInstance().getCarEntity().carTypeId;
            commodifyOrderEntity.isShoppingCart="1";
            commodifyOrderEntity.carTypeId=MyApplication.getInstance().getCarEntity().carTypeId;
            List<CommodifyOrderVo> commodifyOrderVos=new ArrayList<>();
            for (int i=0;i<commodityEntities.size();i++){
                CommodifyOrderVo commodifyOrderVo=new CommodifyOrderVo();
                commodifyOrderVo.commodityId=commodityEntities.get(i).id;
                commodifyOrderVo.price=commodityEntities.get(i).presentPrice;
                commodifyOrderVo.mVaule=commodityEntities.get(i).mValue;
                commodifyOrderVo.num= commodityEntities.get(i).num;
                commodifyOrderVo.freight= "0";
                for (int j=0;j<costEntity.freightVo.size();j++){
                    if (commodifyOrderVo.commodityId.equals(costEntity.freightVo.get(j).commodityId)){
                        commodifyOrderVo.freight=  String.valueOf(costEntity.freightVo.get(j).freight);
                    }
                }

                commodifyOrderVos.add(commodifyOrderVo);
            }
            commodifyOrderEntity.vos=commodifyOrderVos;

        }else{
            if (MyApplication.getInstance().getCarEntity()!=null) {
                commodifyOrderEntity.carTypeId = MyApplication.getInstance().getCarEntity().carTypeId;
            }
                List<CommodifyOrderVo> commodifyOrderVos=new ArrayList<>();
                CommodifyOrderVo commodifyOrderVo=new CommodifyOrderVo();
                commodifyOrderVo.commodityId=shopDetailEntity.id;
                commodifyOrderEntity.isShoppingCart="1";
                commodifyOrderVo.merchantId=shopDetailEntity.merchantId;
                commodifyOrderVo.price= String.valueOf(shopDetailEntity.presentPrice);
                commodifyOrderVo.mVaule= String.valueOf(shopDetailEntity.MValue);
                commodifyOrderVo.num= shopDetailEntity.num;
                commodifyOrderVo.freight= String.valueOf(costEntity.freight);
                commodifyOrderVos.add(commodifyOrderVo);

            commodifyOrderEntity.vos=commodifyOrderVos;
        }


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
        if (flag==3){
            url="crowdfundingOrder/getCost";
            rp.addBodyParameter("projectId",projectId);
        }
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));

        OrderCommodityVo orderCommodityVo=new OrderCommodityVo();
        orderCommodityVo.receiveProvince=addressVo.province;
        orderCommodityVo.receiveCity=addressVo.city;
        orderCommodityVo.receiveDistrict=addressVo.district;
        orderCommodityVo.receiveDetail=addressVo.address;
        if (outSelEntity!=null){
            orderCommodityVo.shopId=outSelEntity.id;
        }
        List<OrderCommodityDigestEntity> commodityDigestEntities=new ArrayList<>();
        if (flag==1){
            for (int i=0;i<shopCartEntity.cartCommodityVOs.size();i++) {
                ShopCartCommodityEntity shopCartCommodityEntity = shopCartEntity.cartCommodityVOs.get(i);
                OrderCommodityDigestEntity digestEntity=new OrderCommodityDigestEntity();
                digestEntity.commodityId=shopCartCommodityEntity.commodityId;
                digestEntity.num=shopCartCommodityEntity.amount;
                commodityDigestEntities.add(digestEntity);
            }
        }else if(flag==4){
            for (int i=0;i<commodityEntities.size();i++) {
                OrderCommodityDigestEntity digestEntity=new OrderCommodityDigestEntity();
                digestEntity.commodityId=commodityEntities.get(i).id;
                digestEntity.num= String.valueOf(commodityEntities.get(i).num);
                commodityDigestEntities.add(digestEntity);
            }
        }else{
            OrderCommodityDigestEntity digestEntity=new OrderCommodityDigestEntity();
            digestEntity.commodityId=shopDetailEntity.id;
            digestEntity.num= String.valueOf(shopDetailEntity.num);
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
//                ToastUtils.displayFailureToast(ClearingActivity.this);
                pro.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                try {
                    Gson gson = new Gson();
                    LogManager.LogShow("-----", arg0.result,
                            LogManager.ERROR);
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        CostState costState=gson.fromJson(arg0.result,CostState.class);
                        costEntity=costState.result;
                        String sshopMoney= MathUtils.getMathDem(costState.result.price-costState.result.freight-costState.result.shopServicePrice);
                        smoney=costState.result.price;
                        if (costEntity.freight==0){
                            delivery.setText("包邮");
                        }else{
                            delivery.setText(costEntity.freight+"元");
                        }
                        shopPrice.setText("商品价格￥"+sshopMoney);
                        installcost.setText("安装费￥"+costState.result.shopServicePrice);
                        freightcost.setText("运费￥"+costState.result.freight);
                        money.setText("￥"+smoney);
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ClearingActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ClearingActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ClearingActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
//                    ToastUtils.displaySendFailureToast(ClearingActivity.this);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1663 && resultCode==RESULT_OK){
            addressVo= (AddressVo) data.getSerializableExtra("entity");
            person.setText("收货人："+addressVo.name);
            phone.setText(addressVo.mobile);
            address.setText("收货地址：" + addressVo.province + addressVo.city + addressVo.district + addressVo.address);
            getCost();
        }
        if (requestCode==5664 && resultCode==RESULT_OK){
            outSelEntity= (OutSelEntity) data.getSerializableExtra("entity");
            outSel.setText(outSelEntity.name);
            getCost();
        }
    }

    private void zhifubaoPay(final String sign){
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(ClearingActivity.this);
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
