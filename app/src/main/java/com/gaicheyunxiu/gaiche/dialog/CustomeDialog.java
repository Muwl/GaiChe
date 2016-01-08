package com.gaicheyunxiu.gaiche.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;


/**
 * @author Mu
 * @date 2015-3-6
 * @description 普通提示对话框
 */
public class CustomeDialog extends Dialog implements
		android.view.View.OnClickListener {
	private Context context;
	private Handler handler;
	private TextView ok;
	private TextView cancel;
	private TextView text;
	public CustomeDialog(Context context, Handler handler) {
		super(context, R.style.dialog2);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.handler = handler;
		this.context = context;
		setContentView(R.layout.custom_dialog);
		getWindow().setBackgroundDrawable(new BitmapDrawable());
		show();
		initView();

	}

	private void initView() {
		text = (TextView) findViewById(R.id.custom_dialog_text);
		ok = (TextView) findViewById(R.id.custom_dialog_ok);
		cancel = (TextView) findViewById(R.id.custom_dialog_cancel);
		text.setText("确定要删除此收货地址吗？");

		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.custom_dialog_ok:
			handler.sendEmptyMessage(40);
			dismiss();
			break;
		case R.id.custom_dialog_cancel:

			dismiss();
			break;
		default:
			break;
		}

	}

}
