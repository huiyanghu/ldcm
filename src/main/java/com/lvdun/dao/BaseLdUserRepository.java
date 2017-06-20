package com.lvdun.dao;

import com.lvdun.entity.BaseLdUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */
@Repository
public interface BaseLdUserRepository extends JpaRepository<BaseLdUser, Long>, JpaSpecificationExecutor<BaseLdUser> {
    @Query("select user from BaseLdUser user where user.account.roleFlag=1 and user.customerId= :customerId ")
    public List<BaseLdUser> getPrimeBaseLdUser(@Param("customerId") Long customerId);


}
