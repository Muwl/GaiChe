package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.CarbrandAdapter;
import com.gaicheyunxiu.gaiche.model.CarBrandComparator;
import com.gaicheyunxiu.gaiche.model.CarBrandEntity;
import com.gaicheyunxiu.gaiche.model.CarBrandState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.sortlistview.SideBar;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/2/11.
 */
public class CarbrandActivity extends BaseActivity  implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private ListView listView;

    private TextView dialog;

    private SideBar sideBar;

    private View pro;

    private View gv;

    private CarbrandAdapter adapter;

    private List<CarBrandEntity> entities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbrand);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        listView= (ListView) findViewById(R.id.carbrand_list);
        gv=  findViewById(R.id.carbrand_gv);
        dialog= (TextView) findViewById(R.id.carbrand_dialog);
        sideBar= (SideBar) findViewById(R.id.constants_sidrbar);
        pro=findViewById(R.id.constants_pro);


        back.setOnClickListener(this);
        title.setText("设置车型");

        findAll();


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
    private void findAll() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "carBrand/findAll", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                gv.setVisibility(View.GONE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                gv.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(CarbrandActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                gv.setVisibility(View.VISIBLE);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        CarBrandState brandState=gson.fromJson(arg0.result,CarBrandState.class);
                        entities=brandState.result;
                        if(entities==null){
                            entities=new ArrayList<CarBrandEntity>();
                        }
                        Collections.sort(entities,
                                new CarBrandComparator());
                        adapter=new CarbrandAdapter(CarbrandActivity.this,entities);
                        listView.setAdapter(adapter);
                        sideBar.setTextView(dialog);

                        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

                            @Override
                            public void onTouchingLetterChanged(String s) {
                                // 该字母首次出现的位置
                                int position = adapter
                                        .getPositionForSection(s
                                                .charAt(0));
                                if (position != -1) {
                                    listView.setSelection(position);
                                }

                            }
                        });

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(CarbrandActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(CarbrandActivity.this);
                    } else {
                        ToastUtils.displayShortToast(CarbrandActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(CarbrandActivity.this);
                }

            }
        });
    }
}
