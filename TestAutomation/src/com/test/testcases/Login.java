package com.test.testcases;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.test.base.TestBase;
import com.test.page.FirstPage;
import com.test.page.LoginPage;
import com.test.util.Log;
import com.test.util.Assertion;

public class Login extends TestBase{
	
	@Test(dataProvider="providerMethod")
	public void testLogin(Map<String, String> param){
		Assertion.flag = true;
		this.goTo(param.get("url"));
		FirstPage fp = new FirstPage(driver);
		Log.logInfo("Pressed login link on home page");
		fp.getElement("login_link").click();
		LoginPage lp = new LoginPage(driver);
		Log.logInfo("User name: " + param.get("username"));
		lp.getElement("login_name").sendKeys(param.get("username"));
		Log.logInfo("Password: " + param.get("password"));
		lp.getElement("login_pwd").sendKeys(param.get("password"));
		lp.getElement("login_button").click();
		String errorMsg = lp.getElement("loginpwd_error").getText().trim();
		Log.logInfo("The error message is: " + errorMsg);
		Assert.assertEquals(errorMsg, "账户名与密码不匹配，请重新输入");
	}
}
