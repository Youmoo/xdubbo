package life.xiaoyuan.xdubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * 用来组装{@link RequestMappingInfo}信息
 *
 * @author youmoo
 * @since 15/11/13 13:09
 */
@Component
public class XDubboRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Autowired(required = false)
    private ServiceFilter serviceFilter;

    @Autowired(required = false)
    private UrlGenerator urlGenerator;

    @PostConstruct
    void setDefaults() {
        if (serviceFilter == null) {
            serviceFilter = new ServiceFilter() {
                @Override
                public boolean test(Class<?> serviceClass) {
                    return false;
                }
            };
        }
        if (urlGenerator == null) {
            urlGenerator = new DefaultUrlGenerator();
        }
    }


    @Override
    protected boolean isHandler(Class<?> beanType) {
        return super.isHandler(beanType) || serviceFilter.test(beanType);
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);

        if (info == null && !super.isHandler(handlerType)) {//不是被@Controller 注解的类
            info = createRequestMappingInfo(method, handlerType);
        }

        return info;
    }

    private RequestMappingInfo createRequestMappingInfo(Method method, Class<?> handlerType) {
        return new RequestMappingInfo(
                null,
                new PatternsRequestCondition(new String[]{urlGenerator.generate(handlerType, method)}, getUrlPathHelper(), getPathMatcher(),
                        true, true, null),
                null,
                null,
                null,
                null,
                null,
                null);
    }


}
