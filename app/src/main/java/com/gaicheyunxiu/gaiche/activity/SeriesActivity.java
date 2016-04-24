package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.CarbrandAdapter;
import com.gaicheyunxiu.gaiche.adapter.SeriesAdapter;
import com.gaicheyunxiu.gaiche.model.CarBrandEntity;
import com.gaicheyunxiu.gaiche.model.CarBrandState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.SeriesEntity;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/11.
 * 型号
 */
public class SeriesActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private TextView brand;

    private ListView listView;

    private SeriesAdapter adapter;

    private View pro;

    private CarBrandEntity brandEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        initView();
    }

    private void initView() {
        brandEntity= (CarBrandEntity) getIntent().getSerializableExtra("brand");
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        brand= (TextView) findViewById(R.id.series_brand);
        listView= (ListView) findViewById(R.id.series_list);
        pro= findViewById(R.id.series_pro);

        title.setText("设置车型");
        back.setOnClickListener(this);
        adapter=new SeriesAdapter(this);
        listView.setAdapter(adapter);
        findType();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SeriesActivity.this, VolumeActivity.class);
                startActivity(intent);
            }
        });
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
        JsonObject object=new JsonObject();
        object.addProperty("carBrandName",brandEntity.name);
        rp.addBodyParameter("carTypeSearchStr", object.toString());
        utils.configTimeout(20000);
        LogManager.LogShow("-----",Constant.ROOT_PATH+ "carType/find?carTypeSearchStr="+object.toString(),LogManager.ERROR);
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
                ToastUtils.displayFailureToast(SeriesActivity.this);
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
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        SeriesEntity entity=gson.fromJson(arg0.result,SeriesEntity.class);

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(SeriesActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(SeriesActivity.this);
                    } else {
                        ToastUtils.displayShortToast(SeriesActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(SeriesActivity.this);
                }

            }
        });
    }
}
