package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ServiceArtAdapter;
import com.gaicheyunxiu.gaiche.model.AdState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ServiceArtEntity;
import com.gaicheyunxiu.gaiche.model.ServiceArtState;
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
 * Created by Administrator on 2016/5/15.
 */
public class ServiceArtActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;

    private ImageView back;

    private RadioGroup radioGroup;

    private ListView listView;

    private View pro;

    private ServiceArtAdapter adapter;

    private List<ServiceArtEntity> entities;

    private String sType="0";

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceart);
        initView();
        
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        radioGroup= (RadioGroup) findViewById(R.id.serviceart_group);
        listView= (ListView) findViewById(R.id.serviceart_list);
        pro=  findViewById(R.id.serviceart_pro);
        entities=new ArrayList<>();
        back.setOnClickListener(this);
        title.setText("服务项目");

        adapter=new ServiceArtAdapter(this,entities,handler);
        listView.setAdapter(adapter);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.serviceart_byrb:
                        sType = "0";
                        getProjectType(sType);
                        break;
                    case R.id.serviceart_xchrb:
                        sType = "1";
                        getProjectType(sType);
                        break;

                    case R.id.serviceart_mrrb:
                        sType = "2";
                        getProjectType(sType);
                        break;

                    case R.id.serviceart_pqrb:
                        sType = "3";
                        getProjectType(sType);
                        break;

                    case R.id.serviceart_ltrb:
                        sType = "4";
                        getProjectType(sType);
                        break;

                    case R.id.serviceart_zhrb:
                        sType = "5";
                        getProjectType(sType);
                        break;

                    case R.id.serviceart_gzrb:
                        sType = "6";
                        getProjectType(sType);
                        break;

                    case R.id.serviceart_qitarb:
                        sType = "7";
                        getProjectType(sType);
                        break;

                }
            }
        });

        radioGroup.check(R.id.serviceart_byrb);
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
     * 服务项目
     */
    private void getProjectType(final String projectType) {
        entities.clear();
        adapter.notifyDataSetChanged();
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("projectType",projectType);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "shop/findByProjectType", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(ServiceArtActivity.this);
                pro.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                if (!sType.equals(projectType)){
                    return;
                }
                pro.setVisibility(View.GONE);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        ServiceArtState serviceArtState=gson.fromJson(arg0.result,ServiceArtState.class);
                        if (serviceArtState.result!=null){
                            for (int i=0;i<serviceArtState.result.size();i++){
                                entities.add(serviceArtState.result.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ServiceArtActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ServiceArtActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ServiceArtActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ServiceArtActivity.this);
                }

            }
        });
    }

}
