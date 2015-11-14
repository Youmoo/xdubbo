package life.xiaoyuan.xdubbo.test.controller;

import life.xiaoyuan.xdubbo.test.domain.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author youmoo
 * @since 15/11/13 12:51
 */
@RestController
@RequestMapping("controller")
public class Controller {

    @RequestMapping("hello")
    public String hello() {

        return "hello world.";
    }

    @RequestMapping("user")
    public User hello(@RequestBody User user) {
        return user;
    }
}
