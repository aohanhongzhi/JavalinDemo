package hxy.javalin.JavalinDemo;

import hxy.javalin.JavalinDemo.handler.UserGetHandler;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 */
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);
        app.get("/", ctx -> ctx.result("Hello World！--Javalin"));

        app.get("/hello/{name}", ctx -> {
            String name = ctx.pathParam("name");
            log.info("name is {}", name);
            ctx.result("Hello: " + name);
        });

        app.get("/user", new UserGetHandler());
    }
}
