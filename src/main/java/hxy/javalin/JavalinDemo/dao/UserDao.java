package hxy.javalin.JavalinDemo.dao;

import hxy.javalin.JavalinDemo.dao.model.UserModel;
import org.rex.DB;
import org.rex.db.Ps;
import org.rex.db.exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
            // FIXME: 由于 rexdb框架没有被维护了，导致代码出错。所以推荐使用 ebean
            int save = DB.update(sql, userModel);
            return save;
        } catch (DBException e) {
            log.error("数据库插入错误", e);
        }
        return 0;
    }

    public static List<UserModel> listUsers() {
        String sql = "select * from user_model";
        try {
            List<UserModel> list = DB.getList(sql, UserModel.class);
            return list;
        } catch (DBException e) {
            log.error("数据库查询错误{}", e);
        }
        return null;
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

    /**
     * rexdb在update这块确实还是比较原始，不是很灵活
     *
     * @param userModel
     * @return
     */
    public static int updateUser(UserModel userModel) {

        boolean canUpdate = false;

        String sql = "update user_model ";

//         这里可以用反射用户，但是性能不好
        boolean dot = false;
        if (userModel.getName() != null) {
            sql = sql + " set name = #{name} ";
            dot = true;
            canUpdate = true;
        }
        if (userModel.getAge() != null) {
            if (dot) {
                sql = sql + " , ";
            } else {
                sql = sql + " set ";
            }
            sql = sql + "  age =  #{age} ";
            canUpdate = true;
        }

        if (!canUpdate) {
            return 0;
        }

        sql = sql + " where id = #{id} ";

        try {
            int save = DB.update(sql, userModel);
            return save;
        } catch (DBException e) {
            log.error("数据库插入错误", e);
        }
        return 0;
    }
}
