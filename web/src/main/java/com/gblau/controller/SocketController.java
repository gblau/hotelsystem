package com.gblau.controller;

import com.gb.common.model.vo.ResponseModel;
import com.gblau.handler.DefaultTextWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpSession;

/**
 * @author gblau
 * @date 2017-09-02
 */
@Controller
@RequestMapping("/websocket")
public class SocketController{
    @Autowired
    private DefaultTextWebSocketHandler socketHandler;
    @Autowired
    private HttpSession session;

    @RequestMapping("/demo")
    public String startWebsocketDemo() {
        session.setAttribute("user", "gblau");
        return "/socket";
    }

    @RequestMapping("/send")
    @ResponseBody
    public ResponseModel sendMessageToUser(String username, String message) {
        socketHandler.sendMessageToUser(username, new TextMessage(message));
        return ResponseModel.ok().build();
    }
}