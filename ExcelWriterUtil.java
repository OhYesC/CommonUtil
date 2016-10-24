package com.ljzforum.platform.util;



import java.io.IOException;
import java.net.URL;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class ExcelWriterUtil {
	 public void createExcel(String templateFileName, Map<String,Object> beanParams, String resultFileName){  
	        //创建XLSTransformer对象  
	        XLSTransformer transformer = new XLSTransformer();  
	        //获取java项目编译后根路径  
	        URL url = this.getClass().getClassLoader().getResource("/template");  
	        //得到模板文件路径  
	        String srcFilePath = url.getPath() + templateFileName;  
	        String destFilePath = url.getPath() + resultFileName;  
	        try {  
	            //生成Excel文件  
	            transformer.transformXLS(srcFilePath, beanParams, destFilePath);  
	        } catch (ParsePropertyException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    }  
}
