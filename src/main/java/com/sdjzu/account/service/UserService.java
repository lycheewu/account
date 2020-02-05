package com.sdjzu.account.service;

import com.sdjzu.account.dao.model.UserDO;

import java.util.List;

/**
 * @author lychee
 * @date 2020/2/4 15:27
 */
public interface UserService {

    UserDO findUserByName(String name);

    List<UserDO> findUserByDepartmentId(String departmentId);

    /**
     * 校验登录信息是否有效
     *
     * @param loginName 登录名
     * @param loginPwd  登录密码
     */
    void checkLogin(String loginName, String loginPwd);
}
