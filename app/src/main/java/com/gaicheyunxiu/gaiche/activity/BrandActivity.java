package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.BrandAdapter;
import com.gaicheyunxiu.gaiche.model.AdState;
import com.gaicheyunxiu.gaiche.model.BrandCatyState;
import com.gaicheyunxiu.gaiche.model.BrandEntity;
import com.gaicheyunxiu.gaiche.model.BrandState;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
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
 * Created by Administrator on 2016/3/20.
 * 品牌选择
 */
public class BrandActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private ListView listView;

    private BrandAdapter adapter;

    private List<BrandEntity> entities;

    private View pro;

    private String id;

    private String brandName;

    private int comeFlag;//1 代表广告 2 代表热门列表 5代表养修品牌 7代表从众筹进入

    private String type;

    private String category;

    private String supportIds;

    private String projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        initView();
    }

    private void initView() {
        comeFlag= getIntent().getIntExtra("comeFlag", 0);
        if (comeFlag==1){
            id=getIntent().getStringExtra("id");
        }else if(comeFlag==2){
            type=getIntent().getStringExtra("type");
        }else if(comeFlag==3){
            category=getIntent().getStringExtra("category");
        }else if(comeFlag==5){
            supportIds=getIntent().getStringExtra("ids");
        }else if (comeFlag==7){
            projectId=getIntent().getStringExtra("id");
        }
        brandName=getIntent().getStringExtra("name");
        entities=new ArrayList<>();
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        listView= (ListView) findViewById(R.id.brand_list);
        pro= findViewById(R.id.brand_pro);
        adapter=new BrandAdapter(BrandActivity.this,entities);
        listView.setAdapter(adapter);
        back.setOnClickListener(this);
        title.setText("品牌");
        getBrands();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < entities.size(); i++) {
                    if (i == position) {
                        entities.get(position).isSel = true;
                    } else {
                        entities.get(i).isSel = false;
                    }
                }
                adapter.notifyDataSetChanged();
                Intent intent=new Intent();
                intent.putExtra("brandName",entities.get(position).name);
                setResult(RESULT_OK,intent);
                finish();
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
     * 获取品牌
     */
    private void getBrands() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        String url="commodity/getBrand";
        MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
        if (comeFlag==1){
            rp.addBodyParameter("id", id);
            url="advertisement/getBrands";
        }else if (comeFlag==2){
            rp.addBodyParameter("type", type);
            if (carEntity!=null){
                rp.addBodyParameter("carTypeId", carEntity.carTypeId);
            }

            url="popularProject/findPopProjBrands";
        }else if (comeFlag==3){
            rp.addBodyParameter("category", category);
            url="commodity/getBrandByCategory";
        }else if(comeFlag==5){
            if (carEntity!=null){
                rp.addBodyParameter("carTypeId", carEntity.carTypeId);
            }
            rp.addBodyParameter("ids", supportIds);
            url="maintenance/findBrands";
        }else if (comeFlag==7){
            url="crowdfundingCommodity/getBrand";
            rp.addBodyParameter("projectId", projectId);
        }
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + url, rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(BrandActivity.this);
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
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
//                        if (comeFlag!=3){
//                            BrandState brandState=gson.fromJson(arg0.result,BrandState.class);
//                            for (int i=0;i<brandState.result.size();i++){
//                                if (!ToosUtils.isStringEmpty(brandName) && brandName.equals(brandState.result.get(i).name)){
//                                    brandState.result.get(i).isSel=true;
//                                }
//                                entities.add(brandState.result.get(i));
//                            }
//                        }else{
                            BrandCatyState brandCatyState=gson.fromJson(arg0.result,BrandCatyState.class);
                            for (int i=0;i<brandCatyState.result.size();i++){
                                BrandEntity entity=new BrandEntity();
                                entity.name=brandCatyState.result.get(i);
                                if (!ToosUtils.isStringEmpty(brandName) && brandName.equals(brandCatyState.result.get(i))){
                                    entity.isSel=true;
                                }
                                entities.add(entity);
//                            }

                        }

                        adapter.notifyDataSetChanged();

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(BrandActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(BrandActivity.this);
                    } else {
                        ToastUtils.displayShortToast(BrandActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(BrandActivity.this);
                }

            }
        });
    }

}
