package com.gaicheyunxiu.gaiche.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Administrator on 2016/6/14.
 */
public class PayDialog extends Dialog {

    public PayDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        setCancelable(true);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        show();
        initView();
    }

    private void initView() {
//        TextView textView= (TextView) findViewById(R.id.id_tv_loadingmsg);
//        textView.setText("路程规划中...");
    }
}

