package hxy.javalin.JavalinDemo.handler;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UserHandler implements Handler {

    Logger logger = LoggerFactory.getLogger(UserHandler.class);

    @Override
    public void handle(@NotNull Context context) throws Exception {
        logger.info("UserHandler用户处理handler");

        Map user = new HashMap();
        user.put("name","eric");
        user.put("coutry","chinae");

        context.json(user);
    }
}
