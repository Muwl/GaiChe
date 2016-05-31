package com.gaicheyunxiu.gaiche.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.ReturnState;
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
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by Administrator on 2016/2/11.
 * 分享
 */
public class ShareActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private TextView weixin;

    private TextView weixinCricle;

    private TextView qq;

    private TextView qqCricle;

    private TextView sina;

    private UMShareListener umShareListener;

    private String url;

    private String topic;

    private String content;

    private UMImage umImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        weixin = (TextView) findViewById(R.id.share_weixin);
        weixinCricle = (TextView) findViewById(R.id.share_weixincricle);
        qq = (TextView) findViewById(R.id.share_qq);
        qqCricle = (TextView) findViewById(R.id.share_qqcricle);
        sina = (TextView) findViewById(R.id.share_sina);

        title.setText("分享");
        back.setOnClickListener(this);
        weixin.setOnClickListener(this);
        weixinCricle.setOnClickListener(this);
        qq.setOnClickListener(this);
        qqCricle.setOnClickListener(this);
        sina.setOnClickListener(this);
        topic = "盖车云修";
        content = "欢迎下载盖车云修！";
        umImage = new UMImage(this, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        Dialog progressDialog = new Dialog(this,R.style.progress_dialog);
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Config.dialog=progressDialog;
        getShare();

        umShareListener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(ShareActivity.this, platform + " 分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(ShareActivity.this, platform + " 分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(ShareActivity.this, platform + " 分享取消", Toast.LENGTH_SHORT).show();
            }
        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.share_weixin:
                new ShareAction(ShareActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withText(content)
                        .withTitle(topic)
                        .withTargetUrl(url)
                        .withMedia(umImage)
                        .share();
                break;
            case R.id.share_weixincricle:
                new ShareAction(ShareActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withText(content)
                        .withTitle(topic)
                        .withTargetUrl(url)
                        .withMedia(umImage)
                        .share();

                break;
            case R.id.share_qq:
                new ShareAction(ShareActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withText(content)
                        .withTitle(topic)
                        .withTargetUrl(url)
                        .withMedia(umImage)
                        .share();
                break;
            case R.id.share_qqcricle:
                new ShareAction(ShareActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withText(content)
                        .withTitle(topic)
                        .withTargetUrl(url)
                        .withMedia(umImage)
                        .share();
                break;
            case R.id.share_sina:
                new ShareAction(ShareActivity.this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .setCallback(umShareListener)
                        .withText(content)
                        .withTitle(topic)
                        .withTargetUrl(url)
                        .withMedia(umImage)
                        .share();
                break;
        }
    }

    /**
     * 查询所有品牌
     */
    private void getShare() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "share/get", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(ShareActivity.this);
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
                        url = (String) state.result;
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ShareActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ShareActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ShareActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ShareActivity.this);
                }

            }
        });
    }
}
