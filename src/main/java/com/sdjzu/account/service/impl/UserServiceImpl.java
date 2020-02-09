package com.sdjzu.account.service.impl;

import com.sdjzu.account.dao.model.UserDO;
import com.sdjzu.account.dao.repo.UserRepo;
import com.sdjzu.account.enums.ResultEnum;
import com.sdjzu.account.exception.AccountException;
import com.sdjzu.account.service.UserService;
import com.sdjzu.account.service.bo.UserBO;
import com.sdjzu.account.service.bo.UserQuery;
import com.sdjzu.account.utils.BeanUtilEx;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public List<UserBO> findUser(UserQuery userQuery) {
        List<UserDO> userDOS = new ArrayList<>();
        //前端没有传来搜索字
        if (StringUtils.equals(userQuery.getName(), "isEmpty")) {
            //建立实体query,进行分页查询
            Pageable pageable = PageRequest.of(userQuery.getPage(), userQuery.getSize());
            //jpa的排序 按照登录名称排序
            pageable.getSortOr(Sort.by("departmentId").ascending());
            //.getContent将page转化为list
            userDOS = userRepo
                    .findByCompanyId(userQuery.getUserId(), pageable).getContent();
        } else {
            //拼接sql,使用JdbcTemplate 精确查询、前缀查询和排序
            String sql = "SELECT * FROM user_info WHERE company_id = \""
                    + userQuery.getUserId() + "\" and nick_name like \""
                    + userQuery.getName() + "%\" ORDER BY department_id asc";
            userDOS = jdbcTemplate
                    .query(sql, new BeanPropertyRowMapper<>(UserDO.class));
        }
        return BeanUtilEx.copyAndGetList(userDOS, UserBO.class);
    }

    @Override
    public void checkLogin(String loginName, String loginPwd) {
        UserDO userDO = new UserDO();
//        List<UserDO> userDOS = userRepo.findAll();
        userDO = userRepo.findByLoginName(loginName);
        if (userDO == null) {
            throw new AccountException(ResultEnum.USER_NOT_EXIST);
        }
        if (!StringUtils.equals(loginPwd, userDO.getLoginPwd())) {
            throw new AccountException(ResultEnum.USER_WRONG_PASSWORD);
        }
    }

    @Override
    public void insertUser(UserBO userBO) {
        userRepo.save(BeanUtilEx.copyAndGet(userBO, UserDO.class));
    }

    @Override
    public void updateUser(UserBO userBO) {
//        userRepo.save(BeanUtilEx.copyAndGet(userBO, UserDO.class));
    }

    @Override
    public void deleteUser(String userId) {
        userRepo.deleteById(userId);
    }
}
