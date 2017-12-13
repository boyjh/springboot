select *from emp_xwbing;
select *from dept_xwbing;
select 子句中 可以对字段加别名，别名前加空格或as,若希望别名区分大小写或者包含空格，可以使用双引号将别名括起来
SELECT ENAME,SAL*12  SAL FROM EMP_XWBING;
select ename,sal*12 "s al"from emp_xwbing;

select *from emp_xwbing where deptno=20;
select *from emp_xwbing where job='CLERK';

查询条件：
<>不等于
select ename,sal from emp_xwbing where sal<2000;
select * from emp_xwbing where deptno<>10;
select * from emp_xwbing where hiredate<to_date('1981-1-1','yyyy-mm-dd');

AND OR 与和或 AND优先级高于or
select *from emp_xwbing where sal>1000 and job='CLERK';
SELECT *FROM emp_xwbing where sal>1000 OR job='CLERK';
select *from emp_xwbing where sal>1000 and (job='CLERK' OR JOB='SALESMAN');

LIKE 模糊查询
%：0到多个字符
_:表示单个字符
select ename from emp_xwbing where ename like '_A%';
select ename from  emp_xwbing where ename like '_L_E%';
SELECT ENAME FROM EMP_XWBING WHERE ENAME LIKE '%E_';

IN(list), NOT IN(list)
判断是否在列表中，常用在子查询中
SELECT *FROM EMP_XWBING WHERE JOB IN('CLERK','SALESMAN');

BETWEEN...AND...之间
select sal from emp_xwbing where sal between 1500 and 3000;

is null ,is not null

ANY,ALL  需要配合> >=  < <= 一个列表使用,常用于判断子查询的结果
select *from emp_xwbing where sal>all(2800,3000,1800);

select *from emp_xwbing where comm is not null and sal>1300;
select *from emp_xwbing where hiredate>to_date('1981-1-1','yyyy-mm-dd') and sal*12>30000;

DISTINCT 过滤重复,必须跟在select后面。可以对多列去重,去重原则是这些列的组合没有重复
select distinct job from emp_xwbing;
select distinct job,deptno from emp_xwbing;

排序：
order by子句，只能定义在select语句中的最后一个子句上。
排序结果集，可以按照指定的字段进行升序或将序排列
可以对多列进行排序,排序具有优先级，先按照第一个字段排序方式排序，当第一个字段有重复值时再按照第二个字段排序方式排序，以次类推
若排序字段里含有null，null被视为最大值
asc：升序，可以不写，默认为升序
desc：降序
select ename ,sal from emp_xwbing order by sal;
select ename ,sal,deptno from emp_xwbing order by deptno,sal desc;
select ename from emp_xwbing order by ename;
order by field(字段,'','');


聚合函数又称多行函数：用来统计结果的
max,min 统计最大值，最小值
查看最高工资？
select max(sal),min(sal) from emp_xwbing;

avg,sum 统计平均值与总和
查看平均工资和工资总和
select trunc(avg(sal)) avg,sum(sal) sum from emp_xwbing;

count 统计的是记录条数，而不关注具体该字段的取值,
查看公司员工人数？
select count(ename) from emp_xwbing;
统计表中记录数，常使用couont（*）
select count(*)from emp_xwbing;

所有聚合函数忽略null值
select avg(nvl(comm,0)),sum(comm) from emp_xwbing;

分组：
GROUP BY子句
GROUP BY子句允许将结果集按照给定的字段值相同的额记录看作一组，然后配合聚合函数对每组记录进行统计
查看每个部门的最高工资？
select deptno from emp_xwbing group by deptno;
当select中含有聚合函数时，凡不在聚合函数中的单独字段都必须出现在GROUP BY子句中
查看每个职位的平均工资和工资总和？
select avg(sal), sum(sal) from emp_xwbing group by job;
查看每个部门各有多少人？
select count(ename),deptno from emp_xwbing group by deptno;


