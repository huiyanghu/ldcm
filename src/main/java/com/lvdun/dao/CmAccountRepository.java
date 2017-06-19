package com.lvdun.dao;

import com.lvdun.entity.CmAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
@Repository
public interface CmAccountRepository extends CrudRepository<CmAccount, Long> {

    @Query("select account from CmAccount account where account.account=:account")
    public List<CmAccount> getByAccount(@Param("account") String username);
}
