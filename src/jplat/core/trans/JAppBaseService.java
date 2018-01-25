package jplat.core.trans;

import javax.servlet.http.HttpServletResponse;

import jplat.base.app.parser.IAppPacketConvertor;
import jplat.base.app.parser.impl.JAppJsonConvertor;
import jplat.error.exception.JSystemException;
import jplat.error.exception.JTransException;
import jplat.tools.config.JAppConfig;
import jplat.tools.trace.JAppLogUtils;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import z.log.tracelog.JTraceLogUtils;

public abstract class JAppBaseService
{
	private Logger logger = LoggerFactory.getLogger(JAppBaseService.class);
	
	//数据安全检查
//	protected IDataChecker dataChecker = new JDataChecker();

	//数据解析器.
	protected IAppPacketConvertor appParser = new JAppJsonConvertor();
	
	//上下文工厂.
	protected JAppContextFactory serviceFactory = new JAppContextFactory().initAppParser(appParser);
	
	@ExceptionHandler(JTransException.class)
	@ResponseBody
	public String execJTransExceptionHandler( JTransException transException, HttpServletResponse response )
	{
		logger.error(JTraceLogUtils.getExceptionFullLog(transException, JAppConfig.LOG_TRACE_CNT, true));
		return JAppLogUtils.buildErrMessage(transException,response );
	}
	
	@ExceptionHandler(JSystemException.class)
	@ResponseBody
	public String execJSystemExceptionHandler( JSystemException sysException, HttpServletResponse response )
	{
		logger.error(JTraceLogUtils.getExceptionFullLog(sysException, JAppConfig.LOG_TRACE_CNT, true));
		if ( sysException.sysEx != null )
		{
			logger.error(JTraceLogUtils.getExceptionFullLog(sysException.sysEx, JAppConfig.LOG_TRACE_CNT, true));
		}
		
		return JAppLogUtils.buildErrMessage(sysException,response);  
	}

}
