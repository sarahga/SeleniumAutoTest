package com.test.base;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.openqa.selenium.WebDriver;


import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import com.test.bean.Config;
import com.test.bean.global;
import com.test.util.ParseXML;
import com.test.util.TimeString;

public class TestBase {
	private ParseXML px;
	private Map<String, String> commonMap;
	
	public WebDriver driver;
	
	private void intialPx(){
		if(px == null)
		px = new ParseXML("test-data/"+ this.getClass().getSimpleName()+".xml");
	}
	
	private void getCommonMap(){
		if(commonMap==null){
			Element element = px.getElementObject("/*/common");
			commonMap= px.getChildrenInfoByElement(element);
		}
	}
	
	@DataProvider
	public Object[][] providerMethod(Method method){
		this.intialPx();
		this.getCommonMap();
		String methodName = method.getName();
		List<Element> elements = px.getElementObjects("/*/"+methodName);
		Object[][] object = new Object[elements.size()][];
		for(int i=0; i<elements.size(); i++){
			//Object[] temp = new Object[]{px.getChildrenInfoByElement(elements.get(i))};
			//Object[] temp = new Object[]{this.getMergeMapData(px.getChildrenInfoByElement(elements.get(i)), commonMap)};
			Map<String, String> mergeCommon = this.getMergeMapData(px.getChildrenInfoByElement(elements.get(i)), commonMap);
			Map<String, String> mergeGlobal = this.getMergeMapData(mergeCommon, global.global);
			Object[] temp = new Object[]{mergeGlobal};
			object[i]=temp;
		}
		Reporter.log(new TimeString().getSimpleDateFormat()+" : "+ object);
		return object;
	}
	
	private Map<String, String> getMergeMapData(Map<String, String> map1, Map<String, String> map2){
		Iterator<String> i = map2.keySet().iterator();
		while(i.hasNext()){
			String key= i.next();
			String value = map2.get(key);
			if(!map1.containsKey(key)){
				map1.put(key, value);
			}
		}
		return map1;
	}
	
	@BeforeClass
	public void initialDriver(){
		SeleniumDriver sd = new SeleniumDriver();
		driver = sd.getDriver();
	}
	
	@AfterClass
	public void closeDriver(){
		if(driver!=null){
			driver.close();
			driver.quit();
		}
	}
	
	public void goTo(String url){
		driver.get(url);
		if(Config.browser.equals("chrome")){
			//Util.sleep(1);
		}
	}
}