package com.lvdun.dao;

import com.lvdun.entity.CmCustomer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/6/15.
 */
@Repository
public interface CmCustumerRepository extends CrudRepository<CmCustomer, Long> {

}
