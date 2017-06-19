package com.dcits.bean;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class WebReportCase {
	private Integer reportCaseId;
	private WebCase webCase;
	private WebReportSet webReportSet;
	private Timestamp testTime;
	
	//����ҳ��չʾ��
	private Integer stepNum;
	private String status;
	private String browser;
	private String caseName;
	
	private Set<WebReport> reports=new HashSet<WebReport>();
	
	public Integer getStepNum() {
		return stepNum;
	}

	public void setStepNum() {
		this.stepNum = this.reports.size();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus() {
		if(reports.size()>0){
			for(WebReport report:reports){
				if(!report.getRunStatus().equals("success")){
					this.status="fail";
					return;
				}
			}
			this.status="success";
			return;
		}			
		this.status="fail";		
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser() {
		this.browser = this.webCase.getBrowser();
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName() {
		this.caseName = this.webCase.getCaseName();
	}

	public Integer getReportCaseId() {
		return reportCaseId;
	}

	public void setReportCaseId(Integer reportCaseId) {
		this.reportCaseId = reportCaseId;
	}


	public WebCase getWebCase() {
		return webCase;
	}

	public void setWebCase(WebCase webCase) {
		this.webCase = webCase;
	}

	public WebReportSet getWebReportSet() {
		return webReportSet;
	}

	public void setWebReportSet(WebReportSet webReportSet) {
		this.webReportSet = webReportSet;
	}
	

	public Timestamp getTestTime() {
		return testTime;
	}

	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}

	public Set<WebReport> getReports() {
		return reports;
	}

	public void setReports(Set<WebReport> reports) {
		this.reports = reports;
	}

	public WebReportCase(WebCase webCase, WebReportSet webReportSet,
			Timestamp testTime) {
		super();
		this.webCase = webCase;
		this.webReportSet = webReportSet;
		this.testTime = testTime;
	}

	public WebReportCase() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
