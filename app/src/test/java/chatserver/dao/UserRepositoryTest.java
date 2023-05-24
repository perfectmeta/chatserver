package chatserver.dao;

import chatserver.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository users;

    @Test
    void findById() {
        User u = users.findByUserId(12346789L);
        Assertions.assertNull(u);
    }

    @Test
    void findById2() {
        String phone = "1234567";
        User byPhone = users.findByPhone(phone);
        if (byPhone != null) {
            users.delete(byPhone);
        }

        User u = new User();
        u.setEmail("ab@a.com");
        u.setPhone(phone);
        u.setNickName("nick");
        User ret = users.save(u);
        Assertions.assertNotNull(ret.getUserId());

        User u2 = users.findByUserId(ret.getUserId());
        Assertions.assertTrue(u2 != null && u2.getNickName().equals(u.getNickName()));
    }


    @Test
    void findByPhone() {
        String phone = "1234567";
        User byPhone = users.findByPhone(phone);
        System.out.println(byPhone);

        User byPhone2 = users.findByPhone(phone);
        System.out.println(byPhone2);
    }

    @Test
    void findByEmail() {
    }
}