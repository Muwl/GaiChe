package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.PartsAdapter;
import com.gaicheyunxiu.gaiche.model.AdState;
import com.gaicheyunxiu.gaiche.model.CommodityEntity;
import com.gaicheyunxiu.gaiche.model.CommodityState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.impl.cookie.BestMatchSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/14.
 * 配件列表页面
 */
public class PartActivity  extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView car;

    private View carLin;

    private RadioGroup group;

    private RadioButton defaultrb;

    private RadioButton pricerb;

    private RadioButton volume;

    private TextView brand;

    private PullToRefreshListView listView;

    private boolean proFlag = true;

    private int pageNo = 1;

    private PartsAdapter adapter;

    private String id;

    private View pro;

    private String brandName;

    private List<CommodityEntity> commodityEntityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part);
        initView();
    }

    private void initView() {
        id=getIntent().getStringExtra("id");
        commodityEntityList=new ArrayList<>();
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        car= (TextView) findViewById(R.id.part_carbrand);
        carLin=findViewById(R.id.part_carlin);
        group= (RadioGroup) findViewById(R.id.part_rb);
        defaultrb= (RadioButton) findViewById(R.id.part_default);
        pricerb= (RadioButton) findViewById(R.id.part_moods);
        volume= (RadioButton) findViewById(R.id.part_technology);
        brand= (TextView) findViewById(R.id.part_price);
        listView= (PullToRefreshListView) findViewById(R.id.part_lisview);
        pro=  findViewById(R.id.part_pro);
        back.setOnClickListener(this);
        adapter=new PartsAdapter(this,commodityEntityList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PartActivity.this, ShopDetailActivity.class);
                startActivity(intent);
            }
        });

        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartActivity.this, BrandActivity.class);
                intent.putExtra("id",id);
                if (!ToosUtils.isStringEmpty(brandName)){
                    intent.putExtra("name",brandName);
                }
                startActivityForResult(intent, 1116);
            }
        });
        group.check(R.id.part_default);

        title.setText("商品列表");

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                closePro();
                if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_START)) {
                    getAdShop(1);
                } else if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                    getAdShop(pageNo + 1);
                }

            }

        });
        getAdShop(1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=RESULT_OK){
            return;
        }
        if (requestCode==1116){
            brandName=data.getStringExtra("brandName");
            getAdShop(1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }

    private void openPro(){
        proFlag=true;
    }

    private void closePro(){
        proFlag=false;
    }



    /**
     * 获取广告
     */
    private void getAdShop(final int page) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("id", id);
        rp.addBodyParameter("pageNo", pageNo + "");
        rp.addBodyParameter("sort","0");
        if (ToosUtils.isStringEmpty(brandName)){
            rp.addBodyParameter("brand","全部");
        }else{
            rp.addBodyParameter("brand",brandName);
        }

        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "advertisement/detail", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                if (proFlag) {
                    pro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(PartActivity.this);
                pro.setVisibility(View.GONE);
                listView.onRefreshComplete();
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                listView.onRefreshComplete();
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        CommodityState commodityState = gson.fromJson(arg0.result, CommodityState.class);
                        pageNo = Integer.valueOf(page);
                        if (pageNo == 1) {
                            commodityEntityList.clear();
                            adapter.notifyDataSetChanged();
                        }
                        if (commodityState.result != null && commodityState.result.size() > 0) {
                            for (int i = 0; i < commodityState.result.size(); i++) {
                                commodityEntityList.add(commodityState.result.get(i));
                            }
                            adapter.notifyDataSetChanged();
                        }

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(PartActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(PartActivity.this);
                    } else {
                        ToastUtils.displayShortToast(PartActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(PartActivity.this);
                }

            }
        });
    }

}
