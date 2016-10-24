package com.ljzforum.platform.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;

public class XMLUtils {
	public static Object parserXml2Bean(String xml,Class<?> clz) throws Exception{
		JAXBContext jaxbContext = JAXBContext.newInstance(clz);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        InputSource source = new InputSource(new StringReader(xml));
        return jaxbUnmarshaller.unmarshal(source);
	}
}
