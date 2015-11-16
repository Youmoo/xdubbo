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

    @RequestMapping("noArg")
    public String primitive() {

        return "no arg";
    }

    @RequestMapping("primitive")
    public Integer primitive(Integer num) {

        return num;
    }

    @RequestMapping("complex")
    public User complex(@RequestBody User user) {

        return user;
    }
}
