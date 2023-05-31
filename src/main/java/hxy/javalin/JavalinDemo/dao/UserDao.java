package hxy.javalin.JavalinDemo.dao;

import hxy.javalin.JavalinDemo.dao.model.UserModel;
import org.rex.DB;
import org.rex.db.Ps;
import org.rex.db.exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author eric
 * @program JavalinDemo
 * @description
 * @date 2022/1/26
 */
public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    public static int saveUser(UserModel userModel) {
        String sql = "insert into user_model (id,name,age) values (#{id}, #{name}, #{age})";
        try {
            int save = DB.update(sql, userModel);
            return save;
        } catch (DBException e) {
            log.error("数据库插入错误", e);
        }
        return 0;
    }

    public static UserModel getUser(Integer id) {
        String sql = "select * from user_model where id = ?";
        Ps ps = new Ps();
        ps.add(id);
        try {
            UserModel userModel = DB.get(sql, ps, UserModel.class);
            return userModel;
        } catch (DBException e) {
            log.error("数据库查询错误{}", e);
        }
        return null;
    }

    public static int deleteUser(Integer id) {
        String sql = "delete from user_model where id = ?";
        Ps ps = new Ps();
        ps.add(id);
        try {
            int update = DB.update(sql, ps);
            return update;
        } catch (DBException e) {
            log.error("数据库删除错误{}", e);
        }
        return 0;
    }
}
