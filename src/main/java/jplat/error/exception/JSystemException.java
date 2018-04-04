package jplat.error.exception;

/**
 * 框架平台级别错误.比如反射交易方法不存在，加解密出错等.
 * @author zhangcq
 * @date Nov 28, 2016
 * @comment
 */
public class JSystemException extends JBaseException
{
	public Exception sysEx;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7963771513846767281L;
	
	public JSystemException(String retCode, String retMsg){
		super(retCode, retMsg);
		// TODO Auto-generated constructor stub
	}
	
	public JSystemException( Exception ex, String retCode, String retMsg){
		super(retCode, retMsg);
		sysEx = ex;
		// TODO Auto-generated constructor stub
	}
}
