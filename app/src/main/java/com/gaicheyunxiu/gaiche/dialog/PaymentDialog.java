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
import com.gaicheyunxiu.gaiche.model.PayEntity;
import com.gaicheyunxiu.gaiche.model.PayState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MD5Util;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


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
	private PayEntity payEntity;
	private View pro;
	private String smoney;
	private  PayDialog payDialog;

	public PaymentDialog(Context context,PayEntity payEntity,String smoney) {
		super(context, R.style.dialog2);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.context = context;
		this.payEntity = payEntity;
		this.smoney=smoney;
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
		pro= findViewById(R.id.dialog_pay_pro);
		money.setText(smoney);
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

			walletPay();
//
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

	/**
	 * 钱包支付
	 */
	private void walletPay() {
		if (ToosUtils.isTextEmpty(pwd)){
			ToastUtils.displayShortToast(context,"请输入密码");
			return;
		}
		payDialog=new PayDialog(context);
		RequestParams rp = new RequestParams();
		HttpUtils utils = new HttpUtils();
		utils.configTimeout(20000);
		String url="pay/walletPay";
//		rp.addBodyParameter("sign", ShareDataTool.getToken(context));
//		rp.addBodyParameter("paySign",payEntity.paySign );
//		rp.addBodyParameter("payType",payEntity.payType );
//		rp.addBodyParameter("payPwd",ToosUtils.getEncrypt(ToosUtils.getTextContent(pwd)));
//		String strUrl=Constant.ROOT_PATH+ url+"?sign="+ ShareDataTool.getToken(context)+ToosUtils.getEncryptto(payEntity.content);
		String strUrl=Constant.ROOT_PATH+ url+"?sign="+ ShareDataTool.getToken(context)+"&paySign="+payEntity.paySign+"&payType="+payEntity.payType +"&payPwd="+MD5Util.MD5(ToosUtils.getTextContent(pwd)) + ToosUtils.getEncryptto(payEntity.content);
//		rp.addBodyParameter("commodityOrderVosStr", new Gson().toJson(getOrderEntity()));
		String ss=Constant.ROOT_PATH+ url+"?sign="+ ShareDataTool.getToken(context)+"&paySign="+payEntity.paySign+"&payType="+payEntity.payType +"&payPwd="+ MD5Util.MD5(ToosUtils.getTextContent(pwd)) + ToosUtils.getEncryptto(payEntity.content);
		LogManager.LogShow("-----", ss,
				LogManager.ERROR);
		LogManager.LogShow("提交****-------", MD5Util.MD5(ToosUtils.getTextContent(pwd)),LogManager.ERROR);
		utils.send(HttpRequest.HttpMethod.POST, strUrl, rp, new RequestCallBack<String>() {
			@Override
			public void onStart() {
				super.onStart();
//				pro.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				ToastUtils.displayFailureToast(context);
				if (payDialog!=null){
					payDialog.dismiss();
				}
//				pro.setVisibility(View.GONE);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
//				pro.setVisibility(View.GONE);
				if (payDialog!=null){
					payDialog.dismiss();
				}
				try {
					Gson gson = new Gson();
					ReturnState state = gson.fromJson(arg0.result,
							ReturnState.class);
					if (Constant.RETURN_OK.equals(state.msg)) {
						LogManager.LogShow("-----", arg0.result,
								LogManager.ERROR);
						dismiss();
						Intent intent1=new Intent(context, PaySuccessActivity.class);
						intent1.putExtra("money",smoney);
						context.startActivity(intent1);
					} else if (Constant.TOKEN_ERR.equals(state.msg)) {
						ToastUtils.displayShortToast(context,
								"验证错误，请重新登录");
						ToosUtils.goReLogin(context);
					} else {
						ToastUtils.displayShortToast(context,
								(String) state.result);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtils.displaySendFailureToast(context);
				}

			}
		});
	}

}
