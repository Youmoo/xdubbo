package life.xiaoyuan.xdubbo;

/**
 * @author youmoo
 * @since 15/11/13 16:40
 */
public interface ServiceFilter {

    /**
     * Test whether a given service can be exposed as http end points
     *
     * @param serviceClass type of service
     * @return
     */
    boolean test(Class<?> serviceClass);
}
