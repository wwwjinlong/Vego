package jplat.core.trans;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.base.app.parser.IAppPacketConvertor;
import jplat.base.app.parser.impl.JAppJsonConvertor;
import jplat.base.constant.KPlatResponseCode;
import jplat.core.trans.impl.JServletAppContext;
import jplat.error.exception.JSystemException;
import jplat.tools.config.JLogConfig;
import jplat.tools.string.JStringUtil;
import jplat.tools.string.StringUtil;
import jplat.tools.trace.JAppLogUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import z.log.tracelog.XLog;

/**
 * App请求服务类的应用上下文工厂实现，
 * 上下文是指一次App请求的上下文,携带了本次请求的全部信息.
 * @author zhangcq
 * @date Dec 7, 2016
 * @comment
 */

public class JAppContextFactory
{
	//解析app传送过来的数据.
	private IAppPacketConvertor appParser = new JAppJsonConvertor();
	
	private Logger logger = LogManager.getLogger(JAppContextFactory.class);

	/***
	 * 建立请求应用上下文.
	 * Oct 31, 20173:46:15 PM
	 * buildAppContext
	 * @param request
	 * @param response
	 * @param parseData
	 * @param needUser
	 * @return
	 * @throws JSystemException
	 */
	public JAppContext buildAppContext( HttpServletRequest request,
			HttpServletResponse response, boolean parseData ) throws JSystemException
	{
		JServletAppContext appCtx = new JServletAppContext();
		appCtx.config(request, response);

		//解析请求头.
		if( !parseHttpHeaderInfo( appCtx ) )
		{
			logger.error(XLog.CONN_MARK+"解析请求头出错.");
			throw new JSystemException(KPlatResponseCode.CD_INPUT_ERROR,KPlatResponseCode.MSG_INPUT_ERROR+",t=h");
		}
		
		//解析App传送过来的数据.
		//但是不解析二进制数据.
		if ( parseData )
		{
			JAppConnectInfo connInfo = appCtx.getConnInfo();
			
			//对加密数据进行密钥获取.
			if( JAppConnectInfo.V_ENC_AES.equals(connInfo.getEncType()) )
			{
				connInfo.setEncKey(appCtx.getUserInfo().getEncryptKey());
			}
			
			appParser.doAppParse(appCtx);
		}

		return appCtx;
	}

	/**
	 * 此上下文建立只管理了会话.
	 * 用于纯GET方式,没有请求体和Cookie.
	 * @author zhangcq
	 * @date Jan 18, 2017
	 * @comment 
	 * @param request
	 * @param response
	 * @param ttkn2
	 * @return
	 * @throws JSystemException
	 */
	public JAppContext buildOnlyContext( HttpServletRequest request,
			HttpServletResponse response , String ttkn2 ) throws JSystemException
	{
		return null;
	}

	/**
	 * 解析通用报文头信息.
	 * @author zhangcq
	 * @date Nov 30, 2016
	 * @comment 
	 * @param ctx
	 * @return
	 */
	private boolean parseHttpHeaderInfo( JAppContext __appCtx )
	{
		JServletAppContext appCtx = (JServletAppContext)__appCtx;
		
		if( JLogConfig.canPrintNetInfo() )
		{
			logger.info("__HTTP_HEADERS:"+JAppLogUtils.getHeadValues((appCtx.getRequest())));
		}

		//解析请求头.
		Map<String,String> reqInfoMap = getReqInfoMap( appCtx );
		
		JAppConnectInfo reqInfoModel = new JAppConnectInfo();
		reqInfoModel.setSessTkn((String)reqInfoMap.get(JAppConnectInfo.H_HTOKEN_NAME));		//会话令牌
		reqInfoModel.setEncType((String)reqInfoMap.get(JAppConnectInfo.K_ENC_TYPE));		//加密类型.
		
		reqInfoModel.setReqChl((String)reqInfoMap.get(JAppConnectInfo.K_CHL_TYPE));
		reqInfoModel.setCliVersion((String)reqInfoMap.get(JAppConnectInfo.K_CLI_VERSION));
		reqInfoModel.setSignKey((String)reqInfoMap.get(JAppConnectInfo.K_DATA_SIGN));		//签名值.
		reqInfoModel.setCliIp(JStringUtil.getIpAddr(appCtx.getRequest()));
		reqInfoModel.setLocalIp(JStringUtil.getLocalIP());
		reqInfoModel.setContentType(appCtx.getRequest().getContentType());

		//设置返回请求信息.
		appCtx.setConnInfo(reqInfoModel);

		return true;
	}

	/**
	 * 获取会话令牌信息.
	 * 优先使用Cookie里面的令牌;
	 * 但是没有HTTP-Only的保护,令牌容易被盗.
	 * 目前暂时没有客户可以看大另外客户留言的场景，所以相对安全.
	 * @author zhangcq
	 * @date Nov 30, 2016
	 * @comment 
	 * @param ctx
	 * @return
	 */
	private Map<String,String> getReqInfoMap( JAppContext appCtx )
	{
		JServletAppContext ctx = (JServletAppContext)appCtx;
		
		Map<String,String> reqInfoMap = new HashMap<String,String>();
		
		//从infoq头中获取.
		String info = ctx.getRequest().getHeader(JAppConnectInfo.H_INFO_KEY);
		if ( StringUtil.isNotEmpty(info) )
		{
			String[] parts = info.split(";");
			for ( int i = 0; i < parts.length; ++i )
			{
				String[] pairs = parts[i].split("=");
				reqInfoMap.put(pairs[0].trim(), StringUtil.getDefString(pairs[1],"SET"));
			}
		}
		
		//获取会话.
		reqInfoMap.put(JAppConnectInfo.H_HTOKEN_NAME, ctx.getRequest().getHeader(JAppConnectInfo.H_HTOKEN_NAME));
		
		//从Cookie中获取:所以,如果值重复,最后是以Cookie中的为准.
		Cookie[] cookies = ctx.getRequest().getCookies();
		if ( cookies != null )
		{
			for ( int i = 0; i < cookies.length; ++i )
			{
				if( JLogConfig.canPrintNetInfo() )
				{
					logger.info("Cookies:"+cookies[i].getName()+"="+cookies[i].getValue());
				}
				
				reqInfoMap.put(cookies[i].getName(), cookies[i].getValue());
			}
		}
		
		return reqInfoMap;
	}

	public IAppPacketConvertor getAppParser() {
		return appParser;
	}

	public JAppContextFactory initAppParser(IAppPacketConvertor appParser) {
		this.appParser = appParser;
		return this;
	}
}
