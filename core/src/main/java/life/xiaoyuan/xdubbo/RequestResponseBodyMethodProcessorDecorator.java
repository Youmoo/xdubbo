package life.xiaoyuan.xdubbo;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * @author youmoo
 * @since 15/11/14 13:12
 */
public class RequestResponseBodyMethodProcessorDecorator implements HandlerMethodReturnValueHandler, HandlerMethodArgumentResolver {
    private final RequestResponseBodyMethodProcessor delegate;

    public RequestResponseBodyMethodProcessorDecorator(RequestResponseBodyMethodProcessor delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType) || XDubboUtil.methodNotAnnotatedWithRequestMapping(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return delegate.supportsParameter(parameter) || XDubboUtil.methodNotAnnotatedWithRequestMapping(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return delegate.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
    }
}
