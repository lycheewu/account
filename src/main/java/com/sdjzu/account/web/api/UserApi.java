package com.sdjzu.account.web.api;

import com.sdjzu.account.dao.model.UserDO;
import com.sdjzu.account.dao.repo.DepartmentRepo;
import com.sdjzu.account.dao.repo.UserRepo;
import com.sdjzu.account.service.UserService;
import com.sdjzu.account.utils.BeanUtilEx;
import com.sdjzu.account.utils.ResultVOUtil;
import com.sdjzu.account.web.auth.JWTUtils;
import com.sdjzu.account.web.vo.LoginVO;
import com.sdjzu.account.web.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

//    @GetMapping("/find")
//    public ResultVO<List<LoginVO>> findAllUser() {
//        List<UserDO> userDOS = userRepo.findAll();
//        List<LoginVO> loginVOS = BeanUtilEx.copyAndGetList(userDOS, LoginVO.class);
//        String userId = getUserId();
//        log.info("userId:" + userId);
//        return ResultVOUtil.success(loginVOS);
//    }
}
