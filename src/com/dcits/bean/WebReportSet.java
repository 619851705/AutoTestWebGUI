package com.dcits.bean;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WebReportSet {
	private Integer reportSetId;
	private Timestamp testTime;
	private WebCaseSet webCaseSet;

	
	private Set<WebReportCase> webReportCases=new HashSet<WebReportCase>();

	/*����Ľ���ҳ��չʾ��*/
	private Map<String,Integer> testDetails=new HashMap<String,Integer>();
	private String setName;
	
	
	public WebReportSet(Timestamp testTime, WebCaseSet webCaseSet) {
		super();
		this.testTime = testTime;
		this.webCaseSet = webCaseSet;
	}

	
	
	
	
	public String getSetName() {
		return setName;
	}





	public void setSetName() {
		this.setName = this.webCaseSet.getSetName();
	}





	public Map<String, Integer> getTestDetails() {
		return testDetails;
	}





	public void setTestDetails() {
		Integer successNum=0;
		Integer failNum=0;
		this.testDetails.put("caseNum", this.getWebCaseSet().getEnableComps().size());
		this.testDetails.put("executeNum", this.getWebReportCases().size());
		
		for(WebReportCase report:this.getWebReportCases()){
			report.setStatus();
			if(report.getStatus().equals("success")){
				successNum++;
			}else{
				failNum++;
			}
		}
		this.testDetails.put("successNum", successNum);
		this.testDetails.put("failNum",failNum );		
	}





	public Set<WebReportCase> getWebReportCases() {
		return webReportCases;
	}




	public void setWebReportCases(Set<WebReportCase> webReportCases) {
		this.webReportCases = webReportCases;
	}





	public WebCaseSet getWebCaseSet() {
		return webCaseSet;
	}

	public void setWebCaseSet(WebCaseSet webCaseSet) {
		this.webCaseSet = webCaseSet;
	}



	public Timestamp getTestTime() {
		return testTime;
	}

	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}

	public Integer getReportSetId() {
		return reportSetId;
	}

	public void setReportSetId(Integer reportSetId) {
		this.reportSetId = reportSetId;
	}


	public WebReportSet() {
		super();
		// TODO Auto-generated constructor stub
	}
		
}
