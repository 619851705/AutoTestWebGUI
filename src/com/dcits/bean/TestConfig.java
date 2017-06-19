package com.dcits.bean;

public class TestConfig {
	private Integer connectTimeOut;
	private Integer readTimeOut;
	private String httpMethodFlag;
	public Integer getConnectTimeOut() {
		return connectTimeOut;
	}
	public void setConnectTimeOut(Integer connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
	public Integer getReadTimeOut() {
		return readTimeOut;
	}
	public void setReadTimeOut(Integer readTimeOut) {
		this.readTimeOut = readTimeOut;
	}
	public String getHttpMethodFlag() {
		return httpMethodFlag;
	}
	public void setHttpMethodFlag(String httpMethodFlag) {
		this.httpMethodFlag = httpMethodFlag;
	}
	public TestConfig() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TestConfig(Integer connectTimeOut, Integer readTimeOut,
			String httpMethodFlag) {
		super();
		this.connectTimeOut = connectTimeOut;
		this.readTimeOut = readTimeOut;
		this.httpMethodFlag = httpMethodFlag;
	}
	
	
}
