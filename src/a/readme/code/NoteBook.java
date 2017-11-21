package a.readme.code;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jplat.error.exception.JTransException;
import jplat.service.asyntask.JASynTaskExample;
import jplat.service.asyntask.JTaskExecutor;
import jplat.service.cache.JCrossCacheFactory;
import jplat.service.cache.JICrossCache;
import jplat.tools.coder.JsonCoder;
import jplat.tools.config.JAppConfig;
import jplat.tools.little.JDoubleUtils;
import jplat.tools.little.JListUtils;
import jplat.tools.string.JDateUtil;
import jplat.tools.string.JRandomUtil;
import jplat.tools.string.JStringUtil;
import z.log.tracelog.XLog;

/**
 * 此类仅作为备注样例.
 * @author zhangcq
 *
 */
public class NoteBook
{
	// 样例中 XLog 为测试日志打印,只会打印到控制台,不会打印到日志文件,实际开发中使用log4j的Logger打印日志.
	
	// 1. 数据库配置文件 MyBatisGenertor.xml
	
	// 1. 渠道代码工具类名: ICOPServiceMain
	// 2. API代码工具类名： AppServiceMain
	// 3. 数据库表代码工具关键字: JDTableXXX 
	// 4. API发布配置名: all_mdls.json
	// 5. 参数配置方式: 
	// 6. 自有工具类都以J开头,然后加上类型例如JString,JDouble,JList等..
	
	/**
	 * 常用配置和字符串，日期等工具.
	 * @throws JTransException 
	 */
	public static void showBaseToolsExample() throws JTransException
	{
		
		// **** 1. 【获取配置文件类方法.】  ****
		String appName = JAppConfig.getString("app.name", "default_value");	//该类已实现热重载，无需启动即可重新读取参数(properties中的参数).
		XLog.log("## app.name=%s", appName);
		
		XLog.log("\n-------------------------------------\n");
		
		// **** 2. 【获取临时文件夹. 需要自己建立文件夹.】 *****
		String tempDir = JAppConfig.getTempDir("img");						// 由于生产是集群模式，所以请不要在机器本地保存数据.因为请求是随机分发，不一定分发到同一台机器.
		XLog.log("## temp dir=%s", tempDir);
		
		// *** 3. 【获取序号流水.25位:时间+机器+6位序号.】
		String seqNo = JRandomUtil.getSeqNo();
		XLog.log("## seqNo=%s,length=%d", seqNo,seqNo.length());
		XLog.log("Long.MAX_VALUE=%s", (Long.MAX_VALUE)+"");
		
		// *** 4. 【判断字符串是否为空】 ****
		String strValue = "";
		if( JStringUtil.isEmpty(strValue) )
		{
			XLog.log("## EMPTY STRING = %s", true+"");
			return;
		}
		
		//******** 或者
		
		JStringUtil.assertNotBlank(strValue, "xx不能为空." );
		
		// *** 5. 【如果其中有任何一个为空则返回true】.
		if( JStringUtil.isAnyOfEmpty("","not empty") )
		{
			XLog.log("## EMPTY STRING = %s", true+"");
		}
		
		// *** 6. 【日期格式化】
		String dateStr = JDateUtil.todayStr(JDateUtil.FMT_YHS);
		XLog.log("## time now is = %s ", dateStr );
		
		// *** 7. 【日期计算. 昨天.】
		Date yestoday = JDateUtil.beforeDate(new Date(), 1);
		String yesStr = JDateUtil.formatDateToStr(yestoday, JDateUtil.FMT_YMD);
		XLog.log("## yestoday is = %s ", yesStr );
		
		//*** 8. 【double 不要直接转字符串.而要进行格式化.】
		double rate = 0.0035;
		String doubleStr = rate*100*360+"";
		String double4Str = JDoubleUtils.format(rate*100*360,4);	//4表示保留4位小数.
		
		XLog.log("ERROR:double string ( just to string ) = %s", doubleStr );
		XLog.log("OK:double string ( 4 precision) = %s",double4Str );
		
		//*** 9. 【double 类型比较.不要直接用==】
		double db1 = (rate/100)*100;		//计算过程中会损失精度.
		XLog.log("[ == ] ---------%s",""+( db1 == rate) );
		XLog.log("[isEqual] ---------%s",""+JDoubleUtils.isEqual(db1,rate));
		
		//*** 10. 【Json对象转换】
		Object ojb = JsonCoder.fromJsonString("{}", Object.class);
		
		//*** 11. 【列表为空判断】
		List list = new ArrayList();
		if( JListUtils.isEmpty(list) )
		{
			XLog.log("list为空.");
			return;
		}
	}
	
