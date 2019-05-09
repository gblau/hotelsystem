package com.gblau;

import com.gblau.handler.DefaultTextWebSocketHandler;
import com.gblau.interceptor.WebSocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketConfiguration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 开启WebSocket配置后，所有实现{@link WebSocketConfigurer}的配置都会加入到一个configurers列表中统一维护，实现多配制共存
 * @see DelegatingWebSocketConfiguration
 * @see EnableWebSocket 使用Import标签将{@link DelegatingWebSocketConfiguration}引入配置。
 * @author gblau
 * @date 2017-05-20
 */
@EnableWebSocket
public class DefaultWebSocketConfig implements WebSocketConfigurer {
    @Value("${websocket.sockjs-path}")
    private String sockJsPath;
    @Value("${websocket.path}")
    private String path;
    @Autowired
    private DefaultTextWebSocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //注册处理拦截器,拦截url的请求
        registry.addHandler(socketHandler, path).addInterceptors(new WebSocketInterceptor());
        //注册SockJs的处理拦截器
        registry.addHandler(socketHandler, sockJsPath).addInterceptors(new WebSocketInterceptor()).withSockJS();
    }
}
