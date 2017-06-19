package com.dcits.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ʵ��С������
 * @author Administrator
 *
 */
public class PracticalUtils {
	
	/**
	 * �ж��ַ����Ƿ�Ϊ����
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
	
	/**
	 * arrayList��toString����
	 * @param list
	 * @return
	 */
	public static String arrayListToString(ArrayList<String> list){
		String returnStr = "";
		for(String s:list){
			returnStr+="["+s+"]";
		}
		return returnStr;
	}
	
	/**
	  * 
	  * @param s  Ҫ�������ַ���
	  * @param string  Ҫɾ�����ַ�
	  * @param i  ɾ���ڼ���
	  * @return
	  */
	 public static String removeChar(String s,String string,int i){
	  if(i==1){
	   int j=s.indexOf(string);
	   s=s.substring(0, j)+s.substring(j+1);
	   i--;
	   return s;
	  }else{
	   int j=s.indexOf(string);
	   i--;
	   return s.substring(0, j+1)+removeChar(s.substring(j+1), string, i);
	  }

	}
	 
	 
	 /**
	  * �滻sql����Ҫ�滻�Ĳ���
	  * ������ʽ<������>
	  * @param sqlStr
	  * @param map
	  * @return
	  */
	 public static String replaceSqlStr(String sqlStr,Map<String,Object> map){
		 String regex = "(<[a-zA-Z0-9]*>)";
		 Pattern pattern = Pattern.compile(regex);
		 List<String> regStrs = new ArrayList<String>();
		 Matcher matcher = pattern.matcher(sqlStr);//ƥ����
		 while (matcher.find()) {
			regStrs.add(matcher.group());
		 }
		 for(String s:regStrs){
			 if(map.get(s)!=null){
				 sqlStr = sqlStr.replaceAll(s, (String)map.get(s));
			 }
		 }
		 return sqlStr;
	 }
	 
	 public static String taskStatus(String statusCode){
		String statusStr = "δ֪״̬";
		switch (statusCode) {
		case "0":
			statusStr = "�������";
			break;
		case "1":
			statusStr = "�ύ������";
			break;
		case "2":
			statusStr = "���ڲ���";
			break;
		case "3":
			statusStr = "�������ô���,����ʧ��";
			break;
		case "4":
			statusStr = "ϵͳ����,����ʧ��";
			break;
		case "5":
			statusStr = "������ʧЧ";
			break;
		default:
			break;
		}
		return statusStr;
	 }
}
