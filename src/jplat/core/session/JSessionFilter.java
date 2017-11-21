package jplat.core.session;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.core.trans.JAppConnectInfo;
import jplat.error.exception.JTransException;
import jplat.tools.config.JAppConfig;
import jplat.tools.string.StringUtil;
import jplat.tools.trace.JAppLogUtils;
import jplat.tools.trace.JTimeCounter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;

public class JSessionFilter implements Filter
{
	private Logger logger = LogManager.getLogger(this.getClass());
	
	private static String QT_FLAG = "quickTrace";
	
	private Pattern pattern = null;
	
	@Override
	public void init(FilterConfig config) throws ServletException
	{
		// TODO Auto-generated method stub
		String websession = config.getInitParameter("websession");
		pattern = Pattern.compile(websession);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain ) throws IOException, ServletException
	{
		JTimeCounter counter = new JTimeCounter();
		
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
		
		//截取url
		String myUrl = cutUrl(req.getRequestURI());
		
		//显示头.
		JAppLogUtils.showHeadValues(req);
		
		//匹配网页会话检测. 此处可以优化为捕获异常.
		JSession jsession = JSessionUtils.checkSessionAvail(req);
		if ( checkWebSession(req.getRequestURI()) && jsession == null )
		{
			request.getRequestDispatcher("/view/jsp/base/timeout.jsp").forward(request, response);
			return;
		}
		
		String sessionId = "";
		String userMark = "";
		if ( jsession != null )
		{
			sessionId = jsession.getSessionID();
			userMark = StringUtil.getDefString(jsession.getValue(KSession.kUSER_MARK),"");
		}
		
		//请求日志会话追踪.
		String transMark = JTraceLogUtils.getLogMark();
		if ( StringUtil.isNotEmpty(sessionId) )
		{
			transMark = sessionId+"."+transMark;
		}
		
		String  quickTrace = req.getHeader("quickTrace");
		logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_RECV_APP,
				transMark, JTraceLogUtils.buildUserData(myUrl,userMark,StringUtil.getIpAddr(req), ""+req.getRemotePort() )));
		
		ThreadContext.put(QT_FLAG,String.format("mb.%s@%s",userMark,(quickTrace==null?"Na":quickTrace)));
		
		String exmark = "NX";
		try
		{
			filterChain.doFilter(request, response);
		}
		catch( Exception e )
		{
			exmark = "EX";
			logger.error("FATAL_ERR::"+JTraceLogUtils.getExceptionFullLog(e, JAppConfig.LOG_TRACE_CNT, true));
			
			writeResponse((HttpServletResponse) response);
			return;
//			throw e;
		}
		finally
		{
			HttpServletResponse rsp = (HttpServletResponse)response;
			String rspCode = rsp.getHeader(JAppConnectInfo.H_RET_HEAD);
			
			logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_RECV_APP,
								transMark, JTraceLogUtils.buildUserData(myUrl,userMark,rspCode,""+counter.endTick(),exmark) ));
			
			ThreadContext.remove(QT_FLAG);
		}
	}
	
	private String cutUrl( String url )
	{
		String[] urlParts = url.split("/");
		if ( urlParts.length > 2 )
		{
			return urlParts[urlParts.length-2]+"/"+urlParts[urlParts.length-1];
		}
		
		return url;
	}
	
	private boolean checkWebSession( String URI )
	{
		Matcher m = pattern.matcher(URI);
		return m.find();
	}
	
	private boolean writeResponse( HttpServletResponse response )
	{
		String exMsg = JAppLogUtils.buildErrMessage( new JTransException("9901","系统错误"), response);
		
		byte[] exDatas;
		try {
			exDatas = exMsg.getBytes(JAppConfig.PACK_CHARSET);
			response.setContentType("application/json");
			response.setContentLength(exDatas.length);
			response.getOutputStream().write(exDatas);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
}
