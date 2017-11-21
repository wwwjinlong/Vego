package jplat.core.trans.impl;

import jplat.base.constant.KPlatResponseCode;
import jplat.core.session.JSession;
import jplat.core.trans.JAppConnectInfo;
import jplat.core.trans.JAppContext;
import jplat.core.trans.JClientReqHeader;
import jplat.core.trans.JIUserInfo;
import jplat.error.exception.JSystemException;
import jplat.error.exception.JTransException;
import jplat.tools.coder.JsonCoder;
import jplat.tools.config.JAppConfig;
import jplat.tools.data.validate.JBeanValidateUtils;

import com.google.gson.JsonElement;

/**
 * 一次服务请求的上下文.
 * 比如App发起一次请求.
 * 该类在基础接口中添加了一些平台默认实现和平台方法,而这些方法不应该暴露给用户.
 * @author zhangcq
 * @date Nov 22, 2016
 * @comment
 */
public abstract class JBaseAppContext implements JAppContext
{
	//相应信息和错误码.
	protected String retCode;
	protected String retMsg;
	
	//请求头. 如果想要平台利用head内容做些事情，
	//那么就需要定义一个准确的类，不然如果在其他地方改变了这个类
	//那么平台就会出问题.
	//可以继承，然后自定义.
	protected JClientReqHeader reqHeader;
	
	//交易请求体
	protected Object reqBody;
	
	//请求响应体
	protected Object rspBody;
	
	//客户信息.
	protected JIUserInfo userInfo;
	
	//请求连接基本信息
	protected JAppConnectInfo connInfo;
	
	//会话信息.
	protected JSession session;
	
	//报文状态
	public JBaseAppContext()
	{
		retCode = KPlatResponseCode.CD_SUCCESS;
		retMsg = KPlatResponseCode.MSG_SUCCESS;
	}
	
	/**
	 * 设置失败信息.
	 * @author zhangcq
	 * @date Dec 14, 2016
	 * @comment 
	 * @param errcode
	 * @param errmsg
	 * @return
	 */
	@Override
	public JAppContext failTransException( String errcode, String errmsg ) throws JTransException
	{
		retCode = errcode;
		retMsg = errmsg;
		throw new JTransException(errcode,errmsg);
	}
	
	/**
	 * 设置失败信息.
	 * @author zhangcq
	 * @date Dec 14, 2016
	 * @comment 
	 * @param errcode
	 * @param errmsg
	 * @return
	 */
	@Override
	public JAppContext failSystemException( String errcode, String errmsg ) throws JSystemException
	{
		retCode = errcode;
		retMsg = errmsg;
		throw new JSystemException(errcode,errmsg);
	}
	
	/**
	 * 获取请求对象，并校验数据是否为空.
	 * 出于性能需要改校验只在测试阶段进行.
	 * 该接口只能调用一次.
	 * @author zhangcq
	 * @date Jan 20, 2017
	 * @comment 
	 * @param clazz
	 * @param validate 是否校验数据.
	 * @return
	 * @throws JSystemException
	 */
	public <T> T obtainReqModel( Class<T> clazz, boolean validate ) throws JSystemException
	{
		reqBody = JsonCoder.fromJson((JsonElement)reqBody, clazz);
		
		if ( JAppConfig.IS_TEST && validate )
		{
			JBeanValidateUtils.validate(reqBody);
		}
		
		return (T)reqBody;
	}
	
	public boolean isNewSession()
	{
		if ( session == null )
		{
			return false;
		}
		
		return session.isNew();
	}
	
	@Override
	public String getRetCode() {
		return retCode;
	}

	@Override
	public String getRetMsg() {
		return retMsg;
	}

	@Override
	public Object getReqModel() {
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

	@Override
	public JClientReqHeader getReqHead() {
		return reqHeader;
	}

	public void setReqHeader(JClientReqHeader reqHeader) {
		this.reqHeader = reqHeader;
	}

	@Override
	public JAppConnectInfo getConnInfo() {
		return connInfo;
	}

	public void setConnInfo(JAppConnectInfo connInfo) {
		this.connInfo = connInfo;
	}

	@Override
	public JIUserInfo getUserInfo() throws JSystemException
	{
		//客户信息需要缓存.
		//因为如果是类似从redis里面获取的话调用成本还是比较高的.
		if ( userInfo != null )
		{
			return userInfo;
		}
		
		if ( session == null )
		{
			session = getSession();
		}
		
		userInfo = session.getUserInfo();
		
		return userInfo;
	}

	@Override
	public void updateUserInfo( JIUserInfo __userInfo ) throws JSystemException
	{
		if ( session == null )
		{
			session = getSession();
		}
		
		this.userInfo = __userInfo;
		
		session.setUserInfo(__userInfo);
	}

	@Override
	public JAppContext setRetInfo( String retCode, String retMsg ) {
		// TODO Auto-generated method stub
		this.retCode = retCode;
		this.retMsg = retMsg;
		
		return this;
	}
}
