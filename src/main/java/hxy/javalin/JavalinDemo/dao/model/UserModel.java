package hxy.javalin.JavalinDemo.dao.model;

/**
 * @author eric
 * @program JavalinDemo
 * @description
 * @date 2022/1/26
 */
public class UserModel {

    Integer id;
    String name;
    Integer age;

    @Override
    public String toString() {
        return "name:" + name + ",age:" + age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
