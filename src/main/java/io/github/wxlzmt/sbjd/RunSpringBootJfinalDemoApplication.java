package io.github.wxlzmt.sbjd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import com.jfinal.core.JFinalFilter;

import io.github.wxlzmt.sbjd.config.JfinalMainConfig;

@SpringBootApplication
@EnableDiscoveryClient
@Configuration
public class RunSpringBootJfinalDemoApplication {

    private static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(RunSpringBootJfinalDemoApplication.class);

    public static void main(String[] args) {
        if (args == null || args.length == 0) {//加这个if是为了方便在集成开发环境下运行,即:默认dev环境
            args = new String[] { "--spring.profiles.active=dev" };
        }
        SpringApplication.run(RunSpringBootJfinalDemoApplication.class, args);
    }

    /**
     * 添加filter
     */
    @Bean
    public FilterRegistrationBean<JFinalFilter> filterRegistration(JfinalMainConfig jfinalMainConfig) {
        LOG.info("注册jfinal filter");
        Assert.notNull(jfinalMainConfig, "jfinalMainConfig is NULL!");
        FilterRegistrationBean<JFinalFilter> registration = new FilterRegistrationBean<JFinalFilter>();
        registration.setFilter(new JFinalFilter(jfinalMainConfig));
        registration.addUrlPatterns("/*");
        registration.setName("jfinal");
        registration.setOrder(1);
        return registration;
    }

}
