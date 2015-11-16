package life.xiaoyuan.xdubbo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.core.MethodParameter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

/**
 * @author youmoo
 * @since 15/11/14 13:12
 */
public class RequestResponseBodyMethodProcessorDecorator implements HandlerMethodReturnValueHandler, HandlerMethodArgumentResolver {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private final RequestResponseBodyMethodProcessor delegate;

    private final ThreadLocal<ArrayNode> body = new ThreadLocal<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

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
        //todo 如果是多参@RequestBody,则使用 ThreadLocal 解决
        if (XDubboUtil.methodNotAnnotatedWithRequestMapping(parameter) && parameter.getMethod().getParameters().length > 1) {
            return resolveArgument(parameter, webRequest);
        }
        return delegate.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
    }

    /**
     * Read request body as string
     */
    private String getBody(HttpServletRequest req) throws Exception {
        String body = "";
        if (req.getMethod().equals("POST")) {
            body = StreamUtils.copyToString(req.getInputStream(), DEFAULT_CHARSET);
        }
        return body;
    }

    /**
     * Resolve argument for handler method with multi parameters
     */
    private Object resolveArgument(MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
        if (parameter.getParameterIndex() == 0) {
            HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
            String json = getBody(servletRequest);
            ArrayNode arrayNode = (ArrayNode) objectMapper.readTree(json);
            body.set(arrayNode);
        }
        ArrayNode arrayNode = body.get();
        JsonNode jsonNode = arrayNode.get(parameter.getParameterIndex());
        if (parameter.getParameterIndex() == parameter.getMethod().getParameterTypes().length - 1) {
            body.remove();
        }
        return objectMapper.treeToValue(jsonNode, parameter.getParameterType());
    }
}
