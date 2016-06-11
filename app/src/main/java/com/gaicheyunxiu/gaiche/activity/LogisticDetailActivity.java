package com.gaicheyunxiu.gaiche.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.LogisticDetailAdapter;
import com.gaicheyunxiu.gaiche.dialog.LogisticDialog;
import com.gaicheyunxiu.gaiche.model.ResultDataState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.RouteInfo;
import com.gaicheyunxiu.gaiche.model.WLlogistic;
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
 * Created by Mu on 2016/1/18.
 * 物流详情页面
 */
public class LogisticDetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private ListView listView;

    private LogisticDetailAdapter adapter;

    private TextView source;

    private TextView no;

    private TextView lohstate;

    private List<RouteInfo> infos = new ArrayList<RouteInfo>();

    private String expressNo;

    private String expressCompany;

    private String orderId;

    private String commodityId;

    private View pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logisticdetail);
        expressNo=getIntent().getStringExtra("expressNo");
        expressCompany=getIntent().getStringExtra("expressCompany");
        orderId=getIntent().getStringExtra("orderId");
        commodityId=getIntent().getStringExtra("commodityId");
        initView();

        if (ToosUtils.isStringEmpty(expressNo)){
            expressNo="";
        }if (ToosUtils.isStringEmpty(expressCompany)){
            expressCompany="";
        }

        source.setText("信息涞源："+expressCompany);
        no.setText("运单号：" + expressNo);
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        listView= (ListView) findViewById(R.id.logisticdetail_list);
        source= (TextView) findViewById(R.id.logisticdetail_source);
        lohstate= (TextView) findViewById(R.id.logistic_state);
        no= (TextView) findViewById(R.id.logisticdetail_no);
        pro= findViewById(R.id.logisticdetail_pro);

        title.setText("查看物流");
        back.setOnClickListener(this);

        getDetail();
//
//        infos.add(new  RouteInfo("感谢您在京东购物，欢迎您再次光临 jd.com ", "2014-06-19 10:39:17"));
//        infos.add(new  RouteInfo("配送员【李四】已出发，联系电话【18523456789】站点电话【13923459876】", "2014-06-19 10:14:17"));
//        infos.add(new  RouteInfo("您的订单在【运城分拨中心】验货完成，正在分配派送员派件", "2014-06-19 09:34:38"));
//        infos.add(new  RouteInfo("您的订单在【太原中转中心】验货完成，正在派往【运城分拨中心】", "2014-06-19 07:34:38"));
//        infos.add(new  RouteInfo("你的订单在【太原分拨中心】发货完成，准备送往【太原中转中心】", "2014-06-18 20:14:07"));
//        infos.add(new  RouteInfo("您的订单在【太原分拨中心】分拣完成", "2014-06-18 18:45:51"));
//        infos.add(new  RouteInfo("您的订单已打包完毕", "2014-06-18 18:43:47"));
//        infos.add(new RouteInfo("您的订单已通过扫描", "2014-06-18 18:27:10"));
//        infos.add(new RouteInfo("您提交了订单，请等待系统确认", "2014-06-18 18:14:17"));
//        adapter=new LogisticDetailAdapter(this,infos);
//        listView.setAdapter(adapter);
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
     *物流详情
     */
    private void getDetail() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("orderId",orderId);
        rp.addBodyParameter("commodityId",commodityId);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "logistics/viewLogistics", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(LogisticDetailActivity.this);
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
                        ResultDataState state1=gson.fromJson(arg0.result,ResultDataState.class);
                        for (int i=0;i<state1.result.size();i++){
                            RouteInfo routeInfo= new  RouteInfo(state1.result.get(i).context, state1.result.get(i).time);
                            infos.add(routeInfo);
                        }
                        adapter=new LogisticDetailAdapter(LogisticDetailActivity.this,infos);
                        listView.setAdapter(adapter);

                        if (infos.size()!=0){
                            lohstate.setText("物流状态："+infos.get(0).getInfo());
                        }

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(LogisticDetailActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(LogisticDetailActivity.this);
                    } else {
                        ToastUtils.displayShortToast(LogisticDetailActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(LogisticDetailActivity.this);
                }

            }
        });
    }
}
