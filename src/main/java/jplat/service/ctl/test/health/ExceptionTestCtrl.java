package jplat.service.ctl.test.health;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jplat.core.trans.JAppBaseService;
import jplat.core.trans.JAppContext;
import jplat.error.exception.JSystemException;

@Controller
public class ExceptionTestCtrl extends JAppBaseService
{
	@RequestMapping("/test/exception.do")
	public void testException( HttpServletRequest request, HttpServletResponse response ) throws JSystemException
	{
		JAppContext appCtx = serviceFactory.buildAppContext(request, response, false);
		appCtx.failSystemException("9999", "异常测试");
	}
}
