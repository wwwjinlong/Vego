package jplat.core.trans.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.base.app.parser.IAppPacketConvertor;
import jplat.base.constant.KPlatResponseCode;
import jplat.core.session.JSession;
import jplat.core.session.JSessionFactory;
import jplat.core.trans.JIUserInfo;
import jplat.core.trans.JAppConnectInfo;
import jplat.core.trans.JAppContext;
import jplat.core.trans.JClientReqHeader;
import jplat.error.exception.JSystemException;
import jplat.error.exception.JTransException;
import jplat.tools.coder.JsonCoder;
import jplat.tools.config.JAppConfig;
import jplat.tools.data.validate.JBeanValidateUtils;

import com.google.gson.JsonElement;

/**
 * 一次服务请求的上下文.
 * 比如App发起一次请求.
 * 但是一次请求可以对应多个ICOP的请求JTransContext
 * 此类应该为接口,以后再优化吧.
 * @author zhangcq
 * @date Nov 22, 2016
 * @comment
 */
public class JServletAppContext extends JBaseAppContext
{
	//实现类才会有这两个对象s
	//其实对应从层来说，它连用的什么协议都不应该知道.
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	//数据解析器.
	private IAppPacketConvertor appParser;
	
	@Override
	public JSession createSession( boolean create ) throws JSystemException
	{
		if ( session != null )
		{
			return session;
		}
		
		//getRequest只有在本context才可见.
		session =  new JSessionFactory().setSessionId(getConnInfo().getSessTkn()).setHttpRequest(getRequest()).createSession(create);
		
		return session;
	}

	/**
	 * 不会强制创建,已有则返回，无则抛出会话超时的异常.
	 * @author zhangcq
	 * @date Dec 22, 2016
	 * @comment 
	 * @return
	 * @throws JSystemException
	 */
	@Override
	public JSession getSession() throws JSystemException
	{
		return createSession(false);
	}
	
	/**
	 * 配置请求参数.
	 * @author zhangcq
	 * @date Dec 14, 2016
	 * @comment 
	 * @param request
	 * @param response
	 */
	public void config( HttpServletRequest request, HttpServletResponse response )
	{
		this.request = request;
		this.response = response;
	}
	
	/******** getter-setters *******/
	public String getRetCode() {
		return retCode;
	}

	public JAppContext setRetCode(String retCode) {
		this.retCode = retCode;
		
		return this;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public JAppContext setRetMsg(String retMsg) {
		this.retMsg = retMsg;
		
		return this;
	}
	
	public Object getReqBody() {
		return reqBody;
	}

	public void setReqBody(Object reqBody) {
		this.reqBody = reqBody;
	}

	public Object getRspBody() {
		return rspBody;
	}

	public void setRspBody(Object rspBody) {
		this.rspBody = rspBody;
	}

	public JClientReqHeader getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(JClientReqHeader reqHeader) {
		this.reqHeader = reqHeader;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public JAppConnectInfo getConnInfo() {
		return connInfo;
	}

	public void setConnInfo(JAppConnectInfo connInfo) {
		this.connInfo = connInfo;
	}

	@Override
	public InputStream getInputStream() throws JSystemException {
		// TODO Auto-generated method stub
			try {
				return request.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new JSystemException(e,KPlatResponseCode.CD_IO_ERR,KPlatResponseCode.MSG_IO_ERR);
			}
	}

	@Override
	public OutputStream getOutputStream() throws JSystemException {
		// TODO Auto-generated method stub
		try {
			return response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSystemException(e,KPlatResponseCode.CD_IO_ERR,KPlatResponseCode.MSG_IO_ERR);
		}
	}

	@Override
	public String getUserMark()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