	/**
	 * 跨服务器缓存使用示例.
	 * 由于服务器为集群模式，导致HashMap之类的只能在本机本方法中使用，如果涉及到跨请求的缓存服务，那么需要使用到该类.
	 * 
	 * 缓存分为自定义key和非自定义key的缓存.
	 * 方法主要为创建缓存和获取缓存.
	 */
	public static void showCacheExample()
	{
		/***************(1) 创建缓存设置值.******************/
		
		//创建缓存.
		JICrossCache cacheNew = JCrossCacheFactory.buildCrossCache();
		
		//设置值.
		cacheNew.set("userName", "zhangcq");
		cacheNew.set("userAge", "18");
		cacheNew.set("cardNo", "6214467873106889837");
		
		//返回客户端该ID.
		String cacheId = cacheNew.getID();
		
		/*************** (2) 根据缓存ID获取缓存 ******************/
		
		//在后续请求中使用id获取缓存对象.
		JICrossCache cacheGet = JCrossCacheFactory.buildCrossCache(cacheId);
		if ( cacheGet == null )
		{
			XLog.log("缓存已过期.");
		}
		
		//由于获取缓存和调用值之间有时间差,所以存在get获取值为空的可能，但是几率很小，所以务必需要对返回值判空.
		String userName = cacheGet.get("userName");
		String cardNo = cacheGet.get("cardNo");
		if ( JStringUtil.isAnyOfEmpty(userName,cardNo) )
		{
			XLog.log("userName or cardNo not found");
		}
		XLog.log("username=%s,cardNo=%s",userName,cardNo);
		
		//移除key.
		cacheGet.remove("userName");
		
		//销毁.
		cacheGet.destroy();
		
		/***************(1) 创建自定义ID缓存******************/
		/* 由于自定义ID通常使用某些共有信息比如客户号，身份证，卡号等.比如A开发人员在登录请求中按照客户身份证号430121199108293895建立了一个缓存 
		 * B开发人员在转账请求中也用身份证号430121199108293895建立了一个缓存，那么B的缓存和A的缓存可能会相互覆盖.
		 * 所以需要在定义缓存ID的时候加入某些特征前缀,比如登录使用userInfo_430121199108293895,转账使用transfer_430121199108293895等.
		 */
		//创建缓存.
		JICrossCache cacheSf = JCrossCacheFactory.buildCrossCache("userInfo_"+"430121199108293895");
		
		//设置值.
		cacheSf.set("userName", "zhangcq");
		cacheSf.set("userAge", "18");
		cacheSf.set("cardNo", "6214467873106889837");
	}
	
	/**
	 * 异步任务样例.
	 */
	public static void doAsynTask()
	{
		//new a task.
		JASynTaskExample asyn = new JASynTaskExample();
		
		//submit task.
		JTaskExecutor.getInstance().submitTask(asyn);
		
		//wait 1 seconde to get the result.
		try {
			String retMsg = asyn.poll(1);
			XLog.log("msg[%s] is returned from JASynTaskExample", retMsg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main( String args[] )
	{
//		showBaseToolsExample();
//		showCacheExample();
		doAsynTask();
	}
}
