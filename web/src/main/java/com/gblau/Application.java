package com.gblau;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * <a>http://localhost:8080<a/>
 * @author gblau
 * @date 2016-11-10
 */
@Configuration
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    /**
     * 设置字符过滤的filter，为UTF-8
     * {@link FilterRegistrationBean}和{@link CharacterEncodingFilter}要在同一个方法注入，不然无效。
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(@Qualifier("characterEncodingFilter") CharacterEncodingFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        return registrationBean;
    }
}
