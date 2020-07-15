#### 测试一个连接的生命周期

| IN | OUT |
|:----|:------|
|IdleStateHandler|IdleStateHandler|
|NettyConnectManageHandler|NettyConnectManageHandler|
|NettyConnectManageHandler|NettyConnectManageHandler|
| |NettyEncoder|
|NettyDecoder| |
|NettyConnectManageHandler|NettyConnectManageHandler|
|NettyServerHandler| |


-- IdleStateHandler

###### case 连接
* c.c.l.n.c.AbstractNettyServer$NettyConnectManageHandler.channelRegistered [id: 0xc61745e5, L:/127.0.0.1:39999 - R:/127.0.0.1:56803]
* c.c.l.n.c.AbstractNettyServer$NettyConnectManageHandler.channelActive [id: 0xc61745e5, L:/127.0.0.1:39999 - R:/127.0.0.1:56803]

###### case 连接-断开(关闭Telnet)
* c.c.l.n.c.AbstractNettyServer$NettyConnectManageHandler.channelInactive [id: 0x1f673e26, L:/127.0.0.1:39999 ! R:/127.0.0.1:56958]
* c.c.l.n.c.AbstractNettyServer$NettyConnectManageHandler.channelUnregistered [id: 0x1f673e26, L:/127.0.0.1:39999 ! R:/127.0.0.1:56958]

###### case 连接-空闲(默认120s触发)
* c.c.l.n.c.AbstractNettyServer$NettyConnectManageHandler.userEventTriggered [id: 0xa9ede41d, L:/127.0.0.1:39999 - R:/127.0.0.1:58423] io.netty.handler.timeout.IdleStateEvent@46d3eeb0
* c.c.l.netty.core.AbstractNettyServer.userEventTriggered channel has been closed [id: 0xa9ede41d, L:/127.0.0.1:39999 ! R:/127.0.0.1:58423], asynchronous is {}
* c.c.l.n.c.AbstractNettyServer$NettyConnectManageHandler.channelInactive [id: 0xa9ede41d, L:/127.0.0.1:39999 ! R:/127.0.0.1:58423]
* c.c.l.n.c.AbstractNettyServer$NettyConnectManageHandler.channelUnregistered [id: 0xa9ede41d, L:/127.0.0.1:39999 ! R:/127.0.0.1:58423]

###### case 连接-请求体格式不正确(和异常类似)
* c.c.l.netty.core.AbstractNettyServer.exceptionCaught [id: 0xa9ede41d, L:/127.0.0.1:39999 - R:/127.0.0.1:58423] DecoderException com.creolophus.liuyi.netty.exception.NettyCommandException: 请求体格式错误,111
* 异常堆栈
* c.c.l.n.c.AbstractNettyServer$NettyConnectManageHandler.flush [id: 0xa9ede41d, L:/127.0.0.1:39999 - R:/127.0.0.1:58423]
* c.c.l.common.api.ApiContextValidator.flush 222 {"header":{"code":0,"error":"请求体格式错误","opaque":"0","result":1004,"type":2}}
