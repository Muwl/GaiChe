package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.CrowdfundAdapter;
import com.gaicheyunxiu.gaiche.model.CrowCommityEntity;
import com.gaicheyunxiu.gaiche.model.CrowCommityState;
import com.gaicheyunxiu.gaiche.model.CrowdfundingProjectEntity;
import com.gaicheyunxiu.gaiche.model.CrowdfundingProjectState;
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
 * Created by Administrator on 2016/3/27.
 * 众筹页面
 */
public class CrowdFundActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private ImageView schedule;

    private TextView bartext;

    private TextView crowed;

    private TextView totalcrow;

    private TextView everycrow;

    private TextView hour;

    private TextView minute;

    private TextView sec;

    private TextView day;

    private RadioGroup group;

    private RadioButton defaultrb;

    private RadioButton pricerb;

    private RadioButton volume;

    private RadioButton brand;

    private ListView listView;

    private CrowdfundAdapter adapter;

    private List<CrowCommityEntity> entities;

    private View pro;

    private CrowdfundingProjectEntity projectEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowdfund);
        projectEntity= (CrowdfundingProjectEntity) getIntent().getSerializableExtra("projectEntity");
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        schedule= (ImageView) findViewById(R.id.crowdfund_schedule);
        bartext= (TextView) findViewById(R.id.crowdfund_bartext);
        crowed= (TextView) findViewById(R.id.crowdfund_crowed);
        totalcrow= (TextView) findViewById(R.id.crowdfund_totalcrow);
        everycrow= (TextView) findViewById(R.id.crowdfund_everycrow);
        hour= (TextView) findViewById(R.id.crowdfund_hour);
        minute= (TextView) findViewById(R.id.crowdfund_minute);
        sec= (TextView) findViewById(R.id.crowdfund_sec);
        day= (TextView) findViewById(R.id.crowdfund_day);
        group= (RadioGroup) findViewById(R.id.crowdfund_rb);
        defaultrb= (RadioButton) findViewById(R.id.crowdfund_default);
        pricerb= (RadioButton) findViewById(R.id.crowdfund_moods);
        volume= (RadioButton) findViewById(R.id.crowdfund_technology);
        brand= (RadioButton) findViewById(R.id.crowdfund_price);
        listView= (ListView) findViewById(R.id.crowdfund_lisview);
        pro=  findViewById(R.id.crowdfund_pro);
        entities=new ArrayList<>();
        adapter=new CrowdfundAdapter(this,entities);
        listView.setAdapter(adapter);

        title.setText("众筹");
        back.setOnClickListener(this);
        group.check(R.id.crowdfund_default);

        crowed.setText(projectEntity.completeMoney);
        totalcrow.setText(projectEntity.expectMoney);


        getFundProjectList();

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
     * 众筹项目查询
     */
    private void getFundProjectList() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        if (!ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
            rp.addBodyParameter("sign",ShareDataTool.getToken(this));
        }
        rp.addBodyParameter("projectId",projectEntity.id);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "crowdfundingProject/findByProjectId", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(CrowdFundActivity.this);
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
                        LogManager.LogShow("-----***111", arg0.result,
                                LogManager.ERROR);
                        entities.clear();
                        CrowCommityState state1=gson.fromJson(arg0.result,CrowCommityState.class);
                        for (int i=0;i<state1.result.size();i++){
                            entities.add(state1.result.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(CrowdFundActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(CrowdFundActivity.this);
                    } else {
                        ToastUtils.displayShortToast(CrowdFundActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(CrowdFundActivity.this);
                }

            }
        });
    }
}
