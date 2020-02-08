package com.sdjzu.account.web.api;

import com.sdjzu.account.dao.repo.UserRepo;
import com.sdjzu.account.service.DepartmentService;
import com.sdjzu.account.service.bo.DepartmentBO;
import com.sdjzu.account.service.bo.DepartmentQuery;
import com.sdjzu.account.utils.BeanUtilEx;
import com.sdjzu.account.utils.KeyUtil;
import com.sdjzu.account.utils.ResultVOUtil;
import com.sdjzu.account.web.vo.DepartmentVO;
import com.sdjzu.account.web.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lychee
 * @date 2020/2/7 21:09
 */
@RestController
@RequestMapping("/api/department")
@Slf4j
public class DepartmentApi extends BaseApi {
    @Resource
    private DepartmentService departmentService;

    @Resource
    private UserRepo userRepo;

    /**
     * 查询所有部门信息
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<DepartmentVO>> findAllDepartment(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        DepartmentQuery departmentQuery = new DepartmentQuery();
        if (Strings.isBlank(name)) {
            name = "isEmpty";
        }
        departmentQuery.setName(name);
        departmentQuery.setDepartmentId(getDepartmentId());
        departmentQuery.setPage(page);
        departmentQuery.setSize(size);
        List<DepartmentBO> departmentBOS = departmentService.findAllDepartment(departmentQuery);
        List<DepartmentVO> departmentVOS = BeanUtilEx.copyAndGetList(departmentBOS, DepartmentVO.class);
        return ResultVOUtil.success(departmentVOS);
    }

    @PutMapping("/insert")
    public ResultVO insetDepartment(@RequestBody DepartmentVO departmentVO) {
        String companyId = userRepo.findById(getUserId()).get().getCompanyId();
        DepartmentBO departmentBO = BeanUtilEx.copyAndGet(departmentVO, DepartmentBO.class);
        departmentBO.setDepartmentId(KeyUtil.genUniqueKey());
        departmentBO.setCompanyId(companyId);
        departmentService.insertDepartment(departmentBO);
        return ResultVOUtil.success();
    }

    @PostMapping("/update")
    public ResultVO updateDepartment(@RequestBody DepartmentVO departmentVO) {
        DepartmentBO departmentBO = BeanUtilEx.copyAndGet(departmentVO, DepartmentBO.class);
        departmentService.updateDepartment(departmentBO);
        return ResultVOUtil.success();
    }

    @DeleteMapping("/delete")
    public ResultVO deleteDepartment(
            @RequestParam(value = "departmentId") String departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResultVOUtil.success();
    }

}
