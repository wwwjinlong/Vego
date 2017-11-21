package jplat.core.net.webservice;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * webservice客户端.
 * @author zhangcq
 * @date Nov 22, 2016
 * @comment
 */
public class JWebServieClient
{
	private static Logger logger = LogManager.getLogger(JWebServieClient.class);
	
	/**
	 * 发送请求xml.
	 * @author zhangcq
	 * @date Nov 22, 2016
	 * @comment 
	 * @param endpoint
	 * @param operation
	 * @param reqXml
	 * @return
	 * @throws RemoteException
	 * @throws ServiceException
	 * @throws MalformedURLException
	 */
	public static String sendMessage( String endpoint, String operation, String reqXml ) 
										throws RemoteException, ServiceException, MalformedURLException
	{

		logger.info("__ICOP.URL="+endpoint);
		logger.info("__SEND_REQ_MSG__请求报文1>>>>>"+reqXml);
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName("request");
		
		//超时暂时定义10分钟  以前3分钟 
//		call.setProperty(Call.CONNECTION_TIMEOUT_PROPERTY, 15*1000); //鸡汤要改的.
		call.setTimeout(60000);
		call.addParameter("xml", XMLType.XSD_STRING, ParameterMode.IN );
		call.setReturnType(XMLType.XSD_STRING);
		String ret = (String) call.invoke(new Object[] { reqXml });
		
		logger.info("__RECV_RSP_MSG__返回报文1<<<<"+ret);
		
		return ret;
	}
}
