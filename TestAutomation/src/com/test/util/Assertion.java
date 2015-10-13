package com.test.util;

import org.testng.Assert;

public class Assertion {
	public static boolean flag;
	
	public static void verifyassert(Object actual, Object expected){
		try {
			Assert.assertEquals(actual, expected);
		} catch (Error e) {
			flag = false;
		}
	}
	
	public static void verifyassert(Object actual, Object expected, String message){
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (Error e) {
			flag = false;
			Log.logError("error show.");
		}
	}
}
