package com.lvdun.dao;

import com.lvdun.queryModel.DataRecordQuery;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/21.
 */
public interface DataRecordDao {
    /*public void insertTest();*/
    public Map getDataRecordPage(Integer page, Integer pageSize, Long platformId, DataRecordQuery dataRecordQuery);
}
