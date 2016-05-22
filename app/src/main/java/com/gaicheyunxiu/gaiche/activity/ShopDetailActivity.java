package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.GalleryAdapter;
import com.gaicheyunxiu.gaiche.adapter.ShopDetailAdapter;
import com.gaicheyunxiu.gaiche.adapter.ShopGalleryAdapter;
import com.gaicheyunxiu.gaiche.model.AdEntity;
import com.gaicheyunxiu.gaiche.model.EvaluationEntity;
import com.gaicheyunxiu.gaiche.model.EvaluationState;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopDetailEntity;
import com.gaicheyunxiu.gaiche.model.ShopDetailState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.gaicheyunxiu.gaiche.utils.HttpPostUtils;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.MyGallery;
import com.gaicheyunxiu.gaiche.view.RatingBar;
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
 * Created by Administrator on 2016/3/20.
 * 商品详情
 */
public class ShopDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private ListView listView;

    private ShopDetailAdapter adapter;

    private String id;

    private View pro;

    protected final int UPDATE_GALLERY = 10; // Grallery更新

    private MyGallery gallery = null;

    private LinearLayout galllin = null;

    private ShopDetailEntity detailEntity;

    private TextView numView;

    private TextView name;

    private RatingBar ratingBar;

    private TextView newPrice;

    private TextView mView;

    private TextView oldMoney;

    private TextView volumeView;

    private TextView addCart;

    private TextView buy;

    private TextView shopNo;

    private TextView afterservice;

    private TextView evalnum;

    private TextView evalscore;

    private ShopGalleryAdapter galleryAdapter;

    private int width;

    private Timer timer;

    private TimerTask task;

    private List<EvaluationEntity> entities;

    private TextView infoView;

    public  ImageView incream;

    public ImageView add;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_GALLERY:
                    if (gallery.getSelectedItemPosition() == 0) {
                        gallery.setSelection(gallery.getFirstVisiblePosition() + 1);
                    } else {
                        gallery.setSelection(gallery.getFirstVisiblePosition() + 2);
                    }
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);
        initView();
    }

    private void initView() {
        width = DensityUtil.getScreenWidth(this);
        id = getIntent().getStringExtra("id");
        back = (ImageView) findViewById(R.id.title_back);
        title = (TextView) findViewById(R.id.title_text);
        gallery = (MyGallery) findViewById(R.id.shopdetail_gallery);
        numView = (TextView) findViewById(R.id.shopdetail_num);
        galllin = (LinearLayout) findViewById(R.id.shopdetail_lin);
        listView = (ListView) findViewById(R.id.shopdetail_list);
        name = (TextView) findViewById(R.id.shopdetail_name);
        ratingBar = (RatingBar) findViewById(R.id.shopdetail_bar);
        newPrice = (TextView) findViewById(R.id.shopdetail_newprice);
        mView = (TextView) findViewById(R.id.shopdetail_m);
        oldMoney = (TextView) findViewById(R.id.shopdetail_oldmoney);
        volumeView = (TextView) findViewById(R.id.shopdetail_volume);
        shopNo = (TextView) findViewById(R.id.shopdetail_no);
        infoView = (TextView) findViewById(R.id.shopdetail_info);
        addCart = (TextView) findViewById(R.id.shopdetail_addcart);
        add = (ImageView) findViewById(R.id.shopdetail_add);
        incream = (ImageView) findViewById(R.id.shopdetail_incream);
        buy = (TextView) findViewById(R.id.shopdetail_buy);
        pro = findViewById(R.id.shopdetail_pro);
        afterservice = (TextView) findViewById(R.id.shopdetail_afterservice);
        evalnum = (TextView) findViewById(R.id.shopdetail_evalnum);
        evalscore = (TextView) findViewById(R.id.shopdetail_evalscore);


        entities = new ArrayList<>();
        adapter = new ShopDetailAdapter(this, entities);
        listView.setAdapter(adapter);
        title.setText("商品详情");
        title.setOnClickListener(this);
        buy.setOnClickListener(this);
        addCart.setOnClickListener(this);
        back.setOnClickListener(this);
        incream.setOnClickListener(this);
        add.setOnClickListener(this);
        infoView.setOnClickListener(this);
        oldMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        getShopDetail(id);
        getEvalute(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.shopdetail_info:
                Intent intent = new Intent(ShopDetailActivity.this, ShopDetailInfoActivity.class);
                intent.putExtra("url", Constant.ROOT_PATH + "commodity/findIntroduction?id=" + id);
                startActivity(intent);
                break;

            case R.id.shopdetail_addcart:
                addshoppingCart();
                break;
            case R.id.shopdetail_buy:
                Intent intent1=new Intent(ShopDetailActivity.this,ClearingActivity.class);
                intent1.putExtra("flag",2);
                intent1.putExtra("entity",detailEntity);
                startActivity(intent1);
                break;

            case R.id.shopdetail_add:
                if (detailEntity!=null){
                    detailEntity.num=detailEntity.num+1;
                    numView.setText(detailEntity.num+"");
                }
                break;

            case R.id.shopdetail_incream:
                if (detailEntity!=null){
                    if (detailEntity.num>1){
                        detailEntity.num=detailEntity.num-1;
                        numView.setText(detailEntity.num+"");
                    }

                }
                break;
        }
    }


    /**
     * 根据商品id查询商品详情
     */
    private void getShopDetail(String id) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("id", id);
        utils.configTimeout(20000);
        LogManager.LogShow("-----", Constant.ROOT_PATH + "commodity/findById?id=" + id,
                LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "commodity/findById", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(ShopDetailActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                LogManager.LogShow("-----", arg0.result + "=====",
                        LogManager.ERROR);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        ShopDetailState shopDetailState = gson.fromJson(arg0.result, ShopDetailState.class);
                        detailEntity = shopDetailState.result;
                        detailEntity.num=1;
                        setValue();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ShopDetailActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ShopDetailActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ShopDetailActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    LogManager.LogShow("-----", e.toString(),
                            LogManager.ERROR);
                    ToastUtils.displaySendFailureToast(ShopDetailActivity.this);
                }

            }
        });
    }

    private void setValue() {
        name.setText(detailEntity.name);
        ratingBar.setStar(detailEntity.evaluationScore);
        newPrice.setText("￥" + detailEntity.presentPrice + "元");
        mView.setText(detailEntity.MValue + "M");
        oldMoney.setText("￥" + detailEntity.originalPrice + "元");
        volumeView.setText("销量" + detailEntity.paymentNum + "笔");
        evalnum.setText("用户评论（" + detailEntity.evaluationNum + "）");
        if (!ToosUtils.isStringEmpty(detailEntity.specification)) {
            shopNo.setText(Html.fromHtml(detailEntity.specification));
        }
        if (!ToosUtils.isStringEmpty(detailEntity.afterSalesService)) {
            afterservice.setText(Html.fromHtml(detailEntity.afterSalesService));
        }
        evalscore.setText("商品综合满意度：" + detailEntity.evaluationScore + "分，共" + detailEntity.evaluationNum + "条");
        addBunne(detailEntity.detailImage);
    }


    /**
     * 根据商品id查询商品评价
     */
    private void getEvalute(String id) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("commodityId", id);
        utils.configTimeout(20000);
        LogManager.LogShow("-----", Constant.ROOT_PATH + "commodityEvaluation/findByCommodityId?commodityId=" + id,
                LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "commodityEvaluation/findByCommodityId", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(ShopDetailActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                LogManager.LogShow("-----", arg0.result + "=====",
                        LogManager.ERROR);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        EvaluationState evaluationState = gson.fromJson(arg0.result, EvaluationState.class);
                        for (int i = 0; i < evaluationState.result.size(); i++) {
                            entities.add(evaluationState.result.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ShopDetailActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ShopDetailActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ShopDetailActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ShopDetailActivity.this);
                }

            }
        });
    }


    /**
     * 加载bunne图片
     */
    private void addBunne(final List<String> images) {
        int m = DensityUtil.dip2px(this, 3);
        for (int i = 0; i < images.size(); i++) {
            ImageView image = (ImageView) LayoutInflater.from(this).inflate(
                    R.layout.detail_point, null);
            image.setPadding(m, 0, m, 0);
            galllin.addView(image);
        }
        galleryAdapter = new ShopGalleryAdapter(this, gallery, images, width);
        gallery.setAdapter(galleryAdapter);
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ImageView iv;
                int count = galllin.getChildCount();
                for (int i = 0; i < count; i++) {
                    iv = (ImageView) galllin.getChildAt(i);
                    if (i == position % images.size()) {
                        iv.setImageResource(R.mipmap.circle_select);
                    } else {
                        iv.setImageResource(R.mipmap.circle_normal);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = UPDATE_GALLERY;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(task, 1000, 3000);
    }


    /**
     * 新增购物车
     */
    private void addshoppingCart() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        MyCarEntity carEntity = MyApplication.getInstance().getCarEntity();
        if (carEntity != null) {
            rp.addBodyParameter("carTypeId", carEntity.carTypeId);
        }
        rp.addBodyParameter("commodityId", id);
        rp.addBodyParameter("num", ToosUtils.getTextContent(numView));
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "shoppingCart/save", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(ShopDetailActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                LogManager.LogShow("-----", arg0.result + "=====",
                        LogManager.ERROR);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        ToastUtils.displayShortToast(ShopDetailActivity.this,
                                "添加成功！");
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ShopDetailActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ShopDetailActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ShopDetailActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    LogManager.LogShow("-----", e.toString(),
                            LogManager.ERROR);
                    ToastUtils.displaySendFailureToast(ShopDetailActivity.this);
                }

            }
        });
    }
}
