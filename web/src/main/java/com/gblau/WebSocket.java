package com.gblau;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring4.2后，Import注解除了可以导入一个配置类以外，还可以直接引入一个普通类当做Bean类，作用同@Bean注解一个方法。
 * @see Import
 * @author gblau
 * @date 2017-09-01
 */
@Configuration
public class WebSocket extends DefaultWebSocketConfig {
}
