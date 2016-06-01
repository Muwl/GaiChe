package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.view.MyScrollLayout;
import com.gaicheyunxiu.gaiche.view.OnViewChangeListener;

/**
 * Created by Mu on 2015/12/23.
 */
public class SplashActivity extends BaseActivity implements OnViewChangeListener{

    private MyScrollLayout mScrollLayout;

    private RelativeLayout mainRLayout;

    private ImageView ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        initView();
    }

    private void initView() {
        mScrollLayout= (MyScrollLayout) findViewById(R.id.splash_scrollayout);
        mainRLayout= (RelativeLayout) findViewById(R.id.splash);
        ok= (ImageView) findViewById(R.id.splash_ok);


        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        // 设置新任务按钮的大小和位置（模糊）
        RelativeLayout.LayoutParams laParams = (RelativeLayout.LayoutParams) ok.getLayoutParams();
        laParams.width = (int) (0.35 * width);
        laParams.height = (int) (0.069 * height);
        laParams.setMargins((int) (0.325* width), (int) (0.889* height), 0, 0);
        ok.setLayoutParams(laParams);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDataTool.saveStart(SplashActivity.this, 1);
                Intent intent = new Intent(
                        SplashActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void OnViewChange(int view) {

    }
}
