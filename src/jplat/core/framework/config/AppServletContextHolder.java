package jplat.core.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 该接口实现用于保存DispatcherServlet的上下文.
 * @author zhangcq
 *
 */
@Component
public class AppServletContextHolder implements ApplicationContextAware
{
	Logger logger = LoggerFactory.getLogger(AppServletContextHolder.class);
	
	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		context = arg0;
		
		logger.info("__INIT:AppContextHolder:Servlet init succeed, context.hashcode="+context.hashCode());
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		AppServletContextHolder.context = context;
	}
}
