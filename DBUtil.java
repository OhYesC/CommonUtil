package com.ljzforum.platform.util;

import org.apache.commons.lang.StringUtils;  
import org.apache.ibatis.session.Configuration;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory; 

public class DBUtil {
	
	private static final String DBTYPE_MYSQL = "MySQL";//支持的类型：MySQL,Oracle  
    private static final String DBTYPE_ORACLE = "Oracle";//支持的类型：MySQL,Oracle  
  
    private static Logger logger = LoggerFactory.getLogger(DBUtil.class);  
    private Configuration configuration = null;  
      
    private String dbType = "";  
    private String defaultDateFormat = "";  
      
    public DBUtil(Configuration configuration){  
        if(configuration == null){  
            logger.error("系统启动失败：MyBatis Configuration 对象为空！");  
            throw new IllegalArgumentException("系统启动失败：MyBatis Configuration 对象为空！");  
        }  
        this.configuration = configuration;  
        this.dbType = this.configuration.getVariables().getProperty("dbtype");  
        if(StringUtils.isBlank(dbType)){  
            logger.error("数据库类型没有配置！");  
        } else {  
            logger.info("数据库类型为："+dbType);  
        }  
        this.defaultDateFormat = this.configuration.getVariables().getProperty("defaultDateFormat");  
        if(StringUtils.isBlank(this.defaultDateFormat)){  
            this.defaultDateFormat="yyyy-MM-dd";  
            logger.info("数据库日期默认格式字符串没有指定！系统默认为：yyyy-MM-dd");  
        } else {  
            logger.info("数据库日期默认格式字符串："+this.defaultDateFormat);  
        }  
    }  
      
    /** 
     * 判断是否是Oracle数据库 
     * @return 
     * @author wangdf 
     */  
    public boolean isOracle(){  
        return DBTYPE_ORACLE.equals(this.dbType);  
    }  
      
      
    /** 
     * 判断是否是MySQL数据库 
     * @return 
     * @author wangdf 
     */  
    public boolean isMySQL(){  
        return DBTYPE_MYSQL.equals(this.dbType);  
    }  
      
    /** 
     * 取得数据库类型 
     * @return 
     * @author wangdf 
     */  
    public String getDbType(){  
        return this.dbType;  
    }  
      
      
    /** 
     * 取得默认日期格式 
     * @return 
     * @author wangdf 
     */  
    public String getDefaultDateFormat(){  
        return this.defaultDateFormat;  
    }  

}
