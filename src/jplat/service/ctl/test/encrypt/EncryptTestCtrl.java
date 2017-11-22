package jplat.service.ctl.test.encrypt;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.core.trans.JAppBaseService;
import jplat.core.trans.JAppContext;
import jplat.core.trans.impl.JServletAppContext;
import jplat.error.exception.JSystemException;
import jplat.error.exception.JTransException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 加密测试类.
 * @author zhangcq
 * @date May 8, 2017
 * @comment
 * 
 * 1. session and aeskey : http://localhost:8080/Vego/test/session/fakeload.do?custno=U123&fakeToken=123456
 * 2. aescipher : EncryptTestUtils.testAES
 * 3. - http://localhost:8080/Vego/test/aes/check.do POST
 * 	  - infoq : ch=app;e=a
 *    - add cookie : ttkn2 = sessionID
 */

@Controller
public class EncryptTestCtrl extends JAppBaseService
{
	private Logger logger = LogManager.getLogger(EncryptTestCtrl.class);
	
	@RequestMapping("/test/aes/check.do")
	public void testAES( HttpServletRequest request,
							HttpServletResponse response ) throws JTransException, JSystemException
	{
		Map<String,Object> retMap = new HashMap<String,Object>();
		
		JAppContext contxt = serviceFactory.buildAppContext(request,response,true);
		
		String encType = contxt.getConnInfo().getEncType();
		if ( !"a".equals(encType) )
		{
			contxt.failTransException("0001.", "加密方式不对.enctype="+encType);
		}
		
		JServletAppContext appCtx = (JServletAppContext)contxt;
		Map<String,Object> reqMap = appCtx.obtainReqModel(HashMap.class,true);
		
		retMap.put("message", "你这个解出来了啊,恭喜AES加密测试成功了." );
		retMap.put("request", reqMap);
		retMap.put("enctype",encType);
		
		appCtx.setRspBody(retMap);
		appParser.doAppPack(appCtx);
	}
		
	
	@RequestMapping("/test/rsa/check.do")
	public void testRSA( HttpServletRequest request,
							HttpServletResponse response ) throws JTransException, JSystemException
	{/*
		Map<String,Object> retMap = new HashMap<String,Object>();
		
		JAppContext appCtx = serviceFactory.buildAppContextNoUser(request,response);
		
		String encType = appCtx.getConnInfo().getEncType();
		if ( !"r".equals(encType) )
		{
			appCtx.failTransException("0001.", "加密方式不对.enctype="+encType);
		}
		
		Map<String,Object> reqMap = appCtx.obtainReqModel(HashMap.class);
		
		retMap.put("message", "你这个也解出来了啊,恭喜RSA加密测试成功了." );
		retMap.put("request", reqMap);
		
		retMap.put("enctype",encType);
		
		appCtx.buildAppResponse(retMap);
	*/}
}
