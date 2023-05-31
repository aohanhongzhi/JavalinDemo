package hxy.javalin.JavalinDemo.handler;

import io.javalin.http.Handler;

/**
 * @author eric
 * @description https://www.baeldung.com/javalin-rest-microservices
 * @date 2023/5/31
 */
public class UserController {

    public static Handler createUser = ctx -> {
        ctx.json("这个lambda也太强了，相当于，先把接口实例化，然后赋值给post变量了");
    };
}
