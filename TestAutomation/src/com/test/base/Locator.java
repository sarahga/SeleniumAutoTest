package com.test.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ho.yaml.Yaml;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.bean.Config;

public class Locator {
	private String yamlfile;
	public WebDriver driver;
	
	private Map<String, Map<String, String>> ml;
	private Map<String, Map<String, String>> extendLocator;
	
	public Locator(WebDriver driver){
		this.driver = driver;
	}
	
	public void setYamlFile(String yamlFile){
		this.yamlfile = yamlFile;
	}
	
	public void getYamlFile(){
		File f= new File("locator/"+yamlfile+".yaml");
		try {
			ml= Yaml.loadType(new FileInputStream(f.getAbsolutePath()), HashMap.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadExtendLocator(String fileName){
		File f= new File("locator/"+ fileName +".yaml");
		try {
			extendLocator= Yaml.loadType(new FileInputStream(f.getAbsolutePath()), HashMap.class);
			ml.putAll(extendLocator);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private WebElement waitForElement(final By by){
		WebElement element = null;
		int waitTime = Config.waitTime;
		try{
			element = new WebDriverWait(driver, waitTime).until(new ExpectedCondition<WebElement>(){
				public WebElement apply(WebDriver d){
					return d.findElement(by);
				}
			});
		}catch(Exception e){
			System.out.println(by.toString()+" is not exist until "+ waitTime);
		}
		return element;
	}
	
	public boolean waitElementToBeDisplayed(final WebElement element){
		boolean wait = false;
		if(element == null){
			return wait;
		}
		try{
		wait = new WebDriverWait(driver, Config.waitTime).until(new ExpectedCondition<Boolean>(){
			public Boolean apply(WebDriver d){
				return element.isDisplayed();
			}
		});}catch(Exception e){
			System.out.println(element.toString()+" is not displayed");
		}
		return wait;
	}
	
	public boolean waitElementToBeDisappear(final WebElement element){
		boolean wait = false;
		if(element == null){
			return wait;
		}
		try{
			wait = new WebDriverWait(driver, Config.waitTime).until(new ExpectedCondition<Boolean>(){
			public Boolean apply(WebDriver d){
				return !element.isDisplayed();
			}
		});}catch(Exception e){
			System.out.println(element.toString()+" is displayed");
		}
		return wait;
	}

	private By getBy(String type, String value){
		By by= null;
		if(type.equals("id")){
			by= By.id(value);
		}
		if(type.equals("xpath")){
			by= By.xpath(value);
		}
		return by;
	}
	
	public WebElement getElement(String key){
		return this.getLocator(key, null, true);
	}
	
	public List getElements(String key){
		return this.getLocators(key);
	}
	
	public WebElement getElement(String key, String[] replace){
		return this.getLocator(key, replace, true);
	}
	
	public WebElement getElementNoWait(String key){
		return getLocator(key, null, false);
	}
	
	public WebElement getElementNoWait(String key, String[] replace){
		return getLocator(key, replace, false);
	}
	
	public WebElement getLocator(String key, String[] replace, boolean wait){
		WebElement element = null;
		if(ml.containsKey(key)){
			Map<String, String> m=ml.get(key);
			String type= m.get("type");
			String value= m.get("value");
			if(replace!=null)
				value = this.getLocatorString(value, replace);
			By by = this.getBy(type, value);
			if(wait){
				element = this.waitForElement(by);
				boolean flag = this.waitElementToBeDisplayed(element);
				if(!flag){
					element=null;
				}else{
					try{
						element = driver.findElement(by);
					}catch(Exception e){
						element=null;
					}
				}
			}
		}
		return element;
	}
	
	public List<WebElement> getLocators(String key){
		List<WebElement> elements = null;
		if(ml.containsKey(key)){
			Map<String, String> m=ml.get(key);
			String type= m.get("type");
			String value= m.get("value");
			By by = this.getBy(type, value);
			try{
				elements = driver.findElements(by);
			}catch(Exception e){
				elements=null;
			}
		}
		return elements;
	}
	
	private String getLocatorString(String locatorString, String[] ss){
		for(String s:ss){
			locatorString = locatorString.replaceFirst("%s", s);
		}
		return locatorString;
	}
	
	public void setLocatorVariableValue(String variable, String value){
		if(ml!=null){
			Set<String> keys = ml.keySet();
			for(String key:keys){
				String v= ml.get(key).get(value).replaceAll("%"+variable+"%", value);
				ml.get(key).put("value", v);
			}
		}
	}
}
