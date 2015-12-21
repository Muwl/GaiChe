package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2015/12/21.
 */
public class WelcomeActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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

        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
