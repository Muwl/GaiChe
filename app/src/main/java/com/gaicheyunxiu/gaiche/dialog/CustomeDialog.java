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


/**
 * @author Mu
 * @date 2015-3-6
 * @description 普通提示对话框
 */
public class CustomeDialog extends Dialog implements
		android.view.View.OnClickListener {
	public static final int RET_OK=40;
	private Context context;
	private Handler handler;
	private TextView ok;
	private TextView cancel;
	private TextView text;
	private String msg;
	private int position;
	private int flag;
	public CustomeDialog(Context context, Handler handler,String msg,int position,int flag) {
		super(context, R.style.dialog2);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.handler = handler;
		this.context = context;
		this.msg=msg;
		this.position=position;
		this.flag=flag;
		setContentView(R.layout.custom_dialog);
		getWindow().setBackgroundDrawable(new BitmapDrawable());
		show();
		initView();

	}

	private void initView() {
		text = (TextView) findViewById(R.id.custom_dialog_text);
		ok = (TextView) findViewById(R.id.custom_dialog_ok);
		cancel = (TextView) findViewById(R.id.custom_dialog_cancel);
		text.setText(msg);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.custom_dialog_ok:
			Message message=new Message();
			message.what=RET_OK;
			message.arg1=position;
			message.arg2=flag;
			handler.sendMessage(message);
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
