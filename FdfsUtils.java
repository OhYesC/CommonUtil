package com.ljzforum.platform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;

public abstract class FdfsUtils {
	static Logger logger = Logger.getLogger(FdfsUtils.class);
	
	static {
		try {
			String classPath = new File(FdfsUtils.class.getResource("/context")
					.getFile()).getCanonicalPath();
			String configFilePath = classPath + File.separator + "fdfs_client.conf";
			System.out.println(classPath);
			logger.info("Fdfs 配置文件:" + configFilePath);
			ClientGlobal.init(configFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("读取fdfs的配置发生错误", e);
		}
	}

	public static String upload(String fileStr) throws Exception {
		FileInputStream fis = null;
		try {
			File file = new File(fileStr);
			if (file == null || !file.exists()) {
				logger.info("当前文件不存在exists:"+fileStr);
				return null;
			}

			fis = new FileInputStream(file);
			byte[] fileBuffer = null;
			if (fis != null) {
				int len = fis.available();
				fileBuffer = new byte[len];
				fis.read(fileBuffer);
			}
			String url=upload(fileBuffer, "jpg");
			url=CustomizedPropertyConfigurer.getContextProperty("static_server")+url;
			logger.info("上传的url="+url+"---fileStr="+fileStr);
			return url;
		} catch (Exception e) {
			logger.error("上传文件发生错误", e);
			throw e;
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String upload(byte[] fileBuffer, String extension) throws Exception {
		String fdfsFile = "";
		StorageClient storageClient = new StorageClient();
		long startTime = System.currentTimeMillis();
		String[] results = storageClient.upload_file(fileBuffer, extension, null);
		logger.debug("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
		if (results == null) {
			logger.error("upload file fail, error code: " + storageClient.getErrorCode());
			return null;
		}
		String groupName = results[0];
		String remoteFilename = results[1];
		logger.debug(storageClient.get_file_info(groupName, remoteFilename));
		if (StringUtils.isNotEmpty(remoteFilename)) {
			fdfsFile = groupName + "/" + remoteFilename;
		}
		logger.debug(fdfsFile);
		// LoggerUtils.info("image fdfs location:%s", fdfsFile);
		return fdfsFile;
	}
	
	public static byte[] download(String path) throws Exception{
		String staticServer = (String)CustomizedPropertyConfigurer.getContextProperty("static_server");
		String pathArray = path.split(staticServer)[1];
		StorageClient storageClient = new StorageClient();
		long startTime = System.currentTimeMillis();
		String group = pathArray.split("/")[0];
		byte[] fileByte = storageClient.download_file(group, pathArray.split(group+"/")[1]);
		logger.debug("download_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
		if(fileByte == null){
			logger.error("download file fail, error code: " + storageClient.getErrorCode());
			return null;
		}
		
		return fileByte;
	}

	public static int deleteFile(String groupName, String remoteFileName)
			throws Exception {
		StorageClient storageClient = new StorageClient();
		return storageClient.delete_file(groupName, remoteFileName);
	}
	
	public static void main(String[] args) throws Exception {
		   File file = new File("D:/photo/2013春节");
		   FileInputStream in = null;
		   in = new FileInputStream(file);
		   byte[] buffer = new byte[in.available()];
		   in.read(buffer);
		   String filePath=FdfsUtils.upload(buffer, "jpg");
		   System.out.println(filePath);
	}

}
