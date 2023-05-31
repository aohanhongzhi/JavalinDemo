package hxy.javalin.JavalinDemo.dao

import org.junit.jupiter.api.Test


class UserDaoTest {

    @Test
    fun `test user list`(){

        val listUsers = UserDao.listUsers()
        println(listUsers)

    }

}