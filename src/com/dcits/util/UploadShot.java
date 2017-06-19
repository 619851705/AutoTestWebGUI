package com.dcits.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class UploadShot {
	public static void uploadFile(String fileName){ 
		String filePath=PropertiesUtil.getKeyValue("filePath");
		try {  
	        URL url = new URL(  
	                PropertiesUtil.getKeyValue("uploadUrl")+"?fileName="+fileName);  
	        // 发送POST请求必须设置如下两行  
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	  
	        conn.setDoOutput(true);  
	        conn.setUseCaches(false);  
	        conn.setRequestMethod("POST");  
	        conn.setRequestProperty("Content-Type", "text/html");  
	        conn.setRequestProperty("Cache-Control", "no-cache");  
	        conn.setRequestProperty("Charsert", "UTF-8");  
	        // conn.setRequestProperty("upFileFileName", "upFileFileName.doc");  
	        conn.connect();  
	        conn.setConnectTimeout(10000);  
	  
	        OutputStream out = conn.getOutputStream();  
	  
	        File file = new File(filePath+fileName);
	       
	        DataInputStream in = new DataInputStream(new FileInputStream(file));  
	        
	        int bytes = 0;  
	        byte[] buffer = new byte[1024];  
	        while ((bytes = in.read(buffer)) != -1) {  
	            out.write(buffer, 0, bytes);  
	        }  
	        in.close();  
	        out.flush();  
	        out.close();  
	  
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn  
	                .getInputStream()));  
	        String line = null;  
	        StringBuffer sb = new StringBuffer();  
	        while ((line = br.readLine()) != null) {  
	            sb.append(line);  
	        } 
	        System.out.println("文件发送完成,服务器返回(0为正常):"+sb.toString());
	        conn.disconnect();  
	    } catch (Exception e) {  
	       System.out.println("发送文件出现错误:"+e.toString());
	    }  
	}
}
