package com.test.bean;

import java.util.Map;

import com.test.util.ParseXML;

public class global {
	public static Map<String, String> global;
	
	static{
		ParseXML px = new ParseXML("test-data/global.xml");
		global = px.getChildrenInfoByElement(px.getElementObject("/*"));
	}
}
