package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.CartAdapter;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.model.EarnIncomeState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopCartEntity;
import com.gaicheyunxiu.gaiche.model.ShopCartState;
import com.gaicheyunxiu.gaiche.utils.CityDBUtils;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LocalUtils;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mu on 2015/12/23.
 * 购物车页面
 */
public class CartActivity extends BaseActivity  implements View.OnClickListener {

    private ImageView back;

    private TextView title;
    private TextView titleCity;

    private View map;

    private ListView listView;

    private CartAdapter adapter;

    private View empty;

    private TextView goSale;

    private TextView goCulture;

    private View pro;

    private List<ShopCartEntity> entities;

    private BDLocation bdLocation = null;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case   CustomeDialog.RET_OK:
                    int position=msg.arg1;
                    int flag=msg.arg2;
                    if (flag>=0){
                        getDel(flag,position);
                    }else if(flag==-1){
                        getClear(position);
                    }
                    break;

                case 1774:
                    adapter.notifyDataSetChanged();
                    break;

                case LocalUtils.LOCAT_OK:
                    bdLocation = (BDLocation) msg.obj;
                    String city=bdLocation.getCity();
                    titleCity.setText(city);
                    break;

                case 89:
                    int poi= (int) msg.obj;

                    break;

                case 90:
                    int poi2= (int) msg.obj;

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        titleCity= (TextView) findViewById(R.id.title_city);
        map=findViewById(R.id.title_map);
        listView= (ListView) findViewById(R.id.cart_list);
        empty=findViewById(R.id.cart_empty);
        goSale= (TextView) findViewById(R.id.cart_gosale);
        goCulture= (TextView) findViewById(R.id.cart_gosale);
        pro=  findViewById(R.id.cart_pro);

        entities=new ArrayList<>();
        adapter=new CartAdapter(this,entities,handler);
        back.setOnClickListener(this);
        title.setOnClickListener(this);
        title.setText("购物车");
        map.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        map.setOnClickListener(this);
        listView.setAdapter(adapter);

        LocalUtils localUtils = new LocalUtils(this, handler);
        localUtils.startLocation();
        getCart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.title_map:
                break;
        }

    }

    /**
     * 查询我的购物车
     */
    private void getCart() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "shoppingCart/find", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(CartActivity.this);
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
                        ShopCartState shopCartState=gson.fromJson(arg0.result,ShopCartState.class);
                        for (int i=0;i<shopCartState.result.size();i++){
                            entities.add(shopCartState.result.get(i));
                        }
                        adapter.notifyDataSetChanged();
                        refushEmppty();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(CartActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(CartActivity.this);
                    } else {
                        ToastUtils.displayShortToast(CartActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(CartActivity.this);
                }

            }
        });
    }




    /**
     *删除购物车
     */
    private void getDel(final int groupId, final int position) {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("id", entities.get(groupId).cartCommodityVOs.get(position).shoppingCartId);
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "shoppingCart/del", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(CartActivity.this);
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
                        entities.get(groupId).cartCommodityVOs.remove(position);
                        if (entities.get(groupId).cartCommodityVOs.size()==0){
                            entities.remove(groupId);
                        }
                        adapter.notifyDataSetChanged();
                        refushEmppty();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(CartActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(CartActivity.this);
                    } else {
                        ToastUtils.displayShortToast(CartActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(CartActivity.this);
                }

            }
        });
    }

    /**
     *清空购物车
     */
    private void getClear(final int position) {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("carTypeId", entities.get(position).carTypeId);
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "shoppingCart/empty", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(CartActivity.this);
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
                        entities.remove(position);
                        adapter.notifyDataSetChanged();
                        refushEmppty();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(CartActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(CartActivity.this);
                    } else {
                        ToastUtils.displayShortToast(CartActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(CartActivity.this);
                }

            }
        });
    }

    private void refushEmppty(){
        if (entities.size()==0){
            empty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else{
            empty.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }
}
