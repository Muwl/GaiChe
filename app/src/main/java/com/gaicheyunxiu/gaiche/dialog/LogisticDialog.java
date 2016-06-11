package com.gaicheyunxiu.gaiche.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;


/**
 * @author Mu
 * @date 2015-3-6
 * @description 普通提示对话框
 */
public class LogisticDialog extends Dialog implements
		View.OnClickListener {
	private Context context;
	private TextView phone;
	private TextView carNo;
	private String scarNo;
	private String sphone;
	public LogisticDialog(Context context, String scarNo, String sphone) {
		super(context, R.style.dialog2);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.context = context;
		this.scarNo=scarNo;
		this.sphone=sphone;
		setContentView(R.layout.dialog_logistic);
		getWindow().setBackgroundDrawable(new BitmapDrawable());
		show();
		initView();

	}

	private void initView() {
		phone = (TextView) findViewById(R.id.dialog_logistic_title);
		carNo = (TextView) findViewById(R.id.dialog_logistic_carno);
		phone.setText(sphone);
		carNo.setText(scarNo);
		phone.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_logistic_title:
			ToosUtils.callPhone(context,sphone);
			dismiss();
			break;
		}

	}

}
