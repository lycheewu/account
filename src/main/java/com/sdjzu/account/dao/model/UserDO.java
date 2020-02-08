package com.sdjzu.account.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@DynamicUpdate //动态更新时间
@Table(name = "user_info")
public class UserDO {

    /**
     * 用户ID
     */
    @Id
    private String userId;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户住址
     */
    private String userPosition;

    /**
     * 菜单权限
     */
    private Integer userRole;

    /**
     * 所属公司ID
     *
     * XX冗余字段XX 暂留
     */
    private String companyId;

    /**
     * 用户所在部门ID
     */
    private String departmentId;

    /**
     * 备注
     */
    private String userComment;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
