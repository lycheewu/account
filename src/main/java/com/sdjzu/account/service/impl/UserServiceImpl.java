package com.sdjzu.account.service.impl;

import com.sdjzu.account.dao.model.UserDO;
import com.sdjzu.account.dao.repo.UserRepo;
import com.sdjzu.account.enums.ResultEnum;
import com.sdjzu.account.exception.AccountException;
import com.sdjzu.account.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lychee
 * @date 2020/2/4 15:28
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private UserRepo userRepo;

    @Override
    public UserDO findUserByName(String name) {
        return null;
    }

    @Override
    public List<UserDO> findUserByDepartmentId(String departmentId) {
        return null;
    }

    @Override
    public void checkLogin(String loginName, String loginPwd) {
        UserDO userDO =new UserDO();
        List<UserDO> userInfos =userRepo.findAll();
        userDO = userRepo.findByLoginName(loginName);
        if (userDO == null) {
            throw new AccountException(ResultEnum.USER_NOT_EXIST);
        }
        if (!StringUtils.equals(loginPwd, userDO.getLoginPwd())) {
            throw new AccountException(ResultEnum.USER_WRONG_PASSWORD);
        }
    }
}
