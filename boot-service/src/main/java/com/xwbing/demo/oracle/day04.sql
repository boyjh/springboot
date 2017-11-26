子查询
子查询是一条查询语句，其嵌套在其他sql语句中，作用是为外层的sql语句提供数据

和james相同部门的员工？
SELECT ename,deptno FROM emp_xwbing WHERE deptno=(SELECT deptno FROM emp_xwbing WHERE ename='JAMES');

子查询除了常用dql之外，也可以子啊ddl与dml中使用
在ddl中应用：
使用子查询的结果快速创建一张表,若子查询中查询的内容是函数或表达式，那么该字段必须给别名。
CREATE TABLE myemployee
AS
SELECT 
 e.empno,e.ename,e.job,e.sal*12 sal,
 e.deptno,d.dname,d.loc
FROM 
 emp e,dept d
WHERE
 e.deptno=d.deptno


dml中使用子查询：
删除和james相同部门的所有员工？
DELETE FROM emp_xwbing WHERE deptno=(SELECT deptno FROM emp_xwbing WHERE ename='JAMES');
UPDATE emp_xwbing SET SAL=SAL*1.1 WHERE deptno=(SELECT deptno FROM emp_xwbing WHERE ename='JAMES');
SELECT *FROM EMP_XWBING;

子查询根据查询结果集不同通常分为：
单行单列：常用在WHERE中（配合=，>,<等）
多行单列：常用在WHERE中（配合IN，ANY，ALL）
多行多列：常用在WHERE中作为表看待

查看比clerk和salesman部门工资都高的额员工？
SELECT ename,sal FROM emp_xwbing WHERE sal>ALL(SELECT deptno FROM emp_xwbing WHERE job='SALESMAN' OR job='CLERK');
查看和clerk相同部门的其他职位员工？
SELECT ename FROM emp_xwbing WHERE deptno  in (SELECT deptno FROM emp_xwbing WHERE JOB='CLERK');

EXISTS 关键字
用在where中，其后要根一个子查询，作用是若子查询至少可以查询出一条记录，那么exisets表达式返回真，NOT EXISTS则起到相反的作用，查不到数据则返回真
产看有员工的部门？
SELECT d.deptno,d.dname,d.loc FROM dept_xwbing d WHERE EXISTS(SELECT *FROM EMP_XWBING  e WHERE e.deptno=d.deptno);
没有下属的员工？
SELECT n.ENAME FROM EMP_XWBING n WHERE NOT EXISTS(SELECT *FROM EMP_XWBING m WHERE N.EMPNO=M.MGR);
查询列出最低薪水高于部门30的最低薪水的部门的最低薪水？
select min(sal),deptno from emp_xwbing group by deptno having min(sal)>(select min(sal) from emp_xwbing where deptno=30 );

子查询在from部分：
查看高于自己所在部门平均工资的员工信息？
select e.ename ,e.sal,e.deptno from emp_xwbing e,(select avg(sal) avg_sal,deptno from emp_xwbing group by deptno) t where e.deptno=t.deptno AND E.SAL>T.AVG_SAL;
子查询在select部分：可以认为是外连接的另一种表现
select e.ename,e.sal,(select d.dname from dept_xwbing d where e.deptno=d.deptno ) dname from emp_xwbing e;




分页查询：orcal
通常一个查询语句查询的数据来量过大时，都会使用分页机制。分页就是将数据分批查询出来。一次只查询部分内容，这样的好处可以减少系统响应时间，减少系统资源开销
分页由于在标准sql语句中没有定义，所以不同的数据库语法不相同（方言）
ORACLE 中使用 ROWNUM 这个伪列来实现分页。
rownum，该列不存在于数据库任何表中，但是任何表都可以查询该列，该列在结果集中的值是每条记录的行号，行号从1开始。
编号是在查询的过程中进行的，只要可以从表中查询出一条数据，那么该记录的rownum字段值即为这条记录的行号
SELECT rownum, ename,job from EMP_XWBING WHERE ROWNUM BETWEEN 2 AND 10 ;
在使用rownum对结果集编号的查询过程中不要使用rownum做>1以上数字的判断，否则查询不到任何数据。先编号再查询。
select * from(SELECT rownum rn, ename,job from EMP_XWBING) where rn between 6 and 10;

