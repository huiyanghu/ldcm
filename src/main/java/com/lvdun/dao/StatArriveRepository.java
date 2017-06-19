package com.lvdun.dao;

import com.lvdun.entity.StatArrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * Created by Administrator on 2017/6/15.
 */
//@Repository
public interface StatArriveRepository extends JpaRepository<StatArrive, Long>,StatArriveDao {
    public String insert_stat_arrive_sql=""+
            "insert into stat_arrive (code_app_id,ugc_type,count_total,count_delete,count_pass,count_review,time_stamp,create_date) " +
            " SELECT " +
            "  code_app_id, " +
            "  data_type, " +
            "  sum(count) count_total, " +
            "  sum(CASE status WHEN 0 THEN count ELSE 0 END ) count_delete, " +
            "  sum(CASE status WHEN 1 THEN count ELSE 0 END ) count_pass, " +
            "  sum(CASE status WHEN 2 THEN count ELSE 0 END ) count_review, " +
            "  DATE_FORMAT(now(),'%Y-%m-%d %H:00:00') time_stamp, " +
            "  now() " +
            " FROM " +
            "  (select code_app_id,data_type,status,count(id) as count " +
            "    from data_record " +
            "    where 1=1  " +
            "    and create_date between DATE_FORMAT(DATE_ADD(Now(),INTERVAL -1 hour),'%Y-%m-%d %H:00:00') and DATE_FORMAT(now(),'%Y-%m-%d %H:00:00') " +
            "  group by code_app_id,data_type,status) as my_table " +
            " group by my_table.code_app_id,my_table.data_type;";

    public String insert_stat_arrive_delete_sql="" +
            "insert into stat_arrive_delete (code_app_id,ugc_type,count_total,count_zz,count_sq,count_wf,count_wg,time_stamp,create_date) " +
            "SELECT " +
            "  code_app_id, " +
            "  data_type, " +
            "  sum(count) count_total, " +
            "  sum(CASE reason_code WHEN 1 THEN count ELSE 0 END ) count_zz, " +
            "  sum(CASE reason_code WHEN 2 THEN count ELSE 0 END ) count_sq, " +
            "  sum(CASE reason_code WHEN 3 THEN count ELSE 0 END ) count_wf, " +
            "  sum(CASE reason_code WHEN 4 THEN count ELSE 0 END ) count_wg, " +
            "  DATE_FORMAT(now(),'%Y-%m-%d %H:00:00') time_stamp, " +
            "  now() " +
            " FROM " +
            "  (select code_app_id,data_type,reason_code,status,count(id) as count " +
            "    from data_record " +
            "    where 1=1" +
            "    and status=0 " +
            "    and create_date between DATE_FORMAT(DATE_ADD(Now(),INTERVAL -1 hour),'%Y-%m-%d %H:00:00') and DATE_FORMAT(now(),'%Y-%m-%d %H:00:00') " +
            "  group by code_app_id,data_type,reason_code) as my_table " +
            " group by my_table.code_app_id,my_table.data_type;";


    /**
     * 插入 数据审核结果 统计
     * @return
     */
    @Query(value = insert_stat_arrive_sql,nativeQuery = true)
    @Modifying
    public void insertIntoStatArrive();

    /**
     * 插入 数据审核结果 不通过原因 统计
     * @return
     */
    @Query(value = insert_stat_arrive_delete_sql,nativeQuery = true)
    @Modifying
    public void insertIntoStatArriveDelete();

}
