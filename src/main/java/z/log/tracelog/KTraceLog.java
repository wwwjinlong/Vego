package z.log.tracelog;

/**
 * 日志追踪常量.
 * @author zhangcq
 * @date Dec 28, 2016
 * @comment
 */
public class KTraceLog
{
	//日志追踪标记
	public static final String GRABY_FLAG = "_GraBy_f:";	//日志提取标记- file-sys.
	public static final String ACTION = "Act=";			//日志抓取标记- 行为.
	public static final String EVENT = "Event=";		//事件.
	public static final String TIME = "Time=";			//志时间.
	public static final String MARK = "Mark=";			//trace-mark关联值
	public static final String CONNMARK = "&";			//链接符号.
	
	//行为枚举.
	public static final String ACTION_RECV_APP = "RECVApp__";		//表示App请求日志标记。
	public static final String ACTION_CALL_ICOP = "CALL_ICOP__";	//表示渠道日志标记。
	public static final String ACTION_AUTH = "AUTH__";				//表示授权行为日志标记。
	public static final String ACTION_REDIS = "REDIS__";			//redis服务监控标记.
	
	public static final String ACTION_JINIT = "JINIT__";			//平台初始化日志.
	public static final String ACTION_EXCEPTION = "EXCEPTION__";	//平台少见异常监控.
	public static final String ACTION_SESSION = "SESSION__";		//Session监控.
	public static final String ACTION_DATACHECK = "DATACHECK__";	//数据检查.
	
	//事件枚举.
	public static final String EVENT_START = "START";			//标记-开始.
	public static final String EVENT_END = "_END_";				//标记-结束.
	public static final String EVENT_SUCCESS = "SUCCESS";		//标记-成功.
	public static final String EVENT_FAIL = "FAIL";				//标记-失败.
	public static final String EVENT_POINT = "POINT";			//标记-过程标记.
	
	public static final String USER_DATA = "UserData={";		//用户自定义数据.
	public static final String USER_DATA_END = "}";
	
	//表示App请求标记长度.
	public static final int MARK_LENGTH=21;
}
