package com.sdjzu.account.dao.repo;

import com.sdjzu.account.dao.model.MenuDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lychee
 * @date 2020/2/7 15:51
 */
public interface MenuRepo extends JpaRepository<MenuDO,String> {
}
