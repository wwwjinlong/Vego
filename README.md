# Vego
- 目前应用服务都是面向多类终端的服务，例如一个交易记录查询请求，可能是同时通过app客户端和一个web网页展示，但是app需要的是json数据，而web网页则是服务端转换为视图返回。
- 本框架主要用于解决两个问题，一是，一个业务逻辑实现可以转换为多种视图。另外，就是将业务逻辑层彻底剥离该框架的细节，只关注接口是输入和输出和逻辑实现，例如一次逻辑实现，在SpringMVC中可以集成，在struts中也可以集成，在dubbo框架中也能使用，方便后续系统升级和服务的迁移。

# 使用技术
    Spring4+SpringMVC4+Myabtis3+druid+redis
    
# 功能介绍
    基于配置的报文加解密（分为无会话的rsa和有会话的aes加解密），多视图，业务逻辑解耦，redis会话保持，灵活的多环境参数配置以及自带日志收集工具等。配合QAutoKit工具包，可以做到基于配置的接口发布和接口代码自动构建，表信息维护和自动构建，以及完备的多环境打包工具等。

# 使用方法
    将工程导出为jar包到VService工程中即可。VService工程中负责实现业务逻辑的实现，实现的的方法类似`public void ping( JAppContext appCtx, HealthReqModel reqModel, HealthRspModel rspModel )`;彻底和servlet,SpringMVC等具体实现脱离。
   
# 待完善的功能
- 配置注解化
- url映射缓存的优化，目前无淘汰机制。

# 示例
参见: http://111.230.155.193:8080/VService/api/all.do

# 文档
- 开发手册: https://github.com/HiRobot/Vego/blob/master/DEV_DOC.md
- 语法配置: https://github.com/HiRobot/Vego/blob/master/CONF_DOC.md
