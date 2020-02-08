package com.sdjzu.account.service.impl;

import com.sdjzu.account.dao.model.DepartmentDO;
import com.sdjzu.account.dao.repo.DepartmentRepo;
import com.sdjzu.account.dao.repo.UserRepo;
import com.sdjzu.account.enums.ResultEnum;
import com.sdjzu.account.exception.AccountException;
import com.sdjzu.account.service.DepartmentService;
import com.sdjzu.account.service.bo.DepartmentBO;
import com.sdjzu.account.service.bo.DepartmentQuery;
import com.sdjzu.account.utils.BeanUtilEx;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lychee
 * @date 2020/2/7 15:56
 */
@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentRepo departmentRepo;

    @Resource
    private UserRepo userRepo;

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 部门管理，查询所有的部门
     *
     * @param departmentQuery
     * @return
     */
    @Override
    public List<DepartmentBO> findAllDepartment(DepartmentQuery departmentQuery) {
        DepartmentBO departmentBO = findDepartment(departmentQuery.getDepartmentId());
        List<DepartmentDO> departmentDOS = new ArrayList<>();
        //前端没有传来搜索字
        if (StringUtils.equals(departmentQuery.getName(), "isEmpty")) {
            //建立实体query,进行分页查询
            Pageable pageable = PageRequest.of(departmentQuery.getPage(), departmentQuery.getSize());
            //jpa的排序 按照部门名称排序
            pageable.getSortOr(Sort.by("departmentName"));
            //.getContent将page转化为list
            departmentDOS = departmentRepo
                    .findByCompanyId(departmentBO.getCompanyId(), pageable).getContent();

        } else {
            //拼接sql
            String sql = "SELECT * FROM department_info WHERE company_id = \""
                    + departmentBO.getCompanyId() + "\" and department_name like \""
                    + departmentQuery.getName() + "%\"";
            departmentDOS = jdbcTemplate
                    .query(sql, new BeanPropertyRowMapper<>(DepartmentDO.class));
        }
        List<DepartmentBO> departmentBOS = BeanUtilEx.copyAndGetList(departmentDOS, DepartmentBO.class);

        return departmentBOS;
    }

    @Override
    public DepartmentBO findDepartment(String departmentId) {
        DepartmentDO departmentDO = departmentRepo.findByDepartmentId(departmentId);
        DepartmentBO departmentBO = BeanUtilEx.copyAndGet(departmentDO, DepartmentBO.class);
        return departmentBO;
    }

    @Override
    public void insertDepartment(DepartmentBO departmentBO) {
        departmentRepo.save(BeanUtilEx.copyAndGet(departmentBO, DepartmentDO.class));
    }

    @Override
    public void updateDepartment(DepartmentBO departmentBO) {
        departmentBO.setCompanyId(findDepartment(departmentBO.getDepartmentId()).getCompanyId());
        departmentRepo.save(BeanUtilEx.copyAndGet(departmentBO, DepartmentDO.class));
    }

    @Override
    public void deleteDepartment(String departmentId) {
        if (!CollectionUtils.isEmpty(userRepo.findByDepartmentId(departmentId))) {
            throw new AccountException(ResultEnum.DEPARTMENT_LIST_NOT_EMPTY);
        }
        departmentRepo.deleteById(departmentId);
    }
}
