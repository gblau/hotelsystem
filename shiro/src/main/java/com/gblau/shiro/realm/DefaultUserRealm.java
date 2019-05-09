package com.gblau.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author gblau
 * @date 2016-11-11
 */
public abstract class DefaultUserRealm extends AuthorizingRealm {
    @Override
    public String getName() {
        return "userRealm";
    }

    //支持UsernamePasswordToken
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 用户验证完成后，在这个方法里给用户添加相应的权限。
     * @param principals the primary identifying principals of the AuthorizationInfo that should be retrieved.
     * @return the AuthorizationInfo associated with this principals.
     * @see SimpleAuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String id = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        setUserRolesAndPermissions(authorizationInfo, id);

        return authorizationInfo;
    }

    /**
     * 认证一个用户Token是否正确
     * @param token the authentication token containing the user's principal and credentials.
     * @throws AuthenticationException if there is an error acquiring data or performing
     *                                 realm-specific authentication logic for the specified <tt>token</tt>
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //拿username从数据库中查询
        return isUserExist((String) token.getPrincipal()) ?
                null : new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), getName());
    }

    /**
     * 通过userId，设置该用户的角色和所拥有的权限
     * @param authorizationInfo
     * @param userId
     */
    protected abstract void setUserRolesAndPermissions(SimpleAuthorizationInfo authorizationInfo, String userId);

    /**
     * 判断是否存在该用户。
     * @param username
     * @return boolean 存在与否
     */
    protected abstract boolean isUserExist(String username);
}