package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.utils.DataCleanManager;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2016/2/11.
 * 设置页面
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private View freeback;

    private View share;

    private TextView clear;

    private View about;

    private View logout;

    private BitmapUtils bitmapUtils;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 40:
                    int flag=msg.arg1;
                    if (flag==-5){
                        ToosUtils.goReLogin(SettingActivity.this);
                    }else if(flag==-2){
                        DataCleanManager.clearAllCache(SettingActivity.this);
                        bitmapUtils.clearCache();
                        bitmapUtils.clearDiskCache();
                        bitmapUtils.clearMemoryCache();

                        try {
                            clear.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        freeback=findViewById(R.id.setting_freeback);
        share=findViewById(R.id.setting_share);
        clear= (TextView) findViewById(R.id.setting_catch);
        about=findViewById(R.id.setting_about);
        logout=findViewById(R.id.setting_logout);
        bitmapUtils = new BitmapUtils(this);
        back.setOnClickListener(this);
        title.setText("设置");
        back.setOnClickListener(this);
        freeback.setOnClickListener(this);
        share.setOnClickListener(this);
        clear.setOnClickListener(this);
        about.setOnClickListener(this);
        logout.setOnClickListener(this);
        try {
            clear.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;

            case R.id.setting_freeback:
                Intent intent=new Intent(SettingActivity.this,FreebackActivity.class);
                startActivity(intent);
                break;

            case R.id.setting_share:
                Intent intent1=new Intent(SettingActivity.this,ShareActivity.class);
                startActivity(intent1);

                break;

            case R.id.setting_catch:
                CustomeDialog customeDialog=new CustomeDialog(SettingActivity.this,handler,"确定清楚缓存？",-2,-1);
                break;

            case R.id.setting_about:
                Intent intent2=new Intent(SettingActivity.this,AboutActivity.class);
                startActivity(intent2);
                break;

            case R.id.setting_logout:
                CustomeDialog customeDialog2=new CustomeDialog(SettingActivity.this,handler,"确定退出？",-5,-1);

                break;
        }
    }
}
