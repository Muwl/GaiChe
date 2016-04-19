package com.gaicheyunxiu.gaiche.utils;

import android.os.Environment;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

	public static boolean checkPwd(String str) {
		if (str.length() < 6) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean MatchPhone(String name) {
		Pattern p = Pattern
				.compile("^((1[3,7][0-9])|(15[^4,\\D])|(18[0-3,5-9])|(14[5,7]))\\d{8}$");
		Matcher m = p.matcher(name);
		return m.matches();
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


	public static String getEncrypt(String str) {
		BlowfishECB bf = new BlowfishECB("xg!$@gcp1*30y%#c");
		return bf.encrypt(str);
	}

	public static String getEncryptto(String str) {
		BlowfishECB bf = new BlowfishECB("xg!$@gcp1*30y%#c");
		return bf.decrypt(str);
	}


}
