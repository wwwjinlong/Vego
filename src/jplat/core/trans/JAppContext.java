package jplat.core.trans;

import java.io.InputStream;
import java.io.OutputStream;

import jplat.core.session.JSession;
import jplat.error.exception.JSystemException;
import jplat.error.exception.JTransException;

/**
 * 一次服务请求的上下文.
 * 比如App发起一次请求,该接口应该暴露给业务模块使用.
 * @author zhangcq
 * @date Nov 22, 2016
 * @comment
 */
public interface JAppContext
{
	/**
	 * 创建会话,会话是会话,会话只是一个保存状态的地方.
	 * 会话的数据代表是会话令牌(即一个字符串,即ttnk2中的字符.),
	 * 会话ID只是状态的标识，可以放到HTTP-header头中.
	 * 
	 * 身份认证是另外的认证令牌，可以放到请求头中.
	 * 当需要客户信息的时候发到请求头中.
	 * 为了以后缓存迁移，先把会话令牌返回给客户端，让客户端先有
	 * 处理令牌的能力.
	 * @author zhangcq
	 * @date Dec 22, 2016
	 * @comment 
	 * @param create 是否强制建一个会话. true--创建,即使已经有会话了也会重新创建一个新会话.
	 * @return
	 * @throws JSystemException 
	 */
	public JSession createSession( boolean create ) throws JSystemException;
	
	/**
	 * 设置失败信息.
	 * @author zhangcq
	 * @date Dec 14, 2016
	 * @comment 
	 * @param errcode
	 * @param errmsg
	 * @return
	 */
	public JAppContext failTransException( String errcode, String errmsg ) throws JTransException;
	
	
	/**
	 * 设置失败信息.
	 * @author zhangcq
	 * @date Dec 14, 2016
	 * @comment 
	 * @param errcode
	 * @param errmsg
	 * @return
	 */
	public JAppContext failSystemException( String errcode, String errmsg ) throws JSystemException;
	
	/**
	 * 获取连接想基本信息.
	 */
	public JAppConnectInfo getConnInfo();
	
	/**
	 * 获取原始输入流.
	 * Oct 31, 20173:27:44 PM
	 * getInputStream
	 * @return
	 */
	public InputStream getInputStream() throws JSystemException;
	
	/**
	 * 获取原始输出流.
	 * Oct 31, 20173:27:57 PM
	 * getOutputStream
	 * @return
	 */
	public OutputStream getOutputStream() throws JSystemException;
	
	/**
	 * 不会强制创建,已有则返回，无则抛出会话超时的异常.所以应该是按需调用.
	 * @author zhangcq
	 * @date Dec 22, 2016
	 * @comment 
	 * @return 该接口实现必须返回会话，否则应抛出会话超时的异常.
	 * @throws JSystemException
	 */
	public JSession getSession() throws JSystemException;
	
	/**
	 * 获取客户信息.
	 * Oct 31, 20173:57:45 PM
	 * getUserInfo
	 * @return
	 */
	public JIUserInfo getUserInfo() throws JSystemException;
	
	/**
	 * 获取用户唯一标识.用于日志追踪等场景.
	 */
	public String getUserMark();
	
	/**
	 * 设置用户信息.
	 * Nov 14, 201711:58:49 AM
	 * setUserInfo
	 * @param userInfo
	 */
	public void updateUserInfo( JIUserInfo userInfo ) throws JSystemException;
	
	/**
	 * 获取报文请求头.
	 */
	public JClientReqHeader getReqHead();
	
	/**
	 * 获取请求体.
	 * Nov 14, 201711:56:30 AM
	 * getReqBody
	 * @return
	 */
	public Object getReqModel();
	
	/**
	 * 获取响应码.
	 */
	public String getRetCode();
	
	/**
	 * 获取响应消息.
	 * Nov 14, 201711:26:10 AM
	 * getRetMsg
	 * @return
	 */
	public String getRetMsg();
	
	/**
	 * 设置响应消息.
	 * Nov 14, 201711:26:10 AM
	 * getRetMsg
	 * @return
	 */
	public JAppContext setRetInfo( String retCode, String retMsg );
	
	
}
