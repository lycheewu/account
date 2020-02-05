package com.sdjzu.account.dao.repo;

import com.sdjzu.account.dao.model.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lychee
 * @date 2020/2/4
 */
public interface UserRepo extends JpaRepository<UserDO,String> {

    UserDO findByLoginName(String loginName);

    List<UserDO> findByDepartmentId(String departmentId);
}
