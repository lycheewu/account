package com.sdjzu.account.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lychee
 * @date 2020/2/9 23:27
 */
@Data
@Entity
@DynamicUpdate //动态更新时间
@Table(name = "company_info")
public class CompanyDO {
    @Id
    private String companyId;

    private String companyName;

    private String companyDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
