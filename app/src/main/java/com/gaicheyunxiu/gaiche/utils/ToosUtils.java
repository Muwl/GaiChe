package com.gaicheyunxiu.gaiche.utils;

import android.os.Environment;
import android.widget.TextView;


/**
 * @author Mu
 * @date 2014-11-4
 * @description 常用工具�?
 */
public class ToosUtils {

	/**
	 * 判断字符串是否为�?
	 * 
	 * @param msg
	 * @return 为空 true,不为�? false
	 */
	public static boolean isStringEmpty(String msg) {
		if (null == msg || "".equals(msg)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取TextView的内�?
	 * 
	 * @param textView
	 * @return textView 的内�?
	 */
	public static String getTextContent(TextView textView) {
		return textView.getText().toString().trim();
	}


	/**
	 * 判断TextView是否为空
	 * 
	 * @param textView
	 * @return 为空 true,不为�? false
	 */
	public static boolean isTextEmpty(TextView textView) {
		return isStringEmpty(getTextContent(textView));
	}


	public static boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}


}
