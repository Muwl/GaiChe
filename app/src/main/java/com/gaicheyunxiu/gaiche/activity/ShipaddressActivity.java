package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ShipaddressAdapter;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.model.AddressState;
import com.gaicheyunxiu.gaiche.model.AddressVo;
import com.gaicheyunxiu.gaiche.model.RegisterState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mu on 2016/1/8.
 * 收货地址页面
 */
public class ShipaddressActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;

    private ImageView back;

    private ListView listView;

    private ShipaddressAdapter adapter;

    private TextView add;

    private View pro;

    private int flag;

    public static final int ADDRESS_ADD=1665;
    private static final int ADDRESS_EDIT=1666;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CustomeDialog.RET_OK:
                    int position=msg.arg1;
                    int flag=msg.arg2;
                    if (flag==-1){
                        delAddress(position);
                    }else if (flag==-2){
                        updateAddress(position);
                    }

                    break;

                case 5336:
                    int posi=msg.arg1;
                    updateAddress(posi);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipaddress);
        initView();
    }

    private void initView() {
        flag=getIntent().getIntExtra("flag",flag);
        back = (ImageView) findViewById(R.id.title_back);
        title = (TextView) findViewById(R.id.title_text);
        listView = (ListView) findViewById(R.id.shipaddress_list);
        add = (TextView) findViewById(R.id.title_service);
        adapter = new ShipaddressAdapter(this,handler);
        back.setOnClickListener(this);
        title.setText("收货地址");
        add.setText("新增");
        add.setVisibility(View.VISIBLE);
        add.setOnClickListener(this);
        listView.setAdapter(adapter);
        pro = findViewById(R.id.shipaddress_pro);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (flag==2){
                    Intent intent=new Intent();
                    intent.putExtra("entity", adapter.getDatas().get(i));
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Intent intent = new Intent(ShipaddressActivity.this, ShipaddressaddActivity.class);
                    startActivity(intent);
                }

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapter.getDatas().get(i).isDefault()){
                    CustomeDialog dialog = new CustomeDialog(ShipaddressActivity.this, handler,"确定要修改此地址为默认地址",i,-2);
                }
                return true;
            }
        });
        getAddressList();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_service:
                Intent intent = new Intent(ShipaddressActivity.this, ShipaddressaddActivity.class);
                startActivityForResult(intent, ADDRESS_ADD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            List<AddressVo> addressVos=adapter.getDatas();
            AddressVo addressVo= (AddressVo) data.getSerializableExtra("entity");
            for (int i=0;i<addressVos.size();i++){
                if (addressVos.get(i).getId().equals(addressVo.getId())){
                    addressVos.set(i, addressVo);
                    adapter.notifyDataSetChanged();
                    return;
                }
            }
            addressVos.add(addressVo);
            adapter.notifyDataSetChanged();
        }

    }

    /**
     * 获取收货地址列表
     */
    private void getAddressList() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(ShipaddressActivity.this));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "address/findByUserId",
                rp, new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        pro.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        pro.setVisibility(View.GONE);
                        ToastUtils.displayFailureToast(ShipaddressActivity.this);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        pro.setVisibility(View.GONE);
                        try {
                            Gson gson = new Gson();
                            LogManager.LogShow("获取到用户收货地址", arg0.result,
                                    LogManager.ERROR);
                            ReturnState state = gson.fromJson(arg0.result,
                                    ReturnState.class);
                            if (Constant.RETURN_OK.equals(state.msg)) {
                                AddressState state2 = gson.fromJson(arg0.result,
                                        AddressState.class);
                                adapter.update(true, state2.result);
                            } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                                ToastUtils.displayShortToast(ShipaddressActivity.this,
                                        "验证错误，请重新登录");
                                ToosUtils.goReLogin(ShipaddressActivity.this);
                            }else {
                                ToastUtils.displayShortToast(
                                        ShipaddressActivity.this,
                                        (String) state.result);
                            }
                        } catch (Exception e) {
                            ToastUtils
                                    .displaySendFailureToast(ShipaddressActivity.this);
                        }

                    }
                });

    }


    /**
     * 删除收货地址
     */
    private void delAddress(final int position) {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(ShipaddressActivity.this));
        rp.addBodyParameter("id", adapter.getDatas().get(position).getId());
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "address/del",
                rp, new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        pro.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        pro.setVisibility(View.GONE);
                        ToastUtils.displayFailureToast(ShipaddressActivity.this);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        pro.setVisibility(View.GONE);
                        try {
                            Gson gson = new Gson();
                            LogManager.LogShow("-----------", arg0.result,
                                    LogManager.ERROR);
                            ReturnState state = gson.fromJson(arg0.result,
                                    ReturnState.class);
                            if (Constant.RETURN_OK.equals(state.msg)) {
                                adapter.getDatas().remove(position);
                                adapter.notifyDataSetChanged();
                                ToastUtils.displayShortToast(ShipaddressActivity.this,
                                        "删除成功！");
                            } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                                ToastUtils.displayShortToast(ShipaddressActivity.this,
                                        "验证错误，请重新登录");
                                ToosUtils.goReLogin(ShipaddressActivity.this);
                            }else {
                                ToastUtils.displayShortToast(
                                        ShipaddressActivity.this,
                                        (String) state.result);
                            }
                        } catch (Exception e) {
                            ToastUtils
                                    .displaySendFailureToast(ShipaddressActivity.this);
                        }

                    }
                });

    }


    /**
     * 删除收货地址
     */
    private void updateAddress(final int position) {
        List<AddressVo> addressVos =adapter.getDatas();
        String oldId="";
        for (int i=0;i<addressVos.size();i++){
            if (addressVos.get(i).isDefault()){
                oldId=addressVos.get(i).getId();
            }
        }
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(ShipaddressActivity.this));
        rp.addBodyParameter("id", adapter.getDatas().get(position).getId());
        if (!ToosUtils.isStringEmpty(oldId)){
            rp.addBodyParameter("oldId", oldId);
        }
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "address/isDefault",
                rp, new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        pro.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        pro.setVisibility(View.GONE);
                        ToastUtils.displayFailureToast(ShipaddressActivity.this);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        pro.setVisibility(View.GONE);
                        try {
                            Gson gson = new Gson();
                            LogManager.LogShow("-----------", arg0.result,
                                    LogManager.ERROR);
                            ReturnState state = gson.fromJson(arg0.result,
                                    ReturnState.class);
                            if (Constant.RETURN_OK.equals(state.msg)) {
                                List<AddressVo> addressVos =adapter.getDatas();
                                for (int i=0;i<addressVos.size();i++){
                                    if (addressVos.get(i).getId().equals(adapter.getDatas().get(position).getId())){
                                        addressVos.get(i).setIsDefault(true);
                                    }else{
                                        addressVos.get(i).setIsDefault(false);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                if (flag==2){
                                    Intent intent=new Intent();
                                    intent.putExtra("entity",addressVos.get(position));
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }else{
                                    ToastUtils.displayShortToast(ShipaddressActivity.this,
                                            "修改成功！");
                                }

                            } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                                ToastUtils.displayShortToast(ShipaddressActivity.this,
                                        "验证错误，请重新登录");
                                ToosUtils.goReLogin(ShipaddressActivity.this);
                            } else {
                                ToastUtils.displayShortToast(
                                        ShipaddressActivity.this,
                                        (String) state.result);
                            }
                        } catch (Exception e) {
                            ToastUtils
                                    .displaySendFailureToast(ShipaddressActivity.this);
                        }

                    }
                });

    }


}
