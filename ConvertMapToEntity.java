package com.ljzforum.platform.util;



import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

public class ConvertMapToEntity {
	protected static Logger logger = Logger.getLogger(ConvertMapToEntity.class);
	/**
	 * 实现结果集到实体对象/值对象/持久化对象转换
	 * 
	 * @param rsResult
	 *            ResultSet
	 * @param strEntity
	 *            String
	 * @throws Exception
	 * @return Object[]
	 */
	public static Object parseDataEntityBeans(Map rsResult,Class clazz) throws Exception {
		// 注册实体,strEntity指定的实体类名称字符串
		Class classEntity = Class.forName(clazz.getName());
		// 获取实体中定义的方法
		HashMap hmMethods = new HashMap();
		for (int i = 0; i < classEntity.getDeclaredMethods().length; i++) {
			MethodEntity methodEntity = new MethodEntity();
			// 方法的名称
			String methodName = classEntity.getDeclaredMethods()[i].getName();
			// 方法的参数
			Class[] paramTypes = classEntity.getDeclaredMethods()[i].getParameterTypes();

			String methodKey = methodName.toUpperCase();
			methodEntity.setMethodName(methodName);
			methodEntity.setMethodParamTypes(paramTypes);
			hmMethods.put(methodKey, methodEntity);
		}

		// 调用方法，根据字段名在hsMethods中查找对应的set方法
		Object objResult = ParseObjectFromResultSet(rsResult, classEntity, hmMethods);

		// 以数组方式返回
		return objResult;
	}

	/**
	 * 从Resultset中解析出单行记录对象，存储在实体对象中
	 */
	public static Object ParseObjectFromResultSet(java.util.Map map, Class classEntity,
			java.util.HashMap hsMethods) throws Exception {
		Object objEntity = classEntity.newInstance();
		Method method = null;

		String strColumnName =null;
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			strColumnName = (String) iterator.next();
			logger.debug(strColumnName);
			// 获取字段值
			Object objColumnValue = map.get(strColumnName);

			// HashMap中的方法名key值
			String strMethodKey = null;

			// 获取set方法名
			if (strColumnName != null) {
				
				strMethodKey = String.valueOf("SET"+ strColumnName.replaceAll("_", "").toUpperCase());
			}
			// 值和方法都不为空,这里方法名不为空即可,值可以为空的
			if (strMethodKey != null) {
				// 判断字段的类型,方法名，参数类型
				try {
					MethodEntity methodEntity = (MethodEntity) hsMethods.get(strMethodKey);
					String methodName = methodEntity.getMethodName();
					Class[] paramTypes = methodEntity.getMethodParamTypes();
					method = classEntity.getMethod(methodName, paramTypes);
					// 如果重载方法数 >
					// 1，则判断是否有java.lang.IllegalArgumentException异常，循环处理
					try {
						// 设置参数,实体对象，实体对象方法参数
						method.invoke(objEntity,new Object[] {changeType(objColumnValue,paramTypes[0])});
					} catch (java.lang.IllegalArgumentException e) {
						e.printStackTrace();
						// 处理重载方法
					}
				} catch (NoSuchMethodException e) {
					throw new NoSuchMethodException();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return objEntity;
	}
	
	public static Object changeType(Object obj,Class clazz) throws ParseException{
		if(obj==null){return null;} 
		logger.debug(obj.getClass().getName()+":"+obj+":"+clazz.getName());
		if(String.class==clazz){
			return obj.toString();
		}else if(Long.class==clazz){
			return Long.parseLong(obj.toString());
		}else if(Integer.class==clazz){
			return Integer.parseInt(obj.toString());
		}else if(Boolean.class==clazz){
			return Boolean.parseBoolean(obj.toString());
		}else if(Double.class==clazz){
			return Double.parseDouble(obj.toString());
		}else if(Short.class==clazz){
			return Short.parseShort(obj.toString());
		}else if(Float.class==clazz){
			return Float.parseFloat(obj.toString());
		}else if(Byte.class==clazz){
			return (Byte)obj;
		}else if(java.util.Date.class==clazz){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(obj.toString());
		}else{
			logger.debug("<<<<<<<<<<<none>>>>>>>>>>");
			return obj;
		}
		
	}
	
	public static void main(String[] args) {
		Object i=0;
		
		DecimalFormat df = new DecimalFormat("#.#");
		System.out.println(new BigDecimal("4.48416e-44").stripTrailingZeros().toPlainString());
		
		Long x = (Long)i;
		System.out.println(x);
	}
}