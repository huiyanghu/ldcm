package com.lvdun.dao;

import com.lvdun.entity.BaseLdUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */
@Repository
public interface BaseLdUserRepository extends CrudRepository<BaseLdUser, Long> {
    @Query("select user from BaseLdUser user where user.isPrime=1 and user.custumerId= :customerId ")
    public List<BaseLdUser> getPrimeBaseLdUser(@Param("customerId") Long customerId);
}
