package com.dcits.core;

import java.lang.reflect.Constructor;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dcits.bean.WebObject;

/**
 * ʵ�ֶ���ʶ�𷽷�
 * �����ȴ���ȡ���󡢼�ʱ��ȡ�����Լ���alert���ڵĴ���
 * @author Administrator
 *
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TestObject {
	
	public static WebElement locateElement(WebDriver driver,String how,String value,Integer objectSeq){
		try {
			
			Class clz=Class.forName("org.openqa.selenium.By$By"+how);					
			Constructor ctr=clz.getConstructor(String.class);
			By ins=(By)ctr.newInstance(value);
			if(objectSeq==0){
				return ins.findElement(driver);
			}
			List<WebElement> els=ins.findElements(driver);
			return els.get(objectSeq-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * ��ָ��ʱ���ڻ�ȡ����
	 * @param driver 
	 * @param object
	 * @param seconds ��ʱʱ��
	 * @return
	 */
	public static Object findObject(final WebDriver driver,final WebObject object,Integer seconds){
		if(object.getObjectType().equals("url")){
			return object.getPropertyValue();
		}
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		return wait.until(new ExpectedCondition<WebElement>() {  			  
            @Override  
            public WebElement apply(WebDriver d) {  
                try {
					return locateElement(driver, object.getHow(), object.getPropertyValue(),object.getObjectSeq());
				} catch (Exception e) {
					e.printStackTrace();
				}
                	return null;
            }  
        }); 
	}
	
	/**
	 * ֱ�ӻ�ȡ����
	 * @param driver
	 * @param object
	 * @return
	 */
	public static WebElement noWaitFindObject(WebDriver driver,WebObject object){
		
		return null;
	}	
}
