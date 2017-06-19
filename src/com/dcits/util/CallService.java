package com.dcits.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.dcits.bean.TestConfig;


/**
 * 通过HttpUrlConnection调用Web接口
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
			httpConn.setDoOutput(true);// 使用 URL 连接进行输出   
			httpConn.setDoInput(true);// 使用 URL 连接进行输入   
			httpConn.setUseCaches(false);// 忽略缓存   
			httpConn.setConnectTimeout(config.getConnectTimeOut());//连接超时时间
			httpConn.setReadTimeout(config.getReadTimeOut());//读取超时时间
			httpConn.addRequestProperty("Content-type","application/json;charset=UTF-8");
			String method="POST";
			if(config.getHttpMethodFlag().equals("1")){
				method="GET";
			}
			httpConn.setRequestMethod(method);// 设置URL请求方法   
			
			
			byte[] bypes = requestJson.toString().getBytes();
			
			httpConn.getOutputStream().write(bypes);// 输入参数
			
			long beginTime=System.currentTimeMillis();
			statusCodeN=httpConn.getResponseCode();
			long endTime=System.currentTimeMillis();
			
			//判断http code
			if ( statusCodeN != 200){
				
			}else{
				InputStream in = httpConn.getInputStream();				
				//调用时间
				useTime=String.valueOf(endTime-beginTime);
				int iTotal = httpConn.getContentLength();
				byte[] bt = new byte[2048];
				int len = 0;
				ByteArrayOutputStream bout = new ByteArrayOutputStream(
						iTotal == -1 ? 50 * 1024 : iTotal);
				
				while ((len = in.read(bt)) != -1) {
					
					bout.write(bt, 0, len);
					
				}
					
				//获取出参内容
				byte[] bta = bout.toByteArray();
				responseJson = new String(bta, "UTF-8");
				runStatus="0";
			}
			
		} catch (Exception e) {		
			responseJson = e.toString();
			runStatus="2";
			
		}finally{
			if(httpConn!=null){
			//断开连接
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
