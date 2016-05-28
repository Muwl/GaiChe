package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.MaintainAdapter;
import com.gaicheyunxiu.gaiche.model.EarnIncomeState;
import com.gaicheyunxiu.gaiche.model.MaintainEntity;
import com.gaicheyunxiu.gaiche.model.MaintainState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
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
 * Created by Mu on 2016/1/8.
 * 保养档案页面
 */
public class MaintainActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private ListView listView;

    private MaintainAdapter adapter;

    private List<MaintainEntity> entities;

    private View pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        listView= (ListView) findViewById(R.id.maintain_list);
        pro=findViewById(R.id.maintain_pro);
        entities=new ArrayList<>();
        adapter=new MaintainAdapter(this,entities);

        back.setOnClickListener(this);
        title.setText("保养档案");
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MaintainActivity.this, MartaindetailActivity.class);
                intent.putExtra("entity",entities.get(position));
                startActivity(intent);
            }
        });
        getArchiveCarTypes();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    /**
     * 查询保养档案车型
     */
    private void getArchiveCarTypes() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        LogManager.LogShow("-----", Constant.ROOT_PATH
                        + "earnings/income?sign=" + ShareDataTool.getToken(this),
                LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "archive/archiveCarTypes", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(MaintainActivity.this);
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
                        MaintainState maintainState=gson.fromJson(arg0.result,MaintainState.class);
                        for (int i=0;i<maintainState.result.size();i++){
                            entities.add(maintainState.result.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(MaintainActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(MaintainActivity.this);
                    } else {
                        ToastUtils.displayShortToast(MaintainActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(MaintainActivity.this);
                }

            }
        });
    }
}
