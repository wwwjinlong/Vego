package jplat.core.trans.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.base.constant.KPlatResponseCode;
import jplat.core.session.JSession;
import jplat.core.session.JSessionFactory;
import jplat.core.trans.JAppContext;
import jplat.error.exception.JSystemException;

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
	
	public JServletAppContext setSession(JSession sess)
	{
		this.session = sess;
		return this;
	}
	
	/**
	 * 配置请求参数.
	 * @author zhangcq
	 * @date Dec 14, 2016
	 * @comment 
	 * @param request
	 * @param response
	 */
	public JServletAppContext config( HttpServletRequest request, HttpServletResponse response )
	{
		this.request = request;
		this.response = response;
		
		return this;
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
	public JAppContext cloneContext(Object reqModel, Object rspModel) {
		// TODO Auto-generated method stub
		JServletAppContext cloneCtx = new JServletAppContext();
		cloneCtx.config(request, response);
		cloneCtx.setSession(session);
		cloneCtx.setUserInfo(userInfo);
		cloneCtx.setReqHeader(reqHeader);
		cloneCtx.setConnInfo(getConnInfo());
		
		cloneCtx.setRspBody(rspModel);
		cloneCtx.setReqBody(reqBody);
		
		return cloneCtx;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public JServletAppContext setRequest(HttpServletRequest request) {
		this.request = request;
		return this;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public JServletAppContext setResponse(HttpServletResponse response) {
		this.response = response;
		return this;
	}
}
