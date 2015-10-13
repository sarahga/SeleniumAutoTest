package com.test.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseXML {
	private Document document;
	private String filePath;
	
	public ParseXML(String filePath){
		this.filePath = filePath;
		this.load(this.filePath);
	}
	public void load(String filePath){
			File file = new File(filePath);
			if(file.exists()){
				SAXReader saxReader = new SAXReader();
				try {
					document = saxReader.read(file);
				} catch (DocumentException e) {
					System.out.println("loading error: "+ filePath);
				}
			}else{
				System.out.println("loading error: "+ filePath);
			}
	}
	
	public Element getElementObject(String elementPath){
		return (Element) document.selectSingleNode(elementPath);
	}
	
	public String getElementText(String elementPath){
		Element element = (Element) document.selectSingleNode(elementPath);
		if(element!=null){
			return element.getTextTrim();
		}else{
			return null;
		}
	}
	
	public boolean isExist(String elementPath){
		boolean flag = false;
		Element element = this.getElementObject(elementPath);
		if(element!=null){
			flag = true;
		}
		return flag;
	}
	
	public List<Element> getElementObjects(String elementPath){
		return document.selectNodes(elementPath);
	}
	
	public Map<String, String> getChildrenInfoByElement(Element element){
		Map<String, String> map  = new HashMap<String, String>();
		List<Element> children = element.elements();
		for(Element e:children){
			map.put(e.getName(), e.getText());
		}
		return map;
	}
}
