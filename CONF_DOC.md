最近更新日期
----------------

```
2017-09-29

```
## 内容目录
```
一、App接口配置
二、ICOP渠道接口配置
三、数据库Excel配置

```

名词解释
----------------
符号 | 中文 | 语义| 
---|---|---
**`{YOUR_PROJECT_NAME}`** | 你的开发工程 | -
**`{QAUTO_KIT}`** | 工具工程路径 | - 
**`{MODULE_NAME}`** | 接口模块名字  | - 


一、App接口配置
-----------------

### 1. App接口标签
- 接口标签都以@开头,单个标签中不同的内容域分割使用tab键，不要使用空格。这个和Excel的分割是一致的。方便转换。其bean模板替换语法为从配置项里面的key添加__key__进行值替换。
- **配置文件路径：`{YOUR_PROJECT_NAME}`的`a.autocode.app.maker`包下的`APP_PORT.conf`**
- 配置文件路径另存路径：`{YOUR_PROJECT_NAME}`的`a.autocode.app.port.backup.{MODULE_NAME}`
- 语法标签如下表:

标签名称 | 标签中文 | 标签作用 | 接口类型 | 语法样例
---|---|---|---|---
DEVUSER | 开发者 | 用于说明开发该接口的开人员名| | @DEVUSER=zhangcq
MDLCODE | 模块代码 | 标记模块，同时也是生成bean的包名  || @MDLCODE=login
MDLDESC | 模块描述 | 描述模块功能 | | @MDLDESC=登录模块
VERSION | 版本号 | 说明当前版本号两位数 | | @VERSION=01
CHANGE | 修改内容 | 本次修改内容 | | @CHANGE=增加返回用户年龄
GDATE | 生成日期 | 666 | |  @GDATE=2017/02/04 17:34:55
BEAN_NAME | bean名称代码 | 也是bean的主名称 | |
BEAN_DESC | bean描述 | bean中文描述 | |
REFURL | 调用接口url | 666 | | @REFURL=/login/mobile/dolonin.do
REQUEST | 请求开始 | 表示请求字段开始 | |
RESPONSE | 响应开始 | 表示响应字段开始 | |
LIST_START | 列表开始 | 表示列表字段开始 | | @LIST_START=cardList
LIST_END | 列表结束 | 表示列表字段结束 | | @LIST_END=cardList
REMARK | 接口备注说明 | 描述接口调用场景和功能等其他 | |
TRANS_CODE | 交易码 | ICOP交易码 | |
TRANS_DESC | 交易描述 | ICOP交易描述  | |

### 2. App接口基础配置项

- **配置文件路径：`{YOUR_PROJECT_NAME}`的`a.autocode.app.maker`包下的`app_autocode.conf`**
- 语法标签如下表:

标签名称 | 标签中文 | 标签作用 | 接口类型 | 语法样例
---|---|---|---|---
APPNAME | 应用名字 | 666 | | @APPNAME=dbank
DEVUSER | 开发人员名称 | 666 | | @DEVUSER=zhangcq 
SRC_PATH | 项目源码路径 | 666 | | E:\dbank\src\
BEAN_PACKAGE | bean接口主包名 | 666 | | ebcs.app.auto.transmodel
PORT_PATH | 接口描述文件 | 666 | | +a\autocode\app\maker\APP_PORT.conf
PORT_BACKUP_PATH | 接口描述备份目录 | 666 | | +\a\autocode\app\port\backup\
BEAN_TEMP_PATH | 应用名字 | 666 | | +a\autocode\app\maker\app_bean.templ
SETTERS_GRP | 是否生成setters方法 | 废弃 | | @SETTERS_GRP=true
GETTERS_GRP | 是否生成getters方法字 | 废弃 | | @GETTERS_GRP=true

### 3. 类型说明
类型表达式 | 表示类型 | 备注
---|---|---
S10 | String类型，长度为10字符 |
N9 | int类型，长度最长为9位数 |
N10 | long类型，长度最长为10位数 |
N10.2 | double类型，长度为10位数,小数精度为2位 |

### 4. 语法示例

序号 | 举例 | 备注
---|---|---
1 | fileType`\t`S10`\t`Y`\t`类型`\b`00-图片；01-视频；02-音频；03-其他；04-文档 |
2 | @LIST_START=List`\b`文件列表`\b`没有分页|


二、ICOP接口配置
-----------------

### 1. ICOP接口标签
- 接口标签都以@开头,单个标签中不同的内容域分割使用tab键，不要使用空格。这个和Excel的分割是一致的。方便转换。
- **配置文件路径 ：`{YOUR_PROJECT_NAME}`的`a.autocode.icop.maker`包下的`ICOP_PORT.conf`**
- 接口另存包位置 : a.autocode.icop.port.backup.raw.{TRANS_CODE}.icop  
- 语法标签如下表 :

标签名称 | 标签中文 | 标签作用 | 接口类型 | 语法样例
---|---|---|---|---
TRANS_CODE | 交易码 | ICOP交易码 | |
TRANS_DESC | 交易描述 | ICOP交易描述  | |
DEVUSER | 开发者 | 用于说明开发该接口的开人员名| | @DEVUSER=zhangcq
GDATE | 生成日期 | 666 | |  @GDATE=2017/02/04 17:34:55
REQUEST | 请求开始 | 表示请求字段开始 | |
RESPONSE | 响应开始 | 表示响应字段开始 | |
LIST_START | 列表开始 | 表示列表字段开始 | | @LIST_START=cardList
LIST_END | 列表结束 | 表示列表字段结束 | | @LIST_END=cardList
FILTER_EMTPY | 是否过滤空列表 | 该调用十分耗费性能在不必要的时候应该关闭 | | @FILTER_EMTPY=true

### 2. ICOP接口基础配置

- **配置文件路径 ：`{YOUR_PROJECT_NAME}`的`a.autocode.icop.maker`包下的`icop_autocode.conf`**
- 语法标签如下表:

标签名称 | 标签中文 | 标签作用 | 语法样例
---|---|---|---
APPNAME | 应用名字 | 666 | @APPNAME=dbank
DEVUSER | 开发人员名称 | 666 | @DEVUSER=zhangcq
SRC_PATH | 项目源码路径 | 666 | E:\dbank\src\
MDLCODE | 模块代码 | 由于ICOP接口不按照模板管理，所以配置在基本配置中 | @MDLCODE=raw
BEAN_PACKAGE | bean接口主包名 | 666 | ebcs.app.auto.transmodel
PORT_PATH | 接口描述文件 | 666 | +a\autocode\icop\maker\ICOP_PORT.conf
PORT_BACKUP_PATH | 接口描述备份目录 | 666 | +\a\autocode\icop\port\backup\
BEAN_TEMP_PATH | bean模板路径 | 666 | +a\autocode\app\maker\app_bean.templ
SERVICE_TEMP_PATH | service模板路径 | 666 | +a\autocode\icop\maker\icop_service.templ
SYSDESC | 系统描述 | 666 |@SYSDESC=渠道接口

三、数据库Excel配置
-----------------
- **配置文件位置：`{YOUR_PROJECT_NAME}`的`a.autocode.database.docs`包下XXXXX.xlsx。**
- 为了便于在不同数据库之间切换，没有提供日期类型，日期使用字符串即可.
- 类型语法如下表：

类型符号 | 字段长度  | 类型中文  | 备注
---|---|---|---
S | 128  | String  | 日期2017/09/30(12位),时间19:19:19:199(14位)
N | 9  | int  |
N | 16  | long  |
N | 18,2  | double  |
