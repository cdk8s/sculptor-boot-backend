
## 版本

- master 为 2.0.0 版本
- 1.0.0 版本请切换分支

## 部署说明

- 后台需要 JDK8 和 Maven 3.3.9+ 的环境
- 前台需要 Node.js 12+、Npm 6.14.11+、Yarn 1.22.5+ 的环境
- 后台部署命令：
```
mvn clean install -Dmaven.test.skip=true
sh startup.sh start prod
```

- 前台部署命令：
```
yarn install
yarn run build
```

## 开发环境 host

```
127.0.0.1 sso.cdk8s.com
127.0.0.1 redis.cdk8s.com
127.0.0.1 mysql.cdk8s.com
127.0.0.1 postgresql.cdk8s.com
127.0.0.1 sculptor.cdk8s.com
127.0.0.1 xxl.cdk8s.com
127.0.0.1 websocket.cdk8s.com
127.0.0.1 elasticsearch.cdk8s.com
127.0.0.1 mongodb.cdk8s.com
127.0.0.1 rabbitmq.cdk8s.com
```

## 核心业务模块的目录结构介绍

```
/src/main/java/com/cdk8s/sculptor/actuator
- actuator 自定义断点
- aop 切面编程
- audit 请求审计
- config 组件配置
- controller 暴露给 admin 后台的 Controller
- enums 一些业务枚举
- eventlistener 事件监听
- extend 组件扩展功能
- filter 过滤器
- init 初始化组件
- interceptor 拦截器
- login 单点登录
- mapper 数据库操作
- mapstruct 简单对象的属性拷贝
- multiapi 暴露给 H5、APP 等大前端的 Controller
- pojo 一些基础简单类（业务的 pojo 是写在 sculptor-boot-pojo 模块下）
- properties 自定义的一些属性
- quartz 调度框架
- service 业务服务
- statistics 业务统计
- strategy 登录、上传的一些策略
- task 调度任务
- thirdclientapi 暴露给第三方客户端的 Controller
- web 前端模板渲染的 Controller
```

## 数据库约定

- 1. 不写 ` 号包裹字段名称和表名称，有助于更好地支持其他数据库，其他库不支持这个符号
- 2. 不写 unsigned，有助于更好地支持其他数据库，其他库没有这个属性
- 3. 不写 auto_increment，有助于更好地支持其他数据库，以及后续移库和分区分表的需求
- 4. 不写 int，在 PGSQL 中它叫做 integer，不能简写。推荐 smallint 和 bigint 来代替一般数字类型上的需求
- 5. 不写 datetime，主要是可以减少因为时区问题带来的一系列问题，对消息队列，大数据软件有更好的支持
- 6. 不在表结构中写 unique key 和 index，推荐单独 SQL 语法写，H2 和 PGSQL 都不支持在表结构中定义。
- 7. 枚举字段命名带有 _enum 后缀
- 8. 逻辑删除命名为 delete_enum，不用 is_ 开头是因为 Java 对这类字段生成 set / get 会有特殊处理，会忽略到 is 这样不易于 Java 代码和数据库之间的维护
- 9. 数字 0,1 理论上代表 0 false ，1 true，mybatis 框架默认了这套逻辑，所以反而在判断数字类型的时候会自动帮你转换，造成了不必要的麻烦，所以不推荐 0 作为下标起点
- 一些 Java 特有的坑
    - [使用java Bean时，is 打头的boolean属性的小坑](https://blog.csdn.net/sdfgedcx/article/details/91953744)
    - [mybatis 中 if-test 判断大坑](https://www.cnblogs.com/grasp/p/11268049.html)

## 自增 ID 优缺点

- 优点：
    - 效率较高，占用空间小
    - 人类可读性高
- 缺点：
    - 易于推断，容易被猜解，爬虫，跨权限泄露数据
    - 易于暴露业务实际数据量，
    - 不易于扩展，迁移，割接，同步其他数据库类型
    - 不易于数据拆分、合并
- 单体、分布式 ID 方案成熟：
    - 雪花算法 snowflake
    - 美团 Leaf
    - 滴滴 Tinyid
    - 百度 uid-generator


## 常用接口格式

- 未登录返回内容

```
HTTP 状态码：401
返回报文：
{
    "code": 200001,
    "isSuccess": false,
    "msg": "您还未登录，请先登录",
    "timestamp": 1606215053152,
    "data": null
}
```


