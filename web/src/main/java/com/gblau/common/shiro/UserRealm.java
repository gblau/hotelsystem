package com.gblau.common.shiro;

import com.gblau.service.authorization.UserService;
import com.gblau.shiro.realm.DefaultUserRealm;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gblau
 * @date 2017-09-10
 */
public class UserRealm extends DefaultUserRealm {
    @Autowired
    private UserService userService;

    /**
     * 通过userId，设置该用户的角色和所拥有的权限
     *
     * @param authorizationInfo
     * @param userId
     */
    @Override
    protected void setUserRolesAndPermissions(SimpleAuthorizationInfo authorizationInfo, String userId) {
        authorizationInfo.setRoles(userService.findRoles(userId));
        authorizationInfo.setStringPermissions(userService.findPermissions(userId));
    }

    /**
     * 判断是否存在该用户。
     *
     * @param username
     * @return boolean 存在与否
     */
    @Override
    protected boolean isUserExist(String username) {
        return userService.findUserByUsername(username) == null;
    }

}
