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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 加密测试类.
 * @author zhangcq
 * @date May 8, 2017
 * @comment
 * 
 * 1. fake session and aeskey : http://localhost:8080/Vego/test/session/fakeload.do?custno=U123&fakeToken=123456
 * 2. aes-cipher : EncryptTestUtils.testAES
 * 3. - http://localhost:8080/Vego/test/aes/check.do POST
 * 	  - add header : infoq : ch=app;e=a
 *    - add cookie : ttkn2 = sessionID
 */

@Controller
public class EncryptTestCtrl extends JAppBaseService
{
	private Logger logger = LoggerFactory.getLogger(EncryptTestCtrl.class);
	
	@RequestMapping("/test/{etype}/check.do")
	public void testAES( HttpServletRequest request, HttpServletResponse response,
									@PathVariable String etype ) throws JTransException, JSystemException
	{
		JAppContext contxt = serviceFactory.buildAppContext(request,response,true);
		
		String encType = contxt.getConnInfo().getEncType();
		if ( !"a".equals(encType) && !"r".equals(encType) )
		{
			contxt.failTransException("0001.", "加密方式不对.enctype="+encType);
		}
		
		String encName = "AES";
		if ("r".equals(encType))
		{
			encName = "RSA";
		}
		
		JServletAppContext appCtx = (JServletAppContext)contxt;
		Map<String,Object> reqMap = appCtx.obtainReqModel(HashMap.class,true);
		
		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put("message", String.format( "你这个解出来了啊,恭喜%s加密测试成功了.", encName.toUpperCase()) );
		retMap.put("request", reqMap);
		retMap.put("enctype", encName );
		
		appCtx.setRspBody(retMap);
		
		//组包.
		appParser.doAppPack(appCtx);
	}
}
