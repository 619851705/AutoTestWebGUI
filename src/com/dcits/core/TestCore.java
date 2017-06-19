package com.dcits.core;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import com.dcits.bean.DataDB;
import com.dcits.bean.WebCase;
import com.dcits.bean.WebConfig;
import com.dcits.bean.WebReport;
import com.dcits.bean.WebReportCase;
import com.dcits.bean.WebStep;
import com.dcits.exception.web.NoDataToValidateException;
import com.dcits.exception.web.NoQueryDBFindException;
import com.dcits.exception.web.NoRequireValueException;
import com.dcits.util.PracticalUtils;
import com.opera.core.systems.OperaDriver;

public class TestCore {
	//����ȡֵ��map
	private static Map<String,Object> acquireParams = new HashMap<String,Object>();
	
	/**
	 * ���ݴ����WebCaseʵ��ִ�������Ĳ������̲����ز��Ա��漯��
	 * @param webcase
	 * @return
	 */
	public static List<WebReport> runTest(WebCase webcase,WebReportCase reportCase,String screenShotPath,WebConfig config,String username,Map<String,DataDB> dataDBS){
		//ÿ�β���һ���������������acquireParams
		acquireParams.clear();
		List<WebReport> wrs=new ArrayList<WebReport>();		
		Set<WebStep> wss=webcase.getSteps();
		//����ָ����WebDriverʵ��
		WebDriver driver=createDriver(webcase.getBrowser(),config);
		if(config.getWindowSize().equals("1")){
			//��󻯴���
			driver.manage().window().maximize();
		}
		WebReport report=null;
		for(int i=1;i<=wss.size();i++){
			for(WebStep step:wss){
				if(step.getOrderNum()==i){
					report=testStep(step,driver,username,reportCase,screenShotPath,config.getElementWaitTime(),config.getResultWaitTime(),dataDBS);
					wrs.add(report);
					if(!report.getRunStatus().equals("success")&&config.getErrorInterruptFlag().equals("1")){
						driver.quit();
						return wrs;
					}
				}
			}
		}
		driver.quit();
		return wrs;
	}
	
	/**
	 * ���ݴ������������ƴ���ָ�����͵�WebDriver
	 * @param driverName
	 * @return
	 */
	public static WebDriver createDriver(String driverName,WebConfig config){
		WebDriver driver=null;
		switch (driverName) {
		case "IE":
			if(config.getIePath()!=null&&!config.getIePath().equals("")){							
				 System.setProperty("webdriver.ie.bin",config.getIePath()); 
			}
			driver=new InternetExplorerDriver();
			break;
		case "Chrome":
			if(config.getChromePath()!=null&&!config.getChromePath().equals("")){
				System.setProperty("webdriver.chrome.bin",config.getChromePath()); 
			}
			driver=new ChromeDriver();
			break;
		case "FireFox":
			if(config.getFirefoxPath()!=null&&!config.getFirefoxPath().equals("")){
				System.setProperty("webdriver.firefox.bin",config.getFirefoxPath()); 
			}
			driver=new FirefoxDriver();
			break;
		case "Opera":
			if(config.getOperaPath()!=null&&!config.getOperaPath().equals("")){
				System.setProperty("webdriver.opera.bin",config.getOperaPath()); 
			}
			driver=new OperaDriver();
			break;
		}		
		return driver;
	}
	
