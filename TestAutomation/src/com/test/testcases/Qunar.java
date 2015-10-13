package com.test.testcases;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.test.base.TestBase;
import com.test.page.QunarPage;
import com.test.util.Assertion;
import com.test.util.Log;

public class Qunar extends TestBase {

	@Test(dataProvider="providerMethod")
	public void testQunar(Map<String, String> param){
		Assertion.flag = true;
		this.goTo(param.get("QNUrl"));
		Log.logInfo("go to qunar page");
		QunarPage qn = new QunarPage(driver);
		qn.waitElementToBeDisplayed(qn.getElement("TypeSng"));
		qn.getElement("TypeSng").click();
		Log.logInfo("Single trip selected");
		qn.getElement("FromCity").clear();
		qn.getElement("FromCity").sendKeys(param.get("FromCity"));
		Log.logInfo("From city input");
		qn.getElement("ToCity").clear();
		qn.getElement("ToCity").sendKeys(param.get("ToCity"));
		Log.logInfo("To city input");
		qn.getElement("FromDate").clear();
		qn.getElement("FromDate").sendKeys(param.get("FromDate"));
		Log.logInfo("From Date input");
		qn.getElement("SearchButton").click();
		Log.logInfo("Search buttton clicked");
		final WebElement element= qn.getElement("description");
		boolean wait= new WebDriverWait(driver, 60000).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d){
				String dec= element.getText();
				return dec.indexOf("搜索结束")>-1;
			}
		});
		Log.logInfo("搜索结束");
		List<WebElement> elements = qn.getElements("booking");
		int number= (int) (Math.random()*(elements.size()));
		Log.logInfo("the number is: "+number);
		elements.get(number).click();
		Log.logInfo("Ramdom clicked");
	}
}
