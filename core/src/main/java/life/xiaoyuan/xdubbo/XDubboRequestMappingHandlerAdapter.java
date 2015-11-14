package life.xiaoyuan.xdubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ViewNameMethodReturnValueHandler;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author youmoo
 * @since 15/11/14 11:44
 */
@Component
public class XDubboRequestMappingHandlerAdapter {

    @Autowired
    RequestMappingHandlerAdapter adapter;

    @PostConstruct
    void decorate() {
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>(adapter.getReturnValueHandlers());
        decorateHandlers(handlers);
        adapter.setReturnValueHandlers(handlers);

        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>(adapter.getArgumentResolvers());
        decorateResolvers(resolvers);
        adapter.setArgumentResolvers(resolvers);
    }

    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        for (HandlerMethodReturnValueHandler handler : handlers) {
            if (handler instanceof ViewNameMethodReturnValueHandler) {
                ViewNameMethodReturnValueHandlerDecorator decorator = new ViewNameMethodReturnValueHandlerDecorator(handler);
                int index = handlers.indexOf(handler);
                handlers.set(index, decorator);
            }
            if (handler instanceof RequestResponseBodyMethodProcessor) {
                RequestResponseBodyMethodProcessorDecorator decorator = new RequestResponseBodyMethodProcessorDecorator((RequestResponseBodyMethodProcessor) handler);
                int index = handlers.indexOf(handler);
                handlers.set(index, decorator);
            }
        }
    }

    private void decorateResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        for (HandlerMethodArgumentResolver resolver : resolvers) {
            if (resolver instanceof RequestResponseBodyMethodProcessor) {
                RequestResponseBodyMethodProcessorDecorator decorator = new RequestResponseBodyMethodProcessorDecorator((RequestResponseBodyMethodProcessor) resolver);
                int index = resolvers.indexOf(resolver);
                resolvers.set(index, decorator);
                break;
            }
        }
    }

}
