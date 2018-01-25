package jplat.tools.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jplat.base.app.parser.impl.JAppJsonConvertor;

public class MyTest {
	
	private static Logger logger = LoggerFactory.getLogger(JAppJsonConvertor.class);
	
	public static void main( String args[] )
	{
		testLog();
	}
	
	public static void testLog()
	{
		logger.info("{} what behind me?", "place");
	}

}
