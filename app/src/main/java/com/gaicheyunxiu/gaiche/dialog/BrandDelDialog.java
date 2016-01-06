package com.gaicheyunxiu.gaiche.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;


/**
 * @author Mu
 * @date 2015-8-1下午10:27:46
 * @description 
 */
public class BrandDelDialog implements OnClickListener {
	private Dialog d = null;
	private View view = null;
	private Context context = null;
	private Handler handler;
	private TextView del;
	private TextView cancel;

	public BrandDelDialog(final Context context, Handler handler) {
		super();
		this.context = context;
		this.handler = handler;
		d = new Dialog(context);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		view = View.inflate(context, R.layout.brand_del_dialog, null);
		d.setContentView(view);
		del = (TextView) d.findViewById(R.id.brand_del_ok);
		cancel = (TextView) d.findViewById(R.id.brand_del_cancel);
		cancel.setOnClickListener(this);
		del.setOnClickListener(this);

		Window dialogWindow = d.getWindow();
		LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER);
		lp.width = LayoutParams.MATCH_PARENT;
		lp.height = LayoutParams.WRAP_CONTENT;
		dialogWindow.setBackgroundDrawableResource(R.drawable.background_dialog);
		d.show();
	}


	private int getWindowHeight() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}

	private void dialogAnimation(final Dialog d, View v, int from, int to,
			boolean needDismiss) {

		Animation anim = new TranslateAnimation(0, 0, from, to);
		anim.setFillAfter(true);
		anim.setDuration(500);
		if (needDismiss) {
			anim.setAnimationListener(new AnimationListener() {

				public void onAnimationStart(Animation animation) {
				}

				public void onAnimationRepeat(Animation animation) {
				}

				public void onAnimationEnd(Animation animation) {
					d.dismiss();
				}
			});

		}
		v.startAnimation(anim);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.brand_del_ok:
			handler.sendEmptyMessage(44);
			d.dismiss();
			break;
		case R.id.brand_del_cancel:
			d.dismiss();
			break;

		default:
			break;
		}
	}

}
