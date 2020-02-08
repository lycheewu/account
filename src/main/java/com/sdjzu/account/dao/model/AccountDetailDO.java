package com.sdjzu.account.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lychee
 * @date 2020/2/7 15:11
 */
@Data
@DynamicUpdate
@Entity
@Table(name="account_detail")
public class AccountDetailDO {

    /**
     * 账目单号
     */
    @Id
    private String accountId;

    /**
     * 账目形式 收款RECEIVE，付款PAY
     */
    private String accountForm;

    /**
     * 账目类型 现金CASH，线上支付ONLINE
     */
    private String accountType;

    /**
     * 数目
     */
    private BigDecimal accountPrice;

    /**
     * 账目状态 已付款PAYMENT，未付款UNPAID，已收款COLLECTION，待收款RECEIVABLE
     */
    private String accountStatus;

    /**
     * 对接用户ID
     */
    private String userId;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
