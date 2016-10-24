package com.ljzforum.platform.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;


public class HttpConnentUtils {
	static Logger log = Logger.getLogger(HttpConnentUtils.class);
	/**
	 * post提交数据
	 * 
	 * @param requestUrl 访问url
	 * @param param 参数用json封装
	 * @return 结果返回json对象
	 */
	public static JSONObject postData(String requestUrl, String param) {
		Map<String, String> logTag = new HashMap<String, String>();

		
		JSONObject obj = null;
		HttpURLConnection connection = null;
		BufferedReader reader=null;
		DataOutputStream out=null;
		PrintWriter printWriter = null;
		String apiUrl = requestUrl;
		
		logTag.put("apiUrl", apiUrl);
		logTag.put("param", param);
		log.info("postData请求数据:"+requestUrl+":param="+param);

		try {
			//创建连接
			URL url = new URL(apiUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(6000);
			connection.setReadTimeout(6000);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			connection.setRequestProperty("Content-Length", String.valueOf(param.length()));
			
			printWriter = new PrintWriter(connection.getOutputStream());
			printWriter.write(param);
			printWriter.flush();
//			connection.connect();
			
			// 读取响应
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			log.info("postData请求数据结果:"+sb.toString());

			obj = JSONObject.fromObject(sb.toString());
		} catch (Exception e) {
			log.error("postData请求数据出错:",e);

		} finally {
			try {
				if(reader!= null){
					reader.close();
				}
				// 断开连接
				if (connection != null){
					connection.disconnect();
				}
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	/**
	 * post提交数据
	 */
	public static byte[] postDataStr(String requestUrl, String param) {	
		HttpURLConnection connection = null;
		BufferedReader reader=null;
		DataOutputStream out=null;
		PrintWriter printWriter = null;
		byte[] data = null;
		try {
			//创建连接
			URL url = new URL(requestUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(6000);
			connection.setReadTimeout(6000);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			
			printWriter = new PrintWriter(connection.getOutputStream());
			printWriter.write(param);
			printWriter.flush();		
			// 读取响应
			InputStream inStream = connection.getInputStream();
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int len = 0;
	        while( (len=inStream.read(buffer)) != -1 ){
	            outStream.write(buffer, 0, len);
	        }
	        inStream.close();
	        data = outStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader!= null){
					reader.close();
				}
				// 断开连接
				if (connection != null){
					connection.disconnect();
				}
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
				log.error(e, e);
			}
		}
		return data;
	}

	/**
	 * get请求返回数据
	 */
	public static JSONObject getData(String requestUrl, JSONObject param,String cookies, Map<String,String> tags) {

		
		JSONObject obj = null;
		HttpURLConnection connection = null;
		BufferedReader reader = null;
//		 setProxy("192.168.75.171", "80");  
		String string = param.toString();
		String paramStr = string.substring(1, string.length() - 2).replaceAll(":", "=").replaceAll("\"", "").replaceAll(",", "&");
		String apiurl = requestUrl + "?" + paramStr;
		log.info("http getData请求:"+apiurl);

		try {
			// 创建连接
			URL url = new URL(apiurl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(6000);
			connection.setReadTimeout(6000);
			// 默认情况下是false;
			connection.setDoOutput(false);
			// 设置是否从httpUrlConnection读入，默认情况下是true
			connection.setDoInput(true);
			// Get 请求不能使用缓存
			connection.setUseCaches(false);
			// 设定请求的方法为"GET"，默认是GET
			connection.setRequestMethod("GET");
			if(cookies!=null){
				connection.setRequestProperty("Cookie", cookies);
			}
			connection.connect();
			
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			log.info("http getData请求结果:"+sb.toString());
		
			obj = JSONObject.fromObject(sb.toString());
		} catch (Exception e) {
			log.error("http getData请求出错", e);

		} finally {
			try {
				if(reader!= null){
				reader.close();
				}
				// 断开连接
				if (connection != null){
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	
	
	
	/**
	 * get请求返回数据
	 * 
	 * @param requestUrl 访问url
	 * @param param 参数用json封装
	 * @return
	 */
	public static JSONObject getData(String requestUrl, JSONObject param,String cookies) {
		return getData(requestUrl, param, cookies, null);
	}
	
	public static String getDataToString(String requestUrl, JSONObject param,String cookies) {
		Map<String, String> logTag = new HashMap<String, String>();
		
//		JSONObject obj = null;
		HttpURLConnection connection = null;
		BufferedReader reader = null;
//		 setProxy("192.168.75.171", "80");  
		String string = param.toString();
		String paramStr = string.substring(1, string.length() - 2).replaceAll(":", "=").replaceAll("\"", "").replaceAll(",", "&");
		String apiurl = requestUrl + "?" + paramStr;
		
		logTag.put("requestUrl", requestUrl);
		logTag.put("param", param.toString());
		logTag.put("apiurl", apiurl);
		log.info("getDataToString 请求数据:"+apiurl);
		try {
			// 创建连接
			URL url = new URL(apiurl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(6000);
			connection.setReadTimeout(6000);
			// 默认情况下是false;
			connection.setDoOutput(false);
			// 设置是否从httpUrlConnection读入，默认情况下是true
			connection.setDoInput(true);
			// Get 请求不能使用缓存
			connection.setUseCaches(false);
			// 设定请求的方法为"GET"，默认是GET
			connection.setRequestMethod("GET");
			if(cookies!=null){
				connection.setRequestProperty("Cookie", cookies);
			}
			
			connection.connect();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			log.info("http getDataToString请求结果:"+sb.toString());

			return sb.toString();
		} catch (Exception e) {
			log.error("http getDataToString请求出错", e);

		} finally {
			try {
				if(reader!= null){
				reader.close();
				}
				// 断开连接
				if (connection != null){
					connection.disconnect();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	
	/**
	 * POST请求返回数据
	 * 
	 * @param requestUrl 访问url
	 * @param paramMap 参数用json封装
	 * @return
	 */
	public static JSONObject getData(String requestUrl, Map<String, Object> paramMap) {
		Map<String, String> logTag = new HashMap<String, String>();
		
		JSONObject obj = null;
		HttpURLConnection connection = null;
		BufferedReader reader = null;
//		 setProxy("192.168.75.171", "80"); 
		StringBuffer paramSb  = new StringBuffer();
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			String key = entry.getKey();
			String val= entry.getValue().toString();
			paramSb.append(key).append("=").append(val).append("&");
		}
		String paramStr = paramSb.substring(0, paramSb.length()-1);
		String apiurl = requestUrl + "?" + paramStr;
		logTag.put("requestUrl", requestUrl);
		log.info("getData 请求数据:"+apiurl);
		try {
			// 创建连接
			URL url = new URL(apiurl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(6000);
			connection.setReadTimeout(6000);
			// 默认情况下是false;
			connection.setDoOutput(false);
			// 设置是否从httpUrlConnection读入，默认情况下是true
			connection.setDoInput(true);
			// POST请求不能使用缓存
			connection.setUseCaches(false);
			// 设定请求的方法为"POST"，默认是GET
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept-Charset", "gzip,deflate");
			
			connection.connect();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			log.info("getData 请求数据结果:"+apiurl);

			obj = JSONObject.fromObject(sb.toString());
		} catch (Exception e) {
			log.error("getData 请求数据结果出错",e);

		} finally {
			try {
				if(reader!= null){
				reader.close();
				}
				// 断开连接
				if (connection != null){
					connection.disconnect();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	public static JSONObject request(String requestURI, String requestMethod, Map<String, String> parameterMap) throws IOException {
        //处理业务逻辑
        Connection conn = HttpConnection.connect(requestURI);
        conn.timeout(20000);
        conn.header("Accept-Encoding", "gzip,deflate");
        conn.header("Connection", "close");
        if ("get".equalsIgnoreCase(requestMethod)) {
            conn.request().method(Connection.Method.GET);
        } else if ("post".equalsIgnoreCase(requestMethod)) {
            conn.request().method(Connection.Method.POST);
        }
        if(parameterMap!=null){
        	conn.data(parameterMap);
        }
        conn.ignoreContentType(true);
        log.debug("requestURI:"+requestURI);
        log.debug("parameterMap:"+parameterMap.toString());
        Connection.Response response = conn.execute();
        if (response.statusCode() == 200) {
            //是否以"Exception:"开头
            String body = response.body();
            JSONObject jo = JSONObject.fromObject(body);
            if(!(boolean)jo.get("success")){
                throw new RuntimeException(body);
            }
            return JSONObject.fromObject(body);
        } else {
            throw new RuntimeException("请求内部api报错!"+requestURI);
        }
    }
	
	
	public static void setProxy(String host, String port) {  
        System.setProperty("proxySet", "true");  
        System.setProperty("proxyHost", host);  
        System.setProperty("proxyPort", port);  
	}
	
}
