package com.gaicheyunxiu.gaiche.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.PaySuccessActivity;
import com.gaicheyunxiu.gaiche.activity.PaymentPwdActivity;


/**
 * @author Mu
 * @date 2015-3-6
 * @description 支付对话框
 */
public class PaymentDialog extends Dialog implements
		View.OnClickListener {
	private Context context;
	private ImageView close;
	private TextView money;
	private EditText pwd;
	private TextView name;
	private TextView forgetpwd;
	private TextView ok;

	public PaymentDialog(Context context) {
		super(context, R.style.dialog2);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.context = context;
		setContentView(R.layout.pay_dialog);
		getWindow().setBackgroundDrawable(new BitmapDrawable());
		show();
		initView();

	}

	private void initView() {
		close= (ImageView) findViewById(R.id.dialog_pay_close);
		money= (TextView) findViewById(R.id.dialog_pay_money);
		pwd= (EditText) findViewById(R.id.dialog_pay_pwd);
		forgetpwd= (TextView) findViewById(R.id.dialog_forget_pwd);
		name= (TextView) findViewById(R.id.dialog_pay_name);
		ok= (TextView) findViewById(R.id.dialog_pay_ok);
		ok.setOnClickListener(this);
		close.setOnClickListener(this);
		forgetpwd.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_pay_close:
			dismiss();
			break;
		case R.id.dialog_pay_ok:
			dismiss();
			Intent intent1=new Intent(context, PaySuccessActivity.class);
			context.startActivity(intent1);
			break;
		case R.id.dialog_forget_pwd:
			dismiss();
			Intent intent=new Intent(context, PaymentPwdActivity.class);
			context.startActivity(intent);
			break;
		default:
			break;
		}

	}

}
