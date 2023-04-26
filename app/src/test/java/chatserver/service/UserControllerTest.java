package chatserver.service;

import chatserver.dao.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserService users;

    @Test
    void findByPhone() {
        String phone = "1234567";
        User byPhone = users.findByPhone(phone);
        System.out.println(byPhone);

        User byPhone2 = users.findByPhone(phone);
        System.out.println(byPhone2);
    }
}