查看每个部门的平均工资，前提是该部门平均工资高于2000？
select avg(sal),deptno from emp_xwbing where avg(sal)>2000 group by deptno;
where中不允许使用聚合函数作为过滤条件，原因在于过滤时机不同。where的过滤时机是在第一次从表中检索数据是添加过滤条件，用来确定那些数据可以被查出来，以确定结果集
HAVING子句：having必须跟在group by 子句之后，可以使用聚合函数作为过滤条件。使之可以对分组进行过滤，将满足条件的分组保留，不满足的去掉
select avg(sal),deptno from emp_xwbing group by deptno having avg(sal)>2000;



关联查询：
查询的结果集中字段来自多张表，联合多张表查询数据就是关联查询，在关联查询中需要在过滤条件中添加两张表中记录之间对应关系，这样的条件称为连接条件
n张表联合查询至少要有n-1个连接条件，不添加连接条件会产生迪卡尔积，除特殊情况下，该结果集通常是无意义的，并且由于数据量大，对系统资源消耗非常大。
查看每个员工的名字以及所在部门的名字？
select ename,dname from emp_xwbing,dept_xwbing where emp_xwbing.deptno=dept_xwbing.deptno;

在关联查询中的过滤条件应当与连接体条件同时成立
查看sales部门的员工信息？
select ename,dname from emp_xwbing,dept_xwbing where emp_xwbing.deptno=dept_xwbing.deptno and dept_xwbing.dname='SALES';

当查询过程中，出现字段在两张表中都有的情况时，必须明确该字段属于哪张表,表可以取别名
select e.ename,e.deptno,d.dname from emp_xwbing e,dept_xwbing d where e.deptno=d.deptno;

select  e.ename ,d.dname   from emp_xwbing e,dept_xwbing d where e.deptno=d.deptno and d.loc='NEW YORK';
查看平均工资高于2000的部门所在地？
select avg(e.sal), d.loc ,d.dname   from emp_xwbing e,dept_xwbing d where e.deptno=d.deptno group by d.dname,d.loc having avg(e.sal)>2000;


内连接：关联查询的另一种写法
查看sales部门员工信息？
select e.ename,d.dname from emp_xwbing e JOIN dept_xwbing d ON e.deptno=d.deptno where d.Dname='SALES'; 

外连接
外连接在进行关联查询时可以将不满足条件的记录也查询出来
外连接分为：左外连接，右外连接，全外连接
左外连接：以join左侧表作为驱动表，该表中所有记录全部会列出来，那么当某条记录不满足连接条件时，该记录中来自右侧表中字段的值为null
SELECT e.ename,d.dname FROM emp_xwbing e LEFT JOIN dept_xwbing d ON e.deptno=d.deptno;
SELECT e.ename,d.dname FROM emp_xwbing e ,dept_xwbing d WHERE e.deptno=d.deptno(+);
SELECT e.ename,d.dname FROM emp_xwbing e LEFT|RIGHT|FULL JOIN dept_xwbing d ON e.deptno=d.deptno;

自连接
自连接指的是自己表中的记录对应自己表中的多条记录，建立的关联查询就是自连接
自连接设计用于解决数据相同，但是存在上下级关系的树状结构使用。
查看每个员工以及其上司的名字？
SELECT  E.ENAME ,M.ENAME  BOSS FROM EMP_XWBING E,EMP_XWBING M WHERE E.MGR=M.EMPNO(+);
JONES的手下？
SELECT  E.ENAME FROM EMP_XWBING E,EMP_XWBING M WHERE E.MGR=M.EMPNO(+) and m.ename='JONES';
查看每个领导有几个手下？
SELECT  count(e.mgr),m.ename FROM EMP_XWBING E,EMP_XWBING M WHERE E.MGR=M.EMPNO group by m.ename;
查看JONES的上司在哪个城市？
SELECT  d.loc  FROM EMP_XWBING E,EMP_XWBING M , dept_xwbing d WHERE E.MGR=M.EMPNO and m.deptno=d.deptno and e.ename='JONES' ;










