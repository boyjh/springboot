# springboot多模块项目
**邮箱:xiangwbs@163.com**
### 介绍
本项目集成springboot，jpa，mybatis(pagehelper，mybatis-generator)，redis，shiro(shiro分支)，druid，RabbitMQ，log4j2，swagger2，email，rsa加密，基于注解接口幂等设计，基于注解接口限流，全局异常处理，乐观锁异常重试机制，基于redis分布式锁，xxl-job，分布式全局id雪花算法，二维码，验证码，防盗链，文件上传，阿里支付，微信支付，快递鸟快递查询，阿里云log，钉钉机器人等。一些常用util。一些常用类的api使用demo！

本项目适合3年以内工作经验的同学学习和借鉴，有不足之处，请大家指出，互相学习！
### 软件架构
软件架构说明
### 启动环境
1. jdk1.8
2. maven
3. redis
4. rabbitmq
5. lombok插件
6. xxl-job服务
### 项目说明
##### boot-config(自动配置)
* com.xwbing.config.aspect.FlowLimiterAspect 基于注解接口限流
* com.xwbing.config.aspect.IdempotentAspect 基于注解接口幂等
* com.xwbing.config.aspect.LockAspect -基于redis分布式锁
* com.xwbing.config.aspect.OptimisticLockRetryAspect -乐观锁异常重试机制
* com.xwbing.config.redis -redis自动配置
* com.xwbing.config.aliyun -aliYunLog和dingTalk自动配置
##### boot-service
* com.xwbing.datasource-(JpaDataSourceConfig,MybatisDataSourceConfig)  
* com.xwbing.demo -常用api的使用  
* com.xwbing.rabiit -rabbitmq使用  
* com.xwbing.util.captcha -验证码
* com.xwbing.util.DateUtil2 -java8日期工具类
* com.xwbing.util.DigestsUtil -MD5/SHA-1
* com.xwbing.util.EmailUtil -邮箱工具类
* com.xwbing.util.EncodeUtil -编码解码工具类
* com.xwbing.util.ExcelUtil -excel工具类
* com.xwbing.util.HttpUtil -httpcliet工具类
* com.xwbing.util.KdniaoUtil -快递鸟快递查询
* com.xwbing.util.PassWordUtil -密码工具类
* com.xwbing.util.QRCodeUtil -二维码工具类
* com.xwbing.util.OrderNoUtil -订单号生成工具类
* com.xwbing.util.RSAUtil -rsa非对称加密工具类
* com.xwbing.util.TraceIdGenerateWorker -分布式全局id雪花算法
* com.xwbing.util.ZipUtil.java -压缩工具类
* generatorConfig.xml -mybatis自动生成插件配置
##### boot-web
* com.xwbing.configuration -核心配置包
* com.xwbing.handler.FileFilter -防盗链
* com.xwbing.handler.GlobalExceptionHandler -全局异常处理
* com.xwbing.handler.LoginInterceptor -登录拦截器
* com.xwbing.handler.UrlPermissionsInterceptor -权限拦截器
* com.xwbing.handler.WebLogAspect -基于注解日志切面
* data.boot_module_ddl.sql -数据库脚本
* data.bootModule.pdm -表结构
* log4j2.xml -log4j2配置
### 接口说明
boot-web/config.properties里面可以开启登录拦截器和权限拦截器。<br>
如果开启权限，得在权限接口里添加权限，否则一些接口无法访问<br>
1. /common/getSign 获取签名，用于所有新增接口请求头中，防止表单重复提交
2. /user/login 登录接口,登录时会返回token，如果开启登录拦截器，所有接口必须有token请求头才可以访问
3. /doc swagger文档映射接口
