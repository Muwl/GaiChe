package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.FSupportAdapter;
import com.gaicheyunxiu.gaiche.adapter.FacialAdapter;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.SupportEntity;
import com.gaicheyunxiu.gaiche.model.SupportState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
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
 * Created by Administrator on 2016/3/27.
 * 养修项目
 */
public class FSupportActivity extends BaseActivity  implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private ListView listView;

    private TextView ok;

    private View pro;

    private FSupportAdapter adapter;

    private List<SupportEntity> entities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        listView= (ListView) findViewById(R.id.fsupport_listview);
        ok= (TextView) findViewById(R.id.fsupport_ok);
        pro= findViewById(R.id.fsupport_pro);

        entities=new ArrayList<>();
        back.setOnClickListener(this);
        title.setText("养修");
        adapter=new FSupportAdapter(this,entities);
        listView.setAdapter(adapter);
        getSupport();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                break;
        }
    }


    /**
     * 查询所有品牌
     */
    private void getSupport() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "maintenance/findMaintenance", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(FSupportActivity.this);
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
                        SupportState supportState=gson.fromJson(arg0.result,SupportState.class);
                        for (int i=0;i<supportState.result.size();i++){
                            entities.add(supportState.result.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(FSupportActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(FSupportActivity.this);
                    } else {
                        ToastUtils.displayShortToast(FSupportActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(FSupportActivity.this);
                }

            }
        });
    }
}
