#包结构说明

## 常用包说明
- ebcs.trans.constant 交易常量包,运行修改和增加,按照功能模块划分.
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
 API发布相关的包.
 
 ##长行业务包
- com.csbank.model.app
  兼容老平台客户模型兼容包，该包不再做任何开发.
  
- ebcs.app.auto
  由a.autocode中自动生成的代码，给代码不要手工修改，因为下次生成被覆盖.
  
- ebcs.controller.***
  控制器,主业务逻辑处理类.

- ebcs.database.xxx
  MyBatis生成工具自动生成的数据库代码类.这些类都不要进行手工修改，下次是自动生成会被覆盖.
   如果要自定义一些类，可以另外自己取名字,规定自定义的在文件名字后面加上_diy.这样技能保证同名又能下次 自动生成时不会被覆盖.

- ebcs.icop.xxx
  由autocode自动生成的渠道代码，该代码不建议手工修改，以后可能会被覆盖.
  
- ebcs.plat.
 长行平台基础类，主要包括一些公共服务，比如密码功能常量，交易流程基础类。该包一般不允许修改.
 
- ebcs.trans.constant
 长行常量包，建议按照功能模块命名常量类，防止不同功能并行开发时，相互影响。

 


