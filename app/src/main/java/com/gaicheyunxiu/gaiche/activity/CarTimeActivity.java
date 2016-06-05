package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.SeriesAdapter;
import com.gaicheyunxiu.gaiche.adapter.VolumeAdapter;
import com.gaicheyunxiu.gaiche.model.CarTimeEntity;
import com.gaicheyunxiu.gaiche.model.CarTypeEntity;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.MyCarState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.SeriesEntity;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.MyCarEntityUtils;
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
import java.util.Set;

/**
 * Created by Administrator on 2016/2/11.
 * 排量列表
 */
public class CarTimeActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private TextView brand;

    private ListView listView;

    private CarTypeEntity typeEntity;

    private View pro;

    private VolumeAdapter adapter;

    MyCarEntityUtils carEntityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        carEntityUtils=new MyCarEntityUtils(this);
        initView();
    }

    private void initView() {
        typeEntity= (CarTypeEntity) getIntent().getSerializableExtra("entity");
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        brand= (TextView) findViewById(R.id.series_brand);
        listView= (ListView) findViewById(R.id.series_list);
        pro= findViewById(R.id.series_pro);

        title.setText("设置车型");
        back.setOnClickListener(this);
        brand.setText(typeEntity.carBrandName + ">" + typeEntity.type + ">" + typeEntity.displacement);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeEntity.productionDate = (String) adapter.getItem(position);
                findTypeId();
            }
        });
        findType();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }

    }


    /**
     * 查询所有品牌
     */
    private void findType() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("carTypeSearchStr", new Gson().toJson(typeEntity));
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "carType/find", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(CarTimeActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                LogManager.LogShow("-----", arg0.result + "=====",
                        LogManager.ERROR);

                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        SeriesEntity entity=gson.fromJson(arg0.result,SeriesEntity.class);
                        Set<String> strings=entity.result.keySet();
                        List<String> datas=new ArrayList<String>();
                        for (String temp:strings){
                            datas.add(temp);
                        }
                        adapter=new VolumeAdapter(CarTimeActivity.this,datas);
                        listView.setAdapter(adapter);
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(CarTimeActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(CarTimeActivity.this);
                    } else {
                        ToastUtils.displayShortToast(CarTimeActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(CarTimeActivity.this);
                }

            }
        });
    }


    /**
     * 查询所有品牌
     */
    private void findTypeId() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("carTypeSearchStr", new Gson().toJson(typeEntity));
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "carType/find", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(CarTimeActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                LogManager.LogShow("-----", arg0.result + "=====",
                        LogManager.ERROR);

                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        CarTimeEntity carTimeEntity=gson.fromJson(arg0.result,CarTimeEntity.class);

                        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(CarTimeActivity.this))){
                            MyCarEntity myCarEntity=new MyCarEntity();
                            myCarEntity.carBrandId=typeEntity.carBrandid;
                            myCarEntity.carTypeId=carTimeEntity.result.get(0);
                            myCarEntity.carBrandName=typeEntity.carBrandName;
                            myCarEntity.carBrandLogo=typeEntity.carBrandLogo;
                            myCarEntity.productionPlace=typeEntity.productionPlace;
                            myCarEntity.type=typeEntity.type;
                            myCarEntity.displacement=typeEntity.displacement;
                            myCarEntity.productionDate=typeEntity.productionDate;

                            carEntityUtils.saveMyCar(myCarEntity);
                            ((MyApplication) getApplication()).setCarEntity(myCarEntity);
                            Intent intent=new Intent();
                            setResult(RESULT_OK,intent);
                            finish();
                        }else{
                            saveCar(carTimeEntity.result.get(0));
                        }
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(CarTimeActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(CarTimeActivity.this);
                    } else {
                        ToastUtils.displayShortToast(CarTimeActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(CarTimeActivity.this);
                }

            }
        });
    }

    /**
     * 保存/修改我的爱车
     */
    private void saveCar(final String id) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("carBrand", typeEntity.carBrandName);
        rp.addBodyParameter("productionPlace", typeEntity.productionPlace);
        rp.addBodyParameter("type", typeEntity.type);
        rp.addBodyParameter("displacement", typeEntity.displacement);
        rp.addBodyParameter("productionDate", typeEntity.productionDate);
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "myCar/save", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(CarTimeActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                LogManager.LogShow("-----", arg0.result + "=====",
                        LogManager.ERROR);

                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        MyCarEntity myCarEntity=new MyCarEntity();
                        myCarEntity.carBrandId=typeEntity.carBrandid;
                        myCarEntity.carTypeId=id;
                        myCarEntity.carBrandName=typeEntity.carBrandName;
                        myCarEntity.carBrandLogo=typeEntity.carBrandLogo;
                        myCarEntity.productionPlace=typeEntity.productionPlace;
                        myCarEntity.type=typeEntity.type;
                        myCarEntity.displacement=typeEntity.displacement;
                        myCarEntity.productionDate=typeEntity.productionDate;
                        ((MyApplication)getApplication()).setCarEntity(myCarEntity);
                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        finish();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(CarTimeActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(CarTimeActivity.this);
                    } else {
                        ToastUtils.displayShortToast(CarTimeActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(CarTimeActivity.this);
                }

            }
        });
    }


}
