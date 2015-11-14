package life.xiaoyuan.xdubbo.test;

import life.xiaoyuan.xdubbo.ServiceFilter;
import life.xiaoyuan.xdubbo.XDubbo;
import life.xiaoyuan.xdubbo.test.service.Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackageClasses = {XDubbo.class})
public class ServiceExporterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceExporterApplication.class, args);
    }

    @Bean
    ServiceFilter serviceFilter() {
        return new ServiceFilter() {
            @Override
            public boolean test(Class<?> serviceClass) {
                return Service.class.isAssignableFrom(serviceClass);
            }
        };
    }

}
