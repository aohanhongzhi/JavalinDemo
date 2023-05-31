package hxy.javalin.JavalinDemo.handler;

import hxy.javalin.JavalinDemo.dao.UserDao;
import hxy.javalin.JavalinDemo.dao.model.UserModel;
import hxy.javalin.JavalinDemo.entity.BaseResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里的方法不需要区分 http method。所以这里就是接收参数和处理业务即可。不需要再新建Service层了。
 * 这个micro框架就不是用来处理重量级的事情的。适合做个啥报表查询之类的。
 */
@Deprecated
public class UserGetHandler implements Handler {

    Logger logger = LoggerFactory.getLogger(UserGetHandler.class);

    /**
     * 如果本方法的行数太多，那么可以抽取方法放在这个类里。
     *
     * @param context
     * @throws Exception
     */
    @Override
    public void handle(@NotNull Context context) throws Exception {
        logger.info("UserHandler用户处理handler");

        String ip = context.ip();
        Map<String, List<String>> stringListMap = context.queryParamMap();

        logger.info("ip:{}查询参数{}", ip, stringListMap);

        String id = context.queryParam("id");
        UserModel user1 = null;
        if (id != null) {
            user1 = UserDao.getUser(Integer.parseInt(id));
        }
        if (user1 != null) {
            context.json(user1);
        } else {
            context.json(BaseResponse.success("用户不存在"));
        }
    }
}
