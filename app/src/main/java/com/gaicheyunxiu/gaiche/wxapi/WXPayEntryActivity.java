package com.gaicheyunxiu.gaiche.wxapi;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.BaseActivity;
import com.gaicheyunxiu.gaiche.activity.MainActivity;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler,View.OnClickListener{

	private ImageView back;

	private TextView title;

	private TextView money;

	private TextView ok;


    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_success);
		api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
		api.handleIntent(getIntent(), this);
		back= (ImageView) findViewById(R.id.title_back);
		title= (TextView) findViewById(R.id.title_text);
		money= (TextView) findViewById(R.id.pay_success_money);
		ok= (TextView) findViewById(R.id.pay_success_ok);

		money.setText("￥"+ MyApplication.getInstance().getWeixinmoney()+"元");
		back.setOnClickListener(this);
		title.setText("去结算");
		ok.setOnClickListener(this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {

		if(resp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
			LogManager.LogShow("------------支付---", "onPayFinish,errCode=" + resp.errCode+"-----------"+resp.errStr, LogManager.ERROR);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.title_back:
				Intent intent=new Intent(WXPayEntryActivity.this,MainActivity.class);
				startActivity(intent);
				break;
			case R.id.pay_success_ok:
				Intent intent1=new Intent(WXPayEntryActivity.this,MainActivity.class);
				startActivity(intent1);
				break;
		}
	}
}