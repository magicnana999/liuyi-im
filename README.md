### 介绍
这是一个分布式、快速、轻便的、IM 系统,现在只包含最基本的功能.

### RoadMap

* 单发消息    已具备
* 群发消息    已具备
* 消息 ID      liuyi-im-id 是基于 Mysql,按用户组生成 ID 的实现,但是未使用.这里使用 redis 生成. 
* 后续任务     没计划


### 关于Netty
* 基于 Netty
* 编码和解码 使用 LengthFieldBasedFrameDecoder,头部 4 字节存储长度
* 内置支持 Jackson Gson 和 ProtoBuf 3,在 MessageCoderSelector中拿一个即可.
* liuyi-im-netty 部分参考 RocketMQ 实现.

### 依赖组件
* [liuyi-ms](http://www.github.com/magicnana999/liuyi-ms) 基于 SpringBoot 的 运行在 kubernetes 的开源 Rest开发平台. 公司已使用数年.  
* Mysql 消息也存储在这里.
* Redis messageId 和 消息推送依赖 redis(主要是 list 队列)

### 主要模块介绍
* liuyi-im-backend 后台服务,包含 用户 群组 消息存储 消息同步. 开放 Http 服务供其他模块调用(主要是 liuyi-im-gateway-sk,liuyi-im-gateway-ws)
* liuyi-im-common backend 依赖此模块,此模块依赖 [liuyi-ms](http://www.github.com/magicnana999/liuyi-ms)
* liuyi-im-gateway-sk socket 网关,持有用户的连接,负责收发消息.
* liuyi-im-gateway-wk websocket 网关,持有用户的会话,负责收发消息. (websocket 为毛不用 netty 实现呢? 呃~原因很多~)
* liuyi-im-gateway socket和 websocket 网关依赖次模块.
* liuyi-im-demo-javaclient java 客户端 demo

### 本地测试
* 启动 liuyi-im-backend
* 启动 liuyi-im-sk
* 启动 liuyi-im-demo-javaclient


##### 启动 liuyi-im-backend
* 按 common/liuyi-im_2020-09-03.sql 创建数据库
* 移动 liuyi-im-backend/src/main/resource/demo 到 liuyi-im-backend/src/main/resource/local
* 修改 local 中的连接信息(mysql 和 redis)
* mvn clean install spring-boot:run -f pom.xml

##### 启动 liuyi-im-gateway-sk
* 移动 liuyi-im-gateway-sk/src/main/resource/demo 到 liuyi-im-gateway-sk/src/main/resource/local
* mvn clean install spring-boot:run -f pom.xml

##### 启动 客户端
* 直接运行 StartClient


### 本人
* 电邮: magicnana999@gmail.com
* 微信：magicnana999







