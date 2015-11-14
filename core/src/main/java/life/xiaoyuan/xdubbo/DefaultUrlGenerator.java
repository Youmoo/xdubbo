package life.xiaoyuan.xdubbo;

import java.lang.reflect.Method;

/**
 * Default implementation of the {@link UrlGenerator} interface
 *
 * @author youmoo
 * @since 15/11/14 10:42
 */
public class DefaultUrlGenerator implements UrlGenerator {

    @Override
    public String generate(Class<?> beanClass, Method beanMethod) {

        String leftPart = transform(beanClass.getSimpleName());
        String rightPart = transform(beanMethod.getName());
        return leftPart + '/' + rightPart;
    }

    private String transform(String name) {
        return name.length() == 1 ? name.toLowerCase() : name.substring(0, 1).toLowerCase() + name.substring(1);
    }
}
