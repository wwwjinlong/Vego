#包结构说明

## 常用包说明
- jplat.base.constant 平台常量包,不允许修改.

## 平台提供的功能
- App接口自动化,管理和字段非空校验注解.
- 渠道接口自动化
- 数据库服务管理
- 异步任务			:jplat.service.asyntask
- 跨请求缓存服务		:jplat.service.cache
- webservice通讯		:jplat.core.net.webservice
- 批量				:jplat.service.batch
- 参数热加载			:jplat.service.ctl.manager
- 参数环境管理		:DEV,PRO
- 平台健康监测		:jplat.service.ctl.health
- 参数化加密[测试]	:jplat.service.ctl.encrypt,IDataGuard
- 日志解析			:zlog
- 请求防重处理以及token生成		:IDataChecker
- 跨应用会话建立以及会话测试
- 代码打包

##平台包结构.
- a.autocode...
  代码自动化工具包,主要包括app接口生成和ICOP接口生成.
  
- conf.xxxx..
  配置包,分为环境配置属性文件(sys_base)和交易配置文件(sys_base),存放配置文件.除了qdbase.properties以外，其他都可以根据环境加载.
  环境标记文件AENV.properties用于切换不同的配置环境.

- jplat.api
 API发布相关的包

 


