##############################################
# 请勿改动jplat开头的包和a.autocode里面的文件. #
# 防止升级之后出现问题. #
# 业务系统代码写到自己业务名称的包中即可. #
##############################################

#***************************
            (1) MyBatis Generator使用命令
                    ***************************#

java -jar E:\EBANK_CODE-svn\EBANK_SERVER\dbank1\trunk\dbank\WebContent\WEB-INF\lib\mybatis-generator-core-1.3.2.jar -configfile E:\EBANK_CODE-svn\EBANK_SERVER\dbank1\trunk\dbank\MyBatisGenertor.xml -overwrite

#***************************
            (2) 平台健康监测
                    ***************************#
http://localhost:8080/[projectName]/test/__info.do|ping.do

#***************************
        (3) 服务端使用说明·接口描述
                    ***************************#
样例类名称：
PullCustInfoExampleCtrl

测试地址:
http://localhost:8080/fileboxy/public/test/qcustinfo.do
http://162.16.2.152:8180/fileboxy/nss/login/auth.do

############# APP请求描述 #############
#请求头约定:
HTTP-header:
[KEY]    |    [VALUE]               |    [描述.]
------------------------------------------------------------
infoq    |    ch=ebank;e=p          |    ch渠道为ebank, e加密类型为明文.
ttkn2    |    [SERVER-RET-VALUE]    |    会话ID(保存了会话数据)

[OR]

Cookie
[KEY]    |    [VALUE]              |    [描述.]
------------------------------------------------------------
infoq    |    ch=ebank;e=p         |    ch渠道为ebank, e加密类型为明文.
ttkn2    |    [SERVER-RETURNED]    |	会话ID(保存了会话数据)

-请求字符集:charset:utf-8
-请求内容格式:

{
    "head": {
        "pageId": "[SERVER-RETURNED]"
    },
    "body": {
        "authKey": "4653B4F21522B715DFE397D3DC0A55AB",
        "authType": "K"
    }
}

-备注:pageId 为客户登录成功后返回的身份令牌，该值不应该出现在明文环境中.
-在强认证环境中需要将该值加入加密传输，普通的请求通过会话ttkn2认证即可.

############# APP响应描述 #############

#响应头约定:
HTTP-header:
[KEY]    |    [VALUE]         |    [描述]
------------------------------------------------
rethd1   |    0000            |    请求成功,数据格式和服务端约定的一样.
rethd1   |    FFFF            |    请求失败,数据格式按照和服务端约定的标准错误信息格式进行解析.
set-ttkn2|    [SERVER-RETURNED] |  会话ID

-返回字符集:text/plain;charset:utf-8
-返回内容格式:

{
  "head" : {
    "retCode" : "0000",
    "retMsg" : "交易成功",
    "pageId":"身份认证后(如果有)返回",            <可选>
    "ttnk2":"会话ID(如果建立)                    <可选>
  },
  "body" : {
    "idNumber" : "210803199101271524",
    "custName" : "杨一君"
  }
}

-字段解释:
    1.pageId表示客户身份认证标志.
    2.ttkn2如果有，则需要放到Http-Header中的ttkn2域名中或者Cookie的ttkn2的域中.

-标准错误信息格式:
{
  "head" : {
    "retCode" : "9904",
    "retMsg" : "没有该客户信息，请核对信息"
  }
  "body" : {},
}

