package com.gaicheyunxiu.gaiche.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static Date getDateByStr(String dd) {

		Date date;
		try {
			date = DATE_FORMAT_DATE.parse(dd);
		} catch (java.text.ParseException e) {
			date = null;
			e.printStackTrace();
		}
		return date;
	}


	public static String getHour(String data){
		Date date=getDateByStr(data);
		Date nowdata=new Date();
		long cur=date.getTime()-nowdata.getTime();
		int m= (int) (cur/(1000*60*60));
		if (m<10){
			return "0"+m;
		}else{
			return m+"";
		}

	}


	public static int getDay(String data){
		Date date=getDateByStr(data);
		Date nowdata=new Date();
		long cur=date.getTime()-nowdata.getTime();
		int m= (int) (cur/(1000*60*60*24));
		return m;

	}

	public static String getMinute(String data){
		Date date=getDateByStr(data);
		Date nowdata=new Date();
		long cur=date.getTime()-nowdata.getTime();
		int m= (int) ((cur%(1000*60*60))/(1000*60));
		if (m<10){
			return "0"+m;
		}else{
			return m+"";
		}

	}

	public static String getMin(String data){
		Date date=getDateByStr(data);
		Date nowdata=new Date();
		long cur=date.getTime()-nowdata.getTime();
		int m= (int) ((cur%(1000*60))/(1000));
		if (m<10){
			return "0"+m;
		}else{
			return m+"";
		}

	}

}