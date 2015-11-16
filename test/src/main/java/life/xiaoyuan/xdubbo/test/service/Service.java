package life.xiaoyuan.xdubbo.test.service;

import life.xiaoyuan.xdubbo.test.domain.User;
import org.springframework.stereotype.Component;

/**
 * Sample service
 *
 * @author youmoo
 * @since 15/11/13 12:52
 */
@Component
public class Service {

    public User find(Integer id) {
        User user = new User();
        user.setId(id);
        user.setUsername("youmoo");
        return user;
    }

    public User update(User user) {
        return user;
    }

    String findAll() {
        return "findAll";
    }

    public Object twoArgs(Integer arg1, User arg2) {
        return new Object[]{arg1, arg2};
    }

}
