package life.xiaoyuan.xdubbo;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author youmoo
 * @since 15/11/14 13:14
 */
public class XDubboUtil {

    /**
     * Check whether a given method has {@link RequestMapping} annotated
     */
    public static boolean methodNotAnnotatedWithRequestMapping(MethodParameter methodParameter) {
        return !methodParameter.getMethod().isAnnotationPresent(RequestMapping.class);
    }
}
