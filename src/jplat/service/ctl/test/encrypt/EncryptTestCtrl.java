package jplat.service.ctl.test.encrypt;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.core.trans.JAppBaseService;
import jplat.core.trans.JAppContext;
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
 */

@Controller
public class EncryptTestCtrl extends JAppBaseService
{
	private Logger logger = LogManager.getLogger(EncryptTestCtrl.class);
	
	@RequestMapping("/test/aes/check.do")
	public void testAES( HttpServletRequest request,
							HttpServletResponse response ) throws JTransException, JSystemException
	{/*
		Map<String,Object> retMap = new HashMap<String,Object>();
		
		JAppContext appCtx = serviceFactory.buildAppContext(request,response,true);
		
		String encType = appCtx.getConnInfo().getEncType();
		if ( !"a".equals(encType) )
		{
			appCtx.failTransException("0001.", "加密方式不对.enctype="+encType);
		}
		
		Map<String,Object> reqMap = appCtx.obtainReqModel(HashMap.class);
		
		retMap.put("message", "你这个解出来了啊,恭喜AES加密测试成功了." );
		retMap.put("request", reqMap);
		
		retMap.put("enctype",encType);
		
//		appCtx.buildAppResponse(retMap);
	*/}
	
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
