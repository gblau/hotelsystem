package com.gblau.controller;

import com.gb.common.model.po.User;
import com.gb.common.model.vo.ResponseModel;
import com.gblau.controller.base.BaseController;
import com.gblau.service.authorization.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gblau
 * @date 2016-11-12
 */
@RestController
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     * http://localhost:8080/login/validate
     * 登录用户接口。
     * @param currentUser
     * @return
     */
    @RequestMapping("/login")
    public ResponseModel doLogin(User currentUser) throws MissingServletRequestParameterException {
        validateRequestParameter(currentUser.getUsername(), currentUser.getPassword());
        return login(currentUser);
    }

    @RequestMapping("/test")
    public ResponseModel doTest(String username) throws MissingServletRequestParameterException {
        return ResponseModel.ok().body(userService.findUserByUsername(username));
    }

    /**
     * 尝试登录，返回登录信息。
     * @param currentUser
     * @return
     */
    private ResponseModel login(User currentUser) {
        UsernamePasswordToken token = new UsernamePasswordToken(currentUser.getUsername(),currentUser.getPassword());
        token.setRememberMe(true);

        try {
            SecurityUtils.getSubject().login(token);
            return ResponseModel.accepted().build();
        } catch (AuthenticationException e) {
            token.clear();
            return ResponseModel.rejected().message(e.getLocalizedMessage());
        }
    }

    /**
     * 注销登录
     */
    @RequestMapping("/logout")
    public ResponseModel doLogout() {
        SecurityUtils.getSubject().logout();
        return ResponseModel.ok().build();
    }
}
