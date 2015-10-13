package com.test.testcases;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.test.base.TestBase;
import com.test.page.MobileList;
import com.test.page.ProductPage;
import com.test.page.ShoppingCart;
import com.test.util.Assertion;
import com.test.util.Log;

public class Shopping extends TestBase {
	
	@Test(dataProvider="providerMethod")
	public void testShopping(Map<String, String> param){
		Assertion.flag = true;
//		this.goTo(param.get("url"));
//		FirstPage fp = new FirstPage(driver);
//		Log.logInfo("Pressed login link on home page");
//		fp.linkToMobileList();
		this.goTo(param.get("phoneUrl"));
		//fp.swithWindowByIndex(1);
		MobileList ml = new MobileList(driver);
		Log.logInfo("before select huawei");
		ml.getElement("huawei").click();
		Log.logInfo("after select huawei");
		ml.getElement("商品价格筛选").click();
		Log.logInfo("商品价格筛选完成 ");
		ml.getElement("商品颜色筛选").click();
		Log.logInfo("商品颜色筛选完成 ");
		ml.getElement("商品类型筛选").click();
		Log.logInfo("商品类型筛选完成 ");
		ml.getElement("第一个商品").click();
		Log.logInfo("选择了第一个商品 ");
		String productName = ml.getElement("商品名称").getText();
		Log.logInfo("商品名称 "+productName);
		String productPrice = ml.getElement("商品价格").getText();
		Log.logInfo("商品价格 "+productPrice);
		ml.swithWindowByIndex(1);
		Log.logInfo("switch window");
		ProductPage pp = new ProductPage(driver);
		Log.logInfo("product page launch");
		String productUrl = driver.getCurrentUrl();
		Log.logInfo("url"+productUrl);
		int s = productUrl.lastIndexOf("/");
		int e = productUrl.lastIndexOf(".");
		String[] sku = new String[]{productUrl.substring(s+1, e)};
		Log.logInfo("sku "+sku);
		pp.getElement("加入购物车").click();
		Log.logInfo("已加入购物车");
		pp.getElement("去购物车结算").click();
		Log.logInfo("已去购物车结算");
		ShoppingCart sc = new ShoppingCart(driver);
		Assertion.verifyassert(true, productName.contains(sc.getElement("商品名称", sku).getText()));
		Log.logInfo("商品名称 "+ sc.getElement("商品名称", sku).getText());
		Assertion.verifyassert(productPrice.substring(1), sc.getElement("商品价格", sku).getText());
		Log.logInfo("商品价格 "+ sc.getElement("商品价格", sku).getText());
		Assertion.verifyassert(true, sc.getElement("勾选商品", sku).isSelected());
		sc.getElement("删除商品", sku).click();
		sc.getElement("确定删除").click();
		sc.waitElementToBeDisappear(sc.getElement("商品名称", sku));
		Assertion.verifyassert(false, sc.isExist(sc.getElementNoWait("商品名称", sku)));
		Assert.assertTrue(Assertion.flag);
	}
}
