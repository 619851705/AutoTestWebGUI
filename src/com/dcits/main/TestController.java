package com.dcits.main;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dcits.bean.DataDB;
import com.dcits.bean.User;
import com.dcits.bean.WebCase;
import com.dcits.bean.WebCaseSet;
import com.dcits.bean.WebCaseSetComp;
import com.dcits.bean.WebConfig;
import com.dcits.bean.WebReport;
import com.dcits.bean.WebReportCase;
import com.dcits.bean.WebReportSet;
import com.dcits.bean.WebTestRmi;
import com.dcits.core.TestCore;
import com.dcits.service.DataDBService;
import com.dcits.service.UserService;
import com.dcits.service.WebCaseService;
import com.dcits.service.WebCaseSetService;
import com.dcits.service.WebConfigService;
import com.dcits.service.WebReportService;
import com.dcits.service.WebTestRmiService;
import com.dcits.util.MD5Util;
import com.dcits.util.PracticalUtils;
import com.dcits.util.PropertiesUtil;
import com.dcits.util.UploadShot;

@Controller
public class TestController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private WebTestRmiService rmiService;
	@Autowired
	private WebCaseService caseService;
	@Autowired
	private WebCaseSetService setService;
	@Autowired
	private WebConfigService configService;
	@Autowired
	private WebReportService reportService;
	@Autowired
	private DataDBService dbService;
	
	//判断登陆用户是否正确
	public User verifyUser(String username,String password){
		return userService.findUserBylogin(username, MD5Util.code(password));
	}
	
	
	//检查当前是否有对应用户的测试任务
	public Object[][] verifyTasks(Integer userId){
		List<WebTestRmi> tasks = rmiService.getByUser(userId);
	    
	    Object[][] cells = new Object[tasks.size()][7];
	    
	    for(int i=0;i<tasks.size();i++){
	    	WebTestRmi task = tasks.get(i);
	    	cells[i][0] = task.getTestId();
	    	cells[i][1] = task.getTaskName();
	    	if(task.getTestMode().equals("0")){
	    		cells[i][2] = "测试用例";
	    	}else{
	    		cells[i][2] = "测试集";
	    	}
	    	cells[i][3] = task.getSubmitTime();
	    	cells[i][4] = task.getFinishTime();
	    	cells[i][5] = task.getStatus();
	    	cells[i][6] = PracticalUtils.taskStatus(task.getStatus());
	    }
	    return cells;
	}
	
	//获取单个测试任务
	public WebTestRmi findTask(Integer testId){
		return rmiService.get(testId);
	}
	
	//删除测试任务
	public String delTask(Integer testId){
		try {
			rmiService.delTask(testId);
			return "0";
		} catch (Exception e) {
			return e.toString();
		}
		
	}
	
	//手动更新当前任务状态
	public String updateTaskStatus(Integer testId,String status){
		try {
			rmiService.updateStatus(status, testId);
			return "0";
		} catch (Exception e) {
			return e.toString();
		}
	}
	//测试用例的测试
	public Map<String,String> caseTest(WebTestRmi info,User user) throws ParseException {
		Map<String,String> returnMap =new HashMap<String,String>();
		WebConfig config=configService.findConfig(user.getUserId());
		if(config==null){
			config=configService.findConfig(0);
			WebConfig config1=new WebConfig(user.getUserId(),config.getElementWaitTime(),config.getResultWaitTime(),config.getIePath(),config.getChromePath(),config.getFirefoxPath(),config.getOperaPath(),config.getWindowSize(),config.getErrorInterruptFlag());
			configService.editConfig(config1);
		}
		WebCase webCase=caseService.getCase(info.getRelatedId());
		if(webCase.getSteps().size()<1){
			Timestamp t1 = new Timestamp(System.currentTimeMillis());
			info.setStatus("4");
			info.setTestMsg("当前测试用例中没有足够的测试步骤去执行,请检查");	
			info.setFinishTime(t1);
			returnMap.put("testStutas", "4");
			returnMap.put("msg", "当前测试用例中没有足够的测试步骤去执行,请检查");
			returnMap.put("finishTime", t1.toString());
		}
		WebReportCase reportCase=new WebReportCase(webCase,null,new Timestamp(System.currentTimeMillis()));
		int ret=reportService.addReportCase(reportCase);
//		reportCase.setReportCaseId(ret);
		reportCase=reportService.getReportCase(ret);       
        String path= PropertiesUtil.getKeyValue("filePath");
        Map<String,DataDB> dbs = new HashMap<String,DataDB>();
        for(DataDB db:dbService.findAll()){
        	dbs.put(String.valueOf(db.getDbId()), db);
        }
		List<WebReport> reports=TestCore.runTest(webCase, reportCase,path,config,user.getRealName(),dbs);
		for(WebReport report:reports){
			reportService.addReport(report);			
			//上传图片
			if(report.getCapturePath()!=null&&!report.getCapturePath().equals("")){
				System.out.println("正在上传截图:"+report.getCapturePath());
				UploadShot.uploadFile(report.getCapturePath());
			}
			
		}
		returnMap.put("testStutas", "0");
		info.setStatus("0");
		info.setFinishTime(new Timestamp(System.currentTimeMillis()));
		rmiService.edit(info);
		return returnMap;
	}
	
	//测试集的测试
	public Map<String,String> setTest(WebTestRmi info,User user) throws ParseException{			
		WebCaseSet caseSet=setService.get(info.getRelatedId());
		Set<WebCaseSetComp> comps=caseSet.getComps();
		Map<String,String> returnMap =new HashMap<String,String>();
		WebConfig config=configService.findConfig(user.getUserId());
		if(config==null){
			config=configService.findConfig(0);
			WebConfig config1=new WebConfig(user.getUserId(),config.getElementWaitTime(),config.getResultWaitTime(),config.getIePath(),config.getChromePath(),config.getFirefoxPath(),config.getOperaPath(),config.getWindowSize(),config.getErrorInterruptFlag());
			configService.editConfig(config1);
		}
		WebCase webCase=null;
		WebReportSet reportSet=new WebReportSet(new Timestamp(System.currentTimeMillis()),caseSet);
		int ret=reportService.addReportSet(reportSet);
		reportSet=reportService.getReportSet(ret);
		for(WebCaseSetComp comp:comps){	
			if(comp.getStatus().equals("0")){
				webCase=comp.getWebCase();
				if(webCase.getSteps().size()<1){				
					continue;
				}
				WebReportCase reportCase=new WebReportCase(webCase,reportSet,new Timestamp(System.currentTimeMillis()));
				ret=reportService.addReportCase(reportCase);
				reportCase=reportService.getReportCase(ret);
				String path= PropertiesUtil.getKeyValue("filePath");
				  Map<String,DataDB> dbs = new HashMap<String,DataDB>();
			        for(DataDB db:dbService.findAll()){
			        	dbs.put(String.valueOf(db.getDbId()), db);
			        }
				List<WebReport> reports=TestCore.runTest(webCase,reportCase,path,config,user.getRealName(),dbs);
				for(WebReport report:reports){
					reportService.addReport(report);			
					//上传图片
					if(report.getCapturePath()!=null&&!report.getCapturePath().equals("")){
						System.out.println("正在上传截图:"+report.getCapturePath());
						UploadShot.uploadFile(report.getCapturePath());
					}
					
				}
			}
		}
		Timestamp t1 = new Timestamp(System.currentTimeMillis());
		setService.addCount(info.getRelatedId());
		returnMap.put("testStutas", "0");
		returnMap.put("finishTime", t1.toString());
		info.setStatus("0");
		info.setFinishTime(t1);
		rmiService.edit(info);
		return returnMap;
	}
	
}
