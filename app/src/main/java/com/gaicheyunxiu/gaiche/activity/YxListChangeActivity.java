package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.BrandAdapter;
import com.gaicheyunxiu.gaiche.adapter.YxListEditAdapter;
import com.gaicheyunxiu.gaiche.model.BrandCatyState;
import com.gaicheyunxiu.gaiche.model.BrandEntity;
import com.gaicheyunxiu.gaiche.model.CommodityEntity;
import com.gaicheyunxiu.gaiche.model.CommodityState;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.YxChangeState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/8.
 */
public class YxListChangeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private BitmapUtils bitmapUtils;

    private ImageView carImage;

    private TextView carName;

    private  TextView serviceName;

    private TextView save;

    private PullToRefreshListView listView;

    private List<CommodityEntity> commodityEntities;

    private YxListEditAdapter adapter;

    private View pro;

    private int position;

    private String serName;

    private RadioGroup group;

    private RadioButton shoprb;

    private RadioButton brandrb;

    private boolean proFlag = true;

    private int pageNo = 1;

    private String brandName="全部";

    private BrandAdapter brandAdapter;

    private List<BrandEntity> brandEntities;

    private String ids;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yxchange);
        initView();
    }

    private void initView() {
        bitmapUtils=new BitmapUtils(this);
        ids=getIntent().getStringExtra("id");
        commodityEntities= new ArrayList<>();

        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        carImage= (ImageView) findViewById(R.id.yxlist_carimage);
        carName= (TextView) findViewById(R.id.yxlist_carbrand);
        group= (RadioGroup) findViewById(R.id.yxlist_group);
        shoprb= (RadioButton) findViewById(R.id.yxlist_shoprb);
        brandrb= (RadioButton) findViewById(R.id.yxlist_brandrb);
        serviceName= (TextView) findViewById(R.id.yxlist_name);
        save= (TextView) findViewById(R.id.yxlist_save);
        listView= (PullToRefreshListView) findViewById(R.id.yxlist_list);
        pro= findViewById(R.id.yxlist_pro);

        title.setText("养修");
        back.setOnClickListener(this);
        save.setOnClickListener(this);
        MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();

        bitmapUtils.display(carImage, carEntity.carBrandLogo);
        carName.setText(carEntity.carBrandName+carEntity.type +carEntity.displacement+carEntity.productionDate+"年产");
        serviceName.setText(serName);

        brandEntities=new ArrayList<>();
        brandAdapter=new BrandAdapter(this,brandEntities);

        adapter=new YxListEditAdapter(this,commodityEntities,handler);
        listView.setAdapter(adapter);

        group.check(R.id.yxlist_shoprb);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yxlist_shoprb) {
                    listView.setAdapter(adapter);
                    listView.setMode(PullToRefreshBase.Mode.BOTH);
                } else {
                    listView.setAdapter(brandAdapter);
                    listView.setMode(PullToRefreshBase.Mode.DISABLED);
                    openPro();
                    getBrands();
                }
            }
        });

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                closePro();
                if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_START)) {

                    getMaceComm(1);
                } else if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                    getMaceComm(pageNo + 1);
                }

            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (group.getCheckedRadioButtonId() == R.id.yxlist_shoprb) {
                    Intent intent=new Intent();
                    intent.putExtra("position",position);
                    intent.putExtra("entity",commodityEntities.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    brandName=brandEntities.get(position).name;
                    group.check(R.id.yxlist_shoprb);
                    openPro();
                    getMaceComm(1);
                }
            }
        });

        getMaceComm(1);

    }


    private void openPro(){
        proFlag=true;
    }

    private void closePro(){
        proFlag=false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;

            case R.id.yxlist_save:
                Intent intent=new Intent();
                intent.putExtra("position",-1);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    /**
     * 查询养修所有商品
     */
    private void getMaceComm(final int page) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url="maintenance/findAllMaceComm";
        MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
        if(!ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
            rp.addBodyParameter("sign",ShareDataTool.getToken(this));
        }
        if (carEntity!=null){
            rp.addBodyParameter("carTypeId",carEntity.carTypeId);
        }
        rp.addBodyParameter("pageNo", String.valueOf(page));
        rp.addBodyParameter("ids", ids);
        rp.addBodyParameter("sort", "0");
        rp.addBodyParameter("brand",brandName);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + url, rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                if (proFlag) {
                    pro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(YxListChangeActivity.this);
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
                        YxChangeState changeState = gson.fromJson(arg0.result, YxChangeState.class);
                        pageNo = Integer.valueOf(page);
                        if (pageNo == 1) {
                            commodityEntities.clear();
                            adapter.notifyDataSetChanged();
                        }
                        if (changeState.result != null && changeState.result.size() > 0) {
                            for (int i = 0; i < changeState.result.size(); i++) {
                                commodityEntities.add(changeState.result.get(i));
                            }
                            adapter.notifyDataSetChanged();
                        }

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(YxListChangeActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(YxListChangeActivity.this);
                    } else {
                        ToastUtils.displayShortToast(YxListChangeActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(YxListChangeActivity.this);
                }

            }
        });
    }

    /**
     * 查询所有养修项目商品品牌
     */
    private void getBrands() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        String url="maintenance/findBrands";
        MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
        if (carEntity!=null){
            rp.addBodyParameter("carTypeId", carEntity.carTypeId);
        }
        rp.addBodyParameter("ids", ids);
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
                ToastUtils.displayFailureToast(YxListChangeActivity.this);
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
                        BrandCatyState brandCatyState=gson.fromJson(arg0.result,BrandCatyState.class);
                        for (int i=0;i<brandCatyState.result.size();i++){
                            BrandEntity entity=new BrandEntity();
                            entity.name=brandCatyState.result.get(i);
                            if (!ToosUtils.isStringEmpty(brandName) && brandName.equals(brandCatyState.result.get(i))){
                                entity.isSel=true;
                            }
                            brandEntities.add(entity);
                        }
                        brandAdapter.notifyDataSetChanged();

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(YxListChangeActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(YxListChangeActivity.this);
                    } else {
                        ToastUtils.displayShortToast(YxListChangeActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(YxListChangeActivity.this);
                }

            }
        });
    }

}
