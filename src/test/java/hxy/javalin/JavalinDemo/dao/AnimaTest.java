//package hxy.javalin.JavalinDemo.dao;
//
//import com.hellokaton.anima.Anima;
//
//import static com.hellokaton.anima.Anima.select;
//
//import hxy.javalin.JavalinDemo.dao.model.UserModel;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//
///**
// * @author eric
// * @description
// * @date 2023/6/7
// */
//class AnimaTest {
//
//
//    private static final Logger log = LoggerFactory.getLogger(AnimaTest.class);
//
//
////CREATE TABLE `user_models`
////(
////    `id`   IDENTITY PRIMARY KEY,
////    `name` varchar(50) NOT NULL,
////    `age`  int(11)
////)
//
//    /**
//     * 不支持jdk17啦，底层框架不适配了
//     */
//    @Test
//    public void testA() {
//        // SQLite
//        Anima.open("jdbc:sqlite:./javalin.db");
//
//        UserModel userModel1 = new UserModel();
//        userModel1.setName("user");
//        userModel1.setAge(11);
//
////        Anima.save(userModel1);
////
////        List<UserModel> userModels = select().from(UserModel.class).all();
////
////        log.info("users: {}", userModels);
//
//    }
//}