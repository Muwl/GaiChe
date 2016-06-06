package com.gaicheyunxiu.gaiche.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.CarManagerAdapter;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.MyCarState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/11.
 */
public class CarmanagerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private TextView add;

    private ListView listView;

    private CarManagerAdapter adapter;

    private List<MyCarEntity> entities;

    private View pro;

    MyCarEntityUtils utils;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CustomeDialog.RET_OK:
                    int poisition = msg.arg1;
                    delMyCal(poisition);
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carmanager);
        utils=new MyCarEntityUtils(this);
        initView();
    }

    private void initView() {
        entities = (List<MyCarEntity>) getIntent().getSerializableExtra("cars");
        back = (ImageView) findViewById(R.id.title_back);
        title = (TextView) findViewById(R.id.title_text);
        add = (TextView) findViewById(R.id.title_service);
        listView = (ListView) findViewById(R.id.activity_listview);
        pro = findViewById(R.id.activity_pro);
        back.setOnClickListener(this);
        title.setText("车辆管理");
        add.setVisibility(View.VISIBLE);
        add.setOnClickListener(this);
        entities = new ArrayList<>();
        adapter = new CarManagerAdapter(this, entities, handler);
        listView.setAdapter(adapter);
        add.setText("新增");
        add.setOnClickListener(this);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CustomeDialog customeDialog = new CustomeDialog(CarmanagerActivity.this, handler, "确定删除此车型？", position, -1);
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setDefault(position);
                MyCarEntity myCarEntity = entities.get(position);
                MyApplication.getInstance().setCarEntity(myCarEntity);
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
            List<MyCarEntity> tempEntities= utils.getAllMyCar();
            if (tempEntities==null){
                tempEntities=new ArrayList<>();
            }
            entities.clear();
            for (int i = 0; i < tempEntities.size(); i++) {
                entities.add(tempEntities.get(i));
            }
            LogManager.LogShow("-----------",entities.toString(),LogManager.ERROR);
            adapter.notifyDataSetChanged();
        }else {
            getMyCal();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_service:
                Intent intent = new Intent(CarmanagerActivity.this, CarbrandActivity.class);
                startActivityForResult(intent, 12240);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12240 && resultCode == RESULT_OK) {
            if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
                List<MyCarEntity> tempEntities= utils.getAllMyCar();
                if (tempEntities==null){
                    tempEntities=new ArrayList<>();
                }
                entities.clear();
                for (int i = 0; i < tempEntities.size(); i++) {
                    entities.add(tempEntities.get(i));
                }
                adapter.notifyDataSetChanged();
            }else {
                getMyCal();
            }
        }
    }




    /**
     * 查询我的爱车
     */
    private void getMyCal() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "myCar/findAll", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(CarmanagerActivity.this);
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
                        entities.clear();
                        MyCarState carState = gson.fromJson(arg0.result, MyCarState.class);
                        for (int i = 0; i < carState.result.size(); i++) {
                            entities.add(carState.result.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(CarmanagerActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(CarmanagerActivity.this);
                    } else {
                        ToastUtils.displayShortToast(CarmanagerActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(CarmanagerActivity.this);
                }

            }
        });
    }

    /**
     * 设置默认爱车
     */
    private void setDefault(final int position) {
        String tempid="";
        for (int i=0;i<entities.size();i++){
            if (entities.get(i).isDefault  && i!=position){
                tempid=entities.get(i).carTypeId;
            }
        }
        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
            utils.updateDefault(entities.get(position));
            for (int i=0;i<entities.size();i++){
                if (i==position){
                    entities.get(i).isDefault=true;
                }else{
                    entities.get(i).isDefault=false;
                }
            }
            adapter.notifyDataSetChanged();
            return;
        }
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("carTypeId", entities.get(position).carTypeId);
        if (ToosUtils.isStringEmpty(tempid)){
            rp.addBodyParameter("oldDefaultId", tempid);
        }
        utils.configTimeout(20000);

        LogManager.LogShow("--------", Constant.ROOT_PATH + "myCar/setDefaultCarType?sign="+ShareDataTool.getToken(this)+"&carTypeId="+entities.get(position).carTypeId,LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "myCar/setDefaultCarType", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(CarmanagerActivity.this);
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
                        for (int i=0;i<entities.size();i++){
                            if (i==position){
                                entities.get(i).isDefault=true;
                            }else{
                                entities.get(i).isDefault=false;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(CarmanagerActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(CarmanagerActivity.this);
                    } else {
                        ToastUtils.displayShortToast(CarmanagerActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(CarmanagerActivity.this);
                }

            }
        });
    }


    /**
     * 删除我的爱车
     */
    private void delMyCal(final int position) {
        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
            utils.delMyCar(entities.get(position));
            ToastUtils.displayShortToast(CarmanagerActivity.this,
                    "删除成功！");
            entities.remove(position);
            adapter.notifyDataSetChanged();
            return;
        }
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("id", entities.get(position).carTypeId);
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "myCar/del", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(CarmanagerActivity.this);
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
                        ToastUtils.displayShortToast(CarmanagerActivity.this,
                                "删除成功！");
                        entities.remove(position);
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(CarmanagerActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(CarmanagerActivity.this);
                    } else {
                        ToastUtils.displayShortToast(CarmanagerActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(CarmanagerActivity.this);
                }

            }
        });
    }
}
