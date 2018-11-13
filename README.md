sonar自定义规则插件开发(基于阿里JAVA开发手册)

--name 
code
1.抽象类命名使用 Abstract 或 Base 开头   次要
2.异常类命名使用Exception结尾 次要
3.中括号是数组类型的一部分，数组定义如下：String[] args (重复)
4.类名使用UpperCamelCase风格，必须遵从驼峰形式，但以下情形例外：（领域模型的相关命名）DO / BO / DTO / VO / DAO （重复）
5.方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase，必须遵从驼峰形式 主要
6.常量命名应该全部大写，单词间用下划线隔开，力求语义表达完整清楚  <修> 主要
7.所有编程相关的命名均不能以下划线或美元符号开始    次要
8.POJO 类中布尔类型的变量，都不要加 is 前缀     次要 
9.包命名检查:包名统一使用小写，点分隔符之间有且仅有一个自然语义的英语单词  次要
10.测试类命名检查:测试类命名以它要测试的类名开始，以 Test 结尾   主要
11.对于 Service 和 DAO 类，暴露出来的服务一定是接口，内部的实现类用 Impl 的后缀与接口区别  主要

--comment
code
12.所有的类都必须添加创建者信息   提示
13.类、类属性、类方法的注释必须使用javadoc规范，使用/**内容*/格式，不得使用//xxx方式和/*xxx*/方式   提示
14.所有的枚举类型字段必须要有注释，说明每个数据项的用途  次要

--concurrent
漏洞 
15.线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式,规避资源耗尽的风险 主要
16.线程资源必须通过线程池提供，不允许在应用中自行显式创建线程  严重 阻断

--constant
code
17.long类型变量赋值检查:long 或者 Long 初始赋值时,使用大写的 L,不能是小写的 l,小写容易跟数字 1 混淆,造成误解   (部分重复) 主要
18.不允许任何魔法值 （ 即未经预先定义的常量 ） 直接出现在代码中  <待修改>   (重复)

--exception
bug 
19.不能在finally块中使用return,finally块中的return返回后方法结束执行,不会再执行try块中的return语句 主要
bug
20.返回类型为基本数据类型，return包装数据类型的对象时，自动拆箱有可能产生NPE 主要

--flowcontrol
21.除常用方法（如getXxx/isXxx）等外，不要在条件判断中执行复杂的语句，将复杂逻辑判断的结果赋值给一个有意义的布尔变量，以提高可读性   <修> code 提示 
22.在if/else/for/while/do语句中,代码块应该写在花括号里,否则可能会影响代码执行  (修)  主要
23.在一个switch块内，都必须包含一个default语句并且放在最后，即使它什么代码也没有 主要
24.在一个switch块内，每个case通过break/return等来终止,要么通过注释来说明 次要

--oop
25.不能使用过时的类或方法  code 提示
26.使用集合转数组的方法,必须使用集合的toArray(T[] array),传入的是类型完全一样的数组,大小就是list.size()  (重复)
27.所有的覆写方法，必须加@Override注解  (重复)
28.所有的包装类对象之间值的比较，全部使用equals方法比较  code 次要
29.获取当前毫秒数：System.currentTimeMillis(); 而不是new Date().getTime() code 提示
30.Math.random()返回是double类型,注意取值的范围[0,1),如果想获取整数类型的随机数，不要将x放大10的若干倍然后取整,直接使用Random对象的nextInt或者nextLong方法  (重复)

--set
31.集合初始化时，指定集合初始值大小 code 次要
32.ArrayList的subList结果不可强转成ArrayList，否则会抛出ClassCastException异常  bug 阻断

--other
33.避免用Apache Beanutils进行属性的copy    bug 主要     
34.iBATIS自带的queryForList(String statementName,int start,int size)不推荐使用   bug 主要
35.正确使用正则表达式,利用好其预编译功能,可以有效加快正则匹配速度     code 次要
36.所有的抽象方法（包括接口中的方法）必须要用javadoc注释、除了返回值、参数、异常说明外，还必须指出该方法做什么事情，实现什么功能  (修) code 提示 
37.避免通过一个类的对象引用访问此类的静态变量或静态方法，无谓增加编译器解析成本，直接用类名来访问即可  （重复）code 次要
38.Object的equals方法容易抛空指针异常，应使用常量或确定有值的对象来调用equals <修> bug 次要
 