###### 服务端日志样例 #############
[INFO][http-bio-8080-exec-3]-[16:21:53.712] JAppPackJsonParser:60 [__FROM_APP1:{"head":{},"body":{custId="00000013720"}}]
__SYS_INIT__:JSystemConfig load success,path=conf/system/sys_base
__SYS_INIT__:JSystemConfig load success,path=conf/system/sys_env
[INFO][http-bio-8080-exec-3]-[16:21:53.808] JWebServieClient:41 [__ICOP.URL=http://162.16.1.137:43294/icop/services/JTService?wsdl]
[INFO][http-bio-8080-exec-3]-[16:21:53.808] JWebServieClient:42 [__SEND_REQ_MSG__请求报文>>>>><?xml version="1.0" encoding="UTF-8"?><Service><Header><ChannelId>MB_01</ChannelId><Encrypt></Encrypt><ExternalReference>20161216162153727097218</ExternalReference><RequestBranchCode></RequestBranchCode><RequestOperatorId></RequestOperatorId><RequestTime>20161216162153727</RequestTime><ServiceCode>PCM1008</ServiceCode><TermNo></TermNo><TradeDate>20161216</TradeDate><Uuid></Uuid><Version>1.0</Version></Header><Body><Request><CustId>00000013720</CustId></Request></Body></Service>]
[INFO][http-bio-8080-exec-3]-[16:21:54.525] JWebServieClient:56 [__RECV_RSP_MSG__返回报文<<<<<Service><Header><ChannelId>MB_01</ChannelId><Encrypt></Encrypt><ExternalReference>20161216162153727097218</ExternalReference><RequestBranchCode></RequestBranchCode><RequestOperatorId></RequestOperatorId><RequestTime>20161216162153727</RequestTime><ServiceCode>PCM1008</ServiceCode><TermNo></TermNo><TradeDate>20161216</TradeDate><Uuid></Uuid><Version>1.0</Version><Response><ReturnCode>00000000</ReturnCode><ReturnMessage></ReturnMessage></Response></Header><Body><Response><IdType>01</IdType><IdNumber>210803199101271524</IdNumber><CustId>00000013720</CustId><CustName>杨一君</CustName><Gender>F</Gender><LastLoginDt></LastLoginDt><Email></Email><CustLev>2</CustLev><QQ></QQ><WxNo></WxNo><CustStatus>0</CustStatus><ReserveInfo></ReserveInfo><Mobile>18712121236</Mobile><Address></Address><Education></Education><OpenDate>2016-09-07 18:50:15</OpenDate><VipLevel>0</VipLevel><ChannelCustNo></ChannelCustNo><PcmChanSAS>11000000</PcmChanSAS><ListNum>2</ListNum><List><MediumType>BCS_P</MediumType><CardNo>1130452948</CardNo><OpenBranchCode>012601</OpenBranchCode><CardStatus>0</CardStatus><CardType>0</CardType><ProductName></ProductName><NickName></NickName><AccountName>杨一君</AccountName><BankName>长沙银行存折</BankName><Mobile>18712121236</Mobile></List><List><MediumType>BCSEBANK</MediumType><CardNo>6214461111000016863</CardNo><OpenBranchCode></OpenBranchCode><CardStatus>0</CardStatus><CardType>0</CardType><ProductName></ProductName><NickName></NickName><AccountName>杨一君</AccountName><BankName>长沙银行e账户</BankName><Mobile>18712121236</Mobile></List><SecListNum></SecListNum><SecList></SecList><MsgFlag>00000000000000000000000000000000</MsgFlag></Response></Body></Service>]
[INFO][http-bio-8080-exec-3]-[16:21:54.591] JAppPackJsonParser:143 [__TO_APP1:{"body":{"idNumber":"210803199101271524","custName":"杨一君"},"head":{"retCode":"0000","retMsg":"交易成功"}}]

#***************************
        ######## (4) 接口代码自动生成工具 配置  ##########
        (可生成app的请求接口和服务端的接收接口bean，另外也可以生成ICOP的接口和服务).
                    ***************************#
1. 目前支持数据类型:
    S32--String类型长度32
    N10--int类型
    N16--long类型
    N10,2--double类型
    LIST--列表类型.

2. 语法说明
    _不同的单元之间采用\t制表符进行分割.其中,中文名字和中文说明之间使用‘ ’空格进行分割,例如:
    >> loginType    登录类型 p-密码的 h-手势密码.    S2    Y  <<
    >>不是语法一部分,其中Y表示必需项目.不能为空.该语法会对bean生成注解,可以在获取bean的时候自动判空。

3. 配置分为[环境配置][接口配置][模板配置],<<...>>中的为必要项.
    [环境配置]>>
        <<@SRC_PATH(源码位置) @BEAN_PACKAGE @BEAN_TEMP_PATH(BEAN模板位置) @PORT_PATH(接口定义文件路径) @PORT_BACKUP_PATH(接口备份路径)>> @APPNAME @DEVUSER  其中各种path可以通过+号与src_path进行连接.
    [接口配置]>>
        <<@BEAN_NAME @REQUEST @LIST_START @LIST_END @RESPONSE @VERSION @MDLDESC>> @DEVUSER @CHANGE(修改内容) @REFURL(调用URL) @BEAN_DESC @GDATE(生成编号) @REMARK(备注) @FILTER_EMTPY(过滤空列表)
    [模板配置]
        <<__BEAN_NAME__,__BEAN_FIELDS__,__BEAN_PROPS__>>__SETTERS_GRP__,__GETTERS_GRP__
    _模板中占位符号是从配置文件中的配置key而来,例如模板__DEVUSER__表示配置中的DEVUSER的值.
        
3. 接口字段都会依据Bean规范转换为首字母小写.

4. 使用方法：配置App接口信息文件APP_PORT.conf(在a.autocode.app.maker包中),运行AppServiceMain即可,注意不要覆盖了.
            -- a.autocode.app.port.backup中会保存接口信息的备份文件.同时也是接口发布的接口信息文件.

            -- 用于bean文件都生成在同一个包中,建议bean名称用模块简称(3到5位不重名)加功能名称命名.例如:tnfDetailQuery --> TnfDetailQueryReqModel
            tnf即transfer的简称,虽然名字不好看,但是保证了代码之间不会覆盖,也好寻找定位,输入模块检查即可.
            
            -- 另外生成的接口bean里面有直接赋值和取值的模板,拷贝模板代码到自己的代码中,稍作修改即可.
            
            -- ICOP服务同理,将ICOP接口文档中的字段按照格式复制过来(ICOP标准文档直接复制接口信息即可)
            -- 另外ICOP服务会自动生成调用服务,无需再手工写代码.直接调用即可.

#***************************
        ######## (5) 服务发布配置说明  ##########
                    ***************************#
1. 实  现 类：JAPIReleaseController
2. 组织结构: 模块:1--> 功能:n
3. 配       置： 在包jplat.api.rel下面放置一个[模块简称XXX]_md.json的配置文件,格式如下:
----------------BEGIN-------------->
{
    "moduleName": "登录模块(共3)",
    "services": [
        {
            "serviceCode": "loginDemo9",
            "chName": "用户登录"
        },
        {
            "serviceCode": "loginDemo",
            "refUrl":"login/qcustinfo.do"
        }
                ]
}
<----------------END--------------
    其中,serviceCode表示的接口名称,即APP_PORT.conf中BEAN_NAME的值.
    平台会根据该名字取a.autocode.app.port.backup中找到对应的接口信息.
    然后解析成网页或者代码.

4. 调用方法：http://localhost:8080/${project_name}/api/[模块简称XXX]/list.do

#***************************
        ######## (6) 监控日志打印规则 说明  ##########
                    ***************************#
1. 实现类见:JLogUtils
2. 设计思路:分为行为,时间,事件和数据这几个关键数据组成.另外,日志有些有开始结束,有些只是作为记录.
    例如:
        (1) 用户TomJ-在19:08:20-开始-登陆 
        (2) 用户TomJ-在19.08:22-登陆-失败
         _其中,开始和结束事件通过Mark值进行关联.
          [OR]
        (1)用户TomJ访问了一次理财页面.
    
3. 日志样例
    _GraBy_f:Act=RECVApp__&Event=START&Mark=fqMkmu1anXCJt&Time=2016/12/31-09:54:15:718&UserData={ileboxy/nss/login/auth.do|192.168.1.100:48243}
    _GraBy_f:Act=RECVApp__&Event=_END_&Mark=fqMkmu1anXCJt&Time=2016/12/31-09:54:16:252&UserData={ileboxy/nss/login/auth.do|0000}

#***************************
        ######## (7) 会话设计思路  ##########
                    ***************************#
-场景
-- 一般是分为,有客户状态,无客户状态,客户登录后不一定每个请求都需要带客户状态.

-思路
1. 会话,只是保存状态的地方，如果两个不同的HTTP请求之间需要交换数据,那么就用普通会话令牌即可.
   -该令牌放在HTTP-Header的头中的JAppConnectInfo.H_HTOKEN_NAME(ttnk2)域中,该令牌遭受XSS脚本攻击后容易被盗取.

2. 客户信息认证,认证客户合法身份后的信息.该认证令牌放在请求报文的head请求头中的pageId中(名字这样可以迷惑)
      这样客户认证信息可以一定程度受到加密的保护(在有加密的情况下)

3. 另外如有客户信息有加密,那么加密密钥的协商是放在普通会话令牌中,否则将会无法解密报文.

4. 鉴于两个令牌管理的复杂性（比如超时管理），通常是将客户令牌保存到会话中，对客户令牌进行校验.
   -这样只需要管理值需要管理会话令牌即可.
      
5. 有些数据只能做关联缓存，不应该放在会话里面，比如，文件上传中对每个文件

6. 数字保管箱会话设计中,发现客户端虽然自己管理JSESSIONID,但是送到服务端的时候是送的E钱庄的会话ID不是授权成功后的会话ID,但是
会话检查一样的通过了.原来JSESSIONID服务端默认如果是同一个套接字，则使用套接字最后创建的那个会话.

7. 会话默认是认为客户认证通过才建立会话,需要会话才会调用获取会话的方法.
   -如果不需要会话但是调用了会话方法则报超时.
   
8. 有些场景的会话是用来记录日志等追踪客户信息的，脱离了框架，所以需要提供给一个方法，可以脱离框架管理来单独获取会话.
   
#****************************
		##########(8) 加密设计 ################
					*********************************#
在http header的infoq中加入如下值:
e=a表示AES加密.
e=r表示RSA加密.

AES加密过程:
1.从登录信息获取AES加密钥aesKey.
2.对报文字符串使用utf-8编码获得字节流.
2.使用aesKey密钥对字节流进行加密,对获得的加密字节流做base64后放入http的body即可

RSA加密过程:
1. 生成一个16字节AES密钥aesKey(包括A-Z,a-z,0-9,_ss)
2. 使用RSA密钥对aesKey密钥进行加密,对加密后的字节流做base64获得字符串str1.
3  对报文字符串使用utf-8编码获得字节流.
4. 使用aesKey对业务数据的字节流做加密，并对加密后的字节流做base64获得字符串str2.
5. 用#号拼接str1#str2后写入http的body即可.

另外，在对若果发生错误，返回信息是否需要加密的问题。
由于在aes加密方式中，如果加密出错或者会话超时，这类错误是无法加密的，所以对于错误信息，加密处理无法统一.
最终报文加密方式以返回报文中的HTTP-Header中的rtt为准.
