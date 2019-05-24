# springboot多模块项目

**邮箱xiangwbs@163.com**

### 介绍

本项目集成springboot，jpa，mybatis(pagehelper，mybatis-generator)，redis，shiro(shiro分支)，druid，RabbitMQ，log4j2，swagger2，email，rsa加密，基于注解接口幂等设计，基于注解接口限流，分布式全局id雪花算法，二维码，验证码，文件上传，阿里支付，微信支付，快递鸟快递查询等。一些常用util。一些常用类的api使用demo

本项目适合2年以内工作经验的同学学习和借鉴

### 软件架构
软件架构说明


### 启动环境

1. jdk1.8
2. maven
3. redis
4. rabbitmq

### 项目说明

1. boot-service/com.xwbing.datasource-(JpaDataSourceConfig,MybatisDataSourceConfig)
2. boot-service/com.xwbing.demo -常用api的使用
3. boot-service/com.xwbing.rabiit -rabbitmq使用
4. boot-service/com.xwbing.redis -redis使用
5. boot-service/com.xwbing.util.captcha -验证码
6. boot-service/com.xwbing.util.DateUtil2 -java8日期工具类
7. boot-service/com.xwbing.util.DigestsUtil -MD5/SHA-1
8. boot-service/com.xwbing.util.EmailUtil -邮箱
9. boot-service/com.xwbing.util.EncodeUtil -编解工具类
10. boot-service/com.xwbing.util.ExcelUtil -excel工具类
11. boot-service/com.xwbing.util.HttpUtil -httpcliet工具类
12. boot-service/com.xwbing.util.KdniaoUtil -快递鸟快递查询
13. boot-service/com.xwbing.util.PassWordUtil -密码工具类
14. boot-service/com.xwbing.util.QRCodeUtil -二维工具类
15. boot-service/com.xwbing.util.RSAUtil -rsa非对称工具类
16. boot-service/com.xwbing.util.TraceIdGenerateWorker -分布式全局id雪花算法
17. boot-service/com.xwbing.util.ZipUtil.java -压缩工具类
18. boot-web/com.xwbing.configuration -核心配置类
19. boot-web/com.xwbing.handler.FlowLimiterAspect -基于注解接口限流
20. boot-web/com.xwbing.handler.GlobalExceptionHandler -全局异常处理
21. boot-web/com.xwbing.handler.IdempotentAspect -基于注解接口幂等
22. boot-web/com.xwbing.handler.LoginInterceptor -登录拦截器
23. boot-web/com.xwbing.handler.UrlPermissionsInterceptor -权限拦截器
24. boot-web/com.xwbing.handler.WebLogAspect -基于注解日志切面
25. boot-web/data.boot_module_ddl.sql -数据库脚本
26. boot-web/data.bootModule.pdm -表结构
27. boot-web/data.mybatis-generator-core-1.3.7.jar -改过源码的mybatis自动生成工具
28. boot-web/log4j2.xml -log4j2配置

### 接口说明

boot-web/config.properties 里面可以开启登录拦截器和权限拦截器
如果开启权限，得在权限接口里添加权限，否则一些接口无法访问
1./common/getSign 获取签名，用于所有新增接口请求头中，防止表单重复提交
2./user/login 登录接口,登录时会返回token，如果开启登录拦截器，所有接口必须有token请求头才可以访问
3./doc swagger文档映射接口
