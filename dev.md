### 最近更新日期

```
2017-10-25

```
## 内容目录
```
一、配置管理
二、渠道接口调用服务自动生成
三、App接口生成和发布
四、数据库服务开发
五、日志埋点和查看
六、信息加密
七、开发工具使用

```

### 线上系统差异描述
现在线上环境目前为五台服务器做集群，请求理论上为随机分发到其中一台服务器，任何保存在一台服务器A上的东西（例如存个图片）等，下次请求不一定能读取到，因为下次请求可能分发到了B机器上。所以类似文件存储（例如用户合同的pdf这类业务文档，5M以下），都统一保存到大数据系统去，集中管理。

### 简述
目前dbank平台主要包含了如下几个方面的功能
0. 自动化工具都在a.autocode.amain的包中，以脍炙人口的**GaGa**开头的类.
1. 根据渠道接口文档自动生成渠道调用服务
2. 根据配置生成App请求接口bean
3. 平台接口发布（给App使用）
4. MyBatis数据库代码自动生成
5. 根据svn记录打包
6. 包名都以ebcs开头.
7. 正式开发前请先阅读**NoteBook.java**中代码以及示例，熟悉平台结构和已有功能不要重复造轮子。
8. 在**a.readme.doc**包下面有设计文档和项目结构文档说明。
9. `http://[server]:[port]/[project]/api/all.do` 访问模块列表。
10. `http://[server]:[port]/[project]/api/[module_code]/list.do` 访问接口列表。
11. 点击进入 [链接App接口配置，ICOP接口配置，表设计Excel配置语法关键字](http://note.youdao.com/noteshare?id=744e2455f90c2901e8fb1ba91dbcb3fe&sub=C7622A99BC7C4860950CBBE15DC2B354)

### 一、配置管理

##### 1.配置描述
- conf/system/AENV.properties配置了当前使用的环境是开发**DEV**还是**PRO**环境。这样应用根据该配置的值去**conf/system/PRO**或者**conf/system/DEV**加载配置。
- 配置信息分为`环境配置（env）`和`基础配置（base）`
- **数据库配置**信息放在了**conf/database**中的**qdbase.properties**中。注意这类没有做DEV和PRO的区别。实际加载的就是**qdbase.properties**这个配置。
- 系统配置统一调用`JAppConfig`的相关方法,例如**临时目录**可以调用`JAppConfig.getTempDir("pdf1")`方法，该方法返回一个`{USER_HOME}/__{APPNAME}/temp_pdf1/`的目录路径。

##### 2.配置获取
应用中调用JAppConfig即可后获取相应的参数值。

-----------------------------------

### 二、渠道接口调用服务自动生成
- 位置：a.autocode.amain.icop

#### (1). 初始配置
 1. 修改**icop_autocode.conf**中的工程目录位置**SRC_PATH**
 
#### (2) 接口配置
1. 配置**ICOP_PORT.conf**中ICOP渠道接口信息，主要为交易码**TRANS_CODE**，交易描述**TRANS_DESC**，请求接口**REQUEST**描述和返回接口描述**RESPONSE**（复制渠道接口文档 黏贴即可）。
2. 确认**GaGaICOPPortMain**中的**OVERRIDE**是否为**false**，这样防止漏改交易码后错误覆盖代码，运行**GaGaICOPPortMain**即可。
3. 成功后会生成相应的接口bean和服务类**Service**，并将接口信息文件保存到了**b.autocode.port.icop.backup.raw**中。
4. 刷新工程使用即可。

#### (3) 使用
1. 找到对应的`Service`类，解开响应的注解可以做服务的单元测试。
2. 复制需要的代码到`Controller`中即可使用。
3. 渠道返回值如果需要直接透传返回给App，在渠道**ResponseBean**中有相应的赋值代码，将方法拷贝到`Controller`中，去掉不需要的赋值代码即可（筛选)。
4. **RequestBean**中也有相应的渠道赋值代码（这个在`Service`代码中已有）。

-------------------------------------

### 三、App接口生成和发布

- 位置：a.autocode.amain.app

