package jplat.service.ctl.test.health;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.core.session.redis.JRedisSession;
import jplat.error.exception.JSystemException;
import jplat.tools.coder.JsonCoder;
import jplat.tools.config.JAppConfig;
import jplat.tools.config.JConfigManager;
import jplat.tools.file.XFileTools;
import jplat.tools.string.JRandomUtil;
import jplat.tools.string.StringUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 该类仅用于需要会话的测试场景，比如性能测试等.
 * @author zhangcq
 * @date Jun 9, 2017
 * @comment
 */
@Controller
public class FakeSessionCtrl
{
	private Logger logger = LogManager.getLogger(FakeSessionCtrl.class);
	
	/**
	 * 便于测试 构造会话.
	 * @author zhangcq
	 * @date May 8, 2017
	 * @comment 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/test/session/fakeload.do")
	@ResponseBody
	public Map<String,Object> loadFakeSession( HttpServletRequest request, HttpServletResponse response )
	{/*
		if ( !JAppConfig.IS_TEST )
		{
			return null;
		}
		
		Map<String,Object> retMap = new HashMap<String,Object>();
		
		List<Map<String,String>> sessList = new ArrayList<Map<String,String>>();
		
		String type = request.getParameter("type");
		if ( "custno".equals(type) )
		{
		}
		
		//获取客户号
		String custP = request.getParameter("custno");
		if ( StringUtil.isEmpty(custP) )
		{
			return setReturnMap(retMap,"0000","客户号custno为空.");
		}
		
		//令牌校验.
		String fakeToken = request.getParameter("fakeToken");
		if ( StringUtil.isEmpty(fakeToken) 
				|| !fakeToken.equals(JConfigManager.getInstance().getSystemConfig().getString("test.fakes")) )
		{
			return setReturnMap(retMap,"0001","客户号custNo为空.");
		}

		//客户号
		String custNos[] = custP.split(",");
		for ( int i = 0; i < custNos.length; ++i )
		{
			String custNo = custNos[i];
			
			try
			{
				CustInfoModel custModel = new CustInfoModel();
				custModel.setCustNo(custNo);
				custModel.setAESKey(JRandomUtil.getRandomSequence(16));
				
				String tmpSessDir = JAppConfig.getTempDir("fakesession");
				String sessFile = tmpSessDir+custNos[i]+".fs";
				
				logger.info("fake-session file-path:"+sessFile);
				if ( new File(sessFile).exists() )
				{
					logger.info("loading fake-session file-path:"+sessFile);
					String custStr = XFileTools.readFile(sessFile,"utf-8");
					custModel = JsonCoder.fromJsonString(custStr, CustInfoModel.class);
				}
				
				//构造会话.
				JRedisSession session = new JRedisSession();
				session.setUserInfo(custModel);
				
				//保存会话ID.
				Map<String,String> itemMap = new HashMap<String,String>();
//				itemMap.put("aesKey", custModel.obtEncInfo() );
				itemMap.put("sessId", session.getSessionID() );
				
				sessList.add(itemMap);
				
			} catch (JSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		retMap.put("retcode", "0000" );
		retMap.put("sessions", sessList);
		return retMap;
	*/
		return null;
		}
	
	/****
	 * 
	 * @author zhangcq
	 * @date May 8, 2017
	 * @comment 
	 * @param errCode
	 * @param errMsg
	 * @return
	 */
	private static Map<String,Object> setReturnMap( Map<String,Object> retMap, String errCode, String errMsg )
	{
		retMap.put("retcode", errCode);
		retMap.put("retmsg", errMsg);
		
		return retMap;
	}
}
