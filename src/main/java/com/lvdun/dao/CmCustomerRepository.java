package com.lvdun.dao;

import com.lvdun.entity.CmCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */
@Repository
public interface CmCustomerRepository extends JpaRepository<CmCustomer, Long>, JpaSpecificationExecutor<CmCustomer> {
    @Query("select customer from CmCustomer customer where customer.customerName= :customerName")
    public List<CmCustomer> getByCustomerName(@Param("customerName") String customerName);
}
