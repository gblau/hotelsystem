package com.gblau;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author gblau
 * @date 2016-11-14
 */
@Configuration
public class SpringWeb extends WebMvcConfigurerAdapter {
    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        return resolver;
    }

    @Bean
    @ConfigurationProperties("encode")
    public CharacterEncodingFilter characterEncodingFilter() {
        return new CharacterEncodingFilter();
    }

    /**
     * 重写MVC对静态资源的处理，把静态资源交还给Servlet处理，可以解决所有静态资源无法访问的问题。
     * 相当于<mvc:default-servlet-handler />
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
