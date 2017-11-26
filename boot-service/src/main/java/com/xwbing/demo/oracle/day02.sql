SELECT语句
SELECT是用来查询数据的语句DQL
查询某张表中的所有字段的记录：
SELECT *FROM EMP_XWBING;
查看指定字段的值：
SELECT ename,sal,job FROM EMP_XWBING;

DQL必须包含的部分是SELECT子句与FROM子句
SELCET用来确定查询的字段，可以使用的有：表的字段，函数，表达式FROM子句来确定查询的表

SELECT中使用表达式
查看每个员工的年薪
SELECT ename,sal*12 from emp_xwbing;


字符串函数
1：concat(p1,p2) 连接字符串
select concat(ename,sal) from emp_xwbing;
2:连接字符串简便方式：||,相当与java里的-+号，拼接作用
select ename||':'||sal from emp_xwbing;

LENGTH(p)函数，获取字符串长度
SELECT ename,LENGTH(ename) FROM emp_xwbing;

UPPER,LOWER,INITCAP
将字符串转换为全大写，全小写，首字母大写
dual：伪表，当查询的内容与任何一张表中的数据无关时，可以查询伪表
SELECT UPPER('hello'),INITCAP('wo de')
FROM dual;

TRIM,LTRIM,RTRIM去除字符串两边指定字符
SELECT TRIM('E'FROM'EELRERE') FROM DUAL;去单
SELECT LTRIM('DSFRRA','SD')FROM DUAL;随便

LPAD,RPAD补位函数，若补位小于原长度，一律从左往右截
要求显示指定内容指定位数，若不足则补充若干指定字符以达到要显示的长度
SELECT ENAME,rPAD(SAL,5,'$') FROM EMP_XWBING;

SUBSTR截取字符串
截取给定字符串，从指定位置开始截取指定个字符，在数据库中，下标都是从1开始的！！！
SELECT SUBSTR('think in java',5,3) from dual;
SELECT SUBSTR('think in java',-4,3) from dual;

INETR(char1,char2[,n[,m]])
查找char2在char1中的位置
n为从指定位置开始查找，可以不写，
m为第几次出现，可以不写
n，m不写默认都是1
SELECT INSTR('think in java','in')from dual;
SELECT INSTR('think in java','in',2,2)from dual;




SELECT ENAME,SAL,DEPTNO FROM emp_xwbing WHERE LOWER(ENAME)='smith';
select ename,sal from emp_xwbing where LENGTH(ename)=5;
select ename,sal from emp_xwbing where substr(ename,1,1)='A';


数字函数
ROUND(n,[,m])对n进行四舍五入，保留到小数点后m位
m可以不写的，不写默认值为0
m为0则保留到整数位，-1为10位数，以此类推
SELECT ROUND(45.678,2) FROM DUAL;
SELECT ROUND(45.678) FROM DUAL;
SELECT ROUND(45.678,-1) FROM DUAL;

TRUNC(N[,M])用于截取，用法跟ROUND一样，只是不四舍五入
SELECT TRUNC(45.678,2) FROM DUAL;
SELECT TRUNC(45.678) FROM DUAL;
SELECT TRUNC(45.678,-1) FROM DUAL;

MOD(m,n) 取余，返回m除以n后余数，n为0则直接返回m
SELECT ename,sal,mod(sal,1000) from emp_xwbing;

CEIL(N)和 FLOOR(N)向上取整和向下取整
SELECT CEIL(44.5) FROM DUAL;
SELECT FLOOR(44.5)FROM DUAL;


日期类型
DATE：7个字节，保存世纪，年月日时分秒
TIMESTAMP：时间戳，比DATE多4个字节，可以保存秒以下的精度，前7个字节与DATE一致
关键字：
SYSDATE：对应一个内置函数，返回一个表示当前系统时间的DATE类型值
DATE默认只显示'DD-MON-RR'
SYSTIMEATAMP：同样的，返回的是表示当前系统时间的时间戳类型的值
select sysdate from dual;
select systimestamp from dual;

