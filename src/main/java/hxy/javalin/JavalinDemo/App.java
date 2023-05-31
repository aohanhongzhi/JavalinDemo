package hxy.javalin.JavalinDemo;

import hxy.javalin.JavalinDemo.entity.BaseResponse;
import hxy.javalin.JavalinDemo.handler.UserController;
import hxy.javalin.JavalinDemo.handler.UserGetHandler;
import io.javalin.Javalin;
import io.javalin.config.SizeUnit;
import io.javalin.http.HttpStatus;
import io.javalin.http.sse.SseClient;
import io.javalin.json.JavalinJackson;
import io.javalin.util.JavalinLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.put;

/**
 * Hello world!
 */
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "text/plain; charset=utf-8"; // 解决 ctx#result 返回中文乱码。
            config.jetty.multipartConfig.maxFileSize(100, SizeUnit.MB); // the maximum individual file size allowed
            config.routing.ignoreTrailingSlashes = true; // treat '/path' and '/path/' as the same path
            config.routing.treatMultipleSlashesAsSingleSlash = true; // treat '/path//subpath' and '/path/subpath' as
            // the same path
            config.jsonMapper(new JavalinJackson().updateMapper(mapper -> {
                // mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // null
                // 值不序列化给前端，优化数据传输
            }));
        }).start("0.0.0.0", 7070);
        app.get("/", ctx -> ctx.result("Hello World！--Javalin"));

        app.get("/hello/{name}", ctx -> {
            String name = ctx.pathParam("name");
            log.info("name is {}", name);

            // FIXME : result乱码，但是json不乱码
            Charset charset = ctx.responseCharset();
            log.info("charset is {}", charset);
            String s = ctx.characterEncoding();
            log.info("characterEncoding is {}", s);

            String characterEncoding = ctx.res().getCharacterEncoding();
            Charset defaultCharset = Charset.defaultCharset();
            log.info("characterEncoding is {} and defaultCharset is {}", characterEncoding, defaultCharset);
            // 乱码原因是字符集解析错误。 https://github.com/javalin/javalin/issues/1899
            // ctx.res().setContentType("text/plain; charset=utf-8");

            ctx.result("Hello: " + name);
        });

        app.get("/json", ctx -> {
            String name = ctx.queryParamAsClass("name", String.class).getOrDefault("gus"); // validate value
            log.info("name is {}", name);
            // https://github.com/javalin/javalin/issues/1392
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("null", null);
            ctx.json(BaseResponse.success(map));
        });

        // 这个写法太麻烦，不推荐
        app.get("/user", new UserGetHandler());
        // 下面这种写法很奇特，也不推荐
        app.post("/user", UserController.createUser);

        app.routes(() -> {
            path("/user", () -> {
                delete(UserController::deleteUser);
                put(UserController::updateUser);
                path("list", () -> {
                    get(UserController::listUsers);
                });
            });
        });

        Queue<SseClient> clients = new ConcurrentLinkedQueue<SseClient>();

        app.sse("/sse", client -> {
            client.keepAlive();
            client.onClose(() -> clients.remove(client));
            clients.add(client);
        });

        app.error(404, ctx -> {
            String requestURI = ctx.req().getRequestURI();
            ctx.json(BaseResponse.notFound("path:" + requestURI));
        });

        // HTTP exceptions
        app.exception(NullPointerException.class, (e, ctx) -> {
            // handle nullpointers here
            log.error("NullPointerException {}", e.getMessage(), e);
        });

        app.exception(Exception.class, (e, ctx) -> {
            // handle general exceptions here
            // will not trigger if more specific exception-mapper found
            log.error("Exception {}", e.getMessage(), e);
        });

        app.exception(FileNotFoundException.class, (e, ctx) -> {
            ctx.status(404);
        }).error(404, ctx -> {
            String requestURI = ctx.req().getRequestURI();
            ctx.result("Generic 404 message FileNotFoundException , current path: " + requestURI);
        });

        Javalin.create(cfg -> {
            cfg.pvt.javaLangErrorHandler((res, error) -> {
                res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getCode());
                JavalinLogger.error("Exception occurred while servicing http-request", error);
            });
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down...");
            clients.forEach(SseClient::close);
            app.stop();
        }));

    }

}
