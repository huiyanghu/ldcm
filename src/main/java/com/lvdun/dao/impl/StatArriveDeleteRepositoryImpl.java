package com.lvdun.dao.impl;

import com.lvdun.dao.StatArriveDeleteDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/17.
 */
public class StatArriveDeleteRepositoryImpl implements StatArriveDeleteDao {
    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Map getTotalResult(Integer flag, Long platformId) {

        String sql = "select " +
                "sum(count_total) as count_total, " +
                "sum(count_pass) as count_pass, " +
                "sum(count_delete) as count_delete, " +
                "sum(count_review) as count_review " +
                " from stat_arrive_delete " +
                " left join code_app on stat_arrive_delete.code_app_id=code_app.id" +
                " where 1=1 " +
                " and code_app.platform_id=" + platformId + " ";

        String condition = "";
        if (flag == 0 || flag == -1) {
            condition = " and  to_days(time_stamp) -to_days(now())= " + flag + ";";
        } else if (flag == 7 || flag == 14 || flag == 30) {
            condition = " and date_sub(now(),interval " + flag + " day)<=time_stamp and now()>=time_stamp;";
        }
        sql += condition;
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list = query.list();
        return list.get(0);
    }

    @Override
    public List<Map> getChapterGeneral(Integer flag, Long platformId) {
        String sql_hours = "select " +
                " sum(count_sq) as count_sq, " +
                " sum(count_zz) as count_zz, " +
                " sum(count_wf) as count_wf, " +
                " sum(count_wg) as count_wg, " +
                " DATE_FORMAT(time_stamp,'%Y-%m-%d %H:00:00') as time_stamp " +
                " from stat_arrive_delete " +
                " left join code_app on stat_arrive_delete.code_app_id=code_app.id " +
                " where 1=1 " +
                " and code_app.platform_id=" + platformId + " " +
                " and  to_days(time_stamp) -to_days(now())= " + flag + " " +
                " group by DATE_FORMAT(time_stamp,'%Y-%m-%d %H:00:00') order by time_stamp;";
        String sql_days = "select " +
                "sum(count_sq) as count_sq, " +
                "sum(count_zz) as count_zz, " +
                "sum(count_wf) as count_wf, " +
                "sum(count_wg) as count_wg, " +
                "DATE_FORMAT(time_stamp,'%Y-%m-%d') as time_stamp " +
                "from stat_arrive_delete " +
                " left join code_app on stat_arrive_delete.code_app_id=code_app.id " +
                "where 1=1 " +
                " and code_app.platform_id=" + platformId + " " +
                " and date_sub(now(),interval " + flag + " day)<=time_stamp and now()>=time_stamp " +
                "group by DATE_FORMAT(time_stamp,'%Y-%m-%d')  order by time_stamp;";
        String sql = "";
        if (flag == 0 || flag == -1) {
            sql = sql_hours;
        } else if (flag == 7 || flag == 14 || flag == 30) {
            sql = sql_days;
        }
        //System.out.println(sql);
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list = query.list();
        return list;
    }

    @Override
    public List<Map> getDistinctType(Long platformId) {
        /*String sql = "select distinct data_type from data_record " +
                " left join  code_app on data_record.code_app_id=code_app.id " +
                " where 1=1 " +
                " and code_app.platform_id=" + platformId + " ";
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);*/
        List<Map> list = new ArrayList();
        Map map=new HashMap();
        map.put("data_type",1);
        list.add(map);
        map=new HashMap();
        map.put("data_type",2);
        list.add(map);
        return list;
    }

    @Override
    public List<Map> getDistinctApp(Long platformId) {
        String sql = "select code_app.id,app_id,app_name " +
                " from code_app " +
                " left join code_platform on code_platform.id=code_app.platform_id " +
                " where 1=1 and code_platform.id=" + platformId + ";";

        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list = query.list();
        return list;
    }


