package io.github.wxlzmt.sbjd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jfinal.core.JFinalFilter;

import io.github.wxlzmt.sbjd.config.JfinalMainConfig;

@SpringBootApplication
@EnableDiscoveryClient
@Configuration
public class RunSpringBootJfinalDemoApplication {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            args = new String[] { "--spring.profiles.active=dev" };
        }
        SpringApplication.run(RunSpringBootJfinalDemoApplication.class, args);
    }

    /**
     * 添加filter
     */
    @Bean
    public FilterRegistrationBean<JFinalFilter> filterRegistration() {
        FilterRegistrationBean<JFinalFilter> registration = new FilterRegistrationBean<JFinalFilter>();
        registration.setFilter(new JFinalFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("configClass", JfinalMainConfig.class.getName());
        registration.setName("jfinal");
        registration.setOrder(1);
        return registration;
    }

}
