package com.ljzforum.platform.util;


import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientUtil {
	static Logger logger = Logger.getLogger(HttpClientUtil.class);
	static CloseableHttpClient httpclient = HttpClients.createDefault();

	public static JSONObject getData(String url) {
		String json = null;

		RequestConfig.Builder builder = RequestConfig.custom();
		builder.setCircularRedirectsAllowed(true);// 设置是否区分调转
		RequestConfig config = builder.build();
		HttpGet httpget = new HttpGet(url);
		httpget.setConfig(config);
		try {
			CloseableHttpResponse response = httpclient.execute(httpget);
			logger.info("getStatusCode>>>>>:"
					+ response.getStatusLine().getStatusCode());

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				logger.info("Response content length: "
						+ entity.getContentLength());
				json = EntityUtils.toString(entity);
			}
			httpget.abort();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		return jsonObject;
	}
	
	public static void main(String[] args) {
		
	}
}
