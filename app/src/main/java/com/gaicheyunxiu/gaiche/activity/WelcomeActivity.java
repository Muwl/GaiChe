package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.utils.FileTool;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import java.io.IOException;

/**
 * Created by Mu on 2015/12/21.
 */
public class WelcomeActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        try {
            FileTool.copyAssetFileToDatabase(this, "city.db", "city.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
        MobclickAgent.setDebugMode(true);

        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.enable();

        new Thread(new Runnable() {

            @Override
            public void run() {
                if (true) {
                    try {
                        Thread.sleep(2000);
                        go();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    private void go() {

        if (ShareDataTool.getStart(WelcomeActivity.this) == 0) {
            Intent intent = new Intent(WelcomeActivity.this,
                    SplashActivity.class);
            startActivity(intent);
            WelcomeActivity.this.finish();
        } else {
            Intent intent = new Intent(WelcomeActivity.this,
                    MainActivity.class);
            startActivity(intent);
            WelcomeActivity.this.finish();
        }

    }
}