若对查询内容有排序需求时，要先进行排序操作。
取公司工资排名的6-10？
select * 
from(SELECT rownum rn,t.* 
      from(select ename,sal,job 
            from emp_xwbing  
            order by sal desc)t 
      where rownum<=10)
where rn >5;

换算范围值：
PageSize：每页显示的条数
Page:页数
start=(page-1)*pagesize+1
end=pagesize*page

DECODE(expr,search1,result1,[search2,result2.......][,default]) 无 default 返回 null
可以实现分支效果
SELECT ename,job,sal,DECODE(job,'MANAGER',sal*1.2,'ANALYST',sal*1.1,'SALESMAN',sal*1.05,sal) bonus FROM emp_xwbing;
统计人数，将职位是‘analyst’和‘manager’看作一组，其他职位看作另一组 分别统计两组人数？
select count(*) ,decode(job,'ANALYST','VIP','MANAGER','VIP','HTHER')   FROM emp_xwbing GROUP BY decode(job,'ANALYST','VIP','MANAGER','VIP','HTHER');
自定义排序
select deptno,dname,loc from dept_xwbing order by decode(dname,'OPERATIONS',1,'ACCOUNTING',2,'SALES',3);

排序函数
ROW_NUMBER() OVER(PARTITION BY col1 ORDER BY col2)
排序函数可以将结果集按照指定的分段分组，然后在组内按照指定的分段排序，并为组内每条记录生成一个编号
ROW_NUMBER:组内连续且唯一的数字
公司每个部门的工资排名？
select ename,sal,deptno,row_number() over(partition by deptno order by sal desc) rank from emp_xwbing;
RANK:组内不连续不唯一数字
RANK() OVER(PARTITION BY col1 ORDER BY col2)
select ename,sal,deptno,rank() over(partition by deptno order by sal desc) rank from emp_xwbing;
DENSE_RANK:组内连续但不唯一数字
select ename,sal,deptno,dense_rank() over(partition by deptno order by sal desc) rank from emp_xwbing;



create table sales_xwbing(year_id number not null,month_id number not null,day_id number not null,sales_value number(10,2) not null);
insert into sales_xwbing
select trunc(dbms_random.value(2010,2012))as year_id,
       trunc(dbms_random.value(1,13))as month_id,
       trunc(dbms_random.value(1,32))as day_id,
      round(dbms_random.value(1,100),2)as sales_value
from dual
connect by level <=1000;



集合操作
union,union all,intersect,minus

union:并集
select ename ,job,sal from emp_xwbing where job='MANAGER' 
UNION  
select ename,job,sal from emp_xwbing where sal>2500; 

intersect:交集
select ename ,job,sal from emp_xwbing where job='MANAGER' 
intersect 
select ename,job,sal from emp_xwbing where sal>2500;

minus:差集
select ename ,job,sal from emp_xwbing where job='MANAGER' 
minus
select ename,job,sal from emp_xwbing where sal>2500;


查看每天的营业额？
select year_id,month_id,day_id,sum(sales_value) from sales_xwbing group by year_id,month_id,day_id order by year_id,month_id,day_id;

高级分组函数
ROLLUP函数
rollup分组次数有指定的参数据决定，次数为参加个数+1次，而且分组原则是每个参数递减的形式，然后将这些分组的结果并在一个结果集中显示
group BY ROLLUP(a,b,c...)

GROUP BY ROLLUP(a,b,c)
等同于
GROUP BY a,b,c
union all
GROUP BY a,b
union all
GROUP BY a


查看每天，每月，每年，和总营业额？
select year_id,month_id,day_id,sum(sales_value) from sales_xwbing group by rollup(year_id,month_id,day_id);

CUBE 函数
CUBE 分组次数是n个参数的2的n次方，会将每种组合进行一次分组并将所有结果并在一个结果集中显示
select year_id,month_id,day_id,sum(sales_value) from sales_xwbing group by cube(year_id,month_id,day_id) order by year_id,month_id,day_id;

GROUPING SETS()
该分组函数允许按照指定的分组方式进行分组，然后将这些分组统计的结果并在一个结果集中显示函数的每一个参数，就是一种分组方式。
select year_id,month_id,day_id,sum(sales_value) from sales_xwbing group by grouping sets((year_id,month_id,day_id),(year_id,month_id)) order by year_id,month_id,day_id;
















