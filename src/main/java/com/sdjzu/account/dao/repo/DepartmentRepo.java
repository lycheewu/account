package com.sdjzu.account.dao.repo;

import com.sdjzu.account.dao.model.DepartmentDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author lychee
 * @date 2020/2/4 21:48
 */
public interface DepartmentRepo extends JpaRepository<DepartmentDO,String>, JpaSpecificationExecutor<DepartmentDO> {
    DepartmentDO findByDepartmentId(String departmentId);

//    List<DepartmentDO> findByDepartmentNameLike(String name);

    Page<DepartmentDO> findByCompanyId(String companyId, Pageable pageable);

}
