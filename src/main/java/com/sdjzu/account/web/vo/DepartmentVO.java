package com.sdjzu.account.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author lychee
 * @date 2020/2/7 21:05
 */
@Data
public class DepartmentVO {
    /**
     * 部门ID
     */
    @JsonProperty(value = "departmentId")
    private String departmentId;

    /**
     * 所属公司ID
     */
//    @JsonProperty(value = "companyId")
//    private String companyId;

    /**
     * 部门名称
     */
    @JsonProperty(value = "departmentName")
    private String departmentName;

    /**
     * 部门描述
     */
    @JsonProperty(value = "departmentDesc")
    private String departmentDesc;
}
