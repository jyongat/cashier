package com.test.cashier.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 从远程数据库或xml配制文件中拉数据，并可通过rmi等方式接受远程控制，以支持远程动态修改系统支持的活动类型
 *
 */

public class ConfUtil {
	
	private static final String CONF_PATH_NAME = "conf/conf.xml";
	private static final String XML_ELEMENT_KEY = "Key";
	private static final String XML_ELEMENT_VALUE = "Value";
			
	public static final String KEY_ACTIVE_STRATEGY = "ActiveStrategy";
	public static final String KEY_SHOP_NAME = "ShopName";
	
	public static final String HINT_CART_MANIFEST = "HintCartManifest";
	public static final String HINT_NAME = "HintName";
	public static final String HINT_QUANTITY = "HintQuantity";
	public static final String HINT_PRICE = "HintPrice";
	public static final String HINT_SUBTOTAL = "HintSubTotal";
	public static final String HINT_TOTAL = "HintTotal";
	public static final String HINT_MONE_NAME = "HintMoneName";
	public static final String HINT_SAVE = "HintSave";
	public static final String HINT_ACTIVE_GIFT = "HintActiveGift";
	public static final String HINT_ACTIVE_JOIN = "HintActiveJoin";
	public static final String HINT_MNACTIVE_FORMAT = "HintMNActiveFormat";
	
	private Map<String, String> map = new HashMap<String, String>();
	
	private static ConfUtil instance = null;
	
	private ConfUtil() {
		// 加载xml配制文件，并初始化map
		SAXReader sr = new SAXReader();
	    Document document = null;
	    try {
			document = sr.read(new File(CONF_PATH_NAME));
		} catch (DocumentException e) {
			e.printStackTrace();
			return ;
		}
	    
		Element root = document.getRootElement();
		List<Element> lstChild = root.elements();
		String key, value;
		for (Element child : lstChild) {
			try {
				key = child.element(XML_ELEMENT_KEY).getText();
				value = child.element(XML_ELEMENT_VALUE).getText();
			} catch (NullPointerException e) {
				continue ;
			}
			
			if (StringUtil.isEmpty(key) || StringUtil.isEmpty(value)) {
				continue ;
			}
			
			map.put(key, value);
		}
		
		lstChild.clear();
		root.clearContent();
	}
	
	public static ConfUtil getInstance() {
		if (null != instance) {
			return instance;
		}
		
		synchronized (ConfUtil.class) {
			if (null == instance) {
				instance = new ConfUtil();
			}
		}
		
		return instance;
	}
	
	public String getVale(String key) {
		String retVal = map.get(key);
		if (null == retVal) {
			retVal = "";
		}
		
		return retVal;
	}
}
