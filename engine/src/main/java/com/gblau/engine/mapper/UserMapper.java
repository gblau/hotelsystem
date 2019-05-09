package com.gblau.engine.mapper;

import com.gb.common.model.po.User;

import java.util.List;
import java.util.Set;

/**
 * @author gblau
 * @date 2016-11-12
 */
public interface UserMapper extends Mapper<User> {
    User selectUserByUsername(String username);
    Set<String> selectRoles(String id);
    Set<String> selectPermissions(String id);
    List<String> selectPermissionUrl(String principal);
}
