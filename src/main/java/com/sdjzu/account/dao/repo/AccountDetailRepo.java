package com.sdjzu.account.dao.repo;

import com.sdjzu.account.dao.model.AccountDetailDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lychee
 * @date 2020/2/7 15:22
 */
public interface AccountDetailRepo extends JpaRepository<AccountDetailDO,String> {
}