    @Override
    public List<Map> getChapterByType(Integer flag, Long platformId, Integer type) {
        String sql_hours = "select " +
                " sum(count_sq) as count_sq, " +
                " sum(count_zz) as count_zz, " +
                " sum(count_wf) as count_wf, " +
                " sum(count_wg) as count_wg, " +
                " DATE_FORMAT(time_stamp,'%Y-%m-%d %H:00:00') as time_stamp " +
                " from stat_arrive_delete " +
                " left join code_app on stat_arrive_delete.code_app_id=code_app.id " +
                " where 1=1 " +
                " and stat_arrive_delete.ugc_type=" + type +
                " and code_app.platform_id=" + platformId +
                " and  to_days(time_stamp) -to_days(now())= " + flag +
                " group by DATE_FORMAT(time_stamp,'%Y-%m-%d %H:00:00') order by time_stamp;";
        String sql_days = "select " +
                " sum(count_sq) as count_sq, " +
                " sum(count_zz) as count_zz, " +
                " sum(count_wf) as count_wf, " +
                " sum(count_wg) as count_wg, " +
                " DATE_FORMAT(time_stamp,'%Y-%m-%d') as time_stamp " +
                " from stat_arrive_delete " +
                " left join code_app on stat_arrive_delete.code_app_id=code_app.id " +
                " where 1=1 " +
                " and stat_arrive_delete.ugc_type=" + type +
                " and code_app.platform_id=" + platformId +
                " and date_sub(now(),interval " + flag + " day)<=time_stamp and now()>=time_stamp " +
                " group by DATE_FORMAT(time_stamp,'%Y-%m-%d')  order by time_stamp;";
        String sql = "";
        if (flag == 0 || flag == -1) {
            sql = sql_hours;
        } else if (flag == 7 || flag == 14 || flag == 30) {
            sql = sql_days;
        }
        //System.out.println(sql);
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list = query.list();
        return list;
    }

    @Override
    public List<Map> getChapterByApp(Integer flag, Long platformId, Long appId) {
        String sql_hours = "select " +
                " sum(count_sq) as count_sq, " +
                " sum(count_zz) as count_zz, " +
                " sum(count_wf) as count_wf, " +
                " sum(count_wg) as count_wg, " +
                " DATE_FORMAT(time_stamp,'%Y-%m-%d %H:00:00') as time_stamp " +
                " from stat_arrive_delete " +
                " left join code_app on stat_arrive_delete.code_app_id=code_app.id " +
                " where 1=1 " +
                " and stat_arrive_delete.code_app_id=" + appId +
                " and code_app.platform_id=" + platformId +
                " and  to_days(time_stamp) -to_days(now())= " + flag +
                " group by DATE_FORMAT(time_stamp,'%Y-%m-%d %H:00:00') order by time_stamp;";
        String sql_days = "select " +
                " sum(count_sq) as count_sq, " +
                " sum(count_zz) as count_zz, " +
                " sum(count_wf) as count_wf, " +
                " sum(count_wg) as count_wg, " +
                " DATE_FORMAT(time_stamp,'%Y-%m-%d') as time_stamp " +
                " from stat_arrive_delete " +
                " left join code_app on stat_arrive_delete.code_app_id=code_app.id " +
                " where 1=1 " +
                " and stat_arrive_delete.code_app_id=" + appId +
                " and code_app.platform_id=" + platformId +
                " and date_sub(now(),interval " + flag + " day)<=time_stamp and now()>=time_stamp " +
                " group by DATE_FORMAT(time_stamp,'%Y-%m-%d')  order by time_stamp;";
        String sql = "";
        if (flag == 0 || flag == -1) {
            sql = sql_hours;
        } else if (flag == 7 || flag == 14 || flag == 30) {
            sql = sql_days;
        }
        //System.out.println(sql);
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list = query.list();
        return list;
    }

    /*public void insertIntoStatArrive() {
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        Query query = session.createSQLQuery("{call p1()}");
        query.executeUpdate();
    }*/


}
