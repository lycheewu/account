package com.sdjzu.account.dao.repo;

import com.sdjzu.account.dao.model.CompanyDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lychee
 * @date 2020/2/9 23:28
 */
public interface CompanyRepo extends JpaRepository<CompanyDO,String> {
}
