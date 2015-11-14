package life.xiaoyuan.xdubbo;

import java.lang.reflect.Method;

/**
 * Strategy interface for generating url for service methods.
 *
 * @author youmoo
 * @since 15/11/14 10:39
 */
public interface UrlGenerator {

    /**
     * Generate a url for a given method of a given bean class
     *
     * @param beanClass
     * @param beanMethod
     * @return
     */
    String generate(Class<?> beanClass, Method beanMethod);
}
