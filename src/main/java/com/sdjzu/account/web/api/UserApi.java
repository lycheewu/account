package com.sdjzu.account.web.api;

import com.sdjzu.account.dao.model.UserDO;
import com.sdjzu.account.dao.repo.CompanyRepo;
import com.sdjzu.account.dao.repo.DepartmentRepo;
import com.sdjzu.account.dao.repo.UserRepo;
import com.sdjzu.account.enums.ResultEnum;
import com.sdjzu.account.exception.AccountException;
import com.sdjzu.account.service.UserService;
import com.sdjzu.account.service.bo.UserBO;
import com.sdjzu.account.service.bo.UserQuery;
import com.sdjzu.account.utils.BeanUtilEx;
import com.sdjzu.account.utils.KeyUtil;
import com.sdjzu.account.utils.ResultVOUtil;
import com.sdjzu.account.web.auth.JWTUtils;
import com.sdjzu.account.web.vo.LoginVO;
import com.sdjzu.account.web.vo.ResultVO;
import com.sdjzu.account.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author lychee
 * @date 2020/2/4 17:04
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserApi extends BaseApi {
    @Resource
    private JWTUtils jwtUtils;

    @Resource
    private UserService userService;

    @Resource
    private UserRepo userRepo;

    @Resource
    private DepartmentRepo departmentRepo;

    @Resource
    private CompanyRepo companyRepo;

    /**
     * 登录
     *
     * @param loginVO
     * @return
     */
    @GetMapping("/login")
    public ResultVO<LoginVO> login(@RequestBody LoginVO loginVO) {
        // 校验登录是否有效
        userService.checkLogin(loginVO.getLoginName(), loginVO.getLoginPwd());

        //校验身份成功生成token返回
        UserDO userDO = new UserDO();
        userDO = userRepo.findByLoginName(loginVO.getLoginName());
        String departmentId = userDO.getDepartmentId();
        String token = jwtUtils.setToken(userDO.getUserId(), departmentId);
        LoginVO loginVO1 = BeanUtilEx.copyAndGet(userDO, LoginVO.class);
        loginVO1.setToken(token);
        loginVO1.setDepartmentName(departmentRepo.findByDepartmentId(departmentId).getDepartmentName());

        return ResultVOUtil.success(loginVO1);
    }

    @GetMapping("/find")
    public ResultVO<List<UserVO>> findUser(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        UserQuery userQuery = new UserQuery();
        if (Strings.isBlank(name)) {
            name = "isEmpty";
        }
        userQuery.setName(name);
        userQuery.setPage(page);
        userQuery.setSize(size);
        userQuery.setUserId(getUserId());
        List<UserBO> userBOS = userService.findUser(userQuery);
        BeanUtilEx.copyAndGetList(userBOS, UserVO.class);
        List<UserVO> userVOS = userBOS.stream().map(
                bo -> {
                    UserVO userVO = BeanUtilEx.copyAndGet(bo, UserVO.class);
                    userVO.setDepartmentName(
                            departmentRepo.findByDepartmentId(
                                    bo.getDepartmentId()).getDepartmentName());
                    return userVO;
                }
        ).collect(Collectors.toList());
        return ResultVOUtil.success(userVOS);
    }

    /**
     * 生成登录名
     * 生成规则是：公司名（这是运维人员命名的，要求尽量是英文缩写）+部门名称+用户权限+随机数2位
     *
     * @param userRole
     * @param departmentId
     * @return
     */
    @GetMapping("/register")
    public ResultVO<String> findName(@RequestParam(value = "userRole") String userRole,
                                     @RequestParam(value = "departmentId") String departmentId) {
        //注意，在使用的时候默认只有老板有这个权限，老板的userId就是companyId
        String name = companyRepo.findById(getUserId()).get().getCompanyName();
        if (Strings.isEmpty(departmentId)) {
            //随机生成5位随机数
            departmentId = String.valueOf(new Random().nextInt(90000) + 10000);
        }
        name += departmentRepo.findByDepartmentId(departmentId).getDepartmentName();
        if (Strings.isEmpty(userRole)) {
            throw new AccountException(ResultEnum.USER_ROLE_NOT_EXIST);
        }
        //生成用户身份+两位随机数 足够容纳
        name += userRole.toLowerCase() + new Random().nextInt(90) + 10;

        return ResultVOUtil.success(name);
    }

    @PostMapping("/insert")
    public ResultVO insertUser(@RequestBody UserVO userVO) {
        if (Objects.nonNull(userRepo.findByLoginName(userVO.getLoginName()))) {
            throw new AccountException(ResultEnum.USER_EXIST);
        }
        String companyId = userRepo.findById(getUserId()).get().getCompanyId();
        UserBO userBO = BeanUtilEx.copyAndGet(userVO, UserBO.class);
        userBO.setCompanyId(companyId);
        userBO.setUserId(KeyUtil.genUniqueKey());
        userService.insertUser(userBO);
        return ResultVOUtil.success();
    }

    @PutMapping("/update")
    public ResultVO updateUser(@RequestBody UserVO userVO) {
        UserDO userDO = userRepo.findById(getUserId()).orElse(null);
        UserBO userBO = BeanUtilEx.copyAndGet(userVO, UserBO.class);
        if (Objects.nonNull(userDO)) {
            userBO.setCompanyId(userDO.getCompanyId());
//            userBO.setDepartmentId(userDO.getDepartmentId());
            userService.insertUser(userBO);
        } else {
            throw new AccountException(ResultEnum.USER_NOT_EXIST);
        }
        return ResultVOUtil.success();
    }

    @DeleteMapping("/delete")
    public ResultVO deleteUser(@RequestParam("userId") String userId) {
        if (Objects.equals(userId, getUserId())) {
            throw new AccountException(ResultEnum.USER_NOT_EMPTY);
        }
        userService.deleteUser(userId);
        return ResultVOUtil.success();
    }

}
