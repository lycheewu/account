package com.sdjzu.account.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lychee
 * @date 2020/2/4 21:45
 */
@Data
@Entity
@DynamicUpdate //动态更新时间
@Table(name="department_info")
public class DepartmentDO {
    /**
     * 部门ID
     */
    @Id
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
