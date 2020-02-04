package com.sdjzu.account.dao.repo;

import com.sdjzu.account.dao.model.DepartmentDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lychee
 * @date 2020/2/4 21:48
 */
public interface DepartmentRepo extends JpaRepository<DepartmentDO,String> {
    DepartmentDO findByDepartmentId(String departmentId);
}
