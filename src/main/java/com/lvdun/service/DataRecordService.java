package com.lvdun.service;

import com.lvdun.entity.DataRecord;
import com.lvdun.queryModel.DataRecordQuery;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/21.
 */
public interface DataRecordService {
    public Map getDataRecordPage(Integer page, Integer pageSize, Long platformId, DataRecordQuery dataRecordQuery);

    public void setStatus(Long id, Integer status);

    public void setReasonCode(Long id, Integer reasonCode);

    void setReasonCodeBatch(String reasonCodeJson);

    void save(DataRecord dataRecord);
}
