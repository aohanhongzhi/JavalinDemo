package hxy.javalin.JavalinDemo.handler;

import hxy.javalin.JavalinDemo.dao.UserDao;
import hxy.javalin.JavalinDemo.dao.model.UserModel;
import hxy.javalin.JavalinDemo.entity.BaseResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;

/**
 * @author eric
 * @description https://www.baeldung.com/javalin-rest-microservices
 * @date 2023/5/31
 */
public class UserController {

    /**
     * 这种写法确实很特别，不推荐，但是推荐使用下面的方式
     * 这个lambda也太强了，相当于，先把接口实例化，然后赋值给post变量了
     */
    @Deprecated
    public static Handler createUser = ctx -> {
        UserModel userModel = ctx.bodyAsClass(UserModel.class);
        int i = UserDao.saveUser(userModel);
        ctx.json(BaseResponse.success("新增结果" + i));
    };

    /**
     * 推荐使用。
     * 这里相当于是对 io.javalin.http.Handler#handle 的具体实现。重点就是：@FunctionalInterface
     *
     * @param ctx
     * @link https://javalin.io/tutorials/auth-example
     * @see io.javalin.http.Handler#handle(io.javalin.http.Context)
     */
    public static void deleteUser(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get();
        int i = UserDao.deleteUser(id);
        ctx.json(BaseResponse.success("删除结果" + i));
    }


    public static void listUsers(Context ctx) {
        List<UserModel> users = UserDao.listUsers();
        ctx.json(BaseResponse.success(users));
    }

    public static void updateUser(Context ctx) {
        UserModel userModel = ctx.bodyAsClass(UserModel.class);
        int i = UserDao.updateUser(userModel);
        ctx.json(BaseResponse.success("更新结果" + i));
    }

}
