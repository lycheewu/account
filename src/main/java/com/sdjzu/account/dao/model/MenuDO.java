package com.sdjzu.account.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lychee
 * @date 2020/2/7 13:10
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "menu_info")
public class MenuDO {
    /**
     * 菜单编码
     */
    @Id
    private String menuCode;

    /**
     * 菜单权限 1老板 2部门经理 3录单员
     */
    private String menuLimit;

    /**
     * 菜单名臣
     */
    private String menuName;

    /**
     * 菜单等级
     */
    private String menuOrder;

    /**
     * 父级菜单编码
     */
    private String parentCode;

    /**
     * 菜单logo
     */
    private String menuIcon;

    /**
     * 插件 *不知道干嘛用的*
     */
    private String component;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
