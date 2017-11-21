package jplat.tools.coder.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import jplat.tools.string.StringUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JNXmlParser {
	
	private static Logger logger = LogManager.getLogger(JNXmlParser.class);
	
	/**
	 * 将xml转换为对象.
	 * 
	 * @author zhangcq
	 * @date Aug 30, 2016
	 * @comment
	 * @param xml
	 * @param tclass
	 * @return
	 */
	public static Object xml2Obj(String xml, Class<?>... tclass) {
		
		logger.info("xml2Obj >>>>>>"+tclass.hashCode());
		
		JAXBContext jaxbContext;
		try {
//			jaxbContext = JAXBContext.newInstance(tclass);
			jaxbContext = JAxbCache.findContext(tclass[0].getCanonicalName(), tclass[0]);		//缓存之后就不支持数组了.
//			logger.info("xml2Obj.newInstance done >>>>>>"+tclass.hashCode());
			
			Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();
//			logger.info("xml2Obj.createUnmarshaller done >>>>>>"+tclass.hashCode());

			// output pretty printed
			// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
			// true);
			// jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			// jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			ByteArrayInputStream inputstream = new ByteArrayInputStream(
					xml.getBytes("utf-8"));

			Object retObj = jaxbMarshaller.unmarshal(inputstream);
			logger.info("xml2Obj.unmarshal done <<<<<"+tclass.hashCode());
			
			return retObj;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 用于转换xml.
	 * 
	 * @author zhangcq
	 * @date Nov 15, 2016
	 * @comment
	 * @param obj
	 * @return
	 */
	public static <T> String obj2Xml(T obj) {
		return obj2Xml(obj, obj.getClass());
	}

	/**
	 * 将对象转换为xml.
	 * 
	 * @author zhangcq
	 * @date Aug 30, 2016
	 * @comment
	 * @param isformat
	 * @param obj
	 * @param clazz
	 * @return
	 */
	public static <T> String obj2Xml(T obj, Class<?>... clazz) {
		
		logger.info("obj2Xml >>>>>>"+clazz.hashCode());
		
		JAXBContext jaxbContext;
		try {
//			jaxbContext = JAXBContext.newInstance(clazz); // 该类可以缓存起来用于加速性能
			
			jaxbContext = JAxbCache.findContext(clazz[0].getCanonicalName(), clazz[0]);
			
//			logger.info("obj2Xml.newInstance done >>>>>>"+clazz.hashCode());
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			logger.info("obj2Xml.createMarshaller done >>>>>>"+clazz.hashCode());
			
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true); // 是否去掉xml的
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			
			jaxbMarshaller.marshal(obj, outstream);
//			logger.info("obj2Xml.marshal done >>>>>>"+clazz.hashCode());
			
			String retStr = outstream.toString("utf-8");
			logger.info("obj2Xml <<<<<"+clazz.hashCode());

			return retStr;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 获取标签之间的数
	 * 如果有重复嵌套标签，那么获取将会有问题。
	 * 
	 * @author zhangcq
	 * @date Aug 30, 2016
	 * @comment
	 * @param xml
	 * @param markS
	 * @param markE
	 * @param withMark
	 * @return
	 */
	public static PTXmlIndex parseDataBetweenMark(String xml, String markS,
			String markE, boolean withMark) {
		PTXmlIndex retInfo = new PTXmlIndex();
		if (StringUtil.isEmpty(xml)) {
			retInfo.error = true;
			return retInfo;
		}

		int start = xml.indexOf(markS);
		int end = xml.indexOf(markE, start + 1);

		if (start == -1 || end == -1) {
			retInfo.error = true;
			return retInfo;
		}

		retInfo.content = xml.substring(start + markS.length(), end);
		retInfo.index = end + markE.length() - 1;

		if (withMark) {
			StringBuilder sb = new StringBuilder(2000);
			sb.append(markS)
				.append(retInfo.content)
				.append(markE);
			
			retInfo.content = sb.toString();
		}

		return retInfo;
	}

	public static void main(String args[]) {
		testJAXB();
	}

	private static void testJAXB() {/*
									 * 
									 * MBook bk1 = new MBook("book1", 101 );
									 * MBook bk2 = new MBook("book2", 102 );
									 * MBook bk3 = new MBook("book3", 103 );
									 * 
									 * List<MBook> blist = new
									 * ArrayList<MBook>(); blist.add(bk1);
									 * blist.add(bk2); blist.add(bk3);
									 * 
									 * Student stu = new Student();
									 * stu.setBooks(blist); stu.setName("Jack");
									 * 
									 * String xml = obj2Xml(stu);
									 * System.out.println(xml);
									 * 
									 * stu =
									 * (Student)xml2Obj(xml,Student.class);
									 * System
									 * .out.println(ETools.toJsonString(stu));
									 * 
									 * //--------------测试分割�?----------------
									 * 
									 * BigBoat<Student> bb = new
									 * BigBoat<Student>(); bb.setLoaders(stu);
									 * bb.setName("DH_num1");
									 * 
									 * xml =
									 * obj2Xml(bb,BigBoat.class,Student.class);
									 * System.out.println("bigboat:"+xml);
									 * 
									 * bb =
									 * (BigBoat<Student>)xml2Obj(xml,bb.getClass
									 * (),Student.class);
									 * System.out.println("bigboat:"
									 * +bb.getLoaders
									 * ().getBooks().get(0).getTitle());
									 * 
									 * stu =
									 * (Student)xml2Obj(xml,Student.class);
									 * System
									 * .out.println("test___:"+ETools.toJsonString
									 * (stu));
									 */
	}
}
