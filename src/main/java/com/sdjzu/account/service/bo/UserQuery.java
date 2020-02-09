package com.sdjzu.account.service.bo;

import com.sdjzu.account.base.pojo.BaseQuery;
import lombok.Data;

/**
 * @author lychee
 * @date 2020/2/9 20:14
 */
@Data
public class UserQuery extends BaseQuery {
    private String name;
}
