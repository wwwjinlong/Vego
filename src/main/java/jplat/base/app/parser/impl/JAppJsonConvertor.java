package jplat.base.app.parser.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jplat.base.app.parser.IAppPacketConvertor;
import jplat.base.constant.KDolphinq;
import jplat.base.constant.KPlatResponseCode;
import jplat.core.net.appdata.IAppDataConnector;
import jplat.core.net.appdata.IDataGuard;
import jplat.core.net.appdata.encrypt.JAppRADataGuard;
import jplat.core.net.appdata.servlet.JHttpServletConnector;
import jplat.core.session.JSession;
import jplat.core.trans.JAppConnectInfo;
import jplat.core.trans.JAppContext;
import jplat.core.trans.JClientReqHeader;
import jplat.core.trans.JClientRspHeader;
import jplat.core.trans.impl.JServletAppContext;
import jplat.error.exception.JSystemException;
import jplat.tools.coder.JsonCoder;
import jplat.tools.config.JAppConfig;
import jplat.tools.string.StringUtil;
import z.log.tracelog.JLog;

/**
 * 该类负责和App的解包和组包，以及加解密.
 * 该组包类根据协议不同需要进行不同的试下,组包中包括通讯头，报文头等，这些都和具体协议实现有关.
 * @author zhangcq
 * @date Dec 16, 2016
 * @comment
 */
public class JAppJsonConvertor implements IAppPacketConvertor
{
	private Logger logger = LoggerFactory.getLogger(JAppJsonConvertor.class);
	
	//请求读取器
	private IAppDataConnector dataConnector = new JHttpServletConnector();
	
	//数据加解密
	private IDataGuard guarder = new JAppRADataGuard();
	
	/**
	 * 初始化返回报文头.
	 * 其中包含了身份管理和会话管理.
	 * @author zhangcq
	 * @date Dec 23, 2016
	 * @comment 
	 * @param context
	 * @return
	 * @throws JSystemException
	 */
	private JClientRspHeader initRspHeader( JAppContext __appCtx  ) throws JSystemException
	{
		JServletAppContext appCtx = (JServletAppContext)__appCtx;
		
		//组头.
		JClientRspHeader rspHeader = new JClientRspHeader( appCtx.getRetCode(), appCtx.getRetMsg() );
		
		//是新建会话则需要返回会话信息.
		if ( appCtx.isNewSession() )
		{
			//用户信息和会话的建立是绑定的.
			JSession session = appCtx.getSession();
			
			//http走的标准Cookie的JSESSIONID
			rspHeader.setTtkn2(session.getSessionID());		//会话ID.
			
			//返回sessionID
			HttpServletResponse response = appCtx.getResponse();
			response.setHeader(JAppConnectInfo.H_SET_HTOKEN_NAME, session.getSessionID());
			
			//兼容浏览器.
			Cookie sessCookie = new Cookie(JAppConnectInfo.H_HTOKEN_NAME,session.getSessionID());
			sessCookie.setPath("/");
			response.addCookie(sessCookie);
		}
		
		return rspHeader;
	}
			
	@Override
	public boolean doAppParse( JAppContext context ) throws JSystemException
	{
		// TODO Auto-generated method stub
		String json = "";
		try
		{
			//可能是密文.
			json = new String( guarder.dencryptData(context, dataConnector.readInputBytes(context)), JAppConfig.getConfigCache().APP_CHARSET );
			
			logger.info(new StringBuilder("__FROM_APP1:[").append(json).append("]").toString());
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSystemException(KPlatResponseCode.CD_INPUT_ERROR,KPlatResponseCode.MSG_INPUT_ERROR+",t=j");
		}
		
		//没有请求数据.
		if ( StringUtil.isEmpty(json) )
		{
			return true;
		}
		
		//解析请求数据.
		doJsonParse(context,json);
		
		return false;
	}
	
	/***
	 * 解析客户端报文.
	 * @author zhangcq
	 * @date Dec 15, 2016
	 * @comment 
	 * @param context
	 * @param json
	 * @return
	 * @throws JSystemException
	 */
	public boolean doJsonParse( JAppContext __appCtx, String json ) throws JSystemException
	{
		JServletAppContext appCtx = (JServletAppContext)__appCtx;
		
		//反序列化对象.
		JsonObject jo = new JsonParser().parse(json).getAsJsonObject();
		JsonElement reqHeader = jo.get(KDolphinq.H_REQ_HEAD);
		JsonElement reqBody = jo.get(KDolphinq.H_RSP_BODY);
		if ( reqHeader == null )
		{
			logger.error("{}无法获取从输入数据获得head",JLog.CONN_MARK);
			throw new JSystemException(KPlatResponseCode.CD_INPUT_ERROR,KPlatResponseCode.MSG_INPUT_ERROR+",t=hd");
		}

		//解析请求头.
		JClientReqHeader reqHead = JsonCoder.fromJson(reqHeader,JClientReqHeader.class);
		appCtx.setReqHeader(reqHead);
		appCtx.setReqBody(reqBody);
		
		return true;
	}

	@Override
	public boolean doAppPack( JAppContext context) throws JSystemException {
		// TODO Auto-generated method stub
		return doAppJsonPack( context );
	}
	
	/**
	 * 组包
	 * @author zhangcq
	 * @date Dec 15, 2016
	 * @comment 
	 * @param context
	 * @return
	 * @throws JSystemException
	 */
	private boolean doAppJsonPack( JAppContext __appCtx ) throws JSystemException
	{
		// TODO Auto-generated method stub
		JServletAppContext appCtx = (JServletAppContext)__appCtx;

		JClientRspHeader rspHeader = initRspHeader(appCtx);		//初始化报文头.

		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put(KDolphinq.H_RSP_HEAD, rspHeader);
		
		Object rspBody = appCtx.getRspBody();
		if ( appCtx.getRspBody() == null )
		{
			retMap.put(KDolphinq.H_RSP_BODY, new HashMap<String,String>());
		}
		retMap.put(KDolphinq.H_RSP_BODY, rspBody );

		HttpServletResponse response = appCtx.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader(JAppConnectInfo.H_RET_HEAD, JAppConnectInfo.V_HRETCODE_SUCC);

		String toApp = JsonCoder.toJsonString(retMap);
		logger.info("__TO_APP1:"+toApp);
		try
		{
			byte[] retData = guarder.encryptData( appCtx, toApp.getBytes(JAppConfig.getConfigCache().APP_CHARSET) );
			dataConnector.writeOutputBytes(appCtx, retData);
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
