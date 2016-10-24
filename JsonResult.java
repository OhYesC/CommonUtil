package com.ljzforum.platform.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

public class JsonResult {

	public static int JSON_RESULT_STATUS_SUCCESS = 1;

	public static int JSON_RESULT_STATUS_FAILED = 0;
	
	private Integer status; 			// 状态1-成功/0-失败

	private String info; 				// 失败信息
	
	protected Object data; 				// 数据
	
	public String toString() {
		JSONObject obj = JSONObject.fromObject(this);
		return obj == null ? null : obj.toString();
	}

	public void setStatusSuccess() {
		setStatus(JSON_RESULT_STATUS_SUCCESS);
	}
	
	public void setStatusFailed() {
		setStatus(JSON_RESULT_STATUS_FAILED);
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}

	public void setDefaultInfo() {
		setInfo("系统异常");
	}
	
	public void setNeedLogin() {
		//未登录
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("needlogin", 1);
		
		setData(map);
	}

	/**
	 * 成功json
	 * @author chuijian.kong
	 * @date 2014年12月24日 下午1:01:27
	 * @version 1.0
	 */
	public static String successResult() {
		return successResult(null);
	}
	
	/**
	 * 成功json
	 * @author chuijian.kong
	 * @date 2014年12月24日 下午1:01:27
	 * @version 1.0
	 */
	public static String successResult(Object key, Object value) {
		return successResult(generateData(key, value));
	}
	
	/**
	 * 成功json
	 * @author chuijian.kong
	 * @date 2014年12月24日 下午1:01:27
	 * @version 1.0
	 */
	public static String successResult(Object data) {
		JsonResult json = new JsonResult();
		
		json.setData(data);
		json.setStatusSuccess();
		
		return json.toString();
	}
	
	/**
	 * 失败json
	 * @author chuijian.kong
	 * @date 2014年12月24日 下午1:01:27
	 * @version 1.0
	 */
	public static String failedResult(Object data) {
		return failedResult(data, null);
	}
	
	/**
	 * 失败json
	 * @author chuijian.kong
	 * @date 2014年12月24日 下午1:01:27
	 * @version 1.0
	 */
	public static String failedResult(String errorMsg) {
		return failedResult(null, errorMsg);
	}
	
	/**
	 * 失败json
	 * @author chuijian.kong
	 * @date 2014年12月24日 下午1:01:27
	 * @version 1.0
	 */
	public static String failedResult(Object data, String errorMsg) {
		JsonResult json = new JsonResult();
		
		json.setData(data);
		json.setStatusFailed();
		
		if (StringUtils.isBlank(errorMsg)) {
			json.setDefaultInfo();
		}else{
			json.setInfo(errorMsg);
		}
		
		return json.toString();
	}
	
	/**
	 * 失败json
	 * @author chuijian.kong
	 * @date 2014年12月24日 下午1:01:27
	 * @version 1.0
	 */
	public static String failedResult(String dataKey, Object data, String errorMsg) {
		JsonResult json = new JsonResult();
		
		json.setData(generateData(dataKey, data));
		json.setStatusFailed();
		
		if (StringUtils.isBlank(errorMsg)) {
			json.setDefaultInfo();
		}else{
			json.setInfo(errorMsg);
		}
		
		return json.toString();
	}
	
	/**
	 * 未登录json
	 * @author chuijian.kong
	 * @date 2014年12月24日 下午1:01:27
	 * @version 1.0
	 */
	public static String nologinResult() {
		JsonResult json = new JsonResult();
		json.setNeedLogin();
		json.setStatusSuccess();
		
		return json.toString();
	}
	
	/**
	 * 生成data
	 * @author chuijian.kong
	 * @date 2014年12月24日 下午1:01:27
	 * @version 1.0
	 */
	public static Map<Object, Object> generateData(Object key, Object value) {
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put(key, value);
		
		return data;
	}
}