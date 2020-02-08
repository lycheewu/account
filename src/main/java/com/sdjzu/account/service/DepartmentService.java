package com.sdjzu.account.service;

import com.sdjzu.account.dao.repo.DepartmentRepo;
import com.sdjzu.account.service.bo.DepartmentBO;
import com.sdjzu.account.service.bo.DepartmentQuery;

import java.util.List;

/**
 * @author lychee
 * @date 2020/2/7 15:55
 */
public interface DepartmentService {
    /**
     * 根据部门名称 模糊查询部门list
     * @param deQuery
     * @return
     */
    List<DepartmentBO> findAllDepartment(DepartmentQuery deQuery);
    DepartmentBO findDepartment(String departmentId);

    /**
     * 添加部门
     * @param departmentBO
     */
    void insertDepartment(DepartmentBO departmentBO);

    /**
     * 更新部门信息
     * @param departmentBO
     */
    void updateDepartment(DepartmentBO departmentBO);

    /**
     * 删除部门
     * @param departmentId
     */
    void deleteDepartment(String departmentId);
}
