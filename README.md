# 分布式发号器服务的设计方案

## 背景：为什么需要设计一个分布式发号器服务
业务系统往往使用关系型数据库的自增id来作为主键，但是当对数据库层做了分库分表之后，就不能依赖于数据库的自增id，在这个场景下自增id的方案就不适用了。
此时就需要一个分布式发号器服务。客户端需要id时，就向分布式发号器服务去申请id。

## 系统的角色
分布式发号器服务涉及到客户端和服务端两个角色。

* 服务端：实现了id的生产方案。对外提供接口可以让客户端获取id。
* 客户端：调用服务的接口获取id。

### 服务端的设计
#### （一）号段方案
借鉴于美团的leaf-segment方案。当客户端调用服务端获取号段的接口，服务端则从数据库中获取一个新号段返回给客户端。

### 客户端的设计
#### （一）号段方案
当客户端号段耗尽的时候，调用服务端接口获取一个新的号段
#### （二）双号段方案
当客户端当前号段下发10%的id时，客户端fork新的线程来加载新的号段作为缓存号段。当前号段消耗完后，则使用缓存号段，若缓存号段未加载完，则等待加载完成后再使用。
