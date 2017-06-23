package com.lvdun.dao;

import com.lvdun.entity.StatArrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;


/**
 * Created by Administrator on 2017/6/15.
 */
public interface StatArriveRepository extends JpaRepository<StatArrive, Long>, StatArriveDao {
    /**
     * 存储过程:统计data_record表 包括status和reason_code
     */
    @Procedure(procedureName = "prc")
    @Modifying
    void executeProcedure();
}
