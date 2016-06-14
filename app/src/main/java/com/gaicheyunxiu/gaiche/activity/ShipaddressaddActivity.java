package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.AreaDialog;
import com.gaicheyunxiu.gaiche.model.AddAdressState;
import com.gaicheyunxiu.gaiche.model.AddressVo;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Mu on 2016/1/8.
 * 新增收货地址
 */
public class ShipaddressaddActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private TextView com;

    private EditText name;

    private EditText phone;

    private View arealin;

    private TextView area;

    private EditText address;

    private EditText telphone;

    private EditText code;

    private View pro;

    private String[] addressStrs=null;

    private AddressVo addressVo;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AreaDialog.OK_ADDRESS:
                    String temp= (String) msg.obj;
                    addressStrs=temp.split(AreaDialog.SUB);
                    area.setText(addressStrs[0]+"/"+addressStrs[1]+"/"+addressStrs[2]);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipaddress_add);
        initView();
    }

    private void initView() {
        addressVo= (AddressVo) getIntent().getSerializableExtra("entity");
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        com= (TextView) findViewById(R.id.title_service);
        name= (EditText) findViewById(R.id.shipaddress_add_name);
        phone= (EditText) findViewById(R.id.shipaddress_add_phone);
        arealin=findViewById(R.id.shipaddress_add_arealin);
        area= (TextView) findViewById(R.id.shipaddress_add_area);
        address= (EditText) findViewById(R.id.shipaddress_add_address);
        telphone= (EditText) findViewById(R.id.shipaddress_add_telphone);
        code= (EditText) findViewById(R.id.shipaddress_add_code);
        pro= findViewById(R.id.shipaddress_add_pro);

        back.setOnClickListener(this);
        title.setText("新增收货地址");
        com.setVisibility(View.VISIBLE);
        com.setText("完成");
        com.setOnClickListener(this);
        arealin.setOnClickListener(this);
        if (addressVo!=null){
            title.setText("修改收货地址");
            name.setText(addressVo.getName());
            phone.setText(addressVo.getMobile());
            area.setText(addressVo.getProvince()+"/"+addressVo.getCity()+"/"+addressVo.getDistrict());
            address.setText(addressVo.getAddress());
            code.setText(addressVo.postcode);
            telphone.setText(addressVo.getPhone());
            addressStrs=new String[3];
            addressStrs[0]=addressVo.getProvince();
            addressStrs[1]=addressVo.getCity();
            addressStrs[2]=addressVo.getDistrict();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.title_service:
                if (checkBox()){
                    addAddress();
                }
                break;

            case R.id.shipaddress_add_arealin:
                AreaDialog dialog=new AreaDialog(ShipaddressaddActivity.this,handler);
                break;
        }
    }

    private boolean checkBox(){
        if (ToosUtils.isTextEmpty(name)){
            ToastUtils.displayShortToast(this,"姓名不能空！");
            return false;
        }
        if (ToosUtils.isTextEmpty(phone)){
            ToastUtils.displayShortToast(this,"手机号码不能空！");
            return false;
        }
        if (ToosUtils.isTextEmpty(area)){
            ToastUtils.displayShortToast(this,"区域不能空！");
            return false;
        }
        if (ToosUtils.isTextEmpty(address)){
            ToastUtils.displayShortToast(this,"详情地址不能空！");
            return false;
        }

        if (ToosUtils.isTextEmpty(code)){
            ToastUtils.displayShortToast(this,"邮编不能空！");
            return false;
        }
        return true;
    }


    /**
     * 新增收货地址
     */
    private void addAddress() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        JsonObject jsonObject=new JsonObject();
        if (addressVo!=null){
            jsonObject.addProperty("id",addressVo.getId());
        }
        jsonObject.addProperty("userId", ShareDataTool.getRegiterEntity(this).userId);
        jsonObject.addProperty("name", ToosUtils.getTextContent(name));
        jsonObject.addProperty("mobile", ToosUtils.getTextContent(phone));
        jsonObject.addProperty("phone", ToosUtils.getTextContent(telphone));
        jsonObject.addProperty("address", ToosUtils.getTextContent(address));
        jsonObject.addProperty("postcode", ToosUtils.getTextContent(code));
        jsonObject.addProperty("province", addressStrs[0]);
        jsonObject.addProperty("city", addressStrs[1]);
        jsonObject.addProperty("district", addressStrs[2]);
        rp.addBodyParameter("addressStr", jsonObject.toString());
        LogManager.LogShow("-----",Constant.ROOT_PATH + "address/save?sign="+ShareDataTool.getToken(this)+"&addressStr="+jsonObject.toString(),LogManager.ERROR);
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "address/save", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(ShipaddressaddActivity.this);
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
                        ToastUtils.displayShortToast(ShipaddressaddActivity.this, "添加成功");
                        AddAdressState addAdressState=gson.fromJson(arg0.result,
                                AddAdressState.class);
                        Intent intent=new Intent();
                        intent.putExtra("entity",addAdressState.result);
                        setResult(RESULT_OK,intent);
                        finish();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ShipaddressaddActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ShipaddressaddActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ShipaddressaddActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ShipaddressaddActivity.this);
                }

            }
        });
    }
}
