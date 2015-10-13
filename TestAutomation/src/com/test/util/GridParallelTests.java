package com.test.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GridParallelTests {
	private WebDriver dr;
    DesiredCapabilities test;
    String baseUrl;

    @Parameters({"browser","nodeUrl","webSite"})
    @BeforeMethod
    public void setUp(String browser,String nodeUrl,String webSite){
        baseUrl = webSite;

        if(browser.equals("ie")) test = DesiredCapabilities.internetExplorer();
        else if(browser.equals("ff")) test = DesiredCapabilities.firefox();
        else if(browser.equals("chrome")) test = DesiredCapabilities.chrome();
        else System.out.println("browser参数有误，只能为ie、 ff、chrome");

        String url = nodeUrl + "/wd/hub";
        URL urlInstance = null;
        try {
            urlInstance = new URL(url);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("实例化url出错，检查一下url格式是否正确，格式为：http://192.168.1.108:5555");
        }
        dr = new RemoteWebDriver(urlInstance,test);
        dr.get(webSite);
    }

    @Test
    public void test(){
        dr.get(baseUrl);
        dr.findElement(By.id("kw")).sendKeys("selenium");
        dr.findElement(By.id("su")).click();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("title:"+dr.getTitle());
    }

    @AfterMethod
    public void quit(){
        dr.close();
    }
}
