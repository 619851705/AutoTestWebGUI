package com.dcits.core;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.dcits.bean.WebStep;

/**
 * �Զ���ĵ��÷���,����click��go_to��setValue��
 * �Ƕ�selenium�еķ�����һ�ַ�װ
 * @author Administrator
 *
 */
public class CallMethod {	
	public static Object callMethod(WebStep step,Object o,WebDriver driver){
		try {
			switch (step.getCallMethod()) {
			case "click":
				click((WebElement)o);
				return null;
			case "go_to":
				go_to((String)o,driver);
				return null;
			case "sendKeys":
				sendKeys((WebElement)o,step.getRequireValue());
				return null;
			case "getTitle":
				return getTitle(driver);
			case "getText":
				return getText((WebElement)o);
			case "toFrame":
				switchToFrame(driver, step.getRequireValue());
				return null;
			case "toDefaltFrame":			
				switchToDefault(driver);
				return null;
			case "toWindow":
				switchToWindow(driver,step.getRequireValue());
				return null;
			case "getHandle":			
				return getWindowHandle(driver);
			case "getAllHandle":
				return getWindowHandles(driver);
			case "dismissDialog":
				dismissDialog(driver);
				return null;
			case "acceptDialog":
				acceptDialog(driver);
				return null;
			case "getDialogText":
				return getDialogText(driver);
			case "sendToDialog":
				sendKeyDialog(driver,step.getRequireValue());
				return null;
			case "getAttribute":
				return getAttribute((WebElement)o,step.getRequireValue());
			case "judgeSelected":
				return judgeSelected((WebElement)o);
			case "clear":
				clear((WebElement)o);
				return null;
			case "selectByValue":
				selectByValue((WebElement)o,step.getRequireValue());
				return null;
			case "selectByOptionText":
				selectByOptionText((WebElement)o,step.getRequireValue());
				return null;
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//��ȡ�ı�����
	//����ǻ�ȡ����input��valueֵ��,�����Ҫע��,ֻ�ܻ�ȡ�м��text
	public static String getText(WebElement element){
		return element.getText();
	}
	
	//��ȡԪ��ָ��������ֵ,����value name,id,class��,���뱣֤��Ԫ��ӵ��������Է��򱨴�
	public static String getAttribute(WebElement element,String attributeName){
		return element.getAttribute(attributeName);
	}
	
	//�������
	//����radio  checkbox  button link �ȱ�ǩ
	public static void click(WebElement element){
		element.click();
		
	}
	
	//��url����
	public static void go_to(String url,WebDriver driver){
		driver.get(url);
	}
	
	//������д����
	public static void sendKeys(WebElement element,String keys){
		element.sendKeys(keys);
	}
	
	//��ȡ��ǰ���ڵ�title
	public static String getTitle(WebDriver driver){
		return driver.getTitle();
	}
	
	//�л���λ��ָ��id����name��frame
	public static void switchToFrame(WebDriver driver,String frameIdOrName){
		driver.switchTo().frame(frameIdOrName);
	}
	
	//���ص�����iframe
	public static void switchToDefault(WebDriver driver){
		driver.switchTo().defaultContent();		
	}
	
	//�л�����,�����Ǵ������ƻ��ߴ��ڵľ��handle
	public static void switchToWindow(WebDriver driver,String nameOrHandle){
		driver.switchTo().window(nameOrHandle);
	}
	
	//��ȡ��ǰ���ڵľ��
	public static String getWindowHandle(WebDriver driver){
		return driver.getWindowHandle();
	}
	
	//��ȡ��ǰ���еĴ򿪵Ĵ��ڵľ��
	public static List<String> getWindowHandles(WebDriver driver){
		return new ArrayList<String>(driver.getWindowHandles());
	}
	
	//X���رջ���ȡ�����Ի���
	public static void dismissDialog(WebDriver driver){
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}
	
	//����Ի����ȷ�ϰ�ť
	public static void acceptDialog(WebDriver driver){
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
	
	//��ȡ�Ի������ı�
	public static String getDialogText(WebDriver driver){
		Alert alert = driver.switchTo().alert();
    	String a = alert.getText();
    	alert.accept();
    	return a;		
	}
	
	//�����ַ�����prompt
	public static void sendKeyDialog(WebDriver driver,String keys){
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(keys);
		alert.accept();
	}
	
	//�ж�Ԫ�ض����Ƿ�ѡ��
	//radio��ѡ��CheckBox��ѡ���Ƿ�ѡ��,ѡ�з����ַ���trueûѡ�з����ַ���false
	public static String judgeSelected(WebElement element){
		return String.valueOf(element.isSelected());		
	}
	
	//�������-��ջ���ȡ��ѡ��
	//�ɲ��������ı���  �ı���  radio checkbox  	
	public static void clear(WebElement element){
		element.clear();
	}
	
	//�����������option��value������ָ������ѡ��
	public static void selectByValue(WebElement element,String value){
		Select select = new Select(element);
		select.selectByValue(value);
	}
	
	//�����������option�Ŀɼ��ı�ֵ��ָ������ѡ��
	public static void selectByOptionText(WebElement element,String text){
		Select select = new Select(element);
		select.selectByVisibleText(text);
	}
}
