package com.test.bean;

import com.test.util.ParseXML;

public class Config {
	public static String browser;
	public static int waitTime;
	
	//public void init(){
	static{
		ParseXML px= new ParseXML("config/config.xml");
		browser = px.getElementText("/*/browser");
		waitTime = Integer.valueOf(px.getElementText("/*/waitTime"));
	}
	
	public static void main(String[] args){
//		Config cg= new Config();
//		cg.init();
		System.out.println(browser);
		System.out.println(waitTime);
	}
}
