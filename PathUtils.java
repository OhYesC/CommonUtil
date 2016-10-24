package com.ljzforum.platform.util;

import java.io.File;

import org.apache.log4j.Logger;

public class PathUtils {

	public static Logger logger = Logger.getLogger(PathUtils.class);
	
	/**
	 * 拼接文件路径
	 * type(1.user 2.project)
	 */
	public static String jointFilePath(Integer id,String fileName, Integer type) {
		String basePath = (String) CustomizedPropertyConfigurer.getContextProperty("file_base_path");
		StringBuffer filePath = new StringBuffer(basePath);
		// 创建用户目录
		if(type == null){
			filePath.append("/").append("temp");
		}else if(type == 1){
			filePath.append("/").append("user").append("/").append(id);
		}else if(type == 2){
			filePath.append("/").append("project").append("/").append(id);
		}
		
		//若文件名不为空则拼接文件具体路径
		if(fileName != null){
			filePath.append("/").append(fileName);
		}
		logger.debug("【服务器路径：】"+filePath.toString());
		return filePath.toString();
	}
	
	/**
	 * 获取文件的绝对路径
	 * @param file
	 * @return
	 */
	public static String getAbsolutePath(File file){
		if(file.getAbsolutePath().indexOf("\\") != -1){
			return file.getAbsolutePath().replace("\\", "/");
		}
		return file.getAbsolutePath();
	}
	
}
