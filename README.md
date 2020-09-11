# JavalinDemo
## javalin
#### 官网：https://javalin.io/

这种没有采用的依赖注入形式的框架，虽然效率高，但是怎么和Spring的控制反转与依赖注入结合呢？此外数据库DAO层怎么结合呢？

下面解决方案可以参考

Service层使用静态方法管理，Controller直接调用，但是更加底层的DAO怎么处理？

有一个ORM框架，可以解决这个问题，就是[rexdb](http://db.rex-soft.org/)，也是静态方法直接调用，只是rexdb，相对于mybatis来说可能自己手写sql语句比较多。

层 | 实现方式
--- | ---
Contoller | javalin
Service | 静态方法调用即可
DAO | rexdb 

## notes
![](./asset/img/slf4j.png)
![](./asset/img/缺少json.png)