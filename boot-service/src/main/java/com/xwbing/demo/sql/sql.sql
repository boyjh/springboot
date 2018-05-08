查询是否开启慢查询和慢查询日志存放位置
show variables like 'slow_query%';
查询超过多少秒才记录
show variables like 'long_query_time';

mysql -v
连接mysql
mysql -u用户名 -p密码
mysql -hIP地址 -u用户名 -p密码
修改密码
mysqladmin -u用户名 -p旧密码 password 新密码

show databases;
create database <数据库名>;
drop database <数据库名>;
use <数据库名>;
show tables;
describe tableName; 显示表结构
source F:/file.sql;导入sql文件

exit(回车)