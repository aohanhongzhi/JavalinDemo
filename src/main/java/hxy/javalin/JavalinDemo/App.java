package hxy.javalin.JavalinDemo;

import hxy.javalin.JavalinDemo.handler.UserHandler;
import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Hello World!" );
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.result("Hello World"));
        
        app.get("/hello/:name", ctx -> {
            ctx.result("Hello: " + ctx.pathParam("name"));
        });

        app.get("/user",new UserHandler());
    }
}
