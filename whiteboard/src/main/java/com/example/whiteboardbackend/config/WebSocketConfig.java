package com.example.whiteboardbackend.config;

import com.example.whiteboardbackend.ws.WhiteboardSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WhiteboardSocketHandler handler;

    public WebSocketConfig(WhiteboardSocketHandler handler) {
        this.handler = handler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/ws/whiteboard")
                .setAllowedOrigins("*");
    }
}
