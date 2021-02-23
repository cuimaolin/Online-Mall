# 在线商城系统

该项目使用前后端分离模式进行开发，项目使用的技术栈包括：SpringBoot+MyBatis+Redis+SpringSecurity+Elasticsearch+RabbitMQ

## 实现的功能

- [x] 商品的增删改查
- [x] 用户的登录与授权
- [x] 数据的缓存
- [x] 分布式锁
- [x] 商品搜索
- [x] 取消超时订单

 ### 用户的登录与授权

通过整合SpringSecurity+JWT实现用户的登录与授权功能。

- JWT token的格式：header.payload.signature
- header中用于存放签名的生成算法

```json
{"alg": "HS512"}
```

- payload中用于存放用户名、token的生成时间和过期时间

```json
{"sub":"admin","created":1489079981393,"exp":1489684781}
```

- signature以header和payload生成的签名，一旦header和payload被篡改，验证将失败

```json
//secret为加密算法的密钥
String signature = HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
```

JWT实现认证和授权的原理

- 用户调用登录接口，登录成功后获取到JWT的token；
- 之后用户每次调用接口都在http的header中添加一个叫Authorization的头，值为JWT的token；
- 后台程序通过对Authorization头中信息的解码及数字签名验证来获取其中的用户信息，从而实现登录和授权

### 商品搜索

整合Elasticsearch实现了商品信息在Elastisearch中的导入、查询、修改、删除。Elasticsearch是基于倒排索引实现的，正向索引是通过key找value，反向索引则是通过value找key。

### 数据的缓存

手机验证码功能。根据手机号生成随机的验证码并设置过期时间，存储在Redis中。使用到了String数据结构。

### 分布式锁

Redis分布式锁主要利用Redis的setnx命令。

- 加锁命令：`SETNX key value`，当键不存在时，对键进行设置操作并返回成功，否则返回失败。KEY是锁的唯一标识，一般按业务来决定命名；
- 解锁命令：`DEL key`，通过删除键值对释放锁，以便其他线程可以通过SETNX命令来获取锁；
- 锁超时：`EXPIRE key timeout`，设置key的超时时间，以保证即使锁没有被显式释放，锁也可以在第一时间后自动释放，避免资源用于被锁住。

### 取消超时订单

- 用户进行下单操作；
- 生成订单，获取订单的id；
- 获取到设置的订单超时时间（假设设置的为60分钟不支付取消订单）；
- 按订单超时时间发送一个延迟消息给RabbitMQ，让它再订单超时后触发取消订单的操作；
- 如果没有支付，进行取消订单

