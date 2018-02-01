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
     * ��ָ��URL����PUT����������
     * @param url   ���������URL        
     * @param headers   �������������ͷ������Ĭ�ϵ��������Ϊ  utf-8.
     * @param content   Post���������
     * @return String   ���󷵻صĽ��
     */
	public static String httpPut(String url,Map<String, String> headers, String content){
        return httpRequest(url, headers,"PUT", content);
    }
	
	/**
     * ��ָ��URL����POST����������
     * @param url   ���������URL        
     * @param headers   �������������ͷ������Ĭ�ϵ��������Ϊ  utf-8.
     * @param content   Post���������
     * @return String   ���󷵻صĽ��
     */
	public static String httpPost(String url,Map<String, String> headers, String content){
        return httpRequest(url, headers,"POST", content);
    }
	
	/**
     * ��ָ��URL����GET����������
     * @param url   ���������URL        
     * @param headers   �������������ͷ������Ĭ�ϵ��������Ϊ  utf-8.
     * @return String   ���󷵻صĽ��
     */
    public static String httpGet(String url,Map<String, String> headers){
        return httpRequest(url,headers,"GET");
    }
    
    /**
     * ��ָ��URL����DELETE����������
     * @param url   ���������URL        
     * @param headers   �������������ͷ������Ĭ�ϵ��������Ϊ  utf-8.
     * @return String   ���󷵻صĽ��
     */
    public static String httpDel(String url,Map<String, String> headers,String content){
        return httpRequest(url,headers,"DELETE",content);
    }
    // GET-method and delete-method is the same;
    public static String httpRequest(String url,Map<String, String> headers,String method){
        URL urlClass=null;
        try {
        	urlClass = new URL(url);
            // �򿪺�URL֮�������
            HttpURLConnection connection = (HttpURLConnection) urlClass.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            //Ĭ��ΪGET����
            connection.setRequestMethod(method);
            // ����ͨ�õ���������
            connection.setRequestProperty("Content-type", "application/json");
            if (headers != null && headers.size() > 0){
            	for(Entry<String, String> entry : headers.entrySet()){
                    connection.setRequestProperty(entry.getKey(), entry.getValue());               
                   }
            }
            // ����ʵ�ʵ�����
            connection.connect();
            // ���� BufferedReader����������ȡURL����Ӧ
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
            // �򿪺�URL֮�������
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
            // ���� BufferedReader����������ȡURL����Ӧ
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