1. **App**接口遵循接口信息最小化原则，只需要返回客户端需要的数据，不要返回多余的数据。
2. **即使部分完全透传的接口也要经过数据筛选和重新赋值**，**不要直接把渠道的bean返回到客户端去**，这样以后渠道改了接口，App那边可能就跑不通了。如果发现代码中有直接返回渠道的返回bean的会严肃处理。
3. 透传类的赋值代码在生成的渠道的返回bean中已经返回，稍作修改即可。

#### (1). 初始配置
1. 修改**app_autocode.conf**中的工程目录位置**SRC_PATH**

#### (2). 接口配置
1. 配置**APP_PORT.conf**中接口信息,语法和ICOP服务接口描述一致，主要为模块代码**MDLCODE**，模块描述**MDLDESC**，交易码**BEAN_NAME**，交易描述**BEAN_DESC**，请求接口**REQUEST**描述和返回接口描述**RESPONSE**（复制渠道接口文档 黏贴即可）。
2. 确认**GaGaAppPortMain**中的**OVERRIDE**是否为**false**，这样防止漏改交易码后错误覆盖代码，运行**GaGaAppPortMain**即可。
3. 成功后会将接口信息文件保存到了**b.autocode.port.app.backup.模块代码MDLDESC**中。
4. 刷新工程使用即可。
5. 如果是透传接口，在对应渠道的**ReponseBean**中已有赋值代码，复制代码稍作修改即可。如果赋值方法太多，建议写一个赋值方法。

#### (3). 接口发布

由于部分透传接口是copy的ICOP的接口文档，所以显示的话，接口发布中会出现首字母大写的情况，可实际是遵循JavaBean规范，报文中首字母是小写的。如果客户端使用的是从服务端下载的bean代码，则实际不用关心首字母大小写的情况。

1. 在包`b.autocode.api.release`新增一个名为[**模块名_md.json**]文件.**(该`模块名`字对应于PORT文件中的MDLCODE的值)**
2. 其`serviceCode`即为接口名字。
3. 配置`all_mdls.json`中写入模块信息
4. 通过`http://ip:port/dbank/api/all.do`访问列表，点击进入模块列表即可。
5. 或者通过`http://ip:port/dbank/api/{模块名}/list.do`
6. 将配置提交svn之后，在服务器运行`pullapi`或者`pa`可以接口同步到对应服务器。

----------------------------

### 四、数据库服务开发

- 位置：a.autocode.amain.database
- 该部分包含四个功能，分别是**建表sql**的生成,**数据库dao**的生成，**数据库服务service**的生成以**bean的赋值方法**的生成。
- **严禁修改自动生成的代码**，如果需要自定义相关服务，可以复制当前文件然后加上后缀`SD (self-define)` 例如:`UserInfoServiceImplSD`,`UserInfoMapperSD.xml`

#### 初始配置
1. 在`table_autocode.conf`中配置好工程根目录`workProjectRoot`路径,例如`E:\dbank`的路径.
3. 配置好`SQL`输出目录sqlOutDir.(`SQL`在控制台也会输出.)

#### (1)、表信息管理( 建表 )
- **工具类名:** `GaGaTableSQLMain`
1. 在`b.autocode.database.docs`包中存放了表信息的Excel.按照模块建立对应的Excel，然后按照格式填入表信息。
2. 配置好数据库类型
3. 写好表名(即`Excel`的`sheet`同名)
4. 点击运行，即可生成建表的相关SQL和索引以及注释等。
5. 复制控制台内容即可.(SQL也会保存到配置的目录).

#### (2)、表访问DAO代码自动生成
1. 在`MyBatisGenertor.xml`中配置相关表信息，运行里面的命令，即可生成相应的数据库操作类的dao类。运行的命令见其中的**操作说明**。
2. **联合查询和分页查询**可以自己复制几个对应的代码，稍作修改即可。工具暂不支持。
3. 刷新工程。
4. 命令的一般格式为: `jar E:\lib\mybatis-generator-core-1.3.2.jar -configfile E:\database\MyBatisGenertor.xml`

- 附：使用`mybatis-generator-core-1.3.2.jar`自动生成部分代码，该部分参考网络即可。自定义的sql不要和自动生成的代码重名(*加SD后缀*)，以防意外覆盖。自动的sql可以满足大部分的使用场景，包括增删改查。
- 地址见:http://www.cnblogs.com/xdp-gacl/p/4271627.htm

