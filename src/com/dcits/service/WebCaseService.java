package com.dcits.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.bean.WebCase;
import com.dcits.bean.WebStep;
import com.dcits.dao.WebCaseDao;
import com.dcits.dao.WebStepDao;

@Service
public class WebCaseService {
	
	@Autowired
	private WebCaseDao webCaseDao;
	
	@Autowired
	private WebStepDao webStepDao;
	
	/**
	 * ��ѯ���еĲ�������-�������Լ�������
	 * @return
	 */
	public List<WebCase> findAll(Integer userId){
		return webCaseDao.findMyCases(userId);
	}
	
	/**
	 * ����һ����������
	 * ���߱༭���²�������
	 * @param c
	 * @return
	 */
	public void editCase(WebCase c){
		webCaseDao.edit(c);
	}
	
	
	/**
	 * ����ID����ָ������������Ϣ
	 * @param caseId
	 * @return
	 */
	public WebCase getCase(Integer caseId){
		return webCaseDao.get(caseId);
	}
	
	/**
	 * ɾ����������
	 * ɾ��ǰ��Ҫ�ֶ������ϵ��ɾ����ص�����
	 * @param caseId
	 */
	public void delCase(Integer caseId){
		webCaseDao.delete(caseId);
	}
	
	/**
	 * ɾ�����Բ���
	 * ɾ��ǰ��Ҫ�����webReport�Ĺ�����ϵ
	 * ɾ������Ҫ���¼�����������µĲ��Բ����ִ��˳��
	 * @param stepId
	 */
	public void delStep(Integer stepId){
		webStepDao.delete(stepId);		
	}
	
	/**
	 * ɾ��һ�����Բ���֮�����¶�orderNum��������
	 * @param caseId
	 */
	public void resortSteps(Integer caseId,Integer orderNum){
		WebCase c=getCase(caseId);	
		List<WebStep> steps=new ArrayList<WebStep>(c.getSteps());
		
		int size=steps.size();
		if(orderNum<=size){
			WebStep step;	
			for(int i=0;i<size-1;i++){
				for(int j=i+1;j<size;j++){
					if(steps.get(i).getOrderNum()>steps.get(j).getOrderNum()){
						step=steps.get(i);
						steps.set(i, steps.get(j));
						steps.set(j, step);
						
					}
				}
			}
			for(int i=0;i<size;i++){
				steps.get(i).setOrderNum(i+1);
			}
			c.addSteps(new HashSet<WebStep>(steps));
			editCase(c);
		}	
	}
	
	/**
	 * ���»����������Բ���
	 * @param step
	 */
	public void editStep(WebStep step){
		webStepDao.edit(step);
	}
	
	/**
	 * ����ָ����webStep
	 * @param stepId
	 * @return
	 */
	public WebStep getStep(Integer stepId){
		return webStepDao.get(stepId);
	}
}
