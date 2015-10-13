package com.test.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeString {
	private String valueOfString(String str, int len){
		String string="";
		for(int i=0; i< len - str.length(); i++){
			string = string + "0";
		}
		return (str.length()==len)?(str):(string+str);
	}
	
	public String getTime(){
		return String.valueOf(new Date().getTime());
	}
	
	public String getSimpleDateFormat(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}
	
	public String getTimeString(){
		Calendar calendar = new GregorianCalendar();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = valueOfString(String.valueOf(calendar.get(Calendar.MONTH)+1),2);
		String day = valueOfString(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),2);
		String hour = valueOfString(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)),2);
		String minute = valueOfString(String.valueOf(calendar.get(Calendar.MINUTE)),2);
		String second = valueOfString(String.valueOf(calendar.get(Calendar.SECOND)),2);
		String millisecond = valueOfString(String.valueOf(calendar.get(Calendar.MILLISECOND)),2);
		return year+month+day+hour+minute+second+millisecond;
	}
	

	public static void main(String[] args) {
		TimeString ts = new TimeString();
		System.out.println(ts.getTime());
		System.out.println(ts.getSimpleDateFormat());
		System.out.println(ts.getTimeString());
	}

}
