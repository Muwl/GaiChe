package com.gaicheyunxiu.gaiche.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

/**
 * 钱包支付
 */
public class WalletPay {

	/**
	 * @title generateSign
	 * @description 生成签名
	 * @param params 参数
	 * @param timeStamp 当前时间戳，用于设置有效连接时间
	 * @param nonce 随机字符串
	 * @return
	 * @author YinZhiwei
	 * @date 2016年5月17日下午4:49:16
	 * @version 1.0.0
	 */
	public static String generateSign(Map<String, String> params,
			Long timeStamp, String nonce) {
		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>(
				params.entrySet());
		Collections.sort(list, new Comparator<Entry<String, String>>() {
			// 升序排序
			public int compare(Entry<String, String> o1,
					Entry<String, String> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}

		});

		StringBuffer sb = new StringBuffer();

		for (Entry<String, String> mapping : list) {
			sb.append(mapping.getKey());
			sb.append(mapping.getValue());
		}
		sb.append(Constant.SECRET);
		sb.append(timeStamp);
		sb.append(nonce);
		return MD5Util.MD5(sb.toString());
	}

	/**
	 * @title validate
	 * @description 签名验证
	 * @param sign 传过来的签名
	 * @param params 参数
	 * @param timeStamp 传过来的时间戳
	 * @param nonce 传过来的随机字符串
	 * @return 0：连接失效，1：验证成功，2：验证失败
	 * @author YinZhiwei
	 * @date 2016年5月17日下午5:07:27
	 * @version 1.0.0
	 */
	public static int validate(String sign, Map<String, String> params,
			Long timeStamp, String nonce) {
		int k = 0;
		if (System.currentTimeMillis() - timeStamp <= 60 * 1000 * 5) { // 链接2分钟内有效
			if (sign.equals(generateSign(params, timeStamp, nonce))) {
				k = 1;
			} else {
				k = 2;
			}
		}
		return k;
	}
	
	public static String generatePayUrl(Map<String, String> params,
			Long timeStamp, String nonce){
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> entry : params.entrySet()) {
			   sb.append("&" + entry.getKey());
			   sb.append("=" + entry.getValue());
		}
		sb.append("&timeStamp=");
		sb.append(timeStamp);
		sb.append("&nonce=");
		sb.append(nonce);
		return sb.toString();
	}


	/**
	 * 获得32位随机字符串
	 * 
	 * @return
	 */
	public static String getNonceStr() {
		String[] letters = new String[] { "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
				"t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E",
				"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
				"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		String[] num = new String[] { "0", "1", "2", "3", "4", "5", "6", "7",
				"8", "9" };
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < 32; i++) {
			Random random = new Random();
			int a = random.nextInt(2);
			if (a == 0) {
				int b = random.nextInt(52);
				stringBuffer.append(letters[b]);
			} else {
				int b = random.nextInt(10);
				stringBuffer.append(num[b]);
			}
		}
		return stringBuffer.toString();
	}

}
