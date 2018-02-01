package com.bupt.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
public class HttpUtils {
	/**
     * 向指定URL发送PUT方法的请求
     * @param url   发送请求的URL        
     * @param headers   请求参数，请求头参数。默认的请求编码为  utf-8.
     * @param content   Post请求的内容
     * @return String   请求返回的结果
     */
	public static String httpPut(String url,Map<String, String> headers, String content){
        return httpRequest(url, headers,"PUT", content);
    }
	
	/**
     * 向指定URL发送POST方法的请求
     * @param url   发送请求的URL        
     * @param headers   请求参数，请求头参数。默认的请求编码为  utf-8.
     * @param content   Post请求的内容
     * @return String   请求返回的结果
     */
	public static String httpPost(String url,Map<String, String> headers, String content){
        return httpRequest(url, headers,"POST", content);
    }
	
	/**
     * 向指定URL发送GET方法的请求
     * @param url   发送请求的URL        
     * @param headers   请求参数，请求头参数。默认的请求编码为  utf-8.
     * @return String   请求返回的结果
     */
    public static String httpGet(String url,Map<String, String> headers){
        return httpRequest(url,headers,"GET");
    }
    
    /**
     * 向指定URL发送DELETE方法的请求
     * @param url   发送请求的URL        
     * @param headers   请求参数，请求头参数。默认的请求编码为  utf-8.
     * @return String   请求返回的结果
     */
    public static String httpDel(String url,Map<String, String> headers,String content){
        return httpRequest(url,headers,"DELETE",content);
    }
    // GET-method and delete-method is the same;
    public static String httpRequest(String url,Map<String, String> headers,String method){
        URL urlClass=null;
        try {
        	urlClass = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) urlClass.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            //默认为GET请求
            connection.setRequestMethod(method);
            // 设置通用的请求属性
            connection.setRequestProperty("Content-type", "application/json");
            if (headers != null && headers.size() > 0){
            	for(Entry<String, String> entry : headers.entrySet()){
                    connection.setRequestProperty(entry.getKey(), entry.getValue());               
                   }
            }
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            StringBuilder sb = new StringBuilder();
            String line="";
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            reader.close();
            connection.disconnect();
            return sb.toString();

            
        } catch (IOException e) {
            return null;
        } 
    }
    // POST-method and PUT-method is the same.
    public static String httpRequest(String url, Map<String, String> headers, String method,String content){
        URL urlClass = null;
        try {
            // 打开和URL之间的连接
            urlClass = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlClass .openConnection();
            connection.setDoOutput(true);
            // Read from the connection. Default is true.
            connection.setDoInput(true);
            // Set the post method. Default is GET
            connection.setRequestMethod(method);
            // Post cannot use caches
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-type", "application/json");
            if (headers != null && headers.size() > 0){
            	for(Entry<String, String> entry : headers.entrySet()){
                    connection.setRequestProperty(entry.getKey(), entry.getValue());               
                   }
            }
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            // The URL-encoded contend
            out.writeBytes(content); 
            out.flush();
            out.close(); // flush and close
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            StringBuilder sb = new StringBuilder();
            String line="";
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            reader.close();
            connection.disconnect();
            return sb.toString();
        } catch (IOException e) {
            return null;
        } 
    }
	public static void main(String[] args) {
		

	}

}
