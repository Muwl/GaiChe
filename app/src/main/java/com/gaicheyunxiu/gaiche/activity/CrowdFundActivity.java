package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.CrowdfundAdapter;
import com.gaicheyunxiu.gaiche.model.CommdityEntity;
import com.gaicheyunxiu.gaiche.model.CommdityState;
import com.gaicheyunxiu.gaiche.model.CommdityVo;
import com.gaicheyunxiu.gaiche.model.CommodityState;
import com.gaicheyunxiu.gaiche.model.CrowCommityEntity;
import com.gaicheyunxiu.gaiche.model.CrowCommityState;
import com.gaicheyunxiu.gaiche.model.CrowdfundingProjectEntity;
import com.gaicheyunxiu.gaiche.model.CrowdfundingProjectState;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.HttpPostUtils;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.TimeUtils;
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
import java.util.Timer;
import java.util.TimerTask;

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

    private TextView brand;

    private ListView listView;

    private CrowdfundAdapter adapter;

    private List<CommdityVo> entities;

    private View pro;

    private Timer shopTimer;

    private TimerTask shopTask;

    protected final int UPDATE_TIME = 2257; // Grallery更新

    private String sort="";

    private CrowdfundingProjectEntity projectEntity;

    private String brandName;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TIME:
                    setCrowTime();
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowdfund);
        projectEntity= (CrowdfundingProjectEntity) getIntent().getSerializableExtra("entity");
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
        brand= (TextView) findViewById(R.id.crowdfund_price);
        listView= (ListView) findViewById(R.id.crowdfund_lisview);
        pro=  findViewById(R.id.crowdfund_pro);
        entities=new ArrayList<>();
        adapter=new CrowdfundAdapter(this,entities);
        listView.setAdapter(adapter);

        title.setText("众筹");
        back.setOnClickListener(this);
        group.check(R.id.crowdfund_default);

        pricerb.setOnClickListener(this);
        volume.setOnClickListener(this);
        brand.setOnClickListener(this);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.part_default) {
                    sort = "";
                    getFundProjectList();
                }
            }
        });

        crowed.setText(projectEntity.completeMoney);
        totalcrow.setText(projectEntity.expectMoney);
        shopTimer=new Timer();

        shopTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = UPDATE_TIME;
                handler.sendMessage(msg);
            }
        };
        shopTimer.schedule(shopTask, 1000, 1000);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CrowdFundActivity.this,ShopDetailActivity.class);
                intent.putExtra("id",entities.get(position-1).id);
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });
        getFundProjectList();

    }


    private void setCrowTime() {
        if (projectEntity != null) {
            hour.setText(TimeUtils.getHour(projectEntity.endDate));
            minute.setText(TimeUtils.getMinute(projectEntity.endDate));
            sec.setText(TimeUtils.getMin(projectEntity.endDate));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;

            case R.id.crowdfund_technology:
                if ("2".equals(sort)){
                    sort="3";
                    Drawable drawable = getResources().getDrawable(R.mipmap.sort_nor);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    pricerb.setCompoundDrawables(null, null, drawable, null);

                    Drawable drawable2 = getResources().getDrawable(R.mipmap.sort_down);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());//必须设置图片大小，否则不显示
                    volume.setCompoundDrawables(null, null, drawable2, null);

                }else{
                    sort="2";
                    Drawable drawable = getResources().getDrawable(R.mipmap.sort_nor);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    pricerb.setCompoundDrawables(null, null, drawable, null);

                    Drawable drawable2 = getResources().getDrawable(R.mipmap.sort_top);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());//必须设置图片大小，否则不显示
                    volume.setCompoundDrawables(null, null, drawable2, null);
                }
                getFundProjectList();
                break;

            case R.id.crowdfund_moods:
                if ("4".equals(sort)) {
                    sort = "5";
                    Drawable drawable = getResources().getDrawable(R.mipmap.sort_nor);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    volume.setCompoundDrawables(null, null, drawable, null);

                    Drawable drawable2 = getResources().getDrawable(R.mipmap.sort_down);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());//必须设置图片大小，否则不显示
                    pricerb.setCompoundDrawables(null, null, drawable2, null);
                }else {
                    sort = "4";

                    Drawable drawable = getResources().getDrawable(R.mipmap.sort_nor);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    volume.setCompoundDrawables(null, null, drawable, null);

                    Drawable drawable2 = getResources().getDrawable(R.mipmap.sort_top);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());//必须设置图片大小，否则不显示
                    pricerb.setCompoundDrawables(null, null, drawable2, null);
                }
                getFundProjectList();
                break;

            case R.id.crowdfund_price:
                Intent intent = new Intent(CrowdFundActivity.this, BrandActivity.class);
                intent.putExtra("comeFlag",7);
                if (!ToosUtils.isStringEmpty(brandName)) {
                    intent.putExtra("name", brandName);
                }
                startActivityForResult(intent, 1116);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1116) {
            brandName = data.getStringExtra("brandName");
            getFundProjectList();
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
        if (!ToosUtils.isStringEmpty(sort)){
            rp.addBodyParameter("sort",sort);
        }
        if (ToosUtils.isStringEmpty(brandName)){
            rp.addBodyParameter("brand","全部");
        }else{
            rp.addBodyParameter("brand",brandName);
        }
        rp.addBodyParameter("projectId",projectEntity.id);
        LogManager.LogShow("=-----", Constant.ROOT_PATH+ "crowdfundingCommodity/findByProjectId?sign="+ShareDataTool.getToken(this)+"&projectId="+projectEntity.id,LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "crowdfundingCommodity/findByProjectId", rp, new RequestCallBack<String>() {
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
                        CommdityState state1=gson.fromJson(arg0.result,CommdityState.class);
                        for (int i=0;i<state1.result.vos.size();i++){
                            entities.add(state1.result.vos.get(i));
                        }
                        adapter.notifyDataSetChanged();
                        everycrow.setText(state1.result.singleMoney+"");
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