TO_DATE函数
按照给定的日期格式将字符串解析为DATE类型值
SELECT TO_DATE('1992-9-1 23:22:11','yyyy-mm-dd hh24:mi:ss') from dual;
SELECT TO_DATE('1992年9月1日 23:22:11','yyyy"年"mm"月"dd"日" hh24:mi:ss') from dual;

DATE是可以比较大小的，越晚的越大
查看82年以后入职的员工？
select ename,hiredate from emp_xwbing where hiredate>to_date('1982-1-1','yyyy-mm-dd');

DATE之间可以做减法，差为相差的天数
select  ename,sysdate-hiredate from emp_xwbing;
select trunc(sysdate-to_date('1990-5-11','yyyy-mm-dd')) from dual;

DATE可以和一个数字进行加减运算，相当与加减了指定的天数，返回值为对应的日期
7天后是那天？
SELECT SYSDATE+7 FROM DUAL;

TO_CHAR
常用于转换日期，可以将日期按照指定的日期格式转换为字符串
SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') FROM DUAL;
SELECT TO_DATE('1992-9-1 23:22:11','RR-mm-dd hh24:mi:ss') from dual;
SELECT TO_CHAR(TO_DATE('99-09-01','RR-MM-DD'),'YYYY-MM-DD') FROM DUAL;

LAST_DAY(date) 该函数返回给定日期所在月的最后一天
查看当月月底？
SELECT LAST_DAY(SYSDATE) FROM DUAL;
查看每个员工入职所在月月底？
SELECT ENAME,LAST_DAY(HIREDATE) FROM EMP_XWBING;

ADD_MONTHS(date,i) 返回日期加上i个月后的日期值
查看每个员工入职20周年的纪念日？
SELECT ENAME,ADD_MONTHS(HIREDATE,12*20) FROM EMP_XWBING;

MONTHS_BETWEEN(DATE1,DATE2) 计算两个日期之间相差的月，计算方式是使用date1-date2的结果换算的
查看每个员工入职几个月？
SELECT ENAME,TRUNC(MONTHS_BETWEEN(SYSDATE,HIREDATE)) FROM EMP_XWBING;

NEXT_DAY(DATE,I)
返回给定日期之后一周内的周几,1代表星期天
SELECT NEXT_DAY(SYSDATE,1) FROM DUAL;

LEAST，GREATEST  求最大值和最小值， 这组函数的参数不限数量，两个以上即可
SELECT LEAST(SYSDATE ,TO_DATE('2008-2-2','YYYY-MM-DD')) FROM DUAL;
查看82年以后入职员工的入职日期，若是82年以前的，则显示为：1982-01-01?
SELECT ENAME,GREATEST(HIREDATE,TO_DATE('1982-01-01','YYYY-MM-DD')) FROM EMP_XWBING;

EXTRACT(date from datetime)提取指定日期指定时间分量的值
SELECT EXTRACT(YEAR FROM SYSDATE) FROM DUAL;
查看81年入职的员工？
SELECT ENAME,HIREDATE FROM EMP_XWBING WHERE EXTRACT(YEAR FROM HIREDATE)=1981;

CREATE TABLE STUDENT_XWBING(ID NUMBER(4),NAME CHAR(20),GENDER CHAR(1));
INSERT INTO STUDENT_XWBING VALUES(1000,'李莫愁','f');
insert into student_xwbing values(1001,'林平之',null);
insert into student_xwbing(id,name) values(1002,'张无忌');
select *from student_xwbing;
update student_xwbing set gender='m' where gender is null;
update student_xwbing set gender=null where gender='m';


NULL的运算
null与字符串连接等于什么都没做
null与数字运算结果还是null
查看每个员工的收入？
SELECT ENAME,SAL,COMM,SAL+COMM FROM EMP_XWBING;

NVL(P1,P1) 若p1为null，函数返回p2. 若不为null，函数返回p1自身。所以该函数的作用是将null替代换为非null值
查看每个员工的收入？
SELECT ENAME,SAL,COMM,SAL+nvl(COMM,0) FROM EMP_XWBING;
NVL2(P1,P2,P3) 若p1不为null值为p2，p1为null值为p3
查看每个员工的奖金情况？
SELECT ENAME,SAL,nvl2(COMM,'有奖金','无奖金') FROM EMP_XWBING;