	/**
	 * ����ִ��һ�����Բ���
	 * @param step
	 * @param driver
	 * @param c
	 * @param username
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static WebReport testStep(WebStep step,WebDriver driver,String username,WebReportCase reportCase,String screenShotPath,Integer elementWaitTime,Integer resultWaitTime,Map<String,DataDB> dataDBS){
		WebReport report=null;
		String runStatus="";
		String mark="";
		String capturePath="";
		try {
			Object o=null;
			if(step.getWebObject()!=null){
				o=TestObject.findObject(driver, step.getWebObject(), (elementWaitTime/1000));	
			}	
			//��֤�Ĳ���,��Ҫ�õ����ص�ֵ��Ԥ�����õ�ֵ���бȶ�,�ȶԳɹ���ò��Գɹ����߼���ִ����һ����,�ȶԲ��ɹ�����û�бȶ�ֵ����
			if(step.getStepMethod().equals("Validate")){
				if(step.getRequireParameter().equals("")||step.getRequireParameter()==null){
					throw new NoRequireValueException();
				}								
				Thread.sleep(resultWaitTime);
				mark=(String) CallMethod.callMethod(step, o, driver);
				//�ȶ�ֵ-��Ҫ����
				String validateStr = null;
				switch (step.getRequireParameterType()) {
				case "0":  //ֱ��ȡֵ
					validateStr = step.getRequireParameter();
					break;
				case "1": //��acquireParams�л�ȡ
					validateStr = (String)acquireParams.get(step.getRequireParameter());
					break;
				default: //�����ݿ��л�ȡֵ
					//1����ȫ��session�л�ȡ��Ӧ�����ݿ���Ϣ
					DataDB db = dataDBS.get(step.getRequireParameterType());
					if(db==null){
						throw new NoQueryDBFindException();
					}
					Connection con = DBUtil.getConnection(db.getDbType(), db.getDbUrl(), db.getDbName(), db.getDbUsername(), db.getDbPasswd());
					if(con==null){
						throw new SQLException();
					}
					//2���滻sql����еĲ���
					String sqlStr = PracticalUtils.replaceSqlStr(step.getRequireParameter(), acquireParams);
					//3��ִ��sql����ȡ����
					validateStr = DBUtil.getDBData(con, sqlStr);
					break;
				}
				if(validateStr==null){
					throw new NoDataToValidateException();
				}
				
				if(mark.equals(validateStr)){
					runStatus="success";
				}else{
					runStatus="fail";
				}
				mark="Ԥ�ڽ��Ϊ:��"+validateStr+"��\nʵ�ʻ�ȡ���Ϊ:��"+mark+"��";
			//ִ�еĲ���,ֱ��ִ��
			}else if(step.getStepMethod().equals("Action")){
				CallMethod.callMethod(step, o, driver);
				runStatus="success";
			//ֻ��ȡֵ,��ȡ��ֵ�Ա����Ĳ���ʹ�á�
			//����:��ȡ��ֵӦ�ô������,���ʹ��?
			}else if(step.getStepMethod().equals("Acquire")){
				if(step.getRequireParameter().equals("")||step.getRequireParameter()==null){
					throw new NoRequireValueException();
				}
				Object o1 = CallMethod.callMethod(step, o, driver);
				if(o1 != null){
					runStatus="success";
					//����list������ת��String
					if(o1 instanceof List){
						acquireParams.put(step.getRequireParameter(), (ArrayList)o1);
						mark = "�ɹ���ȡ��ֵ: "+PracticalUtils.arrayListToString((ArrayList)o1);
					}else{
						acquireParams.put(step.getRequireParameter(), (String)o1);
						mark = "�ɹ���ȡ��ֵ: "+o1.toString();
					}					
				}else{
					runStatus="fail";
					mark = "��ȡ���ʧ��";
				}				
			}
			if(step.getCapture().equals("0")){
				capturePath=String.valueOf(System.currentTimeMillis())+".png";
				File screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);  
				FileUtils.copyFile(screenShotFile, new File(screenShotPath+capturePath));
			}
		}catch(UnhandledAlertException e){ 
			runStatus="fail";
			mark="ҳ�浯����δ����,�޷�������һ������";
			e.printStackTrace();
		}catch(NoSuchElementException e){ 
			runStatus="fail";
			mark="�޷���λԪ��,����!";
			e.printStackTrace();
		}catch(NoSuchWindowException e){ 
			runStatus="fail";
			mark="�޷��л���ָ���Ĵ���,����!";
			e.printStackTrace();
		}catch(NoAlertPresentException e){ 
			runStatus="fail";
			mark="��ǰҳ��û�г��ֵ�����,����!";
			e.printStackTrace();
		}catch(NoSuchFrameException e){ 
			runStatus="fail";
			mark="�޷��л���ָ����frame";
			e.printStackTrace();
		}catch(UnexpectedTagNameException e){ 
			runStatus="fail";
			mark="û���ҵ�ָ����html��ǩ";
			e.printStackTrace();
		}catch(StaleElementReferenceException e){ 
			runStatus="fail";
			mark="������ҳ��ˢ�µ���ָ���Ĳ��Զ�����ʧЧ,�����»�ȡ";
			e.printStackTrace();
		}catch(TimeoutException e){ 
			runStatus="fail";
			mark="������ʱ";
			e.printStackTrace();
		}catch(NoRequireValueException e){ 
			runStatus="fail";
			mark="û���ҵ�Ҫʹ�õı�Ҫ����,����";
			e.printStackTrace();
		}catch(NoDataToValidateException e){ 
			runStatus="fail";
			mark="��ȡ��֤�ȶԲ���ֵʧ��,����ò����ȡֵ��ʽ�ͱ�Ҫ����";
			e.printStackTrace();
		}catch(SQLException e){ 
			runStatus="fail";
			mark="��ȡ���ݿ�����ʧ��,�����ѯ���ݿ��������Ϣ";
			e.printStackTrace();
		}catch(NoQueryDBFindException e){ 
			runStatus="fail";
			mark="��ѯ����ָ����ѯ���ݿ��������Ϣ,��ȷ�ϸ����ݿ���Ϣ�Ƿ�ɾ��?";
			e.printStackTrace();
		}catch (Exception e) {
			runStatus="error";
			mark=e.toString();
			e.printStackTrace();			
		}		
		report=new WebReport(step, reportCase,runStatus, mark, capturePath, username, new Timestamp(System.currentTimeMillis()));		
		return report;
	}
}
