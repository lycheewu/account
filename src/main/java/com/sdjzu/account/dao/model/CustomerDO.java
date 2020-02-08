package com.sdjzu.account.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lychee
 * @date 2020/2/7 12:50
 */
@Data
@Entity
@DynamicUpdate //动态更新时间
@Table(name = "department_info")
public class CustomerDO {

    /**
     * 客户ID
     */
    @Id
    private String customerId;

    /**
     * 对接客户的用户ID
     */
    private String userId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 客户手机号
     */
    private String customerPhone;

    /**
     * 客户地址
     */
    private String customerAddress;

    /**
     * 备注
     */
    private String comment;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
