package com.lvdun.dao;

import com.lvdun.entity.CmAccount;
import org.springframework.data.jpa.repository.Modifying;
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
    public List<CmAccount> getByAccount(@Param("account") String account);

    @Query("select account from CmAccount account where account.mobile=:mobile")
    public List<CmAccount> getByMobile(@Param("mobile") String mobile);

    @Query("select account from CmAccount account where account.roleFlag=1 and account.customerId = :customerId ")
    public List<CmAccount> getPrimeCmAccount(@Param("customerId") Long customerId);

    @Query("update CmAccount set roleFlag=0")
    @Modifying
    public void updateAccountRoleFlagToZero();


}
