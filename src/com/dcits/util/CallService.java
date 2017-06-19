package com.dcits.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.dcits.bean.TestConfig;


/**
 * ͨ��HttpUrlConnection����Web�ӿ�
 * @author xwc
 *
 */
public class CallService {
	public static Map<String,String> callService(String requestUrl,String requestJson,TestConfig config){
		Map<String,String> returnMap=new HashMap<String,String>();
		String responseJson="";
		String useTime="0";
		String runStatus="0";
		int statusCodeN=0;
		HttpURLConnection httpConn=null;
		try {
			URL url = new URL(requestUrl);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true);// ʹ�� URL ���ӽ������   
			httpConn.setDoInput(true);// ʹ�� URL ���ӽ�������   
			httpConn.setUseCaches(false);// ���Ի���   
			httpConn.setConnectTimeout(config.getConnectTimeOut());//���ӳ�ʱʱ��
			httpConn.setReadTimeout(config.getReadTimeOut());//��ȡ��ʱʱ��
			httpConn.addRequestProperty("Content-type","application/json;charset=UTF-8");
			String method="POST";
			if(config.getHttpMethodFlag().equals("1")){
				method="GET";
			}
			httpConn.setRequestMethod(method);// ����URL���󷽷�   
			
			
			byte[] bypes = requestJson.toString().getBytes();
			
			httpConn.getOutputStream().write(bypes);// �������
			
			long beginTime=System.currentTimeMillis();
			statusCodeN=httpConn.getResponseCode();
			long endTime=System.currentTimeMillis();
			
			//�ж�http code
			if ( statusCodeN != 200){
				
			}else{
				InputStream in = httpConn.getInputStream();				
				//����ʱ��
				useTime=String.valueOf(endTime-beginTime);
				int iTotal = httpConn.getContentLength();
				byte[] bt = new byte[2048];
				int len = 0;
				ByteArrayOutputStream bout = new ByteArrayOutputStream(
						iTotal == -1 ? 50 * 1024 : iTotal);
				
				while ((len = in.read(bt)) != -1) {
					
					bout.write(bt, 0, len);
					
				}
					
				//��ȡ��������
				byte[] bta = bout.toByteArray();
				responseJson = new String(bta, "UTF-8");
				runStatus="0";
			}
			
		} catch (Exception e) {		
			responseJson = e.toString();
			runStatus="2";
			
		}finally{
			if(httpConn!=null){
			//�Ͽ�����
			httpConn.disconnect();
			
			}
			returnMap.put("statusCode", String.valueOf(statusCodeN));
			returnMap.put("responseJson", responseJson);
			returnMap.put("useTime", useTime);	
			returnMap.put("runStatus",runStatus);
		}
		return returnMap;				
	}
}
