package life.xiaoyuan.xdubbo;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author youmoo
 * @since 15/11/14 12:56
 */
public class ViewNameMethodReturnValueHandlerDecorator implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public ViewNameMethodReturnValueHandlerDecorator(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    /**
     * 确保只支持{@link RequestMapping}注解的方法
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType) && returnType.getMethod().isAnnotationPresent(RequestMapping.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}