#### (3)、表访服务Service代码生成
- **工具类名：**`GaGaTableServiceMain`
1. 配置的`Excel`名称.
2. 配置的表名.
3. 点击运行。
4. 刷新工程。

#### (4)、Table-Bean 的赋值代码生成
- **工具类名：**`GaGaTableSetClazzMain`
1. 配置号类名，例如`FileAuth.class`.
2. 点击运行。
3. 复制控制台输出的代码。

-------------------------

### 五、日志埋点和查看
- 日志默认打印在`${USER_HOME}`目录的`__logs/dbank`中，按照小时打印，超过`2G`会分文件存储。例如我的日志是在`C:\Users\zhangcq\__logs\dbank`中。
- 埋点日志主要包括以下五部分:

名称 | 代码 | 作用 
---|---|---
行为 | Action | 标识请求类型，比如接收到App请求，用户登录等。
事件 | Event | 标记请求事件，比如登录开始或登录失败
时间 | Time | 标识请求发生时间
关联标记 | Mark | 用于关联事件开始和结束标记
自定义数据 | UserData | 用于记录该请求的特有数据，比如登录手机号，登录IP等。

#### 1. 日志埋点方法
```
String transMark = JTraceLogUtils.getLogMark();

//server receive an app request.
logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_RECV_APP,
				transMark, JTraceLogUtils.buildUserData(myUrl,userMark,StringUtil.getIpAddr(req), ""+req.getRemotePort() )));
/****
    some business code here,balabala
                                    *****/
                      
//an app request finished.
logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_RECV_APP,
    transMark, JTraceLogUtils.buildUserData(myUrl,userMark,
                      rspCode,""+counter.endTick(),exmark) ));
```
*如果没有开始和结束的概念，则打印一个日志即可，关联标记为空，类型为POINT.*

#### 2. 日志埋点样例
- GraBy_f:Act=**RECVApp__**&Event=**_END_**&Mark=**tk2zffC61078797a6174a648c8e0dc0e3bdd306.66FzpQ95RS1o36WIk0ZT**&Time=**2017/08/21-10:15:28:641**&UserData=**{v1/billItemQry.do|18600000000|9901|82|NX}**
- 其中，用户自定义数据分别为`{请求URL,登录ID,请求返回码,请求消耗时间,以及是否异常结束}`
- 每对行为和事件即可表示一个数据模型（表），解析程序可以通过解析以上日志，将日志基本信息和用户自定义数据保存到数据模型中。

#### 3. 日志查看
##### 方法(1).debugcode
`debugcode`主要用于场景测试，App的调试页面会有一个6位数字，该数字点击会改变，所以可以搜索`@debugCode`可以找到日志。在测试某个场景的时候，可以先改变debugcode，然后做交易，然后运行 `grep debugcode logfile` 即可看到该场景下面的日志。

##### 方法(2).会话信息
- **找到会话ID**，`select * from jn_access_list where cust_no = '' or loginid = ''
order by create_date desc,create_time desc` 找到登录信息，`jsession`即为会话ID，客户每次登录的会话ID都是唯一的。
- **找到访问列表**，`flog jsessionID [logfile]` ，即可搜索到本次登录访问的所有请求列表。
- 访问列表分为start，end和mobile三个部分，其中start表示发起的请求，end表示有结束记录的请求，end的userData的基本格式为`{请求的URL|登录账号|返回码(null代表未知,000代表成功,其他表示失败)|交易耗费时间|是否异常标记(EX:表示该请求异常结束)}`
- **获取请求详情**,取会话ID后面的markID值，然后运行`mklog mmmm [logfile]`获取按照线程号过滤后的精简日志。 加上`-d`选项即可查看开始和结束标记之间的所有日志。

--------------------------

### 六、信息加密
- 加密目前由平台接管，应用层不再关心加密方式。但是在部分交易中，可以检查App传送报文是否做过加密，调用JAppContext应用上下文的isSecurity即可，返回true表示做了加密。

----------------------------------------

### 七、开发工具使用
1. 手动进入灰度环境，打开开关即可。
2. 网页版进入灰度环境，首先访问：https://pbank.bankofchangsha.com/directBank/public/test/grayload.do?userid=1862159XXXX ，然后访问后续网页即可。
3. 在测试开发打开后，/test/session/fakeload.do。
4. 调用/mgr/config/reload.do后可以动态刷新参数。
