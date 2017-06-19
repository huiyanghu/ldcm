package com.lvdun.dao;

import com.lvdun.entity.BaseLdUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/6/16.
 */
@Repository
public interface BaseLdUserRepository extends CrudRepository<BaseLdUser, Long> {
}
