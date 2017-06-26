package com.lvdun.dao.impl;

import com.lvdun.dao.DataRecordDao;
import com.lvdun.queryModel.DataRecordQuery;
import com.lvdun.util.StringUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/21.
 */
public class DataRecordRepositoryImpl implements DataRecordDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Map getDataRecordPage(Integer page, Integer pageSize, Long platformId, DataRecordQuery dataRecordQuery) {
        String sql = "select " +
                " data_record.id, " +
                " data_record.user_ip, " +
                " date_format(data_record.publish_date, '%Y-%m-%d %H:%I') as publish_date,"+
                " data_resource.data_content, " +
                " data_record.reason_code, " +
                " data_record.status " +
                " from data_record " +
                " left join data_resource on data_record.id=data_resource.data_record_id " +
                " left join code_app on data_record.code_app_id=code_app.id " +
                " left join code_platform on code_platform.id=code_app.platform_id " +
                " where 1=1 " +
                " and code_platform.id=" + platformId;
        String sqlCondition = "";
        if (StringUtil.isNotEmpty(dataRecordQuery.getStartTime())) {
            sqlCondition += " and data_record.create_date >" + dataRecordQuery.getStartTime();
        }
        if (StringUtil.isNotEmpty(dataRecordQuery.getEndTime())) {
            sqlCondition += " and data_record.create_date <date_sub(str_to_date('"+dataRecordQuery.getEndTime()+"', '%Y-%m-%d %H'),interval -1 day)";
        }
        if (StringUtil.isNotEmpty(dataRecordQuery.getDataType())) {
            sqlCondition += " and data_record.data_type =" + dataRecordQuery.getDataType();
        }
        if (StringUtil.isNotEmpty(dataRecordQuery.getAppName())) {
            sqlCondition += " and code_app.id =" + dataRecordQuery.getAppName();
        }
        if (StringUtil.isNotEmpty(dataRecordQuery.getStatus())) {
            sqlCondition += " and data_record.status =" + dataRecordQuery.getStatus();
        }
        if (StringUtil.isNotEmpty(dataRecordQuery.getReasonCode())) {
            sqlCondition += " and data_record.reason_code =" + dataRecordQuery.getReasonCode();
        }
        if (StringUtil.isNotEmpty(dataRecordQuery.getReviewType())) {
            if (dataRecordQuery.getReviewType() == -1) {//
                sqlCondition += " and data_record.operator_id =-1";
            } else if (dataRecordQuery.getReviewType() == 0) {
                sqlCondition += " and data_record.operator_id >-1";
            } else {

            }
        }
        if (StringUtil.isNotEmpty(dataRecordQuery.getConditionName())&&StringUtil.isNotEmpty(dataRecordQuery.getConditionValue())) {
            if ("data_content".equals(dataRecordQuery.getConditionName())) {
                sqlCondition += " and data_resource.data_content  like '%" + dataRecordQuery.getConditionValue() + "%'";
            } else if ("user_ip".equals(dataRecordQuery.getConditionName())) {
                sqlCondition += " and data_record.user_ip  like '%" + dataRecordQuery.getConditionValue()+"%'";
            } else if ("device_id".equals(dataRecordQuery.getConditionName())) {
                sqlCondition += " and data_record.device_id like '%" + dataRecordQuery.getConditionValue() + "%'";
            }else if ("user_id".equals(dataRecordQuery.getConditionName())) {
                sqlCondition += " and data_record.user_id like '%" + dataRecordQuery.getConditionValue() + "%'";
            }
        }

        sql += sqlCondition;

        Session session = entityManager.unwrap(org.hibernate.Session.class);
        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Map> list = query.list();


        String sqlCount = "select count(*) as count " +
                " from data_record " +
                " left join data_resource on data_record.id=data_resource.data_record_id " +
                " left join code_app on data_record.code_app_id=code_app.id " +
                " left join code_platform on code_platform.id=code_app.platform_id " +
                " where 1=1 " +
                " and code_platform.id=" + platformId;
        sqlCount += sqlCondition;
        query = session.createSQLQuery(sqlCount);
        Integer count = Integer.parseInt("" + query.uniqueResult());


        int size = count / pageSize;//总条数/每页显示的条数=总页数
        int mod = count % pageSize;//最后一页的条数
        if (mod != 0) {
            size++;
        }


        Map result = new HashMap();
        result.put("content", list);
        result.put("total", count);
        result.put("pageCount", count == 0 ? 1 : size);
        result.put("currentPage", page);
        result.put("pageSize", pageSize);


        return result;
    }


/*
    @Override
    public void insertTest(String apiVersion,Integer codeAppId,String createDate,Integer dataType,String deviceId,String digest,Integer filtertypeId,Integer imageCount,Integer operatorId,String publishDate,Integer reasonCode,Integer status,Integer sysPolicy,Integer sysStatus,String updateDate,Integer userId,) {
        String sql = " insert into data_record(api_version,code_app_id,create_date,data_type,device_id,digest,filter_type_id,image_count,operator_id,publish_date,reason_code,status,sys_policy,sys_status,update_date,user_id,user_ip,user_publish_data_id,word_count,has_count) " +
                " values" +
                " (0,0,now(),0,0,0,0,0,0,now(),0,0,0,0,now(),0,0,0,0,0); " ;


        Session session = entityManager.unwrap(org.hibernate.Session.class);
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
    }*/
}
