package com.dcits.bean;

import java.util.HashSet;
import java.util.Set;

public class WebObjectCategory {
	
	private Integer categoryId;
	private String categoryName;
	private String categoryType;
	
	//���е��ӽڵ㼯��
	private Set<WebObjectCategory> webObjectCategories=new HashSet<WebObjectCategory>();
	//ֻ��page���͵�ӵ��WebObject����
	private Set<WebObject> webObjects=new HashSet<WebObject>();
	//����ĸ��ڵ�,website���͵�û��  ��Ϊnull
	private WebObjectCategory webObjectCategory;
	
	private String name;
	
	private Integer parentCategoryId;
	
	private Integer objectNum;
	
	public Integer getObjectNum() {
		return this.objectNum;
	}

	public void setObjectNum() {
		this.objectNum = this.webObjects.size()+getChildObjectNum(this);
	}

	public Integer getChildObjectNum(WebObjectCategory c){
		Integer a=0;
		for(WebObjectCategory w:c.getWebObjectCategories()){
			a+=w.getWebObjects().size()+getChildObjectNum(w);
		}
		return a;
	}

	public void setName() {
		this.name = this.categoryName;
	}
	
	public void setParentCategoryId() {
		if(webObjectCategory!=null){
			this.parentCategoryId = webObjectCategory.getCategoryId();
		}else{
			this.parentCategoryId=0;
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getParentCategoryId() {
		return parentCategoryId;
	}


	public WebObjectCategory getWebObjectCategory() {
		return webObjectCategory;
	}

	public void setWebObjectCategory(WebObjectCategory webObjectCategory) {
		this.webObjectCategory = webObjectCategory;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public Set<WebObjectCategory> getWebObjectCategories() {
		return webObjectCategories;
	}

	public void setWebObjectCategories(Set<WebObjectCategory> webObjectCategories) {
		this.webObjectCategories = webObjectCategories;
	}

	public Set<WebObject> getWebObjects() {
		return webObjects;
	}

	public void setWebObjects(Set<WebObject> webObjects) {
		this.webObjects = webObjects;
	}

	public WebObjectCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WebObjectCategory(String categoryName, String categoryType,
			WebObjectCategory webObjectCategory) {
		super();
		this.categoryName = categoryName;
		this.categoryType = categoryType;
		this.webObjectCategory = webObjectCategory;
	}

	
	
	
	
}
