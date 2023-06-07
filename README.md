JavalinDemo
===

# javalin

官网：https://javalin.io/

https://github.com/javalin/javalin

Javalin是一种 **编程式** 的micro web framework。javalin的活跃度要比[sparkjava](https://github.com/perwendel/spark)
更好。但是[sparkjava](https://github.com/perwendel/spark)的github star数更多。

[SparkJava and Javalin comparison](https://javalin.io/comparisons/sparkjava)：`javalin`就是从`sparkjava`fork出来的。

> 其实这种编程式的框架，可以尝试用kotlin来编写下更好。

## 开发

### 热重启

http://blog.houxiaoyi.cn/#/src/Java/热加载

```shell
-XX:+AllowEnhancedClassRedefinition -XX:HotswapAgent=fatjar
```

### jdk17运行添加VM参数

```shell
--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED
```

## 打包

```shell
mvn clean package -Dmaven.test.skip=true
```
### 运行jar

```shell
java --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED -jar target/JavalinDemo-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

# 推荐使用的ORM —— ebean

https://ebean.io/

https://github.com/ebean-orm/ebean

已经使用的项目

https://github.com/aohanhongzhi/JavalinDemo-Kotlin

> 因为rexdb不咋更新了，此外rexdb的原生sql在处理update的时候比较麻烦。

下面这个尝试后，已经太远了，不支持jdk17啦，直接放弃。

https://github.com/hellokaton/anima

```java
com.hellokaton.anima.exception.AnimaException
	at com.hellokaton.anima.utils.AnimaUtils.toColumnValues(AnimaUtils.java:104)
	at com.hellokaton.anima.core.AnimaQuery.save(AnimaQuery.java:1229)
	at com.hellokaton.anima.Model.save(Model.java:28)
	at com.hellokaton.anima.Anima.save(Anima.java:395)
	at hxy.javalin.JavalinDemo.dao.AnimaTest.testA(AnimaTest.java:42)
Caused by: java.lang.IllegalArgumentException: Class versions V1_5 or less must use F_NEW frames.
	at org.objectweb.asm.MethodWriter.visitFrame(MethodWriter.java:780)
	at com.hellokaton.blade.asm.MethodAccess.get(MethodAccess.java:173)
	at com.hellokaton.anima.utils.AnimaUtils.lambda$invokeMethod$0(AnimaUtils.java:127)
	at com.hellokaton.anima.utils.AnimaUtils.invokeMethod(AnimaUtils.java:125)
	at com.hellokaton.anima.utils.AnimaUtils.toColumnValues(AnimaUtils.java:95)
```

> 主要看update的映射是否简单

### 2023/05/31

Jdk17支持 rexdb

jvm参数

```shell
--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED 
```

https://blog.csdn.net/qq_41611125/article/details/126635762

### 2023/05/25

中文乱码
https://github.com/javalin/javalin/issues/1899

### 2022/01/26

* Handler的方法不需要区分 http method。所以这里就是接收参数和处理业务即可。也不需要额外再新建Service层了。可以理解Handler本身就是Service层。这里和
  tornado有相似之处，也有不同之处。相似在都是一种用Handler的注册形式，不同在于两者注册的时候，tornado没有指定http
  method，需要再handler里面用方法区分，无疑增加了handler的复杂度
  ，而javalin在注册时候明确指定handler是啥http method，因此handler可以直接作为Service层来处理任务。Handler里面就可以直接使用rexdb来操作数据库。
* 这个micro框架就不是用来处理重量级的事情的。适合做个啥报表查询之类的。不需要特别广泛的生态啥的。

 层                   | 实现方式                    
---------------------|-------------------------
 Handler（Service逻辑层） | javalin                 
 DAO                 | rexdb (很可惜，这个框架活跃度比较低了) 


### 初次想法

这种没有采用的依赖注入形式的框架，虽然效率高，~~但是怎么和Spring的控制反转与依赖注入结合呢~~ 此外数据库DAO层怎么结合呢？

这种简单的极简框架，没必要和Spring结合，直接静态方法即可，或者直接免去Service层，直接Controller+DAO即可。

如果非要用依赖注入这一套，可以参考Google Guice，不建议使用Spring，还是太重了。

https://javalin.io/tutorials/javalin-java-10-google-guice

下面解决方案可以参考

~~Service层使用静态方法管理，Controller直接调用，但是更加底层的DAO怎么处理？~~

有一个ORM框架，可以解决这个问题，就是 [rexdb](https://rexdb.gitee.io/)，也是静态方法直接调用，只是rexdb，相对于mybatis来说可能自己手写sql语句比较多。

 层           | 实现方式         
-------------|--------------
 Contoller   | javalin      
 ~~Service~~ 推荐 [Google Guice](https://javalin.io/tutorials/javalin-java-10-google-guice) | ~~静态方法调用即可~~ 
 DAO         | rexdb        

## notes

框架提示都非常不错。

![](./asset/img/slf4j.png)
![](./asset/img/缺少json.png)
