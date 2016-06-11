package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.LogisticAdapter;
import com.gaicheyunxiu.gaiche.adapter.ShopOrderAdapter;
import com.gaicheyunxiu.gaiche.dialog.LogisticDialog;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopOrderEntity;
import com.gaicheyunxiu.gaiche.model.ShopOrderState;
import com.gaicheyunxiu.gaiche.model.ShopOrderVo;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mu on 2016/1/18.
 * 物流列表页面
 */
public class LogisticActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private LogisticAdapter adapter;

    private PullToRefreshListView listView;

    private View pro;

    private boolean proFlag = true;

    private int pageNo = 1;

    private List<ShopOrderEntity> entities;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1336:
                    //查看物流
                    int groupPoi=msg.arg2;
                    int position=msg.arg1;

                    ShopOrderVo shopOrderVo=null;
                    if ("0".equals(entities.get(groupPoi).split)){
                        shopOrderVo=entities.get(groupPoi).orderListVos.get(position);
                    }else{
                        shopOrderVo=entities.get(groupPoi).vos.get(position);
                    }
                    Gson gson=new Gson();
                    if ("0".equals(shopOrderVo.distributionType)){
                        Map<String,String> logmaps=shopOrderVo.distributionDetail;
                        if (!ToosUtils.isStringEmpty(logmaps.get("LPN")) && !ToosUtils.isStringEmpty(logmaps.get("mobile"))){
                            LogisticDialog dialog=new LogisticDialog(LogisticActivity.this,logmaps.get("LPN"),logmaps.get("mobile"));
                        }

                    }else{
                        Intent intent11=new Intent(LogisticActivity.this, LogisticDetailActivity.class);
                        Map<String,String> logmaps=shopOrderVo.distributionDetail;
                        intent11.putExtra("expressNo",logmaps.get("LPN"));
                        intent11.putExtra("expressCompany",logmaps.get("mobile"));
                        intent11.putExtra("orderId",entities.get(groupPoi).orderId);
                        intent11.putExtra("commodityId",shopOrderVo.id);
                        startActivity(intent11);
                    }

//                    Intent intent11=new Intent(LogisticActivity.this, LogisticDetailActivity.class);
//                    startActivity(intent11);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        initView();
    }

    private void initView() {

        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        listView= (PullToRefreshListView) findViewById(R.id.logistic_list);
        pro= findViewById(R.id.logistic_pro);
        title.setText("查看物流");
        back.setOnClickListener(this);

        entities=new ArrayList<>();
        adapter=new LogisticAdapter(this,entities,handler);
        listView.setAdapter(adapter);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                closePro();
                if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_START)) {
                    getOrder(1);
                } else if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                    getOrder(pageNo + 1);
                }

            }

        });

        getOrder(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }

    }


    private void openPro() {
        proFlag = true;
    }

    private void closePro() {
        proFlag = false;
    }


    /**
     * 查询商品订单
     */
    private void getOrder(final int page) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("pageNum", String.valueOf(page));
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "logistics/find", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                if (proFlag) {
                    pro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(LogisticActivity.this);
                listView.onRefreshComplete();
                pro.setVisibility(View.GONE);
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
                        LogManager.LogShow("-----***111", arg0.result,
                                LogManager.ERROR);
                        ShopOrderState shopOrderState=gson.fromJson(arg0.result,ShopOrderState.class);
                        pageNo = Integer.valueOf(page);
                        if (pageNo == 1) {
                            entities.clear();
                            adapter.notifyDataSetChanged();
                        }
                        for (int i=0;i<shopOrderState.result.size();i++){
                            entities.add(shopOrderState.result.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(LogisticActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(LogisticActivity.this);
                    } else {
                        ToastUtils.displayShortToast(LogisticActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(LogisticActivity.this);
                }

            }
        });
    }

}
