package com.gaicheyunxiu.gaiche.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.CarbrandActivity;
import com.gaicheyunxiu.gaiche.activity.CrowdFundActivity;
import com.gaicheyunxiu.gaiche.activity.QRScanActivity;
import com.gaicheyunxiu.gaiche.adapter.FHomeGrallryAdapter;
import com.gaicheyunxiu.gaiche.adapter.GalleryAdapter;
import com.gaicheyunxiu.gaiche.model.AdEntity;
import com.gaicheyunxiu.gaiche.model.AdState;
import com.gaicheyunxiu.gaiche.model.CarTypeEntity;
import com.gaicheyunxiu.gaiche.model.PersonDynamicEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.MyGallery;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2015/12/19.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    private ImageView code;

    private ImageView message;

    private TextView title;

    private MyGallery gallery1;

    private View carLin;

    private FHomeGrallryAdapter adapter;

    private  int width;

    private LinearLayout lin = null;

    private GalleryAdapter galleryAdapter;

    private ImageView shop;

    private Timer timer;

    private TimerTask task;

    protected final int UPDATE_GALLERY = 10; // Grallery更新

    private MyGallery gallery = null;

    private LinearLayout galllin = null;

    private int screenWidth = 0;

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
        };
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        code= (ImageView) view.findViewById(R.id.title_code);
        message= (ImageView) view.findViewById(R.id.title_message);
        title= (TextView) view.findViewById(R.id.title_text);
        gallery1= (MyGallery) view.findViewById(R.id.home_grally);
        carLin=view.findViewById(R.id.main_carlin);
        shop= (ImageView) view.findViewById(R.id.home_shop);
        lin = (LinearLayout) view.findViewById(R.id.home_lin);
        gallery = (MyGallery) view.findViewById(R.id.gallery);
        galllin = (LinearLayout) view.findViewById(R.id.lin);
        code.setVisibility(View.VISIBLE);
        message.setVisibility(View.VISIBLE);
        view.findViewById(R.id.title_back).setVisibility(View.GONE);
        title.setText("盖车云修");
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        width= DensityUtil.getScreenWidth(getActivity());
        adapter=new FHomeGrallryAdapter(getActivity(),width);
        gallery1.setAdapter(adapter);
        carLin.setOnClickListener(this);
        code.setOnClickListener(this);
        shop.setOnClickListener(this);
        int m = DensityUtil.dip2px(getActivity(), 3);
        for (int i = 0; i <3; i++) {
            ImageView image = (ImageView) LayoutInflater.from(getActivity())
                    .inflate(R.layout.banner_point, null);
            image.setPadding(m, 0, m, 0);
            lin.addView(image);
        }
        gallery1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ImageView iv;
                int count = lin.getChildCount();
                for (int i = 0; i < count; i++) {
                    iv = (ImageView) lin.getChildAt(i);
                    if (i == position % 3) {
                        iv.setImageResource(R.drawable.circle_select);
                    } else {
                        iv.setImageResource(R.drawable.circle_normal);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getAd();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_code:
                Intent intent=new Intent(getActivity(), QRScanActivity.class);
                startActivity(intent);
                break;
            case R.id.main_carlin:
                Intent intent2=new Intent(getActivity(), CarbrandActivity.class);
                startActivityForResult(intent2, 1224);
                break;

            case R.id.home_shop:
                Intent intent3=new Intent(getActivity(), CrowdFundActivity.class);
                startActivity(intent3);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1224 && resultCode== Activity.RESULT_OK){
            CarTypeEntity entity= (CarTypeEntity) data.getSerializableExtra("entity");
        }

    }


    /**
     * 获取广告
     */
    private void getAd() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "advertisement/getAd", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(getActivity());
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        AdState adState = gson.fromJson(arg0.result, AdState.class);
                        addBunne(adState.result);
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(getActivity(),
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(getActivity());
                    } else {
                        ToastUtils.displayShortToast(getActivity(),
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(getActivity());
                }

            }
        });
    }

    /**
     * 加载bunne图片
     */
    private void addBunne(final List<AdEntity> images) {
        int m = DensityUtil.dip2px(getActivity(), 3);
        for (int i = 0; i < images.size(); i++) {
            ImageView image = (ImageView) LayoutInflater.from(getActivity()).inflate(
                    R.layout.detail_point, null);
            image.setPadding(m, 0, m, 0);
            galllin.addView(image);
        }
        galleryAdapter = new GalleryAdapter(getActivity(), gallery, images, width);
        gallery.setAdapter(galleryAdapter);
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ImageView iv;
                int count = lin.getChildCount();
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
}
