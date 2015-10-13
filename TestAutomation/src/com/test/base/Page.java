package com.test.base;

import java.util.Set;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.test.util.Log;

public class Page extends Locator{
	
	public Page(WebDriver driver){
		super(driver);
		this.setYamlFile(this.getClass().getSimpleName());
		this.getYamlFile();
	}
	
	public Actions getAction(){
		return new Actions(driver);
	}
	
	public void swithWindowByIndex(int index){
		Log.logInfo("enter handles");
		String currentWindow = driver.getWindowHandle();
		Log.logInfo("got current handle: "+ currentWindow);
		Set<String> hdls = driver.getWindowHandles();
		Log.logInfo("got handles set: "+ hdls);
		Object[] handles = driver.getWindowHandles().toArray();
		Log.logInfo("got handles: "+ handles);
		if(index>handles.length){
			Log.logInfo("got handles: "+ handles.length);
			return;
		}
		driver.switchTo().window(handles[index].toString());
	}
	
	public boolean isExist(WebElement element){
		if(element==null){
			return false;
		}else{
			return true;
		}
	}
}
