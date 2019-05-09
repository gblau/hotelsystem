package com.gblau;

import com.gblau.common.shiro.UserRealm;
import com.gblau.shiro.DefaultShiroConfig;
import com.gblau.shiro.realm.DefaultUserRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;


/**
 * Spring boot 的shiro基础配置，可以通过注解大概学习shiro的知识。
 * Created by gblau on 2016-11-10.
 */
@Configuration
public class Shiro extends DefaultShiroConfig {
    /**
     * 继承自AuthorizingRealm的自定义Realm,即指定Shiro验证用户登录的类为自定义的Realm
     * @see DefaultUserRealm
     */
    @Bean
    @DependsOn(value="lifecycleBeanPostProcessor")
    public AuthorizingRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCacheManager(cacheManager());
        return userRealm;
    }
}
