package com.gaicheyunxiu.gaiche.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.activity.BrandActivity;
import com.gaicheyunxiu.gaiche.activity.CarbrandActivity;
import com.gaicheyunxiu.gaiche.activity.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

	public static void openUrl(Context context,String temp){
		Uri uri = Uri.parse(temp);
		Intent  intent = new  Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(intent);
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

	public static void goReLogin(Context context) {
		Intent intent = new Intent(context, LoginActivity.class);
		ShareDataTool.SaveInfo(context,null);
		context.startActivity(intent);
	}

	public static boolean goBrand(Context context,int flag){
		if (MyApplication.getInstance().getCarEntity()==null){
			Intent intent=new Intent(context, CarbrandActivity.class);
			if (flag==0){
				ToastUtils.displayShortToast(context,"请先添加爱车！");
			}
			((Activity)context).startActivityForResult(intent,8856);
			return true;
		}
		return false;
	}

	public static void callPhone(Context context,String phone){
		Intent phoneIntent = new Intent(
				"android.intent.action.CALL", Uri.parse("tel:"
				+ phone));
		context.startActivity(phoneIntent);
	}



	public static File createFile(String path) {
		File file = new File(path);
		File dir = new File(file.getParent());
		if (!dir.exists()) {
			try {
				// 按照指定的路径创建文件夹
				dir.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		if (!file.exists()) {
			try {
				// 在指定的文件夹中创建文件
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return file;

	}


	public static File saveImage2SD(String filePath, Bitmap bitmap) {
		try {
			File saveFile = null;

			if (bitmap != null) {
				saveFile = createFile(filePath);
			}
			FileOutputStream fos = new FileOutputStream(filePath);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte[] bytes = stream.toByteArray();
			fos.write(bytes);
			fos.close();
			return saveFile;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}





}
