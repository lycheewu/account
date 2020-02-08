package com.sdjzu.account.dao.repo;

import com.sdjzu.account.dao.model.CustomerDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lychee
 * @date 2020/2/7 13:07
 */
public interface CustomerRepo extends JpaRepository<CustomerDO,String> {

}
