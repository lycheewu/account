package com.sdjzu.account.service;

import com.sdjzu.account.service.bo.UserBO;
import com.sdjzu.account.service.bo.UserQuery;

import java.util.List;

/**
 * @author lychee
 * @date 2020/2/4 15:27
 */
public interface UserService {

    /**
     * 查询所有的用户
     *
     * @param userQuery
     * @return
     */
    List<UserBO> findUser(UserQuery userQuery);

    /**
     * 校验登录信息是否有效
     *
     * @param loginName 登录名
     * @param loginPwd  登录密码
     */
    void checkLogin(String loginName, String loginPwd);

    /**
     * 插入用户
     * @param userBO
     */
    void insertUser(UserBO userBO);

    /**
     * 修改用户
     * @param userBO
     */
    void updateUser(UserBO userBO);

    /**
     * 删除用户
     * @param userId
     */
    void deleteUser(String userId);
}
