package com.lvdun.dao;

import com.lvdun.entity.StatArrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;


/**
 * Created by Administrator on 2017/6/15.
 */
//@Repository
public interface StatArriveRepository extends JpaRepository<StatArrive, Long>,StatArriveDao {

    /**
     * 插入 数据审核结果 统计
     * @return
     */
    @Procedure(procedureName="p1")
    @Modifying
    public void insertIntoStatArrive();

    /**
     * 插入 数据审核结果 不通过原因 统计
     * @return
     */
    @Procedure()
    @Modifying
    public void insertIntoStatArriveDelete();

}
