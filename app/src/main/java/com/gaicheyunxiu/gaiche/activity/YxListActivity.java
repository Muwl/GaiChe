package com.gaicheyunxiu.gaiche.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.YxListAdapter;
import com.gaicheyunxiu.gaiche.model.CommodityEntity;
import com.gaicheyunxiu.gaiche.model.CommodityState;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.YxEntity;
import com.gaicheyunxiu.gaiche.model.YxState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.HttpPostUtils;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
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
 * Created by Administrator on 2016/6/6.
 */
public class YxListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private View carLin;

    private ImageView carImage;

    private ImageView carAddImage;

    private TextView carName;

    private ListView listView;

    private TextView money;

    private TextView kefu;

    private TextView ok;

    private View pro;

    private YxListAdapter listAdapter;

    private List<YxEntity> entities;

    private BitmapUtils bitmapUtils;

    private String ids;

    private double totalMoney;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HttpPostUtils.FIND_MYCAR:
                    MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
                    if (carEntity!=null){
                        bitmapUtils.display(carImage,carEntity.carBrandLogo);
                        carAddImage.setVisibility(View.GONE);
                        carName.setText(carEntity.carBrandName+carEntity.type +carEntity.displacement+carEntity.productionDate+"年产");
                    }
                    break;

                case 1662:
                    Intent intent=new Intent(YxListActivity.this,YxListEditActivity.class);
                    int poi= (int) msg.obj;
                    intent.putExtra("position",poi);
                    intent.putExtra("name",entities.get(poi).name);
                    intent.putExtra("ids",entities.get(poi).id);
                    intent.putExtra("entity", (Serializable) entities.get(poi).vos);
                    startActivityForResult(intent,7884);

                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yxlist);
        initView();
        if (ToosUtils.goBrand(this, 0)){
            return;
        }
        getAdShop();
    }

    private void initView() {
        ids=getIntent().getStringExtra("ids");
        back = (ImageView) findViewById(R.id.title_back);
        title = (TextView) findViewById(R.id.title_text);
        carLin = findViewById(R.id.yxlist_carlin);
        carImage = (ImageView) findViewById(R.id.yxlist_carimage);
        carAddImage = (ImageView) findViewById(R.id.yxlist_caraddimage);
        carName = (TextView) findViewById(R.id.yxlist_carbrand);
        listView = (ListView) findViewById(R.id.yxlist_lisview);
        money = (TextView) findViewById(R.id.yxlist_sermoney);
        kefu = (TextView) findViewById(R.id.yxlist_serkehu);
        ok = (TextView) findViewById(R.id.yxlist_serok);
        pro = findViewById(R.id.yxlist_pro);
        bitmapUtils=new BitmapUtils(this);
        entities=new ArrayList<>();
        listAdapter=new YxListAdapter(this,entities,handler);
        listView.setAdapter(listAdapter);


        title.setText("养修");
        back.setOnClickListener(this);
        carLin.setOnClickListener(this);
        ok.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
        if (carEntity!=null){
            bitmapUtils.display(carImage,carEntity.carBrandLogo);
            carAddImage.setVisibility(View.GONE);
            carName.setText(carEntity.carBrandName+carEntity.type +carEntity.displacement+carEntity.productionDate+"年产");
        }else{
            HttpPostUtils.getMyCar(this, handler);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!= Activity.RESULT_OK && requestCode==8856 ){
            finish();
        }
        if (requestCode==1224 && resultCode== Activity.RESULT_OK){
            MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
            if (carEntity!=null){
                bitmapUtils.display(carImage,carEntity.carBrandLogo);
                carAddImage.setVisibility(View.GONE);
                carName.setText(carEntity.carBrandName+carEntity.type+carEntity.displacement+carEntity.productionDate+"年产");
            }
            LogManager.LogShow("----------**&&", carEntity.toString(), LogManager.ERROR);
        }

        if (requestCode==8856){
            getAdShop();
        }

        if (requestCode==7884 && resultCode==RESULT_OK){
            int posi=data.getIntExtra("position",0);
            List<CommodityEntity> commodityEntities= (List<CommodityEntity>) data.getSerializableExtra("entity");
            entities.get(posi).vos.clear();
            for (int i=0;i<commodityEntities.size();i++){
                entities.get(posi).vos.add(commodityEntities.get(i));
            }
            listAdapter.notifyDataSetChanged();
            setMoney();
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;

            case R.id.yxlist_carlin:

                MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
                if (carEntity==null){
                    ToosUtils.goBrand(YxListActivity.this,1);
                }else{
                    Intent intent1=new Intent(YxListActivity.this, CarmanagerActivity.class);
                    startActivityForResult(intent1, 8856);
                }
                break;

            case R.id.yxlist_serok:
                Intent intent=new Intent(YxListActivity.this,ClearingActivity.class);

                List<CommodityEntity> commodityEntities=new ArrayList<>();
                for (int i=0;i<entities.size();i++){
                    for (int j=0;j<entities.get(i).vos.size();j++){
                        commodityEntities.add(entities.get(i).vos.get(j));
                    }
                }
                intent.putExtra("entity", (Serializable) commodityEntities);
                intent.putExtra("flag", 4);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取广告商品
     */
    private void getAdShop() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url="maintenance/findDefaultMaceComm";

        MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
        rp.addBodyParameter("carTypeId", carEntity.carTypeId);
        rp.addBodyParameter("ids", ids);
        if (!ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
            rp.addBodyParameter("sign",ShareDataTool.getToken(this));
        }
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + url, rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(YxListActivity.this);
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
                        YxState yxState = gson.fromJson(arg0.result, YxState.class);
                        entities.clear();
                        if (yxState.result != null && yxState.result.size() > 0) {
                            for (int i = 0; i < yxState.result.size(); i++) {
                                entities.add(yxState.result.get(i));
                            }
                        }
                        listAdapter.notifyDataSetChanged();
                        setMoney();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(YxListActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(YxListActivity.this);
                    } else {
                        ToastUtils.displayShortToast(YxListActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(YxListActivity.this);
                }

            }
        });
    }

    private void setMoney(){
        double totalMon=0;
        for (int i=0;i<entities.size();i++){
            for (int j=0;j<entities.get(i).vos.size();j++){
                double m= Double.parseDouble(entities.get(i).vos.get(j).originalPrice);
                int num=Integer.valueOf(entities.get(i).vos.get(j).num);
                totalMon=totalMon+m*num;
            }
        }
        totalMoney=totalMon;
        money.setText("￥"+totalMon);
    }
}
